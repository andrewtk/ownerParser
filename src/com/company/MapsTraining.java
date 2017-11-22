package com.company;

import java.util.HashMap;
import java.util.Map;

public class MapsTraining {
    public static void main(String[] args) {

        Map<String, Integer> nameOfFiles = new HashMap<String, Integer>();
        String s = "here we are here we have very long string with long words and check it it we have have have";
        String[] myWords = s.split(" ");
        //проходим каждую подстроку

        Integer counter = 1;
        for (Integer i=0; i<myWords.length;i++) {
            if (!nameOfFiles.keySet().contains(myWords[i])) {
                nameOfFiles.put(myWords[i],1);
            } else {
                counter = nameOfFiles.get(myWords[i])+1;
            }

            nameOfFiles.put(myWords[i], counter);
            counter=1;

        }
        for (Map.Entry entry : nameOfFiles.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: "
                    + entry.getValue());
        }
        System.out.println();

    }

}

