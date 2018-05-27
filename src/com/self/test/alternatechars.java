package com.self.test;

public class alternatechars {
    public static void main(String[] args) {
        char[] arr = "you got beautiful eyes".toCharArray();

        // The array is going to shrink - so iterate from beginning of the array
        int iCurrIndex=0;
        int iMovIndex = 0;
        int iLength = arr.length;
        int iCharStep = 2;
        for (iMovIndex = 0; (iMovIndex + 1) < iLength && iCurrIndex < iLength;) {
            if ((iMovIndex + 1) % iCharStep == 0){
                iMovIndex++; // increment just the moving pointer
            }
            arr[iCurrIndex++] = arr[iMovIndex++];
        }

        if ((iCurrIndex + 1) < iLength) {
            arr[iCurrIndex] = '\0'; // add null terminator at the end of the string?
        }
        System.out.println(arr);
    }
}
