package test.data;

import static org.junit.Assert.*;
import org.junit.Test;

import data.fitness.AdditiveSNP;

public class TestAdditiveSNP {

	
	@Test
	public void test_snp1()
	{
		AdditiveSNP a=SharedDataFactory.getAdditiveSNP_int1();
		assertEquals(a.getFitnessForGenotype(new char[]{'A','A'}), 1.0, 0.00000000001);
		assertEquals(a.getFitnessForGenotype(new char[]{'T','T'}), 1.1, 0.00000000001);
		assertEquals(a.getFitnessForGenotype(new char[]{'A','T'}), 1.05,0.00000000001);
		assertEquals(a.getFitnessForGenotype(new char[]{'T','A'}), 1.05,0.00000000001);
	}
	
	@Test
	public void test_snp2()
	{
		AdditiveSNP a=SharedDataFactory.getAdditiveSNP_int2();
		assertEquals(a.getFitnessForGenotype(new char[]{'C','C'}), 1.0, 0.00000000001);
		assertEquals(a.getFitnessForGenotype(new char[]{'G','G'}), 0.5, 0.00000000001);
		assertEquals(a.getFitnessForGenotype(new char[]{'C','G'}), 0.75,0.00000000001);
		assertEquals(a.getFitnessForGenotype(new char[]{'G','C'}), 0.75,0.00000000001);
	}
	
	@Test
	public void test_dom1()
	{
		AdditiveSNP a=SharedDataFactory.getAdditiveSNP_domW11();
		assertEquals(a.getFitnessForGenotype(new char[]{'A','A'}), 1.0, 0.00000000001);
		assertEquals(a.getFitnessForGenotype(new char[]{'T','T'}), 1.1, 0.00000000001);
		assertEquals(a.getFitnessForGenotype(new char[]{'T','A'}), 1.0,0.00000000001);
		assertEquals(a.getFitnessForGenotype(new char[]{'A','T'}), 1.0 ,0.00000000001);
	}
	
	@Test
	public void test_dom2()
	{
		AdditiveSNP a=SharedDataFactory.getAdditiveSNP_domW22();
		assertEquals(a.getFitnessForGenotype(new char[]{'A','A'}), 1.0, 0.00000000001);
		assertEquals(a.getFitnessForGenotype(new char[]{'T','T'}), 1.1, 0.00000000001);
		assertEquals(a.getFitnessForGenotype(new char[]{'T','A'}), 1.1,0.00000000001);
		assertEquals(a.getFitnessForGenotype(new char[]{'A','T'}), 1.1 ,0.00000000001);
	}
}
