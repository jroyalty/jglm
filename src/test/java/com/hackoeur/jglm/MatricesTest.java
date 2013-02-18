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

import org.junit.Test;

/**
 * @author James Royalty
 */
public class MatricesTest {
	@Test
	public void testPerspective() {
		Mat4 m1 = Matrices.perspective(60.0f, (640.0f/480.0f), 0.3f, 100.0f);
		
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
		Mat4 m1 = Matrices.frustum(-1.0f, 1.0f, -1.0f, 1.0f, 1.5f, 20.0f);
		System.out.println(m1);
		
		/*
		 test_frustrum:
   1.50000    0.00000    0.00000    0.00000
   0.00000    1.50000    0.00000    0.00000
   0.00000    0.00000   -1.16216   -3.24324
   0.00000    0.00000   -1.00000    0.00000
		 */
	}
	
	@Test
	public void testLookAt() {
		Mat4 m1 = Matrices.lookAt(
				new Vec3(1f, 2f, 3f),
				new Vec3(4f, 5f, 6f),
				new Vec3(7f, 8f, 9f)
		);
		
		System.out.println(m1);
		
		/*
		 test_lookAt:
   0.40825   -0.81650    0.40825   -0.00000
  -0.70711    0.00000    0.70711   -1.41421
  -0.57735   -0.57735   -0.57735    3.46410
   0.00000    0.00000    0.00000    1.00000
		 */
	}
	
	@Test
	public void testOrtho() {
		Mat4 m1 = Matrices.ortho(-1.0f, 1.0f, -1.0f, 1.0f, 1.5f, 20.0f);
		System.out.println(m1);
		/*
		 test_ortho:
   1.00000    0.00000    0.00000   -0.00000
   0.00000    1.00000    0.00000   -0.00000
   0.00000    0.00000   -0.10811   -1.16216
   0.00000    0.00000    0.00000    1.00000
		 */
	}
	
	@Test
	public void testOrtho2d() {
		Mat4 m1 = Matrices.ortho2d(-1.0f, 20.0f, -10.0f, 20.0f);
		System.out.println(m1);
		
		/*
		 2d:
   0.09524    0.00000    0.00000   -0.90476
   0.00000    0.06667    0.00000   -0.33333
   0.00000    0.00000   -1.00000    0.00000
   0.00000    0.00000    0.00000    1.00000
		 */
	}
}
