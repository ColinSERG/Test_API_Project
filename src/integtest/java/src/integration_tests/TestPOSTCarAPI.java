package src.integration_tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import spark.Spark;
import src.api.Main;
import src.integration_tests.utils.*;
import org.junit.Assert;

public class TestPOSTCarAPI extends TestUtil {

	private final static String address = "http://localhost:4567";
	@Rule
	public TestName name = new TestName();

	@BeforeClass
	public static void beforeClass() throws IOException, InterruptedException {
		Main.main(null);
	}

	@Test
	public void postMissingParameters() throws MalformedURLException {

		URL url = new URL(address);
		waitForAPI(url);
		String data = "{'model':'Clio','year':2001,'make':'Renault'}";
		ResponseDTO resp;
		outputInfoMessages("Start Test - " + name.getMethodName());
		url = new URL(address + "/car");

		HashMap<String, String> requestProp = new HashMap<String, String>();
		requestProp.put("Content-Type", "application/json");

		resp = request("POST", url, requestProp, data);
		Assert.assertEquals("Verify correct response code.", 400, resp.getStatus());
		String expectedBody = "Parameters reg, make, model and year must be supplied";
		Assert.assertTrue("Verify correct response message.", resp.getBody().contains(expectedBody));		

	}

	@Test
	public void postInvalidYear() throws MalformedURLException {
		
		URL url = new URL(address);
		waitForAPI(url);
		String data = "{'reg':'QAX 6253','model':'Clio','year':2qwe001,'make':'Renault'}";
		ResponseDTO resp;
		outputInfoMessages("Start Test - " + name.getMethodName());
		url = new URL(address + "/car");
		HashMap<String, String> requestProp = new HashMap<String, String>();
		requestProp.put("Content-Type", "application/json");

		resp = request("POST", url, requestProp, data);
		Assert.assertEquals("Verify correct response code.", 400, resp.getStatus());
		String expectedBody = "Parameter reg must be a number.";
		Assert.assertTrue("Verify correct response message.", resp.getBody().contains(expectedBody));
	}

	@Test
	public void postValidData() throws MalformedURLException {
		
		URL url = new URL(address);
		waitForAPI(url);
		String data = "{'reg':'QAX 6253','model':'Clio','year':2001,'make':'Renault'}";
		ResponseDTO resp;
		outputInfoMessages("Start Test - " + name.getMethodName());
		url = new URL(address + "/car");
		HashMap<String, String> requestProp = new HashMap<String, String>();
		requestProp.put("Content-Type", "application/json");

		resp = request("POST", url, requestProp, data);
		Assert.assertEquals("Verify correct response message.", "\"Car has been added.\"", resp.getBody());
		Assert.assertEquals("Verify correct response code.", 200, resp.getStatus());

	}

	@AfterClass
	public static void afterClass() {
		Spark.stop();
	}
}
