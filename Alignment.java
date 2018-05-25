import java.lang.reflect.Method;
import java.lang.Math.*;

public class Alignment {
  private int lengthx;
  private int lengthy;
  private Method func; // cost method
  private Class cl;    // class from cost method
  private int[][] F;

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
     F = new int[lengthx+1][lengthy+1];

     fill(x, y);

     String[] asd = getAlignment1(x, y);

     System.out.println(asd[0]);
     System.out.println(asd[1]);


     return cost (x, y);
  }

  public String[] getAlignment1 (String A, String B) throws Exception {
     String AlignmentA = "";
     String AlignmentB = "";
     int i = lengthx;
     int j = lengthy;

     while (i > 0 && j > 0) {
        int temp;

        try {
          temp = cost("" + A.charAt(i-1),"" + B.charAt(j-1));
        } catch(Exception e) {
          throw new Exception ("Cost Method Failure. Values " + i + "  " + j);
        }

        if (i > 0 && j > 0 && F[i][j] == F[i-1][j-1] + temp) {
          AlignmentA = "" + A.charAt(i-1) + AlignmentA;
          AlignmentB = "" + B.charAt(j-1) + AlignmentB;
          i--;
          j--;
       } else if (i > 0 && F[i][j] == F[i-1][j] + 1) {
          AlignmentA = A.charAt(i-1) + AlignmentA;
          AlignmentB = "-" + AlignmentB;
          i--;
       } else {
          AlignmentA = "-" + AlignmentA;
          AlignmentB = B.charAt(j-1) + AlignmentB;
          j--;
       }
    }
    String[] cosa = {AlignmentA, AlignmentB};

    return cosa;
   }

   private void fill(String A, String B) throws Exception {
      int d = 1;

      for (int i = 0; i < lengthx ; i++ ) {
         F[i][0] = 1*i;
      }

      for (int j = 0; j < lengthx ; j++ ) {
         F[0][j] = 1*j;
      }

      int match;
      int delete;
      int insert;

      for (int i = 1 ; i < lengthx ; i++ ) {
         for (int j = 1; j < lengthy ; j++ ) {
            try {
              // common failure, method isn't static,
              match = cost("" + A.charAt(i-1), "" + B.charAt(j-1));
            } catch(Exception e) {
              throw new Exception ("Cost Method Failure. Values");
            }
            delete = F[i-1][j] +1;
            insert = F[i][j-1] +1;

            int maximo = Math.max(Math.max(match, delete), insert);

            F[i][j] = maximo;

         }
      }
   }



}
