package ru.ifmo.pga.news.stats.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class WordCounter {
    private Map<String, Integer> wordCount;

    public WordCounter() {
        wordCount = new TreeMap<>();
    }

    public void addWord(String word){
        changeValue(word, i -> i + 1);
    }

    public void removeWord(String word){
        changeValue(word, i -> i - 1);
    }

    public void clear(){
        wordCount = new TreeMap<>();
    }

    public boolean isEmpty(){
        return wordCount.isEmpty();
    }

    public int size(){
        return wordCount.size();
    }

    public List<String> getMostPopularWords(){
        List<Map.Entry<String, Integer>> list = new ArrayList<>(wordCount.entrySet());
        list.sort((entryA, entryB) -> entryB.getValue() - entryA.getValue());
        return list.stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private void changeValue(String word, IntFunction<Integer> fun) {
        wordCount.compute(word, (k, v) -> v != null ? fun.apply(v): 0);
    }

}
