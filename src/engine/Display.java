package engine;
import engine.util.Color;

public class Display {
  private int width;
  private int height;
  private final int PIXELCHAR = 9600;
  private Color[][] screenBuffer;

  public static void main(String[] args) {
    System.out.println("Bonjour bienvenue dans la classe pour créer un ecran pixelisé en java dans le terminal.");
    Display display = new Display(10, 10, new Color(0, 0, 0));
    display.setPixel(1, 1, new Color(0, 63, 255));
    display.setPixel(2, 1, new Color(255, 0, 0));
    display.setPixel(3, 1, new Color(0, 255, 0));
  }

  public Display(int width, int height, Color background) {
    this.width = width;
    this.height = height;
    screenBuffer = new Color[height][width]
    System.out.print("\033[0H\033[2J");
  }

  public void setPixel(int x, int y, Color color) {
    int row = y/2;
    int colorPad = 38;
    if (y % 2 == 0) {
      colorPad = 48;
    }
    System.out.printf("\033[%d;%dH\033[%d;2;%d;%d;%dm%c", row, x, colorPad, color.r, color.g, color.b, PIXELCHAR);
    try {
      Thread.sleep(1000);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
