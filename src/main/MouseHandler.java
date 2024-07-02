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
    public boolean leftClick;
    public boolean shortGet = true;

//    public MouseHandler() {
//        addMouseMotionListener(new MouseAdapter() {
//            @Override
//            public void mouseMoved(MouseEvent e) {
//                cursor.updateMousePosition(e.getX(), e.getY());
//            }
//
//        });
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                mouseH.mouseClicked(e);
//            }
//        });
//    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = true;
            System.out.println("mouseclicked");
            shortGet = false;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
