import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Simulator{
	ChronoTimer ct;
	Channel chan;
	boolean simulatorOn;

	/**
	 * Constructor
	 * @throws IOException 
	 */
	public Simulator() throws IOException{

		simulatorOn = true;
		ct = new ChronoTimer();
		chan = new Channel(ct);
		Scanner s = new Scanner(System.in);
		System.out.println("Read file from console or file (c/f): ");
		String input = s.nextLine();
		if(input.equalsIgnoreCase("f")) {
			System.out.print("Enter the file name: ");
			String fileName = s.nextLine();
			try(Scanner sc = new Scanner (new File(fileName))){
				while(sc.hasNextLine() && simulatorOn){
					generalParser(sc.nextLine().trim().split("\\s+"));
				}
			}
			catch(FileNotFoundException e){
				System.out.println("File not found!");
			}
		}
		else if(input.equalsIgnoreCase("c")){
			String uInput="";
			System.out.println("ChronoTimer simulator: powering chronoTimer on.");
			ct.power();
			while(!uInput.equals("Q")) {
				System.out.println("Enter Full command to be sent to parser (WITH SPACES) Q to quit:");
				String time = Time.time2formattedString(Time.getLocalTime())+ " ";
				uInput =  s.nextLine();
				System.out.println("Performing action on:"+ (time+uInput));
				if(!uInput.equals("Q")) {
					generalParser((time+uInput).trim().split("\\s+"));
				}
			}			
		}
		s.close();
	}
	/**
	 * parses input text file and executes commands
	 * @param tokens
	 * @throws IOException
	 */
	private void generalParser(String[] tokens) throws IOException {
		switch(tokens[1]){
		case "POWER":
			ct.power();
			break;
		case "NEWRUN":
			ct.startNewRun();
			break;
		case "TOG" :
			chan.Toggle(Integer.parseInt(tokens[2]));
			break;
		case "TRIG":
			String[] time = tokens[0].split(":|\\.");
			ct.trigger(Time.string2LocalTime(time),tokens[2]);
			break;
		case "PRINT":
			ct.printResults();
			break;
		case "ENDRUN":
			ct.endRun();
			break;
		case "TIME":
			//tokens[2] to set time 
			break;
		case "NUM":
			ct.setBib(tokens[2]);
			break;
		case "EVENT":
			ct.initiateNewEvent();
			break;
		case "EXIT":
			simulatorOn = false;
			break;
		case "CONN" :
			chan.connectSensor(tokens[2], Integer.parseInt(tokens[3]));
		}	

	}
	public static void main(String[] s) throws IOException {
		new Simulator();
	}
}