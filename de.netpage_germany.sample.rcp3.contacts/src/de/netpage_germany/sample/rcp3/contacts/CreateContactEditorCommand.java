package de.netpage_germany.sample.rcp3.contacts;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * CommandHandler for open the contact editor.
 * 
 * @author Denis Wirries
 */
public class CreateContactEditorCommand extends AbstractHandler implements IHandler {

    /**
     * ID of the command.
     */
    public static final String ID = "de.netpage_germany.sample.rcp3.contacts.createcontacteditorcommand";

    /**
     * {@inheritDoc}
     */
    @Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {
        // get the page
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
        IWorkbenchPage page = window.getActivePage();

        // open the editor with null id
        ContactEditorInput input = new ContactEditorInput(ContactEditorInput.NEW_ID);
        try {
            page.openEditor(input, ContactEditor.ID);
        } catch (PartInitException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
