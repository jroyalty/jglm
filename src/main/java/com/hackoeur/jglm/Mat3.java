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
import java.util.ArrayList;
import java.util.List;

import com.hackoeur.jglm.support.Compare;

/**
 * A 3x3 matrix.
 * 
 * @author James Royalty
 */
public final class Mat3 extends AbstractMat {
	public static final Mat3 MAT3_ZERO = new Mat3();
	public static final Mat3 MAT3_IDENTITY = new Mat3(1.0f);
	
	/* ::-------------------------------------------------------------------------:: 
	 * COLUMN MAJOR LAYOUT: The first index indicates the COLUMN NUMBER.
	 * The second is the ROW NUMBER.
	 * 
	 * | A D G |   | m00 m10 m20 |
	 * | B E H | = | m01 m11 m21 |
	 * | C F I |   | m02 m12 m22 |
	 */
	final float m00, m10, m20;
	final float m01, m11, m21;
	final float m02, m12, m22;
	
	/**
	 * Creates a matrix with all elements equal to ZERO.
	 */
	public Mat3() {
		m00 = m10 = m20 = 0f;
		m01 = m11 = m21 = 0f;
		m02 = m12 = m22 = 0f;
	}
	
	/**
	 * Creates a matrix with the given value along the diagonal.
	 * 
	 * @param diagonalValue
	 */
	public Mat3(final float diagonalValue) {
		m00 = m11 = m22 = diagonalValue;
		m10 = m20 = 0f;
		m01 = m21 = 0f;
		m02 = m12 = 0f;
	}
	
	/**
	 * Create a matrix using the given vectors as <em>columns</em>. For example, 
	 * <pre>
	 * Mat3 m1 = new Mat3(
	 * 	new Vec3(1f, 2f, 3f), // first column
	 * 	new Vec3(4f, 5f, 6f), // second
	 * 	new Vec3(7f, 8f, 9f)  // third
	 * );</pre>
	 * 
	 * will create the following 3x3 matrix:
	 * <pre>
	 * | 1 4 7 |
	 * | 2 5 8 |
	 * | 3 6 9 |
	 * </pre>
	 * 
	 * @param col0 vector for the first column
	 * @param col1 vector for the second column
	 * @param col2 vector for the third column
	 */
	public Mat3(final Vec3 col0, final Vec3 col1, final Vec3 col2) {
		this.m00 = col0.x; this.m10 = col1.x; this.m20 = col2.x;
		this.m01 = col0.y; this.m11 = col1.y; this.m21 = col2.y;
		this.m02 = col0.z; this.m12 = col1.z; this.m22 = col2.z;
	}
	
	/**
	 * Creates a matrix using successive triples as <em>columns</em>.  For example,
	 * <pre>
	 * Mat3 m1 = new Mat3(
	 * 	1f, 2f, 3f, // first column
	 * 	4f, 5f, 6f, // second
	 * 	7f, 8f, 9f  // third
	 * );</pre>
	 * 
	 * will create the following 3x3 matrix:
	 * <pre>
	 * | 1 4 7 |
	 * | 2 5 8 |
	 * | 3 6 9 |
	 * </pre>
	 *
	 * @param x00 first column, x
	 * @param x01 first column, y
	 * @param x02 first column, z
	 * @param x10 second column, x
	 * @param x11 second column, y
	 * @param x12 second column, z
	 * @param x20 third column, x
	 * @param x21 third column, y
	 * @param x22 third column, z
	 */
	public Mat3(
			final float x00, final float x01, final float x02,
			final float x10, final float x11, final float x12,
			final float x20, final float x21, final float x22) {
		// Col 1
		this.m00 = x00;
		this.m01 = x01;
		this.m02 = x02;
		
		// Col 2
		this.m10 = x10;
		this.m11 = x11;
		this.m12 = x12;
		
		// Col 3
		this.m20 = x20;
		this.m21 = x21;
		this.m22 = x22;
	}
	
	/**
	 * Creates a matrix using successive triples as <em>columns</em>.  For example,
	 * <pre>
	 * Mat3 m1 = new Mat3(new float[] {
	 * 	1f, 2f, 3f, // first column
	 * 	4f, 5f, 6f, // second
	 * 	7f, 8f, 9f  // third
	 * });</pre>
	 * 
	 * will create the following 3x3 matrix:
	 * <pre>
	 * | 1 4 7 |
	 * | 2 5 8 |
	 * | 3 6 9 |
	 * </pre>
	 * 
	 * @param mat array containing <em>at least</em> 9 elements.  It's okay if
	 * the given array is larger than 9 elements; those elements will be ignored.
	 */
	public Mat3(final float[] mat) {
		assert mat.length >= 9 : "Invalid matrix array length";
		
		int i = 0;
		
		// Col 1
		m00 = mat[i++];
		m01 = mat[i++];
		m02 = mat[i++];
		
		// Col 2
		m10 = mat[i++];
		m11 = mat[i++];
		m12 = mat[i++];
		
		// Col 3
		m20 = mat[i++];
		m21 = mat[i++];
		m22 = mat[i++];
	}
	
	/**
	 * Creates a matrix using successive triples as <em>columns</em>.  The semantics
	 * are the same as the float array constructor.
	 * 
	 * @param buffer
	 */
	public Mat3(final FloatBuffer buffer) {
		assert buffer.capacity() >= 9 : "Invalid matrix buffer length";
		
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
	
	/**
	 * Creates a matrix that is a copy of the given matrix.
	 * 
	 * @param mat matrix to copy
	 */
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
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Vec> T getColumn(final int columnIndex) {
		assert columnIndex < 3 : "Invalid column index = " + columnIndex;
		
		switch (columnIndex) {
		case 0:
			return (T) new Vec3(m00, m01, m02);
		case 1:
			return (T) new Vec3(m10, m11, m12);
		case 2:
			return (T) new Vec3(m20, m21, m22);
		default:
			throw new IllegalArgumentException("Invalid column index = " + columnIndex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Vec> Iterable<T> getColumns() {
		List<Vec3> cols = new ArrayList<Vec3>(3);
		
		cols.add(new Vec3(m00, m01, m02));
		cols.add(new Vec3(m10, m11, m12));
		cols.add(new Vec3(m20, m21, m22));
		
		return (Iterable<T>) cols;
	}

	@Override
	public FloatBuffer getBuffer() {
		final FloatBuffer buffer = allocateFloatBuffer();
		final int startPos = buffer.position();
		
		// Col 1
		buffer.put(m00)
			.put(m01)
			.put(m02);
		
		// Col 2
		buffer.put(m10)
			.put(m11)
			.put(m12);
		
		// Col 3
		buffer.put(m20)
			.put(m21)
			.put(m22);
		
		buffer.position(startPos);
		
		return buffer;
	}

	@Override
	public boolean isIdentity() {
		return Compare.equals(m00, 1f, Compare.MAT_EPSILON)
				&& Compare.equals(m11, 1f, Compare.MAT_EPSILON)
				&& Compare.equals(m22, 1f, Compare.MAT_EPSILON)
				
				&& Compare.equalsZero(m01)
				&& Compare.equalsZero(m02)
				
				&& Compare.equalsZero(m10)
				&& Compare.equalsZero(m12)
				
				&& Compare.equalsZero(m20)
				&& Compare.equalsZero(m21);
	}

	@Override
	public boolean isZero() {
		return Compare.equalsZero(m00)
				&& Compare.equalsZero(m01)
				&& Compare.equalsZero(m02)
				
				&& Compare.equalsZero(m10)
				&& Compare.equalsZero(m11)
				&& Compare.equalsZero(m12)
				
				&& Compare.equalsZero(m20)
				&& Compare.equalsZero(m21)
				&& Compare.equalsZero(m22);
	}
	
	public Mat3 multiply(final float a) {
		return new Mat3(
				m00*a, m01*a, m02*a,
				m10*a, m11*a, m12*a,
				m20*a, m21*a, m22*a
		);
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
	
	/**
	 * This is the equivalent of <strong>this * vector</strong> (if we had operator
	 * overloading).  If you want <strong>vector * this</strong> then 
	 * see {@link Vec3#multiply(Mat3)}.
	 * 
	 * @param vec
	 * @return
	 */
	public Vec3 multiply(final Vec3 vec) {
		return new Vec3(
				m00 * vec.x + m10 * vec.y + m20 * vec.z,
				m01 * vec.x + m11 * vec.y + m21 * vec.z,
				m02 * vec.x + m12 * vec.y + m22 * vec.z
		);
	}
	
	public Mat3 transpose() {
		return new Mat3(
				m00, m10, m20,
				m01, m11, m21,
				m02, m12, m22
		);
	}
	
	public float determinant() {
		return m00 * (m11 * m22 - m12 * m21) - m01 * (m10 * m22 - m12 * m20) + m02 * (m10 * m21 - m11 * m20);
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
		
		return Compare.equals(m00, other.m00, epsilon)
				&& Compare.equals(m01, other.m01, epsilon)
				&& Compare.equals(m02, other.m02, epsilon)
				
				&& Compare.equals(m10, other.m10, epsilon)
				&& Compare.equals(m11, other.m11, epsilon)
				&& Compare.equals(m12, other.m12, epsilon)
				
				&& Compare.equals(m20, other.m20, epsilon)
				&& Compare.equals(m21, other.m21, epsilon)
				&& Compare.equals(m22, other.m22, epsilon);
	}

	public String toString() {
		return new StringBuilder()
			.append(getClass().getSimpleName())
			.append("{")
			.append("\n ").append(String.format("%8.5f %8.5f %8.5f", m00, m10, m20))
			.append("\n ").append(String.format("%8.5f %8.5f %8.5f", m01, m11, m21))
			.append("\n ").append(String.format("%8.5f %8.5f %8.5f", m02, m12, m22))
			.append("\n}")
			.toString();
	}
}
