/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools.util;

//import com.vividsolutions.jts.geom.Coordinate;
import org.locationtech.jts.geom.Coordinate;
import org.junit.Assert;
import org.junit.Test;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;

/**
 * Test correct functionality of Transformer class, that is responsible for converting GPS location coordinates to local
 * projected coordinates.
 */
public class TransformerTest {

	@Test
	public void testBerlinTargetSrid() throws FactoryException, TransformException {

		Transformer transformer = new Transformer(3068);
		double latitude = 52.516064;
		double longitude = 13.396305;
		Coordinate coordinate = new Coordinate(longitude, latitude);
		Coordinate projectedCoordinate = transformer.toProjected(coordinate);
		// long=x=24445.804
		// lat=y=21019.910

		Assert.assertEquals(24445.804, projectedCoordinate.x, 1);
		Assert.assertEquals(21019.910, projectedCoordinate.y, 1);

		Coordinate backProjectedCoordinate = transformer.toReal(projectedCoordinate);

		Assert.assertEquals(13.396305, backProjectedCoordinate.x, 0.0000001);
		Assert.assertEquals(52.516064, backProjectedCoordinate.y, 0.0000001);
	}

	@Test
	public void testPragueTargetSrid() throws FactoryException, TransformException {

		Transformer transformer = new Transformer(2065);
		double longitude = 14.424638;
		double latitude = 50.077319;
		Coordinate coordinate = new Coordinate(longitude, latitude);
		Coordinate projectedCoordinate = transformer.toProjected(coordinate);
		// lon=x= 742723,6
		// lat=y= 1044163.9

		Assert.assertEquals(742723.6, projectedCoordinate.x, 1);
		Assert.assertEquals(1044163.9, projectedCoordinate.y, 1);

		Coordinate backProjectedCoordinate = transformer.toReal(projectedCoordinate);

		Assert.assertEquals(14.424638, backProjectedCoordinate.x, 0.0000001);
		Assert.assertEquals(50.077319, backProjectedCoordinate.y, 0.0000001);
	}

	@Test
	public void testLoadTargetSrid() throws FactoryException, TransformException {

		// Helsinky
		Transformer transformer = new Transformer(2392);
		Assert.assertNotNull(transformer);

		// Terneuzen
		transformer = new Transformer(28992);
		Assert.assertNotNull(transformer);

		// Milan
		transformer = new Transformer(3003);
		Assert.assertNotNull(transformer);

		// Trikala
		transformer = new Transformer(2100);
		Assert.assertNotNull(transformer);

		// Barcelona
		transformer = new Transformer(2062);
		Assert.assertNotNull(transformer);
	}

}
