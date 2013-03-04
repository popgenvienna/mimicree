package test.data;

import static org.junit.Assert.*;
import org.junit.Test;
import mimicree.data.*;
import mimicree.data.fitness.EpistaticSNP;

public class TestEpistaticSNP {

	
	@Test
	public void test_episnp1()
	{
		EpistaticSNP e=SharedDataFactory.getEpistaticSNP_syn1();
		DiploidGenome g=SharedDataFactory.getDiploidGenome_11();
		assertEquals(e.getEpistaticEffect(g),1.1, 0.00000001);
	}
	
	@Test
	public void test_episnp2()
	{
		EpistaticSNP e=SharedDataFactory.getEpistaticSNP_syn1();
		DiploidGenome g=SharedDataFactory.getDiploidGenome_10();
		assertEquals(e.getEpistaticEffect(g),1.1, 0.00000001);
	}
	
	@Test
	public void test_episnp3()
	{
		EpistaticSNP e=SharedDataFactory.getEpistaticSNP_syn1();
		DiploidGenome g=SharedDataFactory.getDiploidGenome_00();
		assertEquals(e.getEpistaticEffect(g),1.0, 0.00000001);
	}
	
	
	
	
	@Test
	public void test_episnp4()
	{
		EpistaticSNP e=SharedDataFactory.getEpistaticSNP_syn2();
		DiploidGenome g=SharedDataFactory.getDiploidGenome_11();
		assertEquals(e.getEpistaticEffect(g),1.0, 0.00000001);
	}
	
	@Test
	public void test_episnp5()
	{
		EpistaticSNP e=SharedDataFactory.getEpistaticSNP_syn2();
		DiploidGenome g=SharedDataFactory.getDiploidGenome_10();
		assertEquals(e.getEpistaticEffect(g),1.1, 0.00000001);
	}
	
	@Test
	public void test_episnp6()
	{
		EpistaticSNP e=SharedDataFactory.getEpistaticSNP_syn2();
		DiploidGenome g=SharedDataFactory.getDiploidGenome_00();
		assertEquals(e.getEpistaticEffect(g),1.1, 0.00000001);
	}
	
	
	
	
	
	@Test
	public void test_episnp7()
	{
		EpistaticSNP e=SharedDataFactory.getEpistaticSNP_syn3();
		DiploidGenome g=SharedDataFactory.getDiploidGenome_11();
		assertEquals(e.getEpistaticEffect(g),1.0, 0.00000001);
	}
	
	@Test
	public void test_episnp8()
	{
		EpistaticSNP e=SharedDataFactory.getEpistaticSNP_syn3();
		DiploidGenome g=SharedDataFactory.getDiploidGenome_00();
		assertEquals(e.getEpistaticEffect(g),1.0, 0.00000001);
	}
	
	@Test
	public void test_episnp9()
	{
		EpistaticSNP e=SharedDataFactory.getEpistaticSNP_syn3();
		DiploidGenome g=SharedDataFactory.getDiploidGenome_10();
		assertEquals(e.getEpistaticEffect(g),1.1, 0.00000001);
	}
	
}
