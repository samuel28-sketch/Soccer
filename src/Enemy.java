import java.awt.*;

public class Enemy {

    Rectangle hitbox;//defines variables for the enemy
    int width;
    int height;
    Image defaultPic;
    Image HESGETTINGCLOSE;
    Image tacklingPic;
    int xpos;
    int ypos;
    double dx;
    double dy;
    boolean isAlive;
    boolean hasBall;
    boolean iFrames;
    int maxspeed;
    double speed;
    int iFrameCount;
    int iFrameCooldownCount;
    boolean iFrameCooldown;
    BasicGameApp game;

    public Enemy(int maxspeed, double speed, int widthInput, int heightInput, int xposInput, int yposInput){//constructor for the enemy
        hitbox = new Rectangle(xposInput, yposInput, widthInput, heightInput);//makes the hitbox
        width =widthInput;// sets the variables for the enemy
        height=heightInput;
        xpos=xposInput;
        ypos=yposInput;
        this.maxspeed = maxspeed;
        this.speed = speed;
        dx = 0;
        dy = 0;
    }

    public void move(){
        xpos += dx;
        ypos += dy;
    }
    public void iframeSetNPC(Ball ball) {
        if (iFrames&&!iFrameCooldown) {
            iFrameCount++;
        }
        if (iFrames && iFrameCount > 40) {
            iFrameCooldown=true;
            iFrameCount = 0;
            iFrames = false;
            return;
        }
        if (iFrameCooldown==true){
            if(!hitbox.intersects(ball.hitbox)){
                iFrameCooldown=false;
            }
        }
    }


}
