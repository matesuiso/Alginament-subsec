import java.lang.reflect.Method;
import java.lang.Math.*;
import java.util.Map;
import java.util.HashMap;

public class Alignment {
  private Method func; // cost method
  private Class cl;    // class from cost method
  private String simbol;
  private Map<String, String[]> cache;
  private String alphabet;


  // Constructor
  public Alignment (Method f, String simb, String chars) {
    func = f;
    cl = f.getDeclaringClass();

    simbol = simb;
    alphabet = chars;
    cache = new HashMap<String, String[]>();

  }



  // Auxiliar method to excecute the cost method
  private int cost (String x, String y) throws Exception{
    Object[] parameters = {x, y};

    try {
      // common failure, method isn't static
      return (int) func.invoke(cl, parameters);
    } catch(Exception e) {
      throw new Exception ("Cost Method Failure. Values: first "+ parameters[0] + " second " + parameters[1]);
    }
  }



  // Method that verifies if the elements of the String belong to the alphabet
  private Boolean verif(String x) {

     for (int i= 0; i < x.length() ; i++) {
        if (alphabet.indexOf(x.charAt(i)) == -1) {
           return false;
        }
     }

     return true;
 }



 // Method that verifies the Strings and returns the alignment
  public String[] getAlignment (String x, String y) throws Exception{

     // Only run, if the Strings belong to the dictionary
     if (!(verif(x) && verif(y))) {
        throw new Exception ("Some strings have characters that do not belong to the alphabet");
     } else {

        String[] result = getStrings(x, y);
        return result;
     }
  }



  // Method that returns the best alignment
  private String[] getStrings (String x, String y) throws Exception{
     String res1 = "";
     String res2 = "";

     // If the recursive call has already been made, the stored value is returned
     if (cache.containsKey(x + y) & y.length() > 0 && x.length() > 0) {
        return cache.get(x + y);
     }

     // If a string is not zero, the calculation is made
     if (y.length() > 0 && x.length() > 0) {

        // If the Chars are equal, we align them
        if (x.charAt(0) == y.charAt(0)) {

           res1 += x.charAt(0);
           res2 += y.charAt(0);


           String [] tmp = getStrings (cortar(x), cortar(y));
           res1 += tmp[0];
           res2 += tmp[1];

         // If the Chars are not equal, we compare the different alignments
        } else {

           // aca llamamos con el 'y' sin un elemento
           String res11 = res1 + simbol;
           String res12 = res2 + y.charAt(0);
           String casiY = cortar(y);

           String[] revisar1 = getStrings(x, casiY);


           // aca llamamos con el 'x' sin un elemento
           String res21 = res1 + x.charAt(0);
           String res22 = res2 + simbol;
           String casiX = cortar(x);

           String[] revisar2 = getStrings (casiX, y);


           // aca llamams al metodo, salteando ambos
           String res31 = res1 + x.charAt(0);
           String res32 = res2 + y.charAt(0);

           String[] revisar3 = getStrings (cortar(x), cortar(y));


           int rev1,rev2,rev3;

           try {
             rev1 = cost(revisar1[0], revisar1[1]);
             rev2 = cost(revisar2[0], revisar2[1]);
             rev3 = cost(revisar3[0], revisar3[1]);
          } catch(Exception e) {
             throw new Exception ("Cost Method Failure.");
          }


          // We acum the best String in res1 and res2
          if (rev1 < rev2) {
             if (rev1 < rev3) {
                res1 += res11 + revisar1[0];
                res2 += res12 + revisar1[1];

             } else {
                res1 += res31 + revisar3[0];
                res2 += res32 + revisar3[1];

             }
          } else {
             if (rev2 < rev3) {
                res1 += res21 + revisar2[0];
                res2 += res22 + revisar2[1];

             } else {
                res1 += res31 + revisar3[0];
                res2 += res32 + revisar3[1];

             }
          }


       }

   // If some String are zero, we fill it with the simbol
    } else {

      if (y.length() == 0) {
         for (int k = 0; k < x.length() ; k++) {
            res2 += simbol;
         }
         res1 += x;
      } else {
         for (int k = 0; k < y.length() ; k++) {
            res1 += simbol;
         }
         res2 += y;
      }

   }

   // We made an auxiliary to return the best result, and save it
   String[] result = {res1, res2};
   cache.put(x + y, result);

   return result;

   }


   // This method, cut the first element of a String
   private String cortar (String x) {
      String resu = "";
      for (int i= 1; i< x.length() ; i++ ) {
         resu += x.charAt(i);
      }
      return resu;
   }

}
