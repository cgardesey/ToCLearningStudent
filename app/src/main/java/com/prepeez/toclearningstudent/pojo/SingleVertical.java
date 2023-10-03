package com.prepeez.toclearningstudent.pojo;



public class SingleVertical {

    private String header, subHeader;

    public SingleVertical( ) {

    }

    public SingleVertical(String subHeader, String header) {
        this.header = header;
        this.subHeader = subHeader;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSubHeader() {
        return subHeader;
    }

    public void setSubHeader(String subHeader) {
        this.subHeader = subHeader;
    }
}
