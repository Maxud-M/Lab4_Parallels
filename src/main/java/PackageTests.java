import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PackageTests {

    public static final String FUNCTION_NAME = "functionName";
    public static final String JS_SCRIPT = "jsScript";
    public static final String TESTS = "tests";
    public static final String EXPECTED_RESULT = "expectedResult";
    public static final String

    @JsonProperty("packageId")
    int packageId;

    @JsonProperty("jsScript")
    String jsScript;

    @JsonProperty("functionName")
    String functionName;

    @JsonProperty("tests")
    ArrayList<Test> tests;



    public class Test{
        @JsonProperty("testName")
        String testName;

        @JsonProperty("expectedResult")
        String expectedResult;

        @JsonProperty("params")
        ArrayList<Object> params;

        @JsonCreator
        Test(@JsonProperty)
    }
}
