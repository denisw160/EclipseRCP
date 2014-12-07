package de.netpage_germany.sample.rcp3.contacts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.TableViewerColumnSorter;

/**
 * This class implements the view of the contacts.
 * 
 * @author Denis Wirries
 */
public class ContactNavigator extends ViewPart {

    /**
     * ID of this view.
     */
    public static final String ID = "de.netpage_germany.sample.rcp3.contacts.contactnavigator"; //$NON-NLS-1$

    private static final int COLUMN_WIDTH = 100;

    private static final IContactStore STORE = ContactStoreFactory.getInstance();

    private List<Contact> contacts;

    private Table table;

    private TableViewer tableViewer;

    private Action newAction;
    private Action deleteAction;
    private Action refreshAction;

    /**
     * Default constructor.
     */
    public ContactNavigator() {
        contacts = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createPartControl(final Composite parent) {
        final Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new FillLayout(SWT.NONE));

        // Add Table and TableViewer
        tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
        table = tableViewer.getTable();
        table.setLinesVisible(false);
        table.setHeaderVisible(true);

        // Setup columns
        TableViewerColumn columnViewerForename = new TableViewerColumn(tableViewer, SWT.NONE);
        new ColumnSorter(columnViewerForename, "forename");
        TableColumn columnForename = columnViewerForename.getColumn();
        columnForename.setWidth(COLUMN_WIDTH);
        columnForename.setText(Messages.getString("column.forename"));

        TableViewerColumn columnViewerSurname = new TableViewerColumn(tableViewer, SWT.NONE);
        new ColumnSorter(columnViewerSurname, "surname");
        TableColumn columnSurname = columnViewerSurname.getColumn();
        columnSurname.setWidth(COLUMN_WIDTH);
        columnSurname.setText(Messages.getString("column.surname"));

        // Setup TableViewer
        tableViewer.setSorter(new TableSorter());
        tableViewer.setLabelProvider(new TableLabelProvider());
        tableViewer.setContentProvider(new TableContentProvider());
        getSite().setSelectionProvider(tableViewer);

        // Add Events
        tableViewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
            public void doubleClick(final DoubleClickEvent event) {
                IHandlerService handlerService = (IHandlerService) getSite().getService(IHandlerService.class);
                try {
                    handlerService.executeCommand(OpenContactEditorCommand.ID, null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new RuntimeException(OpenContactEditorCommand.ID + " not found", ex);
                }
            }
        });

        // Initialize other
        createActions();
        initializeToolBar();
        initializeMenu();

        loadData();
    }

    /**
     * Create the actions.
     */
    private void createActions() {
        newAction = new Action(Messages.getString("action.new")) {
            @Override
            public void run() {
                IHandlerService handlerService = (IHandlerService) getSite().getService(IHandlerService.class);
                try {
                    handlerService.executeCommand(CreateContactEditorCommand.ID, null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new RuntimeException(CreateContactEditorCommand.ID + " not found", ex);
                }
            }
        };

        deleteAction = new Action(Messages.getString("action.delete")) {
            @Override
            public void run() {
                ISelection selection = tableViewer.getSelection();
                if (selection != null && selection instanceof IStructuredSelection) {
                    Object obj = ((IStructuredSelection) selection).getFirstElement();

                    // if we had a selection lets open the editor
                    if (obj != null) {
                        Contact contact = (Contact) obj;
                        STORE.delete(contact);
                        loadData();
                    }
                }
            }
        };

        refreshAction = new Action(Messages.getString("action.refresh")) {
            @Override
            public void run() {
                loadData();
            }
        };

    }

    /**
     * Initialize the toolbar.
     */
    private void initializeToolBar() {
        final IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
        toolbarManager.add(refreshAction);
    }

    /**
     * Initialize the menu.
     */
    private void initializeMenu() {
        final IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
        menuManager.add(newAction);
        menuManager.add(deleteAction);
    }

    /**
     * Loads the data.
     */
    public void loadData() {
        contacts = STORE.get();
        tableViewer.setInput(contacts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFocus() {
        table.setFocus();
    }

    /**
     * This is the table label provider for displaying the data.
     * 
     * @author Denis Wirries
     */
    private class TableLabelProvider extends LabelProvider implements ITableLabelProvider {

        /**
         * {@inheritDoc}
         */
        @Override
        public Image getColumnImage(final Object element, final int columnIndex) {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getColumnText(final Object element, final int columnIndex) {
            Contact p = (Contact) element;
            String result = "";
            switch (columnIndex) {
            case 0:
                result = p.getForename();
                break;
            case 1:
                result = p.getSurname();
                break;
            default:
                // should not reach here
                result = "";
            }
            return result;
        }
    }

    /**
     * This is the content provider for displaying the list of contacts.
     * 
     * @author Denis Wirries
     */
    private class TableContentProvider implements IStructuredContentProvider {

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public Object[] getElements(final Object inputElement) {
            List<Contact> list = (List<Contact>) inputElement;
            return list.toArray();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void dispose() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
        }
    }

    /**
     * Sorter for the table.
     * 
     * @author Denis Wirries
     */
    private class TableSorter extends ViewerSorter {

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(final Viewer viewer, final Object e1, final Object e2) {
            final Contact item1 = (Contact) e1;
            final Contact item2 = (Contact) e2;

            int result = 0;
            if (result == 0 && item1.getSurname() != null) {
                result = item1.getSurname().compareTo(item2.getSurname());
            }
            if (result == 0 && item1.getForename() != null) {
                result = item1.getForename().compareTo(item2.getForename());
            }

            return result;
        }
    }

    /**
     * Sorter for the columns.
     * 
     * @author Denis Wirries
     */
    private class ColumnSorter extends TableViewerColumnSorter {

        private final String name;

        public ColumnSorter(final TableViewerColumn column, final String name) {
            super(column);
            this.name = name;
        }

        @Override
        protected int doCompare(final Viewer viewer, final Object e1, final Object e2) {
            final Contact item1 = (Contact) e1;
            final Contact item2 = (Contact) e2;

            if (name.equalsIgnoreCase("surname")) {
                return item1.getSurname().compareTo(item2.getSurname());
            }
            if (name.equalsIgnoreCase("forename")) {
                return item1.getForename().compareTo(item2.getForename());
            }

            return super.doCompare(viewer, e1, e2);
        }

    }

}
