package co.arcade.card.mail;

import java.util.HashMap;

import org.json.simple.JSONObject;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public class SMS {
	private static final String api_key = "NCSO40JKDPS6VAFR";
	private static final String api_secret = "YPQE8QCZJNKCBSGRDYKZRTG8DDUXT9OU";
	private static Message coolsms = new Message(api_key, api_secret);
	private static final String from = "010-3823-3874";

	public static int sendMessage(String to) {
		HashMap<String, String> params = new HashMap<String, String>();
		int randNum = (int) (Math.random() * 99999 - 10000 + 1) + 10000;
		params.put("to", to);
		params.put("from", from);
		params.put("type", "SMS");
		params.put("text", "인증코드는 " + String.valueOf(randNum) + "입니다.");
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