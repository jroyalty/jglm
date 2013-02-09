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
package com.hackoeur.jglm;

import java.nio.FloatBuffer;

import com.hackoeur.jglm.buffer.BufferAllocator;
import com.hackoeur.jglm.buffer.BufferAllocatorFactory;
import com.hackoeur.jglm.support.FastMath;

/**
 * @author James Royalty 
 */
abstract class AbstractVec implements Vec {
	private static final BufferAllocator BUFFER_ALLOCATOR = BufferAllocatorFactory.getInstance();
	
	@Override
	public final float getLength() {
		final float sqrt = (float) FastMath.sqrtFast( getLengthSquared() );
		final float diff = FastMath.abs(1.0f - sqrt);
		
		if (diff <= EPSILON) { // FIXME: Should I be doing this here?!
			return 1f;
		} else {
			return sqrt;
		}
	}
	
	protected FloatBuffer allocateFloatBuffer() {
		return BUFFER_ALLOCATOR.allocateFloatBuffer( getDimensions() );
	}
}
