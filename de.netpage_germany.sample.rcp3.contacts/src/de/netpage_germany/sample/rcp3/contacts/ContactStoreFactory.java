package de.netpage_germany.sample.rcp3.contacts;

/**
 * Factory for creation and managing the ContactStore.
 * 
 * @author Denis Wirries
 */
public final class ContactStoreFactory {

    private static final IContactStore STORE = new ContactStore();

    // Static initialisation
    static {
        final int max = 20;
        for (int i = 0; i < max; i++) {
            Contact c = new Contact("Forename " + i, "Surename " + i, "Address " + i);
            STORE.store(c);
        }
    }

    /**
     * Private Constructor.
     */
    private ContactStoreFactory() {
    }

    /**
     * Return the instance of the store.
     * 
     * @return store
     */
    public static IContactStore getInstance() {
        return STORE;
    }

}
