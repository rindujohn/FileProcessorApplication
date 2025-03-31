package com.project.assignment.controller;

import com.project.assignment.service.CsvFileParser;
import com.project.assignment.service.FileParser;
import com.project.assignment.service.XMLFileParser;
import com.project.assignment.utils.FileParameter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static com.project.assignment.utils.ApplicationConstants.*;


@RestController
@RequestMapping("/assignment")
@Log4j2
public class TextController {

    protected static final Logger logger = LogManager.getLogger();
    @Value("${file.input.name}")
    private String inputFilePath;

    private XMLFileParser xmlFileParser;
    private CsvFileParser csvFileParser;

    public TextController(XMLFileParser xmlFileParser, CsvFileParser csvFileParser) {
        this.xmlFileParser = xmlFileParser;
        this.csvFileParser = csvFileParser;
    }

    @PostMapping("/xmlparser")
    public ResponseEntity<String> xmlParser(@RequestParam("file") String inputFilePath) {
        try {
            logger.info("XML Parsing started for file {}",inputFilePath);
            FileParameter fileParameters = new FileParameter();
            fileParameters.setInputFileName(inputFilePath);
            processFile(inputFilePath,xmlFileParser,fileParameters);
            String outputFile = xmlFileParser.xmlFileWrite(fileParameters);
            return ResponseEntity.status(201).body("Output File Created :" + outputFile);
        } catch (IOException e) {
            logger.error("IOException:XMLParser: error message{}", (Object) e.getStackTrace());
        } catch (Exception e) {
            logger.error("Exception:XMLParser: error message{}", (Object) e.getStackTrace());
        }

        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/csvparser")
    public ResponseEntity<?> csvParser(@RequestParam("file") String inputFilePath) {
        try {
            logger.info("CSV Parsing started for file {}",inputFilePath);
            FileParameter fileParameters = new FileParameter();
            fileParameters.setInputFileName(inputFilePath);
            processFile(inputFilePath,csvFileParser,fileParameters);
            String outputFile = csvFileParser.csvFileWrite(fileParameters);
            return ResponseEntity.status(201).body("Output File Created :" + outputFile);

        } catch (IOException e) {
            logger.error("IOException:CSVParser: error message{}", (Object) e.getStackTrace());

        } catch (Exception e) {
            logger.error("Exception:CSVParser: error message{}", (Object) e.getStackTrace());

        }
        return ResponseEntity.internalServerError().build();
    }


    private void processFile(String inputFilePath, FileParser converter, FileParameter fileParameters) throws IOException {
        String batch = "";
        try (SeekableByteChannel ch = Files.newByteChannel(Paths.get(inputFilePath), StandardOpenOption.READ)) {
            ByteBuffer bf = ByteBuffer.allocate(1000);
            while (ch.read(bf) > 0) {
                bf.flip();
                batch = batch.concat(StandardCharsets.UTF_8.decode(bf).toString()).replaceAll(SPECIAL_CHAR_REGEX,"");
                if(batch.endsWith(".")||(batch.endsWith("?"))||(batch.endsWith("!"))) {
                    converter.fileParser(batch,fileParameters);
                    batch="";
                } else {
                    batch.concat(" ");
                }
                bf.clear();
            }
            converter.fileParser(batch,fileParameters);
        }

    }
}
