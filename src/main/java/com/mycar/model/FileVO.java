package com.mycar.model;

import static com.mycar.utility.Constants.CSV_MIME;
import static com.mycar.utility.Constants.EXL_MIME;

public class FileVO {
    private String fileName;
    private String fileExt;
    private long fileSize;
    private String mimeType;
    private String filePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    //application/vnd.ms-excel","text/csv"}
    public boolean isCSV()
    {
        return CSV_MIME.equalsIgnoreCase(this.mimeType)?true:false;
    }

    public boolean isEXL()
    {
        return EXL_MIME.equalsIgnoreCase(this.mimeType)?true:false;
    }

}
