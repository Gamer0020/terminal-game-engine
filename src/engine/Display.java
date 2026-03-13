package engine;

import java.util.*;
import java.util.stream.Collectors;
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
    Display display = new Display(100, 100, new Color(0, 0, 0)); //don’t put an odd length
    
    int x = 0;
    int y = 0;
    int vx = 1;
    int vy = 2;

    for (int k = 0; k < 1000; k++) {
      long currentTime = System.currentTimeMillis();

      for (int i = 0; i < 50; i++) {
        for (int j = 0; j < 50; j++) {
          //display.setPixel(j, i, new Color(k % 256, 255, 0));
        }
      }

      if (x + vx >= display.width || x + vx < 0) vx *= -1;
      if (y + vy >= display.height || y + vy < 0) vy *= -1;

      x += vx;
      y += vy;

      display.clear();
      display.setPixel(x, y, Color.red);
      display.update();
      long timeTaken = System.currentTimeMillis()- currentTime;
      if (timeTaken < 16) {
        try {
          Thread.sleep(16-timeTaken);
        } catch (Exception e) {}
      }
      timeTaken = System.currentTimeMillis()-currentTime;
      long FPS;
      try {
        FPS = 1000/timeTaken;
      } catch (ArithmeticException e) {
        FPS = 1000;
      }
      System.out.println("\033[" + display.height + ";1H" + Color.getColorChangeString(Color.white, Color.black) + "Time taken: " + timeTaken + ". FPS : " + FPS);
    }
    reset(true);
  }


  public Display(int nwidth, int nheight, Color background) {
    width = nwidth;
    height = nheight;
    defaultBackgroundColor = background;
    screenBuffer = new Color[height][width];
    screenChanges = new ArrayList<Pixel>();

    clear();
    System.out.print("\033[?25l");
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
    System.out.print("\033c");
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        screenBuffer[i][j] = defaultBackgroundColor;
      }
    }
  }


  public void setPixel(int x, int y, Color color) {
    if (0 > y | y >= height | x < 0 | x >= width) {
      return;
    }
    screenChanges.add(new Pixel(x, y, color));
  }

  private void sortList(List<Pixel> list) {

    //Sorting the list
    list.sort(
        Comparator.comparingInt(Pixel::getY)
        .thenComparingInt(Pixel::getX)
        );

    //Removing duplicates
    for (int i = 0; i < list.size(); i++) {
      if (i+1 < list.size() && list.get(i).equals(screenChanges.get(i+1))) {
        
        list.remove(i);
        i--;
      }
    }
  }


  public void update() {
    //le but la c’est de faire le moins de print possible

    sortList(screenChanges);
    for (Pixel pixel : screenChanges) {
      screenBuffer[pixel.y][pixel.x] = pixel.color;
    }
    //after this we assume that the screenChanges is sorted and without duplicates.

    System.out.print("\033[1;1H" + Color.getColorChangeString(defaultBackgroundColor, defaultBackgroundColor));

    if (screenChanges.size() < 100) {
      pixelPrint();
    } else if (false){
      linePrint();
    } else {
      screenPrint();
    }
    screenChanges.clear();
    reset();
  }

  private void pixelPrint() {
    for (int i = 0; i < screenChanges.size(); i++) {
      Pixel pixel = screenChanges.get(i);

      //Donc la on va venir tester si le pixel est celui du haut pour apres venir chercher celui du bas. On part du principe que on va pas chercher celui pour un pixel du bas parce qu’il aura deja ete processed par son ami du haut.
      //
      //Meme si la commande pour display commence sur 1, on va rester sur 0 jusqu’a la toute fin. Donc le pixel du haut est paire.
      Pixel otherPixel = pixel.getOtherPixel(screenChanges, screenBuffer);
      if (otherPixel.color.equals(Color.red)) System.err.println("\033[0mNot normal");

      System.out.print(Pixel.getPrintString(pixel, otherPixel));
    }
  }

  private void linePrint() {
    for (int y = 0; y < height; y+=2) {
      String line = getLine(y);
      System.out.println(line);
    //System.out.println("Lineprint function...");
    }
  }


  private String getLine(int y) {
    Color topColor = new Color(defaultBackgroundColor);
    Color bottomColor = new Color(defaultBackgroundColor);
    String line = "";
    for (int x = 0; x < width; x++) {
			Color topPixel = screenBuffer[y][x];
			Color bottomPixel = y + 1 < height ? screenBuffer[y+1][x] : defaultBackgroundColor;
			if (!(topPixel.equals(topColor) && bottomPixel.equals(bottomColor))) {
				topColor.set(topPixel);
				bottomColor.set(bottomPixel);
        //System.out.printf("(%d, %d, %d)", topColor.r, topColor.g, topColor.b);
        try {
          //Thread.sleep(100);
        } catch (Exception e) {}
				line += Color.getColorChangeString(topPixel, bottomPixel);
			}
			line += Character.toString(PIXELCHAR);
    }

    return line;
  }

  private void screenPrint() {
    String stringToPrint = "";
    for (int y = 0; y < height; y+=2) {
      stringToPrint += getLine(y);
      stringToPrint += "\n";
    }
    System.out.println(stringToPrint);
    //System.out.println("Printing using the screenPrint function...");
  }
}
