//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class OptimVisitor extends GJDepthFirst<String,Integer> {

    Map<Integer, Set<Integer>> pred = new HashMap<Integer, Set<Integer>>();
    Map<Integer, Set<Integer>> succ = new HashMap<Integer, Set<Integer>>();
    Map<Integer, Set<String>> def = new HashMap<Integer, Set<String>>();
    Map<Integer, Set<String>> use = new HashMap<Integer, Set<String>>();
    Map<Integer, Map<String, Integer>> in = new HashMap<Integer, Map<String, Integer>>();
    Map<Integer, Map<String, Integer>> out = new HashMap<Integer, Map<String, Integer>>();
    Vector<Integer> printstatements = new Vector<Integer>();
    Integer prev_statement=-1;
    Integer curr_statement=1;
    Integer classflg=0;
    Vector<Integer> ifflg = new Vector<Integer>();
    Vector<Integer> whileflg = new Vector<Integer>();
//    Vector<Integer> worklist= new Vector<Integer>();
    SortedSet<Integer> worklist = new TreeSet<Integer>();
    //
    // User-generated visitor methods below
    //

    /**
     * f0 -> MainClass()
     * f1 -> ( ClassDeclaration() )*
     * f2 -> <EOF>
     */
    public String visit(Goal n, Integer argu) {
        String _ret = null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
//        System.out.println("Predecessors");
//        for (Map.Entry<Integer, Set<Integer>> set : pred.entrySet()) {
//            System.out.print(set.getKey()+": ");
//            for(Integer val : set.getValue()){
//                System.out.print(", "+val);
//            }
//            System.out.print("\n");
//        }
//        System.out.println("Use");
//        for (Map.Entry<Integer, Set<String>> set : use.entrySet()) {
//            System.out.print(set.getKey()+": ");
//            for(String val : set.getValue()){
//                System.out.print(", "+val);
//            }
//            System.out.print("\n");
//        }

//        System.out.println("Def");
//        for (Map.Entry<Integer, Set<String>> set : def.entrySet()) {
//            System.out.print(set.getKey()+": ");
//            for(String val : set.getValue()){
//                System.out.print(", "+val);
//            }
//            System.out.print("\n");
//        }

//        generate succ
        Integer mx=0;
        for (Map.Entry<Integer, Set<Integer>> set : pred.entrySet()) {
            Set<Integer> vars = set.getValue();
            Integer node = set.getKey();
//            Set<Integer> set = pred.get(curr_statement);
            for(Integer it: vars){
                Set<Integer> curr_succ = succ.get(it);
                if (curr_succ == null) {
                    curr_succ = new HashSet<Integer>();
                    succ.put(it, curr_succ);
                }
                succ.get(it).add(node);
                mx = Math.max(mx, it);
            }

        }
        Set<Integer> curr_succ = new HashSet<Integer>();
        succ.put(mx+1, curr_succ);

//        succ.remove(1);

        for (Map.Entry<Integer, Set<Integer>> set : succ.entrySet()) {
            Set<Integer> vars = set.getValue();
            Integer node = set.getKey();
//            Set<Integer> set = pred.get(curr_statement);
            for(Integer it: vars){
                Set<String> curr_use = use.get(it);
                if (curr_use == null) {
                    curr_use = new HashSet<String>();
                    use.put(it, curr_use);
                }
                Set<String> curr_def = def.get(it);
                if(curr_def == null) {
                    curr_def = new HashSet<String>();
                    def.put(it, curr_def);
                }

                Map<String, Integer> curr_out = out.get(it);
                if(curr_out == null){
                    curr_out = new HashMap<String, Integer>();
                    out.put(it, curr_out);
                }

                Map<String, Integer> curr_in = in.get(it);
                if(curr_in == null){
                    curr_in = new HashMap<String, Integer>();
                    in.put(it, curr_in);
                }
            }
        }


//        System.out.println("Successors");
//        for (Map.Entry<Integer, Set<Integer>> set : succ.entrySet()) {
//            System.out.print(set.getKey()+": ");
//            for(Integer val : set.getValue()){
//                System.out.print(", "+val);
//            }
//            System.out.print("\n");
//        }
        Iterator<Integer> itr = worklist.iterator();

        // traversing over HashSet
//        System.out.println("Traversing over Set using Iterator");
        while(itr.hasNext()){
            Integer dummy = itr.next();
//            System.out.println(dummy);
            Set<String> curr_use = use.get(dummy-1);
            if (curr_use == null) {
                curr_use = new HashSet<String>();
                use.put(dummy-1, curr_use);
            }
            Set<String> curr_def = def.get(dummy-1);
            if(curr_def == null) {
                curr_def = new HashSet<String>();
                def.put(dummy-1, curr_def);
            }

            Map<String, Integer> curr_out = out.get(dummy-1);
            if(curr_out == null){
                curr_out = new HashMap<String, Integer>();
                out.put(dummy-1, curr_out);
            }

            Map<String, Integer> curr_in = in.get(dummy-1);
            if(curr_in == null){
                curr_in = new HashMap<String, Integer>();
                in.put(dummy-1, curr_in);
            }
        }

        while (true){
//            System.out.println("Hey");
//            for every element in our worklist
            Iterator<Integer> iter = worklist.iterator();
            Integer noOutChange = 0;

            while(worklist.size()!=0) {

                Integer i = worklist.first();
                Map<String, Map<Integer, Integer>> cnts = new HashMap<String, Map<Integer, Integer>>();
                Map<String, Integer> outDash = new HashMap<String, Integer>();
                for (Map.Entry<String, Integer> val : out.get(i).entrySet()) {
                    outDash.put(val.getKey(), val.getValue());
                }
//                for every p belonging to  pred[i] get its out[p] and note variables having different points of
                /// definition
//                System.out.println(i);
//                System.out.println(out.get(i).size());
//                System.out.println(outDash.size());
//

//                if(worklist.first()!=i) {       // no need to generate in for the first element (entry node)

                for (Integer p : pred.get(i)) {
//                        System.out.println("HEY1");
                    Map<String, Integer> outofp = out.get(p);
                    for (Map.Entry<String, Integer> val : outofp.entrySet()) {
//                            System.out.println("HEY2");
                        String constant = val.getKey();
                        Integer ptOfDefinition = val.getValue();
                        Map<Integer, Integer> map = cnts.get(constant);
                        if (map == null) {
                            map = new HashMap<Integer, Integer>();
                            cnts.put(constant, map);
                        }
                        cnts.get(constant).put(ptOfDefinition, cnts.get(constant).getOrDefault(ptOfDefinition, 0) + 1);
                    }

                }
//                for(Map.Entry<String, Integer> cnt)
//                generate in[i]
                for (Map.Entry<String, Map<Integer, Integer>> cnt : cnts.entrySet()) {
//                        System.out.println("HEY3");
                    Map<Integer, Integer> defOfconstant = cnt.getValue();
                    Integer sz = defOfconstant.size();
                    Map.Entry<Integer, Integer> entry = defOfconstant.entrySet().iterator().next();
                    Integer ptOfDefinition = entry.getKey();
                    Integer count = entry.getValue();
//                    if(i==9) {
//                        System.out.println(cnt.getKey());
//                        System.out.println(ptOfDefinition);
//                        System.out.println(count);
//                    }
                    if (sz == 1 && count == pred.get(i).size()) {
//                        we are concerned with only those which have only one point of defintion and this point is common
//                        for all the predecessors which we get by equating the count to number of predecessors
//                            System.out.println("HEY4");
                        String constant = cnt.getKey();
                        Map<String, Integer> currin = in.get(i);
                        if (currin == null) {
//                                System.out.println("HEY5");
                            currin = new HashMap<String, Integer>();
                            in.put(i, currin);
                        }

                        in.get(i).put(cnt.getKey(), ptOfDefinition);

                    }
                }

                Integer chk = 0;
//                generate out[i]
//                System.out.println(pred.get(i).size());

//                add constants of in to out
                for(Map.Entry<String, Integer> val : in.get(i).entrySet()){
//                    System.out.println("in to out ok");
                    out.get(i).put(val.getKey(), val.getValue());
                }


//                add or replace newly generated constants to out
                if(def.get(i).size()!=0) {
                    String defvar = def.get(i).iterator().next();
                    for (String it : use.get(i)) {

//                    check whether all operands are constant
                        if (in.get(i).containsKey(it) == false) {
                            chk = 1;
                            break;
                        }
                    }
                    if (chk == 0) {
//                    if all operands are constant add it to out[i]
//                        System.out.println("out is ok");
                        Map<String, Integer> currout = out.get(i);
                        if (currout == null) {
                            currout = new HashMap<String, Integer>();
                            out.put(i, currout);
                        }
                        out.get(i).put(defvar, i);
                    }
                    else{
                        if(in.get(i).containsKey(defvar)==true)
                        out.get(i).remove(defvar);
                    }
                }

//                check whether out has changed
                Integer flg=0;
                Map<String, Integer> vars = out.get(i);
//                System.out.println(vars.size());
//                System.out.println(outDash.size());
                if (vars.size() != outDash.size()) {
                    flg = 1;
                } else {
                    Map<String, Integer> tempmap = outDash;
                    for (Map.Entry<String, Integer> val : vars.entrySet()) {
                        if (tempmap.containsKey(val.getKey()) == false) {
                            flg = 1;
                            break;
                        }
                    }
                }

                if (flg == 1) {

                        for (Integer tmp : succ.get(i)) {
//                            System.out.println("worklist add ok");
                            worklist.add(tmp);
                        }
                    noOutChange = 1;
                }
//                System.out.println(noOutChange);
                worklist.remove(i);
            }
//            System.out.println("LOOP BREAK");

            if(noOutChange==0){
                break;
            }

       }


//        System.out.println("ins");
//        for (Map.Entry<Integer, Map<String, Integer>> set : in.entrySet()) {
//            System.out.print(set.getKey()+": ");
//            Map<String, Integer> mymap = set.getValue();
//            for(Map.Entry<String, Integer> val : mymap.entrySet()){
//                System.out.print("constant: "+val.getKey()+" ");
//                System.out.print("defpt: "+val.getValue()+" ");
//                System.out.print(" , ");
//            }
//            System.out.print("\n");
//        }
//
//        System.out.println("outs");
//        for (Map.Entry<Integer, Map<String, Integer>> set : out.entrySet()) {
//            System.out.print(set.getKey()+": ");
//            Map<String, Integer> mymap = set.getValue();
//            for(Map.Entry<String, Integer> val : mymap.entrySet()){
//                System.out.print("constant: "+val.getKey()+" ");
//                System.out.print("defpt: "+val.getValue()+" ");
//                System.out.print(" , ");
//            }
//            System.out.print("\n");
//        }

        for(Integer i:printstatements){
            for(Map.Entry<String, Integer> val : in.get(i).entrySet())
            {
                System.out.print(val.getKey()+" ");
            }
            System.out.println("");
        }
        return _ret;

    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> "public"
     * f4 -> "static"
     * f5 -> "void"
     * f6 -> "main"
     * f7 -> "("
     * f8 -> "String"
     * f9 -> "["
     * f10 -> "]"
     * f11 -> Identifier()
     * f12 -> ")"
     * f13 -> "{"
     * f14 -> ( VarDeclaration() )*
     * f15 -> ( Statement() )*
     * f16 -> "}"
     * f17 -> "}"
     */
    public String visit(MainClass n, Integer argu) {
        String _ret=null;
        worklist.add(curr_statement+1);
        n.f1.accept(this, argu);
        n.f11.accept(this, argu);
        n.f14.accept(this, argu);
        n.f15.accept(this, -1);
        return _ret;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> ( VarDeclaration() )*
     * f4 -> ( MethodDeclaration() )*
     * f5 -> "}"
     */
    public String visit(ClassDeclaration n, Integer argu) {
        String _ret=null;
        curr_statement++;
        worklist.add(curr_statement+1);
        n.f1.accept(this, argu);
        classflg=1;
        n.f3.accept(this, argu);
        classflg=0;
        n.f4.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    public String visit(VarDeclaration n, Integer argu) {
        String _ret=null;

        n.f0.accept(this, argu);
        String tmp1 = n.f1.accept(this, argu);
        if(classflg==1){
            prev_statement = curr_statement;
            // increment current statement for every new node
            curr_statement++;
            // add to predecessor
            Set<Integer> set1 = pred.get(curr_statement);
            if (set1 == null) {
                set1 = new HashSet<Integer>();
                pred.put(curr_statement, set1);
            }
            pred.get(curr_statement).add(prev_statement);

            Set<String> set = def.get(curr_statement);
            if (set == null) {
                set = new HashSet<String>();
                def.put(curr_statement, set);
            }
            def.get(curr_statement).add(tmp1);
        }
        return _ret;
    }

    /**
     * f0 -> "public"
     * f1 -> Type()
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( FormalParameterList() )?
     * f5 -> ")"
     * f6 -> "{"
     * f7 -> ( VarDeclaration() )*
     * f8 -> ( Statement() )*
     * f9 -> "return"
     * f10 -> Identifier()
     * f11 -> ";"
     * f12 -> "}"
     */
    public String visit(MethodDeclaration n, Integer argu) {
        String _ret=null;
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f4.accept(this, argu);
        n.f7.accept(this, argu);
        n.f8.accept(this, -1);
        // put return statement in use(leave it for now since grammar doesn't treat it as statement)
        n.f10.accept(this, argu);

        return _ret;
    }

    /**
     * f0 -> FormalParameter()
     * f1 -> ( FormalParameterStringest() )*
     */
    public String visit(FormalParameterList n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    public String visit(FormalParameter n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ","
     * f1 -> FormalParameter()
     */
    public String visit(FormalParameterRest n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> BooleanType()
     *       | IntegerType()
     *       | Identifier()
     */
    public String visit(Type n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "boolean"
     */
    public String visit(BooleanType n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "int"
     */
    public String visit(IntegerType n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Block()
     *       | AssignmentStatement()
     *       | IfStatement()
     *       | WhileStatement()
     *       | PrintStatement()
     *       | ConstantQueryStatement()
     */
    public String visit(Statement n, Integer argu) {
        String _ret=null;

        // receive previous statement from argu
        if(argu==-1)
            prev_statement = curr_statement;
        else
            prev_statement = argu;
        // increment current statement for every new node
        curr_statement++;
        // add to predecessor
        Set<Integer> set = pred.get(curr_statement);
        if (set == null) {
            set = new HashSet<Integer>();
            pred.put(curr_statement, set);
        }
        if(whileflg.size()!=0){
            for(Integer i: whileflg)
                pred.get(curr_statement).add(i);
            whileflg.clear();
        }
        else{
            pred.get(curr_statement).add(prev_statement);
        }

        if(ifflg.size()!=0){
            for(Integer i: ifflg)
            pred.get(curr_statement).add(i);
            ifflg.clear();
        }


        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "{"
     * f1 -> ( Statement() )*
     * f2 -> "}"
     */
    public String visit(Block n, Integer argu) {
        String _ret=null;
        n.f1.accept(this, -1);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    public String visit(AssignmentStatement n, Integer argu) {
        String _ret=null;
        String tmp1 = n.f0.accept(this, argu);

        // variable denoted by identifier will be defined after this node.
        n.f2.accept(this, argu);
        Set<String> set = def.get(curr_statement);
        if (set == null) {
            set = new HashSet<String>();
            def.put(curr_statement, set);
        }
        def.get(curr_statement).add(tmp1);

        return _ret;
    }

    /**
     * f0 -> "if"
     * f1 -> "("
     * f2 -> Identifier()
     * f3 -> ")"
     * f4 -> Statement()
     * f5 -> "else"
     * f6 -> Statement()
     */
    public String visit(IfStatement n, Integer argu) {
        String _ret=null;
        n.f2.accept(this, argu);
        Integer temp = curr_statement;
        // call visit for then and else with same parent
        n.f4.accept(this, temp);
        Integer tmp1 = curr_statement;
        n.f6.accept(this, temp);
        // there can be multiple if statements parent of same statement
        ifflg.add(tmp1);
        return _ret;
    }

    /**
     * f0 -> "while"
     * f1 -> "("
     * f2 -> Identifier()
     * f3 -> ")"
     * f4 -> Statement()
     */
    public String visit(WhileStatement n, Integer argu) {
        String _ret=null;
        n.f2.accept(this, argu);
        Integer temp = curr_statement;
        n.f4.accept(this, temp);
        // current statement remains the same
        // only the previous statement changes. it becomes the statement at the last
        Integer tmp1 = temp;
        temp = curr_statement;
        curr_statement = tmp1;
        n.f4.accept(this, temp);
//        curr_statement = tmp1;
//        prev_statement = tmp1;
        whileflg.add(curr_statement);
        return _ret;
    }

    /**
     * f0 -> "System.out.println"
     * f1 -> "("
     * f2 -> Identifier()
     * f3 -> ")"
     * f4 -> ";"
     */
    public String visit(PrintStatement n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> <SCOMMENT1>
     * f1 -> <CONSTANTQUERY>
     * f2 -> <SCOMMENT2>
     */
    public String visit(ConstantQueryStatement n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        printstatements.add(curr_statement);
        return _ret;
    }

    /**
     * f0 -> OrExpression()
     *       | AndExpression()
     *       | CompareExpression()
     *       | neqExpression()
     *       | PlusExpression()
     *       | MinusExpression()
     *       | TimesExpression()
     *       | DivExpression()
     *       | MessageSend()
     *       | PrimaryExpression()
     */
    public String visit(Expression n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "&&"
     * f2 -> Identifier()
     */
    public String visit(AndExpression n, Integer argu) {
        String _ret=null;
        String tmp1 = n.f0.accept(this, argu);
        String tmp2 = n.f2.accept(this, argu);
        Set<String> set = use.get(curr_statement);
        if (set == null) {
            set = new HashSet<String>();
            use.put(curr_statement, set);
        }
        use.get(curr_statement).add(tmp1);
        use.get(curr_statement).add(tmp2);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "||"
     * f2 -> Identifier()
     */
    public String visit(OrExpression n, Integer argu) {
        String _ret=null;
        String tmp1 = n.f0.accept(this, argu);
        String tmp2 = n.f2.accept(this, argu);
        Set<String> set = use.get(curr_statement);
        if (set == null) {
            set = new HashSet<String>();
            use.put(curr_statement, set);
        }
        use.get(curr_statement).add(tmp1);
        use.get(curr_statement).add(tmp2);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "<"
     * f2 -> Identifier()
     */
    public String visit(CompareExpression n, Integer argu) {
        String _ret=null;
        String tmp1 = n.f0.accept(this, argu);
        String tmp2 = n.f2.accept(this, argu);
        Set<String> set = use.get(curr_statement);
        if (set == null) {
            set = new HashSet<String>();
            use.put(curr_statement, set);
        }
        use.get(curr_statement).add(tmp1);
        use.get(curr_statement).add(tmp2);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "!="
     * f2 -> Identifier()
     */
    public String visit(neqExpression n, Integer argu) {
        String _ret=null;
        String tmp1 = n.f0.accept(this, argu);
        String tmp2 = n.f2.accept(this, argu);
        Set<String> set = use.get(curr_statement);
        if (set == null) {
            set = new HashSet<String>();
            use.put(curr_statement, set);
        }
        use.get(curr_statement).add(tmp1);
        use.get(curr_statement).add(tmp2);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "+"
     * f2 -> Identifier()
     */
    public String visit(PlusExpression n, Integer argu) {
        String _ret=null;
        String tmp1 = n.f0.accept(this, argu);
        String tmp2 = n.f2.accept(this, argu);
        Set<String> set = use.get(curr_statement);
        if (set == null) {
            set = new HashSet<String>();
            use.put(curr_statement, set);
        }
        use.get(curr_statement).add(tmp1);
        use.get(curr_statement).add(tmp2);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "-"
     * f2 -> Identifier()
     */
    public String visit(MinusExpression n, Integer argu) {
        String _ret=null;
        String tmp1 = n.f0.accept(this, argu);
        String tmp2 = n.f2.accept(this, argu);
        Set<String> set = use.get(curr_statement);
        if (set == null) {
            set = new HashSet<String>();
            use.put(curr_statement, set);
        }
        use.get(curr_statement).add(tmp1);
        use.get(curr_statement).add(tmp2);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "*"
     * f2 -> Identifier()
     */
    public String visit(TimesExpression n, Integer argu) {
        String _ret=null;
        String tmp1 = n.f0.accept(this, argu);
        String tmp2 = n.f2.accept(this, argu);
        Set<String> set = use.get(curr_statement);
        if (set == null) {
            set = new HashSet<String>();
            use.put(curr_statement, set);
        }
        use.get(curr_statement).add(tmp1);
        use.get(curr_statement).add(tmp2);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "/"
     * f2 -> Identifier()
     */
    public String visit(DivExpression n, Integer argu) {
        String _ret=null;
        String tmp1 = n.f0.accept(this, argu);
        String tmp2 = n.f2.accept(this, argu);
        Set<String> set = use.get(curr_statement);
        if (set == null) {
            set = new HashSet<String>();
            use.put(curr_statement, set);
        }
        use.get(curr_statement).add(tmp1);
        use.get(curr_statement).add(tmp2);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "."
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( ArgList() )?
     * f5 -> ")"
     */
    public String visit(MessageSend n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        n.f2.accept(this, argu);
        n.f4.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> ( ArgRest() )*
     */
    public String visit(ArgList n, Integer argu) {
        String _ret=null;
        String tmp1 = n.f0.accept(this, argu);
        Set<String> set = use.get(curr_statement);
        if (set == null) {
            set = new HashSet<String>();
            use.put(curr_statement, set);
        }
        use.get(curr_statement).add(tmp1);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ","
     * f1 -> Identifier()
     */
    public String visit(ArgRest n, Integer argu) {
        String _ret=null;
        String tmp1 = n.f1.accept(this, argu);
        Set<String> set = use.get(curr_statement);
        if (set == null) {
            set = new HashSet<String>();
            use.put(curr_statement, set);
        }
        use.get(curr_statement).add(tmp1);
        return _ret;
    }

    /**
     * f0 -> IntegerLiteral()
     *       | TrueLiteral()
     *       | FalseLiteral()
     *       | Identifier()
     *       | ThisExpression()
     *       | AllocationExpression()
     *       | NotExpression()
     */
    public String visit(PrimaryExpression n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    public String visit(IntegerLiteral n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "true"
     */
    public String visit(TrueLiteral n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "false"
     */
    public String visit(FalseLiteral n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    public String visit(Identifier n, Integer argu) {
        String _ret=null;
        _ret = n.f0.tokenImage;
        return _ret;
    }

    /**
     * f0 -> "this"
     */
    public String visit(ThisExpression n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    public String visit(AllocationExpression n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "!"
     * f1 -> Identifier()
     */
    public String visit(NotExpression n, Integer argu) {
        String _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

}
