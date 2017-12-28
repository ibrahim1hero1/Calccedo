/*
  This is simple code to help in how to use Calccedo Engine Library

*/
package testcalccedo;
import com.precious.calccedo.handlers.CalccedoHandler;
/**
 *
 * @author Ibrahim Abdsaid Hanna
 *         ibrahim.seniore@gmail.com
 */

public class TestCalccedo extends CalccedoHandler{

    public TestCalccedo(){
        super.init();
        // to show tracing operations of Calccedo
        super.enableDeepTrace(false);
        
    }

    
   
     
    
    public static void main(String[] args) {
       // this is sample formula  
       String formula="1+1+2+3sin(30)+10";
       
       // instantiate object from TestCalccedo 
       TestCalccedo calccedoHandler=new TestCalccedo();

       // now let's get result, just by calling calccedo calculate method
       System.out.println("Welcome to Calccedo Engine Library");
       System.out.println("Result is "+calccedoHandler.calculate(formula));
       
    }  
}
