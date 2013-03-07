/**
 * 
 */
package analysis;

import mimicree.misc.*;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.Date;

/**
 * @author robertkofler
 *
 */
public class MimicrEE {

	/**
	 * @param args
	 * 	 */
	public static void main(String[] args) 
	{

		// Parse command lines to determine the analysis mode and the LOG
		LinkedList<String> rawarguments=new LinkedList<String>(Arrays.asList(args));
		boolean detailedLog=false;
		String mode="";
		LinkedList<String> arguments=new LinkedList<String>();


		while(rawarguments.size()>0)
		{

			String cu=rawarguments.removeFirst();
            if(cu.equals("--detailed-log"))
            {
            	detailedLog=true;
            }
            else if(cu.equals("--mode"))
            {

                mode = rawarguments.removeFirst();
            }
			else if(cu.equals("--version"))
			{
				printVersion();
			}
            else if(cu.equals("--threads"))
            {		

            	int threadCount=Integer.parseInt(rawarguments.removeFirst());
            	MimicreeThreadPool.setThreads(threadCount);
            }
			else
			{
				       arguments.addLast(cu);
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
        else if(mode.equals("hap2sum"))
        {
        	mimicree.analysis.hap2sum.Hap2SumParser.parseCommandline(logger, arguments);
        }
        else if(mode.equals("rsquaredslide"))
        {
        	mimicree.analysis.rsquared.RsquaredParser.parseCommandline(logger, arguments);
        }
        else if(mode.equals("rsquaredrandom"))
        {
        	mimicree.analysis.rsquaredrandom.RsquaredRandomParser.parseCommandline(logger,arguments);
        }
        else if(mode.equals("fsc2hap"))
        {
            mimicree.analysis.fcs2hap.Fcs2HapParser.parseCommandline(logger,arguments);
        }
        else
        {
        	throw new IllegalArgumentException("Do not recognise analysis mode "+mode);
        }
        
        MimicreeThreadPool.getExector().shutdown();
        
		logger.info("Thank you for using MimicrEE");
	}

	public static void printVersion()
	{
		String version="MimicrEE version 1.12; build "+String.format("%tc",new Date(System.currentTimeMillis()));
		System.out.println(version);
		System.exit(0);
	}

	
	
	public static String getGeneralHelpmessage()
	{
		
		StringBuilder sb=new StringBuilder();
		sb.append("--mode					the analysis mode of operation; see manual for supported modes\n");
		sb.append("--detailed-log				print detailed log messages\n");
		sb.append("--threads				the number of threads to use\n");
		sb.append("--help					print the help\n"); 
		return sb.toString();

	}

}
