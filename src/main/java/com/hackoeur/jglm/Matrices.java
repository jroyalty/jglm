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

import com.hackoeur.jglm.support.FastMath;

/**
 * Utility methods that replace OpenGL and GLU matrix functions there were 
 * deprecated in GL 3.0.
 * 
 * @author James Royalty
 */
public final class Matrices {
	/**
	 * Creates a perspective projection matrix using field-of-view and 
	 * aspect ratio to determine the left, right, top, bottom planes.  This
	 * method is analogous to the now deprecated {@code gluPerspective} method.
	 * 
	 * @param fovy field of view angle, in degrees, in the {@code y} direction
	 * @param aspect aspect ratio that determines the field of view in the x 
	 * direction.  The aspect ratio is the ratio of {@code x} (width) to 
	 * {@code y} (height).
	 * @param zNear near plane distance from the viewer to the near clipping plane (always positive)
	 * @param zFar far plane distance from the viewer to the far clipping plane (always positive)
	 * @return
	 */
	public static final Mat4 perspective(final float fovy, final float aspect, final float zNear, final float zFar) {
		final float halfFovyRadians = (float) FastMath.toRadians( (fovy / 2.0f) );
		final float range = (float) FastMath.tan(halfFovyRadians) * zNear;
		final float left = -range * aspect;
		final float right = range * aspect;
		final float bottom = -range;
		final float top = range;
		
		return new Mat4(
				(2f * zNear) / (right - left), 0f, 0f, 0f,
				0f, (2f * zNear) / (top - bottom), 0f, 0f,
				0f, 0f, -(zFar + zNear) / (zFar - zNear), -1f,
				0f, 0f, -(2f * zFar * zNear) / (zFar - zNear), 0f
		);
	}
	
	/**
	 * Creates a perspective projection matrix (frustum) using explicit
	 * values for all clipping planes.  This method is analogous to the now
	 * deprecated {@code glFrustum} method.
	 * 
	 * @param left left vertical clipping plane
	 * @param right right vertical clipping plane
	 * @param bottom bottom horizontal clipping plane
	 * @param top top horizontal clipping plane
	 * @param nearVal distance to the near depth clipping plane (must be positive)
	 * @param farVal distance to the far depth clipping plane (must be positive)
	 * @return
	 */
	public static final Mat4 frustum(final float left, final float right, final float bottom, final float top, final float nearVal, final float farVal) {
		final float m00 = (2f * nearVal) / (right - left);
		final float m11 = (2f * nearVal) / (top - bottom);
		final float m20 = (right + left) / (right - left);
		final float m21 = (top + bottom) / (top - bottom);
		final float m22 = -(farVal + nearVal) / (farVal - nearVal);
		final float m23 = -1f;
		final float m32 = -(2f * farVal * nearVal) / (farVal - nearVal);
		
		return new Mat4(
				m00, 0f, 0f, 0f, 
				0f, m11, 0f, 0f, 
				m20, m21, m22, m23, 
				0f, 0f, m32, 0f
		);
	}
	
	/**
	 * Defines a viewing transformation.  This method is analogous to the now
	 * deprecated {@code gluLookAt} method.
	 * 
	 * @param eye position of the eye point
	 * @param center position of the reference point
	 * @param up direction of the up vector
	 * @return
	 */
	public static final Mat4 lookAt(final Vec3 eye, final Vec3 center, final Vec3 up) {
		final Vec3 f = center.subtract(eye).getUnitVector();
		Vec3 u = up.getUnitVector();
		final Vec3 s = f.cross(u).getUnitVector();
		u = s.cross(f);
		
		return new Mat4(
				s.x, u.x, -f.x, 0f,
				s.y, u.y, -f.y, 0f,
				s.z, u.z, -f.z, 0f,
				-s.dot(eye), -u.dot(eye), f.dot(eye), 1f
		);
	}
	
	/**
	 * Creates an orthographic projection matrix.  This method is analogous to the now
	 * deprecated {@code glOrtho} method.
	 * 
	 * @param left left vertical clipping plane
	 * @param right right vertical clipping plane
	 * @param bottom bottom horizontal clipping plane
	 * @param top top horizontal clipping plane
	 * @param zNear distance to nearer depth clipping plane (negative if the plane is to be behind the viewer)
	 * @param zFar distance to farther depth clipping plane (negative if the plane is to be behind the viewer)
	 * @return
	 */
	public static final Mat4 ortho(final float left, final float right, final float bottom, final float top, final float zNear, final float zFar) {
		final float m00 = 2f / (right - left);
		final float m11 = 2f / (top - bottom);
		final float m22 = -2f / (zFar - zNear);
		final float m30 = - (right + left) / (right - left);
		final float m31 = - (top + bottom) / (top - bottom);
		final float m32 = - (zFar + zNear) / (zFar - zNear);
		
		return new Mat4(
				m00, 0f, 0f, 0f, 
				0f, m11, 0f, 0f,
				0f, 0f, m22, 0f, 
				m30, m31, m32, 1f
		);
	}
	
	/**
	 * Creates a 2D orthographic projection matrix.  This method is analogous to the now
	 * deprecated {@code gluOrtho2D} method.
	 * 
	 * @param left left vertical clipping plane
	 * @param right right vertical clipping plane
	 * @param bottom bottom horizontal clipping plane
	 * @param top top horizontal clipping plane
	 * @return
	 */
	public static final Mat4 ortho2d(final float left, final float right, final float bottom, final float top) {
		final float m00 = 2f / (right - left);
		final float m11 = 2f / (top - bottom);
		final float m22 = -1f;
		final float m30 = - (right + left) / (right - left);
		final float m31 = - (top + bottom) / (top - bottom);
		
		return new Mat4(
				m00, 0f, 0f, 0f, 
				0f, m11, 0f, 0f, 
				0f, 0f, m22, 0f, 
				m30, m31, 0f, 1f
		);
	}

	/**
	 * Creates a rotation matrix for the given angle (in rad) around the given
	 * axis.
	 *
	 * @param phi The angle (in rad).
	 * @param axis The axis to rotate around. Must be a unit-axis.
	 * @return This matrix, rotated around the given axis.
	 */
	public static Mat4 rotate(final float phi, final Vec3 axis) {
		double rcos = FastMath.cos(phi);
		double rsin = FastMath.sin(phi);
		float x = axis.x;
		float y = axis.y;
		float z = axis.z;
		Vec4 v1 = new Vec4((float) (rcos + x * x * (1 - rcos)), (float) (z * rsin + y * x * (1 - rcos)), (float) (-y * rsin + z * x * (1 - rcos)), 0);
		Vec4 v2 = new Vec4((float) (-z * rsin + x * y * (1 - rcos)), (float) (rcos + y * y * (1 - rcos)), (float) (x * rsin + z * y * (1 - rcos)), 0);
		Vec4 v3 = new Vec4((float) (y * rsin + x * z * (1 - rcos)), (float) (-x * rsin + y * z * (1 - rcos)), (float) (rcos + z * z * (1 - rcos)), 0);
		Vec4 v4 = new Vec4(0, 0, 0, 1);
		return new Mat4(v1, v2, v3, v4);
	}

	public static Mat4 invert(final Mat4 matrix){

		final float a[][] = new float[][]{{matrix.m00, matrix.m10, matrix.m20, matrix.m30},
				{matrix.m01, matrix.m11, matrix.m21, matrix.m31},
				{matrix.m02, matrix.m12, matrix.m22, matrix.m32},
				{matrix.m03, matrix.m13, matrix.m23, matrix.m33}};
		final int n = 4;
		float[][] inverted = invert(a,
				n);
		return new Mat4(inverted[0][0], inverted[1][0], inverted[2][0], inverted[3][0],
				inverted[0][1], inverted[1][1], inverted[2][1], inverted[3][1],
				inverted[0][2], inverted[1][2], inverted[2][2], inverted[3][2],
				inverted[0][3], inverted[1][3], inverted[2][3], inverted[3][3]);
	}

	public static Mat3 invert(final Mat3 matrix){

		final float a[][] = new float[][]{{matrix.m00, matrix.m10, matrix.m20},
				{matrix.m01, matrix.m11, matrix.m21},
				{matrix.m02, matrix.m12, matrix.m22}};
		final int n = 3;
		float[][] inverted = invert(a,
				n);
		return new Mat3(inverted[0][0], inverted[1][0], inverted[2][0],
				inverted[0][1], inverted[1][1], inverted[2][1],
				inverted[0][2], inverted[1][2], inverted[2][2]);
	}

	private static float[][] invert(final float[][] a,
	                                final int n){
		float x[][] = new float[n][n];
		float b[][] = new float[n][n];
		int index[] = new int[n];

		for (int i = 0; i < n; ++i) {
			b[i][i] = 1;
		}
		// Transform the a into an upper triangle
		gaussian(a, index);
		// Update the a b[i][j] with the ratios stored
		for (int i = 0; i < n - 1; ++i){
			for (int j = i + 1; j < n; ++j){
				for (int k = 0; k < n; ++k){
					b[index[j]][k] -= a[index[j]][i] * b[index[i]][k];
				}
			}
		}
		// Perform backward substitutions
		for (int i = 0; i < n; ++i){
			x[n - 1][i] = b[index[n - 1]][i] / a[index[n - 1]][n - 1];
			for (int j = n - 2; j >= 0; --j){
				x[j][i] = b[index[j]][i];
				for (int k = j + 1; k < n; ++k){
					x[j][i] -= a[index[j]][k] * x[k][i];
				}
				x[j][i] /= a[index[j]][j];
			}
		}
		return x;
	}


	// Method to carry out the partial-pivoting Gaussian
	// elimination.  Here index[] stores pivoting order.
	private static void gaussian(float a[][],
	                             int index[]) {
		int n = index.length;
		float c[] = new float[n];
		// Initialize the index
		for (int i = 0; i < n; ++i) {
			index[i] = i;
		}
		// Find the rescaling factors, one from each row
		for (int i = 0; i < n; ++i){
			float c1 = 0;
			for (int j = 0; j < n; ++j){
				float c0 = Math.abs(a[i][j]);
				if (c0 > c1) {
					c1 = c0;
				}
			}
			c[i] = c1;
		}
		// Search the pivoting element from each column
		int k = 0;
		for (int j = 0; j < n - 1; ++j){
			float pi1 = 0;
			for (int i = j; i < n; ++i){
				float pi0 = Math.abs(a[index[i]][j]);
				pi0 /= c[index[i]];
				if (pi0 > pi1) {

					pi1 = pi0;
					k = i;
				}
			}
			// Interchange rows according to the pivoting order
			int itmp = index[j];
			index[j] = index[k];
			index[k] = itmp;
			for (int i = j + 1; i < n; ++i){
				float pj = a[index[i]][j] / a[index[j]][j];
				// Record pivoting ratios below the diagonal
				a[index[i]][j] = pj;

				// Modify other elements accordingly
				for (int l = j + 1; l < n; ++l){
					a[index[i]][l] -= pj * a[index[j]][l];
				}
			}
		}
	}
}