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

import com.hackoeur.jglm.support.Compare;
import com.hackoeur.jglm.support.Precision;

/**
 * A 4x4 matrix.
 * 
 * @author James Royalty
 */
public class Mat4 extends AbstractMat {
	/* ::-------------------------------------------------------------------------:: 
	 * COLUMN MAJOR LAYOUT: The first index indicates the COLUMN NUMBER.
	 * The second is the ROW NUMBER.
	 * 
	 * | A E I M |   | m00 m10 m20 m30 |
	 * | B F J N | = | m01 m11 m21 m31 |
	 * | C G K O |   | m02 m12 m22 m32 |
	 * | D H L P |   | m03 m13 m23 m33 |
	 */
	final float m00, m10, m20, m30;
	final float m01, m11, m21, m31;
	final float m02, m12, m22, m32;
	final float m03, m13, m23, m33;
	
	/**
	 * Creates a matrix with all elements equal to ZERO.
	 */
	public Mat4() {
		m00 = m10 = m20 = m30 = 0f;
		m01 = m11 = m21 = m31 = 0f;
		m02 = m12 = m22 = m32 = 0f;
		m03 = m13 = m23 = m33 = 0f;
	}
	
	@Override
	public int getNumRows() {
		return 4;
	}

	@Override
	public int getNumColumns() {
		return 4;
	}
	
	@Override
	public FloatBuffer getBuffer() {
		final FloatBuffer buffer = allocateFloatBuffer();
		final int startPos = buffer.position();
		
		// Col1
		buffer.put(m00)
			.put(m01)
			.put(m02)
			.put(m03);
		
		// Col 2
		buffer.put(m10)
			.put(m11)
			.put(m12)
			.put(m13);
		
		// Col 3
		buffer.put(m20)
			.put(m21)
			.put(m22)
			.put(m23);
		
		// Col 4
		buffer.put(m30)
			.put(m31)
			.put(m32)
			.put(m33);
		
		buffer.position(startPos);
		
		return buffer;
	}
	
	@Override
	public boolean isIdentity() {
		return Precision.equals(m00, 1f, Compare.MAT_EPSILON)
				&& Precision.equals(m11, 1f, Compare.MAT_EPSILON)
				&& Precision.equals(m22, 1f, Compare.MAT_EPSILON)
				&& Precision.equals(m33, 1f, Compare.MAT_EPSILON)
				
				&& Precision.equals(m01, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m02, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m03, 0f, Compare.ABS_EPSILON)
				
				&& Precision.equals(m10, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m12, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m13, 0f, Compare.ABS_EPSILON)
				
				&& Precision.equals(m20, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m21, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m23, 0f, Compare.ABS_EPSILON)
		
				&& Precision.equals(m30, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m31, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m32, 0f, Compare.ABS_EPSILON);
	}
	
	@Override
	public boolean isZero() {
		return Precision.equals(m00, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m01, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m02, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m03, 0f, Compare.ABS_EPSILON)
				
				&& Precision.equals(m10, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m11, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m12, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m13, 0f, Compare.ABS_EPSILON)
				
				&& Precision.equals(m20, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m21, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m22, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m23, 0f, Compare.ABS_EPSILON)
		
				&& Precision.equals(m30, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m31, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m32, 0f, Compare.ABS_EPSILON)
				&& Precision.equals(m33, 0f, Compare.ABS_EPSILON);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(m00);
		result = prime * result + Float.floatToIntBits(m01);
		result = prime * result + Float.floatToIntBits(m02);
		result = prime * result + Float.floatToIntBits(m03);
		result = prime * result + Float.floatToIntBits(m10);
		result = prime * result + Float.floatToIntBits(m11);
		result = prime * result + Float.floatToIntBits(m12);
		result = prime * result + Float.floatToIntBits(m13);
		result = prime * result + Float.floatToIntBits(m20);
		result = prime * result + Float.floatToIntBits(m21);
		result = prime * result + Float.floatToIntBits(m22);
		result = prime * result + Float.floatToIntBits(m23);
		result = prime * result + Float.floatToIntBits(m30);
		result = prime * result + Float.floatToIntBits(m31);
		result = prime * result + Float.floatToIntBits(m32);
		result = prime * result + Float.floatToIntBits(m33);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Mat4)) {
			return false;
		}
		Mat4 other = (Mat4) obj;
		if (Float.floatToIntBits(m00) != Float.floatToIntBits(other.m00)) {
			return false;
		}
		if (Float.floatToIntBits(m01) != Float.floatToIntBits(other.m01)) {
			return false;
		}
		if (Float.floatToIntBits(m02) != Float.floatToIntBits(other.m02)) {
			return false;
		}
		if (Float.floatToIntBits(m03) != Float.floatToIntBits(other.m03)) {
			return false;
		}
		if (Float.floatToIntBits(m10) != Float.floatToIntBits(other.m10)) {
			return false;
		}
		if (Float.floatToIntBits(m11) != Float.floatToIntBits(other.m11)) {
			return false;
		}
		if (Float.floatToIntBits(m12) != Float.floatToIntBits(other.m12)) {
			return false;
		}
		if (Float.floatToIntBits(m13) != Float.floatToIntBits(other.m13)) {
			return false;
		}
		if (Float.floatToIntBits(m20) != Float.floatToIntBits(other.m20)) {
			return false;
		}
		if (Float.floatToIntBits(m21) != Float.floatToIntBits(other.m21)) {
			return false;
		}
		if (Float.floatToIntBits(m22) != Float.floatToIntBits(other.m22)) {
			return false;
		}
		if (Float.floatToIntBits(m23) != Float.floatToIntBits(other.m23)) {
			return false;
		}
		if (Float.floatToIntBits(m30) != Float.floatToIntBits(other.m30)) {
			return false;
		}
		if (Float.floatToIntBits(m31) != Float.floatToIntBits(other.m31)) {
			return false;
		}
		if (Float.floatToIntBits(m32) != Float.floatToIntBits(other.m32)) {
			return false;
		}
		if (Float.floatToIntBits(m33) != Float.floatToIntBits(other.m33)) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean equalsWithEpsilon(final Mat obj, final float epsilon) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof Mat4)) {
			return false;
		}
		
		final Mat4 other = (Mat4) obj;
		
		return Precision.equals(m00, other.m00, epsilon)
				&& Precision.equals(m01, other.m01, epsilon)
				&& Precision.equals(m02, other.m02, epsilon)
				&& Precision.equals(m03, other.m03, epsilon)
				
				&& Precision.equals(m10, other.m10, epsilon)
				&& Precision.equals(m11, other.m11, epsilon)
				&& Precision.equals(m12, other.m12, epsilon)
				&& Precision.equals(m13, other.m13, epsilon)
				
				&& Precision.equals(m20, other.m20, epsilon)
				&& Precision.equals(m21, other.m21, epsilon)
				&& Precision.equals(m22, other.m22, epsilon)
				&& Precision.equals(m23, other.m23, epsilon)
				
				&& Precision.equals(m30, other.m30, epsilon)
				&& Precision.equals(m31, other.m31, epsilon)
				&& Precision.equals(m32, other.m32, epsilon)
				&& Precision.equals(m33, other.m33, epsilon);
	}

	public String toString() {
		return new StringBuilder()
			.append(getClass().getSimpleName())
			.append("{")
			.append("\n ").append(String.format("%8.5f %8.5f %8.5f %8.5f", m00, m10, m20, m30))
			.append("\n ").append(String.format("%8.5f %8.5f %8.5f %8.5f", m01, m11, m21, m31))
			.append("\n ").append(String.format("%8.5f %8.5f %8.5f %8.5f", m02, m12, m22, m32))
			.append("\n ").append(String.format("%8.5f %8.5f %8.5f %8.5f", m03, m13, m23, m33))
			.append("\n}")
			.toString();
	}
}
