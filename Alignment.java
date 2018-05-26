import java.lang.reflect.Method;
import java.lang.Math.*;

public class Alignment {
  private int lengthx;
  private int lengthy;
  private Method func; // cost method
  private Class cl;    // class from cost method
  private int[][] F;
  private String simbol = "-";

  // Constructor
  public Alignment (Method f) {
    func = f;
    cl = f.getDeclaringClass();
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

     String[] prueba = cosa(x, y, 1);

     System.out.println(prueba[0]);
     System.out.println(prueba[1]);

     return cost (x, y);
  }

  /*public String[] cosa (String x, String y, int i) throws Exception{
     String res1 = "";
     String res2 = "";
     System.out.println("hooola");
     if (y.length() > 0 && x.length() > 0) {
        System.out.println("entrada");
     if (x.charAt(i) == y.charAt(i)) {
        System.out.println("pasada casi importante");
        res1 += x.charAt(i);
        res2 += y.charAt(i);

        System.out.println("res1 " + res1 + " res2 " + res2);

        String [] tmp = cosa (x, y, i+1);
        res1 += tmp[0];
        res2 += tmp[1];

     } else {  // si no son iguales, deberia probar poner guion en cada lado
        System.out.println("pasada importante");
        // aca llamaria con el 'y' sin uno
        String res11 = "" + res1 + simbol;
        String res12 = "" + res2 + y.charAt(i);
        //String[] casiY = Arrays.copyOfRange(y, 1, y.length());
        String casiY = cortar(y);
        String[] revisar1 = cosa(x, casiY, i);

        // aca llamaria con el 'x' sin uno
        String res21 = "" + res1 + x.charAt(i);
        String res22 = "" + res2 + simbol;
        //String[] casiX = Arrays.copyOfRange(x, 1, x.length());
        String casiX = cortar(x);
        String[] revisar2 = cosa (casiX, y, i);

        String[] revisar3 = cosa (x, y, i);

        int rev1,rev2,rev3;
        try {
          rev1 = cost(revisar1[0], revisar1[1]);
          rev2 = cost(revisar2[0], revisar2[1]);
          rev3 = cost(revisar3[0], revisar3[1]);
        } catch(Exception e) {
          throw new Exception ("Cost Method Failure. Values");
        }

        if (rev1 < rev2) {
           if (rev1 < rev3) {
             res1 += revisar1[0];
             res2 += revisar1[1];
           } else {
             res1 += revisar3[0];
             res2 += revisar3[1];
          }
        } else {
           if (rev2 < rev3) {
             res1 += revisar2[0];
             res2 += revisar2[1];
           } else {
             res1 += revisar3[0];
             res2 += revisar3[1];
          }
        }

     }
     } else {
      res1 += x;
      res2 += y;
   }

     String[] result = {res1, res2};
     return result;
 }*/

 public String[] cosa (String x, String y, int i) throws Exception{
    String res1 = "";
    String res2 = "";

    if (y.length() > 0 && x.length() > 0) {

    if (x.charAt(0) == y.charAt(0)) {

      res1 += x.charAt(0);
      res2 += y.charAt(0);


      String [] tmp = cosa (cortar(x), cortar(y), i);
      res1 += tmp[0];
      res2 += tmp[1];

    } else {  // si no son iguales, deberia probar poner guion en cada lado

      // aca llamaria con el 'y' sin uno
      String res11 = "" + res1 + simbol;
      String res12 = "" + res2 + y.charAt(0);
      //String[] casiY = Arrays.copyOfRange(y, 1, y.length());
      String casiY = cortar(y);
      String[] revisar1 = cosa(x, casiY, i);

      // aca llamaria con el 'x' sin uno
      String res21 = "" + res1 + x.charAt(0);
      String res22 = "" + res2 + simbol;
      //String[] casiX = Arrays.copyOfRange(x, 1, x.length());
      String casiX = cortar(x);
      String[] revisar2 = cosa (casiX, y, i);

      String res31 = "" + res1 + x.charAt(0);
      String res32 = "" + res2 + y.charAt(0);
      String[] revisar3 = cosa (cortar(x), cortar(y), i);

      int rev1,rev2,rev3;
      try {
         rev1 = cost(revisar1[0], revisar1[1]);
         rev2 = cost(revisar2[0], revisar2[1]);
         rev3 = cost(revisar3[0], revisar3[1]);
      } catch(Exception e) {
         throw new Exception ("Cost Method Failure. Values");
      }

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
            res2 += "-";
         }
         res1 += x;
      } else {
         for (int k = 0; k < y.length() ; k++) {
            res1 += "-";
         }
         res2 += y;
      }

  }
   //System.out.println("res1 " + res1 + " res2 " + res2);
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
 /*private String[] cosa2 (String x, String y, int i) {
    String res1 = "";
    String res2 = "";

    String[] rev1 = ;
    String[] rev2 = ;

    String[] result = {res1, res2};
    return result;
}*/

}
