public class VISION {
}

/*
something like either a top down  would probably be best,
where you can click the mouse to kick teh ball farther,
or use arrow keys to dribble/manipulate the ball around the player,
then have some skill moves, maybe that need sspecial key combinations, that will manipulate the
ball interestly or at least give the player iframes,
then teh defender will have different actions it can take based on the dribbling,
e.g if key = rightarrow && key = down arrow, boolean ball down = true, (moves the ball bellow the player),
and ball right = true, and then the defender wil have code like if ball down = true, 1/5 chance to slide tackle,


there will also be fouls and set pieces, look at soccer mobile games for example, ball moves aross the screen
then u have to click a part of it and where you click it will affect its spin/curve

then a field system, where the whole field isnt on the screen, but the game keeps track of every player
/entity relative to the player, so when the player or ball goes off screen, or the ball gets pased to
someone else, the game will reposition the focus of the screen so that the new point of interest
is at the middle, and it re constructs everythign based on how far that thing was to the player
(also use trig for angles relative to the player)

might be an easier way to do above, if it just uses xpos and ypos as normal
(game still records x and ypos of people off the screen, but then when the screen repositions
 it'll reposition everyones x and ypos accordingly)


 */
