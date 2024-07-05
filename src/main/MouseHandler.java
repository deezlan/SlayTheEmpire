package main;

import entity.Cursor;
//import object.OBJ_Gun_SnowBallCannon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseMotionListener;

public class MouseHandler implements MouseListener {
//    public MouseHandler mouseH = new MouseHandler();
//    entity.Cursor cursor = new Cursor(); // Initialize cursor
    public boolean leftClicked, hovering;

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClicked = true;
            System.out.println("mouseclicked");
        }
    }

    public void clearMouseClick() {
        leftClicked = false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClicked = false;
        }
        System.out.println("Released");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClicked = true;
            System.out.println("mouseclicked");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        hovering = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        hovering = false;
    }
}
