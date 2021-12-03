import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PackageTests {
    @JsonProperty("packageId")
    int packageId;

    @JsonProperty("jsScript")
    String jsScript;

    @JsonProperty("functionName")
    String functionName;

    @JsonProperty("tests")
    ArrayList<Test> tests;



    public class Test{
        
    }
}