package main;

import entity.PlayerDummy;
import mobs.MOB_FrostGiant;
import object.OBJ_Gate;
import object.OBJ_Pillar;
import object.OBJ_SavePedestal;

import java.awt.*;
import java.util.Objects;

public class CutsceneManager {
    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;

    // SCENE NUMBERS
    public final int NA = 0;
    public final int frostGiant = 1;

    public CutsceneManager(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        this.g2=g2;

        switch (sceneNum) {
            case frostGiant: scene_FrostGiant(); break;
        }
    }

    public void scene_FrostGiant() {
        if(scenePhase == 0) {
            gp.bossBattleOn = true;
//            for (int i = 0; i < gp.objArr[1].length; i++){
//                if(gp.objArr[gp.currentMap][i] == null){
//                    gp.objArr[gp.currentMap][i].tempScene = true;
//                    break;
//                }
//            }
            for(int i = 0; i < gp.npcArr[1].length; i++) {
                if(gp.npcArr[gp.currentMap][i] == null){
                    gp.npcArr[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.npcArr[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npcArr[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npcArr[gp.currentMap][i].action = gp.player.action;
                    break;
                }
            }
            gp.player.drawing = false;
            scenePhase++;
        }
        if(scenePhase == 1){
            gp.player.worldY -= 2;
            if(gp.player.worldY < gp.TILE_SIZE*5){
                scenePhase++;
            }
        }
        if(scenePhase == 2){
            for(int i = 0; i < gp.mobArr[1].length; i++){
                if(gp.mobArr[gp.currentMap][i] != null && Objects.equals(gp.mobArr[gp.currentMap][i].name, MOB_FrostGiant.monName)){
                    gp.mobArr[gp.currentMap][i].sleep = false;
                    gp.ui.npc = gp.mobArr[gp.currentMap][i];
                    scenePhase++;
                    break;
                }
            }
        }
        if(scenePhase == 3) {
            gp.ui.drawDialogScreen();
        }
    }
}
