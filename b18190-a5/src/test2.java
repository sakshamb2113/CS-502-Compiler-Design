import java.util.ArrayList;
// custom functional interface
interface square{ Integer sq(Integer i); }
// using the below interface will give error
// interface square{ String sq(String i); }
public class test2 {
    public static void main(String args[]){
        ArrayList<Integer> values = new ArrayList<Integer>();
        values.add(4);
        values.add(2);
        values.add(9);
        values.add(41);
        square method= x -> x*x;
        // lambda expression inside another lambda expression
        values.forEach( (x) -> System.out.println(method.sq(x)) ); 
    }
}
