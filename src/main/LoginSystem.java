package main;

import data.SaveLoad;

import javax.swing.*;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class LoginSystem extends JPanel {
    GamePanel gp;
    PrintWriter output;
    Scanner input;
    String encryptedPass;
    String[] accUser;
    HashMap<String, String> accDatabase;
    File file;

    public LoginSystem(GamePanel gp) {
        this.gp = gp;
        file = new File("res/userData.txt");
        accDatabase = new HashMap<>();

        try {
            // CREATES FILE IF DOESN'T EXIST
            file.createNewFile();
        } catch (IOException e) { e.printStackTrace(System.out); }
    }

    public void loadUsers() {
        accDatabase.clear();
        try {
            input = new Scanner(file);
        } catch (IOException e) { e.printStackTrace(System.out); }

        while (input.hasNext()) {
            accUser = input.nextLine().split(":");
            accDatabase.put(accUser[0], accUser[1]);
        }
    }

    public void authLogin() {
        loadUsers();

        encryptPass();
        if (accDatabase.containsKey(gp.ui.inpUser)) {
            if (accDatabase.get(gp.ui.inpUser).equals(encryptedPass)) {
                gp.gameState = gp.MAIN_MENU_STATE;
                gp.saveLoad = new SaveLoad(gp, 3);
            } else { gp.ui.isInvalidLogin = true; }
        } else {
            gp.ui.isInvalidLogin = true;
        }
    }

    public void authRegister() {
        loadUsers();

        if (accDatabase.containsKey(gp.ui.inpUser)) {
            gp.ui.isUserTaken = true;
        } else {
            try {
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                output = new PrintWriter(bw);
            } catch (IOException e) { e.printStackTrace(System.out); }

            encryptPass();
            output.print(gp.ui.inpUser + ":" + encryptedPass + "\n");
            output.close();
            gp.ui.regSuccessful = true;
        }
    }

    public void encryptPass() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            BigInteger number = new BigInteger(1, md.digest(gp.ui.inpPass.getBytes(StandardCharsets.UTF_8)));
            StringBuilder hexString = new StringBuilder(number.toString(16));

            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }

            encryptedPass = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.out);
        }
    }
}
