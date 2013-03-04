package test.data;

import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;
import data.recombination.*;
import data.*;
import static org.junit.Assert.*;


public class TestRandomAssortment {

	private static RandomAssortmentGenerator randgen;
	private static Chromosome c2L=Chromosome.getChromosome("2L");
	private static Chromosome c2R=Chromosome.getChromosome("2R");
	
	@BeforeClass
	public static void ini()
	{
		ArrayList<ArrayList<Chromosome>> chrs=new ArrayList<ArrayList<Chromosome>>();
		ArrayList<Chromosome> c2=new ArrayList<Chromosome>();
		c2.add(c2L);
		c2.add(c2R);
		chrs.add(c2);
		randgen=new RandomAssortmentGenerator(chrs);
	}
	
	
	@Test
	public void test_noCrossover()
	{
		CrossoverEvents ce=new CrossoverEvents(new ArrayList<GenomicPosition>());
		for(int i=0; i<100; i++)
		{
			RandomAssortment ra=randgen.getRandomAssortment(ce);
			boolean first=ra.startWithFirstHaplotype(c2L);
			boolean second=ra.startWithFirstHaplotype(c2R);
			assertEquals(first,second);
		}
	}
	
	
	@Test
	public void test_singleCrossover()
	{
		ArrayList<GenomicPosition> cros=new ArrayList<GenomicPosition>();
		cros.add(new GenomicPosition(c2L,1));
		CrossoverEvents ce=new CrossoverEvents(cros);
		
		for(int i=0; i<100; i++)
		{
			RandomAssortment ra=randgen.getRandomAssortment(ce);
			boolean first=ra.startWithFirstHaplotype(c2L);
			boolean second=ra.startWithFirstHaplotype(c2R);
			assertEquals(first,!second);
		}
	}
	
	@Test
	public void test_singleCrossoverOtherChromosome()
	{
		// Should have no effect
		ArrayList<GenomicPosition> cros=new ArrayList<GenomicPosition>();
		cros.add(new GenomicPosition(c2R,1));
		CrossoverEvents ce=new CrossoverEvents(cros);
		
		for(int i=0; i<100; i++)
		{
			RandomAssortment ra=randgen.getRandomAssortment(ce);
			boolean first=ra.startWithFirstHaplotype(c2L);
			boolean second=ra.startWithFirstHaplotype(c2R);
			assertEquals(first,second);
		}
	}
	
	@Test
	public void test_twoCrossover()
	{
		// Should have no effect
		ArrayList<GenomicPosition> cros=new ArrayList<GenomicPosition>();
		cros.add(new GenomicPosition(c2L,1));
		cros.add(new GenomicPosition(c2L,2));
		CrossoverEvents ce=new CrossoverEvents(cros);
		
		for(int i=0; i<100; i++)
		{
			RandomAssortment ra=randgen.getRandomAssortment(ce);
			boolean first=ra.startWithFirstHaplotype(c2L);
			boolean second=ra.startWithFirstHaplotype(c2R);
			assertEquals(first,second);
		}
	}
	
	@Test
	public void test_threeCrossover()
	{
		// Should have no effect
		ArrayList<GenomicPosition> cros=new ArrayList<GenomicPosition>();
		cros.add(new GenomicPosition(c2L,1));
		cros.add(new GenomicPosition(c2L,2));
		cros.add(new GenomicPosition(c2L,3));
		CrossoverEvents ce=new CrossoverEvents(cros);
		
		for(int i=0; i<100; i++)
		{
			RandomAssortment ra=randgen.getRandomAssortment(ce);
			boolean first=ra.startWithFirstHaplotype(c2L);
			boolean second=ra.startWithFirstHaplotype(c2R);
			assertEquals(first,!second);
		}
	}
	
	
}
