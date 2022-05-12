package co.arcade.card.manual;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TranslateService {
	private static String clientId = "VHh1tKam0ftZGAHKiMzd";
	private static String clientSecret = "Fcz9z5hWfX";
	private static String serviceURL = "https://openapi.naver.com/v1/papago/n2mt";
	private static String text;
	private static InputStreamReader isr;
	private static BufferedReader br;
	private static HttpURLConnection con;

	public static String request(String source) {
		text = Manual.readManual(source);
		try {
			text = URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);

		String responseBody = setParam(serviceURL, requestHeaders, text);
		responseBody = translatedManual(responseBody);
		return responseBody;
	}

	private static String setParam(String serviceURL, Map<String, String> requestHeaders, String text) {
		con = connect(serviceURL);
		String paramURL = "source=en&target=ko&text=" + text;

		try {
			con.setRequestMethod("POST");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}
			con.setDoOutput(true);
			try (DataOutputStream ops = new DataOutputStream(con.getOutputStream())) {
				ops.write(paramURL.getBytes());
				ops.flush();
			}
			int responseCode = con.getResponseCode();
			System.out.println(responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) {
				isr = new InputStreamReader(con.getInputStream());
				br = new BufferedReader(isr);
				StringBuilder sb = new StringBuilder();
				String context = "";
				while ((context = br.readLine()) != null) {
					sb.append(context);
				}
				return sb.toString();
			} else {
				return con.getResponseMessage();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	private static HttpURLConnection connect(String serviceURL) {
		try {
			URL url = new URL(serviceURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			return con;

		} catch (IOException e) {
			e.printStackTrace();

		}
		return null;
	}

	private static String translatedManual(String responseBody) {
		// message => result => translatedText
		// StringBuilder sb = new StringBuilder();
		String trans = "";
		try {
			JSONParser parser = new JSONParser();
			JSONObject info = (JSONObject) parser.parse(responseBody);
			JSONObject message = (JSONObject) info.get("message");
			JSONObject result = (JSONObject) message.get("result");
			String translatedText = (String) result.get("translatedText");

			trans += translatedText;

		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		return trans;

	}

	private static void close() {
		try {
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (con != null) {
				con.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
