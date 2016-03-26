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
 * A 4x4 matrix.
 * 
 * @author James Royalty
 */
public final class Mat4 extends AbstractMat {
	public static final Mat4 MAT4_ZERO = new Mat4();
	public static final Mat4 MAT4_IDENTITY = new Mat4(1.0f);
	
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
	
	/**
	 * Creates a matrix with the given value along the diagonal.
	 * 
	 * @param diagonalValue
	 */
	public Mat4(final float diagonalValue) {
		m00 = m11 = m22 = m33 = diagonalValue;
		m01 = m02 = m03 = 0f;
		m10 = m12 = m13 = 0f;
		m20 = m21 = m23 = 0f;
		m30 = m31 = m32 = 0f;
	}
	
	/**
	 * Create a matrix using the given 3-elements vectors as <em>columns</em>.  The fourth 
	 * element of each given vector will be set to zero.
	 * 
	 * @param col0 vector for the first column
	 * @param col1 vector for the second column
	 * @param col2 vector for the third column
	 * @param col3 vector for the fourth column
	 */
	public Mat4(final Vec3 col0, final Vec3 col1, final Vec3 col2, final Vec3 col3) {
		this.m00 = col0.x; this.m10 = col1.x; this.m20 = col2.x; this.m30 = col3.x;
		this.m01 = col0.y; this.m11 = col1.y; this.m21 = col2.y; this.m31 = col3.y;
		this.m02 = col0.z; this.m12 = col1.z; this.m22 = col2.z; this.m32 = col3.z;
		this.m03 = 0f;     this.m13 = 0f;     this.m23 = 0f;     this.m33 = 0f;
	}
	
	/**
	 * Create a matrix using the given 4-elements vectors as <em>columns</em>.
	 * 
	 * @param col0 vector for the first column
	 * @param col1 vector for the second column
	 * @param col2 vector for the third column
	 * @param col3 vector for the fourth column
	 */
	public Mat4(final Vec4 col0, final Vec4 col1, final Vec4 col2, final Vec4 col3) {
		this.m00 = col0.x; this.m10 = col1.x; this.m20 = col2.x; this.m30 = col3.x;
		this.m01 = col0.y; this.m11 = col1.y; this.m21 = col2.y; this.m31 = col3.y;
		this.m02 = col0.z; this.m12 = col1.z; this.m22 = col2.z; this.m32 = col3.z;
		this.m03 = col0.w; this.m13 = col1.w; this.m23 = col2.w; this.m33 = col3.w;
	}
	
	/**
	 * Creates a matrix using successive 4-tuples as <em>columns</em>.
	 *
	 * @param x00 first column, x
	 * @param x01 first column, y
	 * @param x02 first column, z
	 * @param x03 first column, w
	 * @param x10 second column, x
	 * @param x11 second column, y
	 * @param x12 second column, z
	 * @param x13 second column, w
	 * @param x20 third column, x
	 * @param x21 third column, y
	 * @param x22 third column, z
	 * @param x23 third column, w
	 * @param x30 fourth column, x
	 * @param x31 fourth column, y
	 * @param x32 fourth column, z
	 * @param x33 fourth column, w
	 */
	public Mat4(
			final float x00, final float x01, final float x02, final float x03,
			final float x10, final float x11, final float x12, final float x13,
			final float x20, final float x21, final float x22, final float x23,
			final float x30, final float x31, final float x32, final float x33) {
		// Col 1
		this.m00 = x00;
		this.m01 = x01;
		this.m02 = x02;
		this.m03 = x03;
		
		// Col 2
		this.m10 = x10;
		this.m11 = x11;
		this.m12 = x12;
		this.m13 = x13;
		
		// Col 3
		this.m20 = x20;
		this.m21 = x21;
		this.m22 = x22;
		this.m23 = x23;
		
		// Col 4
		this.m30 = x30;
		this.m31 = x31;
		this.m32 = x32;
		this.m33 = x33;
	}
	
	/**
	 * Creates a matrix using successive 4-tuples as <em>columns</em>.
	 * 
	 * @param mat array containing <em>at least</em> 16 elements.  It's okay if
	 * the given array is larger than 16 elements; those elements will be ignored.
	 */
	public Mat4(final float[] mat) {
		assert mat.length >= 16 : "Invalid matrix array length";
		
		int i = 0;
		
		// Col 1
		m00 = mat[i++];
		m01 = mat[i++];
		m02 = mat[i++];
		m03 = mat[i++];
		
		// Col 2
		m10 = mat[i++];
		m11 = mat[i++];
		m12 = mat[i++];
		m13 = mat[i++];
		
		// Col 3
		m20 = mat[i++];
		m21 = mat[i++];
		m22 = mat[i++];
		m23 = mat[i++];
		
		// Col 4
		m30 = mat[i++];
		m31 = mat[i++];
		m32 = mat[i++];
		m33 = mat[i++];
	}
	
	/**
	 * Creates a matrix using successive 4-tuples as <em>columns</em>.  The semantics
	 * are the same as the float array constructor.
	 * 
	 * @param buffer
	 */
	public Mat4(final FloatBuffer buffer) {
		assert buffer.capacity() >= 16 : "Invalid matrix buffer length";
		
		final int startPos = buffer.position();
		
		// Col 1
		m00 = buffer.get();
		m01 = buffer.get();
		m02 = buffer.get();
		m03 = buffer.get();
		
		// Col 2
		m10 = buffer.get();
		m11 = buffer.get();
		m12 = buffer.get();
		m13 = buffer.get();
		
		// Col 3
		m20 = buffer.get();
		m21 = buffer.get();
		m22 = buffer.get();
		m23 = buffer.get();
		
		// Col 4
		m30 = buffer.get();
		m31 = buffer.get();
		m32 = buffer.get();
		m33 = buffer.get();
		
		buffer.position(startPos);
	}
	
	/**
	 * Creates a matrix that is a copy of the given matrix.
	 * 
	 * @param mat matrix to copy
	 */
	public Mat4(final Mat4 mat) {
		this.m00 = mat.m00;
		this.m01 = mat.m01;
		this.m02 = mat.m02;
		this.m03 = mat.m03;
		
		this.m10 = mat.m10;
		this.m11 = mat.m11;
		this.m12 = mat.m12;
		this.m13 = mat.m13;
		
		this.m20 = mat.m20;
		this.m21 = mat.m21;
		this.m22 = mat.m22;
		this.m23 = mat.m23;
		
		this.m30 = mat.m30;
		this.m31 = mat.m31;
		this.m32 = mat.m32;
		this.m33 = mat.m33;
	}
	
	@Override
	public int getNumRows() {
		return 4;
	}

	@Override
	public int getNumColumns() {
		return 4;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Vec> T getColumn(final int columnIndex) {
		assert columnIndex < 4 : "Invalid column index = " + columnIndex;
		
		switch (columnIndex) {
		case 0:
			return (T) new Vec4(m00, m01, m02, m03);
		case 1:
			return (T) new Vec4(m10, m11, m12, m13);
		case 2:
			return (T) new Vec4(m20, m21, m22, m23);
		case 3:
			return (T) new Vec4(m30, m31, m32, m33);
		default:
			throw new IllegalArgumentException("Invalid column index = " + columnIndex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Vec> Iterable<T> getColumns() {
		List<Vec4> cols = new ArrayList<Vec4>(4);
		
		cols.add(new Vec4(m00, m01, m02, m03));
		cols.add(new Vec4(m10, m11, m12, m13));
		cols.add(new Vec4(m20, m21, m22, m23));
		cols.add(new Vec4(m30, m31, m32, m33));
		
		return (Iterable<T>) cols;
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
		return Compare.equals(m00, 1f, Compare.MAT_EPSILON)
				&& Compare.equals(m11, 1f, Compare.MAT_EPSILON)
				&& Compare.equals(m22, 1f, Compare.MAT_EPSILON)
				&& Compare.equals(m33, 1f, Compare.MAT_EPSILON)
				
				&& Compare.equalsZero(m01)
				&& Compare.equalsZero(m02)
				&& Compare.equalsZero(m03)
				
				&& Compare.equalsZero(m10)
				&& Compare.equalsZero(m12)
				&& Compare.equalsZero(m13)
				
				&& Compare.equalsZero(m20)
				&& Compare.equalsZero(m21)
				&& Compare.equalsZero(m23)
		
				&& Compare.equalsZero(m30)
				&& Compare.equalsZero(m31)
				&& Compare.equalsZero(m32);
	}
	
	@Override
	public boolean isZero() {
		return Compare.equalsZero(m00)
				&& Compare.equalsZero(m01)
				&& Compare.equalsZero(m02)
				&& Compare.equalsZero(m03)
				
				&& Compare.equalsZero(m10)
				&& Compare.equalsZero(m11)
				&& Compare.equalsZero(m12)
				&& Compare.equalsZero(m13)
				
				&& Compare.equalsZero(m20)
				&& Compare.equalsZero(m21)
				&& Compare.equalsZero(m22)
				&& Compare.equalsZero(m23)
		
				&& Compare.equalsZero(m30)
				&& Compare.equalsZero(m31)
				&& Compare.equalsZero(m32)
				&& Compare.equalsZero(m33);
	}
        
        /**
         * Multiply this matrix with another and return the result.
         * @param other
         */
        public Mat4 multiply(final Mat4 right) {            
            float nm00 = this.m00 * right.m00 + this.m10 * right.m01 + this.m20 * right.m02 + this.m30 * right.m03;
            float nm01 = this.m01 * right.m00 + this.m11 * right.m01 + this.m21 * right.m02 + this.m31 * right.m03;
            float nm02 = this.m02 * right.m00 + this.m12 * right.m01 + this.m22 * right.m02 + this.m32 * right.m03;
            float nm03 = this.m03 * right.m00 + this.m13 * right.m01 + this.m23 * right.m02 + this.m33 * right.m03;
            float nm10 = this.m00 * right.m10 + this.m10 * right.m11 + this.m20 * right.m12 + this.m30 * right.m13;
            float nm11 = this.m01 * right.m10 + this.m11 * right.m11 + this.m21 * right.m12 + this.m31 * right.m13;
            float nm12 = this.m02 * right.m10 + this.m12 * right.m11 + this.m22 * right.m12 + this.m32 * right.m13;
            float nm13 = this.m03 * right.m10 + this.m13 * right.m11 + this.m23 * right.m12 + this.m33 * right.m13;
            float nm20 = this.m00 * right.m20 + this.m10 * right.m21 + this.m20 * right.m22 + this.m30 * right.m23;
            float nm21 = this.m01 * right.m20 + this.m11 * right.m21 + this.m21 * right.m22 + this.m31 * right.m23;
            float nm22 = this.m02 * right.m20 + this.m12 * right.m21 + this.m22 * right.m22 + this.m32 * right.m23;
            float nm23 = this.m03 * right.m20 + this.m13 * right.m21 + this.m23 * right.m22 + this.m33 * right.m23;
            float nm30 = this.m00 * right.m30 + this.m10 * right.m31 + this.m20 * right.m32 + this.m30 * right.m33;
            float nm31 = this.m01 * right.m30 + this.m11 * right.m31 + this.m21 * right.m32 + this.m31 * right.m33;
            float nm32 = this.m02 * right.m30 + this.m12 * right.m31 + this.m22 * right.m32 + this.m32 * right.m33;
            float nm33 = this.m03 * right.m30 + this.m13 * right.m31 + this.m23 * right.m32 + this.m33 * right.m33;
            
            return new Mat4(
                            nm00, nm01, nm02, nm03,
                            nm10, nm11, nm12, nm13,
                            nm20, nm21, nm22, nm23,
                            nm30, nm31, nm32, nm33
            );
        }
        
        /**
         * Subtract other matrix from this one and return the result ( this - right )
         * @param right
         */
        public Mat4 subtract(final Mat4 right) {
            return new Mat4(
                            m00 - right.m00, m01 - right.m01, m02 - right.m02, m03 - right.m03,
                            m10 - right.m10, m11 - right.m11, m12 - right.m12, m13 - right.m13,
                            m20 - right.m20, m21 - right.m21, m22 - right.m22, m23 - right.m23,
                            m30 - right.m30, m31 - right.m31, m32 - right.m32, m33 - right.m33
            );
        }
        
        /**
         * Add two matrices together and return the result
         * @param other
         */
        public Mat4 add(final Mat4 other) {
            return new Mat4(
                            m00 + other.m00, m01 + other.m01, m02 + other.m02, m03 + other.m03,
                            m10 + other.m10, m11 + other.m11, m12 + other.m12, m13 + other.m13,
                            m20 + other.m20, m21 + other.m21, m22 + other.m22, m23 + other.m23,
                            m30 + other.m30, m31 + other.m31, m32 + other.m32, m33 + other.m33
            );
        }

	public Vec4 multiply(final Vec4 right) {
		return new Vec4(this.m00 * right.x + this.m10 * right.y + this.m20 * right.z + this.m30 * right.w,
				this.m01 * right.x + this.m11 * right.y + this.m21 * right.z + this.m31 * right.w,
				this.m02 * right.x + this.m12 * right.y + this.m22 * right.z + this.m32 * right.w,
				this.m03 * right.x + this.m13 * right.y + this.m23 * right.z + this.m33 * right.w);
	}
	
	public Mat4 translate(final Vec3 translation) {
		Vec4 v0 = new Vec4(m00 * translation.x, m01 * translation.x, m02 * translation.x, m03 * translation.x);
		Vec4 v1 = new Vec4(m10 * translation.y, m11 * translation.y, m12 * translation.y, m13 * translation.y);
		Vec4 v2 = new Vec4(m20 * translation.z, m21 * translation.z, m22 * translation.z, m23 * translation.z);
		Vec4 v3 = new Vec4(m30, m31, m32, m33);
		
		Vec4 result = v0.add(v1).add(v2).add(v3);
		
		return new Mat4(
				m00, m01, m02, m03,
				m10, m11, m12, m13,
				m20, m21, m22, m23,
				result.x, result.y, result.z, result.w
		);
	}
	
	public Mat4 transpose() {
		return new Mat4(
				m00, m10, m20, m30,
				m01, m11, m21, m31,
				m02, m12, m22, m32,
				m03, m13, m23, m33
		);
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
		
		return Compare.equals(m00, other.m00, epsilon)
				&& Compare.equals(m01, other.m01, epsilon)
				&& Compare.equals(m02, other.m02, epsilon)
				&& Compare.equals(m03, other.m03, epsilon)
				
				&& Compare.equals(m10, other.m10, epsilon)
				&& Compare.equals(m11, other.m11, epsilon)
				&& Compare.equals(m12, other.m12, epsilon)
				&& Compare.equals(m13, other.m13, epsilon)
				
				&& Compare.equals(m20, other.m20, epsilon)
				&& Compare.equals(m21, other.m21, epsilon)
				&& Compare.equals(m22, other.m22, epsilon)
				&& Compare.equals(m23, other.m23, epsilon)
				
				&& Compare.equals(m30, other.m30, epsilon)
				&& Compare.equals(m31, other.m31, epsilon)
				&& Compare.equals(m32, other.m32, epsilon)
				&& Compare.equals(m33, other.m33, epsilon);
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
