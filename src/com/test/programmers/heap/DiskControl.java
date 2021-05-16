package com.test.programmers.heap;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class DiskControl {

    /**
     * 문제 설명
     * 하드디스크는 한 번에 하나의 작업만 수행할 수 있습니다. 디스크 컨트롤러를 구현하는 방법은 여러 가지가 있습니다. 가장 일반적인 방법은 요청이 들어온 순서대로 처리하는 것입니다.
     *
     * 예를들어
     *
     * - 0ms 시점에 3ms가 소요되는 A작업 요청
     * - 1ms 시점에 9ms가 소요되는 B작업 요청
     * - 2ms 시점에 6ms가 소요되는 C작업 요청
     * 와 같은 요청이 들어왔습니다. 이를 그림으로 표현하면 아래와 같습니다.
     * Screen Shot 2018-09-13 at 6.34.58 PM.png
     *
     * 한 번에 하나의 요청만을 수행할 수 있기 때문에 각각의 작업을 요청받은 순서대로 처리하면 다음과 같이 처리 됩니다.
     * Screen Shot 2018-09-13 at 6.38.52 PM.png
     *
     * - A: 3ms 시점에 작업 완료 (요청에서 종료까지 : 3ms)
     * - B: 1ms부터 대기하다가, 3ms 시점에 작업을 시작해서 12ms 시점에 작업 완료(요청에서 종료까지 : 11ms)
     * - C: 2ms부터 대기하다가, 12ms 시점에 작업을 시작해서 18ms 시점에 작업 완료(요청에서 종료까지 : 16ms)
     * 이 때 각 작업의 요청부터 종료까지 걸린 시간의 평균은 10ms(= (3 + 11 + 16) / 3)가 됩니다.
     *
     * 하지만 A → C → B 순서대로 처리하면
     * Screen Shot 2018-09-13 at 6.41.42 PM.png
     *
     * - A: 3ms 시점에 작업 완료(요청에서 종료까지 : 3ms)
     * - C: 2ms부터 대기하다가, 3ms 시점에 작업을 시작해서 9ms 시점에 작업 완료(요청에서 종료까지 : 7ms)
     * - B: 1ms부터 대기하다가, 9ms 시점에 작업을 시작해서 18ms 시점에 작업 완료(요청에서 종료까지 : 17ms)
     * 이렇게 A → C → B의 순서로 처리하면 각 작업의 요청부터 종료까지 걸린 시간의 평균은 9ms(= (3 + 7 + 17) / 3)가 됩니다.
     *
     * 각 작업에 대해 [작업이 요청되는 시점, 작업의 소요시간]을 담은 2차원 배열 jobs가 매개변수로 주어질 때, 작업의 요청부터 종료까지 걸린 시간의 평균을 가장 줄이는 방법으로 처리하면 평균이 얼마가 되는지 return 하도록 solution 함수를 작성해주세요. (단, 소수점 이하의 수는 버립니다)
     *
     * 제한 사항
     * jobs의 길이는 1 이상 500 이하입니다.
     * jobs의 각 행은 하나의 작업에 대한 [작업이 요청되는 시점, 작업의 소요시간] 입니다.
     * 각 작업에 대해 작업이 요청되는 시간은 0 이상 1,000 이하입니다.
     * 각 작업에 대해 작업의 소요시간은 1 이상 1,000 이하입니다.
     * 하드디스크가 작업을 수행하고 있지 않을 때에는 먼저 요청이 들어온 작업부터 처리합니다.
     * 입출력 예
     * jobs	return
     * [[0, 3], [1, 9], [2, 6]]	9
     * 입출력 예 설명
     * 문제에 주어진 예와 같습니다.
     *
     * 0ms 시점에 3ms 걸리는 작업 요청이 들어옵니다.
     * 1ms 시점에 9ms 걸리는 작업 요청이 들어옵니다.
     * 2ms 시점에 6ms 걸리는 작업 요청이 들어옵니다.
     * @param args
     */

    public static void main(String[] args) {
        //int[][] jobs = {{0, 3}, {1, 9}, {2, 6}};
        //int[][] jobs = {{24, 10}, {18, 39}, {34, 20}, {37, 5}, {47, 22}, {20, 47}, {15, 34}, {15, 2}, {35, 43}, {26, 1}};
        int[][] jobs = {{24, 10}, {28, 39}, {43, 20}, {37, 5}, {47, 22}, {20, 47}, {15, 34}, {15, 2}, {35, 43}, {26, 1}};
        DiskControl diskControl = new DiskControl();
        int solution = diskControl.solution(jobs);

        System.out.println("solution = " + solution);
    }

    public int solution(int[][] jobs) {
        int answer = 0;

        int regularAvgTime = getAvgTimeWithRegularSequence(getTaskQueue(jobs));
        int priorityAvgTime = getAvgTimeWithPrioritySequence(getTaskQueue(jobs));

        answer = regularAvgTime < priorityAvgTime ? regularAvgTime : priorityAvgTime;
        return answer;
    }

    private Queue<Task> getTaskQueue(int[][] jobs) {
        Queue<Task> taskQueue = new PriorityQueue<>((o1, o2) -> {
            if(o1.getInTime() == o2.getInTime()){
                return Long.compare(o1.getWorkTime(), o2.getWorkTime());
            }else{
                return Long.compare(o1.getInTime(), o2.getInTime());
            }
        });

        for (int[] job : jobs) {
            Task task = new Task(job[0], job[1]);
            taskQueue.add(task);
        }
        return taskQueue;
    }

    private int getAvgTimeWithRegularSequence(Queue<Task> jobs) {
        Queue<Task> processQueue = new LinkedList<>();

        return getAgvTime(jobs, processQueue);
    }

    private int getAvgTimeWithPrioritySequence(Queue<Task> jobs) {
        Queue<Task> processQueue = new PriorityQueue<>((o1, o2) -> {
            if(o1.getWorkTime() == o2.getWorkTime()){
                return Long.compare(o1.getInTime(), o2.getInTime());
            }else{
                return Long.compare(o1.getWorkTime(), o2.getWorkTime());
            }
        });

        return getAgvTime(jobs, processQueue);
    }

    public int getAgvTime(Queue<Task> jobs, Queue<Task> processQueue){
        Task currentTask = null;
        long totalCompleteTime = 0;
        long totalCompleteCount = 0;
        long currentTimeStamp = 0;

        while(!(jobs.isEmpty() && processQueue.isEmpty() && currentTask == null)){
            if(currentTask != null){
                currentTimeStamp += currentTask.getWorkTime();
                totalCompleteCount++;
                totalCompleteTime += currentTimeStamp - currentTask.getInTime();
                currentTask = null;
                
            }

            if(processQueue.isEmpty() && !jobs.isEmpty()){
                 if(currentTimeStamp < jobs.peek().getInTime()){
                     currentTimeStamp = jobs.peek().getInTime();
                 }
            }

            while(!jobs.isEmpty() && jobs.peek().getInTime() <= currentTimeStamp){
                processQueue.add(jobs.poll());
            }

            currentTask = processQueue.poll();
        }

        return (int)Math.floor(totalCompleteCount==0?0:totalCompleteTime/totalCompleteCount);
    }



    /*public int solution(int[][] jobs) {
        int answer = 0;

        long regularAvgTime = getAvgTimeWithRegularSequence(getTaskQueue(jobs));
        long priorityAvgTime = getAvgTimeWithPrioritySequence(getTaskQueue(jobs));

        answer = (int)(regularAvgTime < priorityAvgTime ? regularAvgTime : priorityAvgTime );
        return answer;
    }

    private Queue<Task> getTaskQueue(int[][] jobs) {
        Queue<Task> taskQueue = new LinkedList<>();

        for (int[] job : jobs) {
            Task task = new Task(job[0], job[1]);
            taskQueue.add(task);
        }
        return taskQueue;
    }

    private long getAvgTimeWithRegularSequence(Queue<Task> task) {
        Queue<Task> processQueue = new LinkedList<>();
        return getAgvTime(task, processQueue);
    }

    private long getAvgTimeWithPrioritySequence(Queue<Task> task) {
        Queue<Task> processQueue = new PriorityQueue<>((o1, o2) -> {
            if( o1.getWorkTime() > o2.getWorkTime()){
                return 1;
            }else if(o1.getWorkTime() == o2.getWorkTime()){
                return o1.getInTime() > o2.getInTime() ? 1 : 0;
            }else{
                return -1;
            }

        });
        return getAgvTime(task, processQueue);
    }

    public long getAgvTime(Queue<Task> task, Queue<Task> processQueue){
        Task currentTask = null;
        long totalCompleteTime = 0;
        long totalCompleteCount = 0;
        int currentTimeStamp = 0;

        while(!(task.isEmpty() && processQueue.isEmpty()&& currentTask == null)){
            if(currentTask != null && currentTask.getWorkTime() <= 0){
                totalCompleteCount++;
                totalCompleteTime += currentTimeStamp - currentTask.getInTime();
                currentTask = null;
            }

            if(!task.isEmpty() && currentTimeStamp == task.peek().getInTime()){
                processQueue.add(task.poll());
            }

            if(currentTask == null){
                currentTask = processQueue.poll();
            }

            if(currentTask != null){
                if(currentTask.getWorkTime() > 0){
                    currentTask.setWorkTime(currentTask.getWorkTime()-1);
                }
            }
            currentTimeStamp++;
        }

        return totalCompleteCount==0?0:totalCompleteTime/totalCompleteCount;
    }

    static class Task{
        int inTime;
        int workTime;

        Task(int inTime, int workTime){
            this.inTime = inTime;
            this.workTime = workTime;
        }

        public int getInTime() {
            return inTime;
        }

        public int getWorkTime() {
            return workTime;
        }

        public void setInTime(int inTime) {
            this.inTime = inTime;
        }

        public void setWorkTime(int workTime) {
            this.workTime = workTime;
        }
    }*/
}

class Task{
    long inTime;
    long workTime;

    Task(long inTime, long workTime){
        this.inTime = inTime;
        this.workTime = workTime;
    }

    public long getInTime() {
        return inTime;
    }

    public long getWorkTime() {
        return workTime;
    }

    public void setInTime(long inTime) {
        this.inTime = inTime;
    }

    public void setWorkTime(long workTime) {
        this.workTime = workTime;
    }
}