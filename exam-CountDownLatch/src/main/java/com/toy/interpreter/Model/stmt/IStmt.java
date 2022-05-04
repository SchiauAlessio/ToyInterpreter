package com.toy.interpreter.Model.stmt;

import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.Model.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws Exception;
    IStmt deepCopy();
    IDict<String, IType> typeCheck(IDict<String, IType> typeEnv);
}
