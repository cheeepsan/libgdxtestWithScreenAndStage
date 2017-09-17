package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;


public class MyGdxGame extends Game {
	private MainMenuScreen maineMenu;
	
	@Override
	public void create () {
		this.maineMenu = new MainMenuScreen(this);
		setScreen(this.maineMenu);
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
