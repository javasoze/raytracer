package raytracer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.awt.Color;
import java.io.IOException;

public class ColorUtil {
	public static Color blend(Color base, Color mixin) {
		float[] baseC = base.getRGBColorComponents(null);
		float[] mixinC = mixin.getRGBColorComponents(null);

		float red = clamp(baseC[0] + mixinC[0]);
		float green = clamp(baseC[1] + mixinC[1]);
		float blue = clamp(baseC[2] + mixinC[2]);
		return new Color(red, green, blue);
	}

	public static float clamp(float x) {
		return Math.max(0.0f, Math.min(1.0f, x));
	}

	public static Color intensify(Color color, float intensity) {
		// TODO: clamp should not be necessary here
		return intensify(color, new Color(clamp(intensity), clamp(intensity), clamp(intensity)));
	}

	public static Color intensify(Color color, Color intensity) {
		float[] c = color.getRGBColorComponents(null);
		float[] i = intensity.getRGBColorComponents(null);

		return new Color(c[0] * i[0], c[1] * i[1], c[2] * i[2]);
	}

	public static Color average(Color... colors) {
		float[] rgb = new float[3];
		float mult = 1.0f / colors.length;
		for(Color c: colors) {
			float[] cc = c.getRGBColorComponents(null);
			rgb[0] += cc[0] * mult;
			rgb[1] += cc[1] * mult;
			rgb[2] += cc[2] * mult;
		}

		return new Color(rgb[0], rgb[1], rgb[2]);
	}

	public static class ColorSerializer extends StdSerializer<Color> {

		public ColorSerializer() {
			super(Color.class);
		}

		@Override
		public void serialize(
				Color color, JsonGenerator jgen, SerializerProvider provider)
				throws IOException, JsonProcessingException {
			jgen.writeStartObject();
			jgen.writeNumberField("red", color.getRed());
			jgen.writeNumberField("green", color.getGreen());
			jgen.writeNumberField("blue", color.getBlue());
			jgen.writeNumberField("alpha", color.getAlpha());
			jgen.writeEndObject();
		}
	}

	public static class ColorDeserializer extends StdDeserializer<Color> {

		public ColorDeserializer() {
			super(Color.class);
		}

		@Override
		public Color deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectCodec codec = jp.getCodec();
			JsonNode node = codec.readTree(jp);

			float redVal = (float) node.get("red").asDouble();
			float greenVal = (float) node.get("green").asDouble();
			float blueVal = (float) node.get("blue").asDouble();
			float alphaVal = 1.0f;
			if (node.hasNonNull("alpha")) {
				alphaVal = (float) node.get("alpha").asDouble();
			}

			//return new Color(ColorUtil.clamp(redVal), ColorUtil.clamp(greenVal), ColorUtil.clamp(blueVal), ColorUtil.clamp(alphaVal));
			return new Color(ColorUtil.clamp(redVal), ColorUtil.clamp(greenVal), ColorUtil.clamp(blueVal));
		}
	}
}
