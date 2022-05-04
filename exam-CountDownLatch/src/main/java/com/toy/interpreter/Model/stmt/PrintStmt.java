package com.toy.interpreter.Model.stmt;


import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.exceptions.StatementException;
import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.adt.IList;
import com.toy.interpreter.Model.exp.Exp;
import com.toy.interpreter.Model.value.IValue;

public class PrintStmt implements IStmt{
    private Exp exp;

    public PrintStmt(Exp v) {
        exp = v;
    }

    public PrgState execute(PrgState state) throws StatementException {
        IList<IValue> out = state.getOut();
        out.add(exp.eval(state.getSymTable(),state.getHeap()));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    public String toString() {
        return "print(" + exp.toString() + ")";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        exp.typeCheck(typeEnv);
        return typeEnv;
    }
}
