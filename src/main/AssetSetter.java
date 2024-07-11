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
            gp.objArr[mapNum][i].worldX = 516;
            gp.objArr[mapNum][i].worldY = -10;
            i++;

            gp.objArr[mapNum][i] = new OBJ_SavePedestal(gp);
            gp.objArr[mapNum][i].worldX = 272;
            gp.objArr[mapNum][i].worldY = 490 ;
            i++;

            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp); // Maiden chat bubble
            gp.objArr[mapNum][i].worldX = 410;
            gp.objArr[mapNum][i].worldY = 230;
            i++;

            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp); // Blacksmith chat bubble
            gp.objArr[mapNum][i].worldX = 134;
            gp.objArr[mapNum][i].worldY = 108;
            i++;

            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp); // Merchant chat bubble
            gp.objArr[mapNum][i].worldX = 640;
            gp.objArr[mapNum][i].worldY = 80;
            i++;

            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp); // Mystery chat bubble
            gp.objArr[mapNum][i].worldX = 540;
            gp.objArr[mapNum][i].worldY = 410;
            // MAP 1
    }
    public void setNPC() {
        int mapNum = 0;
        int i = 0;

//            // MAP 0
            gp.npcArr[mapNum][i] = new NPC_Maiden(gp);
            gp.npcArr[mapNum][i].worldX = 368;
            gp.npcArr[mapNum][i].worldY = 250;
            System.out.println("NPC Maiden set at (" + gp.npcArr[mapNum][0].worldX + ", " + gp.npcArr[mapNum][0].worldY + ")");
            i++;

            gp.npcArr[mapNum][i] = new NPC_Blacksmith(gp);
            gp.npcArr[mapNum][i].worldX = 85;
            gp.npcArr[mapNum][i].worldY = 128;
            System.out.println("NPC Blacksmith set at (" + gp.npcArr[mapNum][0].worldX + ", " + gp.npcArr[mapNum][0].worldY + ")");
            i++;

            gp.npcArr[mapNum][i] = new NPC_Merchant(gp);
            gp.npcArr[mapNum][i].worldX = 600;
            gp.npcArr[mapNum][i].worldY = 100;
            System.out.println("NPC Merchant set at (" + gp.npcArr[mapNum][0].worldX + ", " + gp.npcArr[mapNum][0].worldY + ")");
            i++;

            gp.npcArr[mapNum][i] = new NPC_Mystery(gp);
            gp.npcArr[mapNum][i].worldX = 500;
            gp.npcArr[mapNum][i].worldY = 440;
            System.out.println("NPC Mystery set at (" + gp.npcArr[mapNum][0].worldX + ", " + gp.npcArr[mapNum][0].worldY + ")");
            // MAP 1
    }
    public void setMonster() {
        int mapNum;
        int i = 0;
                // MAP 1
                mapNum = 0;
//                gp.mobArr[mapNum][i] = new MOB_SkeletonKnight(gp);
//                gp.mobArr[mapNum][i].worldX = 350;
//                gp.mobArr[mapNum][i].worldY = 270;
//                i++;

                gp.mobArr[mapNum][i] = new MOB_ArmoredGuardian(gp);
                gp.mobArr[mapNum][i].worldX = 350;
                gp.mobArr[mapNum][i].worldY = 270;
//                i++;
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

                gp.mobArr[mapNum][i] = new MOB_RobotGuardian(gp);
                gp.mobArr[mapNum][i].worldX = 400;
                gp.mobArr[mapNum][i].worldY = 270;
                    i++;

                // MAP 2
//                mapNum = 2;
//                gp.mobArr[mapNum][i] = new MOB_Slime(gp);
//                gp.mobArr[mapNum][i].worldX = 450;
//                gp.mobArr[mapNum][i].worldY = 270;
//                i++;
//
//                gp.mobArr[mapNum][i] = new MOB_RobotGuardian(gp);
//                gp.mobArr[mapNum][i].worldX = 300;
//                gp.mobArr[mapNum][i].worldY = 270;
//                i++;
//
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
