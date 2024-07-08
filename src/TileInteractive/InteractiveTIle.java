package TileInteractive;


import entity.Entity;
import main.GamePanel;

public class InteractiveTIle extends Entity {
    GamePanel gp;
    public boolean destructible = false;
    public InteractiveTIle(GamePanel gp) {
        super(gp);
        this.gp = gp;
    }

    public void update(){

    }
}
