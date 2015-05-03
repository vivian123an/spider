package I000.common;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.helper.StringUtil;

public class CommonUtil {

	 /** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
	 public static final String US_ASCII = "US-ASCII";

	 /** ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1 */
	 public static final String ISO_8859_1 = "ISO-8859-1";

	 /** 8 位 UCS 转换格式 */
	 public static final String UTF_8 = "UTF-8";

	 /** 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序 */
	 public static final String UTF_16BE = "UTF-16BE";

	 /** 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序 */
	 public static final String UTF_16LE = "UTF-16LE";

	 /** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
	 public static final String UTF_16 = "UTF-16";

	 /** 中文超大字符集 */
	 public static final String GBK = "GBK";
	 
	
	 public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	 public static SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	  * 字符串编码转换的实现方法
	  * @param str  待转换编码的字符串
	  * @param oldCharset 原编码
	  * @param newCharset 目标编码
	  * @return
	  * @throws UnsupportedEncodingException
	  */
	 public static String changeCharset(String str, String oldCharset, String newCharset)
			 throws UnsupportedEncodingException {
		 if (str != null) {
			 //用旧的字符编码解码字符串。解码可能会出现异常。
			 byte[] bs = str.getBytes(oldCharset);
			 //用新的字符编码生成字符串
			 return new String(bs, newCharset);
		 }
		 return null;
	 }
	 
	/**
	 * 日期字符串转换
	 * 20140830 转换2014-08-30
	 * @param dateStr
	 * @return
	 */
	public static String dateFormat(String dateStr){
		if(!StringUtil.isBlank(dateStr)){
			dateStr = dateStr.substring(0,8);
			try {
				Date pdate = yyyyMMdd.parse(dateStr);
				return yyyy_MM_dd.format(pdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}
