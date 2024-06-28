package main;

import entity.NPCMaiden;
import object.OBJ_SavePedestal;
import object.OBJ_Shop;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.objArr[0] = new OBJ_Shop();
        gp.objArr[0].worldX = 570;
        gp.objArr[0].worldY = 0;

        gp.objArr[1] = new OBJ_SavePedestal();
        gp.objArr[1].worldX = 300;
        gp.objArr[1].worldY = 500;
    }

    public void setNPC() {
        gp.npcArr[0] = new NPCMaiden(gp);
        gp.npcArr[0].worldX = 270;
        gp.npcArr[0].worldY = 10;
        System.out.println("NPC Maiden set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");
    }
}
