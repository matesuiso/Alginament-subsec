import java.lang.reflect.Method;
import java.lang.Math.*;
import java.util.Map;
import java.util.HashMap;

public class Alignment {
  private int lengthx;
  private int lengthy;
  private Method func; // cost method
  private Class cl;    // class from cost method
  private int[][] F;
  private String simbol = "-";
  private Map<Tupla<String, String>, Tupla<Integer, String>> map;

  // Constructor
  public Alignment (Method f) {
    func = f;
    cl = f.getDeclaringClass();
    map = new HashMap<Tupla<String, String>, Tupla<Integer, String>>();
  }

  // Auxiliar method to excecute the cost method
  private int cost (String x, String y) throws Exception{
    Object[] parameters = {x, y};

    try {
      // common failure, method isn't static,
      return (int) func.invoke(cl, parameters);
    } catch(Exception e) {
      throw new Exception ("Cost Method Failure. Values: first "+ parameters[0] + " second " + parameters[1]);
    }
  }


  public int getAlignment (String x, String y) throws Exception{
     lengthx = x.length();
     lengthy = y.length();

     String[] prueba = getStrings(x, y);

     System.out.println(prueba[0]);
     System.out.println(prueba[1]);

     return cost (x, y);
  }

  private String[] getStrings (String x, String y) throws Exception{
    String res1 = "";
      String res2 = "";

      if (y.length() > 0 && x.length() > 0) {

         if (x.charAt(0) == y.charAt(0)) {

            res1 += x.charAt(0);
            res2 += y.charAt(0);


            String [] tmp = getStrings (cortar(x), cortar(y));
            res1 += tmp[0];
            res2 += tmp[1];

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

            // ver si costo en hash

            try {
               rev1 = cost(revisar1[0], revisar1[1]);
               rev2 = cost(revisar2[0], revisar2[1]);
               rev3 = cost(revisar3[0], revisar3[1]);
            } catch(Exception e) {
               throw new Exception ("Cost Method Failure.");
            }

            // aca guardar hash

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

    String[] result = {res1, res2};
    return result;
}

   private String cortar (String x) {
      String resu = "";
      for (int i= 1; i< x.length() ; i++ ) {
         resu += x.charAt(i);
      }
      return resu;
   }

}
