package com.test.programmers.stackqueue;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class BridgeTruck {

    public static void main(String[] args) {
        /**
         *
         * url : https://programmers.co.kr/learn/courses/30/lessons/42583
         *
         * 문제 설명
         * 트럭 여러 대가 강을 가로지르는 일 차선 다리를 정해진 순으로 건너려 합니다. 모든 트럭이 다리를 건너려면 최소 몇 초가 걸리는지 알아내야 합니다. 트럭은 1초에 1만큼 움직이며, 다리 길이는 bridge_length이고 다리는 무게 weight까지 견딥니다.
         * ※ 트럭이 다리에 완전히 오르지 않은 경우, 이 트럭의 무게는 고려하지 않습니다.
         *
         * 예를 들어, 길이가 2이고 10kg 무게를 견디는 다리가 있습니다. 무게가 [7, 4, 5, 6]kg인 트럭이 순서대로 최단 시간 안에 다리를 건너려면 다음과 같이 건너야 합니다.
         *
         * 경과 시간	다리를 지난 트럭	다리를 건너는 트럭	대기 트럭
         * 0	[]	[]	[7,4,5,6]
         * 1~2	[]	[7]	[4,5,6]
         * 3	[7]	[4]	[5,6]
         * 4	[7]	[4,5]	[6]
         * 5	[7,4]	[5]	[6]
         * 6~7	[7,4,5]	[6]	[]
         * 8	[7,4,5,6]	[]	[]
         * 따라서, 모든 트럭이 다리를 지나려면 최소 8초가 걸립니다.
         *
         * solution 함수의 매개변수로 다리 길이 bridge_length, 다리가 견딜 수 있는 무게 weight, 트럭별 무게 truck_weights가 주어집니다. 이때 모든 트럭이 다리를 건너려면 최소 몇 초가 걸리는지 return 하도록 solution 함수를 완성하세요.
         *
         * 제한 조건
         * bridge_length는 1 이상 10,000 이하입니다.
         * weight는 1 이상 10,000 이하입니다.
         * truck_weights의 길이는 1 이상 10,000 이하입니다.
         * 모든 트럭의 무게는 1 이상 weight 이하입니다.
         * 입출력 예
         * bridge_length	weight	truck_weights	return
         * 2	10	[7,4,5,6]	8
         * 100	100	[10]	101
         * 100	100	[10,10,10,10,10,10,10,10,10,10]	110
         * 출처
         *
         * ※ 공지 - 2020년 4월 06일 테스트케이스가 추가되었습니다.
         *
         */

        /*
        int bridge_length = 2;
        int weight = 10;
        int[] truck_weights = {7,4,5,6};
        // result 8
        */

        int bridge_length = 100;
        int weight = 100;
        int[] truck_weights = {10,10,10,10,10,10,10,10,10,10};

        BridgeTruck bridgeTruck = new BridgeTruck();

        int result = bridgeTruck.solution(bridge_length, weight, truck_weights);

        System.out.println(result);

    }


    public int solution(int bridge_length, int weight, int[] truck_weights) {
        int answer = 0;

        Bridge bridge = new Bridge(bridge_length, weight);

        Queue<Integer> waitingTrucks = 대기트럭_큐_생성(truck_weights);

        while(bridge.getCurWeight() > 0 || !waitingTrucks.isEmpty()){
            트럭이동(bridge, waitingTrucks);
            answer++;
        }

        return answer;
    }

    public Queue<Integer> 대기트럭_큐_생성(int[] truck_weights) {
        Queue<Integer> waitingTrucks =  new LinkedList<>();

        for(int i=0 ; i < truck_weights.length; i++){
            waitingTrucks.add(truck_weights[i]);
        }

        return waitingTrucks;
    }

    public void 트럭이동(Bridge bridge, Queue<Integer> waitingTrucks){
        다리건너는_트럭_진행(bridge);
        대기트럭_진행(bridge, waitingTrucks);
    }

    public void 다리건너는_트럭_진행(Bridge bridge){
        bridge.트럭진행하기();
    }

    public void 대기트럭_진행(Bridge bridge, Queue<Integer> waitingTrucks) {
        ;
        if(bridge.getMaxWeight() - bridge.getCurWeight() >= Optional.ofNullable(waitingTrucks.peek()).orElseGet(() -> 0) ){
            bridge.트럭올라가기( Optional.ofNullable(waitingTrucks.poll()).orElseGet(()-> 0));
        }else{
            bridge.트럭올라가기(0);
        }
    }

    static class Bridge{

        private int length; // 길이
        private int maxWeight;  // 최대 하중
        private int curWeight; // 현재 하중

        Queue<Integer> onBridge = new LinkedList<>(); // 다리 위의 상태

        public Bridge(int length, int maxWeight){
            다리상태_초기화(length, maxWeight);
        }

        public void 다리상태_초기화(int length, int maxWeight) {
            this.length = length;
            this.maxWeight = maxWeight;

            for(int i = 0; i < length; i++){
                트럭올라가기(0);
            }
        }

        public void 트럭올라가기(int weight) {
            onBridge.add(weight);
            curWeight += weight;
        }

        public Integer 트럭진행하기(){
            Integer poll = onBridge.poll();
            curWeight -= poll;
            return poll;
        }

        public int getCurWeight(){
            return curWeight;
        }

        public int getMaxWeight() {
            return maxWeight;
        }
    }

}
