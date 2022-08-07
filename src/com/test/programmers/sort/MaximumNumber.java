package com.test.programmers.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MaximumNumber {

    /** test2
     * 문제 설명
     * 0 또는 양의 정수가 주어졌을 때, 정수를 이어 붙여 만들 수 있는 가장 큰 수를 알아내 주세요.
     *
     * 예를 들어, 주어진 정수가 [6, 10, 2]라면 [6102, 6210, 1062, 1026, 2610, 2106]를 만들 수 있고, 이중 가장 큰 수는 6210입니다.
     *
     * 0 또는 양의 정수가 담긴 배열 numbers가 매개변수로 주어질 때, 순서를 재배치하여 만들 수 있는 가장 큰 수를 문자열로 바꾸어 return 하도록 solution 함수를 작성해주세요.
     *
     * 제한 사항
     * numbers의 길이는 1 이상 100,000 이하입니다.
     * numbers의 원소는 0 이상 1,000 이하입니다.
     * 정답이 너무 클 수 있으니 문자열로 바꾸어 return 합니다.
     * 입출력 예
     * numbers	return
     * [6, 10, 2]	"6210"
     * [3, 30, 34, 5, 9]
     * @param args
     */

    public static void main(String[] args) {
        MaximumNumber maximumNumber = new MaximumNumber();
        //int[] numbers = {3, 30, 34, 5, 9};
        int[] numbers = {3, 30, 34};
        //int[] numbers = {89,898,8};
        String solution = maximumNumber.solution(numbers);
        System.out.println("solution = " + solution);
    }

    public String solution(int[] numbers) {
        String answer = "";

        List<String> strNumbers = Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .sorted(this::compareSubString)
                .collect(Collectors.toList());
        Collections.reverse(strNumbers);
        if(strNumbers.get(0).equals("0")){
            answer = "0";
        }else{
            answer = strNumbers.stream().reduce(String::concat).orElse("");
        }
        return answer;
    }

    private int compareSubString(String s1, String s2) {
        if(s1.length() == s2.length()){
            return s1.compareTo(s2);
        }else if(s1.length() > s2.length()){
            if(s1.startsWith(s2)){
                return compareSubString(s1.substring(s2.length()), s2);
            }else{
                return s1.substring(0, s2.length()).compareTo(s2);
            }
        }else{
            if(s2.startsWith(s1)){
                return compareSubString(s1, s2.substring(s1.length()));
            }else{
                return s1.compareTo(s2.substring(0, s1.length()));
            }
        }
    }
}
