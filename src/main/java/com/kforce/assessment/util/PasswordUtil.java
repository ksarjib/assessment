//package com.kforce.assessment.util;
//
//import com.kforce.assessment.domain.User;
//import com.kforce.assessment.exception.InvalidRequestException;
//
//public class PasswordUtil {
//    public static void validateUserRequest(User user) {
//        StringBuilder message = new StringBuilder();
//        boolean invalid = false;
//        if(user.getUsername() == null || user.getUsername() == "") {
//            message.append("Username cannot be empty.");
//            invalid = true;
//        }
//        if(user.getPassword() == null || user.getPassword() == "") {
//            message.append("Password cannot be empty. ");
//            invalid = true;
//        } else if (user.getPassword().length() <= 8) {
//            message.append("Password needs to be greater than 8 characters.");
//        }
//        if (!user.getPassword().contains("#") &&
//                !user.getUsername().contains("_") &&
//                !user.getPassword().contains("%") &&
//                !user.getUsername().contains("$") &&
//                !user.getUsername().contains(".")
//        ) {
//            invalid = true;
//            message.append(("Password must contain one of these special characters '_ # $ % .' "));
//        }
//        boolean containsNumber = false;
//
//        // checking for digits (0-9)
//        for (int i = 0; i <= 9; i++) {
//
//            // to convert int to string
//            String str1 = Integer.toString(i);
//
//            if (user.getPassword().contains(str1)) {
//                containsNumber = true;
//            }
//        }
//
//        if(!containsNumber) {
//            message.append("Password must contain at least one number. ");
//            invalid = true;
//        }
//        boolean containsCapital = false;
//
//        // checking for capital letters
//        for (int i = 65; i <= 90; i++) {
//
//            char c = (char)i;
//
//            String str1 = Character.toString(c);
//            if (user.getPassword().contains(str1)) {
//                containsCapital = true;
//            }
//        }
//
//        if(!containsCapital) {
//            message.append("Password must contain at least one capital letter. ");
//            invalid = true;
//        }
//
//        if(invalid) {
//            throw new InvalidRequestException(message.toString());
//        }
//
//    }
//}
