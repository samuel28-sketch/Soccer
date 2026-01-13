import java.awt.*;

public class Goal {
    Rectangle hitbox;
       int width;
       int height;
       double xpos;
       double ypos;
       double distanceToPlayer;
       double angleToPlayer;
    boolean scored; //might not need, let see how it turns out

    public Goal(int widthInput, int heightInput, int xposInput, int yposInput){//its just makes a hitbox since the goals are alr in the picture really no that complicated
        hitbox = new Rectangle(xposInput, yposInput, widthInput, heightInput);
        width =widthInput;
        height=heightInput;
        xpos=xposInput;
        ypos=yposInput;
    }


}
