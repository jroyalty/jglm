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

import com.hackoeur.jglm.support.Precision;

/**
 * @author James Royalty
 */
public class Mat3 extends AbstractMat {
	public static final Mat3 MAT3_ZERO = new Mat3();
	public static final Mat3 MAT3_IDENTITY = new Mat3(1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f);
	
	final float m00, m01, m02;
	final float m10, m11, m12;
	final float m20, m21, m22;
	
	public Mat3() {
		m00 = m01 = m02 = 0f;
		m10 = m11 = m12 = 0f;
		m20 = m21 = m22 = 0f;
	}
	
	public Mat3(
			final float x00, final float x01, final float x02,
			final float x10, final float x11, final float x12,
			final float x20, final float x21, final float x22) {
		this.m00 = x00;
		this.m01 = x01;
		this.m02 = x02;
		
		this.m10 = x10;
		this.m11 = x11;
		this.m12 = x12;
		
		this.m20 = x20;
		this.m21 = x21;
		this.m22 = x22;
	}
	
	public Mat3(final float[] mat) {
		assert mat.length >= 9 : "Invalid matrix array length";
		
		int i = 0;
		
		m00 = mat[i++];
		m01 = mat[i++];
		m02 = mat[i++];
		
		m10 = mat[i++];
		m11 = mat[i++];
		m12 = mat[i++];
		
		m20 = mat[i++];
		m21 = mat[i++];
		m22 = mat[i++];
	}
	
	public Mat3(final FloatBuffer buffer) {
		assert buffer.capacity() >= 9 : "Invalid matrix buffer capacity";
		
		final int startPos = buffer.position();
		
		m00 = buffer.get();
		m01 = buffer.get();
		m02 = buffer.get();
		
		m10 = buffer.get();
		m11 = buffer.get();
		m12 = buffer.get();
		
		m20 = buffer.get();
		m21 = buffer.get();
		m22 = buffer.get();
		
		buffer.position(startPos);
	}
	
	public Mat3(final Mat3 mat) {
		this.m00 = mat.m00;
		this.m01 = mat.m01;
		this.m02 = mat.m02;
		
		this.m10 = mat.m10;
		this.m11 = mat.m11;
		this.m12 = mat.m12;
		
		this.m20 = mat.m20;
		this.m21 = mat.m21;
		this.m22 = mat.m22;
	}
	
	@Override
	public int getNumRows() {
		return 3;
	}

	@Override
	public int getNumColumns() {
		return 3;
	}

	@Override
	public FloatBuffer getBuffer() {
		final FloatBuffer buffer = allocateFloatBuffer();
		final int startPos = buffer.position();
		
		buffer.put(m00);
		buffer.put(m01);
		buffer.put(m02);
		
		buffer.put(m10);
		buffer.put(m11);
		buffer.put(m12);
		
		buffer.put(m20);
		buffer.put(m21);
		buffer.put(m22);
		
		buffer.position(startPos);
		
		return buffer;
	}

	@Override
	public boolean isIdentity() {
		return Precision.equals(m00, 1f)
				&& Precision.equals(m11, 1f)
				&& Precision.equals(m22, 1f);
	}

	@Override
	public boolean isZero() {
		return Precision.equals(m00, 0f)
				&& Precision.equals(m01, 0f)
				&& Precision.equals(m02, 0f)
				
				&& Precision.equals(m10, 0f)
				&& Precision.equals(m11, 0f)
				&& Precision.equals(m12, 0f)
				
				&& Precision.equals(m20, 0f)
				&& Precision.equals(m21, 0f)
				&& Precision.equals(m22, 0f);
	}
	
	public Mat3 multiply(final Mat3 mat) {
		return new Mat3(
				this.m00 * mat.m00 + this.m10 * mat.m01 + this.m20 * mat.m02, // m00
				this.m01 * mat.m00 + this.m11 * mat.m01 + this.m21 * mat.m02, // m01
				this.m02 * mat.m00 + this.m12 * mat.m01 + this.m22 * mat.m02, // m02
				
				this.m00 * mat.m10 + this.m10 * mat.m11 + this.m20 * mat.m12, // m10
				this.m01 * mat.m10 + this.m11 * mat.m11 + this.m21 * mat.m12, // m11
				this.m02 * mat.m10 + this.m12 * mat.m11 + this.m22 * mat.m12, // m12
				
				this.m00 * mat.m20 + this.m10 * mat.m21 + this.m20 * mat.m22, // m20
				this.m01 * mat.m20 + this.m11 * mat.m21 + this.m21 * mat.m22, // m21
				this.m02 * mat.m20 + this.m12 * mat.m21 + this.m22 * mat.m22  // m22
		);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(m00);
		result = prime * result + Float.floatToIntBits(m01);
		result = prime * result + Float.floatToIntBits(m02);
		result = prime * result + Float.floatToIntBits(m10);
		result = prime * result + Float.floatToIntBits(m11);
		result = prime * result + Float.floatToIntBits(m12);
		result = prime * result + Float.floatToIntBits(m20);
		result = prime * result + Float.floatToIntBits(m21);
		result = prime * result + Float.floatToIntBits(m22);
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
		if (!(obj instanceof Mat3)) {
			return false;
		}
		Mat3 other = (Mat3) obj;
		if (Float.floatToIntBits(m00) != Float.floatToIntBits(other.m00)) {
			return false;
		}
		if (Float.floatToIntBits(m01) != Float.floatToIntBits(other.m01)) {
			return false;
		}
		if (Float.floatToIntBits(m02) != Float.floatToIntBits(other.m02)) {
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
		if (Float.floatToIntBits(m20) != Float.floatToIntBits(other.m20)) {
			return false;
		}
		if (Float.floatToIntBits(m21) != Float.floatToIntBits(other.m21)) {
			return false;
		}
		if (Float.floatToIntBits(m22) != Float.floatToIntBits(other.m22)) {
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
		
		if (!(obj instanceof Mat3)) {
			return false;
		}
		
		final Mat3 other = (Mat3) obj;
		
		return Precision.equals(m00, other.m00, epsilon)
				&& Precision.equals(m01, other.m01, epsilon)
				&& Precision.equals(m02, other.m02, epsilon)
				
				&& Precision.equals(m10, other.m10, epsilon)
				&& Precision.equals(m11, other.m11, epsilon)
				&& Precision.equals(m12, other.m12, epsilon)
				
				&& Precision.equals(m20, other.m20, epsilon)
				&& Precision.equals(m21, other.m21, epsilon)
				&& Precision.equals(m22, other.m22, epsilon);
	}

	public String toString() {
		return new StringBuilder()
			.append(getClass().getSimpleName())
			.append("{")
			.append("\n ").append(String.format("%8.5f %8.5f %8.5f", m00, m01, m02))
			.append("\n ").append(String.format("%8.5f %8.5f %8.5f", m10, m11, m12))
			.append("\n ").append(String.format("%8.5f %8.5f %8.5f", m20, m21, m22))
			.append("\n}")
			.toString();
	}
}
