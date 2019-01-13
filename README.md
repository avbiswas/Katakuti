# Katakuti - Multi grid Tic Tac Toe with AI Player

Google Play Link for this app: https://play.google.com/store/apps/details?id=com.zombieinawaterbottle.katakuti&hl=en_IN


The classic game of Tic Tac Toe with the following rules:
1) Games are to be played in a 5X5 or 7X7 grid. (The algorithm supports expansion of the grid to any nXn grid)
2) Like classic Tic Tac Toe, the first player gets a 'X' and the second player gets a 'O'
3) If a player connects 3 'X's (or 3 'O's), he gets 1 point. If he connects 4 'X's, he gets 2 points, and if he connects 5 'X's he gets 3 points, and so on...
4) Player can connect their squares horizontally, vertically as well as diagonally.
5) The game continues till the board is completely filled.
6) The center is super important in a game like this! Since the 1st player gets an extra move as well as the chance to take the central square, the second player is compensated with a free point at the start of the game. The game starts with 0 - 1 score.


Check the Katakuti/src/katakuti/CPUplayer.java file for the main code for the AI.
