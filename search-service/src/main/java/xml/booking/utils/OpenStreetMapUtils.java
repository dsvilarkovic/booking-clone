package xml.booking.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class OpenStreetMapUtils {
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	static OpenStreetMapUtils instance;
	
	public static OpenStreetMapUtils getInstance() {
        if (instance == null) {
            instance = new OpenStreetMapUtils();
        }
        return instance;
    }
	
	/**
	 * Dobijanje koordinata (latitude, longitude) na osnovu zadate adrese; pomocu Open Street Maps
	 */
	public Map<String,Double> getCoordinates(String address, String city, String country) {
		
		String addressString = address + " " + city + " " + country;
		
		Map<String,Double> resultMap = new HashMap<String,Double>();
		
		String url = "https://nominatim.openstreetmap.org/search?q=";
		
		String[] split = addressString.split(" ");
		
		for(int i = 0; i < split.length-1; i++) {
			url += split[i] + "+";
		}
		
		url += split[split.length-1];
		
		url += "&format=json&addressdetails=1";
		
		ResponseEntity<String> result = restTemplate().getForEntity(url, String.class);
		
		if(result.getStatusCodeValue() != 200) {
			return null;
		}
		else {
			Object obj = JSONValue.parse(result.getBody());
			
			if (obj instanceof JSONArray) {
	            JSONArray array = (JSONArray) obj;
	            
	            if (array.size() > 0) {
	                JSONObject jsonObject = (JSONObject) array.get(0);

	                String lon = (String) jsonObject.get("lon");
	                String lat = (String) jsonObject.get("lat");
	                resultMap.put("lon", Double.parseDouble(lon));
	                resultMap.put("lat", Double.parseDouble(lat));

	                return resultMap;
	            }
	        }
		}
		
		return null;
	}

	/**
	 * Racunanje udaljenosti izmedju dve lokacije, na osnovu (latitude, longitude)
	 */
	public double calculateDistance(Map<String,Double> coordinates1,  Map<String,Double> coordinates2) {
		
		return 0;
		
	}
}
