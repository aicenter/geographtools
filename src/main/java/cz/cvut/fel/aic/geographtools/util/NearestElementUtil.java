package cz.cvut.fel.aic.geographtools.util;

//import com.vividsolutions.jts.geom.Coordinate;
import org.locationtech.jts.geom.Coordinate;
import cz.cvut.fel.aic.geographtools.GPSLocation;
import net.sf.javaml.core.kdtree.KDTree;
//import org.javatuples.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * @author Marek Cuchy
 */
public class NearestElementUtil<TElement> implements Serializable {

	private static final long serialVersionUID = 8310413576935699214L;

	private transient KDTree<TElement> kdTree;
	private final Transformer transformer;

	private final SerializableIntFunction<TElement[]> arrayConstructor;

	/**
	 * It contains list of elements with coordinates converted to a Cartesian coordinate system.
	 */
	private List<NearestElementUtilPair<Coordinate, TElement>> elements;

	/**
	 * It builds util for returning of nearest element from {@code elements}. The coordinates of the {@code elements}
	 * have to be in WGS84 - longitude as 'x',latitude as 'y' in the coordinate. The {@code transformer} have to
	 * converts to some Cartesian reference system.
	 *
	 * @param elements
	 * @param transformer
	 */
	public NearestElementUtil(List<NearestElementUtilPair<Coordinate, TElement>> elements, Transformer transformer,
							  SerializableIntFunction<TElement[]> arrayConstructor) {
		this(elements, transformer, false, arrayConstructor);
	}

	/**
	 * It builds util for returning of nearest element from {@code elements}. If {@code converted} is {@code true} it
	 * supposes that coordinates in {@code elements} have already been converted to the target reference system of
	 * {@code transformer}. If it is set to {@code false} the coordinates will be converted and therefore the
	 * coordinates of the {@code elements} have to be in WGS84. The {@code transformer} have to converts to some
	 * Cartesian reference system.
	 *
	 * @param elements
	 * @param transformer
	 * @param converted
	 * 		if {@code converted} is {@code true} it supposes that coordinates in {@code elements} have already been
	 * 		converted to the target reference system of {@code transformer}. If it is set to {@code false} the
	 * 		coordinates
	 * 		will be converted and therefore the coordinates of the {@code elements} have to be in WGS84.
	 * @param arrayConstructor
	 * 		Function generating arrays containing multiple nearest elements.
	 */
	public NearestElementUtil(List<NearestElementUtilPair<Coordinate, TElement>> elements, Transformer transformer, boolean converted,
							  SerializableIntFunction<TElement[]> arrayConstructor) {
		this.transformer = transformer;
		this.arrayConstructor = arrayConstructor;
		initKDTree(elements, converted);
	}

	private void initKDTree(List<NearestElementUtilPair<Coordinate, TElement>> elements, boolean converted) {
		this.kdTree = new KDTree<>(2, arrayConstructor);
		elements = convertIfNeeded(elements, converted);
		elements.forEach(pair -> kdTree.insert(convertCoordinateToDoubleArray(pair.getValue0()), pair.getValue1()));
		this.elements = elements;
	}

	private List<NearestElementUtilPair<Coordinate, TElement>> convertIfNeeded(List<NearestElementUtilPair<Coordinate, TElement>> elements,
															 boolean converted) {
		if (!converted) {
			elements = elements.stream()
							   .map(pair -> new NearestElementUtilPair<>(transformer.toProjected(pair.getValue0()), pair.getValue1()))
							   .collect(Collectors.toList());
		}
		return elements;
	}

	/**
	 * If the {@code location} doesn't contain projected coordinates it transforms the coordinates. Otherwise it uses
	 * the projected coordinates.
	 *
	 * @param location
	 *
	 * @return
	 */
	public TElement getNearestElement(GPSLocation location) {
		if (hasProjectedCoordinates(location)) {
			return getNearestElementProjected(getProjectedCoordinate(location));
		} else {
			return getNearestElement(getNormalCoordinate(location));
		}

	}

	/**
	 * Get {@code k} nearest elements to {@code location}. If the {@code location} doesn't contain projected
	 * coordinates
	 * it transforms the coordinates. Otherwise it uses the projected coordinates.
	 *
	 * @param location
	 * @param k
	 * 		number of elements to search for.
	 *
	 * @return
	 */
	public TElement[] getKNearestElements(GPSLocation location, int k) {
		if (hasProjectedCoordinates(location)) {
			return getKNearestElementProjected(getProjectedCoordinate(location), k);
		} else {
			return getKNearestElement(getNormalCoordinate(location), k);
		}
	}

	/**
	 * Return an element nearest to {@code convertedCoordinates}. Coordinates have to be in Cartesian reference system
	 * for which the util was initialized.
	 *
	 * @param convertedCoordinates
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public TElement getNearestElementFromConvertedCoordinates(Coordinate convertedCoordinates) {
		return kdTree.nearest(convertCoordinateToDoubleArray(convertedCoordinates));
	}

	/**
	 * Get nearest element to {@code coordinate}. Coordinates have to be in Cartesian reference system for which the
	 * util was initialized.
	 *
	 * @param coordinate
	 * 		It has to be already projected.
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public TElement getNearestElementProjected(Coordinate coordinate) {
		return kdTree.nearest(convertCoordinateToDoubleArray(coordinate));
	}

	/**
	 * Get {@code k} nearest elements to {@code coordinate}.Coordinates have to be in Cartesian reference system for
	 * which the util was initialized.
	 *
	 * @param coordinate
	 * 		It has to be already projected.
	 * @param k
	 * 		number of elements to search for.
	 *
	 * @return
	 */
	public TElement[] getKNearestElementProjected(Coordinate coordinate, int k) {
		return kdTree.nearest(convertCoordinateToDoubleArray(coordinate), k);
	}

	/**
	 * Coordinates have to be in WGS84
	 *
	 * @param coordinate
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public TElement getNearestElement(Coordinate coordinate) {
		Coordinate converted = transformer.toProjected(coordinate);
		return kdTree.nearest(convertCoordinateToDoubleArray(converted));
	}

	/**
	 * Get {@code k} nearest elements to {@code coordinate}.Coordinates have to be in WGS84.
	 *
	 * @param coordinate
	 * 		have to be in WGS84.
	 * @param k
	 * 		number of elements to search for.
	 *
	 * @return
	 */
	public TElement[] getKNearestElement(Coordinate coordinate, int k) {
		Coordinate converted = transformer.toProjected(coordinate);
		return kdTree.nearest(convertCoordinateToDoubleArray(converted), k);
	}

	private static double[] convertCoordinateToDoubleArray(Coordinate c) {
		return new double[]{c.x, c.y};
	}

	private static Coordinate getNormalCoordinate(GPSLocation location) {
		return new Coordinate(location.getLongitude(), location.getLatitude(), 0);
	}

	private static Coordinate getProjectedCoordinate(GPSLocation location) {
		return new Coordinate(location.getLongitudeProjected(), location.getLatitudeProjected(), 0);
	}

	private static boolean hasProjectedCoordinates(GPSLocation location) {
		return location.getLatitudeProjected1E2() != 0 || location.getLatitudeProjected1E2() != 0;
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		this.initKDTree(elements, true);
	}

	public static interface SerializableIntFunction<T> extends IntFunction<T>, Serializable {

	}
	
	
}
