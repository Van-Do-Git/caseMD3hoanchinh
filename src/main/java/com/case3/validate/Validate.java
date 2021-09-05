package com.case3.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validate {
    private static Validate instance;
    public static String regexPhone = "^[0-9]{9,10}$";
    public static String regexPassword = "^[0-9]{6,8}$";
    public static String regexUsername = "^[\\w]{4,8}$";

    private Validate() {
    }

    public static Validate getInstance() {
        if (instance == null) {
            instance = new Validate();
        }
        return instance;
    }

    public boolean validate(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}

