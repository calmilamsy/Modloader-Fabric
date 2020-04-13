/*package net;

import com.google.gson.Gson;
import net.glasslauncher.testmod.Config;
import net.glasslauncher.testmod.jsontemplate.JoinRequest;
import net.glasslauncher.testmod.jsontemplate.Profile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class HttpJoinHandler {

	public static String getResponse(URL url) {
		try {
			String req = url.toString();
			// Original: http://www.minecraft.net/game/joinserver.jsp?user=calmilamsy&sessionId=-&serverId=77e59ac042d8a50e
			// New:      http://localhost:25561/join/?user=calmilamsy&sessionId=-&serverId=77e59ac042d8a50e
			if (Config.isServer()) {
				String response;
				// Turns to: https://sessionserver.mojang.com/session/minecraft/hasJoined?username=calmilamsy&serverId=77e59ac042d8a50e
				req = req.replaceFirst("user", "username");
				req = req.replaceFirst("/join/", "/session/minecraft/hasJoined");
				req = "https://sessionserver.mojang.com" + req;

				HttpURLConnection reqJoined = (HttpURLConnection) (new URL(req)).openConnection();
				if (reqJoined.getResponseCode() == 200) {
					response = "YES";
				} else {
					response = "NO";
				}

				return response;
			} else {
				String[] reqParts = req.split("[&=]");
				reqParts = new String[] {reqParts[1], reqParts[3], reqParts[5]};
				URL nameURL = new URL("https://api.mojang.com/users/profiles/minecraft/" + reqParts[0]);
				URLConnection nameUrlConnection = nameURL.openConnection();
				String response = convertStreamToString(nameUrlConnection.getInputStream());

				Profile responseJson = (new Gson()).fromJson(response, Profile.class);
				String uuid = responseJson.getId();

				JoinRequest reqJson = new JoinRequest(reqParts[1], uuid, reqParts[2]);

				HttpURLConnection reqJoin = (HttpURLConnection) (new URL("https://sessionserver.mojang.com/session/minecraft/join")).openConnection();
				reqJoin.setRequestMethod("POST");
				reqJoin.setRequestProperty("Content-Type", "application/json");
				reqJoin.setDoOutput(true);

				OutputStreamWriter wr = new OutputStreamWriter(reqJoin.getOutputStream());
				wr.write((new Gson()).toJson(reqJson));
				wr.flush();

				if (reqJoin.getResponseCode() != 204) {
					throw new IOException("Got unexpected response from join request: " + reqJoin.getResponseCode());
				} else {
					response = "ok";
				}

				return response;

			}
		} catch (Exception e) {
			System.err.println("Exception while handling join:");
			e.printStackTrace();
		}
		return "NO";
	}
	
	static String convertStreamToString(InputStream is) {
	    Scanner s = new Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}*/