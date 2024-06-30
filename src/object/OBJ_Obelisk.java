package object;

import main.UtilityTool;

import java.io.IOException;

public class OBJ_Obelisk extends SuperObject {
    public OBJ_Obelisk() {
        name = "Obelisk";
        message = "teleport";
        // Load sprites
        getObjectSprites();

        // Set collision settings
        solidArea.x = 48;
        solidArea.y = 160;
        solidArea.width = 20;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collision = true;
    }

    public void getObjectSprites() {
        String dir = "/objects/Obelisk/";
        try {
            defaultList.add(0, UtilityTool.loadSprite(dir + "idle/00.png", "Missing Idle 0"));
            defaultList.add(1, UtilityTool.loadSprite(dir + "idle/01.png", "Missing Idle 1"));
            defaultList.add(2, UtilityTool.loadSprite(dir + "idle/02.png", "Missing Idle 2"));
            defaultList.add(3, UtilityTool.loadSprite(dir + "idle/03.png", "Missing Idle 3"));
            defaultList.add(4, UtilityTool.loadSprite(dir + "idle/04.png", "Missing Idle 4"));
            defaultList.add(5, UtilityTool.loadSprite(dir + "idle/05.png", "Missing Idle 5"));
            defaultList.add(6, UtilityTool.loadSprite(dir + "idle/06.png", "Missing Idle 6"));
            defaultList.add(7, UtilityTool.loadSprite(dir + "idle/07.png", "Missing Idle 7"));
            defaultList.add(8, UtilityTool.loadSprite(dir + "idle/08.png", "Missing Idle 8"));
            defaultList.add(9, UtilityTool.loadSprite(dir + "idle/09.png", "Missing Idle 9"));
            defaultList.add(10, UtilityTool.loadSprite(dir + "idle/10.png", "Missing Idle 10"));
            defaultList.add(11, UtilityTool.loadSprite(dir + "idle/11.png", "Missing Idle 11"));
            defaultList.add(12, UtilityTool.loadSprite(dir + "idle/12.png", "Missing Idle 12"));
            defaultList.add(13, UtilityTool.loadSprite(dir + "idle/13.png", "Missing Idle 13"));
            UtilityTool.scaleObjectList(defaultList, (int)(36*3.3), 36*4 + 70);

            interactList.add(0, UtilityTool.loadSprite(dir + "interact/00.png", "Missing Interact 0"));
            interactList.add(1, UtilityTool.loadSprite(dir + "interact/01.png", "Missing Interact 1"));
            interactList.add(2, UtilityTool.loadSprite(dir + "interact/02.png", "Missing Interact 2"));
            interactList.add(3, UtilityTool.loadSprite(dir + "interact/03.png", "Missing Interact 3"));
            interactList.add(4, UtilityTool.loadSprite(dir + "interact/04.png", "Missing Interact 4"));
            interactList.add(5, UtilityTool.loadSprite(dir + "interact/05.png", "Missing Interact 5"));
            interactList.add(6, UtilityTool.loadSprite(dir + "interact/06.png", "Missing Interact 6"));
            interactList.add(7, UtilityTool.loadSprite(dir + "interact/07.png", "Missing Interact 7"));
            interactList.add(8, UtilityTool.loadSprite(dir + "interact/08.png", "Missing Interact 8"));
            interactList.add(9, UtilityTool.loadSprite(dir + "interact/09.png", "Missing Interact 9"));
            interactList.add(10, UtilityTool.loadSprite(dir + "interact/10.png", "Missing Interact 10"));
            interactList.add(11, UtilityTool.loadSprite(dir + "interact/11.png", "Missing Interact 11"));
            interactList.add(12, UtilityTool.loadSprite(dir + "interact/12.png", "Missing Interact 12"));
            interactList.add(13, UtilityTool.loadSprite(dir + "interact/13.png", "Missing Interact 13"));
            UtilityTool.scaleObjectList(interactList, (int)(36*3.3), 36*4 + 70);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}