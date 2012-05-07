package mimicree.io.inversion;


import java.util.*;
import java.util.logging.Logger;

import mimicree.data.*;

public class InversionReader {
	private final int  haplotpyeCount;
	private final String inversionFile;
	private Logger logger;
	public InversionReader(String inversionFile,int haplotypeCount, Logger logger)
	{
		this.haplotpyeCount=haplotypeCount;
		this.inversionFile=inversionFile;
		this.logger=logger;
	}
	
	
	public ArrayList<InversionHaplotype> getInversions()
	{
		this.logger.info("Start reading the inversions");
		this.logger.fine("Start reading inversion definitions");
		ArrayList<Inversion> inversions=new InversionDefinitionReader(inversionFile).setupInversionDefinitions();
		this.logger.fine("Finished reading inversion definitions; Found " + inversions.size()+ " inversions");
		this.logger.fine("Start validating inversions");
		boolean overlapping=new InversionValidator(inversions).areOverlapping();
		if(overlapping) throw new IllegalArgumentException("Overlapping inversions are not allowed");
		this.logger.fine("Finished validating inversions. All insertions are valid");
		this.logger.fine("Start reading the inversion-haplotypes");
		ArrayList<InversionHaplotype> invHap=new InversionHaplotypeReader(this.inversionFile,this.haplotpyeCount).getInversionHaplotypes();
		this.logger.fine("Finished reading inversion-haplotypes. Read " + invHap.size() + " haplotypes");
		this.logger.info("Finished reading Inversions; Found " + inversions.size() + " distinct inversions and read " + invHap.size() + " inversion-haplotypes");
		return invHap;
	}
}

