// we start at    https://stackoverflow.com/questions/3038464/how-to-call-java-function-from-string-stored-in-a-variable
// and moves to   http://www.rgagnon.com/javadetails/java-0031.html

import java.lang.reflect.Method;

public class Main {

   // la parte que sigue es para que no joda cuando compila, es por el metodo que busca la funcion de costo
   @SuppressWarnings("unchecked")
  public static void main(String[] args) throws Exception{

    // These are the arguments are passed from console
    String word1 = args[0];
    String word2 = args[1];
    String simbol = args[2];
    String alphabet = args[3];
    String nom_func_cost = args[4];
    String nom_class_func_cost = args[5];

    // this will never be changed (?).. CostMethod revice 2 Strings and return a integer
    Class[] parameterTypes = {String.class, String.class};

    // get the Class
    Class theClass = Class.forName(nom_class_func_cost);

    // get the method
    Method theCostMethod = theClass.getDeclaredMethod(nom_func_cost, parameterTypes);

    Alignment asd = new Alignment(theCostMethod, simbol, alphabet);

    String[] res = asd.getAlignment(word1, word2);

    System.out.println(res[0]);
    System.out.println(res[1]);


  }
}
