
package domain;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ValidatorTest {
    
    Validator validator;
    
    @Before
    public void setUp() {
        validator = new Validator();
    }
    
    @Test
    public void acceptsValid() {
        validator.isValid("Nimi", "valid");
        assertEquals(validator.getValid(), true);
    }
    
    @Test
    public void rejectsTooShort() {
        validator.isValid("Nimi", "a");
        assertEquals(validator.getValid(), false);
        assertEquals(validator.getErrorMessage(), "Nimi -kentän tulee olla vähintään 3 merkkiä pitkä.");
    }
    
    @Test
    public void rejectsTooLong() {
        validator.isValid("Nimi", "tämäonaivanliianpitkänimeksitälletestille");
        assertEquals(validator.getValid(), false);
        assertEquals(validator.getErrorMessage(), "Nimi -kentän tulee olla enintään 20 merkkiä pitkä.");
    }
}
