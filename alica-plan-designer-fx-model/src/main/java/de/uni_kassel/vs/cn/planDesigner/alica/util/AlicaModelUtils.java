package de.uni_kassel.vs.cn.planDesigner.alica.util;

/**
 * General purpose utility class
 */
public class AlicaModelUtils {
    // Characters that are illegal as C++ identifiers are not allowed to be names of any ALICA object
    public static final String forbiddenCharacters = ".*[\\./\\*\\\\$§?\\[\\]!{}\\-äüö#\"%~'ÄÖÜß@]+.*";

    /**
     * Checks whether a given string contains illegal characters
     * @param toCheck the string to check (NEVER <code>null</code>)
     * @return the result
     */
    public static boolean containsIllegalCharacter(String toCheck) {
        return toCheck.matches(forbiddenCharacters);
    }
}
