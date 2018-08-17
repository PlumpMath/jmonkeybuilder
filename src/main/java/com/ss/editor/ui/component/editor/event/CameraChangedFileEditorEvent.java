package com.ss.editor.ui.component.editor.event;

import com.ss.editor.part3d.editor.control.impl.CameraEditor3dPartControl.CameraState;
import org.jetbrains.annotations.NotNull;

/**
 * The event about that editor's camera was changed.
 *
 * @author JavaSaBr
 */
public class CameraChangedFileEditorEvent extends AbstractFileEditorEvent {

    /**
     * The new camera state.
     */
    @NotNull
    private final CameraState cameraState;

    public CameraChangedFileEditorEvent(@NotNull CameraState cameraState) {
        this.cameraState = cameraState;
    }

    /**
     * Get the new camera state.
     *
     * @return the new camera state.
     */
    public @NotNull CameraState getCameraState() {
        return cameraState;
    }
}
