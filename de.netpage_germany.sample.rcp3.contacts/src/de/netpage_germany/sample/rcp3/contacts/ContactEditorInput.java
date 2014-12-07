package de.netpage_germany.sample.rcp3.contacts;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * This is the input for the contact editor.
 * 
 * @author Denis Wirries
 */
public class ContactEditorInput implements IEditorInput {

    /**
     * ID for new contacts.
     */
    public static final String NEW_ID = "NEW";

    private final String id;

    /**
     * Creates an editor input for the contact id.
     * 
     * @param id id of the contact
     */
    public ContactEditorInput(final String id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImageDescriptor getImageDescriptor() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPersistableElement getPersistable() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getToolTipText() {
        return "Displays a contact";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("rawtypes")
    public Object getAdapter(final Class adapter) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ContactEditorInput other = (ContactEditorInput) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}
