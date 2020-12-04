package pl.coderslab;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


public class TaskManager {

    public static void main(String[] args){
        String filePath = "tasks.csv";
        String[][] tasks = new String[0][0];
    try{
        tasks = readTasks(filePath);
    }catch (IOException e){
        e.printStackTrace();
        System.out.println("nie znalazłem pliku");
    }
        Scanner scan = new Scanner(System.in);
    String command;
    do {
        System.out.println("Please select an option: \nadd\nremove\nlist\nexit");
        command = scan.nextLine();
        switch (command) {
            case "add":
                tasks = writeTasks(tasks);
                break;
            case "remove":
                break;
            case "list":
                for (String[] task : tasks) {
                    System.out.println(String.join(" ", task));
                }
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
                String taskNumber = Integer.toString(tasks.length + 1);
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
        newTask[0] = (tasks.length + 1) + ":a ";
        System.out.println("Please add task description");
        String line = scanner.nextLine();
        newTask[1]=line;
        System.out.println("Please add task due date");
        line = scanner.nextLine();
        newTask[2]=line;
        System.out.println("Is your task important?");
        line = scanner.nextLine();
        newTask[3]=line;
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length-1] = newTask;
        return tasks;
    }

    public static String[][] removeTask(String[][] tasks){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Jaki numer ma zadanie które chcesz usunąć?");
        return null;
    }
    public static int getNum(Scanner scanner){
        while(!scanner.hasNextInt()){
            scanner.next();
            System.out.println("Podaj liczbę");

        }
        int num = scanner.nextInt();
        return num;
    }
}
