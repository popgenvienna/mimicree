package mimicree.misc;

import java.util.*;
/**
 * Immutable container of the command line arguments 
 * @author robertkofler
 *
 */
public class CommandLineArguments { 
	private final String haplotypeFile;
	private final String inversionFile;
	private final String recombinationFile;
	private final String additiveFile;
	private final String epistasisFile;
	private final String outputDir;
	private final ArrayList<Integer> outputGenerations;
	private final int replicateRuns;
	private final boolean displayHelp;
	private final boolean detailedLog;
	
	public CommandLineArguments(String haplotypeFile, String inversionFile, String recombinationFile, String additiveFile, String epistasisFile,
			String outputDir, ArrayList<Integer> outputGenerations, int replicateRuns,boolean displayHelp,boolean detailedLog)
	{
		this.haplotypeFile=haplotypeFile;
		this.inversionFile=inversionFile;
		this.recombinationFile=recombinationFile;
		this.additiveFile=additiveFile;
		this.epistasisFile=epistasisFile;
		this.outputDir=outputDir;
		this.outputGenerations=new ArrayList<Integer>(outputGenerations);
		this.replicateRuns=replicateRuns;
		this.displayHelp=displayHelp;
		this.detailedLog=detailedLog;
	}
	
	public String haplotypeFile()
	{
		return this.haplotypeFile;
	}
	public String inversionFile()
	{
		return this.inversionFile;
	}
	public String recombinationFile()
	{
		return this.recombinationFile;
	}
	public String additiveFile()
	{
		return this.additiveFile;
	}
	public String epistasisFile()
	{
		return this.epistasisFile;
	}
	public String outputDir()
	{
		return this.outputDir;
	}
	public ArrayList<Integer> outputGenerations()
	{
		return new ArrayList<Integer>(this.outputGenerations);
	}
	public int replicateRuns()
	{
		return this.replicateRuns;
	}
	public boolean displayHelp()
	{
		return this.displayHelp;
	}
	public boolean detailedLog()
	{
		return this.detailedLog;
	}
	
	

}
