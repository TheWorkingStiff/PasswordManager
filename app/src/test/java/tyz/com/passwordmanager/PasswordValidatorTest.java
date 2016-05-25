package tyz.com.passwordmanager;

import org.junit.Test;

import static org.junit.Assert.*;
import java.util.regex.Pattern;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class PasswordValidatorTest {
    //Tests for ScreenPasswordDecodeFragment
    @Test
    public void passwordValidator_MasterHasUniqueChars_ReturnsTrue() {
        assertTrue(ScreenPasswordDecodeFragment.checkUniqueBV("nosecurity"));
    }

    @Test
    public void passwordValidator_MasterHasUniqueChars_ReturnsFalse() {
        assertFalse(ScreenPasswordDecodeFragment.checkUniqueBV("nnosecurity"));
        assertFalse(ScreenPasswordDecodeFragment.checkUniqueBV("nosecurityy"));
        assertFalse(ScreenPasswordDecodeFragment.checkUniqueBV("nosecurityn"));
        assertFalse(ScreenPasswordDecodeFragment.checkUniqueBV("ynosecurity"));
    }

    @Test
    public void passwordValidator_GetMasterLetters_ReturnsTrue() {
        assertTrue(ScreenPasswordDecodeFragment.getMasterLetters("0123", "nosecurity").equals("nose"));
        assertTrue(ScreenPasswordDecodeFragment.getMasterLetters("6789", "nosecurity").equals("rity"));
        assertTrue(ScreenPasswordDecodeFragment.getMasterLetters("0123456789", "nosecurity").equals("nosecurity"));
    }
    @Test
    public void passwordValidator_GetMasterLetters_ReturnsFalse() {
        assertFalse(!ScreenPasswordDecodeFragment.getMasterLetters("0123", "nosecurity").equals("nose"));
    }

}
