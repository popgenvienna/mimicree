/**
 * 
 */

import mimcore.misc.*;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.Date;

/**
 * @author robertkofler
 *
 */
public class MimicrEEToolShed {

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
            if(cu.equals("--mode"))
            {

                mode = rawarguments.removeFirst();
            }
			else if(cu.equals("--version"))
			{
				printVersion();
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
        mimhandler.setFormatter(new mimcore.misc.MimicreeLogFormatter());
        logger.addHandler(mimhandler);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
       
        // Start the appropriate sub-application of mimicree
        mode=mode.toLowerCase();

        if(mode.equals("measure-fitness"))
        {
        	analysis.measurefitness.AnalyseFitnessParser.parseArguments(logger, arguments);
        }
        else if(mode.equals("hap2sync"))
        {
        	analysis.hap2sync.Hap2SyncParser.parseCommandline(logger, arguments);
        }
        else if(mode.equals("hap2sum"))
        {
        	analysis.hap2sum.Hap2SumParser.parseCommandline(logger, arguments);
        }
        else if(mode.equals("rsquaredslide"))
        {
        	analysis.rsquared.RsquaredParser.parseCommandline(logger, arguments);
        }
        else if(mode.equals("rsquaredrandom"))
        {
        	analysis.rsquaredrandom.RsquaredRandomParser.parseCommandline(logger,arguments);
        }
        else if(mode.equals("fsc2hap"))
        {
            analysis.fcs2hap.Fcs2HapParser.parseCommandline(logger,arguments);
        }
        else
        {
        	throw new IllegalArgumentException("Do not recognise analysis mode "+mode);
        }

        
		logger.info("Thank you for using MimicrEEToolShed");
	}

	public static void printVersion()
	{
		String version="MimicrEEToolShed version 1.02; build "+String.format("%tc",new Date(System.currentTimeMillis()));
		System.out.println(version);
		System.exit(0);
	}

}
