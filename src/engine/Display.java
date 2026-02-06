package engine;
import engine.util.Color;

public class Display {
  private int width;
  private int height;
  private final int PIXELCHAR = 9600;
  private Color[][] screenBuffer;
  private Color[][] screenChanges;

  private Color mainColor;
  private Color backgroundColor;

  public static void main(String[] args) {
    System.out.println("Bonjour bienvenue dans la classe pour créer un ecran pixelisé en java dans le terminal.");
    System.out.print("\033[?25l");
    Display display = new Display(25, 25, new Color(0, 0, 0));
    display.setPixel(0, 0, new Color(0, 63, 255));
    display.setPixel(1, 0, new Color(255, 0, 0));
    display.setPixel(2, 0, new Color(0, 255, 0));
    display.setPixel(1, 1, new Color(255, 255, 0));
    display.setPixel(3, 3, new Color(255, 0, 255));
    for (int i = 0; i < 25; i++) {
      for (int j = 0; j < 25; j++) {
        display.setPixel(j, i, new Color(i * 10, 255, j * 10));
      }
    }
    display.update();
    System.out.print("\033[?25h");
    System.out.println("\033[0m");
  }

  public Display(int nwidth, int nheight, Color background) {
    width = nwidth;
    height = nheight;
    screenBuffer = new Color[height][width];
    for (int i = 0; i < height/2; i++) {
      for (int j = 0; j < width; j++) {
        screenBuffer[i][j] = background;
        screenBuffer[i][j] = background;
      }
    }
    System.out.print("\033[0H\033[2J");
  }

  public void setPixel(int x, int y, Color color) {
    if (0 > y | y >= height | x < 0 | x >= width) {
      return;
    }
    screenBuffer[y][x] = color;
    int row = y/2;
    mainColor = screenBuffer[y - (y%2)][x];
    backgroundColor = screenBuffer[y%2 == 0 ? y + 1 : y][x];
    System.out.printf("\033[%d;%dH\033[38;2;%d;%d;%dm\033[48;2;%d;%d;%dm%c", row + 1, x + 1, mainColor.r, mainColor.g, mainColor.b, backgroundColor.r, backgroundColor.g, backgroundColor.b, PIXELCHAR);
    try {
      //Thread.sleep(0);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void update() {
    
  }
}
