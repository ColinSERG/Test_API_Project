package src.util;

public class ParseInt {

	public int parseInt(String num) {
		int x;
		try {
			x = Integer.parseInt(num);
		} catch (Exception e) {
			x = -1;
		}
		return x;
	}

}
