import simulate.SimulationCommandLineParserLimits;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author robertkofler
 *
 */
public class MimicrEELimitsOfSelection {

	/**
	 * @param args
	 * 	 */
	public static void main(String[] args) 
	{

		// Parse command lines to determine the analysis mode and the LOG
		LinkedList<String> rawarguments=new LinkedList<String>(Arrays.asList(args));
        SimulationCommandLineParserLimits.runMimicreeSimulations(rawarguments);
	}



	
	

}
