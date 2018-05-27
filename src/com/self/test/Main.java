package com.self.test;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Test");
        String str = "Bachelor of Engineering";
        System.out.println("Original phrase: " + str);

        String[] words = str.split(" ");
        int length = words.length;
        if (length > 0) {
            str = "";
            for (int i = 0; i < length; i++) {
                str = str + words[length - i - 1];
                if (i < length){
                    str = str + " ";
                }
            }
        }
        System.out.println("Fixed phrase: " + str);
    }
}
