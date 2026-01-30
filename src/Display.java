public class Display {
  private int width;
  private int height;
  private final int PIXELCHAR = 9600;

  public static void main(String[] args) {
    System.out.println("Bonjour bienvenue dans la classe pour créer un ecran pixelisé en java dans le terminal.");
    Display display = new Display(10, 10, 0);
    display.setPixel(1, 1, 2);
    display.setPixel(2, 1, 2);
    display.setPixel(3, 1, 2);

  }

  public Display(int width, int height, int background) {
    this.width = width;
    this.height = height;
    System.out.print("\033[0H\033[2J");
    System.out.printf("\033[%dm", 40 + background);

  }

  public void setPixel(int x, int y, int colorId) {
    int row = y/2;
    int colorPad = 30;
    if (y % 2 == 0) {
      colorPad = 40;
    }
    System.out.printf("\033[%d;%dH\033[%dm%c", row, x, colorPad + colorId, PIXELCHAR);
    try {
      Thread.sleep(1000);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
