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
    @Test
    public void passwordValidator_GetMasterOffsets_ReturnsTrue() {
        assertTrue(ScreenPasswordDecodeFragment.getMasterOffsets("nose", "nosecurity").equals("0123"));
        assertTrue(ScreenPasswordDecodeFragment.getMasterOffsets("rity", "nosecurity").equals("6789"));
        assertTrue(ScreenPasswordDecodeFragment.getMasterOffsets("nosecurity", "nosecurity").equals("0123456789"));
    }
    @Test
    public void passwordValidator_GetMasterOffsets_ReturnsFalse() {
        assertFalse(!ScreenPasswordDecodeFragment.getMasterOffsets("nose", "nosecurity").equals("0123"));
    }

    @Test
    public void passwordValidator_ValidateMaster_ReturnsFalse() {
        // redundancy is checked in MasterHasUniqueChars
        assertFalse(ScreenPasswordDecodeFragment.validateMaster("Gosecurity")); //No caps
        assertFalse(ScreenPasswordDecodeFragment.validateMaster("nosecurityx")); //11 letters
        assertFalse(ScreenPasswordDecodeFragment.validateMaster("nosecurit")); //9 letters
        assertFalse(ScreenPasswordDecodeFragment.validateMaster("nosecurit1")); //illegal char
        assertFalse(ScreenPasswordDecodeFragment.validateMaster("nosecurit1")); //illegal char
        assertFalse(ScreenPasswordDecodeFragment.validateMaster(null)); //illegal char
    }
}
