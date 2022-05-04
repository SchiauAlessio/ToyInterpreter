package com.toy.interpreter.Model.stmt;

import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.adt.IHeap;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.exceptions.StatementException;
import com.toy.interpreter.Model.exp.Exp;
import com.toy.interpreter.Model.types.RefType;
import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.RefValue;

public class WriteHeap implements IStmt{
    Exp expression;
    String id;

    public WriteHeap(String var, Exp e) {expression = e; id = var;}

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> table = state.getSymTable();
        IHeap heap = state.getHeap();

        if (!table.isDefined(id))
            throw new StatementException("Variable is not defined");

        IValue ivalue = table.lookup(id);
        if (!(ivalue.getType() instanceof RefType))
            throw new StatementException("Variable is not a RefType");

        RefValue rValue = (RefValue)ivalue;
        if (!heap.isDefined(rValue.getAddr()))
            throw new StatementException("Variable is not on the heap");

        IValue newValue = expression.eval(table, heap);
        if (!newValue.getType().equals(rValue.getLocationType()))
            throw new StatementException("Variable and expression types don't match");

        heap.update(rValue.getAddr(), newValue);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeap(id,expression.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(" + id + "," + expression.toString() + ")";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        IType varType = typeEnv.lookup(id);
        IType expType = expression.typeCheck(typeEnv);
        if (!varType.equals(new RefType(expType)))
            throw new StatementException("Variable and expression types don't match");
        return typeEnv;
    }
}
