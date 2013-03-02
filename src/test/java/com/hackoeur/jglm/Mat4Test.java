package com.hackoeur.jglm;

import java.nio.FloatBuffer;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author James Royalty
 */
public class Mat4Test {
	@Test
	public void testNewWithDiagonalValue() {
		Mat4 m1 = new Mat4(1f);
		Mat4 m2 = Mat4.MAT4_IDENTITY;
		Assert.assertEquals(m2, m1);
		
		Mat4 m3 = new Mat4(4f);
		JglmTesting.assertFloatsEqualDefaultTol(4f, m3.m00);
		JglmTesting.assertFloatsEqualDefaultTol(4f, m3.m11);
		JglmTesting.assertFloatsEqualDefaultTol(4f, m3.m22);
		JglmTesting.assertFloatsEqualDefaultTol(4f, m3.m33);
		
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m01);
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m02);
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m03);
		
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m10);
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m12);
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m13);
		
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m20);
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m21);
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m23);
		
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m30);
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m31);
		JglmTesting.assertFloatsEqualDefaultTol(0f, m3.m32);
	}
	
	@Test
	public void testNewWithVec4() {
		Vec4 v1 = new Vec4(1f, 2f, 3f, 4f);
		Vec4 v2 = new Vec4(5f, 6f, 7f, 8f);
		Vec4 v3 = new Vec4(9f, 10f, 11f, 12f);
		Vec4 v4 = new Vec4(13f, 14f, 15f, 16f);
		
		Mat4 m1 = new Mat4(v1, v2, v3, v4);
		Mat4 m2 = new Mat4(
				1f, 2f, 3f, 4f,
				5f, 6f, 7f, 8f,
				9f, 10f, 11f, 12f,
				13f, 14f, 15f, 16f
		);
		
		Assert.assertEquals(m2, m1);
	}
	
	@Test
	public void testNewWithArray() {
		Mat4 m1 = new Mat4(new float[] { 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f, 12f, 13f, 14f, 15f, 16f } );
		Mat4 m2 = new Mat4(
				1f, 2f, 3f, 4f,
				5f, 6f, 7f, 8f,
				9f, 10f, 11f, 12f,
				13f, 14f, 15f, 16f
		);
		
		Assert.assertEquals(m2, m1);
	}
	
	@Test
	public void testNewWithBuffer() {
		FloatBuffer buffer = FloatBuffer.allocate(16);
		buffer.put(1f).put(2f).put(3f).put(4f).put(5f).put(6f).put(7f).put(8f)
			.put(9f).put(10f).put(11f).put(12f).put(13f).put(14f).put(15f).put(16f);
		buffer.flip();
		
		Mat4 m1 = new Mat4(buffer);
		Mat4 m2 = new Mat4(
				1f, 2f, 3f, 4f,
				5f, 6f, 7f, 8f,
				9f, 10f, 11f, 12f,
				13f, 14f, 15f, 16f
		);
		
		Assert.assertEquals(m2, m1);
	}
	
	@Test
	public void testNewCopy() {
		Mat4 m1 = new Mat4(
				1f, 2f, 3f, 4f,
				5f, 6f, 7f, 8f,
				9f, 10f, 11f, 12f,
				13f, 14f, 15f, 16f
		);
		
		Mat4 m2 = new Mat4(m1);
		
		Assert.assertEquals(m1, m2);
	}
	
	@Test
	public void testGetColumn() {
		Mat4 m1 = new Mat4(
				1f, 2f, 3f, 4f,
				5f, 6f, 7f, 8f,
				9f, 10f, 11f, 12f,
				13f, 14f, 15f, 16f
				);

		Assert.assertEquals(new Vec4(1f, 2f, 3f, 4f), m1.getColumn(0));
		Assert.assertEquals(new Vec4(5f, 6f, 7f, 8f), m1.getColumn(1));
		Assert.assertEquals(new Vec4(9f, 10f, 11f, 12f), m1.getColumn(2));
		Assert.assertEquals(new Vec4(13f, 14f, 15f, 16f), m1.getColumn(3));
	}

	@Test
	public void testGetColumns() {
		Mat4 m1 = new Mat4(
				1f, 2f, 3f, 4f,
				5f, 6f, 7f, 8f,
				9f, 10f, 11f, 12f,
				13f, 14f, 15f, 16f
				);

		Iterable<Vec4> cols = m1.getColumns();
		Iterator<Vec4> iter = cols.iterator();

		Assert.assertEquals(new Vec4(1f, 2f, 3f, 4f), iter.next());
		Assert.assertEquals(new Vec4(5f, 6f, 7f, 8f), iter.next());
		Assert.assertEquals(new Vec4(9f, 10f, 11f, 12f), iter.next());
		Assert.assertEquals(new Vec4(13f, 14f, 15f, 16f), iter.next());
		Assert.assertFalse(iter.hasNext());
	}
	
	@Test
	public void testPureTranslate() {
		Vec3 vec = new Vec3(10.0f, 20.0f, 30.0f);
		Mat4 matTrans = Mat4.MAT4_IDENTITY.translate(vec);
		Mat4 expectedTrans = new Mat4(
				new Vec4(1f, 0f, 0f, 0f),
				new Vec4(0f, 1f, 0f, 0f),
				new Vec4(0f, 0f, 1f, 0f),
				new Vec4(vec, 1f)
		);
		
		Assert.assertEquals(expectedTrans, matTrans);
	}
	
	@Test
	public void testTranslate() {
		Mat4 m1 = new Mat4(
				1f, 2f, 3f, 4f,
				5f, 6f, 7f, 8f,
				9f, 10f, 11f, 12f,
				13f, 14f, 15f, 16f
		);
		
		Vec3 translation = new Vec3(10.0f, 1.0f, 2.0f);
		Mat4 matTrans = m1.translate(translation);
		
		Mat4 expectedTrans = new Mat4(
				1f, 2f, 3f, 4f,
				5f, 6f, 7f, 8f,
				9f, 10f, 11f, 12f,
				46f, 60f, 74f, 88f);
		
		Assert.assertEquals(expectedTrans, matTrans);
	}
	
	@Test
	public void testTranspose() {
		Mat4 m1 = new Mat4(
				1f, 2f, 3f, 4f,
				5f, 6f, 7f, 8f,
				9f, 10f, 11f, 12f,
				13f, 14f, 15f, 16f
		);
		
		Mat4 m1T = m1.transpose();
		Assert.assertFalse(m1.equals(m1T));
		
		Mat4 m1T_T = m1T.transpose();
		Assert.assertEquals(m1, m1T_T);
	}
}
