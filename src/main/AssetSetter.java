package main;

import entity.NPCMaiden;
import object.OBJ_Gun_SnowBallCannon;
import object.OBJ_SavePedestal;
import object.OBJ_Shop;

public class AssetSetter {
    GamePanel gp;

//    MouseHandler mouseH;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.objArr[0] = new OBJ_Shop(gp);
        gp.objArr[0].worldX = 106;
        gp.objArr[0].worldY = 192;

        gp.objArr[1] = new OBJ_SavePedestal();
        gp.objArr[1].worldX = 50;
        gp.objArr[1].worldY = 50;


        gp.projectileArr[0] = new OBJ_Gun_SnowBallCannon(gp);
        gp.projectileArr[0].worldX = gp.projectile.proX;
        gp.projectileArr[0].worldY = gp.projectile.proY;

    }

    public void setNPC() {
        gp.npcArr[0] = new NPCMaiden(gp);
        gp.npcArr[0].worldX = 370;
        gp.npcArr[0].worldY = 240;
    }
}
