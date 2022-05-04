package com.toy.interpreter.Model.adt;

import com.toy.interpreter.exceptions.ADTException;

import java.util.Stack;

public interface IStack<T> {
    T pop();

    void push(T e) throws ADTException;

    boolean isEmpty();

    String toStringFile();

    Stack<T> getContent();
}

