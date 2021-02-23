/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools.util;

import java.io.Serializable;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

/**
 * Class is responsible for converting GPS coordinates to local projected coordinates and from projected coordinates
 * back to GPS defined by city coordinate system (parameter citySRID).
 */
public class Transformer implements Serializable {

	private static final long serialVersionUID = 8432061425989150088L;

	private static final int REAL_SRID = 4326;

	private ProjectionTransformer fromProjectedToReal;
	private ProjectionTransformer fromRealToProjected;

	/**
	 * Creates Transformer.
	 *
	 * @param citySRID
	 * 		specifies the coordinate system
	 */
	public Transformer(int citySRID) {
		this.fromProjectedToReal = new ProjectionTransformer(citySRID, REAL_SRID);
		this.fromRealToProjected = new ProjectionTransformer(REAL_SRID, citySRID);
	}

	/**
	 * Converts coordinate from projected coordinates to real GPS.
	 */
	public Coordinate toReal(Coordinate c) {
		return fromProjectedToReal.transform(c);
	}

	/**
	 * Converts coordinate from projected coordinates to real GPS.
	 */
	public <T extends Geometry> T toReal(T g) {
		return fromProjectedToReal.transform(g);
	}

	/**
	 * Converts coordinate from real GPS coordinates to projected.
	 */
	public Coordinate toProjected(Coordinate c) {
		return fromRealToProjected.transform(c);
	}

	/**
	 * Converts coordinate from real GPS coordinates to projected.
	 */
	public <T extends Geometry> T toProjected(T g) {
		return fromRealToProjected.transform(g);
	}
}
