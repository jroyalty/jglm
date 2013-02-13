package com.hackoeur.jglm.support;

/**
 * @author James Royalty [Jan 27, 2013]
 */
public interface VecLinearType<T extends VecLinearType<T>> extends LinearType<T> {
	float getLength();
	
	float getLengthSquared();
	
	T normalize();
	
	T dot(T vec);
	
	T cross(T vec);
}
