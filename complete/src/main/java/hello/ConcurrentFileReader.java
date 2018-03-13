/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dr
 */
public class ConcurrentFileReader {

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

        //Set<String> words = Collections.synchronizedSet(new HashSet<>());
        Set<String> words = new HashSet<>();  // requires synchronizing
        
        String[] files = {"bible", "warpeace", "hamlet", "tomsawyer"};

        ArrayList<Thread> workerThreads = new ArrayList<>();

        for (String filename : files) {

            ReadFile rf = new ReadFile(words, filename);

            //this would just result in serial execution
            //rf.run();
                
            // make a new thread object    
            Thread th = new Thread(rf);

            workerThreads.add(th);  // save it for later

            th.start();
            // this would also just wait for the thread
                // to finish, resulting in serial execution
                // th.join();
        }

        for (Thread th : workerThreads) {
            try {
                th.join();  // wait for thread th to finish
            } catch (InterruptedException ex) {
                Logger.getLogger(ConcurrentFileReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // now all the threads have finished

        System.out.println("There are " + words.size()
                + " unique words.");

    }

}
