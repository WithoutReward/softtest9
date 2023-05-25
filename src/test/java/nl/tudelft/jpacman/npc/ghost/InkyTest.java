package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.PointCalculatorLoader;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InkyTest {
    private static PacManSprites pacManSprites;
    private static PlayerFactory playerFactory;
    private static GhostFactory ghostFactory;
    private static LevelFactory levelFactory;
    private static BoardFactory boardFactory;
    private static GhostMapParser ghostMapParser;

    @BeforeAll
    private static void init(){
        pacManSprites=new PacManSprites();
        playerFactory = new PlayerFactory(pacManSprites);
        ghostFactory = new GhostFactory(pacManSprites);
        levelFactory = new LevelFactory(pacManSprites,ghostFactory,new PointCalculatorLoader().load());
        boardFactory = new BoardFactory(pacManSprites);
        ghostMapParser = new GhostMapParser(levelFactory,boardFactory,ghostFactory);
    }

    @Test
    @DisplayName("玩家后方inky跟在Blinky后，靠近玩家")
    void test1() throws IOException {
        Level level = ghostMapParser.parseMap("/InkyTest/test1.txt");

        //设置Play初始方向并注册
        Player player=playerFactory.createPacMan();
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);

        //获取Inky
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertEquals(Optional.of(Direction.EAST), inky.nextAiMove());
    }

    @Test
    @DisplayName("玩家后方Blinky在inky后，无法选择")
    void test2() throws IOException {
        Level level = ghostMapParser.parseMap("/InkyTest/test2.txt");

        //设置Play初始方向并注册
        Player player=playerFactory.createPacMan();
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);

        //获取Inky
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertEquals(Optional.empty(), inky.nextAiMove());
    }

    @Test
    @DisplayName("玩家后方Blinky，玩家前方inky，远离玩家")
    void test3() throws IOException {
        Level level = ghostMapParser.parseMap("/InkyTest/test3.txt");

        //设置Play初始方向并注册
        Player player=playerFactory.createPacMan();
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);

        //获取Inky
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertEquals(Optional.of(Direction.WEST), inky.nextAiMove());
    }

    @Test
    @DisplayName("玩家后方inky，玩家前方Blinky，靠近玩家")
    void test4() throws IOException {
        Level level = ghostMapParser.parseMap("/InkyTest/test4.txt");

        //设置Play初始方向并注册
        Player player=playerFactory.createPacMan();
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);

        //获取Inky
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertEquals(Optional.of(Direction.EAST), inky.nextAiMove());
    }

    @Test
    @DisplayName("玩家前方inky、Blinky，无法选择")
    void test5() throws IOException {
        Level level = ghostMapParser.parseMap("/InkyTest/test5.txt");

        //设置Play初始方向并注册
        Player player=playerFactory.createPacMan();
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);

        //获取Inky
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertEquals(Optional.empty(), inky.nextAiMove());
    }

}
