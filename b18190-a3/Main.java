import syntaxtree.*;
import visitor.*;

public class Main {
   public static void main(String [] args) {
      try {
         Node root = new LasagnePPJavaParser(System.in).Goal();
//         System.out.println("Program parsed successfully");
         root.accept(new CGvisitor(),null); // Your assignment part is invoked here.
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 
