import java.util.ArrayList;
import java.util.function.Consumer;

class test1{
    public static void main(String args[]){
        ArrayList<Integer> values = new ArrayList<Integer>();
        values.add(4);
        values.add(2);
        values.add(9);
        values.add(41);
        Consumer<Integer> print = (x) -> {System.out.println(x*x);};
        values.forEach(print);
    }
    
}