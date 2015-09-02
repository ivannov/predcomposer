package com.nosoftskills.predcomposer.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ivan St. Ivanov
 */
public class PasswordHashUtil {

    public static String hashPassword(String originalPassword) {
        try {
            MessageDigest md =  MessageDigest.getInstance("SHA");
            return new String(md.digest(originalPassword.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            return  originalPassword;
        }
    }

}
