package main;

import entity.NPCMaiden;
import entity.NPCMerchant;
import entity.NPCMystery;
import entity.NPCBlacksmith;
import object.OBJ_DialogueBubble;
import object.OBJ_SavePedestal;
import object.OBJ_Shop;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        switch (gp.gameArea) {
            case 0:
                gp.objArr[0] = new OBJ_Shop(gp);
                gp.objArr[0].worldX = gp.TILE_SIZE*9 + 20;
                gp.objArr[0].worldY = gp.TILE_SIZE/2;

                gp.objArr[1] = new OBJ_SavePedestal();
                gp.objArr[1].worldX = 272;
                gp.objArr[1].worldY = 446;

                gp.objArr[3] = new OBJ_DialogueBubble(); // Maiden chat bubble
                gp.objArr[3].worldX = 410;
                gp.objArr[3].worldY = 220;

                gp.objArr[4] = new OBJ_DialogueBubble(); // Blacksmith chat bubble
                gp.objArr[4].worldX = 230;
                gp.objArr[4].worldY = 60;

                gp.objArr[5] = new OBJ_DialogueBubble(); // Merchant chat bubble
                gp.objArr[5].worldX = 600;
                gp.objArr[5].worldY = 110;

                gp.objArr[6] = new OBJ_DialogueBubble(); // Mystery chat bubble
                gp.objArr[6].worldX = 490;
                gp.objArr[6].worldY = 400;
                break;
            case 1:
                gp.objArr[0] = new OBJ_Shop(gp);
                gp.objArr[0].worldX = gp.TILE_SIZE*9 + 20;
                gp.objArr[0].worldY = gp.TILE_SIZE/2;

                gp.objArr[1] = new OBJ_SavePedestal();
                gp.objArr[1].worldX = 272;
                gp.objArr[1].worldY = 446;

                gp.objArr[3] = new OBJ_DialogueBubble(); // Maiden chat bubble
                gp.objArr[3].worldX = 410;
                gp.objArr[3].worldY = 220;

                gp.objArr[4] = new OBJ_DialogueBubble(); // Blacksmith chat bubble
                gp.objArr[4].worldX = 230;
                gp.objArr[4].worldY = 60;

                gp.objArr[5] = new OBJ_DialogueBubble(); // Merchant chat bubble
                gp.objArr[5].worldX = 600;
                gp.objArr[5].worldY = 110;

                gp.objArr[6] = new OBJ_DialogueBubble(); // Mystery chat bubble
                gp.objArr[6].worldX = 490;
                gp.objArr[6].worldY = 400;
                break;
            default:
                gp.objArr[0] = new OBJ_Shop(gp);
                gp.objArr[0].worldX = gp.TILE_SIZE*9 + 20;
                gp.objArr[0].worldY = gp.TILE_SIZE/2;

                gp.objArr[1] = new OBJ_SavePedestal();
                gp.objArr[1].worldX = 272;
                gp.objArr[1].worldY = 446;

                gp.objArr[3] = new OBJ_DialogueBubble(); // Maiden chat bubble
                gp.objArr[3].worldX = 410;
                gp.objArr[3].worldY = 220;

                gp.objArr[4] = new OBJ_DialogueBubble(); // Blacksmith chat bubble
                gp.objArr[4].worldX = 230;
                gp.objArr[4].worldY = 60;

                gp.objArr[5] = new OBJ_DialogueBubble(); // Merchant chat bubble
                gp.objArr[5].worldX = 600;
                gp.objArr[5].worldY = 110;

                gp.objArr[6] = new OBJ_DialogueBubble(); // Mystery chat bubble
                gp.objArr[6].worldX = 490;
                gp.objArr[6].worldY = 400;
                break;
        }
    }

    public void setNPC() {
        switch (gp.gameArea) {
            case 0:
                gp.npcArr[0] = new NPCMaiden(gp);
                gp.npcArr[0].worldX = 370;
                gp.npcArr[0].worldY = 240;
                System.out.println("NPC Maiden set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");

                gp.npcArr[1] = new NPCMystery(gp);
                gp.npcArr[1].worldX = 450;
                gp.npcArr[1].worldY = 430;
                System.out.println("NPC Mystery set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");

                gp.npcArr[2] = new NPCBlacksmith(gp);
                gp.npcArr[2].worldX = 180;
                gp.npcArr[2].worldY = 80;
                System.out.println("NPC Blacksmith set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");

                gp.npcArr[3] = new NPCMerchant(gp);
                gp.npcArr[3].worldX = 550;
                gp.npcArr[3].worldY = 130;
                System.out.println("NPC Merchant set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");

                break;
            case 1:
                gp.npcArr[0] = new NPCMaiden(gp);
                gp.npcArr[0].worldX = 370;
                gp.npcArr[0].worldY = 240;
                System.out.println("NPC Maiden set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");

                gp.npcArr[1] = new NPCMystery(gp);
                gp.npcArr[1].worldX = 450;
                gp.npcArr[1].worldY = 430;
                System.out.println("NPC Mystery set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");

                gp.npcArr[2] = new NPCBlacksmith(gp);
                gp.npcArr[2].worldX = 180;
                gp.npcArr[2].worldY = 80;
                System.out.println("NPC Blacksmith set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");

                gp.npcArr[3] = new NPCMerchant(gp);
                gp.npcArr[3].worldX = 550;
                gp.npcArr[3].worldY = 130;
                System.out.println("NPC Merchant set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");
                break;
            default:
                gp.npcArr[0] = new NPCMaiden(gp);
                gp.npcArr[0].worldX = 370;
                gp.npcArr[0].worldY = 240;
                System.out.println("NPC Maiden set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");

                gp.npcArr[1] = new NPCMystery(gp);
                gp.npcArr[1].worldX = 450;
                gp.npcArr[1].worldY = 430;
                System.out.println("NPC Mystery set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");

                gp.npcArr[2] = new NPCBlacksmith(gp);
                gp.npcArr[2].worldX = 180;
                gp.npcArr[2].worldY = 80;
                System.out.println("NPC Blacksmith set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");

                gp.npcArr[3] = new NPCMerchant(gp);
                gp.npcArr[3].worldX = 550;
                gp.npcArr[3].worldY = 130;
                System.out.println("NPC Merchant set at (" + gp.npcArr[0].worldX + ", " + gp.npcArr[0].worldY + ")");
                break;
        }
    }
}
