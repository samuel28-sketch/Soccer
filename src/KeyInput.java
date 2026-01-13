import java.awt.event.KeyAdapter; //imports even more stuff
import java.awt.event.KeyEvent; //imports keyboard stuff
import java.awt.event.KeyListener;
import java.lang.reflect.Array; //imports array stuff
import java.util.ArrayList;
import java.util.HashSet; //imports hashset stuff
import java.util.Set;

//look at YT tutorial by Rea2lTutsGML
public class KeyInput extends KeyAdapter {//googled that it basically becomes a subclass i.e extension of the alr existing java class Keyadapter, basically it lets this class use all of keyadapters stuff
    BasicGameApp game; //
    Set<Integer> pressedKeys; //makes a set of integers, (which is better than an array because its faster and cant hold duplicates, a problem i ran into was that if you like released a key too quick or multiple at a time it wouldnt remove it from the keyspressed list so player would accelerate infinity)
    //   ArrayList<Integer> upKeys;


    public KeyInput(BasicGameApp game){
        this.game=game; //sets BasicGameApp game on line 11 to BasicGameApp game in this constructor's parameters
        pressedKeys = new HashSet<>();//makes a hashset
        //       upKeys = new ArrayList<>();
    }

    public void keyPressed(KeyEvent e){ //method for when a key is pressed using keyadapter stuff to identify what key is pressed (numerical ID)
        pressedKeys.add(e.getKeyCode()); //adds the pressed key into the set
        game.keyPressed(e); //basically runs the keyPressed method in basicGameApp instead of here in the KeyInput class
    }

    public void keyReleased(KeyEvent e){//same thing as KeyPressed but for when the key is released
        game.keyReleased(e);//runs keyreleased in the basicgame class
        pressedKeys.remove((Integer)e.getKeyCode());//removes the ID of the key that was pressed from the set, when its released

        //       upKeys.add(e.getKeyCode());
    }

    public boolean isKeyDown (int keyCode){ //googled, makes a boolean that returns a 'true' when you give it the int ID of a key thats in the pressedkeys set, tl;dr checks whether that key is being pressed, just a more complicated version of the moving boolean in basicgame
        return pressedKeys.contains(keyCode);
    }
    /*public boolean anyKeyDown(){
        return pressedKeys.isEmpty();
    }*/
    //   public boolean isKeyUp (int keyCode){ //googled
    //       return upKeys.contains(keyCode);
    //  }



}
