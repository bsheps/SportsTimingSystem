import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author faassad
 * channel 1,3 is start, channel 2,4 is for finish 
 */

public class ParaIndEvent {
	Queue<Racer> channels12,finishers,channels34,waitingToRace; 
	Printer printer;

	public ParaIndEvent() {
		channels12 = (Queue<Racer>) new LinkedList<Racer>();
		channels34 = (Queue<Racer>)new LinkedList<Racer>();
		waitingToRace = (Queue<Racer>) new LinkedList<Racer>();
		finishers = (Queue<Racer>) new LinkedList<Racer>();
	}

	public void addRacer(String name) {
		waitingToRace.add(new Racer(name));
	}

	/**
	 * @param chanNum
	 * @param time
	 */
	public void trigger(int chanNum, LocalTime time) {
		if(!Channel.channel[chanNum]) printer.printThis("", "Channel "+ chanNum+" is disabled", false);
		if(waitingToRace.size()==0) {
			Racer noName = new Racer("noName");
			noName.startRace(time);			
			waitingToRace.add(noName);
		}
		else {
			Racer racer = waitingToRace.remove();
			if(chanNum==1) {
				channels12.add(racer);
				racer.startRace(time);
			}
			if(chanNum==2) {
				finishers.add(channels12.remove());
				racer.finishRace(time);
			}
			if(chanNum==3) {
				channels34.add(racer);
				racer.startRace(time);
			}
			if(chanNum==4) {
				finishers.add(channels34.remove());
				racer.finishRace(time);
			}
		}
	}

	public void endEvent(boolean endRace) {
		if(endRace) {
			while(channels12.size()!=0)
				channels12.remove().startRace(null);
			while(channels34.size()!=0)
				channels34.remove().startRace(null);
		}
	}

}
