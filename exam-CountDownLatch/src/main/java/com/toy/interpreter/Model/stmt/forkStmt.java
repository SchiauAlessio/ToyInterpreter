package com.toy.interpreter.Model.stmt;

import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.adt.Dict;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.Model.value.IValue;

public class forkStmt implements IStmt{
    IStmt statement;

    public forkStmt(IStmt stmt) {statement = stmt;}

    @Override
    public PrgState execute(PrgState state) throws Exception {
        PrgState newState = new PrgState(statement);
        newState.setFileTable(state.getFileTable());
        newState.setHeap(state.getHeap());
        newState.setOutput(state.getOut());
        IDict<String, IValue> table = new Dict<>(state.getSymTable());
        newState.setSymTable(table);
        newState.setLatchTable(state.getLatchTable());
        return newState;
    }

    @Override
    public IStmt deepCopy() {
        return new forkStmt(statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        return statement.typeCheck(typeEnv);
    }
}
