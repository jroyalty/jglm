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
import com.hackoeur.jglm.support.Compare;

/**
 * @author James Royalty
 */
abstract class AbstractMat implements Mat {
	private static final BufferAllocator BUFFER_ALLOCATOR = BufferAllocatorFactory.getInstance();
	
	@Override
	public boolean equalsWithEpsilon(final Mat obj) {
		return equalsWithEpsilon(obj, Compare.MAT_EPSILON);
	}
	
	protected FloatBuffer allocateFloatBuffer() {
		return BUFFER_ALLOCATOR.allocateFloatBuffer( getNumRows() * getNumColumns() );
	}
}
