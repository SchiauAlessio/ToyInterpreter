package com.toy.interpreter.Model.commands;

import com.toy.interpreter.Controller.Controller;

public class RunCommand extends Command {
    private Controller ctrl;

    public RunCommand(String k, String desc, Controller c) {super(k, desc); this.ctrl = c;}

    @Override
    public void execute() throws Exception {
        ctrl.allStep();
    }
}
