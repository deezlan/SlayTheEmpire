package main;

import object.OBJ_Gate;
import object.OBJ_Pillar;
import object.OBJ_SavePedestal;

import java.awt.*;

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
            for (int i = 0; i < gp.objArr[1].length; i++){
                if(gp.objArr[gp.currentMap][i] == null){
                    gp.objArr[gp.currentMap][i] = new OBJ_Pillar(gp);
                    gp.objArr[gp.currentMap][i].worldX = 441;
                    gp.objArr[gp.currentMap][i].worldY = 810;
//                    i++;
//                    gp.objArr[gp.currentMap][i] = new OBJ_Pillar(gp);
//                    gp.objArr[gp.currentMap][i].worldX = 441;
//                    gp.objArr[gp.currentMap][i].worldY = 810;
//                    i++;
//                    gp.objArr[gp.currentMap][i] = new OBJ_Pillar(gp);
//                    gp.objArr[gp.currentMap][i].worldX = 431;
//                    gp.objArr[gp.currentMap][i].worldY = 1000;
                    gp.objArr[gp.currentMap][i].tempScene = true;
                    break;
                }
            }
            gp.player.drawing = false;
            scenePhase++;
        }
        if(scenePhase == 1){
            gp.player.worldY -= 2;
        }
    }
}
