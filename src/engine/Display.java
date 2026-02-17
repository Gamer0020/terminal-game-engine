package engine;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import engine.util.Color;
import engine.util.Pixel;


public class Display {
  private int width;
  private int height;
  private final int PIXELCHAR = 9600;
  private Color[][] screenBuffer;
  private List<Pixel> screenChanges;

  private Color mainColor;
  private Color backgroundColor;
  private final Color defaultBackgroundColor;

  public static void main(String[] args) {
    System.out.println("Bonjour bienvenue dans la classe pour créer un ecran pixelisé en java dans le terminal.");
    System.out.print("\033[?25l");
    Display display = new Display(50, 50, new Color(0, 0, 0)); //don’t put an odd length
    display.clear();
    for (int i = 0; i < 50; i++) {
      for (int j = 0; j < 50; j++) {
        display.setPixel(j, i, new Color(255, i*10, j * 10));
      }
    }
    display.update();
    reset(true);
  }


  public Display(int nwidth, int nheight, Color background) {
    width = nwidth;
    height = nheight;
    defaultBackgroundColor = background;
    screenBuffer = new Color[height][width];
    screenChanges = new ArrayList<Pixel>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        screenBuffer[i][j] = background;
      }
    }

    reset();
  }

  public static void reset(boolean cursor) {
    System.out.print("\033[0m");
    if (cursor) {
      System.out.print("\033[?25h");
    }
  }

  public static void reset() {
    reset(false);
  }

  public void clear() {
    System.out.print("\033[0H\033[2J");
  }


  public void setPixel(int x, int y, Color color) {
    if (0 > y | y >= height | x < 0 | x >= width) {
      return;
    }
    screenChanges.add(0, new Pixel(x, y, color));
  }


  public void update() {
    //le but la c’est de faire le moins de print possible.
    Collections.sort(screenChanges, (a, b)-> {
      if (a.y == b.y) return a.x - b.x;
      return a.y - b.y;
    });
    
    for (Pixel element: screenChanges) {
      //System.out.printf("(%d, %d, (%d, %d, %d))\n", element.x, element.y, element.color.r, element.color.g, element.color.b);
      screenBuffer[element.y][element.x] = element.color;
    }
  }

  public void pixelPrint() {
    Iterator<Pixel> pixelIterator = screenChanges.iterator();
    List<int[]> screenChanged = new ArrayList<>();

    while (pixelIterator.hasNext()) {
      Pixel pixel = pixelIterator.next();
      if (screenChanged.contains(pixelIterator)) continue;
      Pixel pixel2 = new Pixel(pixel.x, pixel.y+1, screenBuffer[pixel.y+1][pixel.x]);

    }
  }
}
