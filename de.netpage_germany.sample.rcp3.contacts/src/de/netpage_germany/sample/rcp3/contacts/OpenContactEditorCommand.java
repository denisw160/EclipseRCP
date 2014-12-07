package de.netpage_germany.sample.rcp3.contacts;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * CommandHandler for open the contact editor.
 * 
 * @author Denis Wirries
 */
public class OpenContactEditorCommand extends AbstractHandler implements IHandler {

    /**
     * ID of the command.
     */
    public static final String ID = "de.netpage_germany.sample.rcp3.contacts.opencontacteditorcommand";

    /**
     * {@inheritDoc}
     */
    @Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {
        // get the view of ContactNavigator
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
        IWorkbenchPage page = window.getActivePage();
        ContactNavigator view = (ContactNavigator) page.findView(ContactNavigator.ID);

        // get the selection of the view
        ISelection selection = view.getSite().getSelectionProvider().getSelection();
        if (selection != null && selection instanceof IStructuredSelection) {
            Object obj = ((IStructuredSelection) selection).getFirstElement();

            // if we had a selection lets open the editor
            if (obj != null) {
                Contact contact = (Contact) obj;
                ContactEditorInput input = new ContactEditorInput(contact.getId());
                try {
                    page.openEditor(input, ContactEditor.ID);
                } catch (PartInitException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return null;
    }

}
