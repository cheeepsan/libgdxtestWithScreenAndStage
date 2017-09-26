package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import pnpObject.PnpObjectProvider;

import java.util.Iterator;


public class MyGdxGame extends Game {
	private MainMenuScreen maineMenu;
	public AssetManager assetManager;
    public PnpObjectProvider provider;
	@Override
	public void create () {

		this.assetManager = new AssetManager();
        this.provider = new PnpObjectProvider(this.assetManager);
		this.loadTextures();

		while (!this.assetManager.update()) {
            System.out.println("Loading assets: " + this.assetManager.getProgress());
        }

        System.out.println("done loading");
        this.maineMenu = new MainMenuScreen(this);
        this.setScreen(this.maineMenu);

	}
    public void loadTextures() {
        Iterator<String> textureIterator = this.provider.getTexturePath().iterator();
        while(textureIterator.hasNext()) {
            this.assetManager.load(textureIterator.next(), Texture.class);
        }
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
