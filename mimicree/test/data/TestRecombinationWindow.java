package mimicree.test.data;

import static org.junit.Assert.*;
import mimicree.data.Chromosome;
import  mimicree.data.recombination.RecombinationWindow;
import mimicree.data.GenomicPosition;

import org.junit.Test;

public class TestRecombinationWindow {

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
		RecombinationWindow rw=new RecombinationWindow(Chromosome.getChromosome("2L"),10,15,50);
		
		for(int i=0; i<100; i++)
		{
			GenomicPosition p=rw.getRandomPosition();
			assertEquals(p.chromosome().toString(),"2L");
			assertTrue(p.position()>=10);
			assertTrue(p.position()<=15);
		}
	}

}