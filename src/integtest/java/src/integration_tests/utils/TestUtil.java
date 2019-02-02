package src.integration_tests.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import spark.utils.IOUtils;
import org.junit.Assert;

//curl -X POST http://localhost:4567/car -H "Content-Type:application/json" --data '{reg:i21,model:h2i2,year:22001,make:fh2}'
//curl -X GET http://localhost:4567/car/i1
//curl -X GET http://localhost:4567/cars

public class TestUtil {

	public static void waitForAPI(URL url) {
		for (int retries = 1; retries < 3; retries++) {

			outputInfoMessages("Attempt " + retries + " to connect to " + url.toString());
			try {
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.getContent();
				int responseCode = conn.getResponseCode();
				if (responseCode == 200) {
					outputInfoMessages("200 response code returned system is ready.");
					return;
				} else {
					outputInfoMessages(responseCode + " response code returned system not yet ready.");
				}
			} catch (IOException e) {
				outputInfoMessages("Failed to make a successful connection to endpoint " + e.getMessage());
			}

			try {
				TimeUnit.SECONDS.sleep(1);
				outputInfoMessages("Waiting 1 second before retry.");
			} catch (InterruptedException e) {
				outputInfoMessages("Error thorwn when waiting 1 second before retry.");
			}

		}
		Assert.fail("Failed to make a successful connection to endpoint " + url.toString());

	}

	public static void outputInfoMessages(String text) {
		System.out.println("INFO:- " + text);
	}

	public static ResponseDTO request(String method, URL url, HashMap<String, String> requestProp, String body) {
		HttpURLConnection conn;
		ResponseDTO resp = new ResponseDTO();
	
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			
			for (Entry<String, String> prop : requestProp.entrySet())
			{
			    conn.setRequestProperty(prop.getKey(), prop.getValue());
			}
			
			if(body !=null && !body.isEmpty()) {
				byte[] outputInBytes = body.getBytes("UTF-8");
				OutputStream os = conn.getOutputStream();
				os.write( outputInBytes );    
				os.close();
			}
			conn.connect();
			
			try {
				resp.setBody(IOUtils.toString(conn.getInputStream()));
			} catch (IOException e) {
				resp.setBody(IOUtils.toString(conn.getErrorStream()));
			}
			
			resp.setStatus(conn.getResponseCode());
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return resp;
		
	}
	
	
}
