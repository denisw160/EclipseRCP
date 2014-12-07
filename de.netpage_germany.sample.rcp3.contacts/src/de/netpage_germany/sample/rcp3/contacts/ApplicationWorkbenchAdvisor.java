package de.netpage_germany.sample.rcp3.contacts;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * Class for configuring the workbench. <br/>
 * Note that the workbench advisor object is created in advance of creating the workbench. However, by the time the
 * workbench starts calling methods on this class, <code>PlatformUI.getWorkbench</code> is guaranteed to have been
 * properly initialized.
 * 
 * @author Denis.Wirries
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

    /** Perspective for application start. */
    private static final String PERSPECTIVE_ID = "de.netpage_germany.sample.rcp3.contacts.perspective"; //$NON-NLS-1$

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(final IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInitialWindowPerspectiveId() {
        return PERSPECTIVE_ID;
    }

}
