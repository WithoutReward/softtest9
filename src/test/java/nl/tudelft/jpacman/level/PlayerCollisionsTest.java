package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PlayerCollisionsTest {
    @Mock private PointCalculator pointCalculator;
    @Mock private Player player;
    @Mock private Ghost ghost;
    @Mock private Pellet pellet;

    private PlayerCollisions playerCollisions;

    @BeforeEach
    private void init(){
        pointCalculator = mock(PointCalculator.class);
        player = mock(Player.class);
        ghost = mock(Ghost.class);
        pellet = mock(Pellet.class);
        playerCollisions = new PlayerCollisions(pointCalculator);
    }

    @Test
    void test1() {
        Player player2 = mock(Player.class);
        playerCollisions.collide(player, player2);

        verify(pointCalculator, never()).collidedWithAGhost(any(), any());
        verify(player, never()).setAlive(anyBoolean());
        verify(player, never()).setKiller(any());
        verify(pointCalculator, never()).consumedAPellet(any(), any());
        verify(pellet, never()).leaveSquare();
    }

    @Test
    void test2() {
        playerCollisions.collide(player, ghost);

        verify(pointCalculator).collidedWithAGhost(any(), any());
        verify(player).setAlive(anyBoolean());
        verify(player).setKiller(any());
        verify(pointCalculator, never()).consumedAPellet(any(), any());
        verify(pellet, never()).leaveSquare();
    }

    @Test
    void test3() {
        playerCollisions.collide(player, pellet);

        verify(pointCalculator, never()).collidedWithAGhost(any(), any());
        verify(player, never()).setAlive(anyBoolean());
        verify(player, never()).setKiller(any());
        verify(pointCalculator).consumedAPellet(any(), any());
        verify(pellet).leaveSquare();
    }

    @Test
    void test4() {
        playerCollisions.collide(ghost, player);

        verify(pointCalculator).collidedWithAGhost(any(), any());
        verify(player).setAlive(anyBoolean());
        verify(player).setKiller(any());
        verify(pointCalculator, never()).consumedAPellet(any(), any());
        verify(pellet, never()).leaveSquare();
    }

    @Test
    void test5() {
        Ghost ghost2 = mock(Ghost.class);
        playerCollisions.collide(ghost, ghost2);
        playerCollisions.collide(ghost, pellet);

        verify(pointCalculator, never()).collidedWithAGhost(any(), any());
        verify(player, never()).setAlive(anyBoolean());
        verify(player, never()).setKiller(any());
        verify(pointCalculator, never()).consumedAPellet(any(), any());
        verify(pellet, never()).leaveSquare();
    }

    @Test
    void test6() {
        playerCollisions.collide(pellet, player);

        verify(pointCalculator, never()).collidedWithAGhost(any(), any());
        verify(player, never()).setAlive(anyBoolean());
        verify(player, never()).setKiller(any());
        verify(pointCalculator).consumedAPellet(any(), any());
        verify(pellet).leaveSquare();
    }

    @Test
    void test7() {
        Pellet pellet2 = mock(Pellet.class);
        playerCollisions.collide(pellet, ghost);
        playerCollisions.collide(pellet, pellet2);

        verify(pointCalculator, never()).collidedWithAGhost(any(), any());
        verify(player, never()).setAlive(anyBoolean());
        verify(player, never()).setKiller(any());
        verify(pointCalculator, never()).consumedAPellet(any(), any());
        verify(pellet, never()).leaveSquare();
    }
}
