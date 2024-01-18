public class Hero extends Character {
    protected boolean gotKey;

    public Hero() {
        level = 1;
        maxHealthPoint = 20 + (3 * diceRoll());
        currentHealthPoint = maxHealthPoint;
        defendPoint = 2 * diceRoll();
        strikePoint = 5 + diceRoll();
        typeOfCharacter = "Hero";
    }

    public void levelUp() {
        level++;
        maxHealthPoint += diceRoll();
        defendPoint += diceRoll();
        strikePoint += diceRoll();
    }
}