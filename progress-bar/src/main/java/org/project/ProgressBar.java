package org.project;

import java.util.stream.Stream;

public class ProgressBar {
    static final String hell = "";

    public static void printMsgWithProgressBar(String message, int length, long timeInterval) {
        char incomplete = '░'; // U+2591 Unicode Character
        char complete = '█'; // U+2588 Unicode Character
        StringBuilder builder = new StringBuilder();
        Stream.generate(() -> incomplete).limit(length).forEach(builder::append);
        timeInterval *= 1000;
        timeInterval /= length;
        for (int i = 0; i < length; i++) {
            builder.replace(i, i + 1, String.valueOf(complete));
            String progressBar = "\r" + builder;
            System.out.print(progressBar);
            try {
                Thread.sleep(timeInterval);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static void main(String[] args) {
        printMsgWithProgressBar("", 100, 60);

    }
}