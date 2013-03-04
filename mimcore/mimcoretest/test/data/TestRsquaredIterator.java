package test.data;

import mimicree.data.*;
import mimicree.data.LD.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;

public class TestRsquaredIterator {
	
	private static RsquaredGenomeSlider iter_linked_2;
	private static RsquaredGenomeSlider iter_linked_1;
	private static RsquaredGenomeSlider iter_unlinked_2;
	private static RsquaredGenomeSlider iter_allfixed;
	private static RsquaredGenomeSlider iter_chrbound;
	
	@BeforeClass
	public static void setUp()
	{
		 iter_linked_2	=new RsquaredGenomeSlider(SharedDataFactory.getHaploidGenomes_linked(),2);
		 iter_linked_1	=new RsquaredGenomeSlider(SharedDataFactory.getHaploidGenomes_linked(),1);
		 iter_unlinked_2=new RsquaredGenomeSlider(SharedDataFactory.getHaploidGenomes_unlinked(),2);
		 iter_allfixed	=new RsquaredGenomeSlider(SharedDataFactory.getHaploidGenomes_allfixed(),1);
		 iter_chrbound 	=new RsquaredGenomeSlider(SharedDataFactory.getHaploidGenomes_chrBound(),2);
	}
	

	@Test
	public void test_allfixed()
	{
		PairwiseRsquared r= iter_allfixed.next();
		assertEquals(r,null);
	}
	
	@Test
	public void test_linked1(){
		PairwiseRsquared r= iter_linked_2.next();
		assertEquals(r.distance(),1);
		assertEquals(r.positionA().chromosome().toString(),"2L");
		assertEquals(r.positionB().chromosome().toString(),"2L");
		assertEquals(r.positionA().position(),1);
		assertEquals(r.positionB().position(),2);
		assertEquals(r.rsquared(),1.0,0.0000001);
	}
	
	@Test
	public void test_linked2()
	{
		PairwiseRsquared r= iter_linked_2.next();
		assertEquals(r.distance(),2);
		assertEquals(r.positionA().chromosome().toString(),"2L");
		assertEquals(r.positionB().chromosome().toString(),"2L");
		assertEquals(r.positionA().position(),1);
		assertEquals(r.positionB().position(),3);
		assertEquals(r.rsquared(),1.0,0.0000001);
	}
	
	@Test
	public void test_linked3()
	{
		PairwiseRsquared r= iter_linked_2.next();
		assertEquals(r.distance(),1);
		assertEquals(r.positionA().chromosome().toString(),"2L");
		assertEquals(r.positionB().chromosome().toString(),"2L");
		assertEquals(r.positionA().position(),2);
		assertEquals(r.positionB().position(),3);
		assertEquals(r.rsquared(),1.0,0.0000001);
	}
	
	@Test
	public void test_linked4()
	{
		PairwiseRsquared r= iter_linked_2.next();
		assertEquals(r,null);
	}
	
	@Test
	public void test_linked5(){
		PairwiseRsquared r= iter_linked_1.next();
		assertEquals(r.distance(),1);
		assertEquals(r.positionA().chromosome().toString(),"2L");
		assertEquals(r.positionB().chromosome().toString(),"2L");
		assertEquals(r.positionA().position(),1);
		assertEquals(r.positionB().position(),2);
		assertEquals(r.rsquared(),1.0,0.0000001);
	}
	
	@Test
	public void test_linked6(){
		PairwiseRsquared r= iter_linked_1.next();
		assertEquals(r.distance(),1);
		assertEquals(r.positionA().chromosome().toString(),"2L");
		assertEquals(r.positionB().chromosome().toString(),"2L");
		assertEquals(r.positionA().position(),2);
		assertEquals(r.positionB().position(),3);
		assertEquals(r.rsquared(),1.0,0.0000001);
	}
	
	@Test
	public void test_linked7(){
		PairwiseRsquared r= iter_linked_1.next();
		assertEquals(r,null);
	}
	
	@Test
	public void test_unlinked1(){
		PairwiseRsquared r= iter_unlinked_2.next();
		assertEquals(r.distance(),1);
		assertEquals(r.positionA().chromosome().toString(),"2L");
		assertEquals(r.positionB().chromosome().toString(),"2L");
		assertEquals(r.positionA().position(),1);
		assertEquals(r.positionB().position(),2);
		assertEquals(r.rsquared(),0.0,0.0000001);
	}
	
	@Test
	public void test_unlinked2(){
		PairwiseRsquared r= iter_unlinked_2.next();
		assertEquals(r.distance(),2);
		assertEquals(r.positionA().chromosome().toString(),"2L");
		assertEquals(r.positionB().chromosome().toString(),"2L");
		assertEquals(r.positionA().position(),1);
		assertEquals(r.positionB().position(),3);
		assertEquals(r.rsquared(),0.0,0.0000001);
	}
	
	@Test
	public void test_unlinked3(){
		PairwiseRsquared r= iter_unlinked_2.next();
		assertEquals(r.distance(),1);
		assertEquals(r.positionA().chromosome().toString(),"2L");
		assertEquals(r.positionB().chromosome().toString(),"2L");
		assertEquals(r.positionA().position(),2);
		assertEquals(r.positionB().position(),3);
		assertEquals(r.rsquared(),0.0,0.0000001);
	}
	
	@Test
	public void test_unlinked4(){
		PairwiseRsquared r= iter_unlinked_2.next();
		assertEquals(r,null);
	}
	
	@Test
	public void test_chrBound1(){
		PairwiseRsquared r= iter_chrbound.next();
		assertEquals(r.distance(),1);
		assertEquals(r.positionA().chromosome().toString(),"2L");
		assertEquals(r.positionB().chromosome().toString(),"2L");
		assertEquals(r.positionA().position(),1);
		assertEquals(r.positionB().position(),2);
		assertEquals(r.rsquared(),1.0,0.0000001);
	}
	
	@Test
	public void test_chrBound2(){
		PairwiseRsquared r= iter_chrbound.next();
		assertEquals(r.distance(),1);
		assertEquals(r.positionA().chromosome().toString(),"2R");
		assertEquals(r.positionB().chromosome().toString(),"2R");
		assertEquals(r.positionA().position(),1);
		assertEquals(r.positionB().position(),2);
		assertEquals(r.rsquared(),1.0,0.0000001);
	}
	
	@Test
	public void test_chrBound3(){
		PairwiseRsquared r= iter_chrbound.next();
		assertEquals(r,null);

	}

	
	

	

}
