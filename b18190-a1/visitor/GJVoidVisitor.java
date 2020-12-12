//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * All GJ void visitors must implement this interface.
 */

public interface GJVoidVisitor<A> {

   //
   // GJ void Auto class visitors
   //

   public void visit(NodeList n, A argu);
   public void visit(NodeListOptional n, A argu);
   public void visit(NodeOptional n, A argu);
   public void visit(NodeSequence n, A argu);
   public void visit(NodeToken n, A argu);

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> ( Housedeclarations() )*
    * f1 -> ( facts() )*
    * f2 -> <EOF>
    */
   public void visit(Goal n, A argu);

   /**
    * f0 -> <HOUSE_NAME>
    * f1 -> <NAME>
    * f2 -> ";"
    */
   public void visit(Housedeclarations n, A argu);

   /**
    * f0 -> <NAME>
    * f1 -> <OPERATOR>
    * f2 -> <NAME>
    * f3 -> ";"
    */
   public void visit(facts n, A argu);

}
