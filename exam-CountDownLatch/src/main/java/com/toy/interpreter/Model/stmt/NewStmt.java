package com.toy.interpreter.Model.stmt;

import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.adt.IHeap;
import com.toy.interpreter.exceptions.StatementException;
import com.toy.interpreter.Model.exp.Exp;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.Model.types.RefType;
import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.RefValue;

public class NewStmt implements IStmt{
    Exp expression;
    String id;

    public NewStmt(String var, Exp e) {expression = e; id = var;}

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IHeap heap = state.getHeap();
        IDict<String, IValue> table = state.getSymTable();

        if (!table.isDefined(id))
            throw new StatementException("Variable is not defined");

        IValue ivalue = table.lookup(id);
        if (!(ivalue.getType() instanceof RefType))
            throw new StatementException("Variable is not RefType");

        IValue newValue = expression.eval(table, heap);
        RefValue value = (RefValue)ivalue;
        if (!value.getLocationType().equals(newValue.getType()))
            throw new StatementException("Variable and expression types don't match");

        int address = heap.add(newValue);
        RefValue nValue = new RefValue(address, value.getLocationType());
        table.update(id, nValue);

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(id,expression.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + id + "," + expression.toString() + ")";
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
