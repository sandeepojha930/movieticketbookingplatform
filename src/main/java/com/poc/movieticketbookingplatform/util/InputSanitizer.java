package com.poc.movieticketbookingplatform.util;

public class InputSanitizer {

    public static String sanitize(String input) {
        if (input == null) {
            return null;
        }
        // Remove any HTML tags and trim whitespace
        return input.replaceAll("<[^>]*>", "").trim();
    }
}