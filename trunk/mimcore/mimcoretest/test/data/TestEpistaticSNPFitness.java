package test.data;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import data.*;
import data.fitness.EpistaticSNP;
import data.fitness.EpistaticSNPFitness;

public class TestEpistaticSNPFitness {

	
	@Test
	public void test_episolofit1()
	{
		ArrayList<EpistaticSNP> es=new ArrayList<EpistaticSNP>();
		es.add(SharedDataFactory.getEpistaticSNP_syn1());
		EpistaticSNPFitness ef=new EpistaticSNPFitness(es);
		DiploidGenome g=SharedDataFactory.getDiploidGenome_11();
		
		assertEquals(ef.getEpistaticFitness(g) ,1.1, 0.00000001);
	}
	
	@Test
	public void test_episolofit2()
	{
		ArrayList<EpistaticSNP> es=new ArrayList<EpistaticSNP>();
		es.add(SharedDataFactory.getEpistaticSNP_syn1());
		EpistaticSNPFitness ef=new EpistaticSNPFitness(es);
		DiploidGenome g=SharedDataFactory.getDiploidGenome_00();
		
		assertEquals(ef.getEpistaticFitness(g) ,1.0, 0.00000001);
	}
	
	@Test
	public void test_episolofit3()
	{
		ArrayList<EpistaticSNP> es=new ArrayList<EpistaticSNP>();
		es.add(SharedDataFactory.getEpistaticSNP_syn1());
		EpistaticSNPFitness ef=new EpistaticSNPFitness(es);
		DiploidGenome g=SharedDataFactory.getDiploidGenome_10();
		
		assertEquals(ef.getEpistaticFitness(g) ,1.1, 0.00000001);
	}
	
	@Test
	public void test_epimultifit1()
	{
		ArrayList<EpistaticSNP> es=new ArrayList<EpistaticSNP>();
		es.add(SharedDataFactory.getEpistaticSNP_syn1());
		es.add(SharedDataFactory.getEpistaticSNP_syn2());
		EpistaticSNPFitness ef=new EpistaticSNPFitness(es);
		DiploidGenome g=SharedDataFactory.getDiploidGenome_11();
		
		assertEquals(ef.getEpistaticFitness(g) ,1.1, 0.00000001);
	}
	
	@Test
	public void test_epimultifit2()
	{
		ArrayList<EpistaticSNP> es=new ArrayList<EpistaticSNP>();
		es.add(SharedDataFactory.getEpistaticSNP_syn1());
		es.add(SharedDataFactory.getEpistaticSNP_syn2());
		EpistaticSNPFitness ef=new EpistaticSNPFitness(es);
		DiploidGenome g=SharedDataFactory.getDiploidGenome_00();
		
		assertEquals(ef.getEpistaticFitness(g) ,1.1, 0.00000001);
	}
	
	@Test
	public void test_epimultifit3()
	{
		ArrayList<EpistaticSNP> es=new ArrayList<EpistaticSNP>();
		es.add(SharedDataFactory.getEpistaticSNP_syn1());
		es.add(SharedDataFactory.getEpistaticSNP_syn2());
		EpistaticSNPFitness ef=new EpistaticSNPFitness(es);
		DiploidGenome g=SharedDataFactory.getDiploidGenome_10();
		
		assertEquals(ef.getEpistaticFitness(g) ,1.21, 0.00000001);
	}
	
	@Test
	public void test_epimultifit4()
	{
		ArrayList<EpistaticSNP> es=new ArrayList<EpistaticSNP>();
		es.add(SharedDataFactory.getEpistaticSNP_syn1());
		es.add(SharedDataFactory.getEpistaticSNP_syn2());
		es.add(SharedDataFactory.getEpistaticSNP_syn3());
		EpistaticSNPFitness ef=new EpistaticSNPFitness(es);
		DiploidGenome g=SharedDataFactory.getDiploidGenome_10();
		
		assertEquals(ef.getEpistaticFitness(g) ,1.331, 0.00000001);
	}
	
	
	
}
