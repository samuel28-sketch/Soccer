import java.awt.*;

public class Player {
    Rectangle hitbox; //creates variables for player class
    int width;
    int height;
    Image alivePic;
    Image tackledPic;
    double xpos;
    double ypos;
    int tackleFrameCount;
    double dx;
    double dy;
    boolean isTackled;
    boolean iFrames;
    boolean hasBall;
    boolean iFrameCooldown;
    int iFrameCount;
    int maxspeed;
    int power;

    //randomNumber = (int) (math.random()*6+1)

    public Player(int maxspeed, int power, int widthInput, int heightInput, int xposInput, int yposInput ) {
        hitbox = new Rectangle(xposInput, yposInput, widthInput, heightInput);
        width = widthInput; //set variables
        height = heightInput;
        xpos = xposInput;
        ypos = yposInput;
        dx = 0;
        dy = 0;
        this.maxspeed = maxspeed;
        this.power = power;
    }

    public void move() {
        xpos += dx; //moves the player by dx and dy
        ypos += dy;
        //if (dx>0){dx-=.1;}
        //if (dy>0){dy-=.1;}
        if (xpos > 1000) { //wraps the player to the left side if it goes over the right side of teh screen
            xpos = 0;
        } else if (xpos < 0) { //doesnt let the player go past the left side of the screen
            xpos = 0;
        }
        if (ypos > 700) { //wraps the player vertically across the screen
            ypos = 0;
        } else if (ypos < 0) {//wraps the player vertically across the screen
            ypos = 700;
        }
        hitbox = new Rectangle((int)xpos, (int)ypos, width, height);//updates the hitbox
    }

    public void iframeSet(Ball ball, int time){
        if (iFrames&&!iFrameCooldown) {
        iFrameCount++;
    }
        if (iFrames && iFrameCount > time) {
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

    public void playerTackled(Ball ball) {
        if (isTackled) {
            dx = 0;
            dy = 0;
            tackleFrameCount++;
        }
        if (isTackled && tackleFrameCount > 30) {
            iFrames = false;
            isTackled = false;
            iFrameCount = 0;
        }



    if (isTackled&&!iFrameCooldown) {
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



    /*public void playerTackled(Ball ball){
        if (isTackled&& tackleFrameCount <60){
            dx=0;
            dy=0;
            tackleFrameCount++;
            System.out.println(tackleFrameCount);

        }
        else if (tackleFrameCount ==60&&!iFrameCooldown){
            isTackled = false;
            tackleFrameCount = 0;
            iFrames = true;
            iFrameCooldown = true;
            //         System.out.println(frameCount);
        }
        if (iFrameCooldown&& !hitbox.intersects(ball.hitbox)){
            iFrameCooldown=false;
        }
    }*/

}
