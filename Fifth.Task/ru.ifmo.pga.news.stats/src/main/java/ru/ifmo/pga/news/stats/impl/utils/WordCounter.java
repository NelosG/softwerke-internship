package ru.ifmo.pga.news.stats.impl.utils;


import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;


public class WordCounter {
    protected final Set<String> IGNORED_WORDS;
    protected Map<String, Integer> wordCount;

    {
        IGNORED_WORDS = new HashSet<>();
        IGNORED_WORDS.add("за");
        IGNORED_WORDS.add("из");
        IGNORED_WORDS.add("на");
        IGNORED_WORDS.add("под");
        IGNORED_WORDS.add("без");
        IGNORED_WORDS.add("до");
        IGNORED_WORDS.add("по");
        IGNORED_WORDS.add("от");
        IGNORED_WORDS.add("перед");
        IGNORED_WORDS.add("при");
        IGNORED_WORDS.add("через");
        IGNORED_WORDS.add("над");
        IGNORED_WORDS.add("об");
        IGNORED_WORDS.add("со");
        IGNORED_WORDS.add("про");
        IGNORED_WORDS.add("для");
        IGNORED_WORDS.add("не");
        IGNORED_WORDS.add("во");
        IGNORED_WORDS.add("что");
        IGNORED_WORDS.add("где");
        IGNORED_WORDS.add("когда");
        IGNORED_WORDS.add("сколько");
        IGNORED_WORDS.add("почему");
        IGNORED_WORDS.add("ей");
        IGNORED_WORDS.add("ее");
        IGNORED_WORDS.add("ему");
        IGNORED_WORDS.add("его");
        IGNORED_WORDS.add("нам");
        IGNORED_WORDS.add("нас");
        IGNORED_WORDS.add("вам");
        IGNORED_WORDS.add("вас");
        IGNORED_WORDS.add("их");
        IGNORED_WORDS.add("им");
    }

    public WordCounter() {
        wordCount = new TreeMap<>();
    }

    public void addWord(String word) {
        changeValue(word, i -> i + 1);
    }

    public void removeWord(String word) {
        changeValue(word, i -> i - 1);
    }

    public void clear() {
        wordCount = new TreeMap<>();
    }

    public boolean isEmpty() {
        return wordCount.isEmpty();
    }

    public int size() {
        return wordCount.size();
    }

    public List<String> getStats(int count) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(wordCount.entrySet());
        list.sort((entryA, entryB) -> entryB.getValue() - entryA.getValue());
        return list.stream()
                .map(Map.Entry::getKey)
                .limit(count)
                .collect(Collectors.toList());
    }

    protected void changeValue(String word, IntFunction<Integer> fun) {
        if (!IGNORED_WORDS.contains(word) && word.length() > 1) {
            wordCount.compute(word, (k, v) -> v != null ? fun.apply(v) : 0);
        }
    }

}
