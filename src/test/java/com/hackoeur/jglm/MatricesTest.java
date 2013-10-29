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
import org.junit.Assert;
import org.junit.Test;

/**
 * @author James Royalty
 */
public class MatricesTest {

	@Test
	public void testPerspective() {
		Mat4 m1 = Matrices.perspective(60.0f, (640.0f / 480.0f), 0.3f, 100.0f);

		/*
		 test_perspective:
		 1.29904    0.00000    0.00000    0.00000
		 0.00000    1.73205    0.00000    0.00000
		 0.00000    0.00000   -1.00602   -0.60181
		 0.00000    0.00000   -1.00000    0.00000
		 */
	}

	@Test
	public void testFrustrum() {
		Mat4 got = Matrices.frustum(-1.0f, 1.0f, -1.0f, 1.0f, 1.5f, 20.0f);
		Mat4 expected = new Mat4(
				+1.50000000f, +0.00000000f, +0.00000000f, +0.00000000f,
				+0.00000000f, +1.50000000f, +0.00000000f, +0.00000000f,
				+0.00000000f, +0.00000000f, -1.16216218f, -1.00000000f,
				+0.00000000f, +0.00000000f, -3.24324322f, +0.00000000f
		);
		System.out.println(got);
		Assert.assertEquals(expected, got);
	}

	@Test
	public void testLookAt() {
		Mat4 got = Matrices.lookAt(
				new Vec3(1f, 2f, 3f),
				new Vec3(4f, 5f, 6f),
				new Vec3(7f, 8f, 9f)
		);
		Mat4 expected = new Mat4(
				+0.40824646f, -0.70710111f, -0.57734823f, +0.00000000f,
				-0.81649292f, +0.00000000f, -0.57734823f, +0.00000000f,
				+0.40824646f, +0.70710111f, -0.57734823f, +0.00000000f,
				-0.00000000f, -1.41420221f, +3.46408939f, +1.00000000f
		);
		System.out.println(got);
		Assert.assertEquals(expected, got);
	}

	@Test
	public void testOrtho() {
		Mat4 got = Matrices.ortho(-1.0f, 1.0f, -1.0f, 1.0f, 1.5f, 20.0f);
		Mat4 expected = new Mat4(
				+1.00000000f, +0.00000000f, +0.00000000f, +0.00000000f,
				+0.00000000f, +1.00000000f, +0.00000000f, +0.00000000f,
				+0.00000000f, +0.00000000f, -0.10810811f, +0.00000000f,
				-0.00000000f, -0.00000000f, -1.16216218f, +1.00000000f
		);
		System.out.println(got);
		Assert.assertEquals(expected, got);
	}

	@Test
	public void testOrtho2d() {
		Mat4 got = Matrices.ortho2d(-1.0f, 20.0f, -10.0f, 20.0f);
		Mat4 expected = new Mat4(
				+0.09523810f, +0.00000000f, +0.00000000f, +0.00000000f,
				+0.00000000f, +0.06666667f, +0.00000000f, +0.00000000f,
				+0.00000000f, +0.00000000f, -1.00000000f, +0.00000000f,
				-0.90476191f, -0.33333334f, +0.00000000f, +1.00000000f
		);
		System.out.println(got);
		Assert.assertEquals(expected, got);
	}

	@Test
	public void testRotate() {
		System.out.println("Rotate");
		Mat4 got, expected;
		got = Matrices.rotate((float) FastMath.PI, new Vec3(1, 0, 0));
		expected = new Mat4(
				+1.000000E+0f, +0.000000E+0f, +0.000000E+0f, +0.000000E+0f,
				+0.000000E+0f, -1.000000E+0f, -8.742278E-8f, +0.000000E+0f,
				+0.000000E+0f, +8.742278E-8f, -1.000000E+0f, +0.000000E+0f,
				+0.000000E+0f, +0.000000E+0f, +0.000000E+0f, +1.000000E+0f
		);
		Assert.assertEquals(expected, got);

		got = Matrices.rotate((float) FastMath.PI, new Vec3(0, 1, 0));
		expected = new Mat4(
				-1.000000E+0f, +0.000000E+0f, +8.742278E-8f, +0.000000E+0f,
				+0.000000E+0f, +1.000000E+0f, +0.000000E+0f, +0.000000E+0f,
				-8.742278E-8f, +0.000000E+0f, -1.000000E+0f, +0.000000E+0f,
				+0.000000E+0f, +0.000000E+0f, +0.000000E+0f, +1.000000E+0f
		);
		Assert.assertEquals(expected, got);

		got = Matrices.rotate((float) FastMath.PI, new Vec3(0, 0, 1));
		expected = new Mat4(
				-1.000000E+0f, -8.742278E-8f, +0.000000E+0f, +0.000000E+0f,
				+8.742278E-8f, -1.000000E+0f, +0.000000E+0f, +0.000000E+0f,
				+0.000000E+0f, +0.000000E+0f, +1.000000E+0f, +0.000000E+0f,
				+0.000000E+0f, +0.000000E+0f, +0.000000E+0f, +1.000000E+0f
		);
		Assert.assertEquals(expected, got);

		got = Matrices.rotate((float) FastMath.PI, new Vec3(1, 1, 1).getUnitVector());
		expected = new Mat4(
				-0.33333474f, +0.66666520f, +0.66666530f, +0.0000000E+0f,
				+0.66666530f, -0.33333474f, +0.66666520f, +0.0000000E+0f,
				+0.66666520f, +0.66666530f, -0.33333474f, +0.0000000E+0f,
				+0.00000000f, +0.00000000f, +0.00000000f, +1.0000000E+0f
		);
		Assert.assertEquals(expected, got);
	}

}
