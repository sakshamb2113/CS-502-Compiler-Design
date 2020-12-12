import java.util.ArrayList;
import java.util.function.Consumer;

interface func{
    public void printdetails(Person p);
}

class Person {
    String name;
    Integer number;
}

public class test4 {
    public static void main(String args[]){
        ArrayList<Person> plist = new ArrayList<Person> ();
        Person p = new Person();
        p.name = "Saksham";p.number = 19; 
        plist.add(p);
        Person d = new Person();
        d.name = "Saransh";d.number = 10;
        plist.add(d);
        
        // traversal using anonynous inner classes
        plist.forEach(
            new Consumer<Person>() {
                @Override
                public void accept(Person p){
                    System.out.println("Name is "+ p.name); 
                    System.out.println("Roll no is " + p.number); 
                }
            } 
        );

        // traversal using lambda expression
        func myf = (x) -> { System.out.println("Name is "+ x.name); System.out.println("Roll no is " + x.number); };

        plist.forEach( (x) -> myf.printdetails(x));
    }
    // here we can see that traversing using lambda expression is way easier than using anonymous inner classes
}
