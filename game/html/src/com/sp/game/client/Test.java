package com.sp.game.client;

/**
 * Created by Troy on 9/27/2015.
 */
public class Test {



    public static void main(String[] args) {
        String n = "aksndjkn2344";
        System.out.println(n);
        reverse(n);
        System.out.println(reverse(n));
    }

    private static String reverse(String n) {

        char[] arr = n.toCharArray();

        for (int i=0; i< arr.length / 2; ++i) {
            char temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }

        return String.copyValueOf(arr);
    }

}
