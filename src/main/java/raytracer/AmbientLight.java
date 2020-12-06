package raytracer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;

public class AmbientLight extends DefaultLight {

	@JsonCreator
	public AmbientLight(@JsonProperty("location") Point location, @JsonProperty("color") Color color,
						@JsonProperty("a") float a, @JsonProperty("b") float b, @JsonProperty("c") float c) {
		super(location, color, a, b, c);
	}

	@Override
	public Color getColor(RayHit hit, Ray lightRay) {
		return ColorUtil.intensify(color, hit.shape.finish.amb);
	}
}
