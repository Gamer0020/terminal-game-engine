package engine.util;

public class Pixel {
  public int x;
  public int y;
  public Color color;

  public static final char PIXELCHAR = 9600;

  public Pixel(int nx, int ny, Color nColor) {
    x = nx;
    y = ny;
    color = nColor;
  }

  public static String printString(Pixel a, Pixel b) { //Retourne la string à print pour le charactère des deux pixels.
    return String.format("\033[%d;%dH\033[38;2;%d;%d;%dm\033[48;2;%d;%d;%dm%c", (b.y + 1)/2, a.x, a.color.r, a.color.g, a.color.b, b.color.r, b.color.g, b.color.b, PIXELCHAR);
  }

  @Override
  public boolean equals(Object o) {
    if (getClass() != o.getClass()) return false;
    if (this == o) return true;
    Pixel other = (Pixel) o;
    
    return (x == other.x && y == other.y) ? true : false;
  }

  @Override
  public String toString() {
    return String.format("Pixel(%d, %d, (%d, %d, %d))", x, y, color.r, color.g, color.b);
  }
}
