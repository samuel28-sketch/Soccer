import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Ball {
    Image dribblingimage;
    double xpos;
    double ypos;
    int width;
    int height;
    double dx;
    double dy;
    Rectangle hitbox;
    boolean playerPossession;
    boolean npcPossession;
    boolean gamestart;
    char ballDirection;
    double kickPower;
    boolean kicked;
    double kickedDX;
    double kickedDY;

    //need a like direction variable either booleans or a char maybe
    //maybe need a like possestion boolean, if player has ball or smth

    public Ball(int width, int height, int xpos, int ypos, boolean playerPossession){
        this.width = width;
        this.height = height;
        this.xpos = xpos;
        this.ypos=ypos;
        this.playerPossession = playerPossession;
        this.kickPower = 1;

        hitbox = new Rectangle(xpos, ypos, width, height);
        boolean gamestart = true;
    }

    public boolean offScreen(){
        if (xpos>1000||xpos<0||ypos>700||ypos<0){
            return true;
        }
        else {
            return false;
        }
    }

    public void move(){
        if (kicked&&!playerPossession&&!npcPossession) {
            xpos += kickedDX * kickPower;
            ypos += kickedDY * kickPower;
        }
        else {
            xpos += dx;
            ypos += dy;
        }
        if (xpos>1000){ //wraps the player to the left side if it goes over the right side of teh screen
            xpos = 0;
        }
        else if (xpos<0){ //doesnt let the player go past the left side of the screen
            xpos=0;
        }
        if (ypos>700){ //wraps the player vertically across the screen
            ypos = 0;
        }
        else if (ypos<0){//wraps the player vertically across the screen
            ypos=700;
        }
        hitbox = new Rectangle((int)xpos, (int)ypos, width, height);

    }

    public void reset(){
        dx=0;
        dy=0;
        kickedDY=0;
        kickedDX=0;
    }

}
