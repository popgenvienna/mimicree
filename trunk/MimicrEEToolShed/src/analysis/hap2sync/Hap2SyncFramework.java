package analysis.hap2sync;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import mimicree.data.statistic.*;
public class Hap2SyncFramework {
	private final ArrayList<String> inputFiles;
	private final String outputFile;
	private java.util.logging.Logger logger;
	
	public Hap2SyncFramework(ArrayList<String> inputFiles, String outputFile, java.util.logging.Logger logger)
	{
		if(inputFiles.size()<1) throw new IllegalArgumentException("At least one haplotype file needs to be provided");
		for(String file: inputFiles)
		{
			if(! new File(file).exists()) throw new IllegalArgumentException("Haplotype file does not exist "+file);
		}
		
		try
		{
        	
			new FileWriter(outputFile);
        }
        catch(IOException e)
        {
        	throw new IllegalArgumentException("Can not create output file:" +outputFile);
        }
		
		this.inputFiles=inputFiles;
		this.outputFile=outputFile;
		this.logger=logger;
	}
	
	public void run()
	{
		ArrayList<PopulationAlleleCount> pac = new mimicree.io.haplotypes.PopulationAlleleCountReader(inputFiles, logger).readPopulations();
		new mimicree.io.misc.SyncWriter(this.outputFile,this.logger).write(pac);
	}


}
