package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import vector.ConcurVector;

public class ConcurVectorTest {
	
	ConcurVector c;
	ConcurVector d;
	ConcurVector mask;

	@Before
	public void setUp() throws Exception {
		
		c = new ConcurVector(10);
		d = new ConcurVector(10);
		mask = new ConcurVector(10);
		
		d.set(4, 10);
	}

	@Test
	public void testDimension() {
		
		assertTrue(c.dimension() == 10);
	}
	
	@Test
	public void testGet()
	{
		assertTrue(0 <= c.get(4));
	}
	
	@Test
	public void testSet()
	{
		c.set(4, 10);
		assertTrue(c.get(4) == 10);
	}
	
	@Test
	public void testSetAll()
	{
		c.set(4.5);
		assertTrue(c.get(9) == 4.5);
		assertTrue(c.get(2) == 4.5);
		assertTrue(c.get(5) == 4.5);
	}
	
	@Test
	public void testAssign()
	{
		c.assign(d);
		assertTrue(c.get(4) == 10);
	}
	
	@Test
	public void testAssignWithMaskGreaterThan0()
	{
		mask.set(1);
		c.assign(mask, d);
		assertTrue(c.get(4) == 10);
	}
	
	@Test
	public void testAssignWithMaskEqualsThan0()
	{
		mask.set(0);
		c.assign(mask, d);
		assertTrue(c.get(4) == 10);
	}
	
	@Test
	public void testAssignWithMaskLessThan0()
	{
		mask.set(-1);
		c.assign(mask, d);
		assertFalse(c.get(4) == 10);
	}
	
	@Test
	public void testAbs()
	{
		c.set(-40);
		c.abs();
		assertTrue(c.get(5) == 40);
	}
	
	@Test
	public void testAdd()
	{
		c.set(5, 10);
		d.set(5, 10);
		c.add(d);
		assertTrue(c.get(5) == 20);
	}
	
	@Test
	public void testSub()
	{
		c.set(5, 10);
		d.set(5, 9);
		c.sub(d);
		assertTrue(c.get(5) == 1);
	}
	
	@Test
	public void testMul()
	{
		c.set(5, 5);
		d.set(5, 4);
		c.mul(d);
		assertTrue(c.get(5) == 20);
	}
	
	@Test
	public void testDiv()
	{
		c.set(5, 20);
		d.set(5, 5);
		c.div(d);
		assertTrue(c.get(5) == 4);
	}
	
	@Test
	public void testSum()
	{
		c.set(4);
		assertTrue(c.sum() == 40);
	}
	
	@Test
	public void testProd()
	{
		c.set(4);
		d.set(4);
		assertTrue(c.prod(d) == 160);
	}
	
	@Test
	public void testNorm()
	{
		c.set(5, 5);
		assertTrue(c.norm() == 5.0);
	}
	
	@Test
	public void testNormalize()
	{
		c.set(5, 5);
		c.normalize();
		assertTrue(c.get(5) == 1);
	}
	
	@Test
	public void testMax()
	{
		c.set(5, 6);
		d.set(5, 7);
		c.max(d);
		assertTrue(c.get(5) == 7);
	}
	
	@Test
	public void testMin()
	{
		c.set(5, 6);
		d.set(5, 7);
		c.min(d);
		assertTrue(c.get(5) == 6);
	}
}
