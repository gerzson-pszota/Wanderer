package models;

public class Monster extends Character {
    public Monster() {
        level = 1;
        maxHealthPoint = 2 * level * diceRoll();
        currentHealthPoint = maxHealthPoint;
        defendPoint = level / 2 * diceRoll();
        strikePoint = level * diceRoll();
        typeOfCharacter = "models.Monster";
    }
}