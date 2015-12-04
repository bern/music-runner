 __      __         .__                                                   
/  \    /  \  ____  |  |    ____    ____    _____    ____                 
\   \/\/   /_/ __ \ |  |  _/ ___\  /  _ \  /     \ _/ __ \                
 \        / \  ___/ |  |__\  \___ (  <_> )|  Y Y  \\  ___/                
  \__/\  /   \___  >|____/ \___  > \____/ |__|_|  / \___  >               
       \/        \/            \/               \/      \/                
 		    __                                                                   
  		 _/  |_   ____                                                          
  		 \   __\ /  _ \                                                         
  		  |  |  (  <_> )                                                        
  		  |__|   \____/                                                         
                                                                          
   _____     ____ ___    _________ .___  _________                        
  /     \   |    |   \  /   _____/ |   | \_   ___ \                       
 /  \ /  \  |    |   /  \_____  \  |   | /    \  \/                       
/    Y    \ |    |  /   /        \ |   | \     \____                      
\____|__  / |______/   /_______  / |___|  \______  /                      
        \/                     \/                \/                       
__________   ____ ___   _______     _______    ___________ __________  ._.
\______   \ |    |   \  \      \    \      \   \_   _____/ \______   \ | |
 |       _/ |    |   /  /   |   \   /   |   \   |    __)_   |       _/ | |
 |    |   \ |    |  /  /    |    \ /    |    \  |        \  |    |   \  \|
 |____|_  / |______/   \____|__  / \____|__  / /_______  /  |____|_  /  __
        \/                     \/          \/          \/          \/   \/



Inside this project directory you will find all source code as well as a
Desktop executable and shortcut for Music Runner. 



===============================================

~~~~~~~~~~~~~ REQUIREMENTS ~~~~~~~~~~~~~~~~~~~~

===============================================

This program runs on Windows 7 or later.

You must have the latest version of Java to run Music Runner.

Additionally, if you wish to generate levels, you must have
Python installed in your PATH variable. Otherwise, the only 
levels you can play are the pre-generated ones.


===============================================

~~~~~~~~~ LAUNCHING MUSIC RUNNER ~~~~~~~~~~~~~~

===============================================

To run music runner, navigate to the project directory. Then, navigate to
game\android\assets\app. You may run the "Music Runner" shortcut to launch 
the game. You may move this shortcut anywhere (for instance, to the Desktop).



===============================================

~~~~~~~~~~~~~~~~ SCREENS ~~~~~~~~~~~~~~~~~~~~~~

===============================================

1. Main Menu

  - After loading, you will see the Main Menu Screen. You will have 3 options:
	1. Play song
	2. Song Manager
	3. Exit

  1. Play Song
	- Any songs you have loaded will appear here. Initially, this will only contain
	demo songs. Upload your own for more!

  2. Song Manager
	- This lets you add or remove songs. To add a song, click "Add". Enter
	the name of a song you wish to add in your "songs" directory (see
	"Songs" section below). Click OK. The name will appear in the list
	as a red entry, until it finishes, where it will turn white.
	- To remove a song, simply click it and then "remove".

  3. Exit
	-Exits the application safely.


===============================================

~~~~~~~~~~~~~~~~~ SONGS ~~~~~~~~~~~~~~~~~~~~~~~

===============================================

You'll notice that only a few songs are initially available: however, you can add your own!

Inside the project directory, navigate to game/android/assets/music. This is where all the song files
are located! Unfortunately, the only currently supported audio type is .wav files. However, you can
convert your mp3 files into playable .wav files for free via the following link:

http://audio.online-convert.com/convert-to-wav

Place your .wav files in here, and then in the application, you can go to the Song Manager Screen
and simply enter the name of the .wav file (with or without the ".wav" at the end) and your song
will be loaded!


===============================================

~~~~~~~~~~~~~~~~ CONTROLS ~~~~~~~~~~~~~~~~~~~~~

===============================================

Your character will always be running to the right. The following controls
are supported:

1) Jump: SPACE bar, the W key, or the up arrow on the bottom right side 
of the screen.

2) Fire: Simply click the region of the game screen where you wish to fire and
a projectile will fire. Holding down will fire rapid fire shots.

3) Bounce: Before landing on an enemy and stomping them, press 'Jump' again
to get an extra boost! Don't get stuck bouncing on small enemies and run into
a bigger one!

4) Reload: the "R" key. Note that it takes a few seconds to reload. If your ammo
reaches zero, you will automatically reload.
  


===============================================

~~~~~~~~~~~~~~~~ GAMEPLAY ~~~~~~~~~~~~~~~~~~~~~

===============================================

Your character will run to the right as the song begins to play. You will notice
a variety of enemies come into view and arrive at your location as musical
features occur. If you run into an enemy on the side, you will lose health.

To kill an enemy:
	1. Stomp on its head by jumping
	2. Shoot it

Collect headphones in the air for extra points and to restore life points!

When you successfully complete a level, you will reach the finish flag and win the game!

If you run out of lives before reaching the finish, you will lose the game.

Either way, you may return to the main menu to continue playing. 


===============================================

~~~~~~~~~~~~~~~~ STRATEGY ~~~~~~~~~~~~~~~~~~~~~

===============================================

Exclusively jumping or shooting will NOT allow you to survive an entire round for most songs - 
you must be smart and combine your efforts! You have a limited ammount of ammo, so use it wisely
and remember to reload! Try jumping along multiple similar sized enemies. When a larger one approaches,
try bouncing to increase your jump height, or begin shooting them so you may safely land. Stomp 
chaining is a good strategy because it simultaneously picks up headphones, restoring precious
life points. Good luck!