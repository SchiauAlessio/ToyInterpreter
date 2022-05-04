package com.toy.interpreter.Repo;
import com.toy.interpreter.Model.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepo {
    void addPrg(PrgState newPrg);
    List<PrgState> getPrgList();
    void popPrg();
    void setFile(String s);
    void logPrgStateExec(PrgState state) throws IOException;
    void setPrgList(List<PrgState> states);
    }
