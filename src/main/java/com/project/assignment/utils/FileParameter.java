package com.project.assignment.utils;

public class FileParameter {
    public int rowCnt;
    public int colCnt;
    public boolean firstFlag;
    public StringBuilder headerContent ;
    public StringBuilder trailerContent ;

    public String inputFileName;

    public FileParameter(){
        firstFlag=false;
        headerContent = new StringBuilder();
        trailerContent = new StringBuilder();
        rowCnt=0;
        colCnt=0;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }
}
