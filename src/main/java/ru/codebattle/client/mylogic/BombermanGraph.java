package ru.codebattle.client.mylogic;

import lombok.Data;
import ru.codebattle.client.api.BoardElement;
import ru.codebattle.client.api.BoardPoint;
import ru.codebattle.client.api.GameBoard;

import java.util.*;

import static ru.codebattle.client.api.BoardElement.*;

@Data
public class BombermanGraph {
	private int size;
	private Map<BombermanVertex, Set<BombermanVertex>> adjVertices;
	public static final BoardElement[] ME_ENEMIES_AND_NONE = {BOMB_BOMBERMAN, BOMBERMAN, NONE, OTHER_BOMBERMAN, OTHER_BOMB_BOMBERMAN, MEAT_CHOPPER};
	private GameBoard gameBoard;

	public BombermanGraph(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
		size = gameBoard.size();
		adjVertices = new HashMap<>();
		for (int x = 0; x <= size; x++) {
			for (int y = 0; y <= size; y++) {
				BoardPoint boardPoint = new BoardPoint(x, y);
				BombermanVertex vertex = new BombermanVertex(boardPoint, Surviving.getDangerousness(boardPoint, gameBoard));
				adjVertices.put(vertex, new HashSet<>());
				if (gameBoard.hasElementAt(boardPoint, ME_ENEMIES_AND_NONE)) {
					BoardPoint topBP = new BoardPoint(x, y - 1);
					BoardPoint rightBP = new BoardPoint(x + 1, y);
					BoardPoint bottomBP = new BoardPoint(x, y + 1);
					BoardPoint leftBP = new BoardPoint(x - 1, y);

					if (gameBoard.hasElementAt(topBP, ME_ENEMIES_AND_NONE)) {
						adjVertices.get(vertex).add(new BombermanVertex(topBP, Surviving.getDangerousness(boardPoint, gameBoard)));
					}

					if (gameBoard.hasElementAt(rightBP, ME_ENEMIES_AND_NONE)) {
						adjVertices.get(vertex).add(new BombermanVertex(rightBP, Surviving.getDangerousness(boardPoint, gameBoard)));
					}

					if (gameBoard.hasElementAt(bottomBP, ME_ENEMIES_AND_NONE)) {
						adjVertices.get(vertex).add(new BombermanVertex(bottomBP, Surviving.getDangerousness(boardPoint, gameBoard)));
					}

					if (gameBoard.hasElementAt(leftBP, ME_ENEMIES_AND_NONE)) {
						adjVertices.get(vertex).add(new BombermanVertex(leftBP, Surviving.getDangerousness(boardPoint, gameBoard)));
					}
				}
			}
		}
	}

	public Deque<BombermanVertex> bfs(BoardPoint boardPoint, BoardElement... targets) {
		List<BoardElement> targetList = Arrays.asList(targets);
		Deque<BombermanVertex> deque = new LinkedList<>();
		Deque<BombermanVertex> toReturn = new LinkedList<>();
		BombermanVertex root = new BombermanVertex(boardPoint, Surviving.getDangerousness(boardPoint, gameBoard));
		Deque<BombermanVertex> visited = new LinkedList<>();
		deque.add(root);
		visited.add(root);

		while (!deque.isEmpty()) {
			BombermanVertex vertex = deque.poll();
			Set<BombermanVertex> bombermanVertices = adjVertices.get(vertex);
			if (bombermanVertices != null) {
				for (BombermanVertex v : bombermanVertices) {
					if (!visited.contains(v)) {
						visited.addLast(v);
						deque.push(v);
						if (targetList.contains(gameBoard.getElementAt(v.getBoardPoint()))) {
							toReturn.push(visited.pop());
							while (!visited.isEmpty()) {
								BombermanVertex peek = toReturn.peek();
								Set<BombermanVertex> popAdj = adjVertices.get(peek);
								if (popAdj.contains(visited.peek())) {
									toReturn.push(visited.pop());
								} else {
									visited.pop();
								}
							}


							toReturn.pollLast();

							return toReturn;
						}
					}
				}
			}
		}

		return toReturn;
	}

	private Deque<BombermanVertex> getPath(Deque<BombermanVertex> deque, BombermanVertex root) {
		Deque<BombermanVertex> path = new LinkedList<>();
		BombermanVertex pop = deque.pop();
		path.push(pop);
		Set<BombermanVertex> bombermanVertices = adjVertices.get(pop);
		for (BombermanVertex bombermanVertex : bombermanVertices) {
			if (deque.contains(bombermanVertex)) {
				path.push(bombermanVertex);

			}
		}

		return null;
	}
}

