package com.toy.interpreter.Model.stmt;

import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.adt.IHeap;
import com.toy.interpreter.Model.adt.IStack;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.exceptions.StatementException;
import com.toy.interpreter.Model.exp.Exp;
import com.toy.interpreter.Model.types.BoolType;
import com.toy.interpreter.Model.value.BoolValue;
import com.toy.interpreter.Model.value.IValue;

public class WhileStmt implements IStmt{
    Exp expression;
    IStmt statement;

    public WhileStmt(Exp e, IStmt stmt) {expression = e; statement = stmt;}

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getStk();
        IDict<String, IValue> table = state.getSymTable();
        IHeap heap = state.getHeap();

        IValue cond = expression.eval(table, heap);

        if (!cond.getType().equals(new BoolType()))
            throw new StatementException("Conditional expression is not boolean");

        if (((BoolValue)cond).getValue()) {
            stack.push(new WhileStmt(expression, statement));
            stack.push(statement);
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(expression.deepCopy(),statement.deepCopy());
    }

    @Override
    public String toString() {
        return "(while (" + expression.toString() + ") " + statement.toString() + ")";
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) {
        IType expType = expression.typeCheck(typeEnv);
        if (!expType.equals(new BoolType()))
            throw new StatementException("While condition is not boolean");
        statement.typeCheck(typeEnv);
        return typeEnv;
    }
}
