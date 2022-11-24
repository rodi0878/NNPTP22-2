package cz.upce.fei.nnptp.entity;

public class PasswordGeneratorConfiguration {
    private final boolean includeCapitalLettersInPassword;
    private final boolean includeNumbersInPassword;
    private final boolean includeSpecialCharactersInPassword;

    public PasswordGeneratorConfiguration(boolean includeCapitalLettersInPassword, boolean includeNumbersInPassword, boolean includeSpecialCharactersInPassword) {
        this.includeCapitalLettersInPassword = includeCapitalLettersInPassword;
        this.includeNumbersInPassword = includeNumbersInPassword;
        this.includeSpecialCharactersInPassword = includeSpecialCharactersInPassword;
    }

    public boolean isIncludeCapitalLettersInPassword() {
        return includeCapitalLettersInPassword;
    }

    public boolean isIncludeNumbersInPassword() {
        return includeNumbersInPassword;
    }

    public boolean isIncludeSpecialCharactersInPassword() {
        return includeSpecialCharactersInPassword;
    }
}
