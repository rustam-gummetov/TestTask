import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    static Commands commands = new CommandsImpl();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Для справки по командам введите 'help'");
            System.out.println("Введите комманду:");
            String command = in.nextLine();
            switch (Arrays.stream(command.split(" ")).limit(1).collect(Collectors.toList()).get(0)) {
                case "add": {
                    commands.addPoint(command);
                    break;
                }
                case "print": {
                    if(command.split(" ").length > 1) {
                        commands.print(command);
                    }
                    else {
                        commands.print();
                    }
                    break;
                }
                case "remove": {
                    commands.remove(command);
                    break;
                }
                case "clear": {
                    commands.clear();
                    break;
                }
                case "help": {
                    commands.help();
                    break;
                }
                case "exit": {
                    exit = true;
                    break;
                }
                default: {
                    System.out.println("Команда введена некорректно");
                }
            }
        }

    }
}
