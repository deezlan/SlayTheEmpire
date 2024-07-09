package data;

import main.GamePanel;

import javax.xml.crypto.Data;
import java.io.*;

public class SaveLoad {

    GamePanel gp;

    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }

    public void save(){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();

            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.coin = gp.player.totalCoins;
            ds.playerClass = gp.player.playerClass;

            //Write the DataStorage object
            oos.writeObject(ds);

        }catch(Exception e){
            System.out.println("Save Exception!");
        }
    }
    public void load(){

        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            //Write the DataStorage object
            DataStorage ds  = (DataStorage)ois.readObject();

            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.totalCoins = ds.coin;
            gp.player.playerClass = ds.playerClass;

        }catch(Exception e){
            System.out.println("Load Exception");
        }

    }
}
