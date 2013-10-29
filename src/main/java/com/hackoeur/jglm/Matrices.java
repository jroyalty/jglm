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

}
