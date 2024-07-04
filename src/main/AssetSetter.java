package main;

import entity.NPC_Maiden;
import entity.NPC_Merchant;
import entity.NPC_Mystery;
import entity.NPC_Blacksmith;
import mobs.MOB_Slime;
import object.OBJ_ChatBubble;
import object.OBJ_SavePedestal;
import object.OBJ_Shop;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;
        switch (mapNum) {
            case 0:
            gp.objArr[mapNum][i] = new OBJ_Shop(gp);
            gp.objArr[mapNum][i].worldX = 540;
            gp.objArr[mapNum][i].worldY = -20;
            i++;

            gp.objArr[mapNum][i] = new OBJ_SavePedestal(gp);
            gp.objArr[mapNum][i].worldX = 272;
            gp.objArr[mapNum][i].worldY = 494;
            i++;

            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp); // Maiden chat bubble
            gp.objArr[mapNum][i].worldX = 410;
            gp.objArr[mapNum][i].worldY = 220;
            i++;

            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp); // Blacksmith chat bubble
            gp.objArr[mapNum][i].worldX = 140;
            gp.objArr[mapNum][i].worldY = 60;
            i++;

            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp); // Merchant chat bubble
            gp.objArr[mapNum][i].worldX = 685;
            gp.objArr[mapNum][i].worldY = 80;
            i++;

            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp); // Mystery chat bubble
            gp.objArr[mapNum][i].worldX = 540;
            gp.objArr[mapNum][i].worldY = 418;
            i++;
            break;
            case 1:
        }
    }
    public void setNPC() {
        int mapNum = 0;
        int i = 0;
        switch (mapNum) {
            case 0:
            gp.npcArr[mapNum][i] = new NPC_Maiden(gp);
            gp.npcArr[mapNum][i].worldX = 370;
            gp.npcArr[mapNum][i].worldY = 240;
            System.out.println("NPC Maiden set at (" + gp.npcArr[mapNum][0].worldX + ", " + gp.npcArr[mapNum][0].worldY + ")");
            i++;

            gp.npcArr[mapNum][i] = new NPC_Blacksmith(gp);
            gp.npcArr[mapNum][i].worldX = 90;
            gp.npcArr[mapNum][i].worldY = 80;
            System.out.println("NPC Blacksmith set at (" + gp.npcArr[mapNum][0].worldX + ", " + gp.npcArr[mapNum][0].worldY + ")");
            i++;

            gp.npcArr[mapNum][i] = new NPC_Merchant(gp);
            gp.npcArr[mapNum][i].worldX = 635;
            gp.npcArr[mapNum][i].worldY = 100;
            System.out.println("NPC Merchant set at (" + gp.npcArr[mapNum][0].worldX + ", " + gp.npcArr[mapNum][0].worldY + ")");
            i++;

            gp.npcArr[mapNum][i] = new NPC_Mystery(gp);
            gp.npcArr[mapNum][i].worldX = 500;
            gp.npcArr[mapNum][i].worldY = 448;
            System.out.println("NPC Mystery set at (" + gp.npcArr[mapNum][0].worldX + ", " + gp.npcArr[mapNum][0].worldY + ")");
            i++;
            break;
            case 1:
        }
    }
    public void setMonster() {
        int mapNum = 0;
        int i = 0;
        switch(mapNum) {
            case 0:
                gp.mobArr[mapNum][i] = new MOB_Slime(gp);
                gp.mobArr[mapNum][i].worldX = 200;
                gp.mobArr[mapNum][i].worldY = 200;
                i++;
                break;
            case 1:
                gp.mobArr[mapNum][i] = new MOB_Slime(gp);
                gp.mobArr[mapNum][i].worldX = 240;
                gp.mobArr[mapNum][i].worldY = 270;
                i++;
                break;
            default:
                System.out.println("Case Default AssetSetter Monster");
        }
    }

    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;
//        gp.iTile[i] = new
    }
}
