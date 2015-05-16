package it.escape.server.model.game.gamemap.positioning;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matcher;
import org.junit.Test;
import it.escape.server.model.game.gamemap.positioning.Position2DMatcher;

public class CoordinatesConverterTest {

	@Test
	public void test() {
		PositionCubic p = new PositionCubic(0, 0, 0);
		assertThat(CoordinatesConverter.fromCubicToOddQ(p), is(new Position2D(0, 0)));
	}

	private Matcher<? super Position2D> is(Position2D riferimento) {
		return new Position2DMatcher(riferimento);
	}

}