package raytracer.shapes;

import com.fasterxml.jackson.annotation.JsonCreator;
import raytracer.Ray;
import raytracer.RayHit;
import raytracer.Vector;

public class Plane extends Shape {
	private final double a, b, c, d;
	private final Vector normal;

	@JsonCreator
	public Plane(Config config) {
		super(config);
		this.a = config.a;
		this.b = config.b;
		this.c = config.c;
		this.d = config.d;
		this.normal = new Vector(a, b, c).normalize();
	}

	@Override
	public RayHit intersect(Ray ray) {
		// from http://www.tar.hu/gamealgorithms/ch22lev1sec2.html
		double denominator = (a * ray.direction.x + b * ray.direction.y + c * ray.direction.z);
		if(denominator == 0.0) return null;

		double t = - (a * ray.origin.x + b * ray.origin.y + c * ray.origin.z + d) / denominator;

		if(t < 0) return null;

		return new RayHit(ray, this, normal, t, true);
	}

	public static class Config extends Shape.Config {
		public double a, b, c, d;
	}
}
