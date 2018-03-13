public interface CommandsInterface {
	void CLR(int bibNumber); // remove the competitor from queue
	void CONN(String sensorType, int channel); // connect a sensor
	void DISC(int channel2disconnect);
	void DNF();	// next racer in queue will not finish
	void ENDRUN();
	void EVENT(String eventName);
	void EXIT();
	void EXPORT(int runNumber); // 
	void FINISH();	// trigger channel 2
	void NEWRUN();	
	void NUM(String bibNumber); // set next competitor to start
	void POWER();
	void PRINT(); //bjf: I had to remove the argument for this method. See my note in CT class 
	void RESET();
	void TIME(String time);
	void TOG(int channelNumber);
	void TRIG(int channelNumber);	
	void START();	// trigger channel 1
	void SWAP(); // swap the next 2 competitor from queue
}
