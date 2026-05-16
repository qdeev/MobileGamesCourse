package ru.samsung.gamestudio;
import com.badlogic.gdx.utils.TimeUtils;
import ru.samsung.gamestudio.managers.MemoryManager;

import java.util.ArrayList;


public class GameSession {

    public GameState state;
    long nextTrashSpawnTime;
    long sessionStartTime;
    long pauseStartTime;
    private int score;
    private int combo;
    private long lastDestructionTime;
    private static final long COMBO_TIMEOUT = 3000;
    int destructedTrashNumber;

    public GameSession() {
    }

    public void startGame() {
        state = GameState.PLAYING;
        score = 0;
        destructedTrashNumber = 0;
        sessionStartTime = TimeUtils.millis();
        nextTrashSpawnTime = sessionStartTime + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                * getTrashPeriodCoolDown());
    }

    public void pauseGame() {
        state = GameState.PAUSED;
        pauseStartTime = TimeUtils.millis();
    }

    public void resumeGame() {
        state = GameState.PLAYING;
        sessionStartTime += TimeUtils.millis() - pauseStartTime;
    }

    public void endGame() {
        updateScore();
        state = GameState.ENDED;
        ArrayList<Integer> recordsTable = MemoryManager.loadRecordsTable();
        if (recordsTable == null) {
            recordsTable = new ArrayList<>();
        }
        int foundIdx = 0;
        for (; foundIdx < recordsTable.size(); foundIdx++) {
            if (recordsTable.get(foundIdx) < getScore()) break;
        }
        recordsTable.add(foundIdx, getScore());
        MemoryManager.saveTableOfRecords(recordsTable);
    }

    public void destructionRegistration() {
        destructedTrashNumber += 1;
    }

    public void updateScore() {
        int timeScore = (int) (TimeUtils.millis() - sessionStartTime) / 100;
        int destructionScore = destructedTrashNumber * 100;
        int bonusScore = getBonusPoints();

        score = timeScore + destructionScore + bonusScore;
    }

    public int getScore() {
        return score;
    }

    public boolean shouldSpawnTrash() {
        if (nextTrashSpawnTime <= TimeUtils.millis()) {
            nextTrashSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                    * getTrashPeriodCoolDown());
            return true;
        }
        return false;
    }

    private float getTrashPeriodCoolDown() {
        return (float) Math.exp(-0.001 * (TimeUtils.millis() - sessionStartTime + 1) / 1000);
    }

    public void addCombo() {
        long currentTime = TimeUtils.millis();
        if (currentTime - lastDestructionTime < COMBO_TIMEOUT) {
            combo++;
        } else {
            combo = 1;
        }
        lastDestructionTime = currentTime;
    }

    public void resetCombo() {
        combo = 0;
    }

    public int getCombo() {
        return combo;
    }

    public int getComboMultiplier() {
        if (combo < 3) return 1;
        if (combo < 5) return 2;
        if (combo < 10) return 3;
        return 5;
    }

    public int getBonusPoints() {
        if (combo >= 3) {
            return combo * 50 * getComboMultiplier();
        }
        return 0;
    }
}
