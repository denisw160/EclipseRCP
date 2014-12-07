package de.netpage_germany.sample.rcp3.contacts;

import java.beans.Beans;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Transaltion for the plugin.
 * 
 * @author Denis.Wirries
 */
public final class Messages {

    /** Resource-File for I18N. */
    private static final String BUNDLE_NAME = "de.netpage_germany.sample.rcp3.contacts.messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = loadBundle();

    /**
     * Constructor.
     */
    private Messages() {
        // do not instantiate
    }

    /**
     * Get the translation.
     * 
     * @param key
     *            key for translation
     * @return translation
     */
    public static String getString(final String key) {
        try {
            ResourceBundle bundle = Beans.isDesignTime() ? loadBundle() : RESOURCE_BUNDLE;
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "!" + key + "!";
        }
    }

    /**
     * Load the translations from file.
     * 
     * @return {@link ResourceBundle}
     */
    private static ResourceBundle loadBundle() {
        return ResourceBundle.getBundle(BUNDLE_NAME);
    }

}
