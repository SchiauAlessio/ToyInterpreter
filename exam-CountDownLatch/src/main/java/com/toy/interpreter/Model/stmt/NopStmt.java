package com.toy.interpreter.Model.stmt;

import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.exceptions.StatementException;
import com.toy.interpreter.Model.PrgState;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementException {
        return null;
    }

    @Override
    public String toString(){
        return "no operation";
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        return typeEnv;
    }
}
