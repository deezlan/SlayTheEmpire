package main;

import object.OBJ_SavePedestal;
import object.OBJ_Shop;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.objArray[0] = new OBJ_Shop();
        gp.objArray[0].worldX = 106;
        gp.objArray[0].worldY = 192;

        gp.objArray[1] = new OBJ_SavePedestal();
        gp.objArray[1].worldX = 96+48;
        gp.objArray[1].worldY = 400;
    }
}
