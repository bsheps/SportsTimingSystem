import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
/**
 * @author BS
 * Lets the user set a current time and retrieve updated time
 */
public class Time {
	static LocalTime _startTime;
	static LocalTime _setTime = null;
	final static DateTimeFormatter FORMAT_TIME=DateTimeFormatter.ofPattern("HH:mm:ss.SS");;
	/**
	 * Lets the user set a new local time
	 */
	public static void startTime(){
		_startTime = LocalTime.now();
	}
	/**
	 * _setTime = user input
	 * @param userInputTime
	 */
	public static void setTime(String userInputTime) {
		try {
		String[] timeParse = userInputTime.split(":|\\.");
		_setTime= LocalTime.of(Integer.parseInt(timeParse[0]), Integer.parseInt(timeParse[1]), Integer.parseInt(timeParse[2]), Integer.parseInt(timeParse[3]));
		}
		catch(Exception e) {
			System.out.println("ERROR SETTING TIME: "+ e.getMessage());
		}
	}
	
	
	public static LocalTime getLocalTime() {
		return LocalTime.now();
	}
	/**
	 * @return a the local time(setTime must be called before you can get this time)
	 */
	public static LocalTime getCurrentTime() {
		return Time.string2LocalTime(Time.getCurrentTimeString().split(":|\\."));
	}
	
	public static String getCurrentTimeString(){
		return _setTime == null? LocalTime.ofNanoOfDay(LocalTime.now().toNanoOfDay()).format(FORMAT_TIME): 
			LocalTime.ofNanoOfDay(_setTime.toNanoOfDay() + LocalTime.now().toNanoOfDay() - _startTime.toNanoOfDay()).format(FORMAT_TIME);
	}
	
	public static LocalTime string2LocalTime(String[] time) {
		return LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]), Integer.parseInt(time[3]));
	}
	/**
	 * @param time
	 * @return a string of the time in HH:mm:ss.SS format
	 */
	public static String time2formattedString(LocalTime time){
		if(time == null)return null;
		return time.format(FORMAT_TIME);
	}
	 

}