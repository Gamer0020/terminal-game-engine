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
    return String.format("\033[%d;%dH\033[38;2;%d;%d;%dm\033[48;2;%d;%d;%dm%c",a.x, (b.y + 1)/2, a.color.r, a.color.g, a.color.b, b.color.r, b.color.g, b.color.b, PIXELCHAR);
  }
}
