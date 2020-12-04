package pl.coderslab;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class TaskManager {

    public static void main(String[] args){
        String filePath = "tasks.csv";
        String[][] tasks = new String[0][0];
    try{
        tasks = readTasks(filePath);
    }catch (IOException e){
        e.printStackTrace();
        System.out.println("Can't find source");
    }
        Scanner scan = new Scanner(System.in);
    String command;
    do {
        System.out.println(ConsoleColors.BLUE + "Please select an option: \n");
        System.out.println(ConsoleColors.RESET + "add\nremove\nlist\nexit");
        command = scan.nextLine();
        switch (command) {
            case "add":
                tasks = writeTasks(tasks);
                break;
            case "remove":
                    tasks = removeTask(tasks);
                break;
            case "list":
                showTasks(tasks);
                break;
            case "exit":
                System.out.println(ConsoleColors.RED +"Bye bye");
                    saveTasks("tasks.csv", tasks);
                    break;
                default:
                    System.out.println("Please select a correct option.");
                }
            }while (!command.equals("exit"));
        }




    public static String[][] readTasks(String filePath) throws IOException{
        Path path = Paths.get(filePath);
        String[][] tasks = new String[0][4];
        if(!Files.exists(path)){
            System.out.println("Brak pliku źródłowego");
        }else{
            for(String line: Files.readAllLines(path)){
                String taskNumber = tasks.length +",";
                line = taskNumber + line;
                String[] splitedLine = line.split(",");
                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                tasks[tasks.length - 1] = splitedLine;
            }
        }
        return tasks;
    }

    public static String[][] writeTasks(String[][] tasks){
        Scanner scanner = new Scanner(System.in);
        String[] newTask = new String[4];
        newTask[0] = Integer.toString(tasks.length);
        System.out.println("Please add task description");
        String line = scanner.nextLine();
        newTask[1]=line;
        System.out.println("Please add task due date");
        line = scanner.nextLine();
        newTask[2]=line;
        System.out.println("Is your task important?true/false");
        line = scanner.nextLine();
        newTask[3]=line;
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length-1] = newTask;
        return tasks;
    }

    public static String[][] removeTask(String[][] tasks)throws IndexOutOfBoundsException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Jaki numer ma zadanie które chcesz usunąć?");
        String number = Integer.toString(getNum(scanner, tasks.length - 1));
        String[][] afterRmv = new String[tasks.length - 1][];
        int indexToAdd = 0;
        for (String[] task : tasks) {
            if (!task[0].equals(number)) {
                afterRmv[indexToAdd] = task;
                indexToAdd++;
            }
        }
        for (int i = 0; i < afterRmv.length; i++) {
            afterRmv[i][0]= Integer.toString(i);
        }
        return afterRmv;
    }

    public static int getNum(Scanner scanner, int max){
        String toParse = scanner.next();
        while(!NumberUtils.isParsable(toParse)||Integer.parseInt(toParse)>max||Integer.parseInt(toParse)<0){
            System.out.println("Podaj liczbę między 0 a " + max);
            toParse = scanner.next();
        }
        int num = Integer.parseInt(toParse);
        return num;
    }

    public static void saveTasks(String filePath, String[][] tasks){
        Path path = Paths.get(filePath);
        List<String> toWrite = new ArrayList<>();
        for (String[] task:tasks) {
            String line = "";
            for (int i = 0; i < task.length; i++) {
                if(i>0 && i<3){
                     line += task[i] +", ";
                }else if(i==3){
                    line += task[i];
                }
            }
            toWrite.add(line);
        }
        try {
            Files.write(path, toWrite);
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Nie można zapisać pliku");
        }
    }

    public static void showTasks(String[][] tasks){
        for (String[] task:tasks) {
            String toShow = task[0]+" : " + task[1] + " " + task[2] + " " + task[3];
            System.out.println(toShow);
        }
    }

}
