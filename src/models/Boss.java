package models;

public class Boss extends Character {
    public Boss() {
        level = 1;
        maxHealthPoint = 2 * level * diceRoll() + diceRoll();
        currentHealthPoint = maxHealthPoint;
        defendPoint = level / 2 * diceRoll() + diceRoll() / 2;
        strikePoint = level * diceRoll() + level;
        typeOfCharacter = "models.Boss";
    }
}
