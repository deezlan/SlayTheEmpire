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
        gp.objArray[0] = new OBJ_Shop();
        gp.objArray[0].worldX = 570;
        gp.objArray[0].worldY = 0;

        gp.objArray[1] = new OBJ_SavePedestal();
        gp.objArray[1].worldX = 300;
        gp.objArray[1].worldY = 500;
    }

    public void setNPC() {
        gp.npc[0] = new NPCMaiden(gp);
        gp.npc[0].worldX = 300;
        gp.npc[0].worldY = 10;
        System.out.println("NPC Maiden set at (" + gp.npc[0].worldX + ", " + gp.npc[0].worldY + ")");
    }
}
