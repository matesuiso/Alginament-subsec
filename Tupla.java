public class Tupla <X, Y> {
   private X x;
   private Y y;

   public Tupla (X first, Y second) {
      x = first;
      y = second;
   }

   public X getFirst () {
      return x;
   }

   public Y getSecond () {
      return y;
   }

}
