package raytracer.pigments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import raytracer.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TexmapPigment implements Pigment {
	private BufferedImage image;
	private int rows, cols;
	private double sa, sb, sc, sd, ta, tb, tc, td;

	@JsonCreator
	public TexmapPigment(@JsonProperty("bmpFile") File bmpFile,
						 @JsonProperty("sa") double sa, @JsonProperty("sb") double sb, @JsonProperty("sc") double sc,
						 @JsonProperty("sd") double sd, @JsonProperty("ta") double ta, @JsonProperty("tb") double tb,
						 @JsonProperty("tc") double tc, @JsonProperty("td") double td) throws IOException {
		this(bmpFile.toURI().toURL(), sa, sb, sc, sd, ta, tb, tc, td);
	}

	public TexmapPigment(URL bmpTexUrl,
						 @JsonProperty("sa") double sa, @JsonProperty("sb") double sb, @JsonProperty("sc") double sc,
						 @JsonProperty("sd") double sd, @JsonProperty("ta") double ta, @JsonProperty("tb") double tb,
						 @JsonProperty("tc") double tc, @JsonProperty("td") double td) throws IOException {
		image = ImageIO.read(bmpTexUrl);

		this.sa = sa;
		this.sb = sb;
		this.sc = sc;
		this.sd = sd;
		this.ta = ta;
		this.tb = tb;
		this.tc = tc;
		this.td = td;

		this.cols = image.getWidth();
		this.rows = image.getHeight();
	}

	@Override
	public Color getColor(Point p) {
		double s = sa*p.x + sb*p.y + sc*p.z + sd;
		double t = ta*p.x + tb*p.y + tc*p.z + td;

		while(s < 0) s = 1.0 + s;
		while(t < 0) t = 1.0 + t;

		while(s >= 1) s = s - 1.0;
		while(t >= 1) t = t - 1.0;

		return new Color(image.getRGB((int)Math.floor(s * cols), (int)Math.floor(t * rows)));
	}

	@Override
	public String toString() {
		return "textured";
	}
}
