import java.awt.*; //imports stuff for text
import java.util.Random; //and random

//looked at tutorial: Keeping Score | Java Pong Part 8 by GamesWithGabe
public class Text {
    String text; //creates variables needed for the text box
    int xpos;
    int ypos;
    double dx;
    double dy;
    Font font;

    public Text(String text, Font font, int xpos, int ypos) {
        this.font = font; //googled that this assigns the inputs you give in the constructor parameter (e.g this.font) to the like general "fields" created at the very top (Font font)
        this.text = text;
        this.xpos = xpos;
        this.ypos = ypos;
        dx = 3;
        dy = 5;
    }

    public void draw(Graphics2D g2) { //draws the text box
        g2.setColor(Color.WHITE); //sets color of text
        g2.setFont(font); //sets font of text
        g2.drawString(text, xpos, ypos); //draws the actual text in a position
    }

    Random random = new Random();

    public void bounce() {
        xpos += dx;//makes it move diagonally to the right and down
        ypos += dy;
        if (xpos > 1000) { //makes it warp across the screen horizontally
            xpos = 0;
        }
        if (xpos < 0) { //makes it warp across the screen horizontally
            xpos = 1000;
        }
        if (ypos < 0 || ypos > 700) { //makes it bounce vertically
            dy = -dy;
            int randombounce = random.nextInt(5); //creates a random int from 0-4
            if (randombounce == 4) { //if the int is 5 its inverts its x motion (1/5 chance)
                dx = -dx; //inverts x motion
            }
        }

    }
}
