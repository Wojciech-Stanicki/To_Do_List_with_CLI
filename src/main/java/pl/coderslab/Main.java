package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        printInteface();
    }


    public static void printInteface() {

        System.out.println("Please select an option:");
        System.out.println(
                """
                add
                remove
                list
                exit"""
        );

        String input = "";
        Scanner inputScanner = new Scanner(System.in);

        while (!isValidOption(input)) {
            try {
                input = inputScanner.nextLine().strip();
                if (!isValidOption(input)) {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Please select a correct option.");
            }
        }
        inputScanner.close();

        switch (input) {
            case "add" -> addTask();
            case "remove" -> removeTask();
            case "list" -> listTasks();
            case "exit" -> System.out.println("Bye, bye");
        }
    }


    public static void addTask() {
        System.out.println("add");
    }


    public static void removeTask() {
        System.out.println("remove");
    }


    public static void listTasks() {
        System.out.println("list");
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


    public static Boolean isValidOption(String input) {
        return (input.equals("add") || input.equals("remove") || input.equals("list") || input.equals("exit"));
    }
}