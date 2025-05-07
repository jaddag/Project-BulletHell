import ArrayListDraw.planet;
import Background.planetGen;
import Player.player;

private void checkCollison(){
    for(planet earth : earthArrayList) {
        if (player.getBounds().overlaps(earth.getBounds())) {
            dropSound.play();
        }
    }

    for (planet jupiter : jupiterArrayList) {
        if (player.getBounds().overlaps(jupiter.getBounds())){
            dropSound.play();

        }

    }

    for (planet saturn : saturnArrayList) {
        if (player.getBounds().overlaps(saturn.getBounds())) {
            dropSound.play;
        }
    }

    for(planet sun : sunArrayList) {
        if(player.getBounds().overlaps(sun.getBounds())){
            dropSound.play;
        }
    }
}
