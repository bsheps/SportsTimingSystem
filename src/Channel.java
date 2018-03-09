
public class Channel {
	private static ChronoTimer chrono;
	static boolean[] channel = new boolean[12];
	static String[] sensor = new String[12];
	public Channel(ChronoTimer ct) {
		chrono = ct;
		for(int i = 0; i < channel.length; i++) 
			channel[i] = false;
	}
	
	public void connectChannel(int n) {
		channel[n] = true;
	}
	public void disconnectChannel(int n) {
		channel[n] = false;
	}
	public void Toggle(int ch) {
		channel[ch] = !channel[ch];
		chrono.usePrinter("Channel " + ch + " toggle: " + (channel[ch]? "Enabled" : "Disabled"));
	}
	public boolean connectSensor(String sensortype, int chan) {
		if(sensor[chan] != null) {return false;/*can't connect because something is already there*/}
		else {
			sensor[chan] = sensortype;
			return true;
		}
	}
}
