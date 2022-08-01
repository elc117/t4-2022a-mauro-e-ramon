package com.ninja.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



public class Hud implements Disposable{

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Mario score/time Tracking Variables
    private Integer worldTimer;
    private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;
    private int detected;

    //Scene2D widgets
    private Label countdownLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label detectedLabel;

    public Hud(SpriteBatch sb, PlayScreen ps){
        //define our tracking variables
        worldTimer = 0;
        timeCount = 0;
        detected = ps.detected;


        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(NinjaOps.V_LAR, NinjaOps.V_ALT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TEMPO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("ALPHA", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        detectedLabel = new Label(String.format("%01d", detected), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(timeLabel).expandX().padTop(10);
        //add a second row to our table
        table.row();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();
        table.row();
        table.add(detectedLabel).expandX();

        //add our table to the stage
        stage.addActor(table);

    }

    public void update(float dt, int detected){
        updateDetection(detected);
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer++;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public void updateDetection(int detected){
        this.detected = detected;
        detectedLabel.setText(String.format("%01d", this.detected));
    }

    @Override
    public void dispose() { stage.dispose(); }

    public boolean isTimeUp() { return timeUp; }
}