package com.ss.editor.model.undo;

import com.ss.editor.annotation.FXThread;
import com.ss.editor.annotation.FromAnyThread;

/**
 * The interface to implement an undoable editor.
 *
 * @author JavaSaBr
 */
public interface UndoableEditor {

    /**
     * Increment changes count.
     */
    @FXThread
    void incrementChange();

    /**
     * Decrement changes count.
     */
    @FXThread
    void decrementChange();

    /**
     * Redo the last operation.
     */
    @FromAnyThread
    void redo();

    /**
     * Undo the last operation.
     */
    @FromAnyThread
    void undo();
}
