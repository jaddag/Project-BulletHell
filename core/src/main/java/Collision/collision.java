package Collision;

import com.badlogic.gdx.graphics.g2d.Sprite;

import Enemy.enemy;
import Enemy.damage.attack;
import Player.player;

public class collision {

    enemy enemy;
    player player;

    boolean playerInvincibilityFrames;
    private long lastHitTime = 0;

    public collision(enemy enemy, player player){
        this.enemy = enemy;
        this.player = player;
    }

    public void update(){
        for(attack elem: enemy.getAttackList()){
            for(Sprite elem1: elem.getRings()){
                if (elem1.getBoundingRectangle().overlaps(player.getSprite().getBoundingRectangle())) {
                    long currentTime = System.currentTimeMillis();
                    if (!playerInvincibilityFrames && currentTime - lastHitTime >= 1000) {
                        playerInvincibilityFrames = true;
                        lastHitTime = currentTime;
                        player.takeDamage(10);
                    }
                }
            }
        }

        // Reset invincibility after 1 second
        if (playerInvincibilityFrames && System.currentTimeMillis() - lastHitTime >= 1000) {
            playerInvincibilityFrames = false;
        }
    }

}
