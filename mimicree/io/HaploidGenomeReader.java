package mimicree.io;

import java.io.File;
import java.util.*;

import mimicree.data.*;
import mimicree.data.haplotypes.*;
import mimicree.io.haplotypes.HaplotypeReader;
import mimicree.io.inversion.InversionReader;

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
	
	
	public ArrayList<HaploidGenome> readGenomes()
	{
		ArrayList<Haplotype> haps= new HaplotypeReader(this.haplotypeFile,this.logger).getHaplotypes();
		ArrayList<InversionHaplotype> invs;
		if(new File(inversionFile).exists())
		{
			this.logger.info("Detected an existing inversion file");
			invs=new mimicree.io.inversion.InversionReader(inversionFile, haps.size(), logger).getInversions();
		}
		else
		{
			this.logger.info("Did not detect an existing inversion file; Loading default (=no inversions)");
			invs=InversionReader.getEmptyInversions(haps.size());
		}
		assert(haps.size()==invs.size());
		
		ArrayList<HaploidGenome> toret=new ArrayList<HaploidGenome>();
		for(int i =0; i<haps.size();  i++)
		{
			toret.add(new HaploidGenome(haps.get(i),invs.get(0)));
		}
		
		
		this.logger.info("Finished creating haploid genomes consisting of SNPs and inversions");
		return toret;
		
	}

}
