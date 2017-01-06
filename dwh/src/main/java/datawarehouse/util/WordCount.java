package datawarehouse.util;

import java.util.*;

/**
 * Created by hongjiayong on 2017/1/6.
 */
public class WordCount {
    public WordCount() {

    }

    private static final String delim = " ,.[]<>()+!?:;\"\\/-*";

    public HashMap<String, Integer> words = new HashMap<>();

    public void addText(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, delim);
        while (stringTokenizer.hasMoreTokens()) {
            String word = stringTokenizer.nextToken();
            if (!words.containsKey(word)) {
                words.put(word, 1);
            } else {
                words.put(word, words.get(word)+1);
            }
        }
    }

    public Map<String, Integer> getMap() {
        return words;
    }

    public List<Map.Entry<String, Integer>> getSortWord() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>();
        list.addAll(words.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue() - o2.getValue();
            }
        });
        return list;
    }
}
