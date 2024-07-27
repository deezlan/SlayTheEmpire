package main;

import entity.*;
import mobs.*;
import object.*;

import java.io.IOException;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void loadAssets() {
        setGates();
        setObject();
        setNPC();
        setMonster();
    }

    public void setObject() {
        int i = 0;

        switch (gp.currentMap) {
            case 0:
                // SHOP & SAVE PEDESTAL
                try {
                    gp.objArr[gp.currentMap][i] = new OBJ_Shop(gp, 516, -10); i++;
                }catch (IOException e){
                    e.printStackTrace(System.out);
                }
                gp.objArr[gp.currentMap][i] = new OBJ_SavePedestal(gp, 272, 490); i++;

                // CHAT BUBBLES - Blacksmith, Maiden, Shady, Merchant
                gp.objArr[gp.currentMap][i] = new OBJ_ChatBubble(gp, 134, 108); i++;
                gp.objArr[gp.currentMap][i] = new OBJ_ChatBubble(gp, 410, 230); i++;
                gp.objArr[gp.currentMap][i] = new OBJ_ChatBubble(gp, 540, 410); i++;
                gp.objArr[gp.currentMap][i] = new OBJ_ChatBubble(gp, 650, 80);
                break;
            case 1:
                // TELEPORTING OBELISK
                gp.objArr[gp.currentMap][i] = new OBJ_Obelisk(gp, 63 + gp.TILE_SIZE*17, 168); i++;

                // PICK UP CHOICES
                gp.objArr[gp.currentMap][i] = new OBJ_PickUpCoin(gp, gp.TILE_SIZE * 20 + 24, gp.TILE_SIZE * 26 + 20, 100); i++;
                gp.objArr[gp.currentMap][i] = new OBJ_PickUpHealth(gp, gp.TILE_SIZE * 37 + 24, gp.TILE_SIZE * 26 + 20);
                break;
            case 2:
                // TELEPORTING OBELISK
                gp.objArr[gp.currentMap][i] = new OBJ_Obelisk(gp, 1551, 650); i++;

                // SECRET COINS
                gp.objArr[gp.currentMap][i] = new OBJ_PickUpCoin(gp, gp.TILE_SIZE * 30 + 24, gp.TILE_SIZE * 25 + 20, 100);
        }
    }

    public void setGates() {
        int i = 0;

        switch (gp.currentMap) {
            case 1:
            {
                // FIRST ROOM GATES index (0-4)
                {
                    // ENTRANCES
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 23, 41); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 23, 42); i++;
                    // EXITS
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 28, 37); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 29, 37); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 30, 37); i++;
                }
                // SPLIT PATHWAY GATES index (5-6) (7-8)
                {
                    // LEFT PATH
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 27, 30); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 27, 31); i++;
                    // RIGHT PATH
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 31, 30); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 31, 31); i++;
                }
                // MINI-BOSS ROOM GATES index (9-15)
                {
                    // ENTRANCES
                    // LEFT SIDE
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 23, 23); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 23, 24); i++;
                    // RIGHT SIDE
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 35, 23); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 35, 24); i++;
                    // EXITS
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 28, 19); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 29, 19); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 30, 19); i++;
                }
                // FINAL BOSS ROOM GATES index (16-19)
                {
                    // ENTRANCES
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 28, 14); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 29, 14); i++;
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 30, 14); i++;
                    // EXIT
                    gp.gateArr[gp.currentMap][i] = new OBJ_Gate(gp, 0, 22, 7);
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
                System.out.println("NPC Maiden set at (" + gp.npcArr[gp.currentMap][i-1].worldX + ", " + gp.npcArr[gp.currentMap][0].worldY + ")");

                gp.npcArr[gp.currentMap][i] = new NPC_Blacksmith(gp, 85, 128); i++;
                System.out.println("NPC Blacksmith set at (" + gp.npcArr[gp.currentMap][i-1].worldX + ", " + gp.npcArr[gp.currentMap][0].worldY + ")");

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
                // FIRST ROOM index 0-4
                gp.mobArr[gp.currentMap][i] = new MOB_Goblin(gp, 1220, 1846); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Goblin(gp, 1220, 1998); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Slime(gp, 1460, 1764); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Slime(gp, 1388, 1910); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Slime(gp, 1460, 2054); i++;

                // MINI-BOSS ROOM index 5-7
                gp.mobArr[gp.currentMap][i] = new MOB_Mushroom(gp, 1316, 900); i++;
                gp.mobArr[gp.currentMap][i] = new BOSS_FireWorm(gp, 1316, 1070); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Mushroom(gp, 1316, 1150); i++;

                // FINAL BOSS ROOM index 8-12
                gp.mobArr[gp.currentMap][i] = new MOB_Ramses(gp, 1113, 100); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Ramses(gp, 1520, 100); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Canine(gp, 1154, 300); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Canine(gp, 1479, 300); i++;
                gp.mobArr[gp.currentMap][i] = new BOSS_FrostGiant(gp, 1216, 180);
            }
                break;
            case 2:
            {
                // FIRST ROOM index 0-3
                gp.mobArr[gp.currentMap][i] = new MOB_RobotGuardian(gp, 910, 1660); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_FlyingEye(gp, 780, 1544); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_FlyingEye(gp, 1040, 1544); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_FlyingEye(gp, 908, 1792); i++;

                // MINI-BOSS ROOM index 4-6
                gp.mobArr[gp.currentMap][i] = new MOB_SkeletonKnight(gp, 460, 947); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_SkeletonKnight(gp, 460, 1248); i++;
                gp.mobArr[gp.currentMap][i] = new BOSS_DemonSlime(gp, 170, 970); i++;

                // 'FINAL' BOSS ROOM index 7-10
                gp.mobArr[gp.currentMap][i] = new MOB_ArmoredGuardian(gp, 100, 96); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_ArmoredGuardian(gp, 422, 96); i++;
                gp.mobArr[gp.currentMap][i] = new MOB_Skellington(gp, 266, 220); i++;
                gp.mobArr[gp.currentMap][i] = new BOSS_Golem(gp, 220, 48); i++;

                // STORY BOSS ROOM index 11-12
                gp.mobArr[gp.currentMap][i] = new BOSS_Cultist(gp, 1475, 180); i++;
                gp.mobArr[gp.currentMap][i] = new BOSS_FallenPrincess(gp, 1551, 180);
            }
        }

        // RESET MONSTERS TO SLEEP ON LEVEL LOAD
        for (Entity mon : gp.mobArr[gp.currentMap]) {
            if (mon != null)
                mon.sleep = true;
        }
    }
}
