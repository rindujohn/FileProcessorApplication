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
public class XMLFileParser implements FileParser{

    protected static final Logger logger = LogManager.getLogger();

    @Autowired
    public FileUtils fileUtils;

    @Override
    public void fileParser(String text, FileParameter fileParameters) {
        logger.debug("Starting: XML generation");
        List<Sentence> sentences = fileUtils.parseText(text);
        String content = toXML(sentences,fileParameters);
        if(!content.isEmpty())
            fileUtils.fileTempWrite(content,XML_EXTENSION,fileParameters);
    }
    public String toXML(List<Sentence> sentences, FileParameter fileParameters){
        logger.debug("Starting: XML generation");
        StringBuilder xmlContent = new StringBuilder();
        logger.trace("No. of sentence tags: {}",sentences.size());

        for (Sentence s : sentences){
            xmlContent.append(XML_START_TAG_SENTENCE);
            for(String word: s.getWords()){
                xmlContent.append(XML_START_TAG_WORD).append(word).append(XML_END_TAG_WORD);
            }
            xmlContent.append(XML_END_TAG_SENTENCE);
        }
        if(!fileParameters.firstFlag){
            fileParameters.headerContent.append(XML_HEADER).append(XML_START_TAG_TEXT);
            fileParameters.trailerContent.append(XML_END_TAG_TEXT);
        }
        return xmlContent.toString();
    }
    public String xmlFileWrite(FileParameter fileParameters){
        return fileUtils.fileWrite(fileParameters,XML_EXTENSION);
    }
}
