<h1> Introduction </h1>

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

import com.precious.calccedo.handlers.CalccedoHandler;
/**
 *
 * @author Ibrahim
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



