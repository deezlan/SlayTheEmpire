package main;

import entity.*;
import mobs.*;
import object.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;
        // MAP 0 SETTER
        {
            gp.objArr[mapNum][i] = new OBJ_Shop(gp, 516, -10); i++;
            gp.objArr[mapNum][i] = new OBJ_SavePedestal(gp, 272, 490); i++;

            // CHAT BUBBLES - Blacksmith, Maiden, Shady, Merchant
            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp, 134, 108); i++;
            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp, 410, 230); i++;
            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp, 540, 410); i++;
            gp.objArr[mapNum][i] = new OBJ_ChatBubble(gp, 640, 80); i++;

            gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 12, 8);
        }

        i = 0;
        mapNum = 1;
        // MAP 1 SETTER
        {
            // TELEPORTING OBELISK
            gp.objArr[mapNum][i] = new OBJ_Obelisk(gp, 63, 168); i++;
            // FIRST ROOM GATES index (1-5)
            {
                // ENTRANCES
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 6, 41); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 6, 42); i++;
                // EXITS
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 11, 37); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 12, 37); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 13, 37); i++;
            }
            // SPLIT PATHWAY GATES index (6-7) (8-9)
            {
                // LEFT PATH
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 10, 30); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 10, 31); i++;
                // RIGHT PATH
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 14, 30); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 14, 31); i++;
            }
            // MINI-BOSS ROOM GATES index (10-16)
            {
                // ENTRANCES
                // LEFT SIDE
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 6, 23); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 6, 24); i++;
                // RIGHT SIDE
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 18, 23); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 18, 24); i++;
                // EXITS
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 11, 19); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 12, 19); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 13, 19); i++;
            }
            // FINAL BOSS ROOM GATES index (17-20)
            {
                // ENTRANCES
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 11, 14); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 12, 14); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 13, 14); i++;
                // EXIT
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 0, 5, 7);
            }
        }

        i = 0;
        mapNum = 2;
        // MAP 2 SETTER
        {
            // TELEPORTING OBELISK
            gp.objArr[mapNum][i] = new OBJ_Obelisk(gp, 1551, 650); i++;
            // FIRST ROOM GATES index (1-4)
            {
                // ENTRANCES
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 26, 36); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 26, 37); i++;
                // EXITS
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 20, 32); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 21, 32); i++;
            }
            // MINI-BOSS ROOM GATES index (5-9)
            {
                // ENTRANCES
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 13, 24); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 13, 25); i++;
                // EXITS
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 6, 19); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 7, 19); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 8, 19); i++;
            }
            // FINAL BOSS ROOM GATES index (10-15)
            {
                // ENTRANCES
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 6, 11); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 7, 11); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 8, 11); i++;
                // EXIT
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 14, 5); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 14, 6); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 14, 7); i++;
            }
            // PLOT TWIST BOSS ROOM GATES index (16-21)
            {
                // ENTRANCES
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 25, 5); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 25, 6); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 25, 7); i++;
                // EXIT
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 32, 11); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 33, 11); i++;
                gp.objArr[mapNum][i] = new OBJ_Gate(gp, 1, 34, 11);
            }
        }
    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;

            // MAP 0
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
        mapNum = 1;
        gp.mobArr[mapNum][i] = new MOB_RobotGuardian(gp);
        gp.mobArr[mapNum][i].worldX = 350;
        gp.mobArr[mapNum][i].worldY = 1968;
        i++;

        gp.mobArr[mapNum][i] = new MOB_SkeletonKnight(gp);
        gp.mobArr[mapNum][i].worldX = 550;
        gp.mobArr[mapNum][i].worldY = 1968;
        i++;

        gp.mobArr[mapNum][i] = new MOB_FrostGiant(gp);
        gp.mobArr[mapNum][i].worldX = 400;
        gp.mobArr[mapNum][i].worldY = 180;

//
//                gp.mobArr[mapNum][i] = new MOB_ArmoredGuardian(gp);
//                gp.mobArr[mapNum][i].worldX = 350;
//                gp.mobArr[mapNum][i].worldY = 270;
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
//
//                gp.mobArr[mapNum][i] = new MOB_Canine(gp);
//                gp.mobArr[mapNum][i].worldX = 500;
//                gp.mobArr[mapNum][i].worldY = 300;
//                i++;
//
//                gp.mobArr[mapNum][i] = new MOB_Slime(gp);
//                gp.mobArr[mapNum][i].worldX = 800;
//                gp.mobArr[mapNum][i].worldY = 400;
//                i++;
//
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

//                 MAP 2
//                mapNum = 2;
//                i = 0;
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
