package com.ideaas.services.bean;

//NO LONGER USED
public class Image {

    private String name;
    private String url;
    private String extension;
    private String fileDownloadUri;
    private Boolean main;

    public Image(String url, String name, String extension, String fileDownloadUri, boolean main) {
        this.url = url;
        this.name = name;
        this.extension = extension;
        this.fileDownloadUri = fileDownloadUri;
        this.main = main;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

}
