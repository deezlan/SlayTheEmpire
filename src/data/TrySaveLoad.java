package data;

import main.GamePanel;

import java.io.*;

public class TrySaveLoad implements Serializable {
    GamePanel gp;
    DataStorage ds = new DataStorage();
    File trySaveFiles[];

    public TrySaveLoad(GamePanel gp, int numberOfFiles){
        this.gp = gp;
        this.trySaveFiles = new File[numberOfFiles];

        for(int i = 0; i < numberOfFiles; i++){
            this.trySaveFiles[i] = new File("trySave" + (i+1) + ".dat");
        }

    }


    public void save(int slot){
        try {
            FileOutputStream fileOut = new FileOutputStream("trySave" + slot + ".dat" );
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            ds.life = gp.player.currentLife;
            ds.maxLife = gp.player.maxLife;
            ds.playerClass = gp.player.playerClass;
            ds.coin = gp.player.totalCoins;

            out.writeObject(ds);
            out.close();

            System.out.println("trySave is saved successfully");

        }catch(IOException i ){
            System.out.println(i.getMessage());
        }

        System.out.println(ds.playerClass);
        System.out.println(ds.life);
        System.out.println(ds.coin);
        System.out.println(ds.maxLife);


    }

    public void load(int slot){
        try{
            FileInputStream fileIn = new FileInputStream("trySave"+ slot +".dat");
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

    public boolean isSaveFileEmpty(int slot) {
        File saveFile = trySaveFiles[slot];
        boolean saveEmpty = !saveFile.exists() || saveFile.length() == 0;
        return saveEmpty;
    }
}
