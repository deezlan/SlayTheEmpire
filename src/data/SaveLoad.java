package data;

import main.GamePanel;

import javax.xml.crypto.Data;
import java.io.*;

public class SaveLoad {

    public boolean isloadPage;
    public boolean slotCheck[];
    GamePanel gp;
    File saveFiles[];


    public SaveLoad(GamePanel gp, int numberOfSaveSlots){
        this.gp = gp;
        this.saveFiles = new File[numberOfSaveSlots];
        this.slotCheck = new boolean[numberOfSaveSlots];
        isloadPage = false;

        for (int i = 0; i < numberOfSaveSlots; i++) {
            this.saveFiles[i] = new File("save" + (i + 1) + ".dat");
            this.slotCheck[i] = false;
        }
    }

    public void clearSaveFile(int slot){
        try(FileOutputStream fos = new FileOutputStream(saveFiles[slot])){

        }catch(IOException e){
            System.out.println("clear save file is not working" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void save(int slot){
        if (slot < 0 || slot >= saveFiles.length) {
            System.out.println("Invalid save slot.");
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFiles[slot]))) {
            DataStorage ds = new DataStorage();
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.currentLife;
            ds.coin = gp.player.totalCoins;
            ds.playerClass = gp.player.playerClass;

            System.out.println(ds.maxLife);
            System.out.println(ds.coin);
            System.out.println(ds.life);
            System.out.println(ds.playerClass);


            // Write the DataStorage object
            oos.writeObject(ds);

        }catch(Exception e){
            System.out.println("Save Exception!");
        }
    }

    public void load(int slot){
        if(!isSaveFileEmpty(slot)){
            System.out.println("saveFile empty!");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFiles[slot]))) {
            // Read the DataStorage object
            DataStorage ds = (DataStorage) ois.readObject();

            System.out.println(ds.maxLife);
            System.out.println(ds.coin);
            System.out.println(ds.life);
            System.out.println(ds.playerClass);

            gp.player.maxLife = ds.maxLife;
            gp.player.currentLife = ds.life;
            gp.player.totalCoins = ds.coin;
            gp.player.playerClass = ds.playerClass;

            slotCheck[slot] = true;

        }catch(Exception e){
            System.out.println("Load Exception");
        }

    }
    public boolean isSaveFileEmpty(int slot) {
        File saveFile = saveFiles[slot];
        boolean saveEmpty = !saveFile.exists() || saveFile.length() == 0;
        if (!saveEmpty){
            slotCheck[slot] = true;
        }
        return saveEmpty;
    }
}
