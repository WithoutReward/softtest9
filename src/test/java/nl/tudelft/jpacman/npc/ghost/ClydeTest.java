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

public class ClydeTest {
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
    @DisplayName("测试距离玩家八格，远离玩家")
    void test1() throws IOException {
        Level level = ghostMapParser.parseMap("/ClydeTest/test1.txt");

        //设置Play初始方向并注册
        Player player=playerFactory.createPacMan();
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);

        //获取Clyde
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

        assertEquals(Optional.of(Direction.EAST), clyde.nextAiMove());
    }

    @Test
    @DisplayName("测试距离玩家九格，靠近玩家")
    void test2() throws IOException {
        Level level = ghostMapParser.parseMap("/ClydeTest/test2.txt");

        //设置Play初始方向并注册
        Player player=playerFactory.createPacMan();
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);

        //获取Clyde
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

        assertEquals(Optional.of(Direction.WEST), clyde.nextAiMove());
    }

    @Test
    @DisplayName("测试被墙挡住")
    void test3() throws IOException {
        Level level = ghostMapParser.parseMap("/ClydeTest/test3.txt");

        //设置Play初始方向并注册
        Player player=playerFactory.createPacMan();
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);

        //获取Clyde
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertEquals(Optional.empty(), clyde.nextAiMove());
    }

    @Test
    @DisplayName("测试竖直水平都距离玩家二格，优先竖直方向远离玩家")
    void test4() throws IOException {
        Level level = ghostMapParser.parseMap("/ClydeTest/test4.txt");

        //设置Play初始方向并注册
        Player player=playerFactory.createPacMan();
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);

        //获取Clyde
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertEquals(Optional.of(Direction.SOUTH), clyde.nextAiMove());
    }
}
