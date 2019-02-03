package src.util;

public class ParseInt {

	public int parseInt(String num) {
		return Integer.parseInt(num);
	}

	//Added to throw error during static analysis
	public void errorMethod() {
		parseInt(null);
	}
}
