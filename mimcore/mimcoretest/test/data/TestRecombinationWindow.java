package test.data;

import static org.junit.Assert.*;
import mimcore.data.Chromosome;
import mimcore.data.recombination.RecombinationWindow;
import mimcore.data.GenomicPosition;

import org.junit.Test;

public class TestRecombinationWindow {

	
	@Test
	public void test_private() {
		RecombinationWindow rw=new RecombinationWindow(Chromosome.getChromosome("2L"),1,1000000,45);
		
		int count=0;
		for(int i=0; i<10000; i++)
		{
			if(rw.hasRecombinationEvent()) count++;
		}
		
		double bla=count;

	}
	
	@Test
	public void test_none() {
		RecombinationWindow rw=new RecombinationWindow(Chromosome.getChromosome("2L"),1,1000000,0);
		
		int count=0;
		for(int i=0; i<1000; i++)
		{
			if(rw.hasRecombinationEvent()) count++;
		}
		assertEquals(count,0);
	}
	
	
	@Test
	public void test_randomPosition() {
		RecombinationWindow rw=new RecombinationWindow(Chromosome.getChromosome("2L"),10,15,30);
		
		for(int i=0; i<100; i++)
		{
			GenomicPosition p=rw.getRandomPosition();
			assertEquals(p.chromosome().toString(),"2L");
			assertTrue(p.position()>=10);
			assertTrue(p.position()<=15);
		}

	}

	@Test
	public void test_rate() {
		RecombinationWindow rw=new RecombinationWindow(Chromosome.getChromosome("2L"),10,15,30);
		assertEquals(rw.recombinationRate(), 30.0,0.0000001);

		rw=new RecombinationWindow(Chromosome.getChromosome("2L"),10,15,10);
		assertEquals(rw.recombinationRate(), 10.0,0.0000001);


		rw=new RecombinationWindow(Chromosome.getChromosome("2L"),10,15,1);
		assertEquals(rw.recombinationRate(), 1.0,0.0000001);
	}

}
