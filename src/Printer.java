import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**@author BS
 * @see- How to use printer:
 * You need to construct a printer before printing (this opens the file). When 
 * printing you must specify a whether or not you want the time stamp. When you 
 * are done printing you must shutdown the printer.
 * @exceptions You may need to add "throws IOexception" to methods inorder to 
 * use the printer
 */
public class Printer {
	private PrintWriter printer;

	/**Constructor for the printer. This method sets up everything we need for printing a receipt.txt file,
	 * if the file exists it appends it instead of overwritting
	 * @throws IOException
	 */
	public Printer() throws IOException {
		printer = new PrintWriter(new FileOutputStream("Race_Printout.txt", true));
	}
	/**
	 * This clears all existing data in the prinout file
	 * by creating a new file of the same name.
	 * @throws IOException
	 */
	public void PrinterRest() throws IOException{
		printer = new PrintWriter("Race_Printout.txt");
	}
	/**
	 * 
	 * @param time comes in via the correct format
	 * @param message - maybe the racer's name?
	 * @param includeTime self explanatory
	 */
	public void printThis(String time, String message, boolean includeTime) {
		System.out.println(time + message);
		if(includeTime) printer.println(time + message);
		else printer.println(time  + message);
		printer.flush();
	}
	public void shutDownPrinter() {
		printer.close();
	}

}