package de.netpage_germany.sample.rcp3.contacts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This is an abstract class for adding changeSupport in a modell class.
 * 
 * @author Denis Wirries
 */
public abstract class ModelChangeSupport {

    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private boolean dirty;

    /**
     * Constructor add a property change listner for logging the change (dirty flag).
     */
    public ModelChangeSupport() {
        addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                dirty = true;
            }
        });
    }

    /**
     * Add an property change listner for the model.
     * 
     * @param listener property change listner
     */
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Removes the listner from model.
     * 
     * @param listener property change listner
     */
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Add an property change listner for a model attribute.
     * 
     * @param propertyName name of the model attribute
     * @param listener property change listner
     */
    public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Removes the listner from the model-attributes.
     * 
     * @param propertyName name of the model attribute
     * @param listener property change listner
     */
    public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Fires the event on change event.
     * 
     * @param propertyName name of the model attribute
     * @param oldValue old value
     * @param newValue new value
     */
    protected void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Returns the state of the model.
     * 
     * @return true if model is dirty.
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Set the dirty-state back.
     */
    public void resetDirty() {
        dirty = false;
    }

    /**
     * Sets the dirty-state and removed the id.
     */
    public void setNew() {
        dirty = true;
        removeId();
    }

    /**
     * Removes the id form the model-object.
     */
    protected abstract void removeId();

}