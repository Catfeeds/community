package com.tongwii.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

/**
 * 汉子转拼音工具类
 *
 * @author Zeral
 * @date 2017-07-27
 */
public class PinYinUtil {
    /**
     * 将字符串中的中文转化为拼音,英文字符不变
     *
     * @param inputString 汉字
     * @return
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder output = new StringBuilder();
        if (inputString != null && inputString.length() > 0
                && !"null".equals(inputString)) {
            char[] input = inputString.trim().toCharArray();
            try {
                for (char anInput : input) {
                    if (Character.toString(anInput).matches("[\\u4E00-\\u9FA5]+")) {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(anInput, format);
                        output.append(temp[0]);
                    } else
                        output.append(Character.toString(anInput));
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        } else {
            return "*";
        }
        return output.toString();
    }

    /**
     * 汉字转换为汉语拼音首字母，英文字符不变
     *
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chines) {
        StringBuilder pinyinName = new StringBuilder();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char aNameChar : nameChar) {
            if (aNameChar > 128) {
                try {
                    pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(aNameChar, defaultFormat)[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName.append(aNameChar);
            }
        }
        return pinyinName.toString();
    }

    /**
     * 汉字转换为汉语拼音首字母，英文字符不变,拿到第一个字符
     *
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToFirstChar(String chines) {
        String firstSpeall = converterToFirstSpell(chines);
        if(StringUtils.isNotEmpty(firstSpeall)) {
            return firstSpeall.substring(0,1);
        }
        return "";
    }


    public static void main(String[] args) {
        String test = "王八蛋";
        System.out.printf(converterToFirstChar(test));
    }
}
