package mimicree.io;


import java.util.logging.Logger;
import mimicree.data.*;
import java.util.*;

public class DiploidGenomeReaderSubfilterDecorator implements IDiploidGenomeReader {
	private final IDiploidGenomeReader reader;
	private final ArrayList<GenomicPosition> posSet;
	private java.util.logging.Logger logger;
	public DiploidGenomeReaderSubfilterDecorator(IDiploidGenomeReader reader, ArrayList<GenomicPosition> toFilter, Logger logger)
	{
		this.reader=reader;
		this.posSet=toFilter;
		this.logger=logger;
	}	
	
	/**
	 * Read the diploid genomes as specified in the haplotypeFile and inversionFile
	 * @return
	 */
	public ArrayList<DiploidGenome> readGenomes()
	{
		ArrayList<DiploidGenome> toFilter=this.reader.readGenomes();
		this.logger.info("Filtering haplotypes for selected SNPs");
		ArrayList<DiploidGenome> filtered=new ArrayList<DiploidGenome>();
		for(DiploidGenome dipgen: toFilter)
		{
			HaploidGenome g1=new HaploidGenome(dipgen.getHaplotypeA().getSNPHaplotype().getSubHaplotype(this.posSet),dipgen.getHaplotypeA().getInversionHaplotype());
			HaploidGenome g2=new HaploidGenome(dipgen.getHaplotypeB().getSNPHaplotype().getSubHaplotype(this.posSet),dipgen.getHaplotypeB().getInversionHaplotype());
			filtered.add(new DiploidGenome(g1,g2));
		}
		this.logger.info("Finished filtering haplotypes");
		return filtered;
	}
	
	

}
