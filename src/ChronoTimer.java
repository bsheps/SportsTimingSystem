import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Queue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class handles the administrative parts of instantiating a race. It
 * prevents multiple races from happening simultaneously. Handles communications
 * with the printer/results. Also acts as the connection between channels and
 * events.
 * 
 * @author BS
 * @author bjf
 *
 */
public class ChronoTimer implements CommandsInterface {
	/*
	 * I made all data fields here private to ensure they are not accessed from the
	 * outside without using our getters and setters.
	 */

	// bjf: chan (Channel) will be set when POWER() is called.
	private Channel chan;

	// bjf: print is initialized in constructor
	private Printer print;

	// bjf: initialize EventInterface with NEWRUN() after EVENT() has been called
	private EventInterface _event;

	// Title of current event name
	private String _eventName;

	// Keep track of CT state
	private boolean _powerOn, _raceInSession;

	// bjf: ?
	private ArrayList<Queue<Racer>> storageUnit;

	// bjf: finishedRuns is holding all of the old events, no matter the type
	private ArrayList<EventInterface> finishedRuns;

	/**
	 * bjf: This constructor initializes the standard components and state
	 * variables. I removed the booleans, "ind" and "parind" because I believe they
	 * store redundant information. I am opting to store this information as a
	 * string in the _eventName field.
	 * 
	 * @throws IOException
	 *             (See Printer() documentation)
	 */
	public ChronoTimer() throws IOException {
		this._powerOn = false;
		this._raceInSession = false;
		// this._eventName = null; //redundant
		// this._event = null; //redundant
		this.print = new Printer();
		this.storageUnit = new ArrayList<Queue<Racer>>();
		this.finishedRuns = new ArrayList<EventInterface>();
	}

	// public void printResults() {
	// if(_eventName.equals("IND")) {
	// while(_event.finishers.size() > 0) {
	// Racer p = _event.finishers.remove();
	// print.printThis(p._bibNum + " " + p.results());
	// }
	// while(_event.inTheRace.size() >0) {
	// Racer d = _event.inTheRace.remove();
	// print.printThis(d._bibNum);
	// }
	// while(_event.WaitingToRace.size()>0) {
	// Racer q = _event.WaitingToRace.remove();
	// print.printThis(q._bibNum);
	// }
	// }
	// else if(_eventName.equals("PARIND")) {
	// while(_event.finishers.size() > 0) {
	// Racer r = _event.finishers.remove();
	// print.printThis(r._bibNum);
	// }
	//
	// //i'm not sure if racers in waiting to race get a DNF if the race ends or
	// not,
	// // while(paraEvent.finishers.size() > 0) {
	// // Racer f = paraEvent.waitingToRace.remove();
	// // print.printThis(f._bibNum);
	// // }
	// }
	// }

	/**
	 * clears the memory
	 * 
	 * @throws IOException
	 */
	public void reset() throws IOException {
		print.PrinterRest();
	}

	/**
	 * Send a string to the printer from outside this class.
	 * 
	 * @param string
	 *            - text to be printed.
	 */
	public void usePrinter(String string) {
		print.printThis(string);
	}

	public void endRun() {
		_raceInSession = false;
		storageUnit.add(_event.moveAll());

		/**
		 * For Future Use
		 * 
		 * else if (_eventName.equals("GRP")) { storageUnit.add(groupEvent.moveAll()); }
		 * else { storageUnit.add(paraGroupEvent.moveAll()); }
		 * 
		 **/

		print.printThis("Ending current run");

	}

	/**
	 * bjf: simple field getter
	 * 
	 * @return int - the size of the numbered runs
	 */
	public int getFinishedRunsSize() {
		return finishedRuns.size();
	}

	@Override
	public void CLR(int bibNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void CONN(String sensorType, int channel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void DISC(int channel2disconnect) {
		// TODO Auto-generated method stub

	}

	@Override
	public void DNF() {

	}

	@Override
	public void ENDRUN() {
		_raceInSession = false;
		finishedRuns.add(_event);
		if (_event != null) {
			storageUnit.add(_event.moveAll());
		}
		// TODO Storage of the results needs to happen here

	}

	public void EVENT(String eventName) {
		if (_raceInSession) {
			System.out.println("ERROR: End current event before setting a new event.");
		} else if (eventName.equals("IND") || eventName.equals("PARIND")) {
			_eventName = eventName;
			print.printThis("Setting event to " + eventName);
		} else {
			System.out.println("ERROR: INVALID EVENT NAME");
		}
	}

	@Override
	public void EXIT() {
		// TODO Auto-generated method stub

	}

	/*
	 * bjf: should not be able to export a race in progress
	 * 
	 * @see CommandsInterface#EXPORT(int)
	 */
	@Override
	public void EXPORT(int runNumber) {
		if (runNumber <= 0 || runNumber > finishedRuns.size()) {
			throw new IllegalArgumentException("Could not find run: " + runNumber);
		}
		// _event.moveAll();
		ArrayList<Racer> thisRun = finishedRuns.get(runNumber).getResults();
		if (thisRun.size() == 0) {
			System.out.println("   JSON EXPORT FOUND NO CONTENT");
		} else {

			/*
			 * bjf: The following three lines are somewhat confusing, but necessary to
			 * prevent a JSON error regarding illegal reflective access... basically I am
			 * using an adapter to define how JSON should parse a racer class. This way it
			 * will not serialize the data
			 */
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(Racer.class, new RacerAdapter());
			Gson g = builder.create();
			/* End confusing bit */
			
			String out = g.toJson(thisRun);
			File f = new File("RUN" + runNumber + ".txt");

			try (PrintWriter pw = new PrintWriter(f);) {
				pw.write(out.toString());
				System.out.println("   JSON EXPORT SUCCESSFUL");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void FINISH() {
		// TODO Auto-generated method stub

	}

	@Override
	public void NEWRUN() {
		if (_raceInSession) {
			System.out.println("ERROR RACE IN SESSION: End current run before starting a NEWRUN.");
		} else {
			_raceInSession = true;

			if (_eventName.equals("IND")) {
				_event = new IndividualEvent();
			} else if (_eventName.equals("PARIND")) {
				_event = new ParaIndEvent();
			}

		}
	}

	public void NUM(String bibNumber) {
		_event.addRacer(bibNumber);
	}

	public void POWER() {
		if (!_powerOn) {
			chan = new Channel();
			Time.startTime();
		} else if (_powerOn) {
			print.printThis("POWERING OFF- PENDING ITEMS:");
			print.shutDownPrinter();
		}
		_powerOn = !_powerOn;
		_raceInSession = false;

	}

	/*
	 * bjf: wasn't sure exactly what they were looking for with this command,
	 * because I didn't see an entry in the Sprint 1 or Sprint 2 pdf's about this,
	 * but it was in the sprint2test.txt that was given. I decided that because it
	 * was only called at the end of a run, that it should just print the results of
	 * the last/current race. I didn't see any "PRINT ##" commands in the test
	 * files, and that is why I removed the runNumber argument from the method
	 * header.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see CommandsInterface#PRINT(int)
	 */
	@Override
	public void PRINT() {
		ArrayList<Racer> results = _event.getResults();

		for (Racer r : results) {
			print.printThis("Bib Number = " + r.getBibNum() + ", Result = " + r.results());
		}

	}

	@Override
	public void RESET() {
		// TODO Auto-generated method stub

	}

	@Override
	public void TIME(String time) {
		Time.setTime(time);
	}

	@Override
	public void TOG(int channelNumber) {
		if (chan.Toggle(channelNumber)) {
			System.out.println("Channel " + channelNumber + " was enabled.");
		} else {
			System.out.println("Channel " + channelNumber + " was disabled.");
		}
	}

	// could we pass on the event type because this is only for indrun
	@Override
	public void TRIG(int channelNumber) {
		if (!_raceInSession) {
			throw new RuntimeException("Could not trigger channel " + channelNumber + ". Race not is session.");
		}
		if (chan.isChannelEnabled(channelNumber) && _raceInSession) {
			_event.trigger(channelNumber);
		}
		// else do nothing, channel is disabled or a race is not in session
	}

	@Override
	public void START() {
		/*
		 * I simplified this method for now. There should be an easier way to access
		 * start triggers than by hard-coding them in.
		 */
		_event.trigger(1);
	}

	@Override
	public void SWAP() {
		// TODO Auto-generated method stub

	}

}