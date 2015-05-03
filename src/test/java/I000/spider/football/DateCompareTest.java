package I000.spider.football;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCompareTest {

	public static void main(String[] args) {
		Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date yesterday = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date sourceDate = sdf.parse("2014-08-19");
			if(sourceDate.after(yesterday)){
				System.out.println("大于");
				return;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("小于");
	}
}
