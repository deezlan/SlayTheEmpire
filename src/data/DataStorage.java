package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {

    // PLAYER STATS
    int maxLife;
    int life;
    int coin;
    int playerClass;
    int maxMana;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;

    // PLAYER WEAPON INVENTORY
    ArrayList<String> weaponNames = new ArrayList<>();
}
