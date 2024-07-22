package data;

import entity.Player;
import main.GamePanel;

import java.io.*;

public class SaveLoad implements Serializable {
    GamePanel gp;
    DataStorage ds = new DataStorage();
    File[] SaveFiles;
    public Boolean[] filledSaveFile;
    public boolean isLoadPage = false;
    public boolean isSaveCompleted = false;
    public int slot;

    // INITIALISING ARRAYS FOR SAVE FILES
    public SaveLoad(GamePanel gp, int numberOfFiles) {
        this.gp = gp;
        this.SaveFiles = new File[numberOfFiles];
        this.filledSaveFile = new Boolean[numberOfFiles];

        for (int i = 0; i < numberOfFiles; i++) {
            this.SaveFiles[i] = new File("res/saves/" + gp.ui.inpUser + "/" +"Save" + i + ".dat");
            this.filledSaveFile[i] = false;
        }
        File dir = new File("res/saves");
        System.out.println("Save Dir Created: " + dir.mkdir());
        isAllFileEmpty();
    }

    // SAVING GAME PROGRESS TO FILE
    public void save(int slot) {
        try {
            File userDir = new File("res/saves/" + gp.ui.inpUser);
            System.out.println("Created User Dir: " + userDir.mkdir());
            FileOutputStream fileOut = new FileOutputStream
                    ("res/saves/" + gp.ui.inpUser + "/Save" + slot + ".dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            ds.life = gp.player.currentLife;
            ds.maxLife = gp.player.maxLife;
            ds.playerClass = gp.player.playerClass;
            ds.coin = gp.player.totalCoins;
            ds.progressSaved = gp.progressSaved;
            ds.weapons.addAll(gp.player.ownedWeapon);
            out.writeObject(ds);
            out.close();

            filledSaveFile[slot] = true;
        } catch (IOException i) {
            System.out.println(i.getMessage());
        }
    }

    // LOADING SAVE FILES TO GAME
    public void load(int slot) {
        try {
            FileInputStream fileIn = new FileInputStream
                    ("res/saves/" + gp.ui.inpUser + "/Save" + slot + ".dat");
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
        gp.player.maxLife = ds.maxLife;
        gp.player.currentLife = ds.life;
        gp.player.totalCoins = ds.coin;
        gp.progressSaved = ds.progressSaved;
        gp.player.ownedWeapon.addAll(ds.weapons);
        gp.loadLevel();
    }

    /*
    CHECKS IF AN INDIVIDUAL FILE IS FILLED
    then updates filledSaveFile[] with corresponding boolean
    if it is filled or not, returns a boolean
     */
    public boolean isSaveFileFilled(int slot){
        File saveFile = SaveFiles[slot];
        boolean notFilledFile = !saveFile.exists() || saveFile.length() == 0;
        filledSaveFile[slot] = !notFilledFile;
        return !notFilledFile;
    }

    /*
    CHECKS IF ALL FILES ARE EMPTY
    uses isSaveFileFilled to check for each file
    then returns false if not all the files are empty
     */
    public boolean isAllFileEmpty(){
        boolean firstElement = filledSaveFile[0];
        for(int i = 1; i < filledSaveFile.length; i++){
            isSaveFileFilled(i);
            if(filledSaveFile[i] != firstElement){
                return false;
            }
        }
        return !firstElement;
    }
}
