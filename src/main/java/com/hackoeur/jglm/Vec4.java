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
public final class Vec4 extends AbstractVec {
	public static final Vec4 VEC4_ZERO = new Vec4();
	
	final float x, y, z, w;
	
	public Vec4() {
		this.x = 0f;
		this.y = 0f;
		this.z = 0f;
		this.w = 0f;
	}
	
	public Vec4(final float x, final float y, final float z, final float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vec4(final Vec4 other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
		this.w = other.w;
	}
	
	public Vec4(final Vec3 other, final float w) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
		this.w = w;
	}
	
	@Override
	public int getDimensions() {
		return 4;
	}
	
	@Override
	public float getLengthSquared() {
		return x * x + y * y + z * z + w * w;
	}
	
	public Vec4 getUnitVector() {
		final float sqLength = getLengthSquared();
		final float invLength = FastMath.invSqrtFast(sqLength);
		
		Vec4 normalVec = new Vec4(x * invLength, y * invLength, z * invLength, w * invLength);
		return normalVec;
	}
	
	public Vec4 getNegated() {
		return new Vec4(-x, -y, -z, -w);
	}
	
	public Vec4 add(final Vec4 vec) {
		return new Vec4( x + vec.x, y + vec.y, z + vec.z, w + vec.w );
	}
	
	public Vec4 subtract(final Vec4 vec) {
		return new Vec4( x - vec.x, y - vec.y, z - vec.z, w - vec.w );
	}
	
	public Vec4 multiply(final float scalar) {
		return new Vec4( x * scalar, y * scalar, z * scalar, w * scalar );
	}
	
	public Vec4 scale(final float scalar) {
		return multiply(scalar);
	}
	
	/**
	 * @return A new vector where every value of the original vector has
	 * been multiplied with the corresponding value of the given vector.
	 */
	public Vec4 scale(final Vec4 vec) {
		return new Vec4(
				this.x * vec.x,
				this.y * vec.y,
				this.z * vec.z,
				this.w * vec.w
		);
	}
	
	public float dot(final Vec4 vec) {
		return this.x * vec.x + this.y * vec.y + this.z * vec.z + this.w * vec.w;
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
	
	public float getW() {
		return w;
	}
	
	@Override
	public FloatBuffer getBuffer() {
		final FloatBuffer buffer = allocateFloatBuffer();
		final int startPos = buffer.position();
		
		buffer.put(x).put(y).put(z).put(x);
		
		buffer.position(startPos);
		
		return buffer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(w);
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
		if (!(obj instanceof Vec4)) {
			return false;
		}
		Vec4 other = (Vec4) obj;
		if (Float.floatToIntBits(w) != Float.floatToIntBits(other.w)) {
			return false;
		}
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
		
		if (!(obj instanceof Vec4)) {
			return false;
		}
		
		Vec4 other = (Vec4) obj;
		
		return Compare.equals(x, other.x, epsilon)
				&& Compare.equals(y, other.y, epsilon)
				&& Compare.equals(z, other.z, epsilon)
				&& Compare.equals(w, other.w, epsilon);
	}

	public String toString() {
		return new StringBuilder()
			.append(getClass().getSimpleName())
			.append("{")
			.append(x).append(", ")
			.append(y).append(", ")
			.append(z).append(", ")
			.append(w)
			.append("}")
			.toString();
	}
}
