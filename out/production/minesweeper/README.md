# Minesweeper
A replication of the Minesweeper game played in the CLI. Written in Java using JDK 11. 

## How To Play
### In IntelliJ
![img.png](img.png)

Run Play.java in the Editor to open the Run Interface. 

###On Command Line
First compile the project with the following command:
On Windows
```console
$ .\build.bat
```

On Mac
```console
$ ./build.bat
```

You can then run the game with the following command:

```console
$ .\runGame.bat
```

On Mac
```console
$ ./runGame.bat
```

### Gameplay
To play a round, enter 2 integers in the command line. For example:

```console
=======Enter Command:=======
0 0
```
This will select the Square at position 0,0 in the grid.
If this square is not a Mine, it will be revealed. If it has no mines around it, it will reveal the squares around it until
it runs into a square with a Mine around it. For example:
```console
=======Enter Command:=======
0 0

 0  0  0  0
 0  0  0  0
 1  2  2  1
 *  *  *  *
There are 0 mines around this square at 0, 0

```
If the square is a Mine, you will lose and the game will exit. For example:
```console
=======Enter Command:=======
0 3

 0  1  *  X
 0  1  *  *
 0  1  1  1
 0  0  0  0
=======BOOM! YOU LOST! MINE AT 0, 3=======
$ >
```
Once every non-Mine square has been revealed, you have won the game!

To exit the game, enter 'EXIT'.
```console
=======Enter Command:=======
EXIT
=======Goodbye=======
$ >
```
