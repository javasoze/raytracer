package raytracer.shapes;

import raytracer.*;

import java.util.List;

public class Cone extends Shape {
	private Point apex;
	private Vector axis;
	private double radius;

	public Cone(Config config) {
		super(config);
		this.apex = config.apex;
		this.axis = config.axis;
		this.radius = config.radius;

		Log.warn("Cone shape is not supported. This shape will be ignored.");
	}

	@Override
	public RayHit intersect(Ray ray) {
		return null;
	}

	public static class Config extends Shape.Config {
		public Point apex;
		public Vector axis;
		public double radius;
	}
}
