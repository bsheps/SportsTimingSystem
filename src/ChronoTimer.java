import java.io.IOException;
import java.util.ArrayList;
/**
 * This class handles the administrative parts of 
 * instantiating a race. It prevents multiple races
 * from happening simultaneously. Handles communications
 * with the printer/results. Also acts as the connection 
 * between channels and events.
 * @author BS
 *
 */
public class ChronoTimer implements Commands {
	Channel chan;
	String _eventName = "IND";
	IndividualEvent indEvent;
	ParaIndEvent paraEvent;
	boolean _powerOn= false;
	boolean _raceInSession=false;
	Printer print;
	boolean parIndEvent=false,indiEvent=false;
	
	ArrayList<FinishedRacer> q1 = new ArrayList<FinishedRacer>();
	ArrayList<FinishedRacer> q2 = new ArrayList<FinishedRacer>();

	public void printResults() {
		if(_eventName.equals("IND")) {
			while(indEvent.finishers.size() > 0) {
				Racer p = indEvent.finishers.remove();
				print.printThis(p._bibNum + " " + p.results());
			}
			while(indEvent.inTheRace.size() >0) {
				Racer d = indEvent.inTheRace.remove();
				print.printThis(d._bibNum);
			}
			while(indEvent.WaitingToRace.size()>0) {
				Racer q = indEvent.WaitingToRace.remove();
				print.printThis(q._bibNum);
			}
		}
		else if(_eventName.equals("PARIND")) {
			while(paraEvent.finishers.size() > 0) {
				Racer r = paraEvent.finishers.remove();
				print.printThis(r._bibNum);
			}

			//i'm not sure if racers in waiting to race get a DNF if the race ends or not,
			//			while(paraEvent.finishers.size() > 0) {
			//				Racer f = paraEvent.waitingToRace.remove();
			//				print.printThis(f._bibNum);
			//			}
		}
	}
	// clears the memory
	public void reset() throws IOException {
		print.PrinterRest();
	}
	
	public void usePrinter(String string){
		print.printThis(string);
	}
	
	public void endRun() {
		_raceInSession= false;
		print.printThis("Ending current run");
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
		paraEvent.endEvent(true);
	}

	@Override
	public void ENDRUN() {
		_raceInSession = false;
		// TODO Storage of the results needs to happen here

	}

	public void EVENT(String eventName) {
		if(_raceInSession) System.out.println("ERROR: End current event before setting a new event.");
		else if(eventName.equals("IND")|| eventName.equals("PARIND"))
		{
			_eventName = eventName;
			print.printThis("Setting event to " + eventName);
		}
		else
			System.out.println("ERROR: INVALID EVENT NAME");
	}

	@Override
	public void EXIT() {
		// TODO Auto-generated method stub

	}

	@Override
	public void EXPORT(int runNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void FINISH() {
		// TODO Auto-generated method stub

	}

	@Override
	public void NEWRUN() {
		if(_raceInSession) System.out.println("ERROR RACE IN SESSION: End current run before starting a NEWRUN.");
		else{
			_raceInSession = true;
			if(_eventName.equals("IND"))
				indEvent = new IndividualEvent();
			else if(_eventName.equals("PARIND"))
				paraEvent = new ParaIndEvent();
		}
	}

	public void NUM(String bibNumber) {
		if(indiEvent)
			indEvent.addRacer(bibNumber);
		else if(parIndEvent)
			paraEvent.addRacer(bibNumber);
	}


	public void POWER() {
		if(!_powerOn) {
			_powerOn = true;
			chan = new Channel();
			_raceInSession = false;
			Time.startTime();
		}
		else if(_powerOn){ 
			_powerOn = false;
			_raceInSession = false;
			if(_eventName.equals("IND") && indEvent != null) {
				print.printThis("POWERING OFF- PENDING ITEMS:");
				printResults();
				print.shutDownPrinter();
			}
			indEvent = null;

			if(_eventName.equals("PARIND") && paraEvent!=null) {
				print.printThis("POWERING OFF - PENDING ITEMS:");
				printResults();
				print.shutDownPrinter();
			}
			paraEvent = null;


		}	
	}

	@Override
	public void PRINT(int runNumber) {


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
		if(chan.Toggle(channelNumber)) System.out.println("Channel "+channelNumber+" was enabled.");
		else System.out.println("Channel "+channelNumber+" was disabled.");
	}


	//could we pass on the event type because this is only for indrun 
	@Override
	public void TRIG(int channelNumber) {
		if(chan.isChannelEnabled(channelNumber) && _raceInSession) {
			if(_eventName.equals("IND")) 
				indEvent.trigger(channelNumber);
			else if(_eventName.equals("PARIND")) 
				paraEvent.trigger(channelNumber);
				
			}
		// else do nothing, channel is disabled or a race is not in session
	}
		
	

	@Override
	public void START() {
		// TODO Auto-generated method stub

	}

	@Override
	public void SWAP() {
		// TODO Auto-generated method stub

	}
	
	public void initalizeArray() {
		
	}
	
	public void setStartTime(){
		
	}
	
	public void setEndTime() {
		
	}
	
	
	
}