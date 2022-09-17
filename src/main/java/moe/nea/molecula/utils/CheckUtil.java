package moe.nea.molecula.utils;

import java.util.function.Supplier;

public class CheckUtil {
    public static void failIf(boolean shouldBeFalse, Supplier<String> message) {
        if (shouldBeFalse)
            throw new IllegalStateException(message.get());
    }

    public static void failUnless(boolean shouldBeTrue, Supplier<String> message) {
        failIf(!shouldBeTrue, message);
    }

    public static void failIfLessThan(int toTest, int minimumAllowedValue, String name) {
        failIf(toTest < minimumAllowedValue, () -> name + " needs to be at least " + minimumAllowedValue);
    }

}

