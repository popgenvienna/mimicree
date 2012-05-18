package mimicree.test.data;

import static org.junit.Assert.*;
import mimicree.data.BitArray.*;
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

}
