package org.fs.qm.events;

import org.fs.common.IEvent;

/**
 * Created by Fatih on 18/06/16.
 * as org.fs.qm.events.ColumnScrollEvent
 */
public final class ColumnScrollEvent implements IEvent {

    public final int dx;
    public final int dy;

    public ColumnScrollEvent(final int dx, final int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}