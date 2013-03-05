package test.data;

import mimcore.data.haplotypes.*;
import mimcore.data.*;

import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSNPCollection {
	
	

	

	@Test
	public void test_index() {
		ArrayList<SNP> snps=SharedDataFactory.getSNPCollection();
		SNPCollection s=new SNPCollection(snps);
		assertEquals(s.size(),12);
		int index;
		
		index=s.getIndexforPosition(new GenomicPosition(Chromosome.getChromosome("2R"),13));
		assertEquals(index,11);
		index=s.getIndexforPosition(new GenomicPosition(Chromosome.getChromosome("2L"),2));
		assertEquals(index,0);
		index=s.getIndexforPosition(new GenomicPosition(Chromosome.getChromosome("2R"),11));
		assertEquals(index,5);		
	}
	
	@Test
	public void test_getSNPForPosition() {
		ArrayList<SNP> snps=SharedDataFactory.getSNPCollection();
		SNPCollection c=new SNPCollection(snps);
		assertEquals(c.size(),12);
		
		SNP s;
		s=c.getSNPforPosition(new GenomicPosition(Chromosome.getChromosome("2R"),13));
		assertEquals(s.genomicPosition().position(), 13);
		assertEquals(s.referenceCharacter(),'T');
	
		s=c.getSNPforPosition(new GenomicPosition(Chromosome.getChromosome("2L"),2));
		assertEquals(s.genomicPosition().position(), 2);
		assertEquals(s.referenceCharacter(),'G');
		
		s=c.getSNPforPosition(new GenomicPosition(Chromosome.getChromosome("3R"),1112));
		assertEquals(s.genomicPosition().position(), 1112);
		assertEquals(s.referenceCharacter(),'C');
	}
	
	
	@Test
	public void test_getSNPForIndex() {
		ArrayList<SNP> snps=SharedDataFactory.getSNPCollection();
		SNPCollection c=new SNPCollection(snps);
		assertEquals(c.size(),12);
		
		SNP s;
		s=c.getSNPforIndex(0);
		assertEquals(s.genomicPosition().position(), 2);
		assertEquals(s.referenceCharacter(),'G');
		
		s=c.getSNPforIndex(11);
		assertEquals(s.genomicPosition().position(), 13);
		assertEquals(s.referenceCharacter(),'T');
		
		s=c.getSNPforIndex(2);
		assertEquals(s.genomicPosition().position(), 1112);
		assertEquals(s.referenceCharacter(),'C');
	}
	
	@Test
	public void test_SNP() {
		ArrayList<SNP> snps=SharedDataFactory.getSNPCollection();
		SNPCollection c=new SNPCollection(snps);
		assertEquals(c.size(),12);
		
		SNP s;
		//	public static SNP s2=new SNP(new GenomicPosition(Chromosome.getChromosome("2L"),2),'G','G','C');
		s=c.getSNPforIndex(0);
		assertEquals(s.genomicPosition().position(), 2);
		assertEquals(s.genomicPosition().chromosome().toString(),"2L");
		assertEquals(s.referenceCharacter(),'G');
		assertEquals(s.ancestralAllele(),'G');
		assertEquals(s.derivedAllele(),'C');
		
		
		//public static SNP s11=new SNP(new GenomicPosition(Chromosome.getChromosome("3R"),1112),'C','G','C');
		s=c.getSNPforIndex(2);
		assertEquals(s.genomicPosition().position(), 1112);
		assertEquals(s.genomicPosition().chromosome().toString(),"3R");
		assertEquals(s.referenceCharacter(),'C');
		assertEquals(s.ancestralAllele(),'G');
		assertEquals(s.derivedAllele(),'C');
	}
	
	@Test
	public void test_upstreamImmutable() {
		ArrayList<SNP> snps=SharedDataFactory.getSNPCollection();
		SNPCollection c=new SNPCollection(snps);
		Collections.sort(snps);
		SNPCollection c1=new SNPCollection(snps);
		
		int index;
		index=c.getIndexforPosition(new GenomicPosition(Chromosome.getChromosome("2R"),13));
		assertEquals(index,11);
		index=c1.getIndexforPosition(new GenomicPosition(Chromosome.getChromosome("2R"),13));
		assertEquals(index,5);
		
		index=c.getIndexforPosition(new GenomicPosition(Chromosome.getChromosome("2L"),2));
		assertEquals(index,0);
		index=c1.getIndexforPosition(new GenomicPosition(Chromosome.getChromosome("2L"),2));
		assertEquals(index,1);
	}
	
	@Test
	public void test_isSorted() {
		ArrayList<SNP> snps=SharedDataFactory.getSNPCollection();
		SNPCollection c=new SNPCollection(snps);
		Collections.sort(snps);
		SNPCollection c1=new SNPCollection(snps);
		
		assertTrue(c1.isSorted());
		assertFalse(c.isSorted());
	}
	
	
	
	
	// Test Immutable by sorting and creating new collection index should not change except for the sorted collection
	// Test upstream and downstream immutable
	
	
	

}
