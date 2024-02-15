import models.Boss;
import models.Character;
import models.Hero;
import models.Monster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.Color.*;

public class Board extends JComponent implements KeyListener {
  // Board class is messy and full of duplicated code. Next task is to create separate classes (e.g.
  // BattleLogic etc.) & clean up the code.
  protected int level = 1;
  protected int tileSize = 72;
  protected int mapWidth = 10;
  protected int mapHeight = 10;
  protected int monster1X = 5 * tileSize;
  protected int monster1Y = 0;
  protected int monster2X = 0;
  protected int monster2Y = 5 * tileSize;
  protected int monster3X = 7 * tileSize;
  protected int monster3Y = 6 * tileSize;
  protected int bossX = 9 * tileSize;
  protected int bossY = 9 * tileSize;
  protected int[][] board = {
    {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 1, 0, 1, 0, 1, 1, 0},
    {0, 1, 1, 1, 0, 1, 0, 1, 1, 0},
    {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
    {1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
    {0, 1, 3, 1, 0, 0, 0, 0, 0, 0},
    {0, 1, 0, 1, 0, 1, 1, 0, 1, 0},
    {0, 0, 0, 0, 0, 1, 1, 0, 1, 0},
    {0, 1, 1, 1, 0, 0, 0, 0, 1, 0},
    {0, 0, 0, 1, 0, 1, 1, 0, 0, 0}
  };

  Hero myHero = new Hero();
  Monster monster1 = new Monster();
  Monster monster2 = new Monster();
  Monster monster3 = new Monster();
  Boss boss = new Boss();

  PositionedImage heroImage = new PositionedImage("img/hero-down.png", 0, 0);
  PositionedImage monster1Image = new PositionedImage("img/skeleton.png", monster1X, monster1Y);
  PositionedImage monster2Image = new PositionedImage("img/skeleton.png", monster2X, monster2Y);
  PositionedImage monster3Image = new PositionedImage("img/skeleton.png", monster3X, monster3Y);
  PositionedImage bossImage = new PositionedImage("img/boss.png", bossX, bossY);
  PositionedImage door = new PositionedImage("img/door.png", 2 * tileSize, 5 * tileSize);
  String floorTile = new String("img/floor.png");
  String wallTile = new String("img/wall.png");

  public Board() {
    setPreferredSize(new Dimension(720, 720));
    setVisible(true);
  }

  @Override
  public void paint(Graphics graphics) {
    super.paint(graphics);

    for (int i = 0; i < 10; i++) { // drawing the board
      for (int j = 0; j < 10; j++) {
        int floorOrWall = board[i][j];
        String imagePath = (floorOrWall == 0) ? floorTile : wallTile;
        PositionedImage image = new PositionedImage(imagePath, j * tileSize, i * tileSize);
        image.draw(graphics);
      }
    }

    door.draw(graphics); // drawing the door
    heroImage.draw(graphics); // drawing hero

    if (monster1.isAlive()) {
      monster1Image.draw(graphics); // drawing skeleton1
    }
    if (monster2.isAlive()) {
      monster2Image.draw(graphics); // drawing skeleton2
    }
    if (monster3.isAlive()) {
      monster3Image.draw(graphics); // drawing skeleton3
    }
    if (boss.isAlive()) {
      bossImage.draw(graphics); // drawing boss
    }
    drawCharacterStats(graphics); // drawing stats HUD
  }

  public static void main(String[] args) { // main method
    JFrame frame = new JFrame("Wanderer");
    Board board = new Board();
    frame.add(board);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.pack();
    frame.addKeyListener(board);
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {}

  @Override
  public void keyReleased(KeyEvent e) {
    int nextX = heroImage.posX; // next position based on direction
    int nextY = heroImage.posY;

    if (e.getKeyCode() == KeyEvent.VK_UP) { // hero's direction
      nextY -= tileSize;
      heroImage.setImage("img/hero-up.png");

    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      nextY += tileSize;
      heroImage.setImage("img/hero-down.png");
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      nextX -= tileSize;
      heroImage.setImage("img/hero-left.png");
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      nextX += tileSize;
      heroImage.setImage("img/hero-right.png");
    }

    if (nextX >= 0
        && nextX < mapWidth * tileSize
        && // boundaries & wall check
        nextY >= 0
        && nextY < mapHeight * tileSize
        && board[nextY / tileSize][nextX / tileSize] != 1) {

      heroImage.posX = nextX; // update hero's image position
      heroImage.posY = nextY;
    }

    myHero.setPosition(
        nextX / tileSize, nextY / tileSize); // update object position based on image position
    monster1.setPosition(monster1X, monster1Y);
    monster2.setPosition(monster2X, monster2Y);
    monster3.setPosition(monster3X, monster3Y);
    boss.setPosition(bossX, bossY);

    battle(getGraphics());

    if (nextX == 2 * tileSize
        && nextY == 5 * tileSize
        && myHero.gotKey()
        && !monster1.isAlive()
        && !monster2.isAlive()
        && !monster3.isAlive()
        && !boss.isAlive()) {
      level++;
      if (level == 2) {
        resetGameForLevel2(getGraphics());
      }
    }

    if (nextX == 2 * tileSize
        && nextY == tileSize
        && myHero.gotKey()
        && !monster1.isAlive()
        && !monster2.isAlive()
        && !monster3.isAlive()
        && !boss.isAlive()) {
      level++;
      if (level == 3) {
        resetGameForLevel3(getGraphics());
      }
    }

    if (nextX == 9 * tileSize
        && nextY == 9 * tileSize
        && myHero.gotKey()
        && !monster1.isAlive()
        && !monster2.isAlive()
        && !monster3.isAlive()
        && !boss.isAlive()) {
      level++;
      if (level == 4) {
        System.exit(0);
      }
    }

    repaint();
  }

  public void battle(Graphics graphics) {
    int heroTileX = heroImage.posX / tileSize;
    int heroTileY = heroImage.posY / tileSize;

    if (heroTileX == monster1X / tileSize
        && heroTileY == monster1Y / tileSize) { // checks if enemy and hero are on the same tile
      while (myHero.getCurrentHealthPoint() > 0
          || monster1.getCurrentHealthPoint() > 0) { // loop until someone dies
        if (monster1.isAlive()) { // if enemy is alive, start the fight
          heroStrikesEnemy(monster1);
          if (monster1.getCurrentHealthPoint() <= 0) {
            PositionedImage image = new PositionedImage("img/boom.png", monster1X, monster1Y);
            image.draw(graphics);
            monster1.setAlive(false);
            myHero.levelUp();
            break;
          } else {
            enemyStrikesHero(monster1);
            if (myHero.getCurrentHealthPoint() <= 0) {
              System.exit(0); // need to find a more elegant way for Game Over
            }
          }
        }
      }

    } else if (heroTileX == monster2X / tileSize && heroTileY == monster2Y / tileSize) {
      while (myHero.getCurrentHealthPoint() > 0 || monster2.getCurrentHealthPoint() > 0) {
        if (monster2.isAlive()) {
          heroStrikesEnemy(monster2);
          if (monster2.getCurrentHealthPoint() <= 0) {
            PositionedImage image = new PositionedImage("img/boom.png", monster2X, monster2Y);
            image = new PositionedImage("img/key.png", monster2X, monster2Y);
            image.draw(graphics);
            monster2.setAlive(false);
            myHero.setGotKey(true);
            myHero.levelUp();
            break;
          } else {
            enemyStrikesHero(monster2);
            if (myHero.getCurrentHealthPoint() <= 0) {
              System.exit(0); // need to find a more elegant way for Game Over
            }
          }
        }
      }
    } else if (heroTileX == monster3X / tileSize && heroTileY == monster3Y / tileSize) {
      while (myHero.getCurrentHealthPoint() > 0 || monster3.getCurrentHealthPoint() > 0) {
        if (monster3.isAlive()) {
          heroStrikesEnemy(monster3);
          if (monster3.getCurrentHealthPoint() <= 0) {
            PositionedImage image = new PositionedImage("img/boom.png", monster3X, monster3Y);
            image.draw(graphics);
            monster3.setAlive(false); // enemy dies
            myHero.levelUp();
            break;
          } else {
            enemyStrikesHero(monster3);
            if (myHero.getCurrentHealthPoint() <= 0) {
              System.exit(0); // need to find a more elegant way for Game Over
            }
          }
        }
      }
    } else if (heroTileX == bossX / tileSize && heroTileY == bossY / tileSize) {
      while (myHero.getCurrentHealthPoint() > 0 || boss.getCurrentHealthPoint() > 0) {
        if (boss.isAlive()) {
          heroStrikesEnemy(boss);
          if (boss.getCurrentHealthPoint() <= 0) {
            PositionedImage image = new PositionedImage("img/boom.png", bossX, bossY);
            image.draw(graphics);
            boss.setAlive(false); // enemy dies
            myHero.levelUp();
            break;
          } else {
            enemyStrikesHero(boss);
            if (myHero.getCurrentHealthPoint() <= 0) {
              System.exit(0); // need to find a more elegant way for Game Over
            }
          }
        }
      }
    }
  }

  public void heroStrikesEnemy(Character enemy) {
    int strikeValue = 2 * myHero.diceRoll() + myHero.getStrikePoint();
    if (strikeValue > enemy.getDefendPoint()) {
      enemy.deductHP(strikeValue);
    }
  }

  private void enemyStrikesHero(Character enemy) {
    int strikeValue = 2 * enemy.diceRoll() + enemy.getStrikePoint();
    if (strikeValue > myHero.getDefendPoint()) {
      myHero.deductHP(strikeValue);
    }
  }

  public void drawCharacterStats(Graphics graphics) {
    int frameX = 2 * tileSize;
    int frameY = 690;
    int frameWidth = 6 * tileSize;
    int frameHeight = 50;

    Color c = new Color(255, 255, 255, 180); // frame
    graphics.setColor(c);
    graphics.fillRoundRect(frameX, frameY, frameWidth, frameHeight, 35, 35);

    graphics.setColor(black); // stats
    graphics.setFont(graphics.getFont().deriveFont(18F));
    int textX = frameX + 53;
    int textY = frameY + 20;
    graphics.drawString(
        myHero.getTypeOfCharacter()
            + " (Level "
            + myHero.getLevel()
            + ") HP: "
            + myHero.getCurrentHealthPoint()
            + "/"
            + myHero.getMaxHealthPoint()
            + " | DP: "
            + myHero.getDefendPoint()
            + " | SP: "
            + myHero.getStrikePoint(),
        textX,
        textY);
  }

  public void nextAreaStats() {
    double randomNumber = Math.random() * 10;
    int currentHP = myHero.getCurrentHealthPoint();
    int maxHP = myHero.getMaxHealthPoint();

    if (randomNumber < 1) {
      myHero.setCurrentHealthPoint(maxHP);
    } else if (randomNumber < 4) {
      myHero.setCurrentHealthPoint(currentHP += maxHP / 3);
      if (currentHP > maxHP) {
        currentHP = maxHP;
      }
    } else if (randomNumber < 5) {
      currentHP += maxHP / 10;
      if (currentHP > maxHP) {
        currentHP = maxHP;
      }
    }
    monster1.levelUp();
    monster2.levelUp();
    monster3.levelUp();
    boss.levelUp();
  }

  public void resetGameForLevel2(Graphics graphics) {
    heroImage.posX = 0;
    heroImage.posY = 0;

    monster1X = 5 * tileSize;
    monster1Y = 0;
    monster2X = 0;
    monster2Y = 5 * tileSize;
    monster3X = 5 * tileSize;
    monster3Y = 8 * tileSize;
    bossX = 9 * tileSize;
    bossY = 3 * tileSize;

    monster1.setAlive(true);
    monster2.setAlive(true);
    monster3.setAlive(true);
    boss.setAlive(true);
    myHero.setGotKey(false);

    monster1Image = new PositionedImage("img/Golem.png", monster1X, monster1Y);
    monster2Image = new PositionedImage("img/Golem.png", monster2X, monster2Y);
    monster3Image = new PositionedImage("img/Golem.png", monster3X, monster3Y);
    bossImage = new PositionedImage("img/lvl2boss.png", bossX, bossY);
    door = new PositionedImage("img/door.png", 2 * tileSize, tileSize);

    floorTile = "img/floor2.png";
    wallTile = "img/wall2.png";

    board =
        new int[][] {
          {0, 0, 1, 1, 0, 0, 0, 1, 1, 1},
          {0, 0, 0, 1, 0, 1, 1, 1, 1, 1},
          {0, 1, 1, 1, 0, 0, 1, 1, 1, 1},
          {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
          {1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
          {0, 1, 3, 1, 0, 0, 0, 0, 1, 0},
          {0, 1, 0, 1, 0, 1, 1, 0, 1, 0},
          {0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
          {0, 1, 1, 1, 0, 0, 1, 0, 1, 0},
          {0, 0, 0, 0, 0, 1, 1, 0, 0, 0}
        };

    nextAreaStats();
    repaint();
  }

  public void resetGameForLevel3(Graphics graphics) {
    heroImage.posX = 0;
    heroImage.posY = 0;

    monster1X = 4 * tileSize;
    monster1Y = 0;
    monster2X = tileSize;
    monster2Y = 5 * tileSize;
    monster3X = 6 * tileSize;
    monster3Y = 8 * tileSize;
    bossX = 7 * tileSize;
    bossY = 4 * tileSize;

    monster1.setAlive(true);
    monster2.setAlive(true);
    monster3.setAlive(true);
    boss.setAlive(true);
    myHero.setGotKey(false);

    monster1Image = new PositionedImage("img/fireGolem.png", monster1X, monster1Y);
    monster2Image = new PositionedImage("img/fireGolem.png", monster2X, monster2Y);
    monster3Image = new PositionedImage("img/fireGolem.png", monster3X, monster3Y);
    bossImage = new PositionedImage("img/boss.png", bossX, bossY);
    door = new PositionedImage("img/door.png", 9 * tileSize, 9 * tileSize);

    floorTile = "img/floor3.png";
    wallTile = "img/lava.png";

    board =
        new int[][] {
          {0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
          {1, 1, 0, 1, 1, 0, 0, 0, 0, 1},
          {0, 1, 0, 1, 1, 1, 1, 1, 0, 1},
          {1, 1, 0, 0, 0, 0, 0, 1, 0, 1},
          {1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
          {1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
          {1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
          {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
          {1, 0, 1, 0, 1, 1, 0, 0, 1, 0},
          {1, 1, 1, 1, 1, 1, 1, 1, 1, 3}
        };

    nextAreaStats();
    repaint();
  }
}
