package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {



    }


    public static String[][] loadCsvData (String pathname) {

        File tasksFile = new File(pathname);
        String line;
        String[] taskLines = new String[0];

        try (Scanner lineScanner = new Scanner(tasksFile)) {
            while (lineScanner.hasNextLine()) {
                line = lineScanner.nextLine();
                taskLines = appendArray(taskLines, line);
            }
        } catch (FileNotFoundException e){
            System.out.println("Couldn't find file");
        }

        String[][] tasksTable = new String[taskLines.length][3];
        String[] taskElements;

        for (int i = 0; i < taskLines.length; i++) {
            taskElements = taskLines[i].split(",");
            tasksTable[i] = taskElements;
        }

        return tasksTable;
    }


    public static String[] appendArray(String[] arrayToAppend, String appendment) {

        String[] resultArray = Arrays.copyOf(arrayToAppend,arrayToAppend.length + 1);
        resultArray[resultArray.length - 1] = appendment;

        return resultArray;
    }

}