package com.hackoeur.jglm;

import java.util.Random;

import org.junit.Assert;

/**
 * @author James Royalty
 */
public class JglmTesting {
	public static final float DEFAULT_EQUALS_TOL = 0.000001f;
	
	public static final Random RAND = new Random();
	
	public static void assertFloatsEqualDefaultTol(final float expected, final float actual) {
		Assert.assertEquals(expected, actual, DEFAULT_EQUALS_TOL);
	}
}
