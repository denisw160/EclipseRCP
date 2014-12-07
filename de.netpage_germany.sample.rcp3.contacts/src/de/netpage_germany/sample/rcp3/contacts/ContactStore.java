package de.netpage_germany.sample.rcp3.contacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Implementation of {@link IContactStore}. Creation and managing over {@link ContactStoreFactory}.
 * 
 * @author Denis Wirries
 */
public class ContactStore implements IContactStore {

    private final Map<String, Contact> store;

    /**
     * Constructor initialized the store.
     */
    protected ContactStore() {
        store = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Contact get(final String id) {
        if (store.containsKey(id)) {
            return store.get(id).clone();
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Contact> get() {
        final List<Contact> list = new ArrayList<>();
        for (final Contact c : store.values()) {
            list.add(c.clone());
        }

        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Contact> search(final String forename, final String surname, final String address) {
        final List<Contact> list = new ArrayList<>();
        for (final Contact c : store.values()) {
            if (forename != null && forename.equals(c.getForename())) {
                list.add(c.clone());
            } else if (surname != null && surname.equals(c.getSurname())) {
                list.add(c.clone());
            } else if (address != null && address.equals(c.getAddress())) {
                list.add(c.clone());
            }
        }

        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(final Contact contact) {
        if (contact.getId() == null) {
            contact.setId(UUID.randomUUID().toString());
        }

        if (store.containsKey(contact.getId())) {
            store.remove(contact.getId());
        }

        contact.resetDirty();
        final Contact clone = contact.clone();
        store.put(clone.getId(), clone);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final Contact contact) {
        if (contact.getId() != null && store.containsKey(contact.getId())) {
            store.remove(contact.getId());
        }
    }

}
