package com.toy.interpreter.Model.stmt;

import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.exceptions.FileException;
import com.toy.interpreter.Model.exp.Exp;
import com.toy.interpreter.Model.types.StringType;
import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.StringValue;
import com.toy.interpreter.exceptions.StatementException;

import java.io.BufferedReader;
import java.io.FileReader;

public class openRFile implements IStmt{
    Exp expression;

    public openRFile(Exp e) {this.expression = e;}

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> table = state.getSymTable();
        IDict<StringValue, BufferedReader> files = state.getFileTable();

        IValue name = expression.eval(table, state.getHeap());
        if (!name.getType().equals(new StringType()))
            throw new FileException("Expression is not a string");

        StringValue vname = (StringValue)name;
        if (files.isDefined(vname))
            throw new FileException("File already exists");
        FileReader fr = new FileReader(vname.getValue());
        BufferedReader br = new BufferedReader(fr);
        files.add(vname, br);

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new openRFile(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "openRFile(" + this.expression + ")";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        IType expType = expression.typeCheck(typeEnv);
        if (!expType.equals(new StringType()))
            throw new StatementException("Expression is not a string");
        return typeEnv;
    }
}
