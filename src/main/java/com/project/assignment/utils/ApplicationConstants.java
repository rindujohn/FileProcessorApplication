package com.project.assignment.utils;

public class ApplicationConstants {

    private ApplicationConstants(){}
    public static final String SPECIAL_CHAR_REGEX = "[`~@#$%^&*()_|+\\-=;:'\",<>{}]";
    public static final String SENTENCE_REGEX = "(?<!\\s[A-Za-z].)[.]+|[?!]";
    public static final String SPACE_REGEX = "\\s+";
    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n";
    public static final String XML_START_TAG_SENTENCE = "<sentence>\n";
    public static final String XML_START_TAG_TEXT = "<text>";
    public static final String XML_START_TAG_WORD = "<word>";
    public static final String XML_END_TAG_SENTENCE = "</sentence>\n";
    public static final String XML_END_TAG_TEXT = "</text>\n";
    public static final String XML_END_TAG_WORD = "</word>\n";
    public static final String CSV_SENTENCE = "Sentence ";
    public static final String CSV_WORD = ", Word ";
    public static final String CSV_COMMA = ", ";
    public static final String NEWLINE = "\n";
    public static final String XML_EXTENSION = ".xml";
    public static final String CSV_EXTENSION = ".csv";
    public static final String CSV_TYPE = "CSV";
    public static final String XML_TYPE = "XML";



}
