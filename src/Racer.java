import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author bshepard Racers has 3 fields: name, startTime and endTime
 */
public class Racer {
	final DateTimeFormatter formatTime=DateTimeFormatter.ofPattern("HH:mm:ss.SS");;
	String _bibNum;
	LocalTime _startTime, _endTime;
	/**
	 * Constructs a racer
	 * @param name
	 */
	public Racer(String name){
		_startTime = _endTime = null;
		_bibNum = name;
	}
	public Racer(String name, LocalTime start) {
		_startTime = start;
		_endTime = null;
		_bibNum = name;
	}
	/**
	 * Sets the start time for racer
	 * @param time
	 */
	public void startRace(LocalTime time) {
		_startTime = time;
	}
	/**
	 * Sets the end time for racer
	 * @param time
	 */
	public void finishRace(LocalTime time) {
		_endTime = time;
	}
	/**
	 * 
	 * @return a printer friendly string of the racer's time
	 */
	public String results() {
		if(_endTime == null || _startTime == null)return "Error printing results";
		return LocalTime.ofNanoOfDay(_endTime.toNanoOfDay() - _startTime.toNanoOfDay()).format(formatTime);
	}


}