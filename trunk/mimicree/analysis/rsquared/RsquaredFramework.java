package mimicree.analysis.rsquared;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import mimicree.data.DiploidGenome;
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
		this.maxDistance=maxDistance;
		this.inputFile=inputFile;
		this.outputFile=outputFile;
		this.logger=logger;
	}
	
	public void run()
	{
		ArrayList<DiploidGenome> dipGenomes= new mimicree.io.DiploidGenomeReader(this.inputFile,"",this.logger).readGenomes();
		new RsquaredWriter(this.outputFile,this.logger).write(new RsquaredGenomeIterator(dipGenomes,this.maxDistance));
		
		
	}


}
