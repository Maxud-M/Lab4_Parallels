import com.fasterxml.jackson.annotation.JsonProperty;

public class PackageTests {
    @JsonProperty("packageId")
    int packageId;

    @JsonProperty("jsScript")
    String jsScript;

    @JsonProperty
}
