package simulate;

import mimcore.misc.MimicreeThreadPool;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SimulationCommandLineParserLimits {
	
	/**
	 * Parse the command line arguments and return the results
	 * @param args the command line arguments
	 * @return
	 */
	public static void runMimicreeSimulations(LinkedList<String> args)
	{

		String haplotypeFile="";
		String recombinationFile="";
		String outputFile="";

		double selectionCoefficient=0;
		double heteroygousEffect=0;
		int selectedSNPs=0;
		double maxFrequency=0;

		String chromosomeDefinition="";
		int replicateRuns=1;
		int maxGenerations=50000;
		int threadCount=1;
		boolean detailedLog=false;

	
		
        while(args.size() > 0)
        {
            String cu=args.remove(0);

			if(cu.equals("--detailed-log"))
			{
				detailedLog=true;
			}

			else if(cu.equals("--version"))
			{
				printVersion();
			}
			else if(cu.equals("--threads"))
			{

				threadCount=Integer.parseInt(args.removeFirst());
			}
            else if(cu.equals("--haplotypes-g0"))
            {
                haplotypeFile = args.remove(0);
            }
            else if(cu.equals("--recombination-rate"))
            {
            	recombinationFile=args.remove(0);
            }
            else if(cu.equals("--chromosome-definition"))
            {
            	chromosomeDefinition=args.remove(0);
            }
            else if(cu.equals("--replicate-runs"))
            {
            	replicateRuns=Integer.parseInt(args.remove(0));
            }
            else if(cu.equals("--output-file"))
            {
            	outputFile=args.remove(0);
            }
            else if(cu.equals("--help"))
            {
            	printHelpMessage();
            }
			else if(cu.equals("-s"))
			{
				selectionCoefficient=Double.parseDouble(args.remove(0));
			}
			else if(cu.equals("-h"))
			{
				heteroygousEffect=Double.parseDouble(args.remove(0));
			}
			else if(cu.equals("--number-selected")){
				selectedSNPs=Integer.parseInt(args.remove(0));
			}
			else if(cu.equals("--max-frequency"))
			{
				maxFrequency=Double.parseDouble(args.remove(0));
			}
			else if(cu.equals("--max-generations"))
			{
				maxGenerations=Integer.parseInt(args.remove(0));
			}
            else
            {
                throw new IllegalArgumentException("Do not recognize command line option "+cu);
            }
        }

		MimicreeThreadPool.setThreads(threadCount);

		// Create a logger to System.err
		Logger logger= Logger.getLogger("Mimicree Logger");
		java.util.logging.ConsoleHandler mimhandler =new java.util.logging.ConsoleHandler();
		mimhandler.setLevel(Level.INFO);
		if(detailedLog)mimhandler.setLevel(Level.FINEST);
		mimhandler.setFormatter(new mimcore.misc.MimicreeLogFormatter());
		logger.addHandler(mimhandler);
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.ALL);


        SimulationFrameworkLimits mimframe= new SimulationFrameworkLimits(haplotypeFile,recombinationFile,chromosomeDefinition,  selectionCoefficient,
				heteroygousEffect,maxGenerations,maxFrequency,selectedSNPs, outputFile,replicateRuns,logger);
        
        mimframe.run();

		MimicreeThreadPool.getExector().shutdown();
		logger.info("Thank you for using MimicrEELimits");
	}
	
	
	public static void printHelpMessage()
	{
		StringBuilder sb=new StringBuilder();
		sb.append("--haplotypes-g0				the haplotype file\n");
		sb.append("--recombination-rate			the recombination rate for windows of fixed size\n");
		sb.append("--chromosome-definition			which chromosomes parts constitute a chromosome\n");
		sb.append("--replicate-runs			how often should the simulation be repeated\n");
		sb.append("--output-file				the output file\n");
		sb.append("--number-selected		the number of selected SNPs\n");
		sb.append("-s						the selection coefficient of the selected SNPs\n");
		sb.append("-h						the heterozygous effect of the selected SNPs\n");
		sb.append("--max-frequency			the maximal frequency of the selected SNPs\n");
		sb.append("--max-generations		the maximal number of generations to run the simulation before aborting it and choosing different SNPs\n");
		sb.append("--detailed-log				print detailed log messages\n");
		sb.append("--threads				the number of threads to use\n");
		sb.append("--help					print the help\n");
		System.out.print(sb.toString());
		System.exit(1);
	}

	public static void printVersion()
	{
		String version="MimicrEELimits version 1.01; build "+String.format("%tc",new Date(System.currentTimeMillis()));
		System.out.println(version);
		System.exit(1);
	}


}
