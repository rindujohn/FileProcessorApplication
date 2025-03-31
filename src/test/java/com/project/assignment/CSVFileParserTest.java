package com.project.assignment;

import com.project.assignment.model.Sentence;
import com.project.assignment.service.CsvFileParser;
import com.project.assignment.utils.FileParameter;
import com.project.assignment.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CSVFileParserTest {

    @Test
    public void testFileParser_GeneratesCorrectCSV() {
        // Arrange
        String text = "Hello world. This is a test. Hope Dr. has prepared it! ";
        FileParameter fileParameters = new FileParameter();
        fileParameters.rowCnt = 0;
        fileParameters.colCnt = 0;
        CsvFileParser csvFileParser = new CsvFileParser();
        FileUtils mockFileUtils = mock(FileUtils.class);
        csvFileParser.fileUtils = mockFileUtils;


        List<Sentence> sentences = Arrays.asList(
                new Sentence(Arrays.asList("Hello", "world")),
                new Sentence(Arrays.asList("a", "is", "test", "This")),
                new Sentence(Arrays.asList("Dr.", "has", "Hope", "it","prepared"))
        );
        when(mockFileUtils.parseText(text)).thenReturn(sentences);

        // Act
        csvFileParser.fileParser(text, fileParameters);

        // Assert
        verify(mockFileUtils, times(1)).fileTempWrite(
                contains("Sentence 1, Hello, world\nSentence 2, a, is, test, This\nSentence 3, Dr., has, Hope, it, prepared"),
                eq(".csv"),
                eq(fileParameters)
        );
    }

    @Test
    public void testFileParser_EmptyInput() {
        // Arrange
        String text = "";
        FileParameter fileParameters = new FileParameter();
        CsvFileParser csvFileParser = new CsvFileParser();
        FileUtils mockFileUtils = mock(FileUtils.class);
        csvFileParser.fileUtils = mockFileUtils;

        when(mockFileUtils.parseText(text)).thenReturn(new ArrayList<>());

        // Act
        csvFileParser.fileParser(text, fileParameters);

        // Assert
        verify(mockFileUtils, never()).fileTempWrite(anyString(), eq(".csv"), eq(fileParameters));
    }

    @Test
    public void testToCSV_HeaderValidation() {
        // Arrange
        FileParameter fileParameters = new FileParameter();
        List<Sentence> sentences = Arrays.asList(
                new Sentence(Arrays.asList("Word1", "Word2")),
                new Sentence(Arrays.asList("Word1", "Word2", "Word3")),
                new Sentence(Arrays.asList("Word4", "Word5"))
        );

        CsvFileParser csvFileParser = new CsvFileParser();

        // Act
        String csvContent = csvFileParser.toCSV(sentences, fileParameters);

        // Assert
        assertEquals("Sentence 1, Word1, Word2\nSentence 2, Word1, Word2, Word3\nSentence 3, Word4, Word5\n", csvContent);
        assertEquals(", Word 1, Word 2, Word 3", fileParameters.headerContent.toString());
    }


    @Test
    public void testFileParser_LargeInput() {
        // Arrange
        StringBuilder largeText = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            largeText.append("Sentence ").append(i).append(". ");
        }
        FileParameter fileParameters = new FileParameter();
        CsvFileParser csvFileParser = new CsvFileParser();
        FileUtils mockFileUtils = mock(FileUtils.class);
        csvFileParser.fileUtils = mockFileUtils;

        List<Sentence> sentences = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            sentences.add(new Sentence(Arrays.asList("Sentence", String.valueOf(i))));
        }
        when(mockFileUtils.parseText(largeText.toString())).thenReturn(sentences);

        // Act
        csvFileParser.fileParser(largeText.toString(), fileParameters);

        // Assert
        verify(mockFileUtils, times(1)).fileTempWrite(anyString(), eq(".csv"), eq(fileParameters));
    }

}
