import java.util.ArrayList;
import java.util.Queue;

public interface EventInterface {
	void addRacer(String bibNumber);
	void trigger(int channelNumber);
	Queue<Racer> moveAll();
	ArrayList<Racer> getResults(); //return (finished + racing + queued)
}
