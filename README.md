# Zone chaser Game

## Capture Zones to earn points! 

This application is a game that can currently be played using
the **Swing library**. It is a game where you control the player character, *a red square*,
to these randomly generated *'Capture Zones'* where you must stay in the zones for a particular amount of time in order to take over them. This captures the zone 
and nets the player a **point!**

This game is designed for anyone who is interested in the gameplay or the future updates where new features are to be implemented such as **enemies to defeat, projectiles to avoid, and power-ups to make use of.**

I have always been interested in creating a video game from scratch after being intrigued by the design process of popular video games such as *Undertale* and *Celeste*.
This inspired me to attempt to create a fun game as a project for my class to gain some skills in Game Design.


As a player in this game, you would be able to:
- Experience the arbitrary number of Capture Zones (CaptureZone class) that will spawn which are added directly to the game (Game class) every 5 seconds
- Move around in 2 dimensions 
- Keep track of the number of zones you have captured with your score appearing on the top of the screen and also in the end screen
- See animations that make it clear when a zone is being captured and which one is being captured depicted by the zone glowing and blinking while the player's color cycles through a rainbow gradient.
- Have the Capture Zones disappear after they have been captured such that the player isn't confused by which zones are still to be captured. (removed from the list of CaptureZones present in the Game class)
- Save the game at any point to keep track of score, player position, and zone locations
- Load the game to a previous save state while playing, or reload a saved game from an earlier instance of the game


#Phase 4: Task 2:

Fri Apr 01 06:10:28 PDT 2022
CaptureZone added to game

Fri Apr 01 06:10:31 PDT 2022
CaptureZone added to game

Fri Apr 01 06:10:33 PDT 2022
CaptureZone captured and removed from game

Fri Apr 01 06:10:34 PDT 2022
CaptureZone added to game

Fri Apr 01 06:10:34 PDT 2022
CaptureZone captured and removed from game

Fri Apr 01 06:10:37 PDT 2022
CaptureZone added to game

Fri Apr 01 06:10:39 PDT 2022
CaptureZone added to game

#Phase 4: Task 3:

I believe the UML diagram for the structure of the project sufficiently conveys 
the structure of the actual project. 

If I had more time, however, I would make the following changes:
- Move the functionality of GameState and GameFrame classes to the ZoneChaserGame class and delete the two 
- move certain methods from the Game class to the Player, CaptureZone and Position Classes in order to reduce dependencies
