package I000.spider.football;

import java.util.regex.Pattern;

public class StringNumberTest {

	public static void main(String[] args) {
		String str = "2.00";
		Pattern pattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");//判断是否为小数
	    System.out.println(pattern.matcher(str).matches());
	}
}
