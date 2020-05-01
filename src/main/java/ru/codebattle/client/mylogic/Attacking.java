package ru.codebattle.client.mylogic;

import ru.codebattle.client.api.BoardElement;
import ru.codebattle.client.api.BoardPoint;
import ru.codebattle.client.api.Direction;
import ru.codebattle.client.api.GameBoard;

import java.util.*;

import static ru.codebattle.client.api.BoardElement.*;

public class Attacking {
	public static final BoardElement[] ENEMIES = {OTHER_BOMBERMAN, OTHER_BOMB_BOMBERMAN, MEAT_CHOPPER, DESTROY_WALL};

	public static boolean doAttack(GameBoard gameBoard) {
		boolean attack = hasEnemy(gameBoard);
		Set<BoardPoint> restrictedBombPoints = Surviving.getRestrictedBombPoints(gameBoard);
		if (restrictedBombPoints.contains(Surviving.myPosition(gameBoard))) {
			attack = false;
		}
		return attack;
	}

	public static boolean hasEnemy(GameBoard gameBoard) {
		Set<BoardPoint> attackablePoints = getAttackablePoints(gameBoard);
		for (BoardPoint attackablePoint : attackablePoints) {
			for (BoardElement enemy : ENEMIES) {
				if (gameBoard.hasElementAt(attackablePoint, enemy)) {
					return true;
				}
			}
		}

		return false;
	}

	public static Set<BoardPoint> getAttackablePoints(GameBoard gameBoard) {
		BoardPoint myPosition = Surviving.myPosition(gameBoard);
		List<BoardPoint> destrayablePoints = gameBoard.findAllElements(ENEMIES);
		Set<BoardPoint> attackablePoints = new HashSet<>();

		for (BoardPoint destrayablePoint : destrayablePoints) {
			if (Surviving.isAtBombWave(myPosition, destrayablePoint, gameBoard)) {
				attackablePoints.add(destrayablePoint);
			}
		}

		return attackablePoints;
	}

//	public static Queue<BombermanVertex> calculateShortestPathToTarget(BombermanGraph graph) {
//
//		BoardPoint myBoardPoint = Surviving.myPosition(graph.getGameBoard());
//		BombermanVertex myPosition = new BombermanVertex(myBoardPoint, 0);
//		BombermanVertex myTarget = graph.bfs(myBoardPoint, ENEMIES);
//
//		Set<BombermanVertex> settledNodes = new HashSet<>();
//		Set<BombermanVertex> unsettledNodes = new HashSet<>();
//
//		unsettledNodes.add(myPosition);
//
//		while (unsettledNodes.size() != 0) {
//			BombermanVertex currentNode = getLowestDistanceNode(unsettledNodes);
//			unsettledNodes.remove(currentNode);
//			for (Map.Entry<BombermanVertex, Set<BombermanVertex>> adjacencyPair: adjVertices.entrySet()) {
//				BombermanVertex adjacentNode = adjacencyPair.getKey();
//				Integer edgeWeight = adjacencyPair.getValue();
//				if (!settledNodes.contains(adjacentNode)) {
//					calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
//					unsettledNodes.add(adjacentNode);
//				}
//			}
//			settledNodes.add(currentNode);
//		}
//		return null;
//	}
}
