package src.unit_tests;

import org.junit.Assert;
import src.util.ParseInt;
import org.junit.Test;

public class TestParseInt {

	@Test
	public void testValidParseInt() {
		
		ParseInt parseInt = new ParseInt();
		int i = parseInt.parseInt("4");
		Assert.assertEquals("Verify correct int is returned", i, 4);
	}
	
	@Test
	public void testInValidParseInt() {
		
		ParseInt parseInt = new ParseInt();
		try {
			parseInt.parseInt("qaz");
			Assert.fail("NumberFormatException shoukd have been thrown.");
		}catch(NumberFormatException nfe) {
			Assert.assertTrue("NumberFormatException was thrown.",true);
		}
	}
	

}
