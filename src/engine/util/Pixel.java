package engine.util;

import engine.util.Color;

public class Pixel {
  public int x;
  public int y;
  public Color color;

  public Pixel(int nx, int ny, Color nColor) {
    x = nx;
    y = ny;
    color = nColor;
  }
}
