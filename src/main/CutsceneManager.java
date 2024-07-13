package main;

import entity.PlayerDummy;
import mobs.BOSS_FrostGiant;

import java.awt.*;

public class CutsceneManager {
    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;

    // SCENE NUMBERS
    public final int NA = 0;
    public final int frostGiant = 1;
    public final int demonSlime = 2;

    public CutsceneManager(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        switch (sceneNum) {
            case frostGiant: scene_FrostGiant(); break;
            case demonSlime: scene_DemonSlime(); break; // (WIP)
        }
    }

    public void scene_FrostGiant() {
        if (scenePhase == 0) {
            gp.bossBattleOn = true;
//            for (int i = 0; i < gp.objArr[1].length; i++){
//                if(gp.objArr[gp.currentMap][i] == null){
//                    gp.objArr[gp.currentMap][i] = new OBJ_Pillar(gp);
//                    gp.objArr[gp.currentMap][i].worldX = gp.TILE_SIZE*23;
//                    gp.objArr[gp.currentMap][i].tempScene = true;
//                    break;
//                }
//            }
            for (int i = 0; i < gp.npcArr[1].length; i++) {
                if (gp.npcArr[gp.currentMap][i] == null) {
                    gp.npcArr[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.npcArr[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npcArr[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npcArr[gp.currentMap][i].action = gp.player.action;
                    break;
                }
            }


                    gp.player.drawing = false;
                    scenePhase++;
                    System.out.println("Phase 0");
                }

                if (scenePhase == 1) {
                    gp.player.worldY -= 2;
                    if (gp.player.worldY < gp.TILE_SIZE * 4) {
                        scenePhase++;
                    }
                    System.out.println("Phase 1");
                }
                if (scenePhase == 2) {
                    for (int i = 0; i < gp.mobArr[1].length; i++) {
                        if (gp.mobArr[gp.currentMap][i] != null && (gp.mobArr[gp.currentMap][i].name.equals(BOSS_FrostGiant.monName))) {
                            gp.mobArr[gp.currentMap][i].sleep = false;
                            gp.ui.npc = gp.mobArr[gp.currentMap][i];
                            scenePhase++;
                            break;
                        }
                    }
                    System.out.println("Phase 2");
                }
                if (scenePhase == 3) {
                    gp.ui.drawDialogScreen();
                    for (int i = 0; i < gp.mobArr[1].length; i++) {
                        if (gp.mobArr[gp.currentMap][i] != null && (gp.mobArr[gp.currentMap][i].name.equals(BOSS_FrostGiant.monName))) {
                            gp.mobArr[gp.currentMap][i].onPath = true;
                            break;
                        }
                    }
                    System.out.println("Phase 3");
                }

                if (scenePhase == 4) {
                    for (int i = 0; i < gp.npcArr[1].length; i++) {
                        if (gp.npcArr[gp.currentMap][i] != null && (gp.npcArr[gp.currentMap][i].name.equals(PlayerDummy.npcName))) {
                            gp.player.worldX = gp.npcArr[gp.currentMap][i].worldX;
                            gp.player.worldY = gp.npcArr[gp.currentMap][i].worldY;
                            gp.npcArr[gp.currentMap][i] = null;
                            break;
                        }
                    }
                    gp.player.drawing = true;
                    sceneNum = NA;
                    scenePhase = 0;
                    gp.gameState = gp.playState;
                    System.out.println("Phase 4");
                }
            }


    public void scene_FireWorm() {
        if (scenePhase == 0) {
            gp.bossBattleOn = true;
//            for (int i = 0; i < gp.objArr[1].length; i++){
//                if(gp.objArr[gp.currentMap][i] == null){
//                    gp.objArr[gp.currentMap][i] = new OBJ_Pillar(gp);
//                    gp.objArr[gp.currentMap][i].worldX = gp.TILE_SIZE*23;
//                    gp.objArr[gp.currentMap][i].tempScene = true;
//                    break;
//                }
//            }
            for (int i = 0; i < gp.npcArr[1].length; i++) {
                if (gp.npcArr[gp.currentMap][i] == null) {
                    gp.npcArr[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.npcArr[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npcArr[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npcArr[gp.currentMap][i].action = gp.player.action;
                    break;
                }
            }


            gp.player.drawing = false;
            scenePhase++;
            System.out.println("Phase 0");
        }

        if (scenePhase == 1) {
            gp.player.worldY -= 2;
            if (gp.player.worldY < gp.TILE_SIZE * 4) {
                scenePhase++;
            }
            System.out.println("Phase 1");
        }
        if(scenePhase == 2){
            for(int i = 0; i < gp.mobArr[1].length; i++){
                if(gp.mobArr[gp.currentMap][i] != null && (gp.mobArr[gp.currentMap][i].name.equals(BOSS_FrostGiant.monName))){
                    gp.mobArr[gp.currentMap][i].sleep = false;
                    gp.ui.npc = gp.mobArr[gp.currentMap][i];
                    scenePhase++;
                    break;
                }
            }
            System.out.println("Phase 2");
        }
        if (scenePhase == 3) {
            gp.ui.drawDialogScreen();
            for(int i = 0; i < gp.mobArr[1].length; i++){
                if(gp.mobArr[gp.currentMap][i] != null && (gp.mobArr[gp.currentMap][i].name.equals(BOSS_FrostGiant.monName))){
                gp.mobArr[gp.currentMap][i].onPath = true;
                break;
                }
            }
            System.out.println("Phase 3");
        }

        if (scenePhase == 4) {
            for (int i = 0; i < gp.npcArr[1].length; i++) {
                if (gp.npcArr[gp.currentMap][i] != null && (gp.npcArr[gp.currentMap][i].name.equals(PlayerDummy.npcName))) {
                    gp.player.worldX = gp.npcArr[gp.currentMap][i].worldX;
                    gp.player.worldY = gp.npcArr[gp.currentMap][i].worldY;
                    gp.npcArr[gp.currentMap][i] = null;
                    break;
                }
            }
            gp.player.drawing = true;
            sceneNum = NA;
            scenePhase = 0;
            gp.gameState = gp.playState;
            System.out.println("Phase 4");
        }
    }
    public void scene_DemonSlime() {

    }
        }

