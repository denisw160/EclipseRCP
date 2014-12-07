package de.netpage_germany.sample.rcp3.contacts;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * Class for configuring a workbench window. <br/>
 * The workbench window advisor object is created in response to a workbench window being created (one per window), and
 * is used to configure the window.
 * 
 * @author Denis Wirries
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    private static final int HEIGTH = 400;
    private static final int WIDTH = 600;

    /**
     * Default constructor.
     * 
     * @param configurer @see {@link IWorkbenchWindowConfigurer}
     */
    public ApplicationWorkbenchWindowAdvisor(final IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionBarAdvisor createActionBarAdvisor(final IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(WIDTH, HEIGTH));
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(false);
        configurer.setTitle(Messages.getString("application.title")); //$NON-NLS-1$
    }

}
