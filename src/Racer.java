import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author bshepard Racers has 3 fields: name, startTime and endTime
 */
public class Racer {
	final DateTimeFormatter formatTime=DateTimeFormatter.ofPattern("HH:mm:ss.SS");;
	String bibNum;
	LocalTime startTime, endTime;
	/**
	 * Constructs a racer
	 * @param name
	 */
	public Racer(String name){
		startTime = endTime = null;
		bibNum = name;
	}
	/**
	 * Sets the start time for racer
	 * @param time
	 */
	public void startRace(LocalTime time) {
		startTime = time;
	}
	/**
	 * Sets the end time for racer
	 * @param time
	 */
	public void finishRace(LocalTime time) {
		endTime = time;
	}
	/**
	 * 
	 * @return a printer friendly string of the racer's time
	 */
	public String results() {
		if(endTime == null || startTime == null)return "Error printing results";
		return LocalTime.ofNanoOfDay(endTime.toNanoOfDay() - startTime.toNanoOfDay()).format(formatTime);
	}


}