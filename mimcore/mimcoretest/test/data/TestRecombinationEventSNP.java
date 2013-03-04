package test.data;

import java.util.ArrayList;
import org.junit.Test;
import data.recombination.*;
import data.*;
import data.haplotypes.*;
import static org.junit.Assert.*;


public class TestRecombinationEventSNP {
	
	// 2L=A-T
	// 2R=C-G
	public static Haplotype maj=SharedDataFactory.getCrossoverHaplotypeMajor(); 
	public static Haplotype min=SharedDataFactory.getCrossoverHaplotypeMinor();
	public static Chromosome c2L=Chromosome.getChromosome("2L");
	public static Chromosome c2R=Chromosome.getChromosome("2R");
	
	@Test
	public void test_no_crossover_hA()
	{
		RecombinationEvent re=new RecombinationEvent(SharedDataFactory.getRandomAssortment11(),new CrossoverEvents(new ArrayList<GenomicPosition>()));
		Haplotype h=re.getCrossoverHaplotype(maj, min);
		assertEquals(h.getAllele(0),'A');
		assertEquals(h.getAllele(9),'A');
		assertEquals(h.getAllele(10),'C');
		assertEquals(h.getAllele(19),'C');
	}
	
	@Test
	public void test_no_crossover_hB()
	{
		RecombinationEvent re=new RecombinationEvent(SharedDataFactory.getRandomAssortment00(),new CrossoverEvents(new ArrayList<GenomicPosition>()));
		Haplotype h=re.getCrossoverHaplotype(maj, min);
		assertEquals(h.getAllele(0),'T');
		assertEquals(h.getAllele(9),'T');
		assertEquals(h.getAllele(10),'G');
		assertEquals(h.getAllele(19),'G');
	}
	
	
	@Test
	public void test_oneCO_firstchr()
	{
		ArrayList<GenomicPosition> cos=new ArrayList<GenomicPosition>();
		cos.add(new GenomicPosition(c2L,1));
		RandomAssortment ra=SharedDataFactory.getRandomAssortment10();
		RecombinationEvent re=new RecombinationEvent(ra,new CrossoverEvents(cos));
		Haplotype h=re.getCrossoverHaplotype(maj, min);
		assertEquals(h.getAllele(0),'A');
		assertEquals(h.getAllele(1),'T');
		assertEquals(h.getAllele(9),'T');
		assertEquals(h.getAllele(10),'G');
		assertEquals(h.getAllele(19),'G');
	}
	
	@Test
	public void test_oneCO_firstchr2()
	{
		ArrayList<GenomicPosition> cos=new ArrayList<GenomicPosition>();
		cos.add(new GenomicPosition(c2L,1));
		RandomAssortment ra=SharedDataFactory.getRandomAssortment01();
		RecombinationEvent re=new RecombinationEvent(ra,new CrossoverEvents(cos));
		Haplotype h=re.getCrossoverHaplotype(maj, min);
		assertEquals(h.getAllele(0),'T');
		assertEquals(h.getAllele(1),'A');
		assertEquals(h.getAllele(9),'A');
		assertEquals(h.getAllele(10),'C');
		assertEquals(h.getAllele(19),'C');
	}
	
	@Test
	public void test_twoCO_firstchr()
	{
		// 2L=A-T
		// 2R=C-G
		ArrayList<GenomicPosition> cos=new ArrayList<GenomicPosition>();
		cos.add(new GenomicPosition(c2L,1));
		cos.add(new GenomicPosition(c2L,2));
		RandomAssortment ra=SharedDataFactory.getRandomAssortment11();
		RecombinationEvent re=new RecombinationEvent(ra,new CrossoverEvents(cos));
		Haplotype h=re.getCrossoverHaplotype(maj, min);
		assertEquals(h.getAllele(0),'A');
		assertEquals(h.getAllele(1),'T');
		assertEquals(h.getAllele(2),'A');
		assertEquals(h.getAllele(9),'A');
		assertEquals(h.getAllele(10),'C');
		assertEquals(h.getAllele(19),'C');
	}
	
	@Test
	public void test_threeCO_firstchr()
	{
		// 2L=A-T
		// 2R=C-G
		ArrayList<GenomicPosition> cos=new ArrayList<GenomicPosition>();
		cos.add(new GenomicPosition(c2L,1));
		cos.add(new GenomicPosition(c2L,2));
		cos.add(new GenomicPosition(c2L,3));
		
		RandomAssortment ra=SharedDataFactory.getRandomAssortment10();
		RecombinationEvent re=new RecombinationEvent(ra,new CrossoverEvents(cos));
		Haplotype h=re.getCrossoverHaplotype(maj, min);
		assertEquals(h.getAllele(0),'A');
		assertEquals(h.getAllele(1),'T');
		assertEquals(h.getAllele(2),'A');
		assertEquals(h.getAllele(3),'T');
		assertEquals(h.getAllele(9),'T');
		assertEquals(h.getAllele(10),'G');
		assertEquals(h.getAllele(19),'G');
	}
	
	@Test
	public void test_threeCOunsorted_firstchr()
	{
		// 2L=A-T
		// 2R=C-G
		ArrayList<GenomicPosition> cos=new ArrayList<GenomicPosition>();
		cos.add(new GenomicPosition(c2L,3));
		cos.add(new GenomicPosition(c2L,1));
		cos.add(new GenomicPosition(c2L,2));

		
		RandomAssortment ra=SharedDataFactory.getRandomAssortment10();
		RecombinationEvent re=new RecombinationEvent(ra,new CrossoverEvents(cos));
		Haplotype h=re.getCrossoverHaplotype(maj, min);
		assertEquals(h.getAllele(0),'A');
		assertEquals(h.getAllele(1),'T');
		assertEquals(h.getAllele(2),'A');
		assertEquals(h.getAllele(3),'T');
		assertEquals(h.getAllele(9),'T');
		assertEquals(h.getAllele(10),'G');
		assertEquals(h.getAllele(19),'G');
	}
	
	
	@Test
	public void test_thrreCOfirst_oneCOsecond()
	{
		// 2L=A-T
		// 2R=C-G
		ArrayList<GenomicPosition> cos=new ArrayList<GenomicPosition>();
		cos.add(new GenomicPosition(c2L,3));
		cos.add(new GenomicPosition(c2L,1));
		cos.add(new GenomicPosition(c2L,2));
		cos.add(new GenomicPosition(c2R,1));
		
		RandomAssortment ra=SharedDataFactory.getRandomAssortment10();
		RecombinationEvent re=new RecombinationEvent(ra,new CrossoverEvents(cos));
		Haplotype h=re.getCrossoverHaplotype(maj, min);
		assertEquals(h.getAllele(0),'A');
		assertEquals(h.getAllele(1),'T');
		assertEquals(h.getAllele(2),'A');
		assertEquals(h.getAllele(3),'T');
		assertEquals(h.getAllele(9),'T');
		assertEquals(h.getAllele(10),'G');
		assertEquals(h.getAllele(11),'C');
		assertEquals(h.getAllele(19),'C');
	}
	
	
	@Test
	public void test_thrreCOfirst_twoCOsecond()
	{
		// 2L=A-T
		// 2R=C-G
		ArrayList<GenomicPosition> cos=new ArrayList<GenomicPosition>();
		cos.add(new GenomicPosition(c2L,3));
		cos.add(new GenomicPosition(c2L,1));
		cos.add(new GenomicPosition(c2L,2));
		cos.add(new GenomicPosition(c2R,1));
		cos.add(new GenomicPosition(c2R,2));
		
		RandomAssortment ra=SharedDataFactory.getRandomAssortment10();
		RecombinationEvent re=new RecombinationEvent(ra,new CrossoverEvents(cos));
		Haplotype h=re.getCrossoverHaplotype(maj, min);
		assertEquals(h.getAllele(0),'A');
		assertEquals(h.getAllele(1),'T');
		assertEquals(h.getAllele(2),'A');
		assertEquals(h.getAllele(3),'T');
		assertEquals(h.getAllele(9),'T');
		assertEquals(h.getAllele(10),'G');
		assertEquals(h.getAllele(11),'C');
		assertEquals(h.getAllele(12),'G');
		assertEquals(h.getAllele(19),'G');
	}
	
	@Test
	public void test_thrreCOfirst_threeCOsecond()
	{
		// 2L=A-T
		// 2R=C-G
		ArrayList<GenomicPosition> cos=new ArrayList<GenomicPosition>();
		cos.add(new GenomicPosition(c2L,3));
		cos.add(new GenomicPosition(c2L,1));
		cos.add(new GenomicPosition(c2L,2));
		cos.add(new GenomicPosition(c2R,1));
		cos.add(new GenomicPosition(c2R,2));
		cos.add(new GenomicPosition(c2R,3));
		
		
		RandomAssortment ra=SharedDataFactory.getRandomAssortment10();
		RecombinationEvent re=new RecombinationEvent(ra,new CrossoverEvents(cos));
		Haplotype h=re.getCrossoverHaplotype(maj, min);
		assertEquals(h.getAllele(0),'A');
		assertEquals(h.getAllele(1),'T');
		assertEquals(h.getAllele(2),'A');
		assertEquals(h.getAllele(3),'T');
		assertEquals(h.getAllele(9),'T');
		assertEquals(h.getAllele(10),'G');
		assertEquals(h.getAllele(11),'C');
		assertEquals(h.getAllele(12),'G');
		assertEquals(h.getAllele(13),'C');
		assertEquals(h.getAllele(19),'C');
	}
	
	
	

}
