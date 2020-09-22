package com.netcity.util;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PinYinUtils {
	public static String getHanziInitials(String hanzi) {
		String result = null;
		if (hanzi != null && !"".equals(hanzi)) {
			char[] charArray = hanzi.toCharArray();
			StringBuffer sb = new StringBuffer();
			byte b;
			int i;
			char[] arrayOfChar1;
			for (i = (arrayOfChar1 = charArray).length, b = 0; b < i;) {
				char ch = arrayOfChar1[b];

				String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(ch);
				if (stringArray != null)
					sb.append(stringArray[0].charAt(0));
				b++;
			}

			if (sb.length() > 0) {
				result = sb.toString().toUpperCase();
			}
		}
		return result;
	}

	public static String getHanziPinYin(String hanzi) {
		String result = null;
		if (hanzi != null && !"".equals(hanzi)) {
			char[] charArray = hanzi.toCharArray();
			StringBuffer sb = new StringBuffer();
			byte b;
			int i;
			char[] arrayOfChar1;
			for (i = (arrayOfChar1 = charArray).length, b = 0; b < i;) {
				char ch = arrayOfChar1[b];

				String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(ch);
				if (stringArray != null) {
					sb.append(stringArray[0].replaceAll("\\d", ""));
				}
				b++;
			}

			if (sb.length() > 0) {
				result = sb.toString();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(getHanziInitials("ƴ��"));
		System.out.println(getHanziPinYin("����"));
	}
}