package com.project.assignment.model;

import java.text.Collator;
import java.util.*;


public class Sentence {
    private List<String> words;

    public Sentence(List<String> words) {
        this.words = new ArrayList<>(words);
    }

    public List<String> getWords(){
        return words;
    }

    public Sentence sortWords(){
        Collections.sort(words, Collator.getInstance());
        return this;
    }


}
