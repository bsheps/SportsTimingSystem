import java.util.LinkedList;
import java.util.Queue;

public class IndividualEvent implements EventInterface{
	Queue<Racer> WaitingToRace = (Queue<Racer>) new LinkedList<Racer>(),
			inTheRace = (Queue<Racer>) new LinkedList<Racer>(),
			finishers = (Queue<Racer>) new LinkedList<Racer>();

	public void addRacer(String name){
		WaitingToRace.add(new Racer(name));
	}
	/**
	 * Indicates that a sensor was "triggered" and stores the corresponding time in 
	 * either the start or finish fields of the racer (odd channels start, even finish)
	 * @param chNum
	 * @param Time.getCurrentTime()
	 */
	public void trigger(int chNum) {
		if(chNum   == 1){	// odd means start time
			if(WaitingToRace.size() == 0) {
				Racer n = new Racer("noName");
				n.startRace(Time.getCurrentTime());
				inTheRace.add(n);
				//print.printThis(" Channel " + chNum+ " triggered at " +Time.time2formattedString(Time.getCurrentTime()) + " for racer: noName");
			}
			else {
				Racer x = WaitingToRace.remove();
				x.startRace(Time.getCurrentTime());
				//print.printThis(" Channel " + chNum+ " triggered at " +Time.time2formattedString(Time.getCurrentTime()) + " for racer: " +x._bibNum);
				inTheRace.add(x);
				x = null;
			}
		}
		else if(chNum == 2){	// even means end race
			if(inTheRace.size() == 0); //print.printThis("Channel " + chNum +" triggered at " + Time.time2formattedString(Time.getCurrentTime()) +"--ERROR: No more racers");
			else {
				Racer y = inTheRace.remove();
				y.finishRace(Time.getCurrentTime());
				//print.printThis(" Channel " + chNum+ " triggered at " +Time.time2formattedString(Time.getCurrentTime()) + " for racer: " +y._bibNum);
				finishers.add(y);
			}
		}
	}
	
	public Queue<Racer> moveAll(){
		for (Racer x : inTheRace ) {
			x = inTheRace.remove();
			finishers.add(x);
		}
		for (Racer y : WaitingToRace) {
			y = WaitingToRace.remove();
			finishers.add(y);
		}
		
		return finishers;
	}
	@Override
	public Queue<Racer> getFinishers() {
		// TODO Auto-generated method stub
		return finishers;
	}
}