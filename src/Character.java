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

    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }
}

