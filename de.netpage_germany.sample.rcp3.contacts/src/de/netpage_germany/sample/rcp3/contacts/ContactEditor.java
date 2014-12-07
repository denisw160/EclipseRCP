package de.netpage_germany.sample.rcp3.contacts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

/**
 * This class is the editor for a contact.
 * 
 * @author Denis Wirries
 */
public class ContactEditor extends EditorPart {

    /**
     * Id of the editor.
     */
    public static final String ID = "de.netpage_germany.sample.rcp3.contacts.contacteditor"; //$NON-NLS-1$

    private static final int ADDRESS_HEIGHT = 70;

    private static final int MARGIN_TOP = 15;
    private static final int MARGIN_LEFT = 10;
    private static final int MARIGN_RIGHT = 10;

    private static final IContactStore STORE = ContactStoreFactory.getInstance();

    private Contact contact;

    private Text forenameText;
    private Text surnameText;
    private Text addressText;

    private DataBindingContext bindingContext;

    /**
     * Default constructor.
     */
    public ContactEditor() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createPartControl(final Composite parent) {
        final Composite container = new Composite(parent, SWT.NONE);
        GridLayout containerLayout = new GridLayout(2, false);
        containerLayout.marginRight = MARIGN_RIGHT;
        containerLayout.marginLeft = MARGIN_LEFT;
        containerLayout.marginTop = MARGIN_TOP;
        container.setLayout(containerLayout);

        // 1. line - forname
        Label forenameLabel = new Label(container, SWT.NONE);
        forenameLabel.setText(Messages.getString("label.forename"));
        forenameText = new Text(container, SWT.BORDER);
        forenameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        // 2. line - surname
        Label surnameLabel = new Label(container, SWT.NONE);
        surnameLabel.setText(Messages.getString("label.surname"));
        surnameText = new Text(container, SWT.BORDER);
        surnameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        // 3. line - address
        Label addressLabel = new Label(container, SWT.NONE);
        addressLabel.setText(Messages.getString("label.address"));
        addressText = new Text(container, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.CANCEL | SWT.MULTI);
        GridData addressTextGrid = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
        addressTextGrid.heightHint = ADDRESS_HEIGHT;
        addressText.setLayoutData(addressTextGrid);

        // Init the binding an the change event
        initDataBindings();
        contact.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                firePropertyChange(PROP_DIRTY);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFocus() {
        forenameText.setFocus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSave(final IProgressMonitor monitor) {
        final int totalWork = 4;
        int i = 1;
        monitor.beginTask("save", totalWork);

        // Store contact
        STORE.store(contact);
        monitor.worked(i++);

        // Reset dirty status
        firePropertyChange(PROP_DIRTY);
        monitor.worked(i++);

        // Update view
        IWorkbenchWindow window = getSite().getWorkbenchWindow();
        IWorkbenchPage page = window.getActivePage();
        ContactNavigator view = (ContactNavigator) page.findView(ContactNavigator.ID);
        view.loadData();
        monitor.worked(i++);

        // Update partname
        updatePartName();
        monitor.worked(i++);

        monitor.done();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSaveAs() {
        // Do the Save As operation
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
        if (!(input instanceof ContactEditorInput)) {
            throw new RuntimeException("Wrong input");
        }

        if (input.getName() != ContactEditorInput.NEW_ID) {
            contact = STORE.get(input.getName());
        } else {
            contact = new Contact();
            contact.setNew();
        }

        setSite(site);
        setInput(input);
        updatePartName();
    }

    /**
     * Updates the partname.
     */
    private void updatePartName() {
        if (contact.getForename() == null || contact.getSurname() == null) {
            setPartName(Messages.getString("label.newcontact"));
        } else {
            setPartName(contact.getForename() + " " + contact.getSurname());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDirty() {
        return contact.isDirty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    /**
     * Initialise the data binding.
     */
    protected void initDataBindings() {
        bindingContext = new DataBindingContext();
        //
        IObservableValue observeTextForenameTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(forenameText);
        IObservableValue forenameContactObserveValue = PojoProperties.value("forename").observe(contact);
        bindingContext.bindValue(observeTextForenameTextObserveWidget, forenameContactObserveValue, null, null);
        //
        IObservableValue observeTextSurnameTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(surnameText);
        IObservableValue surnameContactObserveValue = PojoProperties.value("surname").observe(contact);
        bindingContext.bindValue(observeTextSurnameTextObserveWidget, surnameContactObserveValue, null, null);
        //
        IObservableValue observeTextAddressTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(addressText);
        IObservableValue addressContactObserveValue = PojoProperties.value("address").observe(contact);
        bindingContext.bindValue(observeTextAddressTextObserveWidget, addressContactObserveValue, null, null);
    }
}
