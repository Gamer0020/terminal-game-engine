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
    System.out.print("\033[?25l");
    Display display = new Display(50, 50, new Color(0, 0, 0)); //don’t put an odd length
    display.clear();
    for (int i = 0; i < 50; i++) {
      for (int j = 0; j < 50; j++) {
        display.setPixel(j, i, new Color(255, i*5, j * 5));
      }
    }

    for (int i = 0; i < 20; i++) {
      //display.setPixel(10, 10+i, Color.RED);
      //display.setPixel(10+i, 29, Color.BLUE);
      //display.setPixel(29, 29-i, Color.YELLOW);
      //display.setPixel(29-i, 10, Color.GREEN);
    }
    display.update();
    reset(true);
    System.out.println("\nEnd of program...");
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

    //Sort the list
    screenChanges.sort(
        Comparator.comparingInt(Pixel::getY)
        .thenComparingInt(Pixel::getX)
        );

    //Removing duplicates
    for (int i = 0; i < screenChanges.size(); i++) {
      if (i+1<screenChanges.size() && screenChanges.get(i).equals(screenChanges.get(i+1))) {
        System.out.println("Found duplicate " + screenChanges.get(i) + ". It duplicates " + screenChanges.get(i+1));
        screenChanges.remove(i);
        i--;
      }
    }

    for (Pixel pixel : screenChanges) {
      screenBuffer[pixel.y][pixel.x] = pixel.color;
    }
    //after this we assume that the screenChanges is sorted and without duplicates.
    pixelPrint();
    screenChanges.clear();
  }

  public void pixelPrint() {
    for (int i = 0; i < screenChanges.size(); i++) {
      Pixel pixel = screenChanges.get(i);

      Optional<Pixel> otherPixelTest = Optional.empty();

      //Donc la on va venir tester si le pixel est celui du haut pour apres venir chercher celui du bas. On part du principe que on va pas chercher celui pour un pixel du bas parce qu’il aura deja ete processed par son ami du haut.
      //
      //Meme si la commande pour display commence sur 1, on va rester sur 0 jusqu’a la toute fin. Donc le pixel du haut est paire.
      boolean topPixel = false;
      if (pixel.y % 2 == 0) { topPixel = true; }
       
      if (topPixel == true) {
        otherPixelTest = screenChanges.stream().filter(p -> (p.y == pixel.y+1)&&(p.x == pixel.x)).findFirst();
      }

      Pixel otherPixel;
      if (otherPixelTest.isPresent()) {
        otherPixel = otherPixelTest.get();
        screenChanges.remove(screenChanges.indexOf(otherPixel));
      } else if (topPixel) {
        otherPixel = new Pixel(pixel.x, pixel.y+1, screenBuffer[pixel.y+1][pixel.x]);
      } else {
        otherPixel = new Pixel(pixel.x, pixel.y-1, screenBuffer[pixel.y-1][pixel.x]);
      }
      
      if (topPixel) {
        System.out.print(Pixel.printString(pixel, otherPixel));
      } else {
        System.out.print(Pixel.printString(otherPixel, pixel));
      }
    }
  }
}
