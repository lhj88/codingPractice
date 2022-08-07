package com.test.programmers.stackqueue;

import java.util.Arrays;

public class StockPrice {

    public static void main(String[] args){
        /**
         url : https://programmers.co.kr/learn/courses/30/lessons/42584?language=java
test1
         문제 설명
         초 단위로 기록된 주식가격이 담긴 배열 prices가 매개변수로 주어질 때, 가격이 떨어지지 않은 기간은 몇 초인지를 return 하도록 solution 함수를 완성하세요.

         제한사항
         prices의 각 가격은 1 이상 10,000 이하인 자연수입니다.
         prices의 길이는 2 이상 100,000 이하입니다.

         입출력 예
         prices	return
         [1, 2, 3, 2, 3]	[4, 3, 1, 1, 0]

         입출력 예 설명
         1초 시점의 ₩1은 끝까지 가격이 떨어지지 않았습니다.
         2초 시점의 ₩2은 끝까지 가격이 떨어지지 않았습니다.
         3초 시점의 ₩3은 1초뒤에 가격이 떨어집니다. 따라서 1초간 가격이 떨어지지 않은 것으로 봅니다.
         4초 시점의 ₩2은 1초간 가격이 떨어지지 않았습니다.
         5초 시점의 ₩3은 0초간 가격이 떨어지지 않았습니다.

         ※ 공지 - 2019년 2월 28일 지문이 리뉴얼되었습니다.

        */

        int[] prices = {1,2,3,2,3};

        StockPrice stockPrice = new StockPrice();

        int[] result = stockPrice.solution(prices);
        System.out.println(Arrays.toString(result));
    }

    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];

        //Queue<Integer> pricesQueue = new LinkedList<>();

        for( int i = 0 ; i < prices.length ; i++){
            int result = 가격유지기간_구하기(prices,i);
            answer[i] = result;
        }
        return answer;
    }

    private int 가격유지기간_구하기(int[] prices, int criteriaIndex) {
        int result;

        if(criteriaIndex == prices.length -1){
            result = 마지막은_0_고정();
        }else{
            result = 마지막제외_가격유지기간_구하기(prices, criteriaIndex);
        }

        return result;
    }

    private int 마지막제외_가격유지기간_구하기(int[] prices, int criteriaIndex) {
        int nextIndex = 1;
        while(criteriaIndex +nextIndex < prices.length-1
                && prices[criteriaIndex +nextIndex]>= prices[criteriaIndex]){
            nextIndex++;
        }
        return nextIndex;
    }

    private int 마지막은_0_고정() {
        return 0;
    }
}
