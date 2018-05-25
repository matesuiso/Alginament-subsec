// we start at    https://stackoverflow.com/questions/3038464/how-to-call-java-function-from-string-stored-in-a-variable
// and moves to   http://www.rgagnon.com/javadetails/java-0031.html

import java.lang.reflect.Method;

public class Main {

   // la parte que sigue es para que no joda cuando compila, es por el metodo que busca la funcion de costo
   @SuppressWarnings("unchecked")
  public static void main(String[] args) throws Exception{

    String word1 = args[0];
    String word2 = args[1];

    // this will never be changed (?)
    Class[] parameterTypes = {String.class, String.class};

    // can ask for method name
    String methodName = "computeLevenshteinDistance";

    // can ask for class name & get the Class
    Class theClass = Class.forName("Levenshtein");

    // get the method
    Method theCostMethod = theClass.getDeclaredMethod(methodName, parameterTypes);


    Alignment asd = new Alignment(theCostMethod);



    int res2 = asd.getAlignment(word1, word2);

    System.out.println(res2);

  }
}
