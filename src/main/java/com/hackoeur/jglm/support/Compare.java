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
 * @author James Royalty
 */
public final class Compare {
	/** Absolute epsilon value. */
	public static final float ABS_EPSILON;
	
	/** Relative epsilon for vectors. */
	public static final float VEC_EPSILON;
	
	/** Relative epsilon for matrices. */
	public static final float MAT_EPSILON;
	
	static {
		String s = System.getProperty("jglm.absEpsilon", "1.192092896e-07");
		ABS_EPSILON = Float.parseFloat(s);
		
		s = System.getProperty("jglm.vecEpsilon", "0.00001");
		VEC_EPSILON = Float.parseFloat(s);
		
		s = System.getProperty("jglm.matEpsilon", "0.00001");
		MAT_EPSILON = Float.parseFloat(s);
	}
}
