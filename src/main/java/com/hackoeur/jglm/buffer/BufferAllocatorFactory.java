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
import java.util.ServiceLoader;

/**
 * Gets an instance of a {@link BufferAllocator} using the standard Java SPI
 * mechanism.  (See {@link ServiceLoader} for more info.)
 * 
 * @author James Royalty
 */
public class BufferAllocatorFactory {
	/**
	 * Default allocator that uses the <code>allocate()</code> method on 
	 * the buffers themselves.
	 */
	private static class DefaultBufferAllocator implements BufferAllocator {
		@Override
		public ByteBuffer allocateByteBuffer(int sizeInBytes) {
			return ByteBuffer.allocate(sizeInBytes);
		}

		@Override
		public FloatBuffer allocateFloatBuffer(int sizeInFloats) {
			return FloatBuffer.allocate(sizeInFloats);
		}
	};
	
	private static final ServiceLoader<BufferAllocator> LOADER;
	private static final BufferAllocator DEFAULT_INSTANCE;
	
	static {
		// Load providers, if any.
		LOADER = ServiceLoader.load(BufferAllocator.class);
		
		// We want the first instance only, if one exists.
		BufferAllocator saved = null;
		for (final BufferAllocator inst : LOADER) {
			saved = inst;
			break;
		}

		// If we didn't find any providers then use our default instance.
		if (saved == null) {
			DEFAULT_INSTANCE = new DefaultBufferAllocator();
		} else {
			DEFAULT_INSTANCE = saved;
		}
	}
	
	/**
	 * @return an allocator, never {@code null}
	 */
	public static final BufferAllocator getInstance() {
		return DEFAULT_INSTANCE;
	}
}
