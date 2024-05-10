package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) {

        String[][] taskTable = loadCsvData("tasks.csv");
        String command = "";

        while (!command.equals("exit")) {
            printInteface();
            command = getCommand();
            System.out.print("\n");
            taskTable = takeAction(command, taskTable);
            System.out.print("\n");
        }
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
                System.out.print("\n");
                System.out.println("Invalid command.");
                printInteface();
            }
        }


        return input;
    }

    public static String[][] takeAction(String action, String[][] taskTable) {

        try {
            taskTable = switch (action) {
                case "add" -> addTask(taskTable);
                case "remove" -> removeTask(taskTable);
                case "list" -> listTasks(taskTable);
                case "exit" -> saveAndExit(taskTable);
                default -> throw new InputMismatchException();
            };
        } catch (InputMismatchException e) {
            System.out.println("Something went terribly wrong! Unexpected command was passed.");
            System.out.println("Please try again.");
        }

            return taskTable;
    }


    public static String[][] addTask(String[][] taskTable) {

        Scanner inputScanner = new Scanner(System.in);
        String inputTaskName;

        while (true) {
            System.out.println("Please add task description:");
            inputTaskName = inputScanner.nextLine().strip();
            if (!inputTaskName.isBlank()) {
                break;
            } else {
                System.out.println("Task name can't be blank.");
            }
        }

        String inputDate;
        Pattern dateFormat = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        Matcher inputMatcher;

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

        String importanceInput;
        Pattern importanceFormat = Pattern.compile("true|false");

        while (true) {
            System.out.println("Is your task important? true/false");
            importanceInput = inputScanner.nextLine().strip().toLowerCase();
            inputMatcher = importanceFormat.matcher(importanceInput);
            if (inputMatcher.matches()) {
                break;
            } else {
                System.out.println("Task importance must be either true or false.");
            }
        }

        String[] newTask = {inputTaskName, inputDate, importanceInput};
        taskTable = Arrays.copyOf(taskTable, taskTable.length + 1);
        taskTable[taskTable.length - 1] = newTask;

        return taskTable;
    }


    public static String[][] removeTask(String[][] taskTable) {
        System.out.println("remove");
        return taskTable;
    }


    public static String[][] listTasks(String[][] taskTable) {

        int longestTaskDescriptionLength = 0;

        for (String[] task : taskTable) {
            if (task[0].length() > longestTaskDescriptionLength) {
                longestTaskDescriptionLength = task[0].length();
            }
        }

        int alignmentSpaces;
        StringBuilder singleTaskPresentation = new StringBuilder();

        for (int i = 0; i < taskTable.length; i++) {
            String taskName = taskTable[i][0];
            String taskDueDate = taskTable[i][1];
            String taskImportance = taskTable[i][2];
            alignmentSpaces = longestTaskDescriptionLength - taskTable[i][0].length();

            singleTaskPresentation.append(i).append(" : ");
            singleTaskPresentation.append(taskName).append(" ".repeat(alignmentSpaces)).append("  ");
            singleTaskPresentation.append(taskDueDate).append("  ");
            singleTaskPresentation.append(taskImportance);

            System.out.println(singleTaskPresentation);
            singleTaskPresentation.setLength(0);
        }

        return taskTable;
    }


    public static String[][] saveAndExit(String[][] taskTable) {
        System.out.println("saveAndExit");
        return taskTable;
    }


    public static String[][] loadCsvData(String pathname) {

        File tasksFile = new File(pathname);
        String line;
        String[] taskLines = new String[0];

        try (Scanner lineScanner = new Scanner(tasksFile)) {
            while (lineScanner.hasNextLine()) {
                line = lineScanner.nextLine();
                taskLines = appendArray(taskLines, line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find file");
        }

        String[][] tasksTable = new String[taskLines.length][3];
        String[] taskElements;

        for (int i = 0; i < taskLines.length; i++) {
            taskElements = taskLines[i].split(",");
            for (int j = 0; j < taskElements.length; j++) {
                taskElements[j] = taskElements[j].strip();
            }
            tasksTable[i] = taskElements;
        }

        return tasksTable;
    }


    public static String[] appendArray(String[] arrayToAppend, String appendment) {

        String[] resultArray = Arrays.copyOf(arrayToAppend, arrayToAppend.length + 1);
        resultArray[resultArray.length - 1] = appendment;

        return resultArray;
    }


    public static Boolean isValidCommand(String input) {
        return (input.equals("add") || input.equals("remove") || input.equals("list") || input.equals("exit"));
    }
}