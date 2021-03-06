package test.data;

import static org.junit.Assert.*;
import java.util.*;
import mimcore.data.fitness.*;
import org.junit.Test;

public class TestAdditiveSNPFitness {

	@Test
	public void test_noSNP() {
		ArrayList<AdditiveSNP> snps=new ArrayList<AdditiveSNP>();
		AdditiveSNPFitness fit=new AdditiveSNPFitness(snps);
		
		assertEquals(fit.getAdditiveFitness(SharedDataFactory.getDiploidGenome_11()), 1.0, 0.000000000001);
		assertEquals(fit.getAdditiveFitness(SharedDataFactory.getDiploidGenome_10()), 1.0, 0.000000000001);
	}
	
	@Test
	public void test_singleSNP()
	{
		ArrayList<AdditiveSNP> snps=new ArrayList<AdditiveSNP>();
		snps.add(SharedDataFactory.getAdditiveSNP_int1());
		AdditiveSNPFitness fit= new AdditiveSNPFitness(snps);
		
		
		assertEquals(fit.getAdditiveFitness(SharedDataFactory.getDiploidGenome_11()), 1.0, 0.000000000001);
		assertEquals(fit.getAdditiveFitness(SharedDataFactory.getDiploidGenome_00()), 1.1, 0.000000000001);
		assertEquals(fit.getAdditiveFitness(SharedDataFactory.getDiploidGenome_10()), 1.05, 0.000000000001);
	}
	
	
	@Test
	public void test_twoSNP()
	{
		ArrayList<AdditiveSNP> snps=new ArrayList<AdditiveSNP>();
		snps.add(SharedDataFactory.getAdditiveSNP_int1());
		snps.add(SharedDataFactory.getAdditiveSNP_int2());
		AdditiveSNPFitness fit= new AdditiveSNPFitness(snps);
		
		
		assertEquals(fit.getAdditiveFitness(SharedDataFactory.getDiploidGenome_11()), 1.0, 0.000000000001);
		assertEquals(fit.getAdditiveFitness(SharedDataFactory.getDiploidGenome_00()), 1.65, 0.000000000001);
		assertEquals(fit.getAdditiveFitness(SharedDataFactory.getDiploidGenome_10()), 1.3125, 0.000000000001);
	}
	
	@Test
	public void test_threeSNP()
	{
		ArrayList<AdditiveSNP> snps=new ArrayList<AdditiveSNP>();
		snps.add(SharedDataFactory.getAdditiveSNP_int1());
		snps.add(SharedDataFactory.getAdditiveSNP_int2());
		snps.add(SharedDataFactory.getAdditiveSNP_domW11());
		AdditiveSNPFitness fit= new AdditiveSNPFitness(snps);
		
		
		assertEquals(fit.getAdditiveFitness(SharedDataFactory.getDiploidGenome_11()), 1.0, 0.000000000001);
		assertEquals(fit.getAdditiveFitness(SharedDataFactory.getDiploidGenome_00()), 1.815, 0.000000000001);
		assertEquals(fit.getAdditiveFitness(SharedDataFactory.getDiploidGenome_10()), 1.3125, 0.000000000001);
	}

}
