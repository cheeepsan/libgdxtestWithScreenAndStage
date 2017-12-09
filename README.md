# libgdxtestWithScreenAndStage

## This is a quick rundown on projects structure. 

This project is built using [libgdx game library](https://libgdx.badlogicgames.com/).

In the "core" folder are found all the main source files. Moreover "assets" folder containing textures and JSON files created for 
automatic initialisation of external resources. 

# Assets

## Json Files

Currently following JSON files are present:
* Tile
* Unit
* Item

JSON files are created to allow simple modification and automated creation of ingame objects. 

## Graphics

Tileset used in this project is taken from [Stone Soup](https://github.com/crawl/tiles)

# Sequence

After main entry point, which is located in file desktop/src/com/mygdx/game/desktop/DesktopLauncher.java, main execution continues
in file core/src/com/mygdx/game/MyGdxGame.java. 

## MyGdxGame.java 

MyGdxGame.java is responsible for downloading assets into memory and invocation of main loop, which is located in GameScreen.java. 

## GameScreen.java

GameScreen.java can be seen as main game loop, where all rendering, interrupting and object initialisation is happening. 

### Constructor of GameScreen.java

In contructor apart from camera and main temp objects, class GameStage is being instantiated. GameStage is a
top graphics layer, used for controlling and actual rendering of UI.

Map, object provider, sprite batches and UI objects are all being created in constructor of GameScreen.java. 

### Rendering 

Rendering is fairly simple - sprite batches are being filled with texture data and later drawn in sequence. Rendering is processed
in method render().

### Interaction

All interrupts are being processed in methods touchDown (for mouse), and keyDown (for keyborad). Game object selection is being
processed in touchDown method.

# PnpObject

PnpObject is a generic object that is used as a superclass for ingame classes. Main information, like name or texture path, can be found
in PnpObject

All PnpObject hierarchy can be found in package /core/src/pnpObject.

## PnpObjectProvider

PnpObjectProvider is used for object fetching and helper methods. Object Provider is instantiated early in the program to process Assets.
PnpObjectProvider contains methods for following operations:

* Fetching data from JSON files (Used by AssetsManager to prepare resource data) 
* Fetching texture and other data from JSON files
* Getting tiles from Grid 

# PnpMap

PnpMap is a container created to encapsulate classes PnpGrid and PnpTile. 

PnpGrid is the main 2d grid that contains coordinate information. Map generation is being controlled by the class PnpMapGeneration,
which is invoked through the grid class. 

PnpTile contains Tile information like type, texture and name. 

