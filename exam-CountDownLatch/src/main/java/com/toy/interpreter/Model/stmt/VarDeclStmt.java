package com.toy.interpreter.Model.stmt;


import com.toy.interpreter.exceptions.StatementException;
import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.Model.value.IValue;

public class VarDeclStmt implements IStmt{
    private String name;
    private IType typ;

    public VarDeclStmt(String varname, IType t) {
        name = varname;
        typ = t;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        IDict<String, IValue> symTbl = state.getSymTable();
        if (symTbl.isDefined(name)) {
            throw new StatementException("Variable '" + name + "' is already defined!");
        }
        symTbl.add(name, typ.defaultValue());
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name,typ);
    }

    public String toString() {
        return typ + " " + name;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        typeEnv.add(name, typ);
        return typeEnv;
    }
}
