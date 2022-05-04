package com.toy.interpreter.Model.adt;

import com.toy.interpreter.exceptions.ADTException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Dict<T1, T2> implements IDict<T1, T2> {
    Map<T1, T2> dictionary;

    public Dict() {
        this.dictionary = new ConcurrentHashMap<>();
    }

    public Dict(IDict<T1, T2> original) {
        dictionary = new ConcurrentHashMap<>();
        dictionary = original.getContent().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public T2 lookup(T1 k) {
        if(dictionary.isEmpty())
            throw new ADTException("Dictionary is empty!");
        return this.dictionary.get(k);
    }

    @Override
    public boolean isDefined(T1 k) {
        return dictionary.containsKey(k);
    }

    @Override
    public void update(T1 k, T2 val) {
        dictionary.replace(k, val);
    }

    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    @Override
    public void remove(T1 v1) {
        this.dictionary.remove(v1);
    }

    @Override
    public void add(T1 k, T2 v) {
        dictionary.put(k, v);
    }

    public String toString() {
        StringBuilder strbld = new StringBuilder();
        for (Map.Entry<T1, T2> e : dictionary.entrySet()) {
            T1 key = e.getKey();
            T2 value = e.getValue();
            strbld.append(key + ": " + value + ", ");
        }
        return strbld.toString();
    }
    @Override
    public String toStringFile() {
        String out = "SymTable:\n";
        for (T1 id : this.dictionary.keySet()) {
            out = out + id.toString() + " --> ";
            out = out + this.dictionary.get(id).toString();
            out += "\n";
        }
        return out;
    }

    @Override
    public Map<T1, T2> getContent() {return this.dictionary;}

    @Override
    public Set<Map.Entry<T1, T2>> entrySet() {
        return dictionary.entrySet();
    }

    @Override
    public Collection<T2> getAllValues() {
        return dictionary.values();
    }


}