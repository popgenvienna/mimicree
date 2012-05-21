package mimicree.test.io;

import static org.junit.Assert.*;

import java.util.ArrayList;
import mimicree.data.haplotypes.Haplotype;
import mimicree.io.haplotypes.HaplotypeReader;
import mimicree.data.haplotypes.SNPCollection;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestHaplotypeReader {
	
	private static ArrayList<Haplotype> h;  // sorted

	
	@BeforeClass
	public static void setUp()
	{
		String input=
				"2R\t10\tG\tA/G\tAG AA AA AA GG\n"+
				"3L\t20\tG\tC/G\tGC CC GC CG GG\n"+
				"3R\t30\tA\tT/A\tTT TA AA AT TA\n"+
				"X\t40\tT\tG/T\tTG GG GG TT GG\n"+
				"X\t50\tT\tT/C\tCC TC CC TT CT\n";

		;
		h=new HaplotypeReader(input,mimicree.test.data.SharedDataFactory.getNullLogger(),true).getHaplotypes();
	}
	
	@Test
	public void test_haplotypeNumber()
	{
		assertEquals(h.size(),10);
	}
	
	@Test
	public void test_snpcol_chromosome() {
		SNPCollection sc=h.get(0).getSNPCollection();
		assertEquals(sc.getSNPforIndex(0).genomicPosition().chromosome().toString(),"2R");
		assertEquals(sc.getSNPforIndex(1).genomicPosition().chromosome().toString(),"3L");
		assertEquals(sc.getSNPforIndex(2).genomicPosition().chromosome().toString(),"3R");
		assertEquals(sc.getSNPforIndex(3).genomicPosition().chromosome().toString(),"X");
		assertEquals(sc.getSNPforIndex(4).genomicPosition().chromosome().toString(),"X");
	}
	
	@Test
	public void test_snpcol_position() {
		SNPCollection sc=h.get(0).getSNPCollection();
		assertEquals(sc.getSNPforIndex(0).genomicPosition().position(),10);
		assertEquals(sc.getSNPforIndex(1).genomicPosition().position(),20);
		assertEquals(sc.getSNPforIndex(2).genomicPosition().position(),30);
		assertEquals(sc.getSNPforIndex(3).genomicPosition().position(),40);
		assertEquals(sc.getSNPforIndex(4).genomicPosition().position(),50);
	}

	@Test
	public void test_snpcol_refchar() {
		SNPCollection sc=h.get(0).getSNPCollection();
		assertEquals(sc.getSNPforIndex(0).referenceCharacter(),'G');
		assertEquals(sc.getSNPforIndex(1).referenceCharacter(),'G');
		assertEquals(sc.getSNPforIndex(2).referenceCharacter(),'A');
		assertEquals(sc.getSNPforIndex(3).referenceCharacter(),'T');
		assertEquals(sc.getSNPforIndex(4).referenceCharacter(),'T');
	}
	
	@Test
	public void test_snpcol_majorallele() {
		SNPCollection sc=h.get(0).getSNPCollection();
		assertEquals(sc.getSNPforIndex(0).majorAllele(),'A');
		assertEquals(sc.getSNPforIndex(1).majorAllele(),'C');
		assertEquals(sc.getSNPforIndex(2).majorAllele(),'T');
		assertEquals(sc.getSNPforIndex(3).majorAllele(),'G');
		assertEquals(sc.getSNPforIndex(4).majorAllele(),'T');
	}

	@Test
	public void test_snpcol_minorallele() {
		SNPCollection sc=h.get(0).getSNPCollection();
		assertEquals(sc.getSNPforIndex(0).minorAllele(),'G');
		assertEquals(sc.getSNPforIndex(1).minorAllele(),'G');
		assertEquals(sc.getSNPforIndex(2).minorAllele(),'A');
		assertEquals(sc.getSNPforIndex(3).minorAllele(),'T');
		assertEquals(sc.getSNPforIndex(4).minorAllele(),'C');
	}
	
	@Test
	public void test_hap0()
	{
		Haplotype ha=h.get(0);
		assertEquals(ha.getAllele(0),'A');
		assertEquals(ha.getAllele(1),'G');
		assertEquals(ha.getAllele(2),'T');
		assertEquals(ha.getAllele(3),'T');
		assertEquals(ha.getAllele(4),'C');
	}
	

	@Test
	public void test_hap1()
	{
		Haplotype ha=h.get(1);
		assertEquals(ha.getAllele(0),'G');
		assertEquals(ha.getAllele(1),'C');
		assertEquals(ha.getAllele(2),'T');
		assertEquals(ha.getAllele(3),'G');
		assertEquals(ha.getAllele(4),'C');
	}
	
	@Test
	public void test_hap8()
	{
		Haplotype ha=h.get(8);
		assertEquals(ha.getAllele(0),'G');
		assertEquals(ha.getAllele(1),'G');
		assertEquals(ha.getAllele(2),'T');
		assertEquals(ha.getAllele(3),'G');
		assertEquals(ha.getAllele(4),'C');
	}
	
	@Test
	public void test_hap9()
	{
		Haplotype ha=h.get(9);
		assertEquals(ha.getAllele(0),'G');
		assertEquals(ha.getAllele(1),'G');
		assertEquals(ha.getAllele(2),'A');
		assertEquals(ha.getAllele(3),'G');
		assertEquals(ha.getAllele(4),'T');
	}
	

}
