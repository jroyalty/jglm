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

import java.nio.Buffer;
import java.nio.FloatBuffer;

/**
 * A single precision floating point matrix.
 * 
 * <p>JGLM stores matrices in <em>column-major</em> order.  This means that the vectors
 * of the matrix are stored in the columns.  This is the order which OpenGL
 * and GLSL require, but is the opposite of how C/C++ store 2D arrays in memory.
 * 
 * <p>For example, if you create a 3x3 matrix containing the vectors 
 * <code>v1 = &lt;1, 2, 3&gt;</code>, <code>v2 = &lt;4, 5, 6&gt;</code> 
 * and <code>v3 = &lt;7, 8, 9&gt;</code> then conceptual layout will be:
 * <pre>
 *   v1 v2 v3
 * | 1  4  7 |
 * | 2  5  8 |
 * | 3  6  9 |</pre>
 * 
 * Furthermore, when the matrix is returned as a contiguous block of memory 
 * (either as an array or a {@link Buffer}) the layout will be:
 * <pre>
 * | 1 2 3 4 5 6 7 8 9 |
 * </pre>
 * 
 * @author James Royalty
 */
public interface Mat {
	int getNumRows();
	
	int getNumColumns();
	
	<T extends Vec> T getColumn(int columnIndex);
	
	<T extends Vec> Iterable<T> getColumns();
	
	boolean isIdentity();
	
	boolean isZero();
	
	FloatBuffer getBuffer();
	
	boolean equalsWithEpsilon(Mat obj);
	
	boolean equalsWithEpsilon(Mat obj, float epsilon);
}
