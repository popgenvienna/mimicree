package simulate;

import mimcore.misc.MimicreeThreadPool;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SimulationCommandLineParser {
	
	/**
	 * Parse the command line arguments and return the results
	 * @param args the command line arguments
	 * @return
	 */
	public static void runMimicreeSimulations(LinkedList<String> args)
	{

		String haplotypeFile="";
		String recombinationFile="";
		String additiveFile="";
		String epistasisFile="";
		String outputFile="";
		OutputFileType outputFileType=OutputFileType.Sum;
		String outputGenRaw="";
		String chromosomeDefinition="";
		int replicateRuns=1;
		boolean detailedLog=false;
		int threadCount=1;

	
		
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
			else if(cu.equals("--output-format"))
			{
				outputFileType=getOutputFileType(args.remove(0));
			}
            else if(cu.equals("--additive"))
            {
            	additiveFile=args.remove(0);
            }
            else if(cu.equals("--epistasis"))
            {
            	epistasisFile=args.remove(0);
            }
            else if(cu.equals("--chromosome-definition"))
            {
            	chromosomeDefinition=args.remove(0);
            }
            else if(cu.equals("--output-mode"))
            {
            	outputGenRaw=args.remove(0);
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

    
        // Parse the string with the generations
        SimulationMode simMode = parseOutputGenerations(outputGenRaw);

        SimulationFrameworkSummary mimframe= new SimulationFrameworkSummary(haplotypeFile,recombinationFile,chromosomeDefinition,
        		additiveFile,epistasisFile,outputFile,outputFileType,simMode,replicateRuns,logger);
        
        mimframe.run();

		MimicreeThreadPool.getExector().shutdown();
		logger.info("Thank you for using MimicrEESummary");
	}
	
	
	public static void printHelpMessage()
	{
		StringBuilder sb=new StringBuilder();
		sb.append("--haplotypes-g0				the haplotype file\n");
		sb.append("--recombination-rate			the recombination rate for windows of fixed size\n");
		sb.append("--additive				the additive fitness effect of SNPs\n");
		sb.append("--epistasis				the epistatic fitness effect of SNPs\n");	
		sb.append("--chromosome-definition			which chromosomes parts constitute a chromosome\n");
		sb.append("--output-mode				a coma separated list of generations to output\n");
		sb.append("--replicate-runs			how often should the simulation be repeated\n");
		sb.append("--output-file				the output file\n");
		sb.append("--output-format				the output format, either sync or sum (default=sum)\n");
		sb.append("--detailed-log				print detailed log messages\n");
		sb.append("--threads				the number of threads to use\n");
		sb.append("--help					print the help\n");
		System.out.print(sb.toString());
		System.exit(1);
	}

	public static void printVersion()
	{
		String version="MimicrEESummary version 1.01; build "+String.format("%tc",new Date(System.currentTimeMillis()));
		System.out.println(version);
		System.exit(1);
	}

	public static OutputFileType getOutputFileType(String arg)
	{
		arg=arg.toLowerCase();
		if(arg.equals("sum"))
		{
			  return OutputFileType.Sum;
		}
		else if(arg.equals("sync"))
		{
			return OutputFileType.Sync;
		}
		else
		{
			throw new IllegalArgumentException("File type must be either sync or sum; Found "+arg);
		}
	}

	
	public static SimulationMode parseOutputGenerations(String outputGenerationsRaw)
	{
		// Parse a String consistent of a comma-separated list of numbers, to a array of integers
		SimulationMode simMode;
			String [] tmp;
			if(outputGenerationsRaw.contains(","))
			{
				tmp=outputGenerationsRaw.split(",");
			}
			else
			{
				tmp=new String[1];
				tmp[0]=outputGenerationsRaw;
			}
			// Convert everything to int
			ArrayList<Integer> ti=new ArrayList<Integer>();
			for(String s :tmp)
			{
				ti.add(Integer.parseInt(s));
			}
			simMode=SimulationMode.Timestamp;
			simMode.setTimestamps(ti);
		return simMode;
	}
}
