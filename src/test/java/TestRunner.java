import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * @Author ColorfulNight
 * @Date 2017/4/9 1:59
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(EncodeTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        String string =result.wasSuccessful()?"past":"failure";
        System.out.println("*************************************\nresult : test "+string);
        System.out.println(result.getRunCount()+" test Ran, " + result.getFailureCount() +" test failure, " +
                "total ran time " + result.getRunTime() + " ms");
    }
}
