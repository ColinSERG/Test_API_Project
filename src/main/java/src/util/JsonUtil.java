package src.util;

import com.google.gson.Gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import spark.ResponseTransformer;

public class JsonUtil {
	
	public String parseJsonString(String jsonString, String name) {
		try {
		JsonParser parser = new JsonParser();
		JsonElement jsonTree = parser.parse(jsonString);
		return jsonTree.getAsJsonObject().get(name).getAsString();
		}catch(Exception e) {
			return null;
		}
	}
	
	public static String toJson(Object object) {
	    return new Gson().toJson(object);
	  }
	 
	  public ResponseTransformer json() {
	    return JsonUtil::toJson;
	  }
}
