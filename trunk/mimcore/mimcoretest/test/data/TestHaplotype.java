package test.data;

import static org.junit.Assert.*;
import mimcore.data.data.Chromosome;
import mimcore.data.data.GenomicPosition;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestHaplotype {
	
	public static SNPCollection sc1;
	
	
	@BeforeClass
	public static void ini()
	{
		
		
	}
	

	@Test
	public void test_minorAllele() {
		SNPCollection s=SharedDataFactory.getSortedSNPCollection();
		BitArrayBuilder b=new BitArrayBuilder(12);
		Haplotype h=new Haplotype(b.getBitArray(),s);
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2L"),2)),'C');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3R"),1113)),'C');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3R"),1112)),'C');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2L"),1)),'T');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2L"),3)),'C');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2R"),11)),'T');		
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2R"),12)),'A');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3R"),1111)),'G');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3L"),113)),'C');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3L"),112)),'C');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3L"),111)),'G');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2R"),13)),'C');
	}
	
	@Test
	public void test_majorAllele() {
		SNPCollection s=SharedDataFactory.getSortedSNPCollection();
		BitArrayBuilder b=new BitArrayBuilder(12);
		b.setBit(0); b.setBit(1); b.setBit(2); b.setBit(3); b.setBit(4); b.setBit(5); b.setBit(6); b.setBit(7); b.setBit(8); b.setBit(9); b.setBit(10);
		b.setBit(11);
		Haplotype h=new Haplotype(b.getBitArray(),s);
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2L"),2)),'G');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3R"),1113)),'T');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3R"),1112)),'G');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2L"),1)),'A');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2L"),3)),'T');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2R"),11)),'C');		
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2R"),12)),'G');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3R"),1111)),'A');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3L"),113)),'T');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3L"),112)),'G');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3L"),111)),'A');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2R"),13)),'G');
	}
	
	@Test
	public void test_mixture() {
		SNPCollection s=SharedDataFactory.getSortedSNPCollection();
		BitArrayBuilder b=new BitArrayBuilder(12);
		b.setBit(0); b.setBit(10); b.setBit(11); 
		Haplotype h=new Haplotype(b.getBitArray(),s);
	
		// major allele, the first and the last two
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3R"),1113)),'T');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3R"),1112)),'G');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2L"),1)),'A');
		
		// minor allele
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2L"),2)),'C');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2L"),3)),'C');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2R"),11)),'T');		
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2R"),12)),'A');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3R"),1111)),'G');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3L"),113)),'C');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3L"),112)),'C');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("3L"),111)),'G');
		assertEquals(h.getAllele(new GenomicPosition(Chromosome.getChromosome("2R"),13)),'C');
	}
	
	
	@Test
	public void test_hasAncestral()
	{
		SNPCollection s=SharedDataFactory.getSortedSNPCollection();
		BitArrayBuilder b=new BitArrayBuilder(12);
		b.setBit(0); b.setBit(10); b.setBit(11); 
		Haplotype h=new Haplotype(b.getBitArray(),s);
		
		assertTrue(h.hasAncestral(0));
		assertFalse(h.hasAncestral(1));
		assertFalse(h.hasAncestral(2));
		assertFalse(h.hasAncestral(3));
		assertFalse(h.hasAncestral(4));
		assertFalse(h.hasAncestral(5));
		assertFalse(h.hasAncestral(6));
		assertFalse(h.hasAncestral(7));
		assertFalse(h.hasAncestral(8));
		assertFalse(h.hasAncestral(9));
		assertTrue(h.hasAncestral(10));
		assertTrue(h.hasAncestral(11));
	}
	
	
	/*
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
	 */
	@Test
	public void test_AlleleAtIndex()
	{
		SNPCollection s=new SNPCollection(SharedDataFactory.getSNPCollection());
		BitArrayBuilder b=new BitArrayBuilder(12);
		b.setBit(0); b.setBit(10); b.setBit(11); 
		Haplotype h=new Haplotype(b.getBitArray(),s);
		
		assertEquals(h.getAllele(0),'G');
		assertEquals(h.getAllele(1),'C');
		assertEquals(h.getAllele(2),'C');
		assertEquals(h.getAllele(3),'T');
		assertEquals(h.getAllele(10),'A');
		assertEquals(h.getAllele(11),'G');
	}

}
