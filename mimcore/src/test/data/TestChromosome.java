package test.data;

import static org.junit.Assert.*;
import mimicree.data.Chromosome;
import java.util.*;

import org.junit.Test;



public class TestChromosome {

	@Test
	public void test_chr1() {
		Chromosome c=Chromosome.getChromosome("2L");
		assertEquals(c.toString(),"2L");
	}
	
	@Test
	public void test_chr2() {
		Chromosome c=Chromosome.getChromosome("2L");
		c=Chromosome.getChromosome("2R");
		assertEquals(c.toString(),"2R");
	}
	
	@Test
	public void test_chrcollection() {
		Chromosome.getChromosome("2L");
		Chromosome.getChromosome("2R");
		Chromosome.getChromosome("3L");
		
		ArrayList<Chromosome> chrs=Chromosome.getChromosome();
		HashSet<Chromosome> h=new HashSet<Chromosome>(chrs);
		assertEquals(chrs.size(),3);
		assertTrue(h.contains(Chromosome.getChromosome("2L")));
		assertTrue(h.contains(Chromosome.getChromosome("2R")));
		assertTrue(h.contains(Chromosome.getChromosome("3L")));
	}
	
	@Test
	public void test_influenceOfDefault() {
		Chromosome.getChromosome("2L");
		Chromosome.getChromosome("2R");
		Chromosome.getChromosome("3L");
		Chromosome.getDefaultChromosome();
		
		ArrayList<Chromosome> chrs=Chromosome.getChromosome();
		HashSet<Chromosome> h=new HashSet<Chromosome>(chrs);
		assertEquals(chrs.size(),3);
		assertTrue(h.contains(Chromosome.getChromosome("2L")));
		assertTrue(h.contains(Chromosome.getChromosome("2R")));
		assertTrue(h.contains(Chromosome.getChromosome("3L")));
	}

}
