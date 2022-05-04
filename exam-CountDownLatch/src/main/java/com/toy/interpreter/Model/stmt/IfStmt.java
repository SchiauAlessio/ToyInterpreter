package com.toy.interpreter.Model.stmt;

import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.exceptions.StatementException;
import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.exp.Exp;
import com.toy.interpreter.Model.types.BoolType;
import com.toy.interpreter.Model.value.BoolValue;
import com.toy.interpreter.Model.value.IValue;

public class IfStmt implements IStmt{
    private Exp expression;
    private IStmt then_argument;
    private IStmt else_argument;

    public IfStmt(Exp ex, IStmt tn, IStmt els) {
        expression = ex;
        then_argument = tn;
        else_argument = els;
    }

    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> dictionary = state.getSymTable();

        IValue expval = expression.eval(dictionary,state.getHeap());

        if (!expval.getType().equals(new BoolType())) {
            throw new StatementException("Expression is not boolean!");
        }
        if (((BoolValue) expval).getValue()) {
            return then_argument.execute(state);
        } else {
            return else_argument.execute(state);
        }
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(expression.deepCopy(),then_argument.deepCopy(),else_argument.deepCopy());
    }

    public String toString() {
        return "(IF(" + expression.toString() + ") THEN(" + then_argument.toString() + ")ELSE(" + else_argument.toString() + "))";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        IType expType = expression.typeCheck(typeEnv);
        if (!expType.equals(new BoolType()))
            throw new StatementException("If condition is not boolean");
        then_argument.typeCheck(typeEnv);
        else_argument.typeCheck(typeEnv);
        return typeEnv;
    }
}
