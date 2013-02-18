package com.hackoeur.jglm;

import java.util.Random;

import org.junit.Assert;

import com.hackoeur.jglm.support.Compare;

/**
 * @author James Royalty
 */
public class JglmTesting {
	public static final float DEFAULT_EQUALS_TOL = 0.000001f;
	
	public static final Random RAND = new Random();
	
	public static void assertFloatsEqualDefaultTol(final float expected, final float actual) {
		Assert.assertTrue("Expected <" + expected + "> but got <" + actual + ">", Compare.equals(expected, actual, DEFAULT_EQUALS_TOL));
	}
}
