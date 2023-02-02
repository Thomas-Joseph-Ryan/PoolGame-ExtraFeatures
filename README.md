# PoolGame

**Note 1:** The friction value in the config file is used as a multiplier in the
implementation. As the friction approaches 0, the friction decreases. As friction
approach 1, the friction increases. A high value of friction will make it 
impossible for a ball to move. Range of valid friction is: `0 < friction < 1`.

**Note 2:** While the forces applied to the cue ball is variable based on the
length of the line shown when dragging from the cue ball, there is a maximum cap
on the force.

**Note 3:** The center of the ball needs to be in the pocket for the code to 
consider it as "in the pocket" instead of its rectangular bound just intersecting
with the pocket's rectangular bound.

**Note 4:** If the CueStick appears off the screen, ie: CueBall is at the bottom of
the pool table, just extend the border of the window until you can see the CueStick
control.

**Note 5:** The hit behaviour is based off of the maroon ball on the cue stick not the 
tip of the cue stick.

### Level Selection
By pressing the respective level key while you are on that level, it will restart the
level. 

**Easy:** Default level, can also be selected by pressing 'E'.

**Normal:** Can be selected by pressing 'M'

**Hard:** Can be selected by pressing 'H'

**Note:** Sometimes when changing levels, you may need to resize the screen to get the
pool table to center again. After resizing slightly the table will snap to the center 
automatically

### Undo
To undo a shot press the 'U' button on the keyboard. This will revert the game state to the moment
just before the player hit the ball with the cue stick. This works if the cueball is sunk and the game
resets. However, the undo will not work between level changes. I.e. If you change from level 1 to 2 accidentally
then go back to 1.

### Cheat
To use the cheat, use the numbers on your keyboard.
The number associated with the ball is each balls score value.
e.g. red = 1, yellow = 2 ...
So by pressing 2, you will remove all the yellow balls.

## Where to find Design Patterns
### Observer
- Observer Package
- Items.CueStick
- Items.Ball
- Items.Timer
- Items.ScoreKeeper
- Game
### Memento
- Memento Package
- Items.Ball
- Items.Timer
- Items.ScoreKeeper
- Game
- App (for key binding)
### Prototype
- Prototype Package
- Strategy Package
- Items.Ball (save and restore state)
## Commands

* Run: `gradle run` to load default config from resources folder or 
`gradle run --args="'insert_config_file_path'"` to load custom config.

* Generate documentation:`gradle javadoc`


idea: Could change level by holding all of the games as an object in a hashmap. Then when changing
levels you just change the drawables in the root. This means it is not creating a new game each time you
swap levels, it is just changing what game is currently being drawn.