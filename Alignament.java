import java.lang.reflect.Method;

public class Alignament {
  private int lengthx;
  private int lengthy;
  private Method func; // cost method
  private Class cl;    // class from cost method

  // Constructor
  public Alignament (Method f) {
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


   public int getAlignament (String x, String y) throws Exception{
     lengthx = x.length();
     lengthy = y.length();

     return cost (x, y);
  }

}
