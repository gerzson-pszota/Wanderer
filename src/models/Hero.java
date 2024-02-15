package models;

public class Hero extends Character {
  protected boolean gotKey;

  public Hero() {
    level = 1;
    maxHealthPoint = 20 + (3 * diceRoll());
    currentHealthPoint = maxHealthPoint;
    defendPoint = 2 * diceRoll();
    strikePoint = 5 + diceRoll();
    typeOfCharacter = "models.Hero";
  }

  @Override
  public void levelUp() {
    level++;
    maxHealthPoint += diceRoll();
    defendPoint += diceRoll();
    strikePoint += diceRoll();
  }

  public boolean gotKey() {
    return gotKey;
  }

  public void setGotKey(boolean gotKey) {
    this.gotKey = gotKey;
  }
}
