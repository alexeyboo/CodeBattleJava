package ru.codebattle.client.mylogic;

import ru.codebattle.client.api.BoardPoint;
import ru.codebattle.client.api.Direction;

public class Moving {
	public static BoardPoint shift(BoardPoint boardPoint, Direction direction) {
		switch (direction) {
			case UP: {
				return boardPoint.shiftTop();
			}
			case RIGHT: {
				return boardPoint.shiftRight();
			}
			case DOWN: {
				return boardPoint.shiftBottom();
			}
			case LEFT: {
				return boardPoint.shiftLeft();
			}
		}

		return boardPoint;
	}

	public static BoardPoint shift(BoardPoint boardPoint, Direction direction, int delta) {
		switch (direction) {
			case UP: {
				return boardPoint.shiftTop(delta);
			}
			case RIGHT: {
				return boardPoint.shiftRight(delta);
			}
			case DOWN: {
				return boardPoint.shiftBottom(delta);
			}
			case LEFT: {
				return boardPoint.shiftLeft(delta);
			}
		}

		return boardPoint;
	}
}
