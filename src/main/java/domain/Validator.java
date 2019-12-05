
package domain;

public class Validator {

    private final int minLength;
    private final int maxLength;
    private String errorMessage;
    private Boolean valid;
    
    public Validator(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        errorMessage = "";
        valid = true;
    }
    
    public boolean isValid(String fieldName, String input) {
        if (tooShort(fieldName, input, this.minLength) || 
            tooLong(fieldName, input, this.maxLength)) {
            return false;
        }
        return true;
    }
    
    public boolean tooShort(String fieldName, String input, int min) {
        if (input.length() < min) {
            errorMessage = fieldName + " -kentän tulee olla vähintään " + min + " merkkiä pitkä.";
            valid = false;
            return true;
        }
        return false;
    }
    
    public boolean tooLong(String fieldName, String input, int max) {
        if (input.length() >= max) {
            errorMessage = fieldName + " -kentän tulee olla enintään " + max + " merkkiä pitkä.";
            valid = false;
            return true;
        }
        return false;
    }

    public String getErrorMessage() {
        String message = errorMessage;
        errorMessage = "";
        valid = true;
        return message;
    }

    public Boolean getValid() {
        return valid;
    }
}
