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

        String command = getCommand();
        takeAction(command, taskTable);
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
    }


    public static String getCommand() {

        String input;
        Scanner inputScanner = new Scanner(System.in);

        while (true) {
            input = inputScanner.nextLine().strip();
            if (isValidCommand(input)) {
                break;
            } else {
                System.out.println("Please select a correct option.");
            }
        }


        return input;
    }

    public static String[][] takeAction(String action, String[][] taskTable) {

        switch (action) {
            case "add" -> addTask(taskTable);
            case "remove" -> removeTask(taskTable);
            case "list" -> listTasks(taskTable);
            case "exit" -> saveAndExit(taskTable);
        }

        return taskTable;
    }


    public static String[][] addTask(String[][] taskTable) {


        String[] newTask = {"Undefined Task", "0000-00-00", "false"};
        Scanner inputScanner = new Scanner(System.in);
        String inputTaskName;

        while (true) {
            System.out.println("Please add task description:");
            inputTaskName = inputScanner.nextLine().strip();
            if (!inputTaskName.isBlank()){
                break;
            } else {
                System.out.println("Task name can't be blank.");
            }
        }

        Pattern dateFormat = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        Matcher inputMatcher;
        String inputDate;

        while (true) {
            System.out.println("Please add task due date:");
            inputDate = inputScanner.nextLine().strip();
            inputMatcher = dateFormat.matcher(inputDate);
            if (inputMatcher.matches()) {
                break;
            } else {
                System.out.println("Wrong date format - YYYY-MM-DD is required:");
            }
        }



        return taskTable;
    }


    public static String[][] removeTask(String[][] taskTable) {
        System.out.println("remove");
        return taskTable;
    }


    public static String[][] listTasks(String[][] taskTable) {
        System.out.println("list");
        return taskTable;
    }


    public static String[][] saveAndExit(String[][] taskTable) {
        System.out.println("saveAndExit");
        return taskTable;
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


    public static Boolean isValidCommand(String input) {
        return (input.equals("add") || input.equals("remove") || input.equals("list") || input.equals("exit"));
    }
}