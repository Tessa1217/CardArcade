package co.arcade.card.mail;

import java.util.HashMap;

import org.json.simple.JSONObject;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public class SMS {
	private static final String api_key = "apikey";
	private static final String api_secret = "apisecret";
	private static Message coolsms = new Message(api_key, api_secret);
	private static final String from = "phone";

	public static int sendMessage(String to) {
		HashMap<String, String> params = new HashMap<String, String>();
		int randNum = (int) (Math.random() * 99999 - 10000 + 1) + 10000;
		params.put("to", to);
		params.put("from", from);
		params.put("type", "SMS");
		params.put("text", String.valueOf(randNum));
		params.put("app_version", "test app 1.2");
		try {
			JSONObject obj = (JSONObject) coolsms.send(params);
			Long successCnt = (Long) obj.get("success_count");
			if (successCnt != 0) {
				System.out.println("인증코드를 발송했습니다.");
				return randNum;
			}
		} catch (CoolsmsException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCode());
		}
		return randNum;
	}
}