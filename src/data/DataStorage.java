package data;

import entity.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {

    // PLAYER STATS
    int maxLife;
    int life;
    int coin;
    int playerClass;

    ArrayList<Integer> weapons = new ArrayList();
}
