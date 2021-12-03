import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PackageTests {

    public static final String FUNCTION_NAME = "functionName";
    public static final String JS_SCRIPT = "jsScript";
    public static final String TESTS = "tests";
    public static final String EXPECTED_RESULT = "expectedResult";
    public static final String PARAMS = "params";
    public static final String TEST_NAME = "testName"

    @JsonProperty(Constants.PACKAGE_ID)
    int packageId;

    @JsonProperty(JS_SCRIPT)
    String jsScript;

    @JsonProperty(FUNCTION_NAME)
    String functionName;

    @JsonProperty(TESTS)
    ArrayList<Test> tests;



    public class Test{
        @JsonProperty(TEST_NAME)
        String testName;

        @JsonProperty(EXPECTED_RESULT)
        String expectedResult;

        @JsonProperty(PARAMS)
        ArrayList<Object> params;

        @JsonCreator
        Test(@JsonProperty(Constants.PACKAGE_ID) int packageId, @JsonProperty(JS_SCRIPT) String jsScript,
             @JsonProperty(FUNCTION_NAME) String functionName, @JsonProperty()) {

        }
    }
}
