package com.ss.editor.part3d.editor;

import com.ss.editor.annotation.JmeThread;
import com.ss.editor.ui.component.editor.FileEditor;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * The interface to mark an editor 3d part that it supports a save method.
 *
 * @author JavaSaBr
 */
public interface SavableEditor3dPart extends Editor3dPart {

    /**
     * Save changes.
     */
    @JmeThread
    @NotNull CompletableFuture<FileEditor> save();

    /**
     * Return true if this editor part has unsaved changes.
     *
     * @return true if this editor part has unsaved changes.
     */
    @JmeThread
    boolean isDirty();
}