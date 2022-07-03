# Game-Of-Life-Basic
<br/>

![image](https://user-images.githubusercontent.com/68727041/163131982-009eb94a-ec54-4041-8c8b-393ea3a7b6f2.png)

<br/>

## Purpose:
This application can be used to view and control a simplified configuration from the 0-player game, <br>
[**"Connway's Game-of-Life"**](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life), a simple example of basic [**Cellular Automata**](https://mathworld.wolfram.com/CellularAutomaton.html#:~:text=A%20cellular%20automaton%20is%20a,many%20time%20steps%20as%20desired)
<br/>
A cellular automaton is a discrete model of computation studied in automata theory. Cellular automata are also called cellular spaces, tessellation automata, homogeneous structures, cellular structures, tessellation structures, and iterative arrays.<br>
Game-of-Life is a 0-player game which demonstrates this automata concept.<br>
This Application is a Desktop GUI application and can run on any computer system with a JRE.
<br>

## Project Details:

The Game-of-Life board is generally infinite, with the bottom row wrapping around to the top row, and the right-most column wrapping around to the left-most column.<br>
Each cell will have two states- DEAD or ALIVE.
A cell switches states every generation, and calculates it's state based on the 8 adjacent cells around it. 
Users can define a basic initial configuration, choosing which cells should be alive initially, and which cells should be dead.<br> 
The default rules followed are:<br>
In the 8 adjacent cells surrounding the cell in question, let x be the number of cells which are in the ALIVE state: <br/>
 
   
| Condition        | Result           
| ------------- |:-------------:| 
| x<2 or x>5      | Cell in question dies | 
| x==3      | Cell in question dies      |   
| Any other condition | Cell retains previous state      | 

<br/>
Of course, these numbers can be modified by the user.
<br>
Using this well-defined ruleset, the state of each cell is calculated based off the states of it's **8 adjacent cells**,<br>
but none of the states are updated until the new state is calculated for each cell in the board. Once the new state is calculated for each<br>
cell in the board, the entire board is updated as a whole, and the <strong>generation count</strong> is updated.

<br>
Multiple generations later, the board either gains stability/all the cells die/infinite growth ocuurs due to looping conditions.<br/>

## How to Launch:
 
- Install the latest release of "Game-of-Life" from the releases section, it should be a .jar file.
- To run this file, you will need to have [JAVA 8/9](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html) installed on your system
- Once JAVA is set up on your system, launch the jar file with a double click!
Check the releases section to download the .jar file to run the app.



