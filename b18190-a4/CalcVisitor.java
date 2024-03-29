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
public class CalcVisitor extends GJNoArguDepthFirst<Integer> {

   Map<String, Integer> values = new HashMap<>();

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> ( AssignmentStatement() )*
    * f1 -> PrintStatment()
    * f2 -> <EOF>
    */
   public Integer visit(Goal n) {
      Integer _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public Integer visit(AssignmentStatement n) {
      Integer _ret=null;
      String id = n.f0.f0.tokenImage;
      int val = n.f2.accept(this);
      values.put(id, val);
      return _ret;
   }

   /**
    * f0 -> <PRINT>
    * f1 -> Expression()
    * f2 -> ";"
    */
   public Integer visit(PrintStatment n) {
      Integer _ret=null;
      int val = n.f1.accept(this);
      System.out.println(val);
      return _ret;
   }

   /**
    * f0 -> PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | PrimaryExpression()
    */
   public Integer visit(Expression n) {
      Integer _ret=null;
      _ret = n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> <PLUS>
    * f2 -> PrimaryExpression()
    */
   public Integer visit(PlusExpression n) {
      Integer _ret=null;
      int val1 = n.f0.accept(this);
      int val2 = n.f2.accept(this);
      _ret = val1 + val2;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> <MINUS>
    * f2 -> PrimaryExpression()
    */
   public Integer visit(MinusExpression n) {
      Integer _ret=null;
      int val1 = n.f0.accept(this);
      int val2 = n.f2.accept(this);
      _ret = val1 - val2;
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> <TIMES>
    * f2 -> PrimaryExpression()
    */
   public Integer visit(TimesExpression n) {
      Integer _ret=null;
      int val1 = n.f0.accept(this);
      int val2 = n.f2.accept(this);
      _ret = val1 * val2;
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    *       | Identifier()
    *       | BracketExpression()
    */
   public Integer visit(PrimaryExpression n) {
      Integer _ret=null;
      _ret = n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public Integer visit(IntegerLiteral n) {
      Integer _ret=null;
      _ret = Integer.parseInt(n.f0.tokenImage);
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public Integer visit(Identifier n) {
      Integer _ret=null;
      String id = n.f0.tokenImage;
      _ret = values.get(id);
      return _ret;
   }

   /**
    * f0 -> <LPAREN>
    * f1 -> Expression()
    * f2 -> ")"
    */
   public Integer visit(BracketExpression n) {
      Integer _ret=null;
      _ret = n.f1.accept(this);
      return _ret;
   }

}
