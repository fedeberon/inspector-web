package com.ideaas.services.bean;

public final class RequestUtil {

    public static String EMPTY = "";
    private static String LEFT = "[";
    private static String RIGTH = "]";

    public String buildFormatValuesSelected(String value){
        String returnValue = EMPTY;

        String[] words = value.split(",");
        if(words.length < 1) return value;

        returnValue = returnValue.concat(LEFT);

        for(String s : words){
            returnValue = returnValue.concat("'".concat(s).concat("',"));
        }

        returnValue = returnValue.substring(0, returnValue.length() - 1);
        returnValue = returnValue.concat(RIGTH);

        return returnValue;
    }
}
