package com.farorigins.ud405.level7

import com.badlogic.gdx.Game

import com.farorigins.ud405.level7.removestaleicicles.IciclesScreen

class IciclesGame : Game() {
    // call setScreen() with a new IciclesScreen()
    override fun create() {
        setScreen(IciclesScreen())
    }
}
