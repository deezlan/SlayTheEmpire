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
        switch (gp.gameArea) {
            case 0:
                gp.objArr[0] = new OBJ_Shop();
                gp.objArr[0].worldX = gp.TILE_SIZE*9;
                gp.objArr[0].worldY = gp.TILE_SIZE/2;

                gp.objArr[1] = new OBJ_SavePedestal();
                gp.objArr[1].worldX = gp.TILE_SIZE*5 + 30;
                gp.objArr[1].worldY = gp.TILE_SIZE*9 + 12;
                break;
            case 1:
                break;
            default:
                gp.objArr[0] = new OBJ_Shop();
                gp.objArr[0].worldX = 570;
                gp.objArr[0].worldY = 0;

                gp.objArr[1] = new OBJ_SavePedestal();
                gp.objArr[1].worldX = 300;
                gp.objArr[1].worldY = 500;
        }
    }

    public void setNPC() {
        switch (gp.gameArea) {
            case 0:
                gp.npcArr[0] = new NPCMaiden(gp);
                gp.npcArr[0].worldX = gp.TILE_SIZE*5;
                gp.npcArr[0].worldY = gp.TILE_SIZE;
                System.out.println("NPC Maiden set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");
                break;
            case 1:
                break;
            default:
                gp.npcArr[0] = new NPCMaiden(gp);
                gp.npcArr[0].worldX = 300;
                gp.npcArr[0].worldY = 10;
                System.out.println("NPC Maiden set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");
        }
    }
}
