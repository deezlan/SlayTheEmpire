package data;

import main.GamePanel;

import java.io.Serializable;
import java.util.ArrayList;


public class DataStorage implements Serializable {

    // PLAYER STATS
    int maxLife;
    int life;
    int coin;
    int playerClass;
    int progressSaved;

    ArrayList<Integer> weapons = new ArrayList();


}
