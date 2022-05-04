package com.toy.interpreter.Controller;

import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Repo.Repo;
import com.toy.interpreter.exceptions.ControllerException;
import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Repo.IRepo;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepo repo;
    ExecutorService executor;
    GarbageCollector collector;

    public Controller(IRepo r) {
        this.repo = r;
        this.collector=new GarbageCollector();
    }

    public Repo getRepo(){
        return (Repo)repo;
    }

    List<PrgState> removeCompletedPrg(List<PrgState> in) {
        return in.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAll(List<PrgState> list) throws Exception {
        for (PrgState prg : list)
            repo.logPrgStateExec(prg);

        list.forEach(System.out::println);

        List<Callable<PrgState>> callList = list.stream()
                .map(p -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newList = executor.invokeAll(callList).stream()
                .map(future -> {try {return future.get();}
                catch (Exception e) {throw new ControllerException(e.getMessage());}})
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        list.addAll(newList);
        for (PrgState prg : list)
            repo.logPrgStateExec(prg);
        list.forEach(System.out::println);
        repo.setPrgList(list);
    }

    public void allStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> list = removeCompletedPrg(repo.getPrgList());
        while (list.size() > 0) {
            Map<Integer, IValue> newHeap = collector.safeGarbageCollector(collector.allAddresses(repo.getPrgList()),
                    repo.getPrgList().get(0).getHeap().getContent());
            repo.getPrgList()
                    .forEach(prg -> prg.getHeap().setContent(newHeap));
            oneStepForAll(list);
            list = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(list);
    }

    public void oneStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programsList = removeCompletedPrg(repo.getPrgList());
        if (programsList.size() > 0) {
            collector.conservativeGarbageCollector(programsList);
            oneStepForAll(programsList);
        }
        executor.shutdownNow();
        repo.setPrgList(programsList);
    }
}

