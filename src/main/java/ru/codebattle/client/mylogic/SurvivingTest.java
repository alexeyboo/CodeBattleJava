package ru.codebattle.client.mylogic;

import org.junit.Assert;
import org.junit.Test;
import ru.codebattle.client.api.BoardPoint;
import ru.codebattle.client.api.Direction;
import ru.codebattle.client.api.GameBoard;

import java.util.*;

import static ru.codebattle.client.api.Direction.*;

public class SurvivingTest {
	private BoardPoint blowableBomb55 = new BoardPoint(5, 5);
	private BoardPoint notThreat56 = new BoardPoint(5, 6);
	private BoardPoint notThreat57 = new BoardPoint(5, 7);
	private BoardPoint notThreat65 = new BoardPoint(6, 5);
	private BoardPoint notThreat75 = new BoardPoint(7, 5);
	private BoardPoint notThreat45 = new BoardPoint(4, 5);
	private BoardPoint notThreat35 = new BoardPoint(3, 5);
	private BoardPoint notThreat54 = new BoardPoint(5, 4);
	private BoardPoint notThreat53 = new BoardPoint(5, 3);
	private BoardPoint notThreat52 = new BoardPoint(5, 2);
	private BoardPoint notThreat66 = new BoardPoint(6, 6);
	private BoardPoint notThreat44 = new BoardPoint(4, 4);
	private BoardPoint notThreat46 = new BoardPoint(4, 6);
	private BoardPoint notThreat64 = new BoardPoint(6, 4);
	private BoardPoint notThreat58 = new BoardPoint(5, 8);
	private BoardPoint notThreat85 = new BoardPoint(8, 5);
	private BoardPoint notThreat25 = new BoardPoint(2, 5);
	private BoardPoint notThreat59 = new BoardPoint(5, 9);
	private BoardPoint notThreat95 = new BoardPoint(9, 5);
	private BoardPoint notThreat51 = new BoardPoint(5, 1);
	private BoardPoint notThreat15 = new BoardPoint(1, 5);
	private BoardPoint notThreat68 = new BoardPoint(6, 8);
	private BoardPoint notThreat78 = new BoardPoint(7, 8);
	private BoardPoint notThreat88 = new BoardPoint(8, 8);
	private BoardPoint notThreat48 = new BoardPoint(4, 8);
	private BoardPoint notThreat38 = new BoardPoint(3, 8);
	private BoardPoint notThreat18 = new BoardPoint(1, 8);
	private BoardPoint notThreat17 = new BoardPoint(1, 7);
	private BoardPoint notThreat37 = new BoardPoint(3, 7);
	private BoardPoint notThreat47 = new BoardPoint(4, 7);
	private BoardPoint notThreat26 = new BoardPoint(2, 6);
	private BoardPoint notThreat24 = new BoardPoint(2, 4);
	private BoardPoint chopper71 = new BoardPoint(7, 1);
	private BoardPoint chopper62 = new BoardPoint(6, 2);
	private BoardPoint chopper82 = new BoardPoint(8, 2);
	private BoardPoint chopper73 = new BoardPoint(7, 3);
	private BoardPoint blowableBomb58 = new BoardPoint(5, 8);
	private BoardPoint blowableBomb28 = new BoardPoint(2, 8);
	private BoardPoint blowableBomb27 = new BoardPoint(2, 7);
	private BoardPoint dangerousPoint41 = new BoardPoint(4, 1);
	private BoardPoint dangerousPoint31 = new BoardPoint(3, 1);
	private BoardPoint dangerousPoint21 = new BoardPoint(2, 1);
	private BoardPoint dangerousPoint71 = new BoardPoint(7, 1);
	private BoardPoint dangerousPoint81 = new BoardPoint(8, 1);
	private BoardPoint dangerousPoint52 = new BoardPoint(5, 2);
	private BoardPoint dangerousPoint53 = new BoardPoint(5, 3);
	private BoardPoint dangerousPoint54 = new BoardPoint(5, 4);
	private BoardPoint dangerousPoint62 = new BoardPoint(6, 2);
	private BoardPoint dangerousPoint63 = new BoardPoint(6, 3);
	private BoardPoint dangerousPoint64 = new BoardPoint(6, 4);
	private BoardPoint dangerousPoint16 = new BoardPoint(1, 6);
	private BoardPoint dangerousPoint15 = new BoardPoint(1, 5);
	private BoardPoint dangerousPoint14 = new BoardPoint(1, 4);
	private BoardPoint dangerousPoint77 = new BoardPoint(7, 7);
	private BoardPoint dangerousPoint76 = new BoardPoint(7, 6);
	private BoardPoint dangerousPoint78 = new BoardPoint(7, 8);
	private BoardPoint dangerousPoint17 = new BoardPoint(1, 7);
	private BoardPoint dangerousPoint66 = new BoardPoint(6, 6);
	private BoardPoint dangerousPoint86 = new BoardPoint(8, 6);
	private BoardPoint me = new BoardPoint(2, 3);
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
	GameBoard gameBoard2 = new GameBoard("☼☼☼☼☼☼☼☼☼☼\n" +
																									   "☼    23  ☼\n" +
																									   "☼ ☼    & ☼\n" +
																									   "☼☼☺ ♥    ☼\n" +
																									   "☼    ☼   ☼\n" +
																									   "☼222 1 ☼ ☼\n" +
																									   "☼ ♥ ☼  ♠ ☼\n" +
																									   "☼♥4      ☼\n" +
																									   "☼ 3  2 ♠ ☼\n" +
																									   "☼☼☼☼☼☼☼☼☼☼\n");

	@Test
	public void hasAdjacentNonDangerousPoint() {
		int i = Surviving.hasAdjacentNonDangerousPoint(new BoardPoint(3,7), gameBoard2, 0, new HashSet<>());
		System.out.println(i);
	}

	@Test
	public void myPosition() {
		Assert.assertEquals(new BoardPoint(2, 3), Surviving.myPosition(gameBoard));
	}

	@Test
	public void getAdjacentElements() {
		Map<Direction, BoardPoint> adjacentElements = new HashMap<>();
		adjacentElements.put(Direction.UP, new BoardPoint(5, 4));
		adjacentElements.put(Direction.RIGHT, new BoardPoint(6, 5));
		adjacentElements.put(DOWN, new BoardPoint(5, 6));
		adjacentElements.put(Direction.LEFT, new BoardPoint(4, 5));

		Assert.assertEquals(adjacentElements, Surviving.getAdjacentElements(new BoardPoint(5, 5)));
	}

	@Test
	public void adjacentFreeDirections() {
		List<Direction> adjacentFreeDirectionsExpected1 = Arrays.asList(DOWN, RIGHT);
		List<Direction> adjacentFreeDirectionsActual1 = Surviving.getAdjacentFreeDirections(gameBoard);
		Assert.assertTrue(adjacentFreeDirectionsExpected1.containsAll(adjacentFreeDirectionsActual1));
		Assert.assertTrue(adjacentFreeDirectionsActual1.containsAll(adjacentFreeDirectionsExpected1));
	}

	@Test
	public void getRestrictedBombPoints() {
		Set<BoardPoint> restrictedBombPointsActual = Surviving.getRestrictedBombPoints(gameBoard);
		Set<BoardPoint> restrictedBombPointsExpected =
			new HashSet<>(Arrays.asList(notThreat25, notThreat35, notThreat45, notThreat65, notThreat56, notThreat57,
				notThreat68, notThreat88, notThreat48, notThreat38, notThreat18, notThreat37, notThreat47, notThreat26,
				notThreat24));

		Assert.assertEquals(restrictedBombPointsExpected, restrictedBombPointsActual);
	}

	@Test
	public void getDangerousBombPoints() {
		Set<BoardPoint> dangerousBombPointsActual = Surviving.getDangerousBombPoints(gameBoard);
		Set<BoardPoint> dangerousBombPointsExpected = new HashSet<>(Arrays.asList(dangerousPoint41,	dangerousPoint31,
			dangerousPoint21, dangerousPoint71, dangerousPoint81, dangerousPoint52, dangerousPoint53,	dangerousPoint62,
			dangerousPoint63,	dangerousPoint64, dangerousPoint41,	dangerousPoint31,	dangerousPoint21,	dangerousPoint71,
			dangerousPoint81, dangerousPoint52, dangerousPoint53,	dangerousPoint62,	dangerousPoint63,	dangerousPoint64));

		System.out.println(gameBoard.size());
		Assert.assertEquals(dangerousBombPointsExpected, dangerousBombPointsActual);
	}

	@Test
	public void getDangerousKamikadzePoints() {
		Set<BoardPoint> dangerousKamikadzePointsActual = Surviving.getDangerousKamikadzePoints(gameBoard);
		Set<BoardPoint> dangerousKamikadzePointsExpected = new HashSet<>(Arrays.asList(dangerousPoint16, dangerousPoint15,
			dangerousPoint14,	dangerousPoint77, dangerousPoint66, dangerousPoint86));

		Assert.assertEquals(dangerousKamikadzePointsExpected, dangerousKamikadzePointsActual);
	}

	@Test
	public void getDangerousChopperPoints() {
		Set<BoardPoint> dangerousPointsActual = Surviving.getDangerousChopperPoints(gameBoard);
		Set<BoardPoint> dangerousPointsExpected = new HashSet<>(Arrays.asList(chopper62, chopper71, chopper73, chopper82));

		Assert.assertEquals(dangerousPointsExpected, dangerousPointsActual);
	}

	@Test
	public void getBlowableBombs() {
		Set<BoardPoint> blowableBombsActual = Surviving.getBlowableBombs(gameBoard);
		List<BoardPoint> blowableBombsExpected =
			Arrays.asList(blowableBomb27, blowableBomb28, blowableBomb55, blowableBomb58);

		Assert.assertTrue(blowableBombsActual.containsAll(blowableBombsExpected));
		Assert.assertTrue(blowableBombsExpected.containsAll(blowableBombsActual));
	}

	@Test
	public void isAtBombWave() {
		Assert.assertTrue(Surviving.isAtBombWave(blowableBomb55, notThreat56, gameBoard));
		Assert.assertTrue(Surviving.isAtBombWave(blowableBomb55, notThreat57, gameBoard));
		Assert.assertTrue(Surviving.isAtBombWave(blowableBomb55, notThreat58, gameBoard));
		Assert.assertTrue(Surviving.isAtBombWave(blowableBomb55, notThreat65, gameBoard));
		Assert.assertTrue(Surviving.isAtBombWave(blowableBomb55, notThreat75, gameBoard));
		Assert.assertFalse(Surviving.isAtBombWave(blowableBomb55, notThreat85, gameBoard));
		Assert.assertTrue(Surviving.isAtBombWave(blowableBomb55, notThreat54, gameBoard));
		Assert.assertFalse(Surviving.isAtBombWave(blowableBomb55, notThreat53, gameBoard));
		Assert.assertFalse(Surviving.isAtBombWave(blowableBomb55, notThreat52, gameBoard));
		Assert.assertTrue(Surviving.isAtBombWave(blowableBomb55, notThreat45, gameBoard));
		Assert.assertTrue(Surviving.isAtBombWave(blowableBomb55, notThreat35, gameBoard));
		Assert.assertTrue(Surviving.isAtBombWave(blowableBomb55, notThreat25, gameBoard));
		Assert.assertFalse(Surviving.isAtBombWave(blowableBomb55, notThreat44, gameBoard));
		Assert.assertFalse(Surviving.isAtBombWave(blowableBomb55, notThreat46, gameBoard));
		Assert.assertFalse(Surviving.isAtBombWave(blowableBomb55, notThreat64, gameBoard));
		Assert.assertFalse(Surviving.isAtBombWave(blowableBomb55, notThreat66, gameBoard));
	}

	@Test
	public void isAtBombRange() {
		Assert.assertTrue(Surviving.isAtBombRange(blowableBomb55, notThreat56));
		Assert.assertTrue(Surviving.isAtBombRange(blowableBomb55, notThreat65));
		Assert.assertTrue(Surviving.isAtBombRange(blowableBomb55, notThreat45));
		Assert.assertTrue(Surviving.isAtBombRange(blowableBomb55, notThreat54));
		Assert.assertTrue(Surviving.isAtBombRange(blowableBomb55, notThreat57));
		Assert.assertTrue(Surviving.isAtBombRange(blowableBomb55, notThreat75));
		Assert.assertTrue(Surviving.isAtBombRange(blowableBomb55, notThreat35));
		Assert.assertTrue(Surviving.isAtBombRange(blowableBomb55, notThreat53));
		Assert.assertFalse(Surviving.isAtBombRange(blowableBomb55, notThreat66));
		Assert.assertFalse(Surviving.isAtBombRange(blowableBomb55, notThreat44));
		Assert.assertFalse(Surviving.isAtBombRange(blowableBomb55, notThreat46));
		Assert.assertFalse(Surviving.isAtBombRange(blowableBomb55, notThreat64));
		Assert.assertTrue(Surviving.isAtBombRange(blowableBomb55, notThreat58));
		Assert.assertTrue(Surviving.isAtBombRange(blowableBomb55, notThreat85));
		Assert.assertTrue(Surviving.isAtBombRange(blowableBomb55, notThreat52));
		Assert.assertTrue(Surviving.isAtBombRange(blowableBomb55, notThreat25));
		Assert.assertFalse(Surviving.isAtBombRange(blowableBomb55, notThreat59));
		Assert.assertFalse(Surviving.isAtBombRange(blowableBomb55, notThreat95));
		Assert.assertFalse(Surviving.isAtBombRange(blowableBomb55, notThreat51));
		Assert.assertFalse(Surviving.isAtBombRange(blowableBomb55, notThreat15));
	}

	@Test
	public void getRestrictedDirections() {
		List<Direction> restrictedDirectionsActual = Surviving.getRestrictedDirections(gameBoard);
		List<Direction> restrictedDirectionsExpected = Arrays.asList(DOWN);

		Assert.assertEquals(restrictedDirectionsExpected, restrictedDirectionsActual);
	}

	@Test
	public void isAtMeatChopperWave() {
		Assert.assertTrue(Surviving.isAtMeatChopperWave(blowableBomb55, notThreat56));
		Assert.assertTrue(Surviving.isAtMeatChopperWave(blowableBomb55, notThreat65));
		Assert.assertTrue(Surviving.isAtMeatChopperWave(blowableBomb55, notThreat45));
		Assert.assertTrue(Surviving.isAtMeatChopperWave(blowableBomb55, notThreat54));
		Assert.assertFalse(Surviving.isAtMeatChopperWave(blowableBomb55, notThreat57));
		Assert.assertFalse(Surviving.isAtMeatChopperWave(blowableBomb55, notThreat75));
		Assert.assertFalse(Surviving.isAtMeatChopperWave(blowableBomb55, notThreat35));
		Assert.assertFalse(Surviving.isAtMeatChopperWave(blowableBomb55, notThreat53));
		Assert.assertFalse(Surviving.isAtMeatChopperWave(blowableBomb55, notThreat66));
		Assert.assertFalse(Surviving.isAtMeatChopperWave(blowableBomb55, notThreat44));
		Assert.assertFalse(Surviving.isAtMeatChopperWave(blowableBomb55, notThreat46));
		Assert.assertFalse(Surviving.isAtMeatChopperWave(blowableBomb55, notThreat64));
	}

	@Test
	public void getKamikadzeBombs() {
		Set<BoardPoint> kamikadzeBombsActual = Surviving.getKamikadzeBombs(gameBoard);
		Set<BoardPoint> kamikadzeBombsExpected =
			new HashSet<>(Arrays.asList(dangerousPoint76, dangerousPoint78, dangerousPoint17));
		Assert.assertEquals(kamikadzeBombsExpected, kamikadzeBombsActual);
	}

	@Test
	public void getEnemyDirections() {
		List<Direction> enemyDirectionsActual = Surviving.getEnemyDirections(gameBoard);
		List<Direction> enemyDirectionsExpected = Arrays.asList(RIGHT);

		List<Direction> enemyDirectionsActual2 = Surviving.getEnemyDirections(gameBoard2);
		List<Direction> enemyDirectionsExpected2 = Arrays.asList(RIGHT);


		Assert.assertEquals(enemyDirectionsExpected, enemyDirectionsActual);
		Assert.assertEquals(enemyDirectionsExpected2, enemyDirectionsActual2);
	}
}