package com.toy.interpreter.View;

import com.toy.interpreter.Controller.Controller;
import com.toy.interpreter.Main;
import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.types.*;
import com.toy.interpreter.Model.value.BoolValue;
import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.IntValue;
import com.toy.interpreter.Model.value.StringValue;
import com.toy.interpreter.Repo.IRepo;
import com.toy.interpreter.Repo.Repo;
import com.toy.interpreter.Model.adt.*;
import com.toy.interpreter.Model.exp.*;
import com.toy.interpreter.Model.stmt.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
//import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PrgListController implements Initializable {

    @FXML
    private Button executeButton;

    @FXML
    private ListView<String> prgListView;

    private RunPrgController runPrgController;
    private java.util.List<IStmt> prgStatesList;

    public static PrgState createPrgState(IStmt stmt) {
        IStack<IStmt> stk = new MyStack<>();
        IDict<String, IValue> symtbl = new Dict<>();
        IList<IValue> ot = new List<>();
        return new PrgState(stmt);
    }

    public static IStmt getEx1() {
        // ex 1:  int v; v = 2; Print(v)
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ConstExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
    }

    public static IStmt getEx2() {
        // ex 2: a=2+3*5;b=a+1;Print(b)
        return new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+', new ConstExp(new IntValue(2)), new ArithExp('*',
                        new ConstExp(new IntValue(3)), new ConstExp(new IntValue(5))))), new CompStmt(
                        new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ConstExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));
    }

    public static IStmt getEx3() {
        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        return new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()), new CompStmt(new AssignStmt("a", new ConstExp(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ConstExp(new IntValue(2))),
                        new AssignStmt("v", new ConstExp(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));
    }

    public static IStmt getEx4() {
        //ex 4: a=4

        return new AssignStmt("a", new ConstExp(new IntValue(4)));
    }

    public static IStmt getEx5() {
        //string varf;varf="test.in";openRFile(varf);
        //int varc;readFile(varf,varc);print(varc); readFile(varf,varc);print(varc)   closeRFile(varf)
        return new CompStmt(new VarDeclStmt("varf",new StringType()),
                new CompStmt(new AssignStmt("varf", new ConstExp(new StringValue("src\\main\\java\\com\\toy\\interpreter\\Data\\test.in"))),
                        new CompStmt(new openRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc",new IntType()),
                                        new CompStmt(new readFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new readFile(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                new closeRFile(new VarExp("varf"))))))))));
    }

    public static IStmt getEx6() {
        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        return new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ConstExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a")))))));
    }

    public static IStmt getEx7() {
        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        return new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ConstExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new heapRead(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+',
                                                        new heapRead(new heapRead(new VarExp("a"))), new ConstExp(new IntValue(5)))))))));
    }

    public static IStmt getEx8() {
        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        return new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ConstExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new heapRead(new VarExp("v"))),
                                new CompStmt(new WriteHeap("v", new ConstExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new heapRead(new VarExp("v")), new ConstExp(new IntValue(5))))))));
    }

    public static IStmt getEx9() {
        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        return new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ConstExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))) ,
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ConstExp(new IntValue(30))),
                                                new PrintStmt(new heapRead(new heapRead(new VarExp("a")))))))));
    }

    public static IStmt getEx10() {
        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ConstExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("v"), new ConstExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ConstExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));
    }

    public static IStmt getEx11() {
        //int v; Ref int a; v=10; new(a,22); fork(wH(a,30); v=32; print(v); print(rH(a))); print(v); print(rH(a))
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ConstExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ConstExp(new IntValue(22))),
                                        new CompStmt(new forkStmt(new CompStmt(new WriteHeap("a", new ConstExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ConstExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new heapRead(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new heapRead(new VarExp("a")))))))));
    }

    public static IStmt makeIt(IStmt... stmtList){
        if(stmtList.length==0)return new NopStmt();
        return new CompStmt(stmtList[0],makeIt(Arrays.copyOfRange(stmtList,1,stmtList.length)));
    }

    public static IStmt getEx12(){
        IStmt ex=makeIt(new VarDeclStmt("v1",new RefType(new IntType())),new VarDeclStmt("v2",new RefType(new IntType())),
                new VarDeclStmt("v3",new RefType(new IntType())),new VarDeclStmt("cnt",new IntType()),
                new NewStmt("v1",new ConstExp(new IntValue(2))),new NewStmt("v2",new ConstExp(new IntValue(3))),
                new NewStmt("v3",new ConstExp(new IntValue(4))),new NewLatchStmt("cnt",new heapRead(new VarExp("v2"))),
                new forkStmt(makeIt(new WriteHeap("v1",new ArithExp('*',new heapRead(new VarExp("v1")),new ConstExp(new IntValue(10)))),
                        new PrintStmt(new heapRead(new VarExp("v1"))),new CountDownStmt("cnt"),
                        new forkStmt(makeIt(new WriteHeap("v2",new ArithExp('*',new heapRead(new VarExp("v2")),new ConstExp(new IntValue(10)))),
                                new PrintStmt(new heapRead(new VarExp("v2"))),new CountDownStmt("cnt"),
                                new forkStmt(makeIt(new WriteHeap("v3",new ArithExp('*',new heapRead(new VarExp("v3")),new ConstExp(new IntValue(10)))),
                                        new PrintStmt(new heapRead(new VarExp("v3"))),new CountDownStmt("cnt"))))))),
                new AwaitStmt("cnt"),new PrintStmt(new ConstExp(new IntValue(100))),new CountDownStmt("cnt"),new PrintStmt(new ConstExp(new IntValue(100))));
        return ex;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prgStatesList = new ArrayList<>(Arrays.asList(getEx1(), getEx2(), getEx3(), getEx4(), getEx5(), getEx6(), getEx7(), getEx8(), getEx9(), getEx10(), getEx11(),getEx12()));
        java.util.List<String> programsToString = prgStatesList.stream().map(Object::toString).collect(Collectors.toList());
        ObservableList<String> observableList = FXCollections.observableList(programsToString);
        prgListView.setItems(observableList);
        executeButton.setOnAction(actionEvent -> {
            int index = prgListView.getSelectionModel().getSelectedIndex();
            if (index < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No program was selected!", ButtonType.OK);
                alert.show();
                return;
            }
            IStmt statement = prgStatesList.get(index);
            IDict<String, IType> typeEnv = new Dict<>();
            try {
                statement.typeCheck(typeEnv);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
                alert.show();
                return;
            }
            PrgState programState = createPrgState(statement);
            IRepo repository = new Repo();
            repository.setFile(String.format("src/main/java/com/toy/interpreter/Data/%d.out", index + 1));
            repository.setPrgList(java.util.List.of(programState));
            Controller controller = new Controller(repository);

            FXMLLoader runLoader = new FXMLLoader(Main.class.getResource("RunPrg.fxml"));
            Parent runWindow = null;
            try {
                runWindow = runLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            RunPrgController runPrgController = runLoader.getController();
            this.runPrgController = runPrgController;
            runPrgController.setController(controller);

            Stage runStage = new Stage();
            runStage.setTitle("Run Prg");
            runStage.setScene(new Scene(runWindow, 1200, 800));
            runStage.show();
        });
    }
}
