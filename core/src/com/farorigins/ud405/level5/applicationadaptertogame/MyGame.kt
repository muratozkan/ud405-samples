package com.farorigins.ud405.level5.applicationadaptertogame

import com.badlogic.gdx.Game

/*
* First complete all the TODOs in MyScreen, then:
*
* Delete the whole body of MyGame
*
* Declare that MyGame extends Game (com.badlogic.gdx.Game)
*
* Hit Ctrl-i to insert the create() method
*
* In create(), call setScreen() with a new instance of MyScreen()
*
* Run what we've created.
*
* Everything should still be working, but now the drawing is happening in MyScreen. That means it
* would be easy to swap out MyScreen for another screen containing a game world, a menu, or
* whatever. Nice work!
*/
class MyGame : Game() {

    override fun create() {
        setScreen(MyScreen())
    }

}