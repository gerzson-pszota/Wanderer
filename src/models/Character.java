package models;

import java.util.Random;

public abstract class Character {
  protected int positionX;
  protected int positionY;
  protected int maxHealthPoint;
  protected int currentHealthPoint;
  protected int defendPoint;
  protected int strikePoint;
  protected int level;
  protected String typeOfCharacter;
  protected boolean isAlive;

  public Character() {
    isAlive = true;
  }

  public int diceRoll() {
    Random random = new Random();
    int result = 0;
    while (true) {
      result = random.nextInt(7);
      if (result != 0) break;
    }
    return result;
  }

  public void deductHP(int strikeValue) {
    currentHealthPoint -= (strikeValue - defendPoint);
  }

  public void levelUp(){
    level++;
  }
  public void setPosition(int x, int y) {
    this.positionX = x;
    this.positionY = y;
  }

  public int getPositionX() {
    return positionX;
  }

  public void setPositionX(int positionX) {
    this.positionX = positionX;
  }

  public int getPositionY() {
    return positionY;
  }

  public void setPositionY(int positionY) {
    this.positionY = positionY;
  }

  public int getMaxHealthPoint() {
    return maxHealthPoint;
  }

  public void setMaxHealthPoint(int maxHealthPoint) {
    this.maxHealthPoint = maxHealthPoint;
  }

  public int getCurrentHealthPoint() {
    return currentHealthPoint;
  }

  public void setCurrentHealthPoint(int currentHealthPoint) {
    this.currentHealthPoint = currentHealthPoint;
  }

  public int getDefendPoint() {
    return defendPoint;
  }

  public void setDefendPoint(int defendPoint) {
    this.defendPoint = defendPoint;
  }

  public int getStrikePoint() {
    return strikePoint;
  }

  public void setStrikePoint(int strikePoint) {
    this.strikePoint = strikePoint;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public String getTypeOfCharacter() {
    return typeOfCharacter;
  }

  public void setTypeOfCharacter(String typeOfCharacter) {
    this.typeOfCharacter = typeOfCharacter;
  }

  public boolean isAlive() {
    return isAlive;
  }

  public void setAlive(boolean alive) {
    isAlive = alive;
  }
}
