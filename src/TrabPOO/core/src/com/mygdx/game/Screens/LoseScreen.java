package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Builder;
import com.mygdx.game.Mapa;

public class LoseScreen implements Screen {
    private final Renderizador game;
    public Mapa mapa;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    private Vector3 touchPosition;
    private Rectangle botaoMenu, botaoJogarDenovo;
    private Builder builder;
    private Renderizador newGame;

    public LoseScreen(final Renderizador game, Mapa mapa) {
        this.game = game;
        this.mapa = mapa;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        builder = new Builder(); //Reinicia o jogo
        newGame = new Renderizador();

        //Organiza a câmera e o TiledMap
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();
        tiledMap = new TmxMapLoader().load("Derrota.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        touchPosition = new Vector3();
        botaoMenu = ((RectangleMapObject) tiledMap.getLayers().get("botoes").getObjects().get("Menu")).getRectangle();
        botaoJogarDenovo = ((RectangleMapObject) tiledMap.getLayers().get("botoes").getObjects().get("Jogar")).getRectangle();
    }

    @Override
    public void render(float delta) {
        //Renderiza o menu inicial
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        //Verifica se os botões são clicados e faz a ação correta
        if(Gdx.input.justTouched()){
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            this.camera.unproject(touchPosition);
        }
        if(botaoMenu.contains(touchPosition.x, touchPosition.y))
            game.setScreen(new MainMenuScreen(game, builder.getMapa()));
        if(botaoJogarDenovo.contains(touchPosition.x,touchPosition.y))
            game.setScreen(new GameScreen(game, builder.getMapa(), false));
    }

    @Override
    public void show() { }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }
}
