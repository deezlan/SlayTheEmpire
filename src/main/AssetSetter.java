package main;

import entity.*;
import mobs.*;
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

            // MAP 0
            gp.objArr[mapNum][i] = new OBJ_Shop(gp);
            gp.objArr[mapNum][i].worldX = 840;
            gp.objArr[mapNum][i].worldY = 100;
            i++;

            gp.objArr[mapNum][i] = new OBJ_SavePedestal(gp);
            gp.objArr[mapNum][i].worldX = 562;
            gp.objArr[mapNum][i].worldY = 592;
            i++;

            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp); // Maiden chat bubble
            gp.objArr[mapNum][i].worldX = 700;
            gp.objArr[mapNum][i].worldY = 328;
            i++;

            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp); // Blacksmith chat bubble
            gp.objArr[mapNum][i].worldX = 430;
            gp.objArr[mapNum][i].worldY = 100;
            i++;

            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp); // Merchant chat bubble
            gp.objArr[mapNum][i].worldX = 970;
            gp.objArr[mapNum][i].worldY = 160;
            i++;

            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp); // Mystery chat bubble
            gp.objArr[mapNum][i].worldX = 845;
            gp.objArr[mapNum][i].worldY = 520;

            // MAP 1
    }
    public void setNPC() {
        int mapNum = 0;
        int i = 0;

            // MAP 0
            gp.npcArr[mapNum][i] = new NPC_Maiden(gp);
            gp.npcArr[mapNum][i].worldX = 655;
            gp.npcArr[mapNum][i].worldY = 340;
            System.out.println("NPC Maiden set at (" + gp.npcArr[mapNum][0].worldX + ", " + gp.npcArr[mapNum][0].worldY + ")");
            i++;

            gp.npcArr[mapNum][i] = new NPC_Blacksmith(gp);
            gp.npcArr[mapNum][i].worldX = 380;
            gp.npcArr[mapNum][i].worldY = 120;
            System.out.println("NPC Blacksmith set at (" + gp.npcArr[mapNum][0].worldX + ", " + gp.npcArr[mapNum][0].worldY + ")");
            i++;

            gp.npcArr[mapNum][i] = new NPC_Merchant(gp);
            gp.npcArr[mapNum][i].worldX = 920;
            gp.npcArr[mapNum][i].worldY = 180;
            System.out.println("NPC Merchant set at (" + gp.npcArr[mapNum][0].worldX + ", " + gp.npcArr[mapNum][0].worldY + ")");
            i++;

            gp.npcArr[mapNum][i] = new NPC_Mystery(gp);
            gp.npcArr[mapNum][i].worldX = 800;
            gp.npcArr[mapNum][i].worldY = 540;
            System.out.println("NPC Mystery set at (" + gp.npcArr[mapNum][0].worldX + ", " + gp.npcArr[mapNum][0].worldY + ")");
            // MAP 1
    }
    public void setMonster() {
        int mapNum = 0;
        int i = 0;
                // MAP 0

                gp.mobArr[mapNum][i] = new MOB_ArmoredGuardian(gp);
                gp.mobArr[mapNum][i].worldX = 350;
                gp.mobArr[mapNum][i].worldY = 270;
                i++;
//
//                gp.mobArr[mapNum][i] = new MOB_FlyingEye(gp);
//                gp.mobArr[mapNum][i].worldX = 400;
//                gp.mobArr[mapNum][i].worldY = 270;
//                i++;
//
//                gp.mobArr[mapNum][i] = new MOB_Mushroom(gp);
//                gp.mobArr[mapNum][i].worldX = 450;
//                gp.mobArr[mapNum][i].worldY = 270;
//                i++;

//                gp.mobArr[mapNum][i] = new MOB_Canine(gp);
//                gp.mobArr[mapNum][i].worldX = 500;
//                gp.mobArr[mapNum][i].worldY = 300;
//                i++;

//                gp.mobArr[mapNum][i] = new MOB_Slime(gp);
//                gp.mobArr[mapNum][i].worldX = 800;
//                gp.mobArr[mapNum][i].worldY = 400;
//                i++;

//                gp.mobArr[mapNum][i] = new MOB_Goblin(gp);
//                gp.mobArr[mapNum][i].worldX = 400;
//                gp.mobArr[mapNum][i].worldY = 300;
//                i++;
//
//                gp.mobArr[mapNum][i] = new MOB_Skellington(gp);
//                gp.mobArr[mapNum][i].worldX = 700;
//                gp.mobArr[mapNum][i].worldY = 300;
//                i++;
//
//                gp.mobArr[mapNum][i] = new MOB_Ramses(gp);
//                gp.mobArr[mapNum][i].worldX = 700;
//                gp.mobArr[mapNum][i].worldY = 400;
//                i++;

//                gp.mobArr[mapNum][i] = new MOB_CrystalGuardian(gp);
//                gp.mobArr[mapNum][i].worldX = 600;
//                gp.mobArr[mapNum][i].worldY = 500;
//                i++;

                // MAP 1
//                mapNum = 1;
//                gp.mobArr[mapNum][i] = new MOB_Slime(gp);
//                gp.mobArr[mapNum][i].worldX = 500;
//                gp.mobArr[mapNum][i].worldY = 270;

//                gp.mobArr[mapNum][i] = new MOB_RobotGuardian(gp);
//                gp.mobArr[mapNum][i].worldX = 300;
//                gp.mobArr[mapNum][i].worldY = 270;
//                i++;

//                gp.mobArr[mapNum][i] = new MOB_ArmoredGuardian(gp);
//                gp.mobArr[mapNum][i].worldX = 350;
//                gp.mobArr[mapNum][i].worldY = 270;
//                i++;
        }

    public void setInteractiveTile() {
//        int mapNum = 0;
//        int i = 0;
//        gp.iTile[i] = new
    }
}
