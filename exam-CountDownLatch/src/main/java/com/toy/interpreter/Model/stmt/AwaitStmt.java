package com.toy.interpreter.Model.stmt;

import com.toy.interpreter.exceptions.StatementException;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.adt.ILatchTable;
import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.Model.types.IntType;
import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.IntValue;

public class AwaitStmt implements IStmt{
    private String var;

    public AwaitStmt(String v){var=v;}

    @Override
    public PrgState execute(PrgState state) throws StatementException {

        IDict<String, IValue> table=state.getSymTable();
        ILatchTable latch = state.getLatchTable();

        if(!table.isDefined(var))throw new StatementException("Variable not defined");

        IntValue fi = (IntValue)table.lookup(var);
        int foundIndex=fi.getValue();

        if(!latch.containsKey(foundIndex)) throw new StatementException("Index not in LatchTable");
        if(latch.get(foundIndex)!=0)
            state.getStk().push(this);

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(var);
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws StatementException {
        if(typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else throw new StatementException("Var is not int");
    }

    @Override
    public String toString() {
        return "await("+var+")";
    }
}