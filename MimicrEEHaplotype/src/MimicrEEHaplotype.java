/**
 * 
 */



import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.Date;
import mimcore.misc.MimicreeThreadPool;

/**
 * @author robertkofler
 *
 */
public class MimicrEEHaplotype {

	/**
	 * @param args
	 * 	 */
	public static void main(String[] args) 
	{

		// Parse command lines to determine the analysis mode and the LOG
		LinkedList<String> rawarguments=new LinkedList<String>(Arrays.asList(args));
        simulate.SimulationCommandLineParser.runMimicreeSimulations(rawarguments);
	}



	
	

}
