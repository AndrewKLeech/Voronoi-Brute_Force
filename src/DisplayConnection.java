/*Most code in this file is just initial code for testing. In the end the program will take
* an input and find the points its self. Program makes a voronoi dragram from a set of random
* points
* */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JFrame;

public class DisplayConnection extends JFrame {
    static BufferedImage img;
    static int posX[], posY[];
    static int windowSize = 500;
    static int numberOfPoints = 20;
    public DisplayConnection() {
        //From https://rosettacode.org/wiki/Voronoi_diagram
        super("Contour Connector");
        setBounds(0, 0, windowSize, windowSize);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Random rand = new Random();

        img = new BufferedImage(windowSize, windowSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setPaint ( new Color ( 255, 255, 255 ) );
        g.fillRect ( 0, 0, windowSize, windowSize);
        g.setColor(Color.BLACK);

        posX = new int[numberOfPoints];
        posY = new int[numberOfPoints];
        for (int i = 0; i < numberOfPoints; i++) {
            posX[i] = rand.nextInt(windowSize);
            posY[i] = rand.nextInt(windowSize);
        }

        for (int curX = 0; curX < windowSize; curX++) {//curr x
            for (int curY = 0; curY < windowSize; curY++) {//curr y
                for (int pointA = 0; pointA < numberOfPoints; pointA++) {//point
                    double distA = euclidian(posX[pointA], curX, posY[pointA], curY);
                    double distB = 0;
                    double smallestDist = windowSize;
                    for(int pointB = 0; pointB < numberOfPoints; pointB++){
                        distB = euclidian(posX[pointB], curX, posY[pointB], curY);
                        if(smallestDist>distB){
                            smallestDist = distB;
                        }
                    }
                    double margin = 0.5;
                    if ((distA > smallestDist) && (distA<smallestDist+margin) || (distA < smallestDist) && (distA>smallestDist-margin) ||(smallestDist>distA) && (smallestDist<distA+margin) || (smallestDist < distA) && (smallestDist>distA-margin)) {
                        img.setRGB(curX, curY, 0);
                    }

                }

            }
        }

        for (int i = 0; i < numberOfPoints; i++) {
            //From https://rosettacode.org/wiki/Voronoi_diagram
            g.fill(new Ellipse2D .Double(Math.abs(posX[i] - 2.5), Math.abs(posY[i] - 2.5), 5, 5));
        }
    }
    //From https://rosettacode.org/wiki/Voronoi_diagram
    static double euclidian(int x1, int x2, int y1, int y2) {
        double d;
        d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return d;
    }

    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, this);
    }

    public static void main(String[] args) {
        new DisplayConnection().setVisible(true);
    }
}
