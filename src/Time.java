import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
/**
 * @author BS
 * Lets the user set a current time and retrieve updated time
 */
public class Time {
	static LocalTime startTime;
	final static DateTimeFormatter formatTime=DateTimeFormatter.ofPattern("HH:mm:ss.SS");;
	/**
	 * Lets the user set a new local time
	 */
	public static void startTimer(){
		startTime = LocalTime.now();
	}
	public static LocalTime getLocalTime() {
		return LocalTime.now();
	}
	/**
	 * @return a the local time(setTime must be called before you can get this time)
	 */
	public static String getCurrentSetTime(){
		return LocalTime.ofNanoOfDay(LocalTime.now().toNanoOfDay() - startTime.toNanoOfDay()).format(formatTime);
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
		return time.format(formatTime);
	}
	 

}