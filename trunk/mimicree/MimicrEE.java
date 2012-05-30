/**
 * 
 */
package mimicree;

import mimicree.misc.*;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;

/**
 * @author robertkofler
 *
 */
public class MimicrEE {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{

		// Parse command lines to determine the analysis mode and the LOG
		LinkedList<String> arguments=new LinkedList<String>(Arrays.asList(args));
		boolean detailedLog=false;
		String mode="";	
		
		for(int i=0; i<arguments.size(); i++)
		{
			String cu=arguments.get(i);
            if(cu.equals("--detailed-log"))
            {
            	arguments.remove(i);
            	detailedLog=true;
            }
            else if(cu.equals("--mode"))
            {
            	arguments.remove(i);
                mode = arguments.remove(i);
            }
            else if(cu.equals("--threads"))
            {		
            	arguments.remove(i);
            	int threadCount=Integer.parseInt(arguments.remove(i));
            	MimicreeThreadPool.setThreads(threadCount);
            }
            
		}
		
        // Create a logger to System.err
        java.util.logging.Logger logger=java.util.logging.Logger.getLogger("Mimicree Logger");
        java.util.logging.ConsoleHandler mimhandler =new java.util.logging.ConsoleHandler();
        mimhandler.setLevel(Level.INFO);
        if(detailedLog)mimhandler.setLevel(Level.FINEST);
        mimhandler.setFormatter(new mimicree.misc.MimicreeLogFormatter());
        logger.addHandler(mimhandler);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
       
        // Start the appropriate sub-application of mimicree
        mode=mode.toLowerCase();
        if(mode.equals("simulate"))
        {
        	mimicree.simulate.SimulationCommandLineParser.runMimicreeSimulations(logger, arguments);
        }
        else if(mode.equals("measure-fitness"))
        {
        	mimicree.analysis.measurefitness.AnalyseFitnessParser.parseArguments(logger, arguments);
        }
        else if(mode.equals("hap2sync"))
        {
        	mimicree.analysis.hap2sync.Hap2SyncParser.parseCommandline(logger, arguments);
        }
        else
        {
        	throw new IllegalArgumentException("Do not recognise analysis mode "+mode);
        }
        
        MimicreeThreadPool.getExector().shutdown();
        
		logger.info("Thank you for using MimicrEE");
	}
	
	
	public static String getGeneralHelpmessage()
	{
		
		StringBuilder sb=new StringBuilder();
		sb.append("--mode						the analysis mode of operation; see manual for supported modes\n");
		sb.append("--detailed-log					print detailed log messages\n");
		sb.append("--threads					the number of threads to use\n");
		sb.append("--help						print the help\n"); 
		return sb.toString();

	}

}
