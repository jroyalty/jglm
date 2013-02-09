package com.hackoeur.jglm;

import java.nio.FloatBuffer;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author James Royalty [Feb 3, 2013]
 */
public class Mat3Test {
	@Test
	public void testSize() {
		Mat3 m1 = new Mat3();
		Assert.assertEquals(3, m1.getNumRows());
		Assert.assertEquals(3, m1.getNumColumns());
	}
	
	@Test
	public void testZero() {
		Assert.assertTrue(Mat3.MAT3_ZERO.isZero());
		Assert.assertFalse(Mat3.MAT3_IDENTITY.isZero());
	}
	
	@Test
	public void testIdentity() {
		Assert.assertTrue(Mat3.MAT3_IDENTITY.isIdentity());
		Assert.assertFalse(Mat3.MAT3_IDENTITY.isZero());
		
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
	public void testMultiply() {
		Mat3 m1 = new Mat3(new float [] {
				123f, 0f, 0f,
				0f, 123f, 0f,
				0f, 0f, 123f
		});
		
		Mat3 m2 = new Mat3(new float [] {
				456f, 0f, 0f,
				0f, 456f, 0f,
				0f, 0f, 456f
		});
		
		Mat m3 = m1.multiply(m2);
		FloatBuffer buffer = m3.getBuffer();
		JglmTesting.assertFloatsEqualDefaultTol(56088f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(56088f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(0f, buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(56088f, buffer.get());
		
		Mat3 m4 = new Mat3(
				1f, 2f, 3f,
				4f, 5f, 6f,
				7f, 8f, 9f
		);
		
		Mat3 m5 = m4.multiply(m2);
		Mat3 m6 = new Mat3(
				456f, 912f, 1368f,
				1824f, 2280f, 2736f,
				3192f, 3648f, 4104f
		);
		
		Assert.assertEquals(m6, m5);
	}
}
