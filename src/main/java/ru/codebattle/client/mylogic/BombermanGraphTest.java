package ru.codebattle.client.mylogic;

import org.junit.Test;
import ru.codebattle.client.api.BoardElement;
import ru.codebattle.client.api.BoardPoint;
import ru.codebattle.client.api.GameBoard;

import java.util.Deque;
import java.util.Set;

import static org.junit.Assert.*;
import static ru.codebattle.client.api.BoardElement.*;

public class BombermanGraphTest {

	GameBoard gameBoard = new GameBoard("☼☼☼☼☼☼☼☼☼☼\n" +
																									  "☼    23  ☼\n" +
																									  "☼ ☼    & ☼\n" +
																									  "☼☼☺ ♥    ☼\n" +
																									  "☼    ☼   ☼\n" +
																									  "☼    1 ☼ ☼\n" +
																									  "☼   ☼  ♠ ☼\n" +
																									  "☼♥4      ☼\n" +
																									  "☼ 3  2 ♠ ☼\n" +
																									  "☼☼☼☼☼☼☼☼☼☼\n");

	@Test
	public void bfs() {
		BombermanGraph bombermanGraph = new BombermanGraph(gameBoard);
		Deque<BombermanVertex> bfs = bombermanGraph.bfs(new BoardPoint(2, 3), MEAT_CHOPPER);
		System.out.println(bfs);
	}
}