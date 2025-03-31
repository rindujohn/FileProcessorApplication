package com.project.assignment;

import com.project.assignment.model.Sentence;
import com.project.assignment.service.CsvFileParser;
import com.project.assignment.service.XMLFileParser;
import com.project.assignment.utils.FileParameter;
import com.project.assignment.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class XMLFileParserTest {

    @Test
    public void testFileParser_GeneratesCorrectXML() {
        // Arrange
        String text = "Hello world. This is a test. Hope Dr. has prepared it! ";
        FileParameter fileParameters = new FileParameter();
        fileParameters.rowCnt = 0;
        fileParameters.colCnt = 0;
        XMLFileParser xmlFileParser = new XMLFileParser();
        FileUtils mockFileUtils = mock(FileUtils.class);
        xmlFileParser.fileUtils = mockFileUtils;


        List<Sentence> sentences = Arrays.asList(
                new Sentence(Arrays.asList("Hello", "world")),
                new Sentence(Arrays.asList("a", "is", "test", "This")),
                new Sentence(Arrays.asList("Dr.", "has", "Hope", "it","prepared"))
        );
        when(mockFileUtils.parseText(text)).thenReturn(sentences);

        // Act
        xmlFileParser.fileParser(text, fileParameters);

        // Assert
        verify(mockFileUtils, times(1)).fileTempWrite(
                contains("""
                        <sentence>
                        <word>Hello</word>
                        <word>world</word>
                        </sentence>
                        <sentence>
                        <word>a</word>
                        <word>is</word>
                        <word>test</word>
                        <word>This</word>
                        </sentence>
                        <sentence>
                        <word>Dr.</word>
                        <word>has</word>
                        <word>Hope</word>
                        <word>it</word>
                        <word>prepared</word>
                        </sentence>"""),
                eq(".xml"),
                eq(fileParameters)
        );
    }

    @Test
    public void testFileParser_EmptyInput() {
        // Arrange
        String text = "";
        FileParameter fileParameters = new FileParameter();
        XMLFileParser xmlFileParser = new XMLFileParser();
        FileUtils mockFileUtils = mock(FileUtils.class);
        xmlFileParser.fileUtils = mockFileUtils;

        when(mockFileUtils.parseText(text)).thenReturn(new ArrayList<>());

        // Act
        xmlFileParser.fileParser(text, fileParameters);

        // Assert
        verify(mockFileUtils, never()).fileTempWrite(anyString(), eq(".xml"), eq(fileParameters));
    }


    @Test
    public void testFileParser_LargeInput() {
        // Arrange
        StringBuilder largeText = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            largeText.append("Sentence ").append(i).append(". ");
        }
        FileParameter fileParameters = new FileParameter();
        XMLFileParser xmlFileParser = new XMLFileParser();
        FileUtils mockFileUtils = mock(FileUtils.class);
        xmlFileParser.fileUtils = mockFileUtils;

        List<Sentence> sentences = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            sentences.add(new Sentence(Arrays.asList("Sentence", String.valueOf(i))));
        }
        when(mockFileUtils.parseText(largeText.toString())).thenReturn(sentences);

        // Act
        xmlFileParser.fileParser(largeText.toString(), fileParameters);

        // Assert
        verify(mockFileUtils, times(1)).fileTempWrite(anyString(), eq(".xml"), eq(fileParameters));
    }

}
