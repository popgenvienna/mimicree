package mimicree.test.data;

import mimicree.data.Chromosome;
import mimicree.data.GenomicPosition;
import mimicree.data.haplotypes.SNP;
import java.util.*;
import java.util.logging.Logger;
import mimicree.data.haplotypes.SNPCollection;

public class SharedDataFactory {
	
	// Logger
	private static Logger logger;
	
	// SNPs
	public static SNP s2=new SNP(new GenomicPosition(Chromosome.getChromosome("2L"),2),'G','G','C');
	public static SNP s12=new SNP(new GenomicPosition(Chromosome.getChromosome("3R"),1113),'T','T','C');
	public static SNP s11=new SNP(new GenomicPosition(Chromosome.getChromosome("3R"),1112),'C','G','C');
	public static SNP s1=new SNP(new GenomicPosition(Chromosome.getChromosome("2L"),1),'A','A','T');
	public static SNP s3=new SNP(new GenomicPosition(Chromosome.getChromosome("2L"),3),'T','T','C');
	public static SNP s4=new SNP(new GenomicPosition(Chromosome.getChromosome("2R"),11),'T','C','T');
	public static SNP s5=new SNP(new GenomicPosition(Chromosome.getChromosome("2R"),12),'G','G','A');
	public static SNP s10=new SNP(new GenomicPosition(Chromosome.getChromosome("3R"),1111),'A','A','G');	
	public static SNP s9=new SNP(new GenomicPosition(Chromosome.getChromosome("3L"),113),'T','T','C');
	public static SNP s8=new SNP(new GenomicPosition(Chromosome.getChromosome("3L"),112),'C','G','C');
	public static SNP s7=new SNP(new GenomicPosition(Chromosome.getChromosome("3L"),111),'A','A','G');
	public static SNP s6=new SNP(new GenomicPosition(Chromosome.getChromosome("2R"),13),'T','G','C');
	
	public static SNP s13=new SNP(new GenomicPosition(Chromosome.getChromosome("X"),11111),'T','A','C');
	public static SNP s14=new SNP(new GenomicPosition(Chromosome.getChromosome("X"),11112),'T','T','G');
	public static SNP s15=new SNP(new GenomicPosition(Chromosome.getChromosome("X"),11113),'T','T','G'); 
	public static SNP s16=new SNP(new GenomicPosition(Chromosome.getChromosome("X"),11114),'T','T','C');
	public static SNP s17=new SNP(new GenomicPosition(Chromosome.getChromosome("X"),11115),'A','C','T');
	
	// Static constructor
	static 
	{
		// Create logger
		logger=java.util.logging.Logger.getLogger("Gowinda Logger");
		logger.setUseParentHandlers(false);
	}
	
	
	public static Logger getNullLogger()
	{
		return logger;
	}
	
	
	/**
	 * Get a collection of 12 SNPs on chromosomes 2L, 2R, 3L, 3R;
	 * 2L-2, 3R-1113, 3R-1112, 2L-1, 2L-3, 2R-11, 2R-12, 3R-1111, 3L-113, 3L-112, 3L-111, 2R-13
	 * @return
	 */
	public static ArrayList<SNP> getSNPCollection()
	{
		ArrayList<SNP> snps=new ArrayList<SNP>();
		snps.add(s2);
		snps.add(s12);
		snps.add(s11);
		snps.add(s1); 
		snps.add(s3); 
		snps.add(s4); 
		snps.add(s5);
		snps.add(s10);
		snps.add(s9);
		snps.add(s8);
		snps.add(s7);
		snps.add(s6);
		return snps;
	}
	
	/**
	 * A SNP collection
	 * 2L-1, 2L-2, 2L-3, 2R-11, 2R-12, 2R-13, 3L-111, 3L-112, 3L-113, 3R-1111, 3R-1112, 3R-1113 
	 * @return
	 */
	public static SNPCollection getSortedSNPCollection()
	{
		ArrayList<SNP> snps=getSNPCollection();
		Collections.sort(snps);
		return new SNPCollection(snps);
				
	}
	
	/**
	 * A SNPCollection
	 * 2L-1, 2L-2, 2L-3, 2R-11, 2R-12, 2R-13, 3L-111, 3L-112, 3L-113, 3R-1111, 3R-1112, 3R-1113, X-11111, X-11112, X-11114, X-11115, X-11116 
	 * @return
	 */
	public static SNPCollection getExtendedSNPCollection()
	{
		ArrayList<SNP> snps=getSNPCollection();
		snps.add(s13); snps.add(s14); snps.add(s15); snps.add(s16); snps.add(s17);
		Collections.sort(snps);
		return new SNPCollection(snps);
	}
	
	/**
	 * A SNP collection
	 * X-11111, X-11112, X-11114, X-11115, X-11116
	 * @return
	 */
	public static SNPCollection getXSNPCollection()
	{
		ArrayList<SNP> snps=new ArrayList<SNP>();
		snps.add(s13); snps.add(s14); snps.add(s15); snps.add(s16); snps.add(s17);
		Collections.sort(snps);
		return new SNPCollection(snps);
	}
	
	


}
