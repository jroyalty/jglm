package com.hackoeur.jglm;

import java.nio.FloatBuffer;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author James Royalty [Feb 3, 2013]
 */
public class Mat3Test {
	@Test
	public void testNewWithDiagonalValue() {
		Mat3 m1 = new Mat3(1f);
		Mat3 m2 = Mat3.MAT3_IDENTITY;
		Assert.assertEquals(m2, m1);
		
		Mat3 m3 = new Mat3(4f);
		JglmTesting.assertFloatsEqualDefaultTol(4f, m3.m00);
		JglmTesting.assertFloatsEqualDefaultTol(4f, m3.m11);
		JglmTesting.assertFloatsEqualDefaultTol(4f, m3.m22);
		
		
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m01);
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m02);
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m10);
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m12);
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m20);
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m21);
	}
	
	@Test
	public void testNewWithVec3() {
		Vec3 v1 = new Vec3(1f, 2f, 3f);
		Vec3 v2 = new Vec3(4f, 5f, 6f);
		Vec3 v3 = new Vec3(7f, 8f, 9f);
		
		Mat3 m1 = new Mat3(v1, v2, v3);
		Mat3 m2 = new Mat3(
				1f, 2f, 3f,
				4f, 5f, 6f,
				7f, 8f, 9f
		);
		
		Assert.assertEquals(m2, m1);
	}
	
	@Test
	public void testNewWithArray() {
		Mat3 m1 = new Mat3(new float[] { 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f });
		Mat3 m2 = new Mat3(
				1f, 2f, 3f,
				4f, 5f, 6f,
				7f, 8f, 9f
		);
		
		Assert.assertEquals(m2, m1);
	}
	
	@Test
	public void testNewWithBuffer() {
		FloatBuffer buffer = FloatBuffer.allocate(9);
		buffer.put(1f).put(2f).put(3f).put(4f).put(5f).put(6f).put(7f).put(8f).put(9f);
		buffer.flip();
		
		Mat3 m1 = new Mat3(buffer);
		Mat3 m2 = new Mat3(
				1f, 2f, 3f,
				4f, 5f, 6f,
				7f, 8f, 9f
		);
		
		Assert.assertEquals(m2, m1);
	}
	
	@Test
	public void testNewCopy() {
		Mat3 m1 = new Mat3(
				1f, 2f, 3f,
				4f, 5f, 6f,
				7f, 8f, 9f
		);
		
		Mat3 m2 = new Mat3(m1);
		
		Assert.assertEquals(m1, m2);
	}
	
	@Test
	public void testSize() {
		Mat3 m1 = new Mat3();
		Assert.assertEquals(3, m1.getNumRows());
		Assert.assertEquals(3, m1.getNumColumns());
	}
	
	@Test
	public void testGetColumn() {
		Mat3 m1 = new Mat3(
				1f, 2f, 3f,
				4f, 5f, 6f,
				7f, 8f, 9f
		);
		
		Assert.assertEquals(new Vec3(1f, 2f, 3f), m1.getColumn(0));
		Assert.assertEquals(new Vec3(4f, 5f, 6f), m1.getColumn(1));
		Assert.assertEquals(new Vec3(7f, 8f, 9f), m1.getColumn(2));
	}
	
	@Test
	public void testGetColumns() {
		Mat3 m1 = new Mat3(
				1f, 2f, 3f,
				4f, 5f, 6f,
				7f, 8f, 9f
		);
		
		Iterable<Vec3> cols = m1.getColumns();
		Iterator<Vec3> iter = cols.iterator();
		
		Assert.assertEquals(new Vec3(1f, 2f, 3f), iter.next());
		Assert.assertEquals(new Vec3(4f, 5f, 6f), iter.next());
		Assert.assertEquals(new Vec3(7f, 8f, 9f), iter.next());
		Assert.assertFalse(iter.hasNext());
	}
	
	@Test
	public void testZero() {
		Assert.assertTrue(Mat3.MAT3_ZERO.isZero());
		Assert.assertFalse(Mat3.MAT3_IDENTITY.isZero());
		
		Mat3 m1 = new Mat3(0f);
		Assert.assertTrue(m1.isZero());
		Assert.assertEquals(Mat3.MAT3_ZERO, m1);
		Assert.assertEquals(Mat3.MAT3_ZERO, new Mat3());
	}
	
	@Test
	public void testIdentity() {
		Assert.assertTrue(Mat3.MAT3_IDENTITY.isIdentity());
		Assert.assertFalse(Mat3.MAT3_IDENTITY.isZero());
		
		Assert.assertEquals(Mat3.MAT3_IDENTITY, new Mat3(1f));
		Assert.assertTrue(new Mat3(1f).isIdentity());
		
		FloatBuffer buffer = Mat3.MAT3_IDENTITY.getBuffer();
		JglmTesting.assertFloatsEqualDefaultTol(1f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(1f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(1f, buffer.get());
		
		// Should be able to get a "fresh" buffer.
		buffer = Mat3.MAT3_IDENTITY.getBuffer();
		JglmTesting.assertFloatsEqualDefaultTol(1f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(1f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(1f, buffer.get());
	}
	
	@Test
	public void testMultiplyScalar() {
		Mat3 m1 = new Mat3(1f, 2f, 3f, 0f, 0f, 0f, 0f, 0f, 0f);
		System.out.println(m1);
		System.out.println(m1.multiply(2));
	}
	
	@Test
	public void testMultiply() {
		Mat3 m1 = new Mat3(new float [] {
				1f, 2f, 3f,
				4f, 5f, 6f,
				7f, 8f, 9f
		});
		
		Mat3 m2 = new Mat3(new float [] {
				10f, 11f, 12f,
				13f, 14f, 15f,
				16f, 17f, 18f
		});
		
		Mat3 mult = m1.multiply(m2);
		
		Mat3 expected = new Mat3(
				138f, 171f, 204f,
				174f, 216f, 258f, 
				210f, 261f, 312f
		);
		
		Assert.assertEquals(expected, mult);
	}
	
	@Test
	public void testMultiplyVec() {
		Mat3 m1 = new Mat3(
				1f, 2f, 3f,
				4f, 5f, 6f,
				7f, 8f, 9f
		);
		
		Vec3 v1 = new Vec3(10.0f, 11.0f, 12.0f);
		
		Vec3 result = m1.multiply(v1);
		
		JglmTesting.assertFloatsEqualDefaultTol(138f, result.x);
		JglmTesting.assertFloatsEqualDefaultTol(171f, result.y);
		JglmTesting.assertFloatsEqualDefaultTol(204f, result.z);
	}
	
	@Test
	public void testTranspose() {
		final Mat3 m1 = new Mat3(
				1f, 2f, 3f,
				4f, 5f, 6f,
				7f, 8f, 9f
		);
		
		final Mat3 m1T = m1.transpose();
		
		final FloatBuffer buffer = m1T.getBuffer();
		JglmTesting.assertFloatsEqualDefaultTol(1f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(4f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(7f, buffer.get());
		
		JglmTesting.assertFloatsEqualDefaultTol(2f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(5f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(8f, buffer.get());
		
		JglmTesting.assertFloatsEqualDefaultTol(3f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(6f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(9f, buffer.get());
		
		final Mat3 m1T_T = m1T.transpose();
		Assert.assertEquals(m1, m1T_T);
	}
	
	@Test
	public void testDeterminant() {
		Mat3 m1 = new Mat3(
				-2f, 2f, 3f,
				-1f, 1f, 3f,
				2f, 0f, -1f
		);
		
		JglmTesting.assertFloatsEqualDefaultTol(6f, m1.determinant());
	}
}
