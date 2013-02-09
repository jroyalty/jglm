package com.hackoeur.jglm;

import org.junit.Assert;
import org.junit.Test;

import com.hackoeur.jglm.support.FastMath;

/**
 * @author James Royalty
 */
public class InvSqrtTest {
	@Test
	public void testFastInvSqrt() {
        final float x = 133511f;
        final float fastInvSqrt = FastMath.invSqrtFast(x);
        final float computedInvSqrt = (float) (1 / StrictMath.sqrt(x));
        
        Assert.assertEquals(fastInvSqrt, computedInvSqrt, 0.000000001f);
	}
	
	@Test
	public void testFastSqrt() {
		final float x = 133511f;
        final float fastSqrt = FastMath.sqrtFast(x);
        final float computedSqrt = (float) StrictMath.sqrt(x);
        
        Assert.assertEquals(fastSqrt, computedSqrt, 0.0001f);
	}
}
