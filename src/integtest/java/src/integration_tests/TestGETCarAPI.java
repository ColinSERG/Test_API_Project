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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import spark.Spark;
import src.api.Main;
import src.integration_tests.utils.*;
import org.junit.Assert;

public class TestGETCarAPI extends TestUtil {

	private final static String address = "http://localhost:4567";
	@Rule
	public TestName name = new TestName();

	@BeforeClass
	public static void beforeClass() throws IOException, InterruptedException {
		Main.main(null);
		URL url = new URL(address);
		waitForAPIUp(url);
		
		
		url = new URL(address + "/car");
		HashMap<String, String> requestProp = new HashMap<String, String>();
		requestProp.put("Content-Type", "application/json");
		
		
		String data = "{'reg':'BAX6253','model':'Clio','year':2001,'make':'Renault'}";
		outputInfoMessages("Populating 1st car " + data);
		ResponseDTO resp = request("POST", url, requestProp, data);
		Assert.assertEquals("Verify correct response message.", "\"Car has been added.\"", resp.getBody());
		Assert.assertEquals("Verify correct response code.", 200, resp.getStatus());
		
		
		data = "{'reg':'EFZ3564','model':'Impreza','year':2018,'make':'Subaru'}";
		outputInfoMessages("Populating 2nd car " + data);
		ResponseDTO resp2 = request("POST", url, requestProp, data);
		Assert.assertEquals("Verify correct response message.", "\"Car has been added.\"", resp2.getBody());
		Assert.assertEquals("Verify correct response code.", 200, resp2.getStatus());
		
	}

	
	@Test 
	public void retrieveListCars() throws IOException {
		
		outputInfoMessages("Start Test - " + name.getMethodName());
		URL url = new URL(address + "/cars");
		ResponseDTO resp = request("GET", url, new HashMap<String, String>(), "");
		Assert.assertEquals("Verify correct response code.", 200, resp.getStatus());
		JsonParser parser = new JsonParser();
		JsonArray array = parser.parse(resp.getBody()).getAsJsonArray();
		Assert.assertEquals("Verify 2 JSON resopnses are returned",2, array.size());
		JsonElement array0 = array.get(0);
		JsonElement array1 = array.get(1);
		
		Assert.assertNotEquals("Verify the regs are different", array0.getAsJsonObject().get("reg").getAsString(),array1.getAsJsonObject().get("reg").getAsString());
	
		for(JsonElement value:array) {
			if(value.getAsJsonObject().get("reg").getAsString().equals("EFZ3564")) {
				
				String make = value.getAsJsonObject().get("make").getAsString();
				String model = value.getAsJsonObject().get("model").getAsString();
				int year = value.getAsJsonObject().get("year").getAsInt();
				Assert.assertEquals("Verify correct make.", "Subaru", make);
				Assert.assertEquals("Verify correct model.", "Impreza", model);
				Assert.assertEquals("Verify correct year.", 2018, year);
			}
			else {
				String reg = value.getAsJsonObject().get("reg").getAsString();
				String make = value.getAsJsonObject().get("make").getAsString();
				String model = value.getAsJsonObject().get("model").getAsString();
				int year = value.getAsJsonObject().get("year").getAsInt();
				Assert.assertEquals("Verify correct reg.", "BAX6253", reg);
				Assert.assertEquals("Verify correct make.", "Renault", make);
				Assert.assertEquals("Verify correct model.", "Clio", model);
				Assert.assertEquals("Verify correct year.", 2001, year);
			}
		}
		
	}
	
	@Test 
	public void retrieveSpecificCar() throws IOException {
		
		outputInfoMessages("Start Test - " + name.getMethodName());
		URL url = new URL(address + "/car/EFZ3564");
		ResponseDTO resp = request("GET", url, new HashMap<String, String>(), "");
		Assert.assertEquals("Verify correct response code.", 200, resp.getStatus());
		JsonParser parser = new JsonParser();
		JsonElement jsonTree = parser.parse(resp.getBody());
		String reg = jsonTree.getAsJsonObject().get("reg").getAsString();
		String make = jsonTree.getAsJsonObject().get("make").getAsString();
		String model = jsonTree.getAsJsonObject().get("model").getAsString();
		int year = jsonTree.getAsJsonObject().get("year").getAsInt();
		Assert.assertEquals("Verify correct reg.", "EFZ3564", reg);
		Assert.assertEquals("Verify correct make.", "Subaru", make);
		Assert.assertEquals("Verify correct model.", "Impreza", model);
		Assert.assertEquals("Verify correct year.", 2018, year);
		
	}
	
	@AfterClass
	public static void afterClass() throws MalformedURLException {
		Spark.stop();
		URL url = new URL(address);
		waitForAPIDown(url);
	}
}
