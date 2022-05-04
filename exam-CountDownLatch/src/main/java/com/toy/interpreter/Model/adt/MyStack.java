package com.toy.interpreter.Model.adt;
import com.toy.interpreter.exceptions.ADTException;

import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    private Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public T pop() {
        if (stack.isEmpty())
            throw new ADTException("Stack is empty!");
        return stack.pop();
    }

    @Override
    public void push(T e) {
        stack.push(e);
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    public String toString() {
        Stack<T> copy = (Stack<T>) stack.clone();
        StringBuilder stringBuilder = new StringBuilder();
        if (!copy.isEmpty()) {
            String str = copy.peek().toString();
            copy.pop();
            stringBuilder.append(str + " ");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toStringFile() {
        String out = "Stack:\n";
        for (T elem : this.stack) {
            out = out + elem.toString();
            out += "\n";
        }
        return out;
    }

    @Override
    public Stack<T> getContent() {
        return stack;
    }


}
