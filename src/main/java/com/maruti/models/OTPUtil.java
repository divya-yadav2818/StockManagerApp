package com.maruti.models;

import java.util.Random;

public class OTPUtil {
    public static String generateOTP(){
        Random r = new Random();
        int otp = 100000 + r.nextInt(900000);
        return String.valueOf(otp);
    }
}
