# Tank Trouble

## Team Members:
Joseph Shao, Eric Wang, Dennis Qin

## Project Summary:
Compared to the original tank game that we referenced to, we've expanded the game in several ways. We created multiple maps and entered new ones every time one player destroyed the other. We created a magazine system that allowed the tank to fire three rounds at a certain period of time. We also put a lot of effort into making the tanks collide with walls perfectly. Of course, there could have been more features to be implanted into the game, for example, generating random items in the map and giving tanks more complex functions. Also, the collision between tanks/balls and corners was too complicated, so we couldn't do it perfectly. However, our group felt that we had already used all the knowledge we learned in this class, and we are very pleased with the results we have delivered.  

## Game Guide:
After running the program, there will be a start screen. After clicking "Start Game,” there should be a maze map and two tanks. Each player will control a tank and destroy the other by firing shells. Each player can fire three shells in a row, and it takes some time to reload afterward. The shells can bounce on the wall and disappear after a few bounces. Each time a player destroys another, they respawn on a new map, and scores are recorded. The first to score five points wins. One can press back to menu to go back to the start interface.

|Control|Player 1 (green tank)|Player 2 (brown tank)|
|:-----:|:-------------------:|:-------------------:|
|Move forward|W|UP|
|Move backward|S|DOWN|
|Turn left|A|LEFT|
|Turn right|D|RIGHT|
|Shoot|Q|PERIOD|

## Challenges:
The most challenging part would be getting the tank to interact with the wall and the internal logic for handling shells smoothly. We discussed and experimented with several different approaches for both parts, and countless tests were conducted to make sure there were no apparent bugs. After writing these parts, our understanding of the methods, like "animate()" and "getElementAt()," has improved considerably.