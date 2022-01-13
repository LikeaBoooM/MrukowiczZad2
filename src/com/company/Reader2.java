package com.company;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Reader2 extends Thread {
    String name;
    int count = 0;
    int maxCount = 50;

    public Reader2(String name) {
        this.name = name;
    }

    public void run() {
        while (count<maxCount) {
            boolean printOnce = true;
            while (Main.writing.isLocked()) {
                if (printOnce) {
                    System.out.println("Some writer is writing, i have to wait!");
                    printOnce = false;
                }
            }
            try {
                Main.reading.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                if (Main.files[2].exists()) {
                    Main.writing.lock();
                }
                FileReader fileReader = new FileReader(Main.files[2]);
                String fileContent = "";
                Scanner scanner = new Scanner(fileReader);
                StringBuilder sb = new StringBuilder();
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine());
                    sb.append(Main.lineSep);
                }
                fileContent = sb.toString();
                fileReader.close();
                System.out.println("Czytelnik: "+this.name + " " + "odczytał pliku nr:" + Main.files[2].getName()+ " " + fileContent);
                fileContent = "";
                count++;
                Thread.sleep(100);
                } catch (InterruptedException | FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                e.printStackTrace();
            }
            Main.reading.release();
            Main.writing.unlock();
        }
    }
}
