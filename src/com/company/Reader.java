package com.company;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Reader extends Thread {
    String name;

    public Reader(String name) {
        this.name = name;
    }

    public void run() {
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
            if (Main.files[0].exists()) {
                Main.writing.lock();
                Main.reading.release();
                Main.fileExist.await();
                Main.writing.unlock();
                Main.reading.acquire();
            }
            FileReader fileReader = new FileReader(Main.files[0]);
            String fileContent = "";
            Scanner scanner = new Scanner(fileReader);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
                sb.append(Main.lineSep);
            }
            fileContent = sb.toString();
            fileReader.close();
            System.out.println("Czytelnik: "+this.name + " " + "odczytałz pliku nr:" + Main.files[0].getName()+ " " + fileContent);
            } catch (InterruptedException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
