package eapli.base;

import static org.junit.Assert.fail;

public class Utils {

    public static <E extends Throwable> void assertUtils(Class exceptionType, ExceptionAction1Checked<E> action) {
        try {
            action.execute();
            fail();
        } catch (Throwable e) {
            if (!exceptionType.isInstance(e)) {
                fail();
            }
        }
    }
}
