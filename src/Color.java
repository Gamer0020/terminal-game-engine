public class Color {
  public static void main(String[] args) {
    try {
      int r = Integer.parseInt(args[0]);
      int g = Integer.parseInt(args[1]);
      int b = Integer.parseInt(args[2]);
      System.out.printf("\033[48;2;%d;%d;%dmThis is what your color looks like.\n \033[0m\n", r, g, b);
    } catch (Exception e) {
      System.err.println("Please enter a valid rgb value in the format \"r g b\"");
      System.err.println(e);
    }
  }
}
