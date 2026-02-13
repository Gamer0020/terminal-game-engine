package engine;
import engine.util.Color;
import engine.util.Pixel;
import java.util.ArrayList;

public class Display {
  private int width;
  private int height;
  private final int PIXELCHAR = 9600;
  private Color[][] screenBuffer;
  private ArrayList<Pixel> screenChanges;

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
    screenChanges.add(new Pixel(x, y, color));
  }


  public void update() {
    //le but la c’est de faire le moins de print possible.
    System.out.print("\033[38;2;255;0;0m");
    System.out.printf("%c%c%c%c\n",PIXELCHAR, PIXELCHAR, PIXELCHAR, PIXELCHAR);
    System.out.printf("%c%c%c%c\n",PIXELCHAR, PIXELCHAR, PIXELCHAR, PIXELCHAR);
  }
}
