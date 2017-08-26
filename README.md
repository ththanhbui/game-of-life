# John Conway's Game of Life
A Java project to implement Object-oriented Programming

**John Conway** was an undergraduate at Cambridge who read Mathematics. He stayed on at Cambridge to study for a Ph.D. and afterwards as a Lecturer. Conway invented the **Game of Life** in 1970. You can play the Game of Life with physical game pieces (a set of stones from Go[5] are a good choice) but a computer simulation of the Game of Life allows for quicker experimentation.

The game board, or world, for the **Game of Life** is a two-dimensional grid of square cells. Each cell in the world is in one of two states, dead or alive. The world transitions through a set of discrete generations, starting from the initial state of the cells at time zero, which is determined by the human player. The rules of the game describe how to transition from generation t to generation t+1, and are as follows: 
 - a live cell with fewer than two neighbours dies (caused by underpopulation);
 - a live cell with two or three neighbours lives (representing a balanced population);
 - a live cell with with more than three neighbours dies (caused by overcrowding and starvation); and
 - a dead cell with exactly three live neighbours comes alive (colonisation).

The game calls for these rules to be applied simultaneously to all cells in order to produce the next generation 
	i.e. a cell that dies due to underpopulation could also play a role in colonisation of another cell. 

An example showing the application of the rules is shown in rule.png

To play the computer simulation of the Game of Life, first you need to install Java JDK and JRE:
- Open JavaSetup8u111.exe and follow the instruction to install JDK and JRE. 
- Go to https://java.com/en/download/help/ie_online_install.xml#download if you need help installing Java

Compile and Run GUILife.java to play:
 - Choose your pattern
 - Press 'Play' to simulate subsequent generations
 - Press '< Back' or 'Forward >'  to go to the generation you want.
 - Press 'Restart' to restart to Generation 0.

Enjoy!
