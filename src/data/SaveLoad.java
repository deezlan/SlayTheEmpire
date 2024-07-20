package data;

import entity.Player;
import main.GamePanel;

import java.io.*;

public class SaveLoad implements Serializable {

    GamePanel gp;
    DataStorage ds = new DataStorage();
    File[] SaveFiles;
    public Boolean[] filledSaveFile;
    public boolean isloadPage = false;
    public int slot;

    public SaveLoad(GamePanel gp, int numberOfFiles) {
        this.gp = gp;
        this.SaveFiles = new File[numberOfFiles];
        this.filledSaveFile = new Boolean[numberOfFiles];

        for (int i = 0; i < numberOfFiles; i++) {
            this.SaveFiles[i] = new File("Save" + i + ".dat");
            this.filledSaveFile[i] = false;
        }
        isAllFileEmpty();
    }

    public void save(int slot) {
        try {
            FileOutputStream fileOut = new FileOutputStream("Save" + slot + ".dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            ds.life = gp.player.currentLife;
            ds.maxLife = gp.player.maxLife;
            ds.playerClass = gp.player.playerClass;
            ds.coin = gp.player.totalCoins;
            ds.progressSaved = gp.progressSaved;
            ds.weapons.addAll(gp.player.ownedWeapon);
            System.out.println("file" + slot + " saved");

            out.writeObject(ds);
            out.close();

            filledSaveFile[slot] = true;
            System.out.println(filledSaveFile[slot]);

        } catch (IOException i) {
            System.out.println(i.getMessage());
        }

        System.out.println(ds.playerClass);
        System.out.println(ds.life);
        System.out.println(ds.coin);
        System.out.println(ds.maxLife);
    }

    public void load(int slot) {
        try {
            FileInputStream fileIn = new FileInputStream("Save" + slot + ".dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            ds = (DataStorage) in.readObject();
            in.close();
            fileIn.close();

        } catch (IOException i) {
            System.out.println(i.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        gp.player = new Player(gp, gp.keyH, gp.cursor, ds.playerClass);
        System.out.println(gp.player.currentLife);
        gp.player.maxLife = ds.maxLife;
        gp.player.currentLife = ds.life;
        gp.player.totalCoins = ds.coin;
        gp.progressSaved = ds.progressSaved;
        gp.player.ownedWeapon.addAll(ds.weapons);
        gp.loadLevel();
    }

    public boolean isSaveFileFilled(int slot){
        File saveFile = SaveFiles[slot];
        boolean notFilledFile = !saveFile.exists() || saveFile.length() == 0;
        filledSaveFile[slot] = !notFilledFile;
        return !notFilledFile;
    }

    public boolean isAllFileEmpty(){
        boolean firstElement = filledSaveFile[0];
        for(int i = 1; i < filledSaveFile.length; i++){
            isSaveFileFilled(i);
            if(filledSaveFile[i] != firstElement){
                return false;
            }
        }
        return firstElement;
    }
}
