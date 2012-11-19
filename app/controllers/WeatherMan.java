package controllers;

import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.mvc.Controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherMan extends Controller {

    public static void now(String city) {
    	
    	String request = String.format("http://free.worldweatheronline.com/feed/weather.ashx?q=%s&format=json&key=%s",
    			city,
    			"a4c835db3c112457121911");
    	
    	HttpResponse response = WS.url(request).get();
    	
    	String current = "Could not find weather for city: " + city; 
    	JsonElement json = new JsonParser().parse(response.getString());
    	JsonObject jsonObject = json.getAsJsonObject().getAsJsonObject("data");
    	if(!jsonObject.has("error")) {
	    	JsonArray conditions = jsonObject.getAsJsonArray("current_condition");
	    	jsonObject = conditions.get(0).getAsJsonObject();
	    	jsonObject = jsonObject.getAsJsonArray("weatherDesc").get(0).getAsJsonObject();
	    	current = jsonObject.getAsJsonPrimitive("value").getAsString();
    	}

    	renderText(current);
    }

}