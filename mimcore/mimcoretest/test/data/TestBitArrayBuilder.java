package test.data;

import static org.junit.Assert.*;
import mimcore.data.BitArray.*;
import org.junit.Test;

public class TestBitArrayBuilder {

	@Test
	public void test_b1() {
		BitArrayBuilder b=new BitArrayBuilder(8);
		b.setBit(0);
		assertTrue(b.hasBit(0));
		assertFalse(b.hasBit(1));
		assertFalse(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertFalse(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertFalse(b.hasBit(6));
		assertFalse(b.hasBit(7));
	}
	
	@Test
	public void test_ms1() {
		BitArrayBuilder b=new BitArrayBuilder(8);
		b.setBit(0);
		b.setBit(0);
		assertTrue(b.hasBit(0));
		assertFalse(b.hasBit(1));
		assertFalse(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertFalse(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertFalse(b.hasBit(6));
		assertFalse(b.hasBit(7));
	}
	
	@Test
	public void test_b2() {
		BitArrayBuilder b=new BitArrayBuilder(8);
		b.setBit(1);
		assertFalse(b.hasBit(0));
		assertTrue(b.hasBit(1));
		assertFalse(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertFalse(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertFalse(b.hasBit(6));
		assertFalse(b.hasBit(7));
	}
	
	@Test
	public void test_b3() {
		BitArrayBuilder b=new BitArrayBuilder(8);
		b.setBit(2);
		assertFalse(b.hasBit(0));
		assertFalse(b.hasBit(1));
		assertTrue(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertFalse(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertFalse(b.hasBit(6));
		assertFalse(b.hasBit(7));
	}
	
	@Test
	public void test_b4() {
		BitArrayBuilder b=new BitArrayBuilder(8);
		b.setBit(3);
		assertFalse(b.hasBit(0));
		assertFalse(b.hasBit(1));
		assertFalse(b.hasBit(2));
		assertTrue(b.hasBit(3));
		assertFalse(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertFalse(b.hasBit(6));
		assertFalse(b.hasBit(7));
	}
	
	@Test
	public void test_b5() {
		BitArrayBuilder b=new BitArrayBuilder(8);
		b.setBit(4);
		assertFalse(b.hasBit(0));
		assertFalse(b.hasBit(1));
		assertFalse(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertTrue(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertFalse(b.hasBit(6));
		assertFalse(b.hasBit(7));
	}
	
	@Test
	public void test_b6() {
		BitArrayBuilder b=new BitArrayBuilder(8);
		b.setBit(5);
		assertFalse(b.hasBit(0));
		assertFalse(b.hasBit(1));
		assertFalse(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertFalse(b.hasBit(4));
		assertTrue(b.hasBit(5));
		assertFalse(b.hasBit(6));
		assertFalse(b.hasBit(7));
	}
	
	@Test
	public void test_b7() {
		BitArrayBuilder b=new BitArrayBuilder(8);
		b.setBit(6);
		assertFalse(b.hasBit(0));
		assertFalse(b.hasBit(1));
		assertFalse(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertFalse(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertTrue(b.hasBit(6));
		assertFalse(b.hasBit(7));
	}
	
	@Test
	public void test_b8() {
		BitArrayBuilder b=new BitArrayBuilder(8);
		b.setBit(7);
		assertFalse(b.hasBit(0));
		assertFalse(b.hasBit(1));
		assertFalse(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertFalse(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertFalse(b.hasBit(6));
		assertTrue(b.hasBit(7));
	}
	
	@Test
	public void test_b9() {
		BitArrayBuilder b=new BitArrayBuilder(9);
		b.setBit(8);
		assertFalse(b.hasBit(0));
		assertFalse(b.hasBit(1));
		assertFalse(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertFalse(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertFalse(b.hasBit(6));
		assertFalse(b.hasBit(7));
		assertTrue(b.hasBit(8));
	}
	
	@Test
	public void test_b10() {
		BitArrayBuilder b=new BitArrayBuilder(10);
		b.setBit(9);
		assertFalse(b.hasBit(0));
		assertFalse(b.hasBit(1));
		assertFalse(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertFalse(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertFalse(b.hasBit(6));
		assertFalse(b.hasBit(7));
		assertFalse(b.hasBit(8));
		assertTrue(b.hasBit(9));
	}
	
	@Test
	public void test_zeroBit() {
		BitArrayBuilder b=new BitArrayBuilder(8);
		assertFalse(b.hasBit(0));
		assertFalse(b.hasBit(1));
		assertFalse(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertFalse(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertFalse(b.hasBit(6));
		assertFalse(b.hasBit(7));
	}
	
	@Test
	public void test_allBit() {
		BitArrayBuilder b=new BitArrayBuilder(12);
		b.setBit(0); b.setBit(1); b.setBit(2);b.setBit(3); b.setBit(4); b.setBit(5); b.setBit(6); b.setBit(7); b.setBit(8); b.setBit(9); b.setBit(10); b.setBit(11);		
		assertTrue(b.hasBit(0));
		assertTrue(b.hasBit(1));
		assertTrue(b.hasBit(2));
		assertTrue(b.hasBit(3));
		assertTrue(b.hasBit(4));
		assertTrue(b.hasBit(5));
		assertTrue(b.hasBit(6));
		assertTrue(b.hasBit(7));
		assertTrue(b.hasBit(8));
		assertTrue(b.hasBit(9));
		assertTrue(b.hasBit(10));
		assertTrue(b.hasBit(11));
	}
	
	@Test
	public void test_bitCombo1() {
		BitArrayBuilder b=new BitArrayBuilder(8);
		b.setBit(0);
		b.setBit(7);
		assertTrue(b.hasBit(0));
		assertFalse(b.hasBit(1));
		assertFalse(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertFalse(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertFalse(b.hasBit(6));
		assertTrue(b.hasBit(7));
	}
	
	@Test
	public void test_bitCombo2() {
		BitArrayBuilder b=new BitArrayBuilder(8);
		b.setBit(1);
		b.setBit(2);
		assertFalse(b.hasBit(0));
		assertTrue(b.hasBit(1));
		assertTrue(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertFalse(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertFalse(b.hasBit(6));
		assertFalse(b.hasBit(7));
	}
	
	@Test
	public void test_bitCombo3() {
		BitArrayBuilder b=new BitArrayBuilder(8);
		b.setBit(0);
		b.setBit(4);
		assertTrue(b.hasBit(0));
		assertFalse(b.hasBit(1));
		assertFalse(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertTrue(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertFalse(b.hasBit(6));
		assertFalse(b.hasBit(7));
	}
	
	@Test
	public void test_bitCombo4() {
		BitArrayBuilder b=new BitArrayBuilder(8);
		b.setBit(2);
		b.setBit(6);
		b.setBit(4);
		assertFalse(b.hasBit(0));
		assertFalse(b.hasBit(1));
		assertTrue(b.hasBit(2));
		assertFalse(b.hasBit(3));
		assertTrue(b.hasBit(4));
		assertFalse(b.hasBit(5));
		assertTrue(b.hasBit(6));
		assertFalse(b.hasBit(7));
	}
	
	
	@Test
	public void test_highBit0() {
		BitArrayBuilder b=new BitArrayBuilder(80);
		b.setBit(72);
		assertTrue(b.hasBit(72));
		assertFalse(b.hasBit(73));
		assertFalse(b.hasBit(74));
		assertFalse(b.hasBit(75));
		assertFalse(b.hasBit(76));
		assertFalse(b.hasBit(77));
		assertFalse(b.hasBit(78));
		assertFalse(b.hasBit(79));
	}
	
	@Test
	public void test_highBit1() {
		BitArrayBuilder b=new BitArrayBuilder(80);
		b.setBit(73);
		assertFalse(b.hasBit(72));
		assertTrue(b.hasBit(73));
		assertFalse(b.hasBit(74));
		assertFalse(b.hasBit(75));
		assertFalse(b.hasBit(76));
		assertFalse(b.hasBit(77));
		assertFalse(b.hasBit(78));
		assertFalse(b.hasBit(79));
	}
	
	@Test
	public void test_highBit2() {
		BitArrayBuilder b=new BitArrayBuilder(80);
		b.setBit(74);
		assertFalse(b.hasBit(72));
		assertFalse(b.hasBit(73));
		assertTrue(b.hasBit(74));
		assertFalse(b.hasBit(75));
		assertFalse(b.hasBit(76));
		assertFalse(b.hasBit(77));
		assertFalse(b.hasBit(78));
		assertFalse(b.hasBit(79));
	}
	
	@Test
	public void test_highBit3() {
		BitArrayBuilder b=new BitArrayBuilder(80);
		b.setBit(75);
		assertFalse(b.hasBit(72));
		assertFalse(b.hasBit(73));
		assertFalse(b.hasBit(74));
		assertTrue(b.hasBit(75));
		assertFalse(b.hasBit(76));
		assertFalse(b.hasBit(77));
		assertFalse(b.hasBit(78));
		assertFalse(b.hasBit(79));
	}
	
	@Test
	public void test_highBit4() {
		BitArrayBuilder b=new BitArrayBuilder(80);
		b.setBit(76);
		assertFalse(b.hasBit(72));
		assertFalse(b.hasBit(73));
		assertFalse(b.hasBit(74));
		assertFalse(b.hasBit(75));
		assertTrue(b.hasBit(76));
		assertFalse(b.hasBit(77));
		assertFalse(b.hasBit(78));
		assertFalse(b.hasBit(79));
	}
	
	@Test
	public void test_highBit5() {
		BitArrayBuilder b=new BitArrayBuilder(80);
		b.setBit(77);
		assertFalse(b.hasBit(72));
		assertFalse(b.hasBit(73));
		assertFalse(b.hasBit(74));
		assertFalse(b.hasBit(75));
		assertFalse(b.hasBit(76));
		assertTrue(b.hasBit(77));
		assertFalse(b.hasBit(78));
		assertFalse(b.hasBit(79));
	}
	
	@Test
	public void test_highBit6() {
		BitArrayBuilder b=new BitArrayBuilder(80);
		b.setBit(78);
		assertFalse(b.hasBit(72));
		assertFalse(b.hasBit(73));
		assertFalse(b.hasBit(74));
		assertFalse(b.hasBit(75));
		assertFalse(b.hasBit(76));
		assertFalse(b.hasBit(77));
		assertTrue(b.hasBit(78));
		assertFalse(b.hasBit(79));
	}
	
	@Test
	public void test_highBit7() {
		BitArrayBuilder b=new BitArrayBuilder(80);
		b.setBit(79);
		assertFalse(b.hasBit(72));
		assertFalse(b.hasBit(73));
		assertFalse(b.hasBit(74));
		assertFalse(b.hasBit(75));
		assertFalse(b.hasBit(76));
		assertFalse(b.hasBit(77));
		assertFalse(b.hasBit(78));
		assertTrue(b.hasBit(79));
	}
	
	@Test
	public void test_highBitZero() {
		BitArrayBuilder b=new BitArrayBuilder(80);
		assertFalse(b.hasBit(72));
		assertFalse(b.hasBit(73));
		assertFalse(b.hasBit(74));
		assertFalse(b.hasBit(75));
		assertFalse(b.hasBit(76));
		assertFalse(b.hasBit(77));
		assertFalse(b.hasBit(78));
		assertFalse(b.hasBit(79));
	}
	
	
	@Test
	public void test_highBitAll() {
		BitArrayBuilder b=new BitArrayBuilder(80);
		b.setBit(72); b.setBit(73); b.setBit(74); b.setBit(75); b.setBit(76); b.setBit(77); b.setBit(78); b.setBit(79);
		assertTrue(b.hasBit(72));
		assertTrue(b.hasBit(73));
		assertTrue(b.hasBit(74));
		assertTrue(b.hasBit(75));
		assertTrue(b.hasBit(76));
		assertTrue(b.hasBit(77));
		assertTrue(b.hasBit(78));
		assertTrue(b.hasBit(79));
	}
	
	
	@Test
	public void test_highBitCombi1() {
		BitArrayBuilder b=new BitArrayBuilder(80);
		b.setBit(0); b.setBit(10); b.setBit(20); b.setBit(30); b.setBit(40); b.setBit(50); b.setBit(60); b.setBit(70);
		assertTrue(b.hasBit(0));
		assertTrue(b.hasBit(10));
		assertTrue(b.hasBit(20));
		assertTrue(b.hasBit(30));
		assertTrue(b.hasBit(40));
		assertTrue(b.hasBit(50));
		assertTrue(b.hasBit(60));
		assertTrue(b.hasBit(70));
		
	}

	
}
