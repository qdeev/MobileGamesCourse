package com.mygdx.game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class TextButton {

    BitmapFont font;

    String text;
    Texture texture;

    int x, y;
    int textX, textY;
    int buttonWidth, buttonHeight;
    int textWidth, textHeight;

    float baseScale = 5f;
    float pulseSpeed = 3f;
    float pulseAmount = 0.3f;
    long startTime;

    public TextButton(int x, int y, String text) {
        this.text = text;
        this.x = x;
        this.y = y;

        font = new BitmapFont();
        font.getData().scale(baseScale);
        font.setColor(Color.WHITE);

        GlyphLayout gl = new GlyphLayout(font, text);
        textWidth = (int) gl.width;
        textHeight = (int) gl.height;

        texture = new Texture("button_bg.png");
        buttonWidth = texture.getWidth();
        buttonHeight = texture.getHeight();

        textX = x + (buttonWidth - textWidth) / 2;
        textY = y + (buttonHeight + textHeight) / 2;

        startTime = TimeUtils.millis();
    }

    public boolean isHit(int tx, int ty) {
        System.out.println(tx + " - " + ty);
        System.out.println(x + " - " + y);
        return tx >= x && tx <= x + buttonWidth
            && ty >= y && ty <= y + buttonHeight;
    }

    public void draw(Batch batch) {
        float currentTime = (TimeUtils.millis() - startTime) / 1000f; // время в секундах
        float currentScale = baseScale + MathUtils.sin(currentTime * pulseSpeed) * pulseAmount;

        font.getData().setScale(currentScale);

        GlyphLayout gl = new GlyphLayout(font, text);
        int currentTextWidth = (int) gl.width;
        int currentTextHeight = (int) gl.height;
        int currentTextX = x + (buttonWidth - currentTextWidth) / 2;
        int currentTextY = y + (buttonHeight + currentTextHeight) / 2;

        batch.draw(texture, x, y, buttonWidth, buttonHeight);
        font.draw(batch, text, currentTextX, currentTextY);

        font.getData().setScale(baseScale);
    }

    public void dispose() {
        texture.dispose();
        font.dispose();
    }
}
