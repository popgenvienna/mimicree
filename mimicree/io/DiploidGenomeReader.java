package mimicree.io;

import java.util.logging.Logger;
import mimicree.data.*;
import java.util.*;

public class DiploidGenomeReader {
	private final String haplotypeFile;
	private final String inversionFile;
	private java.util.logging.Logger logger;
	public DiploidGenomeReader(String haplotypeFile,String inversionFile, Logger logger)
	{
		this.haplotypeFile=haplotypeFile;
		this.inversionFile=inversionFile;
		this.logger=logger;
	}
	
	/**
	 * Read the diploid genomes as specified in the haplotypeFile and inversionFile
	 * @return
	 */
	public ArrayList<DiploidGenome> readDiploidGenomes()
	{
		ArrayList<HaploidGenome> hapGenomes=new HaploidGenomeReader(this.haplotypeFile,this.inversionFile,this.logger).readHaploidGenomes();
		ArrayList<DiploidGenome> dipGenomes=new ArrayList<DiploidGenome>();
		for(int i=0; i<hapGenomes.size(); i+=2)
		{
			dipGenomes.add(new DiploidGenome(hapGenomes.get(i),hapGenomes.get(i+1)));
		}
		this.logger.info("Finished creating diploid genomes");
		return dipGenomes;
	}
	
	

}