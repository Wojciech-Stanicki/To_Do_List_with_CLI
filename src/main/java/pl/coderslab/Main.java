package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) {

        String[][] taskTable = loadCsvData("tasks.csv");
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

        String input;


        try (Scanner inputScanner = new Scanner(System.in)) {
            while (true) {
                input = inputScanner.nextLine().strip();
                if (isValidOption(input)) {
                    break;
                } else {
                    System.out.println("Please select a correct option.");
                }
            }
        }

        switch (input) {
            case "add" -> addTask();
            case "remove" -> removeTask();
            case "list" -> listTasks();
            case "exit" -> System.out.println("Bye, bye");
        }
    }


    public static void addTask() {

        System.out.println("Please add task description:");

        String[] newTask = {"Undefined Task", "0000-00-00", "false"};

        try (Scanner inputScanner = new Scanner(System.in)) {
            newTask[0] = inputScanner.nextLine().strip();
        }

        System.out.println("Please add task due date:");

        Pattern dateFormat = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        Matcher inputMatcher;
        String inputDate;

        try (Scanner inputScanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Please add task due date");
                inputDate = inputScanner.nextLine().strip();
                inputMatcher = dateFormat.matcher(inputDate);
                if (inputMatcher.matches()) {
                    break;
                } else {
                    System.out.println("Wrong date format - YYYY-MM-DD is required:");
                }
            }
        }
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