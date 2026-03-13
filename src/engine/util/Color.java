package engine.util;

public class Color {
  public int r;
  public int g;
  public int b;

  public static final Color red = new Color(255, 0, 0);
  public static final Color turquoise = new Color(0, 255, 255);
  public static final Color blue = new Color(0, 0, 255);
  public static final Color green = new Color(0, 255, 0);
  public static final Color yellow = new Color(255, 255, 0);
  public static final Color purple = new Color(255, 0, 255);
  public static final Color white = new Color(255, 255, 255);
  public static final Color black = new Color(0,0,0);


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

  public Color(Color other) {
    this.set(other);
  }

  public void set(Color other) {
    r = other.r;
    g = other.g;
    b = other.b;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    if (o.getClass() != getClass()) return false;

    Color c = (Color) o;

    if (c.r == r && c.g == g && c.b == b) return true;

    return false;
  }

  @Override 
  public String toString() {
    return String.format("(%d, %d, %d)", r, g, b);
  }

  public static String getColorChangeString(Color top, Color bottom) {
    return String.format("\033[38;2;%d;%d;%dm\033[48;2;%d;%d;%dm", top.r, top.g, top.b, bottom.r, bottom.g, bottom.b);
  }
}
