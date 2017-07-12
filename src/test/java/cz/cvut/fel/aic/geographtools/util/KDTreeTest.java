/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools.util;

import cz.agents.basestructures.GPSLocation;
import cz.cvut.fel.aic.geographtools.util.KDTree.ConflictResolverMode;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KDTreeTest {

	Random rng = new Random(123L);
	public static final int NUM_OF_POINTS = 1000;
	public static final int NUM_OF_TRIALS = 20;

	@Test
	public void testKdTree() {

		for (int i = 0; i < NUM_OF_TRIALS; i++) {
			final GPSLocation pivot = generateLocationWithRandomProjected();

			KDTree<GPSLocation> tree = new KDTree<>(2, new GPSLocationKDTreeResolver<>(), ConflictResolverMode
					.USE_OLD, GPSLocation[]::new);
			List<GPSLocation> points = new ArrayList<>();
			for (int j = 0; j < NUM_OF_POINTS; j++) {
				points.add(generateLocationWithRandomProjected());
				tree.insert(points.get(points.size() - 1));
			}

			Collections.sort(points, ((o1, o2) -> GPSLocationTools.computeDistance(o1, pivot) -
												  GPSLocationTools.computeDistance(o2, pivot)));

			Assert.assertEquals("pivot is " +
								pivot.toString(), points.get(0), tree.getNearestNode(tree.getTreeResolver()
					.getCoordinates(pivot)));
			Assert.assertEquals("pivot is " +
								pivot.toString(), points.get(1), tree.getNNearestNodes(tree.getTreeResolver()
					.getCoordinates(pivot), 2).get(1));

		}

	}

	public GPSLocation generateLocationWithRandomProjected() {

		int projLat = rng.nextInt(10000);
		int projLon = rng.nextInt(10000);

		return new GPSLocation((double) projLat, (double) projLon, projLat, projLon, 0);
	}

}
