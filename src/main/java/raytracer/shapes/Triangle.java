package raytracer.shapes;

import com.fasterxml.jackson.annotation.JsonCreator;
import raytracer.Point;
import raytracer.Ray;
import raytracer.RayHit;
import raytracer.Vector;

public class Triangle extends Shape {
	private final Point p1, p2, p3;
	private final Vector u, v;
	private final Plane plane;
	private final Vector normal;

	@JsonCreator
	public Triangle(Config config) {
		super(config);
		this.p1 = config.p1;
		this.p2 = config.p2;
		this.p3 = config.p3;

		this.u = new Vector(p1, p2);
		this.v = new Vector(p1, p3);
		this.normal = u.cross(v).normalize();

		double a = normal.x;
		double b = normal.y;
		double c = normal.z;
		double d = p1.x * normal.x + p1.y * normal.y + p1.z * normal.z;

		var planeConfig = new Plane.Config();
		planeConfig.a = a;
		planeConfig.b = b;
		planeConfig.c = c;
		planeConfig.d = d;

		this.plane = new Plane(planeConfig);
	}

	@Override
	public RayHit intersect(Ray ray) {
		RayHit planeHit = plane.intersect(ray);
		if(planeHit == null) return null;

		double uu, uv, vv, wu, wv, D;
		uu = u.dot(u);
		uv = u.dot(v);
		vv = v.dot(v);
		Vector w = new Vector(planeHit.point.plus(new Vector(p1).negate()));

		wu = w.dot(u);
		wv = w.dot(v);
		D = uv * uv  - uu * vv;

		double s, t;
		s = (uv * wv - vv * wu) / D;
		if(s < 0 || s > 1) return null;
		t = (uv * wu - uu * wv) / D;
		if(t < 0 || (s + t) > 1) return null;
		return new RayHit(planeHit.ray, this, planeHit.normal, planeHit.point, true);
	}

	public static class Config extends Shape.Config {
		public Point p1, p2, p3;
	}
}
