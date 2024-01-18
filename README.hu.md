# Egész hetes project: Wanderer - Az RPG játék

Készíts egy hős alapú, mezőkön mozgós, szörnyeket gyilkolós játékot.
Hősünket billentyűzet segítségével irányíthatjuk egy útvesztőben. A hősnek és
a szörnyeknek szintjük (level) és értékeik (stats) vannak amelyek a szinttől
függnek. A cél, hogy elérd a legmagasabb szintet a szörnyek levágásával és
a náluk relytőző kulcs megtalálásával, ami a következő szintre vezet.

## Workshop: Tervezd meg a munkád

### 0. Forkold le a repository-t (a saját user-hez)

### 1. Klónozd a repository-t a gépedre 

### 2. Menj végig a technikai részleteken

#### Hogyan indítsd el a programot

- A játék indítása a `Board` class `main()` függvényével történik.

- Mikor a specifikációkat és a sztorikat olvasod tartsd ezt észben.

- Itt egy példa :
  - Egy nagy rajzolható vászon melyre egy kép van rajzolva
  - és kezeli a lenyomott gombokat, hogy a hero tudjon mozogni
  - Ne feledd, hogy ezek csak a szükséges dolgok egy helyen,
  - bármit szétszedhetsz, akárhogy

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JComponent implements KeyListener {

  int testBoxX;
  int testBoxY;

  public Board() {
    testBoxX = 300;
    testBoxY = 300;

    // beállítja a rajztábla méretét
    setPreferredSize(new Dimension(720, 720));
    setVisible(true);
  }

  @Override
  public void paint(Graphics graphics) {
    super.paint(graphics);
    graphics.fillRect(testBoxX, testBoxY, 100, 100);
    // Van egy 720 x 720-as rajztábla
    // Az alábbi class-al készíthetsz és rajzolhatsz ki egy képet. pl.:
    PositionedImage image = new PositionedImage("yourimage.png", 300, 300);
    image.draw(graphics);
  }

  public static void main(String[] args) {
    // Itt láthatod, hogy készíthetsz egy új ablakot, és hogyan adhatod hozzá a táblánkat (board). 
    JFrame frame = new JFrame("RPG Game");
    Board board = new Board();
    frame.add(board);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.pack();
    // Itt láthatod, hogy adsz hozzá gomb esemény figyelőt (key event listener) 
    // A board object értesítődik ha valamelyik gomb lenyomásra kerül
    // és a rendszer meghívja az alábbi 3 függvény egyikét
    frame.addKeyListener(board);
    // Figyeld meg, (fent) hogy csak így tudjuk kivitelezni
    // mivel a Board class (a board object típusa) is egy KeyListener
  }
  // Hogy legyen egy KeyListenerünk, a classnak erre a 3 függvényre van szüksége.
  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {

  }
  // De valójában csak ezt a függvényt használjuk a projekt során
  @Override
  public void keyReleased(KeyEvent e) {
    // Mikor megnyomódik a lefele vagy felfele gomb, a négyzetünk pozíciója változik
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      testBoxY -= 100;
    } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
      testBoxY += 100;
    }
    // és újra rajzolódik az új koordinátákkal
    repaint();
  
  }

}

```

- Használhatod az alábbi image class-t alapul:

```java
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PositionedImage {

  BufferedImage image;
  int posX, posY;

  public PositionedImage(String filename, int posX, int posY) {
    this.posX = posX;
    this.posY = posY;
    try {
      image = ImageIO.read(new File(filename));
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void draw(Graphics graphics) {
    if (image != null) {
      graphics.drawImage(image, posX, posY, null);
    }

  }

}

```

### 3. Készíts egy GitHub projektet

- a repositorydban a munkádhoz, és add hozzá a [projekt sztorikat](https://github.com/green-fox-academy/teaching-materials/blob/master/project/wanderer/stories.hu.md) 

### 4. Alkossatok csoportokat és tervezzétek meg az applikációtokat együtt

Tervezd meg a felépítést. A felépítésben az alábbi elemeket gondold át:

- Models

- GameObject

  - Character

    - Monster

    - Hero

    - types

  - Area
  
    - Tile
      - EmptyTile
      - NotEmptyTile

- GameLogic

  - aktuális hero
  - aktuális mező(area)

- Main

  - események kezelése
  - aktuális játék

#### 5. Gondolkodjatok a feladatok (tasks) szétbontásáról Kanban

Most, hogy összeállt a kép, **menjetek végig a sztorin együtt** és 
gondolkodjatok el azon hogyan bontanátok le kisebb feladatokra:

- Classokra
- Függvényekre
- Adatra és cselekvésekre
- Bővítsd ki a sztori kártyákat ezekkel a pontokkal emlékeztetőként

#### 6. Kezdj el dolgozni az első feladatodon!