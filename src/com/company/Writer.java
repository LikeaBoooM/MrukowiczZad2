package com.company;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Writer extends Thread {
    String name;

    public Writer(String name) throws IOException {
        this.name = name;
    }

    public void run() {
        if (this.name == "1") {
            boolean printOnce = true;
            while (Main.reading.availablePermits() < 10) {
                if (printOnce) {
                    System.out.println("Some reader is reading now, i have to wait");
                    printOnce = false;
                }
            }
            boolean fileExst = Main.files[0].exists();
            String fileContent = Main.data;
            Main.writing.lock();
            if (fileExst) {
                try {
                    FileWriter fileWriter = new FileWriter(Main.files[0]);
                    for (int i = 0; i < 20; i++) {
                        fileWriter.write(fileContent + Main.lineSep);
                        System.out.println("Pisarz nr: " + this.name + " pisze do pliku: " + Main.files[0].getName() + " " + Main.data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Main.writing.unlock();
                }
            }
        } else if (this.name == "2" || this.name == "3") {
            boolean printOnce = true;
            while (Main.reading.availablePermits() < 10) {
                if (printOnce) {
                    System.out.println("Some reader is reading now, i have to wait");
                    printOnce = false;
                }
            }
            boolean fileExst = Main.files[2].exists();
            String fileContent = Main.numberofAlbum;
            Main.writing.lock();
            if (fileExst) {
                try {
                    FileWriter fileWriter = new FileWriter(Main.files[2]);
                    for (int i = 0; i < 2; i++) {
                        fileWriter.write(fileContent + Main.lineSep);
                        System.out.println("Pisarz nr: " + this.name + " pisze do pliku: " + Main.files[2].getName() + " " + Main.numberofAlbum);
                    }
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Main.writing.unlock();
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

