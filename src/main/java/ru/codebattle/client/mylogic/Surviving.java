package ru.codebattle.client.mylogic;

import ru.codebattle.client.api.BoardElement;
import ru.codebattle.client.api.BoardPoint;
import ru.codebattle.client.api.Direction;
import ru.codebattle.client.api.GameBoard;

import java.util.*;

import static ru.codebattle.client.api.BoardElement.*;
import static ru.codebattle.client.api.Direction.*;

public class Surviving {
	private static final BoardElement[] EVERYTHING = {NONE, WALL, DESTROY_WALL, DESTROYED_WALL, MEAT_CHOPPER,
		OTHER_BOMBERMAN, OTHER_BOMB_BOMBERMAN, BOMB_TIMER_1, BOMB_TIMER_2, BOMB_TIMER_3, BOMB_TIMER_4, BOMB_TIMER_5,
		DEAD_MEAT_CHOPPER, OTHER_DEAD_BOMBERMAN, BOOM};
	private static final BoardElement[] BARRIERS = {WALL, DESTROY_WALL, MEAT_CHOPPER, OTHER_BOMBERMAN, OTHER_BOMB_BOMBERMAN,
		BOMB_TIMER_1, BOMB_TIMER_2, BOMB_TIMER_3, BOMB_TIMER_4, BOMB_TIMER_5};
	private static final BoardElement[] STILL_BARRIERS = {WALL, DESTROY_WALL};
	private static final BoardElement[] EMPTYNESS = {NONE};
	private static final BoardElement[] OTHER_ALIVE_BOMBERMANS = {OTHER_BOMBERMAN, OTHER_BOMB_BOMBERMAN};
	private static final BoardElement[] REAL_BARRIERS = {WALL, DESTROY_WALL, DESTROYED_WALL, OTHER_BOMB_BOMBERMAN,
		BOMB_TIMER_1, BOMB_TIMER_2, BOMB_TIMER_3, BOMB_TIMER_4, BOMB_TIMER_5};

	public static BoardPoint myPosition(GameBoard gameBoard) {
		BoardPoint myPosition = gameBoard.findElement(BoardElement.BOMBERMAN);

		if (myPosition == null) {
			myPosition = gameBoard.findElement(BoardElement.BOMB_BOMBERMAN);
		}

		if (myPosition == null) {
			myPosition = gameBoard.findElement(BoardElement.DEAD_BOMBERMAN);
		}

		return myPosition;
	}

	public static Direction doMove(GameBoard gameBoard) {
		Direction direction = STOP;
		List<Direction> freeDirections = getAdjacentFreeDirections(gameBoard);
		freeDirections.removeAll(getRestrictedDirections(gameBoard));
		List<Direction> dangerousDirections = getDangerousDirections(gameBoard);
		freeDirections.removeAll(dangerousDirections);
		List<Direction> enemyDirections = getEnemyDirections(gameBoard);
		freeDirections.removeAll(enemyDirections);

		Random rand = new Random();
//		BombermanGraph bombermanGraph = new BombermanGraph(gameBoard);
//		BoardPoint myPosition = Surviving.myPosition(gameBoard);
//		Deque<BombermanVertex> bfs = bombermanGraph.bfs(myPosition);
//		BombermanVertex bombermanVertex = bfs.pollLast();
//		BoardPoint boardPoint = null;
//		if (bombermanVertex != null) {
//			boardPoint = bombermanVertex.getBoardPoint();
//			for (Direction value : Direction.values()) {
//				BoardPoint shift = Moving.shift(myPosition, value);
//				if (boardPoint.equals(shift)) {
//					direction = value;
//				}
//			}
//		}

		if (!freeDirections.isEmpty()) {
			direction = freeDirections.get(rand.nextInt(freeDirections.size()));
		} else {
			if (!enemyDirections.isEmpty()) {
				freeDirections.addAll(enemyDirections);
				direction = freeDirections.get(rand.nextInt(freeDirections.size()));
			} else if (!dangerousDirections.isEmpty()) {
				freeDirections.addAll(dangerousDirections);
				direction = freeDirections.get(rand.nextInt(freeDirections.size()));
			}
		}

		return direction;
	}

	public static List<Direction> getEnemyDirections(GameBoard gameBoard) {
		Set<BoardPoint> attackablePoints = Attacking.getAttackablePoints(gameBoard);
		List<Direction> enemyDirections = new ArrayList<>();
		BoardPoint myPosition = myPosition(gameBoard);
		for (Direction direction : Direction.values()) {
			for (int i = 2; i < 4; i++) {
				if (gameBoard.hasElementAt(Moving.shift(myPosition, direction), BARRIERS)) {
					break;
				} else if (attackablePoints.contains(Moving.shift(myPosition, direction, i))) {
					enemyDirections.add(direction);
					break;
				}
			}
		}

		return enemyDirections;
	}

	public static int hasAdjacentNonDangerousPoint(BoardPoint boardPoint, GameBoard gameBoard, int i, Set<BoardPoint> visited) {

		Set<BoardPoint> restrictedBombPoints = getRestrictedBombPoints(gameBoard);
		restrictedBombPoints.addAll(getDangerousBombPoints(gameBoard));
		restrictedBombPoints.addAll(getDangerousChopperPoints(gameBoard));
		restrictedBombPoints.addAll(getDangerousKamikadzePoints(gameBoard));
		visited.add(boardPoint);

		Map<Direction, BoardPoint> adjacentElements = getAdjacentElements(boardPoint);

		for (Direction direction : adjacentElements.keySet()) {
			BoardPoint o = adjacentElements.get(direction);
			if (restrictedBombPoints.contains(o) && !visited.contains(o)) {
				i = i + 1;
				hasAdjacentNonDangerousPoint(Moving.shift(boardPoint, direction), gameBoard, i, visited);
			} else {
				return i;
			}
		}

		return i;
	}

	public static Map<Direction, BoardPoint> getAdjacentElements(BoardPoint boardPoint) {
		Map<Direction, BoardPoint> adjacentElements = new HashMap<>();
		adjacentElements.put(Direction.UP, boardPoint.shiftTop());
		adjacentElements.put(Direction.RIGHT, boardPoint.shiftRight());
		adjacentElements.put(Direction.DOWN, boardPoint.shiftBottom());
		adjacentElements.put(Direction.LEFT, boardPoint.shiftLeft());

		return adjacentElements;
	}

	public static List<Direction> getAdjacentFreeDirections(GameBoard gameBoard) {
		List<Direction> adjacentFreeDirections = new ArrayList<>();
		BoardPoint myPosition = myPosition(gameBoard);
		Map<Direction, BoardPoint> adjacentElements = getAdjacentElements(myPosition);

		for (Map.Entry<Direction, BoardPoint> directionBoardPointEntry : adjacentElements.entrySet()) {
			if (gameBoard.hasElementAt(directionBoardPointEntry.getValue(), EMPTYNESS)) {
				adjacentFreeDirections.add(directionBoardPointEntry.getKey());
			}
		}

		return adjacentFreeDirections;
	}

	public static List<Direction> getRestrictedDirections(GameBoard gameBoard) {
		Set<BoardPoint> restrictedBombPoints = getRestrictedBombPoints(gameBoard);
		return getBadDirections(restrictedBombPoints, gameBoard);
	}

	public static List<Direction> getDangerousDirections(GameBoard gameBoard) {
		Set<BoardPoint> allDangerousPoints = new HashSet<>();
		allDangerousPoints.addAll(getDangerousChopperPoints(gameBoard));
		allDangerousPoints.addAll(getDangerousKamikadzePoints(gameBoard));
		allDangerousPoints.addAll(getDangerousBombPoints(gameBoard));

		return getBadDirections(allDangerousPoints, gameBoard);
	}

	public static Set<BoardPoint> getRestrictedBombPoints(GameBoard gameBoard) {
		Set<BoardPoint> blowableBombs = getBlowableBombs(gameBoard);
		Set<BoardPoint> restrictedPoints = new HashSet<>();

		getBloawablePoints(blowableBombs, restrictedPoints, gameBoard);

		return restrictedPoints;
	}

	public static Set<BoardPoint> getDangerousChopperPoints(GameBoard gameBoard) {
		List<BoardPoint> meatChoppers = gameBoard.getMeatchoppers();
		Set<BoardPoint> dangerousChopperPoints = new HashSet<>();

		for (BoardPoint meatChopper : meatChoppers) {
			Map<Direction, BoardPoint> meatChopperAdjacentElements = Surviving.getAdjacentElements(meatChopper);
			for (BoardPoint meatChopperAdjacentElement : meatChopperAdjacentElements.values()) {
				if (gameBoard.hasElementAt(meatChopperAdjacentElement, NONE)) {
					dangerousChopperPoints.add(meatChopperAdjacentElement);
				}
			}
		}

		return dangerousChopperPoints;
	}

	public static Set<BoardPoint> getDangerousBombPoints(GameBoard gameBoard) {
		Set<BoardPoint> dangerousPoints = new HashSet<>();
		Set<BoardPoint> bombs = new HashSet<>(gameBoard.getBombs());
		bombs.removeAll(getBlowableBombs(gameBoard));

		getBloawablePoints(bombs, dangerousPoints, gameBoard);

		return dangerousPoints;
	}

	public static Set<BoardPoint> getDangerousKamikadzePoints(GameBoard gameBoard) {
		Set<BoardPoint> kamikadzeBombs = getKamikadzeBombs(gameBoard);
		Set<BoardPoint> dangerousKamikadzePoints = new HashSet<>();

		getBloawablePoints(kamikadzeBombs, dangerousKamikadzePoints, gameBoard);

		dangerousKamikadzePoints.removeAll(Surviving.getRestrictedBombPoints(gameBoard));

		return dangerousKamikadzePoints;
	}

	public static int getDangerousness(BoardPoint boardPoint, GameBoard gameBoard) {
		Set<BoardPoint> restrictedBombPoints = Surviving.getRestrictedBombPoints(gameBoard);
		Set<BoardPoint> dangerousBombPoints = Surviving.getDangerousBombPoints(gameBoard);
		Set<BoardPoint> dangerousKamikadzePoints = Surviving.getDangerousKamikadzePoints(gameBoard);
		Set<BoardPoint> dangerousChopperPoints = Surviving.getDangerousChopperPoints(gameBoard);

		if (restrictedBombPoints.contains(boardPoint)) {
			return 7;
		}
		if (dangerousBombPoints.contains(boardPoint)) {
			return 4;
		}
		if (dangerousKamikadzePoints.contains(boardPoint)) {
			return 5;
		}
		if (dangerousChopperPoints.contains(boardPoint)) {
			return 6;
		}

		return 0;
	}

	public static Set<BoardPoint> getKamikadzeBombs(GameBoard gameBoard) {
		List<BoardPoint> blowableBombs = new ArrayList<>(getBlowableBombs(gameBoard));
		List<BoardPoint> kamikadzes = gameBoard.findAllElements(OTHER_ALIVE_BOMBERMANS);
		Set<BoardPoint> kamikadzeBombs = new HashSet<>();

		for (int i = 0; i < blowableBombs.size(); i++) {
			for (BoardPoint kamikadze : kamikadzes) {
				if (isAtBombWave(blowableBombs.get(i), kamikadze, gameBoard)) {
					if (!blowableBombs.contains(kamikadze)) {
						kamikadzeBombs.add(kamikadze);
						blowableBombs.add(kamikadze);
					}
				}
			}
		}

		return kamikadzeBombs;
	}

	public static Set<BoardPoint> getBlowableBombs(GameBoard gameBoard) {
		List<BoardPoint> blowableBombs = gameBoard.findAllElements(BOMB_TIMER_1);
		List<BoardPoint> notBlowableBombs = gameBoard.findAllElements(BOMB_TIMER_2, BOMB_TIMER_3, BOMB_TIMER_4, BOMB_TIMER_5);

		for (int i = 0; i < blowableBombs.size(); i++) {
			for (BoardPoint notBlowableBomb : notBlowableBombs) {
				if (isAtBombWave(blowableBombs.get(i), notBlowableBomb, gameBoard)) {
					if (!blowableBombs.contains(notBlowableBomb)) {
						blowableBombs.add(notBlowableBomb);
					}
				}
			}
		}


		return new HashSet<>(blowableBombs);
	}

	public static boolean isAtBombWave(BoardPoint blowableBomb, BoardPoint notBlowableBomb, GameBoard gameBoard) {
		if (isAtBombRange(blowableBomb, notBlowableBomb)) {
			if (blowableBomb.getX() == notBlowableBomb.getX()) {
				if (Math.abs(blowableBomb.getY() - notBlowableBomb.getY()) == 1) {
					return true;
				}

				if (Math.abs(blowableBomb.getY() - notBlowableBomb.getY()) == 2) {
					if (blowableBomb.getY() > notBlowableBomb.getY()) {
						if (gameBoard.hasElementAt(notBlowableBomb.shiftBottom(), STILL_BARRIERS)) {
							return false;
						}
					}

					if (blowableBomb.getY() < notBlowableBomb.getY()) {
						if (gameBoard.hasElementAt(notBlowableBomb.shiftTop(), STILL_BARRIERS)) {
							return false;
						}
					}

					return true;
				}

				if (Math.abs(blowableBomb.getY() - notBlowableBomb.getY()) == 3) {
					if (blowableBomb.getY() > notBlowableBomb.getY()) {
						if (gameBoard.hasElementAt(notBlowableBomb.shiftBottom(), STILL_BARRIERS)) {
							return false;
						}

						if (gameBoard.hasElementAt(notBlowableBomb.shiftBottom(2), STILL_BARRIERS)) {
							return false;
						}

						return true;
					}

					if (blowableBomb.getY() < notBlowableBomb.getY()) {
						if (gameBoard.hasElementAt(notBlowableBomb.shiftTop(), STILL_BARRIERS)) {
							return false;
						}

						if (gameBoard.hasElementAt(notBlowableBomb.shiftTop(2), STILL_BARRIERS)) {
							return false;
						}

						return true;
					}
				}
			} else if (blowableBomb.getY() == notBlowableBomb.getY()) {
				if (Math.abs(blowableBomb.getX() - notBlowableBomb.getX()) == 1) {
					return true;
				}

				if (Math.abs(blowableBomb.getX() - notBlowableBomb.getX()) == 2) {
					if (blowableBomb.getX() > notBlowableBomb.getX()) {
						if (gameBoard.hasElementAt(notBlowableBomb.shiftRight(), STILL_BARRIERS)) {
							return false;
						}
					}

					if (blowableBomb.getY() < notBlowableBomb.getY()) {
						if (gameBoard.hasElementAt(notBlowableBomb.shiftLeft(), STILL_BARRIERS)) {
							return false;
						}
					}

					return true;
				}

				if (Math.abs(blowableBomb.getX() - notBlowableBomb.getX()) == 3) {
					if (blowableBomb.getX() > notBlowableBomb.getX()) {
						if (gameBoard.hasElementAt(notBlowableBomb.shiftRight(), STILL_BARRIERS)) {
							return false;
						}

						if (gameBoard.hasElementAt(notBlowableBomb.shiftRight(2), STILL_BARRIERS)) {
							return false;
						}

						return true;
					}

					if (blowableBomb.getX() < notBlowableBomb.getX()) {
						if (gameBoard.hasElementAt(notBlowableBomb.shiftLeft(), STILL_BARRIERS)) {
							return false;
						}

						if (gameBoard.hasElementAt(notBlowableBomb.shiftLeft(2), STILL_BARRIERS)) {
							return false;
						}

						return true;
					}
				}
			}
		}

		return false;
	}

	public static boolean isAtBombRange(BoardPoint bomb, BoardPoint otherThing) {
		for (Direction direction : Direction.values()) {
			for (int i = 1; i < 4; i++) {
				if (Moving.shift(bomb, direction, i).equals(otherThing)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isAtMeatChopperWave(BoardPoint meatChopper, BoardPoint notMeatChopper) {
		Map<Direction, BoardPoint> adjacentElements = getAdjacentElements(meatChopper);
		for (BoardPoint boardPoint : adjacentElements.values()) {
			if (boardPoint.equals(notMeatChopper)) {
				return true;
			}
		}

		return false;
	}

	private static List<Direction> getBadDirections(Set<BoardPoint> badPoints, GameBoard gameBoard) {
		Map<Direction, BoardPoint> adjacentElements = getAdjacentElements(myPosition(gameBoard));
		ArrayList<Direction> directions = new ArrayList<>();

		for (Map.Entry<Direction, BoardPoint> directionBoardPointEntry : adjacentElements.entrySet()) {
			if (badPoints.contains(directionBoardPointEntry.getValue())) {
				directions.add(directionBoardPointEntry.getKey());
			}
		}

		return directions;
	}

	private static void getBloawablePoints(Set<BoardPoint> bombs, Set<BoardPoint> dangerousPoints, GameBoard gameBoard) {
		//!!!must have bomberman here because of checking under myself
		List<BoardPoint> emptyPoints = gameBoard.findAllElements(NONE, BOMBERMAN);

		for (BoardPoint blowableBomb : bombs) {
			for (BoardPoint emptyPoint : emptyPoints) {
				if (isAtBombWave(blowableBomb, emptyPoint, gameBoard)) {
					if (!dangerousPoints.contains(emptyPoint)) {
						dangerousPoints.add(emptyPoint);
					}
				}
			}
		}
	}
}
