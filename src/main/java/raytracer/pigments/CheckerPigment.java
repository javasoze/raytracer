package raytracer.pigments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import raytracer.Point;

import java.awt.Color;


public class CheckerPigment implements Pigment {
	private Color color1;
	private Color color2;
	private double scale;

	@JsonCreator
	public CheckerPigment(@JsonProperty("color1") Color color1,
						  @JsonProperty("color2") Color color2,
						  @JsonProperty("scale") double scale) {
		this.color1 = color1;
		this.color2 = color2;
		this.scale = scale;
	}

	public Color getColor(Point p) {
		int which = (floor(p.x/scale) + floor(p.y/scale) + floor(p.z/scale)) % 2;
		if(which == 0) return color1;
		return color2;
	}

	private int floor(double d) {
		return (int)Math.abs(Math.floor(d));
	}

	public String toString() {
		return "checkered";
	}
}
