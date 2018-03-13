/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dr
 */
public class FileReaderBasic {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args)
            throws FileNotFoundException {
        long startTime = System.currentTimeMillis();

        readAllFiles();

        long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;
        System.out.println("Took " + elapsed + " ms.");
    }

    public static void readAllFiles() throws FileNotFoundException {
        
            Set<String> words = new HashSet<>();
            String[] files = {"bible", "warpeace", "hamlet", "tomsawyer"};
            ArrayList<Thread> workerThreads = new ArrayList<>();

            for (String filename : files) {
                            readFile(filename, words);

            }

            System.out.println("There are " + words.size()
                    + " unique words.");
       
    }

    public static void readFile(String file, Set<String> words)
            throws FileNotFoundException {
        System.out.println("Starting to read " + file + " ...");
        Scanner input = new Scanner(new File(file + ".txt"));
        while (input.hasNext()) {
            String word = input.next();
           
                words.add(word);
            
        }
        System.out.println("Done reading " + file + ".");
    }

}
