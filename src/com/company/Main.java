package com.company;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    static String lineSep = System.lineSeparator();
    //static File f = new File("C:\\Users\\HP\\filename.txt");
    static ReentrantLock writing = new ReentrantLock(true);
    static Condition fileExist = writing.newCondition();
    static Semaphore reading = new Semaphore(10);
    static String[] namesofFiles = {"mat1.txt", "mat2.txt", "mat3.txt","mat4.txt"};
    static File[] files = new File[namesofFiles.length];
    static String data = "19.01.2022";
    static String numberofAlbum = "104987";

    public static void main(String[] args) throws IOException {
        for(int i = 0; i<4; i++){
            files[i] = new File(namesofFiles[i]);
        }
        for (File file : files
             ) {
            file.createNewFile();
        }

        new Writer("1").start();
        new Writer("2").start();
        new Writer("3").start();
    }
}
