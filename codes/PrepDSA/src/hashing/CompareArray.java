package hashing;

import java.util.*;

public class CompareArray {
}

class Solution {
    static void relativeSort(int[] a1, int[] a2) {
        // code here
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0; i< a1.length; i++){
            map.put(a1[i], map.getOrDefault(a1[i],0)+1);

        }
        ArrayList<Integer> sorted = new ArrayList<>();
        for(int i=0; i<a2.length; i++){
            int value = map.get(a2[i]);
            for(int j=0; j<value; j++){
                sorted.add(a2[i]);
            }
            map.remove(a2[i]);
        }
        List<Integer> a3 = map.keySet().stream().toList();
        Collections.sort(a3);
        for(int i=0; i< a3.size(); i++){
            sorted.add(a3.get(i));
        }
        for(int i=0; i<sorted.size(); i++){
            a1[i] = sorted.get(i);
        }

    }
}