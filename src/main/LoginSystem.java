package main;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class LoginSystem extends JPanel {
    GamePanel gp;
    PrintWriter output;
    Scanner input;
    boolean found;
    String encryptedPass;
    String[] dbCredentials;
    File file = new File("res/userData.txt");

    public LoginSystem(GamePanel gp) {
        this.gp = gp;
        try {
            file.createNewFile();
        } catch (IOException e) { e.printStackTrace(System.out); }
    }

    public void authLogin() {
        try {
            input = new Scanner(file);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        found = false;

        encryptPass();
        // Loop through every entry in the file
        while (input.hasNext() && !found) {
            dbCredentials = input.nextLine().split(":");

            if (dbCredentials[0].equalsIgnoreCase(gp.ui.inpUser)){
                if (dbCredentials[1].equalsIgnoreCase(encryptedPass)){
                    found = true;
//                    gp.ui.validLogin = true;
                    gp.gameState = gp.startMenuState;
                    break;
                }
            }
        }

        if(!found) {
            gp.ui.isInvalidLogin = true;
        }
    }

    public void authRegister() {
        try {
            input = new Scanner(file);
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            output = new PrintWriter(bw);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        found = false;

        encryptPass();
        // Loop through every entry in the file
        while(input.hasNext() && !found) {
            dbCredentials = input.nextLine().split(":");

            if (dbCredentials[0].equalsIgnoreCase(gp.ui.inpUser)){
                found = true;
                gp.ui.usernameTaken = true;
                break;
            }
        }

        if (!found) {
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
