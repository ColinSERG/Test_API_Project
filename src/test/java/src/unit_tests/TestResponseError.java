package src.unit_tests;

import org.junit.Assert;

import src.exceptions.ResponseError;
import org.junit.Test;

public class TestResponseError {

	@Test
	public void testResponseError() {
		
		ResponseError resp = new ResponseError("Check error is thrown '%s'", "1");
		Assert.assertEquals("Verify correct message value is set.", "Check error is thrown '1'", resp.getMessage());	
	}

}
