/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dr
 */
public class ReadFile implements Runnable {

    String file;
    Set<String> words;

    ReadFile(Set<String> words, String filename) {

        this.words = words;
        this.file = filename;

    }

    public void readFile(String file, Set<String> words) {
        System.out.println("Starting to read " + file + " ...");
        Scanner input;
        try {
            input = new Scanner(new File(file + ".txt"));

            while (input.hasNext()) {
                String word = input.next();

                // not necessary to synchronize
                // if a synchronized set is used instead.
                synchronized (words) {
                  words.add(word);
                }

            }

            System.out.println("Done reading " + file + ".");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        readFile(file, words);

    }
}
