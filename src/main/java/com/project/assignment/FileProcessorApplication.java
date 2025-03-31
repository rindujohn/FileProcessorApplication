package com.project.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class FileProcessorApplication {

	public static void main(String[] args) {SpringApplication.run(FileProcessorApplication.class, args);}

}
