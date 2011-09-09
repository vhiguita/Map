
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.*;
import javax.microedition.location.*;

class NavegarCanvas extends Canvas {

    private Image image;
    private int cordX = 0;
    private int cordY = 0;
    private int deltaX = 0;
    private int deltaY = 0;
    private Graphics g2;
    private boolean marcar = false;
    private Vector vCordX;
    private Vector vCordY;
    Mapa eje;

    public NavegarCanvas(Image image, Mapa eje) {
        this.image = image;
        this.eje = eje;
        cordX = 0;
        cordY = 0;
        vCordX = new Vector(0);
        vCordY = new Vector(0);

        deltaX = getWidth() / 4;
        System.out.println("getWidth():" + deltaX);
        deltaY = getHeight() / 4;
        System.out.println("getHeight():" + deltaY);
    }

    public void deltaXY(int x, int y) {
        cordX += x;
        cordY += y;
    }

    public void add(int cordX, int cordY) {
                vCordX.addElement(String.valueOf(cordX));
                vCordY.addElement(String.valueOf(cordY));
                eje.vCordX = vCordX;
                eje.vCordY = vCordY;

    }
     public void relocate(int x, int y) {
          g2.translate(x, y);
    }


    public void paint(Graphics g) {
        int width = this.getWidth();
//        System.out.println("this.getWidth():" + this.getWidth());
        int height = this.getHeight();
//        System.out.println("this.getHeight():" + this.getHeight());
        g2 = g;
        g.setGrayScale(0);
        g.fillRect(0, 0, width - 1, height - 1);
        g.setGrayScale(0);
        g.drawRect(0, 0, width - 1, height - 1);
        System.out.println("coordx():" + cordX);
        System.out.println("coordy():" + cordY);

        g.translate(cordX, cordY);
        
        for (int j = 0; j < vCordX.size(); ++j) {
            String str = (String )vCordX.elementAt(j);
            int corx = Integer.parseInt(str);
            String str2 = (String) vCordY.elementAt(j);
            int cory = Integer.parseInt(str2);
            g.drawRect(corx, cory, 20, 20);
        }


        g.drawImage(image, 0, 0, g.TOP | g.LEFT);
        g.setGrayScale(0);


//
//        g.translate(-g.getTranslateX(), -g.getTranslateY());
//        g.drawRect(cordX + deltaX*2, cordY + deltaY*2, 20, 20);
//        g.drawRect((cordX + deltaX) / 4, (cordY + deltaY) / 4, 20, 20);
        g.drawRect(-g.getTranslateX()+deltaX*2, -g.getTranslateY()+deltaY*2, 20, 20);
        System.out.println("blah: " + g.getTranslateX());
        System.out.println("blahY: " + g.getTranslateY());
//        g.drawRect(g.getTranslateX(), -g.getTranslateY(),; 20, 20);

//        System.out.println("g.TOP():" + g.getTranslateX());
//
//        System.out.println("g.LEFT():" + g.getTranslateY());

    }

    protected void keyPressed(int keyCode) {
        int move = getGameAction(keyCode);
        switch (move) {
            case UP:
                deltaXY(0, deltaY);
                break;
            case DOWN:
                deltaXY(0, -deltaY);
                break;
            case LEFT:
                deltaXY(deltaX, 0);
                break;
            case RIGHT:
                deltaXY(-deltaX, 0);
                break;
            case FIRE:
                Display.getDisplay(eje).setCurrent(eje.form);
                eje.setCords(cordX, cordY);
                System.out.println("I just pressed fire");
                System.out.println("(" + cordX + ", " + cordY + ")");
                add(cordX, cordY);
               
                break;
        }
        repaint();
    }
} 