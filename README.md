
<h1> Build Calculator using Java </h1>

<h3> Introduction </h3>

Calccedo-V2 is the next revolution of calccedo, it support many reach features like addSemantic(), like add addVariable(), like addConstant(), like injectFunction()


<br/>


1- <a style="font-weight: bold;"> Setup using jar and add it to project class path:</a>


<a href="https://www.dropbox.com/scl/fi/t5uwu7shqlbd7exx4fmwc/Calccedo-V2-1.0-SNAPSHOT.jar?rlkey=h4mfnughrvpyzzwzugfxrxi9k&dl=0">
<img src="https://raw.githubusercontent.com/ibrahim1hero1/calccedo/Calccedo-v2/readme/images/dropbox-logo.png" data-canonical-src="https://raw.githubusercontent.com/ibrahim1hero1/calccedo/Calccedo-v2/readme/images/dropbox-logo.png" width="800" height="200" />
</a>



(https://www.dropbox.com/scl/fi/t5uwu7shqlbd7exx4fmwc/Calccedo-V2-1.0-SNAPSHOT.jar?rlkey=h4mfnughrvpyzzwzugfxrxi9k&dl=0)

<br/>

<a style="font-weight: bold;"> Features: </a>
<pre>
add semantics like Sin(30) = 0.5
add variables like x=10
add constants like PI = 3.14
inject functions like f(x)=x^2+2x+1
you can build large complicated mathematical application using Calccedo-V2

  </pre>


<br/>

<h1>Development</h1>
<h3>Calculate simple operation</h3>

<br/>
<pre>
import org.calccedo.handlers.CalccedoHandler;
/**
 *
 * @author Ibrahim Abdsaid Hanna
 */   
    public class TestCalccedo extends CalccedoHandler{
    
    public TestCalccedo(){
      super.enableDeepTrace(true);
    }
    
    public static void main(String [] args){
      TestCalccedo calccedoHandler = new TestCalccedo();
      String formula = "1+5+sin(30)+20+70+80+90";
      String result = calccedoHandler.calculate(formula);
      System.out.println(result);
    }
}
</pre>

<h3>Adding Sin(30) to calccedo as a semantic Sin(30)=0.5</h3>
     <pre>    
     addSemantic(String semanticName,int quoteNumber,double value);
     addSemantic(String semanticName,double quoteNumber,double value);
     addSemantic(String semanticName,int quoteNumber,Object object);
     addSemantic(String semanticName,double quoteNumber,Object object);
    </pre>
    <pre>
import org.calccedo.handlers.CalccedoHandler;
/**
 *
 * @author Ibrahim Abdsaid Hanna
 */   
    public class TestCalccedo extends CalccedoHandler{
    
    
    public TestCalccedo(){
      super.enableDeepTrace(false);
      super.addSemantic("Sin",30,0.5);
      super.addSemantic("Sin",30.0,0.5);
    }
    
    public static void main(String [] args){
      TestCalccedo calccedoHandler = new TestCalccedo();
      String formula = "Sin(30)";
      String result = calccedoHandler.calculate(formula);
      System.out.println(result);
    }
}
    </pre>

<h3>Adding Tan(90) to calccedo as an Illegal custom operand Tan(90)=Error</h3>
<pre>
import org.calccedo.handlers.CalccedoHandler;
import org.calccedo.handlers.Infinity;
/**
 *
 * @author Ibrahim Abdsaid Hanna
 */
public class TestCalccedo extends CalccedoHandler{
    
    public TestCalccedo(){
      super.enableDeepTrace(false);
      super.addSemantic("Tan",90,Infinity.class);
      super.addSemantic("Tan",90.0,Infinity.class);
    }
    
    public static void main(String [] args){
      TestCalccedo calccedoHandler = new TestCalccedo();
      String formula = "Tan(90)";
      String result = calccedoHandler.calculate(formula);
      System.out.println(result);
    }
}

</pre>

<h3>Calculate complicated operations</h3>
<pre>
import org.calccedo.handlers.CalccedoHandler;
/**
 *
 * @author Ibrahim Abdsaid Hanna
 */   
    public class TestCalccedo extends CalccedoHandler{
    
    public TestCalccedo(){
      super.enableDeepTrace(true);
    }
    
    public static void main(String [] args){
      TestCalccedo calccedoHandler = new TestCalccedo();
      String formula = "(5(1+2)+3sin(30cos(30+30))+(5*7+10%2)+(10^21+5E10)/5)";
      String result = calccedoHandler.calculate(formula);
      System.out.println(result);
    }
}
  
  
</pre>



