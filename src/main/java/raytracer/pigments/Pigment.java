package raytracer.pigments;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import raytracer.Point;

import java.awt.Color;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = SolidPigment.class)
@JsonSubTypes({
		@JsonSubTypes.Type(name = "solid", value = SolidPigment.class),
		@JsonSubTypes.Type(name = "gradient", value = GradientPigment.class),
		@JsonSubTypes.Type(name = "checker", value = CheckerPigment.class),
		@JsonSubTypes.Type(name = "texmap", value = TexmapPigment.class)
})
public interface Pigment {
	public Color getColor(Point p);
}
