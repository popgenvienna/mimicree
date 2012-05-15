/**
 * 
 */
package mimicree;

import mimicree.misc.*;


import java.util.logging.Level;

/**
 * @author robertkofler
 *
 */
public class MimicrEE {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CommandLineArguments arguments= CommandLineParser.getCommandLineArguments(args);
        if(arguments.displayHelp())
        {
            System.out.print(mimicree.misc.CommandLineParser.helpMessage());
            System.exit(1); // 1 means that programm exited correctly
        }
      
        // Create a logger to System.err
        java.util.logging.Logger logger=java.util.logging.Logger.getLogger("Mimicree Logger");
        java.util.logging.ConsoleHandler mimhandler =new java.util.logging.ConsoleHandler();
        mimhandler.setLevel(Level.INFO);
        if(arguments.detailedLog())mimhandler.setLevel(Level.FINEST);
        mimhandler.setFormatter(new mimicree.misc.MimicreeLogFormatter());
        logger.addHandler(mimhandler);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        
        mimicree.MimicreeFramework mimframe= new mimicree.MimicreeFramework(arguments.haplotypeFile(),arguments.inversionFile(),arguments.recombinationFile(),arguments.chromosomeDefintion(),
        		arguments.additiveFile(),arguments.epistasisFile(),arguments.outputDir(),arguments.outputGenerations(),arguments.replicateRuns(),arguments.threads(),logger);
        
        mimframe.run();
        
	}

}
