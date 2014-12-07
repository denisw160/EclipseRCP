package de.netpage_germany.sample.rcp3.contacts;

import java.util.List;

/**
 * Interface for declaration the store of contacts.
 * 
 * @author Denis Wirries
 */
public interface IContactStore {

    /**
     * Loads the contact to the id.
     * 
     * @param id id of the contact
     * @return matched contact
     */
    Contact get(String id);

    /**
     * Return all stored contacts.
     * 
     * @return list of contacts
     */
    List<Contact> get();

    /**
     * Search for a contact, who matched to the parameter.
     * 
     * @param forename forename
     * @param surname surname
     * @param address address
     * @return list of matched contacts
     */
    List<Contact> search(String forename, String surname, String address);

    /**
     * Stores the contact.
     * 
     * @param contact contact
     */
    void store(Contact contact);

    /**
     * Deletes the contact.
     * 
     * @param contact contact
     */
    void delete(Contact contact);

}
