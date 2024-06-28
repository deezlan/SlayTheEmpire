package main;

import entity.Projectile;
import object.OBJ_Gun_SnowBallCannon;
import object.OBJ_SavePedestal;
import object.OBJ_Shop;

public class AssetSetter {
    GamePanel gp;
    MouseHandler mouseH;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.objArray[0] = new OBJ_Shop();
        gp.objArray[0].worldX = 106;
        gp.objArray[0].worldY = 192;

        gp.objArray[1] = new OBJ_SavePedestal();
        gp.objArray[1].worldX = 50;
        gp.objArray[1].worldY = 50;

        if (gp.mouseH.leftClick) {
            gp.projectileArr[0] = new Projectile(gp);
            gp.projectileArr[0].worldX = 400;
            gp.projectileArr[0].worldY = 300;
        }
    }
}
