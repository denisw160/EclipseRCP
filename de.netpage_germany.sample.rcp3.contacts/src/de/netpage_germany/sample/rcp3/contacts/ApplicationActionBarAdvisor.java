package de.netpage_germany.sample.rcp3.contacts;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * Class for configuring the action bars of a workbench window. <br/>
 * An application should declare a subclass of <code>ActionBarAdvisor</code> and override methods to configure a
 * window's action bars to suit the needs of the particular application.
 * 
 * @author Denis Wirries
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    /**
     * Menu id for file menu.
     */
    private static final String M_FILE = IWorkbenchActionConstants.M_FILE;

    /**
     * Menu id for file menu.
     */
    private static final String M_HELP = IWorkbenchActionConstants.M_HELP;

    /**
     * Group id for save group.
     */
    private static final String SAVE_GROUP = IWorkbenchActionConstants.SAVE_GROUP;

    /**
     * Save the current editor.
     */
    private IWorkbenchAction saveAction;

    /**
     * Quit the application.
     */
    private IWorkbenchAction quitAction;

    /**
     * Open the about dialog.
     */
    private IWorkbenchAction aboutAction;

    /**
     * The default constructor.
     * 
     * @param configurer @see {@link IActionBarConfigurer}
     */
    public ApplicationActionBarAdvisor(final IActionBarConfigurer configurer) {
        super(configurer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void makeActions(final IWorkbenchWindow window) {
        // menu file
        saveAction = ActionFactory.SAVE.create(window);
        register(saveAction);

        quitAction = ActionFactory.QUIT.create(window);
        register(quitAction);

        // menu help
        aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillMenuBar(final IMenuManager menuBar) {
        // Add file menu
        MenuManager menuManagerFile = new MenuManager(Messages.getString("menu.file"), M_FILE);
        menuBar.add(menuManagerFile);

        menuManagerFile.add(saveAction);
        menuManagerFile.add(new Separator());
        menuManagerFile.add(quitAction);

        // Add help menu
        MenuManager menuManagerHelp = new MenuManager(Messages.getString("menu.help"), M_HELP);
        menuBar.add(menuManagerHelp);

        menuManagerHelp.add(aboutAction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillCoolBar(final ICoolBarManager coolBar) {
        // File Group
        IToolBarManager fileToolBar = new ToolBarManager(SWT.FLAT);
        fileToolBar.add(new GroupMarker(SAVE_GROUP));
        fileToolBar.add(saveAction);
        coolBar.add(fileToolBar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillStatusLine(final IStatusLineManager statusLine) {
    }

}
