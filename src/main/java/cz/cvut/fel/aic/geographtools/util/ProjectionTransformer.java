/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools.util;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import java.io.Serializable;

/**
 * Class is responsible for converting coordinates between source coordinate system and target coordinate system. The
 * coordinate systems are represented by SRID
 */
public class ProjectionTransformer implements Serializable {

	private static final long serialVersionUID = -2843931857397642056L;
	
	private int sourceSrid;
	private int targetSrid;
	private MathTransform transform;

	public ProjectionTransformer(int sourceSrid, int targetSrid, boolean longitudeFirst) {
		super();
		this.sourceSrid = sourceSrid;
		this.targetSrid = targetSrid;

		CoordinateReferenceSystem sourceCRS = null;
		CoordinateReferenceSystem targetCRS = null;
		try {
			sourceCRS = CRS.decode("EPSG:" + checkSRID(this.sourceSrid), longitudeFirst);
			targetCRS = CRS.decode("EPSG:" + checkSRID(this.targetSrid), longitudeFirst);
			transform = CRS.findMathTransform(sourceCRS, targetCRS, true);
		} catch (FactoryException e) {
			e.printStackTrace();
		}
	}

	public int getSourceSrid() {
		return sourceSrid;
	}

	public int getTargetSrid() {
		return targetSrid;
	}

	private int checkSRID(int srid) {
		if (srid == 900913) {
			return 3857;
		} else {
			return srid;
		}
	}

	public ProjectionTransformer(int sourceSrid, int targetSrid) {
		this(sourceSrid, targetSrid, true);
	}

	public ProjectionTransformer(CoordinateReferenceSystem krovak, int targetSrid) {
		CoordinateReferenceSystem targetCRS = null;

		try {
			targetCRS = CRS.decode("EPSG:" + targetSrid);
			transform = CRS.findMathTransform(krovak, targetCRS);
		} catch (FactoryException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Converts a geometry.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Geometry> T transform(T geometry) {
		T g = null;
		try {
			g = (T) JTS.transform(geometry, transform);
		} catch (TransformException e) {
			e.printStackTrace();
			return null;
		}

		g.setSRID(targetSrid);
		return g;

	}

	/**
	 * Converts coordinates.
	 */
	public Coordinate transform(Coordinate coordinate) {
		try {
			return JTS.transform(coordinate, new Coordinate(), transform);
		} catch (TransformException e) {
			e.printStackTrace();
			return null;
		}
	}

}
