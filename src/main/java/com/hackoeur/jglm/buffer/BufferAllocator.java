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
package com.hackoeur.jglm.buffer;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Represents a strategy for allocating buffers.  A default, basic 
 * implementation is provided.  Users of JGLM can supply their own
 * allocator that integrates with their application's method of
 * buffer creation.  See {@link BufferAllocatorFactory} for details
 * on how.
 * 
 * <p>Note that JGLM doesn't particularly care whether or not the
 * returned buffer is <em>direct</em> or not.  User-provided implementations
 * may provide either.
 * 
 * @author James Royalty
 */
public interface BufferAllocator {
	/**
	 * Allocate a new {@link ByteBuffer}.  Whether the returned buffer is
	 * <em>direct</em> or not is implementation-specific.
	 * 
	 * @param size the size of the byte buffer.  This is the number of bytes
	 * the buffer is capable of holding.
	 * 
	 * @return a byte buffer ready for use. 
	 */
	ByteBuffer allocateByteBuffer(int sizeInBytes);
	
	/**
	 * Allocate a new {@link FloatBuffer}.  Whether the returned buffer is
	 * <em>direct</em> or not is implementation-specific.
	 * 
	 * @param size the number of {@code float}s the buffer is capable of holding.
	 * Note that this <em>is not</em> a measure in bytes.
	 * 
	 * @return a float buffer ready for use.
	 */
	FloatBuffer allocateFloatBuffer(int sizeInFloats);
}
