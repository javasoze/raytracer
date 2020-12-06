package raytracer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import raytracer.pigments.Finish;
import raytracer.pigments.Pigment;
import raytracer.shapes.Shape;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Scene {
    public static ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    public static class UrlDeseralizer extends JsonDeserializer<URL> {

        @Override
        public URL deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            ObjectCodec objectCodec = p.getCodec();
            JsonNode node = objectCodec.readTree(p);
            String stringUrl = node.asText();
            return new URL(stringUrl);
        }
    }

    public static class FileDeseralizer extends JsonDeserializer<File> {
        @Override
        public File deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            ObjectCodec objectCodec = p.getCodec();
            JsonNode node = objectCodec.readTree(p);
            String fileName = node.asText();
            return new File(fileName);
        }
    }

    static {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Color.class, new ColorUtil.ColorDeserializer());
        module.addDeserializer(URL.class, new UrlDeseralizer());
        module.addDeserializer(File.class, new FileDeseralizer());
        MAPPER.registerModule(module);
    }

    @JsonProperty
    public Point eye;

    @JsonProperty
    public Point center = new Point(0.0, 0.0, 0.0);

    @JsonProperty
    public Vector up = new Vector(0.0, 1.0, 0.0);

    @JsonProperty
    public double fovy = 45.0;

    @JsonProperty
    public List<Light> lights = List.of();

    @JsonProperty
    public List<Pigment> pigments = List.of();

    @JsonProperty
    public List<Finish> finishes = List.of();

    @JsonProperty
    public List<Shape> shapes = List.of();

    @JsonProperty
    public int width = 640;

    @JsonProperty
    public int height = 480;

    @Override
    public String toString() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
