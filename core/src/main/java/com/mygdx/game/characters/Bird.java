package com.mygdx.game.characters;

import static com.mygdx.game.MyGdxGame.SCR_HEIGHT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Bird {

    int x, y;
    int width, height;

    int speed;
    int jumpHeight;
    final int maxHeightOfJump = 120;
    boolean jump = true;

    int frameCounter;
    Texture[] framesArray;

    Sound jumpSound;

    public Bird(int x, int y, int speed, int width, int height) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
        frameCounter = 0;

        framesArray = new Texture[]{
            new Texture("birdTiles/bird0.png"),
            new Texture("birdTiles/bird1.png"),
            new Texture("birdTiles/bird2.png"),
            new Texture("birdTiles/bird1.png"),
        };

        jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
    }

    public void setY(int y) {
        this.y = y;
    }

    public void onClick() {
        jump = true;
        jumpHeight = maxHeightOfJump + y;

        // Воспроизведение звука прыжка
        // 0.5f - громкость (от 0.0 до 1.0)
        if (jumpSound != null) {
            jumpSound.play(0.5f);
        }
    }

    public void fly() {
        if (y >= jumpHeight) {
            jump = false;
        }

        if (jump) {
            y += speed;
        } else {
            y -= speed;
        }
    }

    public boolean isInField() {
        if (y + height < 0) return false;
        if (y > SCR_HEIGHT) return false;
        return true;
    }

    public void draw(Batch batch) {
        int frameMultiplier = 10;
        batch.draw(framesArray[frameCounter / frameMultiplier], x, y, width, height);
        if (frameCounter++ == framesArray.length * frameMultiplier - 1) frameCounter = 0;
    }

    public void dispose() {
        for (Texture texture : framesArray) {
            texture.dispose();
        }

        if (jumpSound != null) {
            jumpSound.dispose();
        }
    }

}
