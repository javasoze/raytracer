package raytracer.pigments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Finish {
	public float amb, diff, spec, shiny, refl, trans, ior;

	@JsonCreator
	public Finish(@JsonProperty("amb") float amb,
				  @JsonProperty("diff") float diff,
				  @JsonProperty("spec") float spec,
				  @JsonProperty("shiny") float shiny,
				  @JsonProperty("refl") float refl,
				  @JsonProperty("trans") float trans,
				  @JsonProperty("ior") float ior) {
		this.amb = amb;
		this.diff = diff;
		this.spec = spec;
		this.shiny = shiny;
		this.refl = refl;
		this.trans = trans;
		this.ior = ior;
	}

	public boolean isReflective() {
		return refl > 0;
	}

	public boolean isTransmittive() {
		return trans > 0;
	}
}
