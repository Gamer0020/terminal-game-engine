package engine.util;

import java.util.*;

public class Pixel {
  public final  int x;
  public final int y;
  public Color color;

  public static final char PIXELCHAR = 9600;

  public Pixel(int nx, int ny, Color nColor) {
    x = nx;
    y = ny;
    color = nColor;
  }

  public static String getPrintString(Pixel pixel1, Pixel pixel2) { //Retourne la string à print pour le charactère des deux pixels.
    Pixel a;
    Pixel b;
    if (pixel1.isTop()) {
      a = pixel1;
      b = pixel2;
    } else {
      a = pixel2;
      b = pixel1;
    }
    return String.format("\033[%d;%dH\033[38;2;%d;%d;%dm\033[48;2;%d;%d;%dm%c", (b.y + 1)/2, a.x+1, a.color.r, a.color.g, a.color.b, b.color.r, b.color.g, b.color.b, PIXELCHAR);
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

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean isTop() {
    return y % 2 == 0;
  }

  public Pixel getOtherPixel(List<Pixel> pixelList, Color[][] colorBuffer) {
    Optional<Pixel> otherPixelTest = Optional.empty();
       
    if (isTop()) {
      otherPixelTest = pixelList.stream().filter(p -> (p.y == y+1)&&(p.x == x)).findFirst();
      if (otherPixelTest.isPresent()) {
        Pixel otherPixel = otherPixelTest.get();
        pixelList.remove(pixelList.indexOf(otherPixel));
        return otherPixel;
      }
      System.out.println(colorBuffer[y+1][x]);
      return new Pixel(x, y+1, colorBuffer[y+1][x]);
    }
    return new Pixel(x, y-1, colorBuffer[y-1][x]);
  }
}
