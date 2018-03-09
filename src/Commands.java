import java.time.LocalTime;

public interface Commands {
void POWER();
void EXIT();
void RESETn();
void TIMEset(LocalTime time);
void TOG(int channelNumber);
void CONN(String sensorType, int channel); // connect a sensor
void DISC(int channel2disconnect);
void EVENT(String eventName);
void NEWRUN();
void ENDRUN();
void PRINT(int runNumber); // 
void EXPORT(int runNumber); // 
void NUM(int bibNumber); // set next competitor to start
void CLR(int bibNumber); // remove the competitor from queue
void SWAP(); // swap the next 2 competitor from queue
void DNF();	// next racer in queue will not finish
void TRIG(int channelNumber);
void START();	// trigger channel 1
void FINISH();	// trigger channel 2
}
