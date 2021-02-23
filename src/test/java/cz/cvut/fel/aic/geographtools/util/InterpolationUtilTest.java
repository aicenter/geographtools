package cz.cvut.fel.aic.geographtools.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class InterpolationUtilTest {

	@Test
	public void testInterpolate() throws Exception {
		double[] fromVector = new double[]{4, -11, 0};
		double[] toVector = new double[]{7, -8, 900};

		List<double[]> expectedInterpolations = new ArrayList<>();

		expectedInterpolations.add(new double[]{5, -10, 300});
		expectedInterpolations.add(new double[]{6, -9, 600});

		List<double[]> actualInterpolations = InterpolationUtil.interpolate(fromVector, toVector, 2);

		assertEquals(expectedInterpolations.size(), actualInterpolations.size());

		Iterator<double[]> expectedIt = expectedInterpolations.iterator();
		Iterator<double[]> actualIt = actualInterpolations.iterator();

		while (expectedIt.hasNext()) {
			double[] expected = expectedIt.next();
			double[] actual = actualIt.next();

			assertArrayEquals(expected, actual, 0d);
		}
	}

	@Test
	public void testInterpolate2() throws Exception {
		double[] fromVector = new double[]{0, 0, 0};
		double[] toVector = new double[]{0, 0, 0};

		List<double[]> expectedInterpolations = new ArrayList<>();

		expectedInterpolations.add(new double[]{0, 0, 0});
		expectedInterpolations.add(new double[]{0, 0, 0});

		List<double[]> actualInterpolations = InterpolationUtil.interpolate(fromVector, toVector, 2);

		assertEquals(expectedInterpolations.size(), actualInterpolations.size());

		Iterator<double[]> expectedIt = expectedInterpolations.iterator();
		Iterator<double[]> actualIt = actualInterpolations.iterator();

		while (expectedIt.hasNext()) {
			double[] expected = expectedIt.next();
			double[] actual = actualIt.next();

			assertArrayEquals(expected, actual, 0d);
		}
	}
}