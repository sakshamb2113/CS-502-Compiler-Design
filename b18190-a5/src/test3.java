import java.util.ArrayList;
// test method references
class square{
    public static void getsq(Integer x) { System.out.println(x*x);} 
}

public class test3 {
    public static void main(String args[]){
        ArrayList<Integer> values = new ArrayList<Integer>();
        values.add(4);
        values.add(2);
        values.add(9);
        values.add(41);
        values.forEach( square::getsq ); 
    }
}
