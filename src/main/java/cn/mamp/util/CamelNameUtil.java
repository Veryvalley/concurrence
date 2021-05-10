package cn.mamp.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * 驼峰命令批量转换util
 *
 * @author mamp
 * @date 2021/5/10
 */
public class CamelNameUtil {
    public static void main(String[] args) throws IOException {
        String content = FileUtils.readFileToString(new File("E:\\testName.txt"));
        System.out.println("读取到文件-----------" + content);
        // ","分割符
        String exchange = exchange(content, ",");
        FileUtils.writeStringToFile(new File("E:\\testName1.txt"), exchange);
        System.out.println("写入到文件-----------" + exchange);
    }

    /**
     * 将下划线命名转换为驼峰命名
     *
     * @param sourceStr
     * @param spearator
     * @return
     */
    public static String exchange(String sourceStr, String spearator) {
        StringBuilder sb = new StringBuilder();
        // 全部转成小写后再转成驼峰
        sourceStr = sourceStr.toLowerCase();
        if (sourceStr.indexOf(spearator) > 0) {

            String[] split = sourceStr.split(spearator);
            Arrays.stream(split).forEach(word -> {
                StringBuilder sb1 = parseStr(word);
                sb1.append(spearator);
                sb.append(sb1);
            });
        } else {
            StringBuilder sb1 = parseStr(sourceStr);
            sb1.append(spearator);
            sb.append(sb1);
        }
        return sb.substring(0, sb.lastIndexOf(spearator)).toString();
    }

    public static StringBuilder parseStr(String str) {
        if ("_".equals(str.charAt(0))) {
            str = str.substring(1);
        } else if ("_".equals(str.charAt(str.length() - 1))) {
            str = str.substring(0, str.length() - 1);
        }

        StringBuilder sb1 = new StringBuilder();
        String[] s = str.split("_");
        sb1.append(s[0]);
        for (int i = 1; i < s.length; i++) {
            char c = s[i].charAt(0);
            if (c >= 97) {
                c -= 32;
            }
            // 使用StringBuilder 是为了避免一个单词中出现相同字母的情况
            StringBuilder tmpsb = new StringBuilder(s[i]);
            tmpsb.replace(0, 1, String.valueOf(c));
            sb1.append(tmpsb);
        }
        return sb1;
    }
}
