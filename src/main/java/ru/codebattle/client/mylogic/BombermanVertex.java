package ru.codebattle.client.mylogic;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import ru.codebattle.client.api.BoardPoint;

import java.util.Objects;

@Data
public class BombermanVertex {
	private BoardPoint boardPoint;
	private int dangerousness;

	public BombermanVertex(BoardPoint boardPoint, int dangerousness) {

		this.boardPoint = boardPoint;
		this.dangerousness = dangerousness;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BombermanVertex that = (BombermanVertex) o;
		return Objects.equals(boardPoint, that.boardPoint);
	}

	@Override
	public int hashCode() {
		return Objects.hash(boardPoint);
	}

	@Override
	public String toString() {
		return String.format("[%s,%s]", boardPoint.getX(), boardPoint.getY());
	}
}
