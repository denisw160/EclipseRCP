package de.netpage_germany.sample.rcp3.contacts;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This is the model class for a contact.
 * 
 * @author Denis Wirries
 */
public class Contact extends ModelChangeSupport implements Serializable {

    private static final long serialVersionUID = -3681960068259561828L;

    private String id;

    private String forename;

    private String surname;

    private String address;

    /**
     * Default constructor.
     */
    public Contact() {
        super();
    }

    /**
     * Constructor to fill the contact.
     * 
     * @param forename Forename
     * @param surname Surname
     * @param address Adress
     */
    protected Contact(final String forename, final String surname, final String address) {
        super();

        this.forename = forename;
        this.surname = surname;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    /**
     * Setter.
     * 
     * @param id id
     */
    protected void setId(final String id) {
        firePropertyChange("id", this.id, id);
        this.id = id;
    }

    public String getForename() {
        return forename;
    }

    /**
     * Setter.
     * 
     * @param forename forename
     */
    public void setForename(final String forename) {
        firePropertyChange("forename", this.forename, forename);
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    /**
     * Setter.
     * 
     * @param surname surname
     */
    public void setSurname(final String surname) {
        firePropertyChange("surname", this.surname, surname);
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    /**
     * Setter.
     * 
     * @param address address
     */
    public void setAddress(final String address) {
        firePropertyChange("address", this.address, address);
        this.address = address;
    }

    @Override
    protected void removeId() {
        id = null;
    }

    @Override
    protected Contact clone() {
        Contact obj = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            obj = (Contact) in.readObject();
            obj.resetDirty();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }

        return obj;
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        Contact other = (Contact) obj;
        if (id != null && other.id != null) {
            return id.equals(other.id);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Contact[" + surname + ", " + forename + "]";
    }

}
