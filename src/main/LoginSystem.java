package main;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class LoginSystem extends JPanel {
    GamePanel gp;
    PrintWriter output;
    Scanner input;
    String encryptedPass;
    String[] userAcc;
    HashMap<String, String> accDatabase = new HashMap<>();
    File file = new File("res/userData.txt");

    public LoginSystem(GamePanel gp) {
        this.gp = gp;
        try {
            file.createNewFile();
        } catch (IOException e) { e.printStackTrace(System.out); }
    }

    public void loadUsers() {
        accDatabase.clear();
        try {
            input = new Scanner(file);
        } catch (IOException e) { e.printStackTrace(System.out); }

        while (input.hasNext()) {
            userAcc = input.nextLine().split(":");
            accDatabase.put(userAcc[0], userAcc[1]);
        }
    }

    public void authLogin() {
        loadUsers();

        encryptPass();
        if (accDatabase.containsKey(gp.ui.inpUser)) {
            if (accDatabase.get(gp.ui.inpUser).equals(encryptedPass)) {
                gp.gameState = gp.startMenuState;
            } else { gp.ui.isInvalidLogin = true; }
        } else {
            gp.ui.isInvalidLogin = true;
        }
    }

    public void authRegister() {
        loadUsers();

        if (accDatabase.containsKey(gp.ui.inpUser)) {
            gp.ui.usernameTaken = true;
        } else {
            try {
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                output = new PrintWriter(bw);
            } catch (IOException e) { e.printStackTrace(System.out); }

            encryptPass();
            output.print(gp.ui.inpUser + ":" + encryptedPass + "\n");
            output.close();
        }
    }

    public void encryptPass() {
        try {
            // Initialize MD5 Hashing Algorithm
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(gp.ui.inpPass.getBytes());

            // Convert Hash value into bytes
            byte[] bytes = m.digest();

            // Convert into hexadecimal format
            StringBuilder s = new StringBuilder();
            for (byte aByte : bytes)
                s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));

            // Store in String
            encryptedPass = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.out);
        }
    }
}
