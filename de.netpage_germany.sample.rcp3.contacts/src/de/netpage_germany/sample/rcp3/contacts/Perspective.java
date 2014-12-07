package de.netpage_germany.sample.rcp3.contacts;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * A perspective generates the initial page layout and visible action set for a page.
 * 
 * @author Denis Wirries
 */
public class Perspective implements IPerspectiveFactory {

    /**
     * Perspective-ID.
     */
    public static final String ID = "de.netpage_germany.sample.rcp3.contacts.perspective";

    private static final float RATIO = 0.4f;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createInitialLayout(final IPageLayout layout) {
        layout.addView(ContactNavigator.ID, IPageLayout.LEFT, RATIO, layout.getEditorArea());
        layout.getViewLayout(ContactNavigator.ID).setCloseable(false);
        layout.getViewLayout(ContactNavigator.ID).setMoveable(false);
    }

}
