package mimicree.io.haplotypes;

import java.util.*;
import mimicree.data.haplotypes.*;

/**
 * Read the content of a haplotype file.
 * Return a collection of diploid specimen
 * @author robertkofler
 *
 */
public class HaplotypeReader {
	private final String haplotypeFile;
	private java.util.logging.Logger logger;
	
	public HaplotypeReader(String haplotypeFile, java.util.logging.Logger logger)
	{
		this.logger=logger;
		this.haplotypeFile=haplotypeFile;
	}
	
	public ArrayList<HaplotypeSNP> getHaplotypes()
	{
		this.logger.info("Starting reading haplotypes from file "+this.haplotypeFile);
		this.logger.fine("Start reading the SNPs");
		SNPCollection snpcol= new HaplotypeSNPReader(this.haplotypeFile).getSNPcollection();
		this.logger.fine("Finished reading SNPs; SNPs read "+ snpcol.size());
		this.logger.fine("Start reading haplotype information");
		ArrayList<BitArray> haps=new HaplotypeHaplotypeReader(this.haplotypeFile,snpcol).getHaplotypes();
		this.logger.fine("Finished reading haplotype information; Haplotypes read " + haps.size());
		
		ArrayList<HaplotypeSNP> haplotypes=new ArrayList<HaplotypeSNP>();
		for (BitArray ba : haps)
		{
			haplotypes.add(new HaplotypeSNP(ba,snpcol));
		}
		this.logger.info("Finished reading haplotypes; Read "+snpcol.size() + " SNPs and " + haplotypes.size() + " haplotypes");
		
		return haplotypes;
	}

}
