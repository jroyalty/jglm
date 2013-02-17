package com.hackoeur.jglm;

import java.nio.FloatBuffer;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.hackoeur.jglm.support.Compare;
import com.hackoeur.jglm.support.FastMath;
import com.hackoeur.jglm.support.Precision;

/**
 * @author James Royalty
 */
public class Vec3Test {
	@Test
	public void testMultiply() {
		Vec3 v1 = new Vec3(724362380f, -328511470f, 2144268067f).multiply(2.5f);
		Assert.assertEquals(new Vec3(1810905856f, -821278656f, 5360670208f), v1);
	}
	
	@Test
	public void testMultiplyMat() {
		Mat3 m1 = new Mat3(
				1f, 2f, 3f,
				4f, 5f, 6f,
				7f, 8f, 9f
		);
		
		Vec3 v1 = new Vec3(10.0f, 11.0f, 12.0f);
		Vec3 result = v1.multiply(m1);
		
		JglmTesting.assertFloatsEqualDefaultTol(68f, result.x);
		JglmTesting.assertFloatsEqualDefaultTol(167f, result.y);
		JglmTesting.assertFloatsEqualDefaultTol(266f, result.z);
	}
	
	@Test
	public void testGetLength() {
		Vec3 v1 = new Vec3(724362380f, -328511470f, 2144268067f);
		float len = (float) StrictMath.sqrt( 
				v1.getX() * v1.getX()
				+ v1.getY() * v1.getY()
				+ v1.getZ() * v1.getZ()
		);
		
		Assert.assertEquals(len, v1.getLength(), JglmTesting.DEFAULT_EQUALS_TOL);
	}
	
	@Test
	public void testGetLengthSquared() {
		Vec3 v1 = new Vec3(724362380f, -328511470f, 2144268067f);
		float len = v1.getX() * v1.getX()
				+ v1.getY() * v1.getY()
				+ v1.getZ() * v1.getZ();
		
		Assert.assertEquals(len, v1.getLengthSquared(), JglmTesting.DEFAULT_EQUALS_TOL);
	}
	
	@Test
	public void testGetUnitVector() {
		final Vec3 vec1 = new Vec3(1f, 2f, 3f);
		final Vec3 norm1 = vec1.getUnitVector();
		Assert.assertEquals(0.267261f, norm1.getX(), JglmTesting.DEFAULT_EQUALS_TOL);
		Assert.assertEquals(0.534522f, norm1.getY(), JglmTesting.DEFAULT_EQUALS_TOL);
		Assert.assertEquals(0.801784f, norm1.getZ(), JglmTesting.DEFAULT_EQUALS_TOL);
		
		final Vec3 vec2 = new Vec3(-2652735904120045568f, 1379645337739722752f, 1107497449448013824f);
		final Vec3 norm2 = vec2.getUnitVector();
		Assert.assertTrue(Precision.equals(norm2.getLength(), 1f, Compare.VEC_EPSILON));
		Assert.assertEquals(-0.831951, norm2.getX(), JglmTesting.DEFAULT_EQUALS_TOL);
		Assert.assertEquals(0.432685, norm2.getY(), JglmTesting.DEFAULT_EQUALS_TOL);
		Assert.assertEquals(0.347334, norm2.getZ(), JglmTesting.DEFAULT_EQUALS_TOL);
	}
	
	@Test
	public void testGetNegated() {
		final Vec3 v1 = new Vec3(-2652735904120045568f, 1379645337739722752f, 1107497449448013824f);
		final Vec3 neg = v1.getNegated();
		
		Assert.assertEquals(-v1.getX(), neg.getX(), JglmTesting.DEFAULT_EQUALS_TOL);
		Assert.assertEquals(-v1.getY(), neg.getY(), JglmTesting.DEFAULT_EQUALS_TOL);
		Assert.assertEquals(-v1.getZ(), neg.getZ(), JglmTesting.DEFAULT_EQUALS_TOL);
	}
	
	@Test
	public void testDot() {
		final Vec3 v1 = new Vec3(1f, 2f, 3f);
		final Vec3 v2 = new Vec3(4f, 5f, 6f);
		final float dot1 = v1.dot(v2);
		Assert.assertEquals(32f, dot1, JglmTesting.DEFAULT_EQUALS_TOL);
		
		final Vec3 v3 = new Vec3(724362380f, -328511470f, 2144268067f);
		final Vec3 v4 = new Vec3(1151420018f, 1006737463f, 1503816073f);
		final float dot2 = v3.dot(v4);
		Assert.assertEquals(3727905169090805760f, dot2, JglmTesting.DEFAULT_EQUALS_TOL);
	}
	
	@Test
	public void testCross() {
		final Vec3 v1 = new Vec3(1f, 2f, 3f);
		final Vec3 v2 = new Vec3(4f, 5f, 6f);
		final Vec3 cross1 = v1.cross(v2);
		Assert.assertEquals(-3f, cross1.getX(), JglmTesting.DEFAULT_EQUALS_TOL);
		Assert.assertEquals(6f, cross1.getY(), JglmTesting.DEFAULT_EQUALS_TOL);
		Assert.assertEquals(-3f, cross1.getZ(), JglmTesting.DEFAULT_EQUALS_TOL);
		
		final Vec3 v3 = new Vec3(724362380f, -328511470f, 2144268067f);
		final Vec3 v4 = new Vec3(1151420018f, 1006737463f, 1503816073f);
		final Vec3 cross2 = v3.cross(v4);
		
		Assert.assertEquals(-2652735904120045568f, cross2.getX(), JglmTesting.DEFAULT_EQUALS_TOL);
		Assert.assertEquals(1379645337739722752f, cross2.getY(), JglmTesting.DEFAULT_EQUALS_TOL);
		Assert.assertEquals(1107497449448013824f, cross2.getZ(), JglmTesting.DEFAULT_EQUALS_TOL);
	}
	
	@Test
	public void testAngle() {
		final Vec3 v1 = new Vec3(0f, 1f, 0f);
		final Vec3 v2 = new Vec3(1f, 0f, 0f);
		Assert.assertEquals(FastMath.toRadians(90d), v1.angleInRadians(v2), JglmTesting.DEFAULT_EQUALS_TOL);
	}
	
	@Test
	public void testEquals() {
		final Vec3 v1 = new Vec3(724362380f, -328511470f, 2144268067f);
		final Vec3 v2 = new Vec3(724362380f, -328511470f, 2144268067f);
		Assert.assertEquals(v1, v2);
		Assert.assertTrue(v1.equalsWithEpsilon(v2, JglmTesting.DEFAULT_EQUALS_TOL));
	}
		
	@Test
	public void testBuffer() {
		final Vec3 v1 = new Vec3(724362380f, -328511470f, 2144268067f);
		final FloatBuffer buffer = v1.getBuffer();
		
		JglmTesting.assertFloatsEqualDefaultTol(v1.getX(), buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(v1.getY(), buffer.get());
		JglmTesting.assertFloatsEqualDefaultTol(v1.getZ(), buffer.get());
	}
	
	@Ignore
	public void testCreatePerformance() {
		final long startTs = System.currentTimeMillis();
		int x = 0;
		int y = 0;
		int z = 0;
		Vec3 v1 = null;
		Vec3 v2 = null;
		
		
		for (long i=0; i<1000000000L; i++) {
			v1 = new Vec3(x+i, y+i, z+i);
			v2 = v1.multiply(i);
		}
		
		final long totalTime = System.currentTimeMillis() - startTs;
		
		System.out.println("Time in ms : " + totalTime);
		System.out.println("Time in sec: " + TimeUnit.MILLISECONDS.toSeconds(totalTime));
		System.out.println("Time in min: " + TimeUnit.MILLISECONDS.toMinutes(totalTime));
		
		System.out.println("v1: " + v1);
		System.out.println("v2: " + v2);
	}
}
