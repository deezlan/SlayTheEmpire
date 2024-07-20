package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Gate extends Entity {
    int gateForm;

    public OBJ_Gate (GamePanel gp, int gateForm, int col, int row) {
        super(gp, col * gp.TILE_SIZE, row * gp.TILE_SIZE);
        this.gp = gp;
        this.gateForm = gateForm;
        type = type_gate;
        setCollisionValues(0, 0, 48, 48);


        // Load save pedestal sprites
        getObjectSprites();
    }

    @Override
    public void runLockAnimation() {
        collision = true;
        spriteCounter++;
        if (spriteNum < currentList.size() && spriteCounter%5 == 0) {
            spriteNum++;
        }
        if (spriteNum >= currentList.size() - 1) {
            spriteNum = 0;
            spriteCounter = 0;
            locking = false;
            locked = true;
            unlocking = false;
            interacting = false;
        }
    }

    @Override
    public void runUnlockingAnimation() {
        interactSpriteCounter++;
        if (interactSpriteNum < interactList.size() && interactSpriteCounter%5 == 0) {
            interactSpriteNum++;
        }
        if (interactSpriteNum >= interactList.size() - 1) {
            interactSpriteNum = 0;
            interactSpriteCounter = 0;
            unlocking = false;
            locked = false;
            locking = false;
            interacting = false;
            collision = false;
        }
    }

    public void getObjectSprites() {
        String dir;
        if (gateForm == 0) { dir = "/objects/gate/brick/"; }
            else { dir = "/objects/gate/fence/"; }

        try {
            if (gateForm == 0) {
                // Load sprites
                for (int i = 0; i <= 6; i++)
                    defaultList.add(i, UtilityTool.loadSprite(dir + i + ".png", "Missing brick gate " + i));

                for (int i = 6; i >= 0; i--)
                    interactList.add(UtilityTool.loadSprite(dir + i + ".png", "Missing brick gate " + i));
            } else {
                for (int i = 0; i <= 7; i++)
                    defaultList.add(i, UtilityTool.loadSprite(dir + i + ".png", "Missing fence gate " + i));

                for (int i = 7; i >= 0; i--)
                    interactList.add(UtilityTool.loadSprite(dir + i + ".png", "Missing brick gate " + i));
            }

            // Scale sprites up
            UtilityTool.scaleEntityList(this, defaultList, 48, 48);
            UtilityTool.scaleEntityList(this, interactList, 48, 48);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
