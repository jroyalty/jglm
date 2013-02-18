/* Copyright (C) 2013 James L. Royalty
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hackoeur.jglm.support;

/**
 * <strong>NOTE:</strong> Some of the code in this class has been pulled from the 
 * Apache Commons-Math library.  That library is covered by the Apache v2
 * license.  See the <code>NOTICE.TXT</code> file for more information.
 * 
 * @author James Royalty
 */
public final class Compare {
	/** Offset to order signed double numbers lexicographically. */
    private static final int SGN_MASK_FLOAT = 0x80000000; // From Commons-Math.
    
	/** Absolute epsilon value. */
	public static final float ABS_EPSILON;
	
	/** Relative epsilon for vectors. */
	public static final float VEC_EPSILON;
	
	/** Relative epsilon for matrices. */
	public static final float MAT_EPSILON;
	
	static {
		ABS_EPSILON = JglmConfig.getFloatProperty("absEpsilon", 1.192092896e-07f);
		VEC_EPSILON = JglmConfig.getFloatProperty("vecRelEpsilon", 0.00001f);
		MAT_EPSILON = JglmConfig.getFloatProperty("matRelEpsilon", 0.00001f);
	}
	
	
	public static boolean equalsZero(final float a) {
		/* Pulled from Precision.java in Commons-Math. */
		
		return equalsUlps(a, 0f, 1) || FastMath.abs(a) <= ABS_EPSILON;
	}
	
	public static boolean equals(final float a, final float b, final float relativeEpsilon) {
		return equals(a, b, ABS_EPSILON, relativeEpsilon);
	}
	
	public static boolean equals(final float a, final float b, final float absoluteEpsilon, final float relativeEpsilon) {
		/* Abstracted from http://randomascii.wordpress.com/2012/02/25/comparing-floating-point-numbers-2012-edition/ */
		
		// Short-cut.
		if (equalsUlps(a, b, 1)) {
            return true;
        }
		
		final float diff = FastMath.abs(a - b);
		
		// Short-cut for comparing with ZERO.
		if (diff <= absoluteEpsilon) {
			return true;
		}
		
		final float absA = FastMath.abs(a);
		final float absB = FastMath.abs(b);
		
		final float largest = (absB > absA) ? absB : absA;
		
		return ( diff <= (largest * relativeEpsilon) );
	}
	
	public static boolean equalsUlps(final float a, final float b, final int maxUlps) {
		/* Pulled from Precision.java in Commons-Math. */
		
		int xInt = Float.floatToIntBits(a);
        int yInt = Float.floatToIntBits(b);

        // Make lexicographically ordered as a two's-complement integer.
        if (xInt < 0) {
            xInt = SGN_MASK_FLOAT - xInt;
        }
        if (yInt < 0) {
            yInt = SGN_MASK_FLOAT - yInt;
        }

        final boolean isEqual = FastMath.abs(xInt - yInt) <= maxUlps;

        return isEqual && !Float.isNaN(a) && !Float.isNaN(b);
	}
}
