package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class MapParserTest {

    @Mock private static BoardFactory mockedBoardFactory;
    @Mock private static LevelFactory mockedLevelFactory;

    private static MapParser mapParser;

    @BeforeAll
    private static void init(){
        mockedBoardFactory = mock(BoardFactory.class);
        mockedLevelFactory = mock(LevelFactory.class);
        mapParser = new MapParser(mockedLevelFactory, mockedBoardFactory);

        when(mockedBoardFactory.createBoard(any())).thenReturn(mock(Board.class));
        when(mockedLevelFactory.createPellet()).thenReturn(mock(Pellet.class));
        when(mockedLevelFactory.createGhost()).thenReturn(mock(Ghost.class));
        when(mockedLevelFactory.createLevel(any(),any(),any())).thenReturn(mock(Level.class));
    }

    @Test
    @DisplayName("测试交互正确性")
    void test1() throws IOException {
        mapParser.parseMap("/MapParserTest/test1.txt");

        //执行1次creatBoard
        verify(mockedBoardFactory).createBoard(any());
        //执行16次creatWall
        verify(mockedBoardFactory, times(16)).createWall();
        //执行5次creatGround
        verify(mockedBoardFactory, times(5)).createGround();

        //执行1次creatLevel
        verify(mockedLevelFactory).createLevel(any(),any(),any());
        //执行1次createPellet
        verify(mockedLevelFactory).createPellet();
        //执行1次createGhost
        verify(mockedLevelFactory).createGhost();
    }

    @Test
    @DisplayName("强制抛出异常")
    void test2() throws IOException {
        when(mockedLevelFactory.createGhost()).thenThrow(new RuntimeException("不能有两个以上的鬼"));

        assertThatThrownBy(()->{
            mapParser.parseMap("/MapParserTest/test2.txt");
        }).isInstanceOf(RuntimeException.class).hasMessage("不能有两个以上的鬼");
    }

    @Test
    @DisplayName("找不到文件")
    void test3(){
        assertThatThrownBy(()->{
            mapParser.parseMap("/MapParserTest/test3.txt");
        }).isInstanceOf(PacmanConfigurationException.class).hasMessage("Could not get resource for: /MapParserTest/test3.txt");
    }

    @Test
    @DisplayName("文件为空")
    void test4(){
        assertThatThrownBy(()->{
            mapParser.parseMap("/MapParserTest/test4.txt");
        }).isInstanceOf(PacmanConfigurationException.class).hasMessage("Input text must consist of at least 1 row.");
    }

    @Test
    @DisplayName("第一行为空")
    void test5(){
        assertThatThrownBy(()->{
            mapParser.parseMap("/MapParserTest/test5.txt");
        }).isInstanceOf(PacmanConfigurationException.class).hasMessage("Input text lines cannot be empty.");
    }

    @Test
    @DisplayName("其他行与第一行长度不等")
    void test6(){
        assertThatThrownBy(()->{
            mapParser.parseMap("/MapParserTest/test6.txt");
        }).isInstanceOf(PacmanConfigurationException.class).hasMessage("Input text lines are not of equal width.");
    }

}
