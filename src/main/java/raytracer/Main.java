package raytracer;

import com.google.common.io.Files;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.File;

public class Main {
	public static boolean DEBUG = false;
	public static boolean ANTI_ALIAS = false;
	public static boolean MULTI_THREAD = false;

	public static void main(String[] args) throws Exception {
		ArgumentParser parser = ArgumentParsers.newFor("raytracer").build()
				.defaultHelp(true)
				.description("Java raytracer");
		parser.addArgument("-i", "--input").required(true)
				.help("Input scene file");
		parser.addArgument("-o", "--output").required(false)
				.help("Output BMP file");
		parser.addArgument("-d", "--debug").required(false).setDefault(false)
				.help("enable debugging - default false");
		parser.addArgument("-a", "--antialiasing").required(false).setDefault(true)
				.help("enable antialiasing - default true");
		parser.addArgument("-m", "--multithread").required(false).setDefault(false)
				.help("enable multi-threading - default true");
		Namespace ns = null;
		try {
			ns = parser.parseArgs(args);
		} catch (ArgumentParserException e) {
			parser.handleError(e);
			System.exit(1);
		}

		File sceneFile = new File(ns.getString("input"));
		Scene scene = Scene.MAPPER.readValue(sceneFile, Scene.class);

		DEBUG = ns.getBoolean("debug");
		ANTI_ALIAS = ns.getBoolean("antialiasing");
		MULTI_THREAD = ns.getBoolean("multithread");

		RayTracer rayTracer = new RayTracer(scene.width, scene.height);
		rayTracer.loadScene(scene);

		var output = ns.getString("output");
		File outFile = null;
		if (output != null) {
			outFile = new File(output);
		} else {
			var name = Files.getNameWithoutExtension(sceneFile.getName());
			outFile = new File(name + ".bmp");
		}
		rayTracer.draw(outFile);
	}
}
