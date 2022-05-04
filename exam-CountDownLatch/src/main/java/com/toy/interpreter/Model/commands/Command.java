package com.toy.interpreter.Model.commands;

public abstract class Command {
    private String key, description;

    public Command(String k, String desc) {
        this.key = k;
        this.description = desc;
    }

    public abstract void execute() throws Exception;

    public String getKey() {return this.key;}

    public String getDescription() {return this.description;}
}