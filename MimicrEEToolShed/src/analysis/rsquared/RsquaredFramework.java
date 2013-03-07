package analysis.rsquared;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import mimicree.data.HaploidGenome;
import mimicree.io.misc.RsquaredWriter;
import mimicree.data.LD.*;


public class RsquaredFramework {
	private final String inputFile;
	private final String outputFile;
	private final int maxDistance;
	private java.util.logging.Logger logger;
	
	public RsquaredFramework(String inputFile, String outputFile, int maxDistance, java.util.logging.Logger logger)
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
		if(maxDistance <1 ) throw new IllegalArgumentException("Invalid max-distance between two SNPs; must be larger than 1 (eg 10.000)");
		this.maxDistance=maxDistance;
		this.inputFile=inputFile;
		this.outputFile=outputFile;
		this.logger=logger;
	}
	
	public void run()
	{
		ArrayList<HaploidGenome> genomes= new mimicree.io.HaploidGenomeReader(this.inputFile,"",this.logger).readGenomes();
		new RsquaredWriter(this.outputFile,this.logger).write(new RsquaredGenomeSlider(genomes,this.maxDistance));
		
		
	}


}
