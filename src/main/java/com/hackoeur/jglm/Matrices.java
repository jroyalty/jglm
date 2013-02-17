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
 * @author James Royalty
 */
public final class Matrices
{
	public static Mat4 perspective(final float fovy, final float aspect, final float zNear, final float zFar) {
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
	
	public static Mat4 frustum(final float left, final float right, final float bottom, final float top, final float nearVal, final float farVal) {
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
	
	public static Mat4 ortho(final float left, final float right, final float bottom, final float top, final float zNear, final float zFar) {
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
	
	public static Mat4 ortho2d(final float left, final float right, final float bottom, final float top) {
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
}
