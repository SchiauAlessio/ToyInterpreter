package com.toy.interpreter.View;

import com.toy.interpreter.Model.commands.Command;
import com.toy.interpreter.Model.commands.RunCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu() {this.commands = new HashMap<>();}

    public void addCommand(Command c) {this.commands.put(c.getKey(), c);}

    public void printMenu() {
        for (Command c : commands.values()) {
            String line = String.format("%s: %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }

    public void run() {
        Scanner scan = new Scanner(System.in);
        String key;
        Command cmd;
        while(true) {
            this.printMenu();
            System.out.print("Enter option: ");
            key = scan.nextLine();
            cmd = commands.get(key);
            if (cmd == null) {
                System.out.println("Invalid option");
                continue;
            }
            try {
                cmd.execute();
                if (cmd instanceof RunCommand)
                    this.commands.remove(key);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

