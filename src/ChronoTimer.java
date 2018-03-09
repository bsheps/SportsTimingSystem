import java.io.IOException;
import java.time.LocalTime;
/**
 * This class handles the administrative parts of 
 * instantiating a race. It prevents multiple races
 * from happening simultaneously. Handles communications
 * with the printer/results. Also acts as the connection 
 * between channels and events.
 * @author BS
 *
 */
public class ChronoTimer {

	IndividualEvent indEvent ;
	boolean power= false;
	boolean raceInSession;
	Printer print;
	/**
	 * Powers the unit on and off. When powering off 
	 * the unit tries to printResults if there are any
	 * @throws IOException
	 */
	public void power() throws IOException {
		power = !power;
		raceInSession = false;
		print = new Printer();
		indEvent= new IndividualEvent(print);
		if(!power){ 
			raceInSession = false;
			if(indEvent != null) {
				print.printThis("", "POWERING OFF- PENDING ITEMS:", false);
				printResults();
				print.shutDownPrinter();
			}
			indEvent = null;
		}
	}

	public void trigger(LocalTime times, String tokens) {
		int channel = Integer.parseInt(tokens);
		switch(tokens){
		case "1" :
			indEvent.trigger(channel, times);
			break;
		case "2" :
			indEvent.trigger(channel, times);
			break;
		case "3" :
			indEvent.trigger(channel, times);
			break;
		case "4" :
			indEvent.trigger(channel, times);
			break;
		}
	}

	public void initiateNewEvent() throws IOException {
		indEvent = new IndividualEvent(print);
		print.printThis("", "Starting new IND", false);
	}
	public void startNewRun() {
		if(raceInSession) {//errormessage
			print.printThis("", "RACE ALREADY STARTED", false);
		}
		else {
			raceInSession = true;
			print.printThis("", "STARTING NEW RACE", false);
		}
	}

	public void setBib(String string) {
		indEvent.addRacer(string);
	}

	public void printResults() {
		while(indEvent.finishers.size() > 0) {
			Racer p = indEvent.finishers.remove();
			print.printThis("Finished", p.bibNum + " " + p.results(), false);
		}
		while(indEvent.inTheRace.size() >0) {
			Racer d = indEvent.inTheRace.remove();
			print.printThis("DNF", d.bibNum, false);
		}
		while(indEvent.WaitingToRace.size()>0) {
			Racer q = indEvent.WaitingToRace.remove();
			print.printThis("Never Started", q.bibNum, false);
		}
	}
	// clears the memory
	public void reset() throws IOException {
		print.PrinterRest();
	}
	public void usePrinter(String string){
		print.printThis("", string, false);
	}
	public void endRun() {
		raceInSession= false;
		print.printThis("", "Ending current run", false);
	}
}