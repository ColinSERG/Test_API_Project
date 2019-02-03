package src.unit_tests;

import org.junit.Assert;
import src.util.JsonUtil;
import org.junit.Test;

public class TestJsonUtil {

	@Test
	public void testValidParseJsonString() {
		
		JsonUtil jsonUtil = new JsonUtil();
		String response = jsonUtil.parseJsonString("{'reg':'BAX6253','model':'Clio','year':2001,'make':'Renault'}", "reg");
		Assert.assertEquals("Verify correct value returned.", response, "BAX6253");
	}
	
	@Test
	public void testInCorrectNameParseJsonString() {
		
		JsonUtil jsonUtil = new JsonUtil();
		String response = jsonUtil.parseJsonString("{'reg':'BAX6253','model':'Clio','year':2001,'make':'Renault'}", "nonexistent");
		Assert.assertNull("Verify null is returned", response);
	}
	
	@Test
	public void testInValidParseJsonString() {
		
		JsonUtil jsonUtil = new JsonUtil();
		String response = jsonUtil.parseJsonString("{'reg'}", "reg");
		Assert.assertNull("Verify null is returned", response);
	}
	

}
