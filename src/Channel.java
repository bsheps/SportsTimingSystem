
public class Channel {
	private boolean[] channel = new boolean[12];
	private String[] sensor = new String[12];

	public Channel() {
		for (int i = 0; i < channel.length; i++)
			channel[i] = false;
	}

	/**
	 * @param ch
	 *            - Channel number to toggle
	 * @return boolean - new channel state
	 */
	public boolean Toggle(int ch) {
		channel[ch] = !channel[ch];
		// print.printThis("Channel "+ch+" is " + (channel[ch]? "Enabled": "Disabled"));
		return channel[ch];
	}

	public boolean isChannelEnabled(int channelNumber) {
		return channel[channelNumber];
	}

	public boolean connectSensor(String sensortype, int chan) {
		if (sensor[chan] != null) {
			return false;
			/* can't connect because something is already there */} else {
			sensor[chan] = sensortype;
			return true;
		}
	}
}
