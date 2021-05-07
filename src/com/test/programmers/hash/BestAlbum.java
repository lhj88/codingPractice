package com.test.programmers.hash;

import java.util.*;
import java.util.stream.Collectors;

public class BestAlbum {

    /**
     * 문제 설명
     * 스트리밍 사이트에서 장르 별로 가장 많이 재생된 노래를 두 개씩 모아 베스트 앨범을 출시하려 합니다. 노래는 고유 번호로 구분하며, 노래를 수록하는 기준은 다음과 같습니다.
     *
     * 속한 노래가 많이 재생된 장르를 먼저 수록합니다.
     * 장르 내에서 많이 재생된 노래를 먼저 수록합니다.
     * 장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래를 먼저 수록합니다.
     * 노래의 장르를 나타내는 문자열 배열 genres와 노래별 재생 횟수를 나타내는 정수 배열 plays가 주어질 때, 베스트 앨범에 들어갈 노래의 고유 번호를 순서대로 return 하도록 solution 함수를 완성하세요.
     *
     * 제한사항
     * genres[i]는 고유번호가 i인 노래의 장르입니다.
     * plays[i]는 고유번호가 i인 노래가 재생된 횟수입니다.
     * genres와 plays의 길이는 같으며, 이는 1 이상 10,000 이하입니다.
     * 장르 종류는 100개 미만입니다.
     * 장르에 속한 곡이 하나라면, 하나의 곡만 선택합니다.
     * 모든 장르는 재생된 횟수가 다릅니다.
     * 입출력 예
     * genres	plays	return
     * ["classic", "pop", "classic", "classic", "pop"]	[500, 600, 150, 800, 2500]	[4, 1, 3, 0]
     * 입출력 예 설명
     * classic 장르는 1,450회 재생되었으며, classic 노래는 다음과 같습니다.
     *
     * 고유 번호 3: 800회 재생
     * 고유 번호 0: 500회 재생
     * 고유 번호 2: 150회 재생
     * pop 장르는 3,100회 재생되었으며, pop 노래는 다음과 같습니다.
     *
     * 고유 번호 4: 2,500회 재생
     * 고유 번호 1: 600회 재생
     * 따라서 pop 장르의 [4, 1]번 노래를 먼저, classic 장르의 [3, 0]번 노래를 그다음에 수록합니다.
     */


    public static void main(String[] args) {
        String[] genres = {"classic", "pop", "classic", "classic", "pop", "ttt", "classic"};
        int[] plays = {500, 600, 150, 800, 2500, 299, 800};

        BestAlbum bestAlbum = new BestAlbum();
        int[] solution = bestAlbum.solution(genres, plays);
        System.out.println(Arrays.toString(solution));
    }

    public int[] solution(String[] genres, int[] plays) {
        int[] answer;

        // 리스트 생성
        List<GenreNode> genreNodeList = initList(genres, plays);

        // totalplay 값 별 정렬
        genreNodeList.sort((o1, o2) ->{
            if(o1.getTotalPlay()<o2.getTotalPlay()){
                return 1;
            }else if(o1.getTotalPlay()==o2.getTotalPlay()){
                return 0;
            }else{
                return -1;
            }
        });

        Vector<Integer> answerList = new Vector<>();
        genreNodeList.forEach(o->{
            List<Map.Entry<Integer, Integer>> songList = o.getSongMap().entrySet().stream().sorted((o1, o2) -> {
                if (o1.getValue().compareTo(o2.getValue()) < 0) {
                    return 1;
                } else if (o1.getValue().compareTo(o2.getValue()) == 0) {
                    if (o1.getKey().compareTo(o2.getKey()) > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    return -1;
                }
            }).collect(Collectors.toList());
            
            for(int i = 0 ; i<songList.size() && i<2 ; i++){
                answerList.add(songList.get(i).getKey());
            }
        });
        answer =  answerList.stream().mapToInt(Integer::intValue).toArray();

        return answer;
    }

    private List<GenreNode> initList(String[] genres, int[] plays) {
        Map<String, Integer> indexMap = new HashMap<String, Integer>();
        List<GenreNode> list = new ArrayList<GenreNode>();

        for (int i = 0; i < genres.length; i++) {
            if(indexMap.containsKey(genres[i])){
                list.get(indexMap.get(genres[i])).addSong(i, plays[i]);
            }else{
                GenreNode genreNode = new GenreNode(genres[i], i, plays[i]);
                indexMap.put(genres[i], list.size());
                list.add(list.size(), genreNode);
            }
        }
        
        return list;
    }


    static class GenreNode {
        private long totalPlay = 0;
        private String name;
        private HashMap<Integer, Integer> songMap = new HashMap<Integer, Integer>();

        GenreNode(String name, int no, int play){
            this.name = name;
            addSong(no, play);
        }

        public void addSong(int no, int play){
            totalPlay += play;
            songMap.put(no, play);
        }

        public long getTotalPlay() {
            return totalPlay;
        }

        public void setTotalPlay(long totalPlay) {
            this.totalPlay = totalPlay;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public HashMap<Integer, Integer> getSongMap() {
            return songMap;
        }

        public void setSongMap(HashMap<Integer, Integer> songMap) {
            this.songMap = songMap;
        }
    }
}
