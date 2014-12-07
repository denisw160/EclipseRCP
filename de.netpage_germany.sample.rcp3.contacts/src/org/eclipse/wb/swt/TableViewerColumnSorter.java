/*******************************************************************************
 * Copyright (c) 2011 Google, Inc. All rights reserved. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Google, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.wb.swt;

import java.lang.reflect.Method;

import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;

/**
 * Helper for sorting {@link TableViewer} by one of its {@link TableViewerColumn}s.
 * <p>
 * Originally from http://wiki.eclipse.org/index.php/JFaceSnippets, Snippet040TableViewerSorting.
 * 
 * @author Tom Schindl <tom.schindl@bestsolution.at>
 * @author Konstantin Scheglov <Konstantin.Scheglov@gmail.com>
 */
public class TableViewerColumnSorter extends ViewerComparator {
    /** Sort ASC. */
    public static final int ASC = 1;
    /** Sort NONE. */
    public static final int NONE = 0;
    /** Sort DESC. */
    public static final int DESC = -1;
    // //////////////////////////////////////////////////////////////////////////
    //
    // Instance fields
    //
    // //////////////////////////////////////////////////////////////////////////
    private final TableViewerColumn mcolumn;
    private final TableViewer mviewer;
    private final Table mtable;
    private int mdirection = NONE;

    // //////////////////////////////////////////////////////////////////////////
    //
    // Constructor
    //
    // //////////////////////////////////////////////////////////////////////////
    /**
     * Default constructor.
     * 
     * @param column column for binding.
     */
    public TableViewerColumnSorter(final TableViewerColumn column) {
        mcolumn = column;
        mviewer = (TableViewer) column.getViewer();
        mtable = mviewer.getTable();
        mcolumn.getColumn().addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                if (mviewer.getComparator() != null) {
                    if (mviewer.getComparator() == TableViewerColumnSorter.this) {
                        if (mdirection == ASC) {
                            setSorter(DESC);
                        } else if (mdirection == DESC) {
                            setSorter(NONE);
                        }
                    } else {
                        setSorter(ASC);
                    }
                } else {
                    setSorter(ASC);
                }
            }
        });
    }

    // //////////////////////////////////////////////////////////////////////////
    //
    // Utils
    //
    // //////////////////////////////////////////////////////////////////////////
    /**
     * Set sort direction.
     * 
     * @param direction sort direction
     */
    public void setSorter(final int direction) {
        if (direction == NONE) {
            mtable.setSortColumn(null);
            mtable.setSortDirection(SWT.NONE);
            mviewer.setComparator(null);
        } else {
            mtable.setSortColumn(mcolumn.getColumn());
            mdirection = direction;
            if (mdirection == ASC) {
                mtable.setSortDirection(SWT.DOWN);
            } else {
                mtable.setSortDirection(SWT.UP);
            }
            if (mviewer.getComparator() == this) {
                mviewer.refresh();
            } else {
                mviewer.setComparator(this);
            }
        }
    }

    // //////////////////////////////////////////////////////////////////////////
    //
    // ViewerComparator
    //
    // //////////////////////////////////////////////////////////////////////////

    /**
     * Compare.
     * 
     * @param viewer column viewer
     * @param e1 object 1
     * @param e2 object 2
     * @return sort order
     */
    @Override
    public int compare(final Viewer viewer, final Object e1, final Object e2) {
        return mdirection * doCompare(viewer, e1, e2);
    }

    /**
     * Compares to elements of viewer. By default tries to compare values extracted from these elements using
     * {@link #getValue(Object)}, because usually you want to compare value of some attribute.
     * 
     * @param viewer column viewer
     * @param e1 object 1
     * @param e2 object 2
     * @return sort order
     */
    @SuppressWarnings("unchecked")
    protected int doCompare(final Viewer viewer, final Object e1, final Object e2) {
        Object o1 = getValue(e1);
        Object o2 = getValue(e2);
        if (o1 instanceof Comparable && o2 instanceof Comparable) {
            return ((Comparable<Object>) o1).compareTo(o2);
        }
        return 0;
    }

    /**
     * Get the value of the object.
     * 
     * @param o Object
     * @return the value to compare in {@link #doCompare(Viewer, Object, Object)}. Be default tries to get it from
     *         {@link EditingSupport}. May return <code>null</code>.
     */
    protected Object getValue(final Object o) {
        try {
            EditingSupport editingSupport;
            Method getEditingMethod = ViewerColumn.class.getDeclaredMethod("getEditingSupport", new Class[] {});
            getEditingMethod.setAccessible(true);
            editingSupport = (EditingSupport) getEditingMethod.invoke(mcolumn, new Object[] {});
            if (editingSupport != null) {
                Method getValueMethod = EditingSupport.class
                        .getDeclaredMethod("getValue", new Class[] { Object.class });
                getValueMethod.setAccessible(true);
                return getValueMethod.invoke(editingSupport, new Object[] { o });
            }
        } catch (Throwable e) {
            // ignore
            e.getMessage();
        }
        return null;
    }
}
