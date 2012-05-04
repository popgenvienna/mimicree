package mimicree.io;

import java.util.*;

import mimicree.data.haplotypes.*;
import mimicree.io.haplotypes.HaplotypeReader;

public class HaploidGenomeReader {
	
	private final String haplotypeFile;
	private final String inversionFile;
	private java.util.logging.Logger logger;
	
	public HaploidGenomeReader(String haplotypeFile, String inversionFile, java.util.logging.Logger logger)
	{
		this.haplotypeFile=haplotypeFile;
		this.inversionFile=inversionFile;
		this.logger=logger;
	}
	
	
	public ArrayList<HaploidGenome> read()
	{
		ArrayList<Haplotype> haps= new mimicree.io.haplotypes.HaplotypeReader(this.haplotypeFile,this.logger).getHaplotypes();

	}

}
