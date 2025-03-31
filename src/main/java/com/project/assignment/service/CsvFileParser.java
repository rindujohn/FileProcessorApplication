package com.project.assignment.service;

import com.project.assignment.model.Sentence;
import com.project.assignment.utils.FileParameter;
import com.project.assignment.utils.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.assignment.utils.ApplicationConstants.*;

@Service
public class CsvFileParser implements FileParser{

    protected static final Logger logger = LogManager.getLogger();

    @Autowired
    public FileUtils fileUtils;
    @Override
    public void fileParser(String text, FileParameter fileParameters) {
        logger.debug("Starting: CSV generation");

        List<Sentence> sentences = fileUtils.parseText(text);
        String content = toCSV(sentences,fileParameters);
        if(!content.isEmpty())
            fileUtils.fileTempWrite(content,CSV_EXTENSION,fileParameters);
    }
    public String toCSV(List<Sentence> sentences,FileParameter fileParameters){
        logger.debug("Starting: CSV generation");
        int colCount = 0;
        logger.trace("No. of sentences: {}",sentences.size());
        StringBuilder csvText = new StringBuilder();
        for (Sentence s : sentences){
            fileParameters.rowCnt++;
            csvText.append(CSV_SENTENCE).append(fileParameters.rowCnt);
            int stmLen = s.getWords().size();
            colCount = Math.max(colCount, stmLen);
            for(String word: s.getWords()){
                csvText.append(CSV_COMMA).append(word);
                if(fileParameters.colCnt < colCount)
                    fileParameters.headerContent.append(CSV_WORD).append(++fileParameters.colCnt);
            }
            csvText.append(NEWLINE);
        }
        return csvText.toString();
    }

    public String csvFileWrite(FileParameter fileParameters){
        return fileUtils.fileWrite(fileParameters,CSV_EXTENSION);
    }
}
