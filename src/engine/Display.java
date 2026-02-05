package engine;
import engine.util.Color;

public class Display {
  private int width;
  private int height;
  private final int PIXELCHAR = 9600;
  private Color[][] screenBuffer;
  private Color[][] screenChanges;

  public static void main(String[] args) {
    System.out.println("Bonjour bienvenue dans la classe pour créer un ecran pixelisé en java dans le terminal.");
    Display display = new Display(10, 10, new Color(0, 0, 0));
    display.setPixel(0, 0, new Color(0, 63, 255));
    display.setPixel(1, 0, new Color(255, 0, 0));
    display.setPixel(2, 0, new Color(0, 255, 0));
    display.setPixel(1, 1, new Color(255, 255, 0));
    display.setPixel(3, 3, new Color(255, 0, 255));

    System.out.println("\033[0m");
  }

  public Display(int nwidth, int nheight, Color background) {
    width = nwidth;
    height = nheight;
    screenBuffer = new Color[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        screenBuffer[i][j] = background;
      }
    }
    System.out.print("\033[0H\033[2J");
  }

  public void setPixel(int x, int y, Color color) {
    if (0 > y | y >= height | x < 0 | x > width) {
      return;
    }
    screenBuffer[y][x] = color;
    int row = y/2;
    System.out.printf("\n%d", row);
    int colorPad = 48;
    if (y % 2 == 0) {
      colorPad = 38;
    }
    System.out.printf("\033[%d;%dH\033[%d;2;%d;%d;%dm%c", row + 1, x + 1, colorPad, color.r, color.g, color.b, PIXELCHAR);
    try {
      Thread.sleep(1000);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void update() {
    
  }
}
