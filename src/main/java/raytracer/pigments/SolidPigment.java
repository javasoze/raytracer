package raytracer.pigments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import raytracer.Point;

import java.awt.Color;

public class SolidPigment implements Pigment {
	public Color color;

	@JsonCreator
	public SolidPigment(@JsonProperty("color")  Color color) {
		this.color = color;
	}

	@JsonIgnore
	public Color getColor(Point p) {
		return color;
	}

	@Override
	public String toString() {
		return "solid";
	}
}
