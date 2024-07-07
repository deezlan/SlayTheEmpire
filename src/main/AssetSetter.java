package main;

import entity.NPC_Maiden;
import entity.NPC_Merchant;
import entity.NPC_Mystery;
import entity.NPC_Blacksmith;
import mobs.MOB_Slime;
import object.OBJ_ChatBubble;
import object.OBJ_SavePedestal;
import object.OBJ_Shop;

public class  AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        switch (gp.gameArea) {
            case 0:
                gp.objArr[0] = new OBJ_Shop(gp);
                gp.objArr[0].worldX = 540;
                gp.objArr[0].worldY = -20;

                gp.objArr[1] = new OBJ_SavePedestal(gp);
                gp.objArr[1].worldX = 272;
                gp.objArr[1].worldY = 494;

                gp.objArr[3] = new OBJ_ChatBubble(gp); // Maiden chat bubble
                gp.objArr[3].worldX = 410;
                gp.objArr[3].worldY = 220;

                gp.objArr[4] = new OBJ_ChatBubble(gp); // Blacksmith chat bubble
                gp.objArr[4].worldX = 140;
                gp.objArr[4].worldY = 60;

                gp.objArr[5] = new OBJ_ChatBubble(gp); // Merchant chat bubble
                gp.objArr[5].worldX = 685;
                gp.objArr[5].worldY = 80;

                gp.objArr[6] = new OBJ_ChatBubble(gp); // Mystery chat bubble
                gp.objArr[6].worldX = 540;
                gp.objArr[6].worldY = 418;

//                gp.objArr[7] = new OBJ_Obelisk(gp);
//                gp.objArr[7].worldX = 200;
//                gp.objArr[7].worldY = 200;

                break;
            case 1, 2:
                break;
        }
    }

    public void setNPC() {
        switch (gp.gameArea) {
            case 0:
                gp.npcArr[0] = new NPC_Maiden(gp);
                gp.npcArr[0].worldX = 370;
                gp.npcArr[0].worldY = 240;
                System.out.println("NPC Maiden set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");

                gp.npcArr[1] = new NPC_Blacksmith(gp);
                gp.npcArr[1].worldX = 90;
                gp.npcArr[1].worldY = 80;
                System.out.println("NPC Blacksmith set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");

                gp.npcArr[2] = new NPC_Merchant(gp);
                gp.npcArr[2].worldX = 635;
                gp.npcArr[2].worldY = 100;
                System.out.println("NPC Merchant set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");

                gp.npcArr[3] = new NPC_Mystery(gp);
                gp.npcArr[3].worldX = 500;
                gp.npcArr[3].worldY = 448;
                System.out.println("NPC Mystery set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");
                break;
            case 1:
                System.out.println("Case 1 AssetSetter");
                break;
            default:
                System.out.println("Default case AssetSetter NPC");
        }
    }

    public void setClasses() {

    }

    public void setMonster() {
        switch(gp.gameArea) {
            case 0:
                gp.mobArr[0] = new MOB_Slime(gp);
                gp.mobArr[0].worldX = 200;
                gp.mobArr[0].worldY = 200;
                break;
            case 1:
//                gp.mobArr[0] = new MOB_Slime(gp);
//                gp.mobArr[0].worldX = 270;
//                gp.mobArr[0].worldY = 270;
                break;
            default:
                System.out.println("Case Default AssetSetter Monster");
        }
    }
}
