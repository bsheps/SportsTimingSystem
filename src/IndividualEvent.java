import java.io.IOException;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

public class IndividualEvent {
	Queue<Racer> WaitingToRace, inTheRace, finishers;
	Printer printer;
	/**
	 * instantiates a new individual event. Racers can be in 1 of 3 states: waiting to race, currently racing and finished racing
	 * @throws IOException
	 */
	public IndividualEvent(Printer p) throws IOException{
		WaitingToRace = (Queue<Racer>) new LinkedList<Racer>();
		inTheRace= (Queue<Racer>) new LinkedList<Racer>();
		finishers= (Queue<Racer>) new LinkedList<Racer>();
		printer = p;
	}

	public void addRacer(String name){
		WaitingToRace.add(new Racer(name));
	}
	/**
	 * Indicates that a sensor was "triggered" and stores the corresponding time in 
	 * either the start or finish fields of the racer (odd channels start, even finish)
	 * @param chNum
	 * @param time
	 */
	public void trigger(int chNum, LocalTime time) {
		if(!Channel.channel[chNum]) {
			printer.printThis("", "ERROR: Channel "+ chNum+ " is disabled", false);
		} // do nothing, possibly print error
		else{
			
			if(chNum  == 1){	// odd means start time
				if(WaitingToRace.size() == 0) {
					Racer n = new Racer("noName");
					n.startRace(time);
					inTheRace.add(n);
					printer.printThis("", " Channel " + chNum+ " triggered at " +Time.time2formattedString(time) + " for racer: noName", false);
				}
				else {
					Racer x = WaitingToRace.remove();
					x.startRace(time);
					printer.printThis("", " Channel " + chNum+ " triggered at " +Time.time2formattedString(time) + " for racer: " +x.bibNum, false);
					inTheRace.add(x);
					x = null;
				}
			}
			else if(chNum == 2){	// even means end race
				if(inTheRace.size() == 0) printer.printThis("","Channel " + chNum +" triggered at " + Time.time2formattedString(time) +"--ERROR: No more racers", false);
				else {
					Racer y = inTheRace.remove();
					y.finishRace(time);
					printer.printThis("", " Channel " + chNum+ " triggered at " +Time.time2formattedString(time) + " for racer: " +y.bibNum, false);
					finishers.add(y);
				}
			}
		}
	}
}