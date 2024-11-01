# 2D-Game
Game made in Intelijj IDEA using JavaFx technology 
Game is separated in 2 separate windows 
First window is simple gui ( graphical user interface) to select diffuculites , cannon size and field shape select 
Second window is game consisent of a pleyer ( hexagon spawned in the middle of the field) and enemies spawned on the outside of the shape in circular way
Player has option to shoot normal bullets and strong bullets , pressing left click on mouse shoots normal , right click shoots strong bullets . Number of strong bullets is maxed at 4 , when it reaches 0 , player cannot shoot strong bullets anymore . 
Player can collect blue hexagon ( shield) , heart ( health points )  and coins ( strong bullet ammo ) . Heart restores 1 health poin , coin restores 1 strong bullet and shield blocks the bullet from the enemy for 5 seconds 
Enemies randomly shoot at player and if they hit player health points reduce by 1 , if it reaches 0 its game over .
Player shoot where the gun of player is pointing ( gun is controlled by mouse movement) , normal bullets reduce enemy hp by 1 , strong bullets by 2 
While player is shooting , periodacally enemies get a wall in front of them that can block player bullet for a few random seconds 
Game is over once you elimanate all enemies or player health drop to 0
