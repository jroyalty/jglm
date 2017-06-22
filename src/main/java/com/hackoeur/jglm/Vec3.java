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
import com.hackoeur.jglm.support.FastMath;

/**
 * @author James Royalty
 */
public final class Vec3 extends AbstractVec {
	public static final Vec3 VEC3_ZERO = new Vec3();
	
	final float x, y, z;
	
	public Vec3() {
		this.x = 0f;
		this.y = 0f;
		this.z = 0f;
	}
	
	public Vec3(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3(final Vec3 vec) {
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	@Override
	public int getDimensions() {
		return 3;
	}

	@Override
	public float getLengthSquared() {
		return x * x + y * y + z * z;
	}
	
	public Vec3 getUnitVector() {
		final float sqLength = getLengthSquared();
		final float invLength = FastMath.invSqrtFast(sqLength);
		
		return new Vec3(x * invLength, y * invLength, z * invLength);
	}
	
	public Vec3 getNegated() {
		return new Vec3(-x, -y, -z);
	}
	
	public Vec3 add(final Vec3 vec) {
		return new Vec3( x + vec.x, y + vec.y, z + vec.z );
	}
	
	public Vec3 subtract(final Vec3 vec) {
		return new Vec3( x - vec.x, y - vec.y, z - vec.z );
	}
	
	public Vec3 multiply(final Mat3 mat) {
		return new Vec3(
				mat.m00 * x + mat.m01 * y + mat.m02 * z,
				mat.m10 * x + mat.m11 * y + mat.m12 * z,
				mat.m20 * x + mat.m21 * y + mat.m22 * z
		);
	}
	
	public Vec3 multiply(final float scalar) {
		return new Vec3( x * scalar, y * scalar, z * scalar );
	}
	
	public Vec3 scale(final float scalar) {
		return multiply(scalar);
	}
	
	/**
	 * @return A new vector where every value of the original vector has
	 * been multiplied with the corresponding value of the given vector.
	 */
	public Vec3 scale(final Vec3 vec) {
		return new Vec3(
				this.x * vec.x,
				this.y * vec.y,
				this.z * vec.z
		);
	}
	
	public float dot(final Vec3 vec) {
		return this.x * vec.x + this.y * vec.y + this.z * vec.z;
	}
	
	public Vec3 cross(final Vec3 vec) {
		return new Vec3(
				this.y * vec.z - vec.y * this.z,
				this.z * vec.x - vec.z * this.x,
				this.x * vec.y - vec.x * this.y
		);
	}
	
	/**
	 * @param vec
	 * @return the angle between this and the given vector, in <em>radians</em>.
	 */
	public float angleInRadians(final Vec3 vec) {
		final float dot = dot(vec);
		final float lenSq = FastMath.sqrtFast( getLengthSquared() * vec.getLengthSquared() );
		return (float) FastMath.acos( dot / lenSq );
	}
	
	public Vec3 lerp(final Vec3 vec, final float amount) {
		final float diff = 1f - amount;
		return new Vec3(
				(diff*this.x + amount*vec.x),
				(diff*this.y + amount*vec.y),
				(diff*this.z + amount*vec.z)
		);
	}
	
	public Vec4 toDirection() {
		return new Vec4(x, y, z, 0f);
	}
	
	public Vec4 toPoint() {
		return new Vec4(x, y, z, 1f);
	}
	
	@Override
	public FloatBuffer getBuffer() {
		final FloatBuffer buffer = allocateFloatBuffer();
		final int startPos = buffer.position();
		
		buffer.put(x).put(y).put(z);
		
		buffer.position(startPos);
		
		return buffer;
	}
	/**
	 * Get the coordinates of this Vec3 as a float array.
	 *
	 * @return new float[]{x, y, z};
	 */
	public float[] getArray() {
		return new float[]{x, y, z};
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
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
		if (!(obj instanceof Vec3)) {
			return false;
		}
		
		final Vec3 other = (Vec3) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x)) {
			return false;
		}
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) {
			return false;
		}
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean equalsWithEpsilon(final Vec obj, final float epsilon) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof Vec3)) {
			return false;
		}
		
		final Vec3 other = (Vec3) obj;
		
		return Compare.equals(x, other.x, epsilon)
				&& Compare.equals(y, other.y, epsilon)
				&& Compare.equals(z, other.z, epsilon);
	}

	@Override
	public String toString() {
		return new StringBuilder()
			.append(getClass().getSimpleName())
			.append("{")
			.append(String.format("%8.5f %8.5f %8.5f", x, y, z))
			.append("}")
			.toString();
	}
}
