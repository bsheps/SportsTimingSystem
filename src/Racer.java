import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author bshepard Racers has 3 fields: name, startTime and endTime
 * 
 *         bjf: I've added getters and setters for each of the fields related to
 *         Json parsing...
 */
public class Racer {
	private final DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss.SS");;
	private String _bibNum;
	private LocalTime _startTime, _endTime;

	/**
	 * Constructs a racer
	 * 
	 * @param name
	 */
	public Racer() {
		this(null, null, null);
	}

	public Racer(String name) {
		this(name, null, null);
	}

	public Racer(String name, LocalTime start) {
		this(name, start, null);
	}

	public Racer(String name, LocalTime start, LocalTime end) {
		if (name == null) {
			throw new IllegalArgumentException("Racer should not have a null name/bibNum");
		}
		_bibNum = name;
		_startTime = start;
		_endTime = end;
	}

	public String getBibNum() {
		return _bibNum;
	}

	public void setBibNum(String bib) {
		if (bib != null) {
			this._bibNum = bib;
		}
	}

	public LocalTime getStartTime() {
		return _startTime;
	}

	public void setStartTime(LocalTime st) {
		if (st != null) {
			this._startTime = st;
		}
	}

	public LocalTime getEndTime() {
		return _endTime;
	}

	public void setEndTime(LocalTime et) {
		if (et != null) {
			this._endTime = et;
		}
	}

	/**
	 * Sets the start time for racer
	 * 
	 * @param time
	 */
	public void startRace(LocalTime time) {
		_startTime = time;
	}

	/**
	 * Sets the end time for racer
	 * 
	 * @param time
	 */
	public void finishRace(LocalTime time) {
		_endTime = time;
	}

	/**
	 * bjf: modified to return race state (waiting/dnf)
	 * 
	 * @return a printer friendly string of the racer's time
	 */
	public String results() {
		if (_endTime == null && _startTime == null) {
			return "WAITING TO RACE";
		} else if (_endTime == null) {
			return "DNF";
		} else {
			return LocalTime.ofNanoOfDay(_endTime.toNanoOfDay() - _startTime.toNanoOfDay()).format(formatTime);
		}
	}

}