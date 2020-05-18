package cn.mamp.leetcode;

import java.util.Stack;

/**
 * @author mamp
 * @data 2020/5/16
 */
public class Test1021 {

}

class Solution {

    public static void main(String[] args) {
        System.out.println(removeOuterParentheses("((a+b)*(c+d))((a+(b-c))*d)"));
    }

    public static String removeOuterParentheses(String s) {

        // stack 用于存储"("
        Stack<String> stack = new Stack<>();
        // 当前最外层"("的index
        int idx = 0;
        // 返回结果
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            // 如果是"(" 进栈
            if (s.charAt(i) == '(') {
                // 记录最外层 ( 的位置
                if (stack.size() == 0) {
                    idx = i;
                }
                stack.add("(");
                continue;
            }
            if (s.charAt(i) == ')') {
                // 如果是")" 出栈
                stack.pop();
                // 如果 栈为空,说明当前的")"是外层的
                if (stack.size() == 0) {
                    result += s.substring(idx + 1, i);
                }
            }
        }
        return result;
    }
}