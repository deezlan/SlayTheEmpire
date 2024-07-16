package main;

import entity.*;
import mobs.*;
import object.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void loadAssets() {
        setNPC();
        setMonster();
        setObject();
        setGates();
    }

    public void setObject() {
        int i = 0;

        switch (gp.currentMap) {
            case 0:
                // SHOP & SAVE PEDESTAL
                gp.objArr[gp.currentMap][i] = new OBJ_Shop(gp, 516, -10); i++;
                gp.objArr[gp.currentMap][i] = new OBJ_SavePedestal(gp, 272, 490); i++;

                // CHAT BUBBLES - Blacksmith, Maiden, Shady, Merchant
                gp.objArr[gp.currentMap][i] = new OBJ_ChatBubble(gp, 134, 108); i++;
                gp.objArr[gp.currentMap][i] = new OBJ_ChatBubble(gp, 410, 230); i++;
                gp.objArr[gp.currentMap][i] = new OBJ_ChatBubble(gp, 540, 410); i++;
                gp.objArr[gp.currentMap][i] = new OBJ_ChatBubble(gp, 650, 80);

                // TEST GATE - DELETE AFTER
                gp.gateArr[gp.currentMap][0] = new OBJ_Gate(gp, 1, 12, 8);
                break;
            case 1:
                // TELEPORTING OBELISK
                gp.objArr[gp.currentMap][i] = new OBJ_Obelisk(gp, 63, 168);
                break;
            case 2:
                // TELEPORTING OBELISK
                gp.objArr[gp.currentMap][i] = new OBJ_Obelisk(gp, 1551, 650);
        }
    }

    public void setGates() {
        int i = 0;

        switch (gp.currentMap) {
            case 0:
                System.out.println("Loading 0 gates for lobby");
                break;
            case 1:
            {
                // FIRST ROOM GATES index (0-4)
                {
                    // ENTRANCES
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 6, 41); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 6, 42); i++;
                    // EXITS
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 11, 37); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 12, 37); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 13, 37); i++;
                }
                // SPLIT PATHWAY GATES index (5-6) (7-8)
                {
                    // LEFT PATH
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 10, 30); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 10, 31); i++;
                    // RIGHT PATH
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 14, 30); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 14, 31); i++;
                }
                // MINI-BOSS ROOM GATES index (9-15)
                {
                    // ENTRANCES
                    // LEFT SIDE
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 6, 23); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 6, 24); i++;
                    // RIGHT SIDE
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 18, 23); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 18, 24); i++;
                    // EXITS
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 11, 19); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 12, 19); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 13, 19); i++;
                }
                // FINAL BOSS ROOM GATES index (16-19)
                {
                    // ENTRANCES
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 11, 14); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 12, 14); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 13, 14); i++;
                    // EXIT
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 5, 7);
                }
            }
                break;
            case 2:
            {
                // FIRST ROOM GATES index (0-3)
                {
                    // ENTRANCES
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 26, 36); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 26, 37); i++;
                    // EXITS
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 20, 32); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 21, 32); i++;
                }
                // MINI-BOSS ROOM GATES index (4-8)
                {
                    // ENTRANCES
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 13, 24); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 13, 25); i++;
                    // EXITS
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 6, 19); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 7, 19); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 8, 19); i++;
                }
                // FINAL BOSS ROOM GATES index (9-14)
                {
                    // ENTRANCES
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 6, 11); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 7, 11); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 8, 11); i++;
                    // EXIT
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 14, 5); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 14, 6); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 14, 7); i++;
                }
                // PLOT TWIST BOSS ROOM GATES index (15-20)
                {
                    // ENTRANCES
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 25, 5); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 25, 6); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 25, 7); i++;
                    // EXIT
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 32, 11); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 33, 11); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 1, 34, 11);
                }
            }
        }
    }

    public void setNPC() {
        int i = 0;

        switch (gp.currentMap) {
            case 0:
            {
                gp.npcArr[gp.currentMap][i] = new NPC_Maiden(gp, 368, 250); i++;
                System.out.println("NPC Maiden set at (" + gp.npcArr[gp.currentMap][0].worldX + ", " + gp.npcArr[gp.currentMap][0].worldY + ")");

                gp.npcArr[gp.currentMap][i] = new NPC_Blacksmith(gp, 85, 128); i++;
                System.out.println("NPC Blacksmith set at (" + gp.npcArr[gp.currentMap][0].worldX + ", " + gp.npcArr[gp.currentMap][0].worldY + ")");

                gp.npcArr[gp.currentMap][i] = new NPC_Merchant(gp, 600, 100); i++;
                System.out.println("NPC Merchant set at (" + gp.npcArr[gp.currentMap][0].worldX + ", " + gp.npcArr[gp.currentMap][0].worldY + ")");

                gp.npcArr[gp.currentMap][i] = new NPC_Mystery(gp, 500, 440);
                System.out.println("NPC Mystery set at (" + gp.npcArr[gp.currentMap][0].worldX + ", " + gp.npcArr[gp.currentMap][0].worldY + ")");
            }
                break;
            case 2:
        }
    }

    public void setMonster() {
        int i = 0;

        switch (gp.currentMap) {
            case 1:
            {
                gp.mobArr[gp.currentMap][i] = new BOSS_FrostGiant(gp, 400, 180);
                gp.mobArr[gp.currentMap][i] = new MOB_RobotGuardian(gp, 350, 1968); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_SkeletonKnight(gp, 550, 1968); i++;
                gp.mobArr[gp.currentMap][i] = new BOSS_FrostGiant(gp, 400, 180); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_FlyingEye(gp, 350, 1968); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Mushroom(gp, 450, 270); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Canine(gp, 500, 300); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Slime(gp, 800, 400); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Goblin(gp, 400, 300); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Skellington(gp, 700, 300); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Ramses(gp, 700, 400);
            }
                break;
            case 2:
            {
                gp.mobArr[gp.currentMap][i] = new MOB_Canine(gp, 1600, 1968); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Skellington(gp, 1700, 1968);
            }
        }
//        // MAP 1
//        gp.currentMap = 1;
//        gp.mobArr[gp.currentMap][i] = new BOSS_FrostGiant(gp, 400, 180);
//        gp.mobArr[gp.currentMap][i] = new MOB_RobotGuardian(gp, 350, 1968); i++;
//        gp.mobArr[gp.currentMap][i] = new MOB_SkeletonKnight(gp, 550, 1968); i++;
//        gp.mobArr[gp.currentMap][i] = new BOSS_FrostGiant(gp, 400, 180); i++;
//        gp.mobArr[gp.currentMap][i] = new MOB_FlyingEye(gp, 350, 1968); i++;
//        gp.mobArr[gp.currentMap][i] = new MOB_Mushroom(gp, 450, 270); i++;
//        gp.mobArr[gp.currentMap][i] = new MOB_Canine(gp, 500, 300); i++;
//        gp.mobArr[gp.currentMap][i] = new MOB_Slime(gp, 800, 400); i++;
//        gp.mobArr[gp.currentMap][i] = new MOB_Goblin(gp, 400, 300); i++;
//        gp.mobArr[gp.currentMap][i] = new MOB_Skellington(gp, 700, 300); i++;
//        gp.mobArr[gp.currentMap][i] = new MOB_Ramses(gp, 700, 400);
//        i = 0;
//
//        // MAP 2
//        gp.currentMap = 2;
//        gp.mobArr[gp.currentMap][i] = new MOB_Slime(gp, 1600, 1968); i++;
//        gp.mobArr[gp.currentMap][i] = new MOB_RobotGuardian(gp, 1700, 1968); i++;
    }
}
