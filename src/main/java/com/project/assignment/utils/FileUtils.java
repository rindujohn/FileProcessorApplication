package com.project.assignment.utils;

import com.project.assignment.model.Sentence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.assignment.utils.ApplicationConstants.*;

@Service
public class FileUtils {
    protected static final Logger logger = LogManager.getLogger();

    public List<Sentence> parseText(String text){
        logger.debug("Starting: Parsing Text");

        List<String> rawSentence = Arrays.stream(text.replaceAll(SPECIAL_CHAR_REGEX, "").split(SENTENCE_REGEX))
                .filter(x -> !x.trim().isEmpty()).collect(Collectors.toList());
        List<Sentence> sentences = new ArrayList<>();
        for (String s : rawSentence) {
            List<String> words = Arrays.stream(s.trim().split(SPACE_REGEX))
                    .filter(x -> !x.trim().isEmpty()).collect(Collectors.toList());
            sentences.add(new Sentence(words).sortWords());
        }
        return sentences;
    }
    public void fileTempWrite(String content, String fileExtension,FileParameter fileParameters) {
        logger.debug("Starting:  File Temp Write");

        String inputFileName = fileParameters.getInputFileName();
        String outfilePath = inputFileName.substring(0,inputFileName.lastIndexOf("/"));
        String outfile = outfilePath.concat("/").concat("temp").concat(fileExtension);
        FileWriter fileWriter = null;
        try {
            if (!fileParameters.firstFlag) {
                fileWriter = new FileWriter(outfile, false);
                fileWriter.write(content);
                fileWriter.flush();
                fileParameters.firstFlag = true;
            } else {
                fileWriter = new FileWriter(outfile, true);
                fileWriter.write(content);
                fileWriter.flush();
            }
        } catch (IOException e) {
                logger.error("IOException:TempFileWrite: error message{}", (Object) e.getStackTrace());
        } catch (Exception e) {
            logger.error("Exception:TempFileWrite: error message{}", (Object) e.getStackTrace());
        } finally {
            // Always close the reader, no matter what
            try {
                fileWriter.close();
            } catch (IOException e) {
                logger.error("IOException:TempFileWrite: error message{}", (Object) e.getStackTrace());
            }

        }

        logger.trace("Temp file {} created", outfile);


    }
    public String fileWrite(FileParameter fileParameters, String fileExtension) {
        logger.debug("Starting:  File Write");

        String inputFileName = fileParameters.getInputFileName();
        String outfilePath = inputFileName.substring(0,inputFileName.lastIndexOf("/"));
        String outfile = inputFileName.split("[.]")[0].concat(fileExtension);
        String tempfile = outfilePath.concat("/").concat("temp").concat(fileExtension);
        BufferedReader reader=null;
        try {
            FileWriter fileWriter = new FileWriter(outfile);
            reader = new BufferedReader(new FileReader(tempfile));
            fileWriter.write(String.valueOf(fileParameters.headerContent));
            fileWriter.write(NEWLINE);

            String line;
            while ((line = reader.readLine()) != null) {
                fileWriter.write(line);
                fileWriter.write(NEWLINE);
            }
            fileWriter.write(String.valueOf(fileParameters.trailerContent));
            fileWriter.close();
        }catch (IOException e) {
            logger.error("IOException:FileWrite: error message{}", (Object) e.getStackTrace());
        } catch (Exception e) {
            logger.error("Exception:FileWrite: error message{}", (Object) e.getStackTrace());
        } finally {
            // Always close the reader, no matter what
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("Resource closed successfully.");
                } catch (IOException e) {
                    System.err.println("Failed to close the resource: " + e.getMessage());
                }
            }
        logger.info("Output file {} created", outfile);
            return outfile;
        }
    }
}
