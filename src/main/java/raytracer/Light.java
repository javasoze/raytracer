package raytracer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.awt.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = DefaultLight.class)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "default", value = DefaultLight.class),
        @JsonSubTypes.Type(name = "ambient", value = AmbientLight.class)
})
public interface Light {
    float getAttenuationFactor(double d);
    Color getColor(RayHit hit, Ray lightRay);
}
