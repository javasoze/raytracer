package raytracer.shapes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import raytracer.Point;
import raytracer.Ray;
import raytracer.RayHit;
import raytracer.pigments.Finish;
import raytracer.pigments.Pigment;

import java.awt.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(name = "sphere", value = Sphere.class),
		@JsonSubTypes.Type(name = "plane", value = Plane.class),
		@JsonSubTypes.Type(name = "triangle", value = Triangle.class)
})
public abstract class Shape {
	@JsonIgnore
	public Pigment pigment;

	@JsonIgnore
	public Finish finish;

	public int pigmentIndex = -1;
	public int finishIndex = -1;

	public Shape(Config config) {
		this.pigmentIndex = config.pigmentIndex;
		this.finishIndex = config.finishIndex;
	}

	public final void setMaterial(Pigment pigment, Finish finish) {
		this.pigment = pigment;
		this.finish = finish;
	}

	public abstract RayHit intersect(Ray ray);

	public boolean contains(Point p) {
		return false;
	}

	public final Color getColor(Point p) {
		return pigment.getColor(p);
	}

	public static class Config {
		public int pigmentIndex = -1;
		public int finishIndex = -1;
	}
}
