package analysis.rsquaredrandom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import mimcore.data.HaploidGenome;
import mimcore.io.misc.RsquaredWriter;
import mimcore.data.LD.*;


public class RsquaredRandomFramework {
	private final String inputFile;
	private final String outputFile;
	private final int randomSamples;
	private final boolean intraChromosomal;
	private java.util.logging.Logger logger;
	
	public RsquaredRandomFramework(String inputFile, String outputFile, int randomSamples,boolean intraChromosomal, java.util.logging.Logger logger)
	{
	

		if(! new File(inputFile).exists()) throw new IllegalArgumentException("Haplotype file does not exist "+inputFile);
		try
		{
			new FileWriter(outputFile);
        }
        catch(IOException e)
        {
        	throw new IllegalArgumentException("Can not create output file:" +outputFile);
        }
		if(randomSamples<1) throw new IllegalArgumentException("Number of samples needs to be larger than zero");
		this.randomSamples=randomSamples;
		this.intraChromosomal=intraChromosomal;
		this.inputFile=inputFile;
		this.outputFile=outputFile;
		this.logger=logger;
	}
	
	public void run()
	{
		ArrayList<HaploidGenome> genomes= new mimcore.io.HaploidGenomeReader(this.inputFile,"",this.logger).readGenomes();
		new RsquaredWriter(this.outputFile,this.logger).write(new RsquaredRandomGenomeIterator(genomes,this.randomSamples,this.intraChromosomal));
		
		
	}


}
