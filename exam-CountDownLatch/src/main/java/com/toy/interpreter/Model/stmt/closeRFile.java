package com.toy.interpreter.Model.stmt;

import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.exceptions.FileException;
import com.toy.interpreter.Model.exp.Exp;
import com.toy.interpreter.Model.types.StringType;
import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.StringValue;

import java.io.BufferedReader;

public class closeRFile implements IStmt{
    Exp expression;

    public closeRFile(Exp e) {this.expression = e;}

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> table = state.getSymTable();
        IDict<StringValue, BufferedReader> files = state.getFileTable();

        IValue string = expression.eval(table,state.getHeap());
        if (!string.getType().equals(new StringType()))
            throw new FileException("Expression is not a string");

        StringValue value = (StringValue)string;
        if (!files.isDefined(value))
            throw new FileException("File does not exist");

        BufferedReader br = files.lookup(value);

        br.close();

        files.remove(value);

        return null;
    }

    public IStmt deepCopy(){
        return new closeRFile(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "closeRFile(" + this.expression + ")";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        IType expType = expression.typeCheck(typeEnv);
        if (!expType.equals(new StringType()))
            throw new FileException("Expression is not a string");
        return typeEnv;
    }
}
