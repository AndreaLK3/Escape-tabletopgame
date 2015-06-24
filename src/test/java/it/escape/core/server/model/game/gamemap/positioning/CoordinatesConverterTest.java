package it.escape.core.server.model.game.gamemap.positioning;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import it.escape.core.server.model.game.exceptions.BadCoordinatesException;
import it.escape.core.server.model.game.gamemap.positioning.CoordinatesConverter;
import it.escape.core.server.model.game.gamemap.positioning.Position2D;
import it.escape.core.server.model.game.gamemap.positioning.Position2DMatcher;
import it.escape.core.server.model.game.gamemap.positioning.PositionCubic;
import it.escape.tools.utils.LogHelper;

public class CoordinatesConverterTest {

	private static final Logger LOG = Logger.getLogger( CoordinatesConverterTest.class.getName() );
	
	@Before
	public void initializeLogger() {
		LogHelper.setDefaultOptions(LOG);
	}
	
	@Test
	public void testCubicToOddQ() {
		PositionCubic p = new PositionCubic(1,-1,0);
		assertThat(CoordinatesConverter.fromCubicToOddQ(p), is(new Position2D(1,0)));
		p = new PositionCubic(1,-2,1);
		assertThat(CoordinatesConverter.fromCubicToOddQ(p), is(new Position2D(1,1)));
		p = new PositionCubic(2,-3,1);
		assertThat(CoordinatesConverter.fromCubicToOddQ(p), is(new Position2D(2,2)));
	
	}

	private Matcher<? super Position2D> is(Position2D riferimento) {
		return new Position2DMatcher(riferimento);
	}
	
	@Test
	public void testPrettifyAlphaNum(){
		String s1="A4", s2= "F14";
		s1=CoordinatesConverter.prettifyAlphaNum(s1);	//checks if A4 becomes A04
		assertTrue(s1.equals("A04"));
		s2=CoordinatesConverter.prettifyAlphaNum(s2);	//verifies that F14 is not modified
		assertTrue(s2.equals("F14"));
	}

	@Test
	public void fromCubicToAlphaNum() {
	
		String s; 
		PositionCubic p;
		
		s = "A00";
		p = new PositionCubic(0,0,0);
		assertTrue(s.equals(CoordinatesConverter.fromCubicToAlphaNum(p)));
		
		s = "A01";
		p = new PositionCubic(0,-1,1);
		assertTrue(s.equals(CoordinatesConverter.fromCubicToAlphaNum(p)));
		
		s = "A02";
		p = new PositionCubic(0,-2,2);
		assertTrue(s.equals(CoordinatesConverter.fromCubicToAlphaNum(p)));
		
		s = "B00";
		p = new PositionCubic(1,-1,0);
		assertTrue(s.equals(CoordinatesConverter.fromCubicToAlphaNum(p)));
		
		s = "C01";
		p = new PositionCubic(2,-2,0);
		assertTrue(s.equals(CoordinatesConverter.fromCubicToAlphaNum(p)));
	
	}

	
	@Test
	public void testFromOddqToCubic() {
		Position2D pos2D = new Position2D(1,0);
		assertThat(CoordinatesConverter.fromOddqToCubic(pos2D), is(new PositionCubic(1,-1, 0)));
		
		pos2D = new Position2D(2,1);
		assertThat(CoordinatesConverter.fromOddqToCubic(pos2D), is(new PositionCubic(2,-2,0)));
		
		pos2D = new Position2D(1,1);
		assertThat(CoordinatesConverter.fromOddqToCubic(pos2D), is(new PositionCubic(1,-2,1)));
		
	}

	private Matcher<? super PositionCubic> is(PositionCubic positionCubic) {
		return new MatcherPos3D(positionCubic);
	}
	
	
	@Test
	public void testFromAlphaNumToOddq() throws BadCoordinatesException {
		
		assertThat(CoordinatesConverter.fromAlphaNumToOddq("C01"), is(new Position2D(2,1)));	
		assertThat(CoordinatesConverter.fromAlphaNumToOddq("A02"), is(new Position2D(0,2)));
		assertThat(CoordinatesConverter.fromAlphaNumToOddq("B01"), is(new Position2D(1,1)));
	}
	
	/**
	 * check if, after repeated conversions, the coordinates are preserved
	 * @throws BadCoordinatesException
	 */
	@Test
	public void testConversionLoop() throws BadCoordinatesException {
		String start = "E15";
		Position2D step1 = CoordinatesConverter.fromAlphaNumToOddq(start);
		PositionCubic step2 = CoordinatesConverter.fromOddqToCubic(step1);
		assertEquals(start, CoordinatesConverter.fromCubicToAlphaNum(step2));
	}
	
}