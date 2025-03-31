package com.project.assignment;

import com.project.assignment.controller.TextController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TextControllerTest {

    @Autowired
    private TextController textController;

    @Test
    void testConvertToXML() {
        String file = "/Users/bethel/Downloads/assignment/src/test/resources/sample.txt";

        ResponseEntity<?> responseEntity = textController.xmlParser(file);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(StringUtils.hasText("Mary"));
        assertTrue(StringUtils.hasText("Aesop"));
    }

    @Test
    void testConvertToCSV() {
        String file = "/Users/bethel/Downloads/assignment/src/test/resources/sample.txt";

        ResponseEntity<?> responseEntity = textController.csvParser(file);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(StringUtils.hasText("Mary"));
        assertTrue(StringUtils.hasText("Aesop"));
    }
    @Test
    void testLargeFileToCSV() {
        String file = "/Users/bethel/Downloads/assignment/src/test/resources/largefile.txt";

        ResponseEntity<?> responseEntity = textController.csvParser(file);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

    }
    @Test
    void testLargeFileToXML() {
        String file = "/Users/bethel/Downloads/assignment/src/test/resources/largefile.txt";
        ResponseEntity<?> responseEntity = textController.xmlParser(file);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

    }

}
