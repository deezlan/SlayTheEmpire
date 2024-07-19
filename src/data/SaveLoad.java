package data;


import entity.Player;
import main.GamePanel;
import java.io.*;

public class SaveLoad implements Serializable {
    GamePanel gp;
    DataStorage ds = new DataStorage();
    File trySaveFiles[];
    public Boolean filledSaveFile[];
    public boolean isloadPage = false;
    public int slot;

    public SaveLoad(GamePanel gp, int numberOfFiles){
        this.gp = gp;
        this.trySaveFiles = new File[numberOfFiles];
        this.filledSaveFile = new Boolean[numberOfFiles];

        for(int i = 0; i < numberOfFiles; i++){
            this.trySaveFiles[i] = new File("trySave" + (i+1) + ".dat");
            this.filledSaveFile[i] = false;
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
            ds.progressSaved = gp.progressSaved;
            ds.weapons.addAll(gp.player.ownedWeapon);

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

        System.out.println(gp.player == null);
        System.out.println(ds.playerClass);
        gp.player = new Player(gp, gp.keyH, gp.cursor, ds.playerClass);
        gp.player.maxLife = ds.maxLife;
        gp.player.currentLife = ds.life;
        gp.player.totalCoins = ds.coin;
        gp.progressSaved = ds.progressSaved;
        gp.player.ownedWeapon.addAll(ds.weapons);
        gp.loadLevel();

    }

    public boolean isSaveFileEmpty(int slot) {
        File saveFile = trySaveFiles[slot];
        boolean filledFile = !saveFile.exists() || saveFile.length() == 0;
        filledSaveFile[slot] = filledFile;
        return filledFile;
    }

    public boolean isAllFileEmpty(){
        boolean firstElement = filledSaveFile[0];
        for(int i = 1; i < filledSaveFile.length; i++){
            isSaveFileEmpty(i);
            if(filledSaveFile[i] != firstElement){
                return false;
            }
        }
        if (firstElement){
            return false;
        }
        return true;
    }
}
