package main;

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
    }
}
