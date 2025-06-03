package Collision;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import Enemy.enemy;
import Enemy.damage.attackEnemy;
import Player.player;

public class collision {

    enemy enemy;
    player player;

    boolean playerInvincibilityFrames;
    private long lastHitTime = 0;
    int count;

    public collision(enemy enemy, player player){
        this.enemy = enemy;
        this.player = player;
        count = 0;
    }

    public void update(){
        playerCollision();
        enemyCollision();
    }

    public void playerCollision(){
        for(attackEnemy elem: enemy.getAttackList()){
            for(Rectangle elem1: elem.getHitBoxes()){
                if (elem1.overlaps(player.getBounds())) {
                    long currentTime = System.currentTimeMillis();
                    if (!playerInvincibilityFrames && currentTime - lastHitTime >= 1000) {
                        playerInvincibilityFrames = true;
                        lastHitTime = currentTime;
                        player.takeDamage(10);
                    }
                }
            }
        }

        // Invincibility Frames
        if (playerInvincibilityFrames && System.currentTimeMillis() - lastHitTime >= 1000) {
            playerInvincibilityFrames = false;
        }
    }

    public void enemyCollision(){
        java.util.ArrayList<Sprite> projectilesToRemove = new java.util.ArrayList<>();

        for(Sprite elem: player.getShotsMap().keySet()){
            if(elem.getBoundingRectangle().overlaps(enemy.getSprite().getBoundingRectangle())){
                count++;
                    enemy.takeDamage(10);
                projectilesToRemove.add(elem);
            }
        }

        for (Sprite hit : projectilesToRemove) {
            player.getShotsMap().remove(hit);
        }
    }

}
