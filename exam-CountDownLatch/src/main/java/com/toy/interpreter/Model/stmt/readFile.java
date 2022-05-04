package com.toy.interpreter.Model.stmt;

import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.exceptions.FileException;
import com.toy.interpreter.Model.exp.Exp;
import com.toy.interpreter.Model.types.IntType;
import com.toy.interpreter.Model.types.StringType;
import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.IntValue;
import com.toy.interpreter.Model.value.StringValue;
import com.toy.interpreter.exceptions.StatementException;

import java.io.BufferedReader;

public class readFile implements IStmt{
    Exp expression;
    String id;

    public readFile(Exp e, String var_name) {this.expression = e; this.id = var_name;}

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> table = state.getSymTable();
        IDict<StringValue, BufferedReader> file = state.getFileTable();

        if (!table.isDefined(id) || !table.lookup(id).getType().equals(new IntType()))
            throw new FileException("Variable is already defined or not an integer");

        IValue string = expression.eval(table,state.getHeap());
        if (!string.getType().equals(new StringType()))
            throw new FileException("Expression is not a string");

        StringValue value = (StringValue)string;
        if (!file.isDefined(value))
            throw new FileException("File is not defined");

        BufferedReader br = file.lookup(value);
        String read = br.readLine();
        int result;
        if (read.isEmpty())
            result = 0;
        else
            result = Integer.parseInt(read);

        table.add(id, new IntValue(result));

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new readFile(this.expression.deepCopy(),this.id);
    }

    @Override
    public String toString() {
        return "readFile(" + this.expression + ")";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        IType varType = typeEnv.lookup(id);
        IType expType = expression.typeCheck(typeEnv);
        if (!varType.equals(new IntType()))
            throw new StatementException("Variable is not an integer");
        if (!expType.equals(new StringType()))
            throw new StatementException("Expression is not a string");
        return typeEnv;
    }
}
