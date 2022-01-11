package com.company;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Reader1 extends Thread {
    String name;

    public Reader1(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println("Gówno");
        boolean printOnce = true;
        while (Main.writing.isLocked()) {
            if (printOnce) {
                System.out.println("Some writer is writing, i have to wait!");
                printOnce = false;
            }
        }
        try {
            Main.reading.acquire();
            //System.out.println("Gówno1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if (Main.files[0].exists()) {
                Main.writing.lock();
                Main.reading.release();
                Main.writing.unlock();
                Main.reading.acquire();
            }
            FileReader fileReader = new FileReader(Main.files[0]);
            String fileContent = "";
            Scanner scanner = new Scanner(fileReader);
            StringBuilder sb = new StringBuilder();
            for(int i=0 ; i<30; i++){
                while (scanner.hasNextLine()) {
                    System.out.println("SCANN : " + scanner.nextLine());
                    sb.append(scanner.nextLine());
                    sb.append(Main.lineSep);
                }
                fileContent = sb.toString();
                fileReader.close();
                System.out.println("Czytelnik: "+this.name + " " + "odczytał pliku " + Main.files[0].getName()+ " " + fileContent);
                fileContent = "";
                Thread.sleep(1000);
            }
        } catch (InterruptedException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
