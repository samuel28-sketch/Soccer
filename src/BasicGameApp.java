//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv DON'T CHANGE! vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// Graphics Libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Set;

//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
public class BasicGameApp implements Runnable, MouseListener {

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;


    //Variable Definition Section
    //You can set their initial values too
    // Like Mario mario = new Mario(); //

    Player player; //adds all the variables used publically in this class and stuff
    int iframes;
    Goal leftGoal;
    Goal rightGoal;
    Enemy npc1;
    Enemy npc2;
    Enemy npc3;
    Ball ball;
    Image background;
    double distance;
    int sprintFrameCount;
    int iFrameCount;
    int tackleFrameCount;
    int scorePlayer = 0;
    int scoreOpponent = 0;
    Text scoreboard;
    int mouseX;
    int mouseY;
    double kickPower;
    boolean mouseHeld;
    boolean kicked;

    ArrayList<Enemy> enemies=new ArrayList<>();
    ArrayList<Teammate> teammates=new ArrayList<>();


    KeyInput keyInput;






    // Initialize your variables and construct your program objects here.
    public BasicGameApp() { // BasicGameApp constructor

        //variable and objects
        //create (construct) the objects needed for the game
        player = new Player(5, 5,50,50,200,300); //creates (constructs) the objects needed for the game

        npc1 = new Enemy(10, 3,50,50,800,300);
        npc2 = new Enemy(10,3,50,50,800,500);
        npc3 = new Enemy(10,3,50,50,800,500);

        enemies.add(npc1);
        enemies.add(npc2);
        enemies.add(npc3);

        ball = new Ball(25,25,255,355,true);
        leftGoal = new Goal(50,90,0,315);
        rightGoal = new Goal(50,90,1000,315);

        keyInput = new KeyInput(this); //creates keyinput object and links its methods to this class
        Font font = new Font("Comic Sans", Font.PLAIN,28); //creates font object for scoreboard (see text class for credits)
        scoreboard = new Text("score:" + scorePlayer + " : " + scoreOpponent, font,500,100); //creates the scoreboard object
        setUpGraphics();
        canvas.addMouseListener(this);




        background = Toolkit.getDefaultToolkit().getImage("fieldPH.jpeg"); //assigns images to all the image variables
        player.alivePic = Toolkit.getDefaultToolkit().getImage("playerPH.jpeg");
        player.tackledPic = Toolkit.getDefaultToolkit().getImage("playerTCKL.PH.jpeg");

        npc1.defaultPic = Toolkit.getDefaultToolkit().getImage("teammatePH.jpeg");
        npc1.HESGETTINGCLOSE = Toolkit.getDefaultToolkit().getImage("teammateSPR.jpeg");
        npc1.tacklingPic = Toolkit.getDefaultToolkit().getImage("teammateTCKL.PH.jpeg");

        ball.dribblingimage = Toolkit.getDefaultToolkit().getImage("ball.png");






    }
    // end BasicGameApp constructor
    boolean moving; //creates a moving boolean to detect when the playe risnt moving to add friction
    public void moveThings() {
        //call the move() code for each object  -
        scoreboard.bounce();
        player.move(); //player constantly moves by dx and dy which are set to 0, decelerate to 0, and increase w the keyboard inputs bellow, this code bellow makes it accelerate while a key is being held


        if (!ball.gamestart&&ball.playerPossession){
            ball.dx = player.dx;
            ball.dy = player.dy;
        }
        if (ball.playerPossession||ball.gamestart) {
                npcDefensivePath(); //makes the enemy chase the player only while its alive
             //   npcSprint();
               // System.out.println("defensive path");
        }

        if (!ball.playerPossession&&!ball.gamestart&&ball.npcPossession) /*if (!ball.playerPossession&&!ball.gamestart)*/ {
                npcOffensivePath();
                ball.dx = npc1.dx;
                ball.dy = npc1.dy;
             //   System.out.println("offensive path");
        }

        ball.move();

        if(keyInput.isKeyDown(KeyEvent.VK_W)&&player.dy>-player.maxspeed){
            player.dy-=.5 ;
            moving = true;
        }
        if(keyInput.isKeyDown(KeyEvent.VK_S)&&player.dy<player.maxspeed){
            player.dy+=.5 ;
            moving = true;
        }
        if(keyInput.isKeyDown(KeyEvent.VK_A)&&player.dx>-player.maxspeed){
            player.dx-=.5 ;
            moving = true;
        }
        if(keyInput.isKeyDown(KeyEvent.VK_D)&&player.dx<player.maxspeed){
            player.dx+=.5 ;
            moving = true;
        }
        if (moving == false){ //friction
            if(Math.abs(player.dx)<1) player.dx=0;
            if(Math.abs(player.dy)<1) player.dy=0;
            if (player.dx>0){
                player.dx-=.8;
            }
            if (player.dx<0){
                player.dx+=.8;
            }
            if (player.dy>0){
                player.dy-=.8;
            }
            if (player.dy<0){
                player.dy+=.8;
            }
        }
        //     if (player.dx>100||player.dy>100){player.dx=0;player.dy=0;player.xpos=200;player.ypos=300;}
    }

    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw the images
        // Signature: drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)

        g.drawImage(background,0,0,WIDTH,HEIGHT,null); //draws background
        if (!player.isTackled) { //draws the player alive pic while the player is alive
            g.drawImage(player.alivePic, (int)player.xpos, (int)player.ypos, player.width, player.height, null);
            if (distance < 150){ //changes the picture or the npc1 enemy while it is close to the player
                g.drawImage(npc1.HESGETTINGCLOSE, npc1.xpos, npc1.ypos, npc1.width, npc1.height, null);
            }
            else {
                g.drawImage(npc1.defaultPic, npc1.xpos, npc1.ypos, npc1.width, npc1.height, null); //draws teh normal npc1 picture
            }
        }

        else {
            g.drawImage(player.tackledPic, (int)player.xpos, (int)player.ypos, player.width, player.height, null); //draws teh gameover player and npc1 pic while the player is dead
            g.drawImage(npc1.tacklingPic, npc1.xpos, npc1.ypos, npc1.width, npc1.height, null);
        }
        g.drawImage(ball.dribblingimage, (int)ball.xpos, (int)ball.ypos, ball.width, ball.height, null);
        scoreboard.draw(g); //draws the scoreboard



        // Keep the code below at the end of render()
        g.dispose();
        bufferStrategy.show();
    }














    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv DON'T CHANGE! vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    // PSVM: This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever.
        while (true) {
         //   System.out.println(ball.playerPossession + " game start : " + ball.gamestart);
         ////   System.out.println("player poessesion;"+ball.playerPossession);
            player.playerTackled(ball);
            moveThings();  //move all the game objects
            npc1.iframeSetNPC(ball);
            player.iframeSet(ball, 40);
            collisionCheck(); //checks collisions for all the hitboxes
            render();  // paint the graphics
            kick();
            pause(10); // sleep for 10 ms
            ////System.out.println(player.iFrameCooldown+"fcount:"+player.iFrameCount);
        }
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time ) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    private Image getImage(String filename){
        return Toolkit.getDefaultToolkit().getImage(filename);
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();

        canvas.addKeyListener(keyInput); //dont rly under stand Keylistener yet
        System.out.println("DONE graphic setup");
    }

    public void keyPressed(KeyEvent e){ //makes it accelerate immidieatly when the key is pressed, kinda redundant but its the basic version of what they tell you to do in the tutorial so i kept it in for refernce
        int key = e.getKeyCode();//basically creates an integer variable to store the numerical ID of the key you press
        /*if(key==KeyEvent.VK_W){//
            player.dy-=.5 ;
        }
        if(key==KeyEvent.VK_S){
            player.dy+=.5 ;
        }
        if(key ==KeyEvent.VK_A){
            player.dx-=.5 ;
        }
        if(key ==KeyEvent.VK_D){
            player.dx+=.5 ;
        }*/
        if(key==KeyEvent.VK_R){ //if you press 'r' it will reset the player back to being alive for when you die, and also resets the score and player position
            //player.iFrames = true;
            //npc1.iFrames = true;
            ball.gamestart = true;
            iFrameCount = 0;
            npc1.iFrameCount = 0;
            sprintFrameCount = 0;
            tackleFrameCount = 0;
            scorePlayer = 0; //resets score
            scoreOpponent = 0;
            ball.xpos = 485;
            ball.ypos = 345;
            ball.playerPossession = false;
            ball.npcPossession = false;
            player.isTackled = false;
            ball.dx = 0;
            ball.dy = 0;
            ball.kickPower=1;
            player.xpos = 50;
            player.ypos = 345;
            npc1.xpos = 950;
            npc1.ypos = 345;
            scoreboard.text = "score:" + scorePlayer + " : " + scoreOpponent; //updates the text in the scoreboard

        }
        /*else {
            if (player.dx>0){
                player.dx-=.8;
            }
            if (player.dx<0){
                player.dx+=.8;
            }
            if (player.dy>0){
                player.dy-=.8;
            }
            if (player.dy<0){
                player.dy+=.8;
            }
        } */


    }

    public void keyReleased(KeyEvent e){
        moving = false; //when you release a key it sets moving to false and applies friction

    }

    public void npcDefensivePath(){ //path finding for the npc1

        double npcDistanceY =  ball.ypos - npc1.ypos; //finds the height of the triangle between the npc1 and player
        double npcDistanceX =  ball.xpos - npc1.xpos; //finds the length (aka base) of the triangle between the npc1 and player
        double angle = Math.atan2(npcDistanceY, npcDistanceX); //finds the angle of where the the player is to the npc1
        npc1.dx = Math.cos(angle)*npc1.speed; //these 2 lines always move the npc1 through the hypotenuse of said triangle i.e the shortest path
        npc1.dy = Math.sin(angle)*npc1.speed;
        npc1.move();
        npc1.hitbox = new Rectangle((int)npc1.xpos,(int)npc1.ypos,npc1.width,npc1.height);//updates npc1 hitbox after moving
        distance = Math.sqrt(npcDistanceX * npcDistanceX + npcDistanceY * npcDistanceY);//uses pythag. thry. to find the distance between player and npc1, and uses that to update the npc1's photo if its close
        //System.out.println(distance);
    }

    public void npcOffensivePath(){
        double ydistance2 =  leftGoal.ypos - npc1.ypos; //finds the height of the triangle between the npc1 and player
        double xdistance2 =  leftGoal.xpos - npc1.xpos; //finds the length (aka base) of the triangle between the npc1 and player
        double angle2 = Math.atan2(ydistance2, xdistance2); //finds the angle of where the the player is to the npc1
        npc1.dx = Math.cos(angle2)*npc1.speed; //these 2 lines always move the npc1 through the hypotenuse of said triangle i.e the shortest path
        npc1.dy = Math.sin(angle2)*npc1.speed;
        npc1.move();
        npc1.hitbox = new Rectangle((int)npc1.xpos,(int)npc1.ypos,npc1.width,npc1.height);//updates npc1 hitbox after moving
        distance = Math.sqrt(xdistance2 * xdistance2+ydistance2*ydistance2);//uses pythag. thry. to find the distance between player and npc1, and uses that to update the npc1's photo if its close
        //System.out.println(distance);
    }

    public void npcSprint(){

        if (distance < 150 /*&& player.xpos>600*/&& sprintFrameCount <60) {
            npc1.speed=4;
            sprintFrameCount++;
        }
        else if (sprintFrameCount >=60&& sprintFrameCount <360){
            npc1.speed=3;
            sprintFrameCount++;
        }
        else if (sprintFrameCount >=360){
            sprintFrameCount = 0;
        }
        else {npc1.speed = 3;
            //System.out.println(frameCount);
        }
        if (scorePlayer>10){
            npc1.speed*=.1*scorePlayer;
        }
        //else {speed=3;}
    }

   /* public void playerTackled(){
        if (player.isTackled&& tackleFrameCount <60){
            player.dx=0;
            player.dy=0;
            tackleFrameCount++;
            System.out.println(tackleFrameCount);

        }
        else if (tackleFrameCount ==60){
            player.isTackled = false;
            tackleFrameCount = 0;
            player.iFrames = true;
   //         System.out.println(frameCount);
        }
    }*/



    public void collisionCheck() {

        for (Enemy enemy: enemies) {
            if (!ball.playerPossession && player.hitbox.intersects(ball.hitbox)&&!player.iFrameCooldown) {
                player.iFrames = true;
                kickPower=0;
                //add the npc getting tackled\

            }

            if (player.hitbox.intersects(enemy.hitbox) && !player.iFrames) {
                player.isTackled = true; //kills the player if it hits the npc1
            }
            if (ball.hitbox.intersects(rightGoal.hitbox)) {
                scorePlayer += 1; //gives the player a point if its alive and touches the right goal
                scoreboard.text = "score:" + scorePlayer + " : " + scoreOpponent; //updates the text on teh scoreboard
                player.xpos = 200; //sets the player back to starting position
                ball.xpos = 485;
                ball.ypos = 345;
                ball.gamestart = true;
                ball.playerPossession = false;
                ball.npcPossession = false;
                player.isTackled = false;
                ball.kicked = false;
                ball.reset();
                ball.dx = 0;
                ball.dy = 0;
                player.xpos = 50;
                player.ypos = 345;
                npc1.xpos = 950;
                npc1.ypos = 345;
                return;
            }
            if (player.hitbox.intersects(ball.hitbox) && !enemy.iFrames && !player.isTackled) {
                ball.playerPossession = true;
                ball.kicked = false;
                ball.gamestart = false;
                ball.reset();
            }
            if (ball.hitbox.intersects(leftGoal.hitbox)) {
                scoreOpponent += 1; //gives the player a point if its alive and touches the right goal
                scoreboard.text = "score:" + scorePlayer + " : " + scoreOpponent; //updates the text on teh scoreboard
                ball.xpos = 485;
                ball.ypos = 345;
                ball.gamestart = true;
                ball.playerPossession = false;
                ball.npcPossession = false;
                player.isTackled = false;
                ball.kicked = false;
                ball.dx = 0;
                ball.dy = 0;
                player.xpos = 50;
                player.ypos = 345;
                npc1.xpos = 950;
                npc1.ypos = 345;
                return;

            }

            if (enemy.hitbox.intersects(ball.hitbox) && !player.iFrames && !enemy.iFrameCooldown) {
                ball.playerPossession = false;
                ball.npcPossession = true;
                ball.reset();
                npc1.iFrames = true;
                ball.gamestart = false;
                kickPower=0;
            }
            else if (enemy.hitbox.intersects(ball.hitbox) && player.iFrameCooldown && !enemy.iFrameCooldown){

            }

        }
        System.out.println(player.dx+" "+player.dy+" ");
    }


    public void ballKick(){ //IDEA: give diff players diff kick powers, and then add that would prob need a similar thing with the for(enemy enemes: enemies) things but well see
        ball.playerPossession = false;
        //player.iframeSet(ball,100); //currently doesnt work aka it doesnt do anything
        player.iFrames=true;
      //  ball.gamestart = true;
        double kickDistanceX= mouseX - ball.xpos;
        double kickDistanceY= mouseY - ball.ypos;
        double ballAngle = Math.atan2(kickDistanceY, kickDistanceX); //finds the angle of where the the player is to the npc1
        ball.kickedDX = Math.cos(ballAngle); //these 2 lines always move the npc1 through the hypotenuse of said triangle i.e the shortest path
        ball.kickedDY = Math.sin(ballAngle);
        System.out.println(ball.dx+" "+ ball.dy);


        //if its held for a while, increases a value that multiplies the velocity, linked to framerate
    }

    public void kick(){
        if(mouseHeld&&!ball.kicked){
            ball.kickPower+=.02;
        }
        else{
            ball.kickPower-=.02;
            if (ball.kickPower<=1){
                ball.kickPower=1;
                ball.kicked = false;
            }
        }
System.out.println(ball.kickPower);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        mouseHeld = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseHeld = false;
        if (!ball.kicked&&ball.playerPossession) {
            ballKick();
            ball.kickPower+=player.power;
            ball.kicked = true; //when the ball is kicked only them use kickpwoer
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /*public void iframeSetPlayer(){
        if (player.iFrames){
            iFrameCount++;
        }
        if (player.iFrames&& iFrameCount >30){
            player.iFrames = false;
            iFrameCount = 0;
        }
      //  System.out.println(ball.playerPossession);
    }
    public void iframeSetNPC() {
        if (npc1.iFrames&&!npc1.iFrameCooldown) {
            npc1.iFrameCount++;
        }
        if (npc1.iFrames && npc1.iFrameCount > 40) {
            npc1.iFrameCooldown=true;
            npc1.iFrameCount = 0;
            npc1.iFrames = false;
            return;
        }
        if (npc1.iFrameCooldown==true){
            if(!npc1.hitbox.intersects(ball.hitbox)){
                npc1.iFrameCooldown=false;
            }
        }
        if(!npc1.hitbox.intersects(ball.hitbox)){
            npc1.iFrameCooldown=false;
        }
    }*/

//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
}
