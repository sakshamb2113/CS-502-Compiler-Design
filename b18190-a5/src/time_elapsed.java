import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.concurrent.TimeUnit;
import java.time.Instant;
import java.time.Duration;

interface func{
    public void printdetails(Person p);
}

class Person {
    String name;
    Integer number;
}

public class time_elapsed {
    public static void main(String args[]){
        // implementation using anonymous class
        ArrayList<Person> plist = new ArrayList<Person> ();
        Person p = new Person();
        p.name = "Saksham";p.number = 19; 
        plist.add(p);
        Person d = new Person();
        d.name = "Saransh";d.number = 10;
        plist.add(d);
        
        // traversal using anonynous inner classes
        Instant start = Instant.now();
        plist.forEach(
            new Consumer<Person>() {
                @Override
                public void accept(Person p){
                    System.out.println("Name is "+ p.name); 
                    System.out.println("Roll no is " + p.number); 
                }
            } 
        );
        Instant finish = Instant.now();
        long elapsedtime = Duration.between(start, finish).toMillis();
        System.out.println("time taken for anonymous inner class based implementation: "+ elapsedtime+" ms.");
        // traversal using lambda expression

        start = Instant.now();

        func myf = (x) -> { System.out.println("Name is "+ x.name); System.out.println("Roll no is " + x.number); };

        plist.forEach( (x) -> myf.printdetails(x));
        finish = Instant.now();
        elapsedtime = Duration.between(start, finish).toMillis();
        System.out.println("time taken for lambda expressions based iteration: " + elapsedtime + " ms.");
    }
    // here we can see that traversing using lambda expression is way easier than using anonymous inner classes
}
