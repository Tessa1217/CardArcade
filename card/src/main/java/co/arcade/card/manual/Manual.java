package co.arcade.card.manual;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Manual {

	private static File oneCard;
	private static FileReader rd;
	private static BufferedReader bf;
	private static StringBuilder sb;
	private static String resource;

	public static String readManual(String source) {
		try {
			sb = new StringBuilder();
			resource = Manual.class.getResource("/" + source + ".txt").getPath();
			oneCard = new File(resource);
			rd = new FileReader(oneCard);
			bf = new BufferedReader(rd);
			while (bf.ready()) {
				sb.append(bf.readLine());
				sb.append("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			closeConnection();
		}
		String manual = sb.toString();
		return manual;
	}

	private static void closeConnection() {
		try {
			if (bf != null) {
			}
			bf.close();

			if (rd != null) {
				rd.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
