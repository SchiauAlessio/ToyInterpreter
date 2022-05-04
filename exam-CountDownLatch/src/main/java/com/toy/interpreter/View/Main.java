//
//
//import java.util.Scanner;
//
//
//public class Main {
//    static IRepo repo = new Repo();
//    static Controller ctrl = new Controller(repo);
//
//    public static PrgState createPrgState(IStmt stmt) {
//        IStack<IStmt> stk = new MyStack<>();
//        IDict<String, IValue> symtable = new Dict<>();
//        IList<IValue> ot = new List<>();
//        return new PrgState(stmt);
//    }
//
////    public static void getEx1() {
////        // ex 1:  int v; v = 2; Print(v)
////        IRepo repo1 = new Repo();
////        repo1.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A3\\src\\Data\\1.out");
////        Controller ctrl1 = new Controller(repo1);
////        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
////                new CompStmt(new AssignStmt("v", new ConstExp(new IntValue(2))),
////                        new PrintStmt(new VarExp("v"))));
////        ctrl1.addProgram(createPrgState(ex1));
////        ctrl=ctrl1;
////    }
////
////    public static void getEx2() {
////        // ex 2: int a;a=2+3*5;b=a+1;Print(b)
////        IRepo repo2 = new Repo();
////        repo2.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A3\\src\\Data\\2.out");
////        Controller ctrl2 = new Controller(repo2);
////        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
////                new CompStmt(new AssignStmt("a", new ArithExp('+', new ConstExp(new IntValue(2)), new ArithExp('*',
////                        new ConstExp(new IntValue(3)), new ConstExp(new IntValue(5))))), new CompStmt(
////                        new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ConstExp(new IntValue(1)))),
////                        new PrintStmt(new VarExp("b"))))));
////        ctrl2.addProgram(createPrgState(ex2));
////        ctrl=ctrl2;
////    }
////
////    public static void getEx3() {
////        IRepo repo3 = new Repo();
////        repo3.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A3\\src\\Data\\3.out");
////        Controller ctrl3 = new Controller(repo3);
////        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
////        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v",
////                new IntType()), new CompStmt(new AssignStmt("a", new ConstExp(new BoolValue(true))),
////                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ConstExp(new IntValue(2))),
////                        new AssignStmt("v", new ConstExp(new IntValue(3)))), new PrintStmt(new
////                        VarExp("v"))))));
////        ctrl3.addProgram(createPrgState(ex3));
////        ctrl=ctrl3;
////    }
////
////    public static void getEx4(){
////        // ex 4: a=4
////        IRepo repo4 = new Repo();
////        repo4.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A3\\src\\Data\\4.out");
////        Controller ctrl4 = new Controller(repo4);
////        IStmt ex4 = new AssignStmt("a", new ConstExp(new IntValue(4)));
////        ctrl4.addProgram(createPrgState(ex4));
////        ctrl=ctrl4;
////    }
////    public static void getEx5() {
////        //string varf;varf="test.in";openRFile(varf);
////        //int varc;readFile(varf,varc);print(varc); readFile(varf,varc);print(varc)   closeRFile(varf)
////        IRepo repo5 = new Repo();
////        repo5.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A3\\src\\Data\\5.out");
////        Controller ctrl5 = new Controller(repo5);
////        IStmt ex5 = new CompStmt(new VarDeclStmt("varf",new StringType()),
////                new CompStmt(new AssignStmt("varf", new ConstExp(new StringValue("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A3\\src\\Data\\test.in"))),
////                new CompStmt(new openRFile(new VarExp("varf")),
////                new CompStmt(new VarDeclStmt("varc",new IntType()),
////                new CompStmt(new readFile(new VarExp("varf"), "varc"),
////                new CompStmt(new PrintStmt(new VarExp("varc")),
////                new CompStmt(new readFile(new VarExp("varf"), "varc"),
////                new CompStmt(new PrintStmt(new VarExp("varc")),
////                new closeRFile(new VarExp("varf"))))))))));
////        ctrl5.addProgram(createPrgState(ex5));
////        ctrl=ctrl5;
////    }
////    public static void printMenu() {
////        System.out.println("Choose one of the options below:");
////        System.out.println("0 - exit");
////        System.out.println("1. ex 1");
////        System.out.println("2. ex 2");
////        System.out.println("3. ex 3");
////        System.out.println("4. ex 4");
////        System.out.println("5. ex 5");
////    }
////
////    public static void run() {
////        while (true) {
////            try {
////                printMenu();
////                System.out.print("Your option>> ");
////                int ex;
////                Scanner scn = new Scanner(System.in);
////                ex = scn.nextInt();
////
////                switch (ex) {
////                    case 1 -> {
////                        System.out.println("ex1:  int v; v=2;Print(v) \n");
////                        getEx1();
////                        ctrl.allStep();
////                    }
////                    case 2 -> {
////                        System.out.println("ex2: int a; a=2+3*5; int b; b=a+1; Print(b) \n");
////                        getEx2();
////                        ctrl.allStep();
////                    }
////                    case 3 -> {
////                        System.out.println("ex3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v) \n");
////                        getEx3();
////                        ctrl.allStep();
////                    }
////                    case 4 -> {
////                        System.out.println("ex 4: a=4");
////                        getEx4();
////                        ctrl.allStep();
////                    }
////                    case 5 -> {
////                        System.out.println("ex 5: ");
////                        getEx5();
////                        ctrl.allStep();
////                    }
////                    case 0 -> {
////                        System.out.println("The program is closing!");
////                        return;
////                    }
////                    default -> {
////                        System.out.println("Something went wrong!");
////                    }
////                }
////            } catch (Exception e) {
////                System.out.println(e.getMessage());
////            }
////        }
////    }
//
//    public static void main(String[] args) {
////        run();
//        // ex 1:  int v; v = 2; Print(v)
//
//        TextMenu menu = new TextMenu();
//        menu.addCommand(new ExitCommand("0", "exit"));
//
//
//        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                new CompStmt(new AssignStmt("v", new ConstExp(new IntValue(2))),
//                        new PrintStmt(new VarExp("v"))));
//        try {
//            IDict<String, IType> typeEnv1 = new Dict<>();
//            ex1.typeCheck(typeEnv1);
//            IRepo repo1 = new Repo();
//            repo1.addPrg(createPrgState(ex1));
//            repo1.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A6\\src\\Data\\1.out");
//            Controller ctrl1 = new Controller(repo1);
//            menu.addCommand(new RunCommand("1", ex1.toString(), ctrl1));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//
//        // ex 2: a=2+3*5;b=a+1;Print(b)
//        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
//                new CompStmt(new AssignStmt("a", new ArithExp('+', new ConstExp(new IntValue(2)), new ArithExp('*',
//                        new ConstExp(new IntValue(3)), new ConstExp(new IntValue(5))))), new CompStmt(
//                        new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ConstExp(new IntValue(1)))),
//                        new PrintStmt(new VarExp("b"))))));
//        try {
//            IDict<String, IType> typeEnv2 = new Dict<>();
//            ex2.typeCheck(typeEnv2);
//            IRepo repo2 = new Repo();
//            repo2.addPrg(createPrgState(ex2));
//            repo2.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A6\\src\\Data\\2.out");
//            Controller ctrl2 = new Controller(repo2);
//            menu.addCommand(new RunCommand("2", ex2.toString(), ctrl2));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
//        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("v",
//                new IntType()), new CompStmt(new AssignStmt("a", new ConstExp(new BoolValue(true))),
//                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ConstExp(new IntValue(2))),
//                        new AssignStmt("v", new ConstExp(new IntValue(3)))), new PrintStmt(new
//                        VarExp("v"))))));
//        try{
//        IDict<String, IType> typeEnv3 = new Dict<>();
//        ex3.typeCheck(typeEnv3);
//        IRepo repo3 = new Repo();
//        repo3.addPrg(createPrgState(ex3));
//        repo3.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A6\\src\\Data\\3.out");
//        Controller ctrl3 = new Controller(repo3);
//        menu.addCommand(new RunCommand("3", ex3.toString(), ctrl3));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        //ex 4: a=4
//
//        IStmt ex4 = new AssignStmt("a", new ConstExp(new IntValue(4)));
//        try{
//        IDict<String, IType> typeEnv4 = new Dict<>();
//        ex4.typeCheck(typeEnv4);
//        IRepo repo4 = new Repo();
//        repo4.addPrg(createPrgState(ex4));
//        repo4.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A6\\src\\Data\\4.out");
//        Controller ctrl4 = new Controller(repo4);
//        menu.addCommand(new RunCommand("4", ex4.toString(), ctrl4));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        //string varf;varf="test.in";openRFile(varf);
//        //int varc;readFile(varf,varc);print(varc); readFile(varf,varc);print(varc)   closeRFile(varf)
//        IStmt ex5 = new CompStmt(new VarDeclStmt("varf",new StringType()),
//                new CompStmt(new AssignStmt("varf", new ConstExp(new StringValue("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A6\\src\\Data\\test.in"))),
//                new CompStmt(new openRFile(new VarExp("varf")),
//                new CompStmt(new VarDeclStmt("varc",new IntType()),
//                new CompStmt(new readFile(new VarExp("varf"), "varc"),
//                new CompStmt(new PrintStmt(new VarExp("varc")),
//                new CompStmt(new readFile(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
//                new closeRFile(new VarExp("varf"))))))))));
//        try{
//        IDict<String, IType> typeEnv5 = new Dict<>();
//        ex5.typeCheck(typeEnv5);
//        IRepo repo5 = new Repo();
//        repo5.addPrg(createPrgState(ex5));
//        repo5.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A6\\src\\Data\\5.out");
//        Controller ctrl5 = new Controller(repo5);
//        menu.addCommand(new RunCommand("5", ex5.toString(), ctrl5));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//
//        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
//        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                new CompStmt(new NewStmt("v", new ConstExp(new IntValue(20))),
//                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
//                new CompStmt(new NewStmt("a", new VarExp("v")),
//                new CompStmt(new PrintStmt(new VarExp("v")),
//                new PrintStmt(new VarExp("a")))))));
//        try {
//            IDict<String, IType> typeEnv6 = new Dict<>();
//            ex6.typeCheck(typeEnv6);
//            IRepo repo6 = new Repo();
//            repo6.addPrg(createPrgState(ex6));
//            repo6.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A6\\src\\Data\\6.out");
//            Controller ctrl6 = new Controller(repo6);
//            menu.addCommand(new RunCommand("6", ex6.toString(), ctrl6));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//
//        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
//        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                new CompStmt(new NewStmt("v", new ConstExp(new IntValue(20))),
//                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
//                new CompStmt(new NewStmt("a", new VarExp("v")),
//                new CompStmt(new PrintStmt(new heapRead(new VarExp("v"))),
//                new PrintStmt(new ArithExp('+',
//                new heapRead(new heapRead(new VarExp("a"))), new ConstExp(new IntValue(5)))))))));
//        try{
//        IDict<String, IType> typeEnv7 = new Dict<>();
//        ex7.typeCheck(typeEnv7);
//        IRepo repo7=new Repo();
//        repo7.addPrg(createPrgState(ex7));
//        repo7.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A6\\src\\Data\\7.out");
//        Controller ctrl7=new Controller(repo7);
//        menu.addCommand(new RunCommand("7", ex7.toString(), ctrl7));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
//        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                new CompStmt(new NewStmt("v", new ConstExp(new IntValue(20))),
//                new CompStmt(new PrintStmt(new heapRead(new VarExp("v"))),
//                new CompStmt(new WriteHeap("v", new ConstExp(new IntValue(30))),
//                new PrintStmt(new ArithExp('+', new heapRead(new VarExp("v")), new ConstExp(new IntValue(5))))))));
//        try{
//        IDict<String, IType> typeEnv8 = new Dict<>();
//        ex8.typeCheck(typeEnv8);
//        IRepo repo8=new Repo();
//        repo8.addPrg(createPrgState(ex8));
//        repo8.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A6\\src\\Data\\8.out");
//        Controller ctrl8=new Controller(repo8);
//        menu.addCommand(new RunCommand("8", ex8.toString(), ctrl8));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//
//        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
//        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                new CompStmt(new NewStmt("v", new ConstExp(new IntValue(20))),
//                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))) ,
//                new CompStmt(new NewStmt("a", new VarExp("v")),
//                new CompStmt(new NewStmt("v", new ConstExp(new IntValue(30))),
//                new PrintStmt(new heapRead(new heapRead(new VarExp("a")))))))));
//        try{
//        IDict<String, IType> typeEnv9 = new Dict<>();
//        ex1.typeCheck(typeEnv9);
//        IRepo repo9=new Repo();
//        repo9.addPrg(createPrgState(ex9));
//        repo9.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A6\\src\\Data\\9.out");
//        Controller ctrl9=new Controller(repo9);
//        menu.addCommand(new RunCommand("9", ex9.toString(), ctrl9));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//
//        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
//        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                new CompStmt(new AssignStmt("v", new ConstExp(new IntValue(4))),
//                new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("v"), new ConstExp(new IntValue(0))),
//                new CompStmt(new PrintStmt(new VarExp("v")),
//                new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ConstExp(new IntValue(1)))))),
//                new PrintStmt(new VarExp("v")))));
//        try{
//        IDict<String, IType> typeEnv10 = new Dict<>();
//        ex10.typeCheck(typeEnv10);
//        IRepo repo10=new Repo();
//        repo10.addPrg(createPrgState(ex10));
//        repo10.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A6\\src\\Data\\10.out");
//        Controller ctrl10=new Controller(repo10);
//        menu.addCommand(new RunCommand("10", ex10.toString(), ctrl10));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        //int v; Ref int a; v=10; new(a,22); fork(wH(a,30); v=32; print(v); print(rH(a))); print(v); print(rH(a))
//        IStmt ex11 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
//                new CompStmt(new AssignStmt("v", new ConstExp(new IntValue(10))),
//                new CompStmt(new NewStmt("a", new ConstExp(new IntValue(22))),
//                new CompStmt(new forkStmt(new CompStmt(new WriteHeap("a", new ConstExp(new IntValue(30))),
//                new CompStmt(new AssignStmt("v", new ConstExp(new IntValue(32))),
//                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new heapRead(new VarExp("a"))))))),
//                new CompStmt(new PrintStmt(new VarExp("v")),
//                new PrintStmt(new heapRead(new VarExp("a")))))))));
//        try{
//        IDict<String, IType> typeEnv11 = new Dict<>();
//        ex11.typeCheck(typeEnv11);
//        IRepo repo11=new Repo();
//        repo11.addPrg(createPrgState(ex11));
//        repo11.setFile("C:\\Users\\alfbu\\Desktop\\College\\MAP\\A6\\src\\Data\\11.out");
//        Controller ctrl11=new Controller(repo11);
//        menu.addCommand(new RunCommand("11", ex11.toString(), ctrl11));
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        menu.run();
//    }
//}
