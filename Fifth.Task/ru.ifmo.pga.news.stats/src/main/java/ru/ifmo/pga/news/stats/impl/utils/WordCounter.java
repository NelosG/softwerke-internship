package ru.ifmo.pga.news.stats.impl.utils;

import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class WordCounter {
    protected final Set<String> IGNORED_WORDS;
    private Map<String, Integer> wordCount;

    {
        IGNORED_WORDS = new HashSet<>();
        IGNORED_WORDS.add("а");
        IGNORED_WORDS.add("за");
        IGNORED_WORDS.add("из");
        IGNORED_WORDS.add("на");
        IGNORED_WORDS.add("под");
        IGNORED_WORDS.add("с");
        IGNORED_WORDS.add("о");
        IGNORED_WORDS.add("без");
        IGNORED_WORDS.add("до");
        IGNORED_WORDS.add("к");
        IGNORED_WORDS.add("по");
        IGNORED_WORDS.add("от");
        IGNORED_WORDS.add("перед");
        IGNORED_WORDS.add("при");
        IGNORED_WORDS.add("через");
        IGNORED_WORDS.add("у");
        IGNORED_WORDS.add("над");
        IGNORED_WORDS.add("об");
        IGNORED_WORDS.add("про");
        IGNORED_WORDS.add("для");
        IGNORED_WORDS.add("в");
        IGNORED_WORDS.add("и");
        IGNORED_WORDS.add("не");
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

    public List<String> getMostPopularWords(int count) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(wordCount.entrySet());
        list.sort((entryA, entryB) -> entryB.getValue() - entryA.getValue());
        return list.stream()
                .map(Map.Entry::getKey)
                .limit(count)
                .collect(Collectors.toList());
    }

    private void changeValue(String word, IntFunction<Integer> fun) {
        if (!IGNORED_WORDS.contains(word)) {
            wordCount.compute(word, (k, v) -> v != null ? fun.apply(v) : 0);
        }
    }

}
