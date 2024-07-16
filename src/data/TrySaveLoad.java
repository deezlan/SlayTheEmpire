package data;

import main.GamePanel;

import java.io.*;

public class TrySaveLoad implements Serializable {
    GamePanel gp;
    DataStorage ds = new DataStorage();

    public TrySaveLoad(GamePanel gp){
        this.gp = gp;
    }

    public void save(){
        try {
            FileOutputStream fileOut = new FileOutputStream("trySave.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            ds.life = gp.player.currentLife;
            ds.maxLife = gp.player.maxLife;
            ds.playerClass = gp.player.playerClass;
            ds.coin = gp.player.totalCoins;

            out.writeObject(ds);
            out.close();

            System.out.println("trySave is saved successfully");

        }catch(IOException i ){
            System.out.println(i.getMessage());;
        }

        System.out.println(ds.playerClass);
        System.out.println(ds.life);
        System.out.println(ds.coin);
        System.out.println(ds.maxLife);


    }

    public void load(){
        try{
            FileInputStream fileIn = new FileInputStream("trySave.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            ds = (DataStorage) in.readObject();
            in.close();
            fileIn.close();

        }catch(IOException i ){
            System.out.println(i.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        gp.player.playerClass = ds.playerClass;
        gp.player.maxLife = ds.maxLife;
        gp.player.currentLife = ds.life;
        gp.player.totalCoins = ds.coin;
        System.out.println(ds.playerClass);
        System.out.println(ds.life);
        System.out.println(ds.coin);
        System.out.println(ds.maxLife);

    }
}
