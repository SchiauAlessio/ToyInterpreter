package com.toy.interpreter.Model.stmt;

import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.exceptions.StatementException;
import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.adt.IStack;

public class CompStmt implements IStmt{
    private IStmt first;
    private IStmt snd;

    public CompStmt(IStmt f, IStmt s) {
        first = f;
        snd = s;
    }

    public PrgState execute(PrgState state) throws StatementException {
        IStack<IStmt> stk = state.getStk();
        stk.push(snd);
        stk.push(first);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(),snd.deepCopy());
    }

    public String toString() {
        return "(" + first.toString() + "; " + snd.toString() + ")";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        return snd.typeCheck(first.typeCheck(typeEnv));
    }
}
