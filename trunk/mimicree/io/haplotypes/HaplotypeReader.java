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
	
	public void getHaplotypes()
	{
		this.logger.info("Starting reading haplotypes from file "+this.haplotypeFile);
		ArrayList<SNP> snps= new HaplotypeSNPReader(this.haplotypeFile).getSNPs();
		
	}

}
