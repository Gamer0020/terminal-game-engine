package engine.util;

public class Color {
  public int r;
  public int g;
  public int b;


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

  public Color(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public Color() {
    this.r = 0;
    this.g = 0;
    this.b = 0;
  }
}
