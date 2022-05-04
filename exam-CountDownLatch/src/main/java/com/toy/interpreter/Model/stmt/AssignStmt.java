package com.toy.interpreter.Model.stmt;

import com.toy.interpreter.exceptions.StatementException;
import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.adt.IStack;
import com.toy.interpreter.Model.exp.Exp;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.Model.value.IValue;

public class AssignStmt implements IStmt {
    private String id;
    private Exp exp;

    public AssignStmt(String varname, Exp expp) {
        id = varname;
        exp = expp;
    }

    public PrgState execute(PrgState state) throws StatementException {
        IStack<IStmt> stk = state.getStk();
        IDict<String, IValue> symTbl = state.getSymTable();
        if (!symTbl.isDefined(id))
            throw new StatementException(id + " variable was not declared before");
        IValue val = exp.eval(symTbl,state.getHeap());
        IType typId = (symTbl.lookup(id)).getType();
        if (!(val.getType()).equals(typId))
            throw new StatementException("Variable type and expression type do not match");
        symTbl.update(id, val);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }

    public String toString() {
        return id + "=" + exp.toString();
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        IType varType = typeEnv.lookup(id);
        IType expType = exp.typeCheck(typeEnv);
        if (!varType.equals(expType))
            throw new StatementException("Assignment operands are of different types");
        return typeEnv;
    }

}
