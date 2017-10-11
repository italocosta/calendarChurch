package br.com.getset.calendarchurch.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import br.com.getset.calendarchurch.model.Event;
import br.com.getset.calendarchurch.model.GroupNotifications;
import br.com.getset.calendarchurch.model.Study;

@SuppressWarnings("serial")
public class PushNotificationService implements Serializable{
	
	private final String URL = "https://onesignal.com/api/v1/notifications";
	private final String API_KEY = "MjY1M2IxODctMTk0Mi00MTZmLTlkM2UtMDRmOWZiY2U3ZTc5";
	private final String API_ID = "95e7da8a-7f6b-489d-9db8-61fe439d6a92";
	
	public void sendNotificationBasic(String title, GroupNotifications gn) throws IOException{
		if(gn != null && gn.getObjs() != null && !gn.getObjs().isEmpty())
			sendNotificationBasicInternal(title, gn);
	}
	
	public void sendNotificationBasic(String title, List<?> listObjs,String type) throws IOException{
		if(listObjs != null && !listObjs.isEmpty()){
			GroupNotifications gn = new GroupNotifications(listObjs,type);
			sendNotificationBasicInternal(title, gn);
		}
	}
	
	public void sendNotificationBasic(String title, Event evt) throws IOException{
		List<Event> evts = new ArrayList<Event>();
		evts.add(evt);
		GroupNotifications ge = new GroupNotifications(evts,"Evento");
		sendNotificationBasicInternal(title, ge);
	}
	public void sendNotificationBasic(String title, Study std) throws IOException{
		List<Study> stds = new ArrayList<Study>();
		std.setDsContent("");
		stds.add(std);
		GroupNotifications gn = new GroupNotifications(stds,"Estudo");
		sendNotificationBasicInternal(title, gn);
	}

	private void sendNotificationBasicInternal(String title, GroupNotifications evts) throws IOException {
		/*
		WebResource webResource = this.client.resource(this.url);
	    Builder builder = webResource.type(MediaType.APPLICATION_JSON);
	    builder.accept(MediaType.APPLICATION_JSON);
	    builder.post(GroupEvents.class, evts);
		*/
		
		Gson gson = new Gson();
		String jsonResponse;
		String jsonObjects = gson.toJson(evts); 
		URL url = new URL(URL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		con.setDoOutput(true);
		con.setDoInput(true);

		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestProperty("Authorization", "Basic "+API_KEY);
		con.setRequestMethod("POST");

		String strJsonBody = "{" + "\"app_id\": \""+API_ID+"\","
				//+ "\"include_player_ids\": [\"0ca23083-c5c2-49d0-b3e2-41cb57406d3c\"],"
				+ "\"included_segments\": [\"All\"]," 
				+ "\"data\": "+jsonObjects+","
				+ "\"contents\": {\"en\": \""+title+"\"}" + "}";

		System.out.println("strJsonBody:\n" + strJsonBody);

		byte[] sendBytes = strJsonBody.getBytes("UTF-8");
		con.setFixedLengthStreamingMode(sendBytes.length);

		OutputStream outputStream = con.getOutputStream();
		outputStream.write(sendBytes);

		int httpResponse = con.getResponseCode();
		System.out.println("httpResponse: " + httpResponse);

		if (httpResponse >= HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
			Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
			jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			scanner.close();
		} else {
			Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
			jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			scanner.close();
		}
		System.out.println("jsonResponse:\n" + jsonResponse);

	}
}
