package raytracer.shapes;

import com.fasterxml.jackson.annotation.JsonCreator;
import raytracer.*;

import java.util.List;

public class Bezier extends Shape {
	private List<Point> points;

	@JsonCreator
	public Bezier(Config config) {
		super(config);
		this.points = config.points;

		Log.warn("Bezier shape is not supported. This shape will be ignored.");
	}

	@Override
	public RayHit intersect(Ray ray) {
		return null;
	}

	public static class Config extends Shape.Config {
		public List<Point> points = List.of();
	}
}
