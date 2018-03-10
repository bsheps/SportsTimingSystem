import java.io.IOException;
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
	IndividualEvent indEvent;
	ParaIndEvent paraEvent;
	boolean _powerOn= false;
	boolean _raceInSession=false;
	Printer print;
	boolean parIndEvent=false,indiEvent=false;

	public void printResults() {
		if(indiEvent) {
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
		else if(parIndEvent) {
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
		// TODO Auto-generated method stub

	}

	public void EVENT(String eventName) {
		if(_raceInSession) System.out.println("ERROR: End current event before starting a new event.");
		if(eventName.equals("IND"))
		{
			indEvent = new IndividualEvent();
			print.printThis("Starting new IND");
		}
		else if(eventName.equals("PARAIND")) {
			paraEvent = new ParaIndEvent();
			print.printThis("Starting new PARAIND");
		}
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
		if(indiEvent)
			indEvent = new IndividualEvent();
		else if(parIndEvent)
			paraEvent = new ParaIndEvent();
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
			if(indiEvent && indEvent != null) {
				print.printThis("POWERING OFF- PENDING ITEMS:");
				printResults();
				print.shutDownPrinter();
			}
			indEvent = null;

			if(parIndEvent && paraEvent!=null) {
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
	public void TRIG(int channelNumber, String eventType) {
		if(eventType==null) return;
		if(chan.isChannelEnabled(channelNumber)) {
			if(eventType.equals("IND")) {
				indiEvent=true;
				switch(channelNumber){
				case 1 :
					indEvent.trigger(channelNumber);
					break;
				case 2 :
					indEvent.trigger(channelNumber);
					break;
				case 3 :
					indEvent.trigger(channelNumber);
					break;
				case 4 :
					indEvent.trigger(channelNumber);
					break;
				}
			}
			else if(eventType.equals("PARAIND")) {
				//can i just do it like this instead of switch statement?
				//				while(channelNumber>0 && channelNumber<5){
				//					paraEvent.trigger(channelNumber);
				//				}
				parIndEvent=true;
				switch(channelNumber) {
				case 1:
					paraEvent.trigger(channelNumber);
					break;
				case 2:
					paraEvent.trigger(channelNumber);
					break;
				case 3: 
					paraEvent.trigger(channelNumber);
					break;
				case 4: 
					paraEvent.trigger(channelNumber);
					break;


				}
			}
		}
		// else do nothing, channel is disabled
	}

	@Override
	public void START() {
		// TODO Auto-generated method stub

	}

	@Override
	public void SWAP() {
		// TODO Auto-generated method stub

	}
}