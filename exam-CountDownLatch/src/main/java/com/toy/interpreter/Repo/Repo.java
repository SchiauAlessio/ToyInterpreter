package com.toy.interpreter.Repo;
import com.toy.interpreter.Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Repo implements IRepo {

    List<PrgState> myPrgStates;
    String logFilePath;

    public Repo() {myPrgStates = new ArrayList<>();}

    public void setFile(String s) {logFilePath = s;}

    public void logPrgStateExec(PrgState state) throws IOException {
        PrintWriter logfile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logfile.write(state.toStringFile());
        logfile.close();
    }

    @Override
    public void setPrgList(List<PrgState> states) {myPrgStates = states;}

    @Override
    public List<PrgState> getPrgList() {
        return this.myPrgStates;
    }

    @Override
    public void popPrg() {
        myPrgStates.remove(myPrgStates.size()-1);
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }
}
