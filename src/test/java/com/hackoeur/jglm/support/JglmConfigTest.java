package com.hackoeur.jglm.support;

import org.junit.Assert;
import org.junit.Test;

import com.hackoeur.jglm.Mat3;

/**
 * @author James Royalty
 */
public class JglmConfigTest {
	@Test
	public void testConfig() {
		System.setProperty("jglm.myProp", "test value");
		System.setProperty("jglm.forOverride", "this has been overridden");
		
		Assert.assertEquals("this is: first property", JglmConfig.getProperty("firstProperty"));
		Assert.assertEquals("test value", JglmConfig.getProperty("myProp"));
		Assert.assertEquals("this has been overridden", JglmConfig.getProperty("forOverride"));
		Assert.assertEquals(0.00002342f, JglmConfig.getFloatProperty("floatProperty", 0f), 0.00001f);
		Assert.assertFalse(JglmConfig.hasProperty("ignored"));
		
		Mat3 mat = JglmConfig.getInstancePropertyOrNull("aClass", Mat3.class);
		Assert.assertNotNull(mat);
		Assert.assertEquals(Mat3.MAT3_ZERO, mat);
	}
}
