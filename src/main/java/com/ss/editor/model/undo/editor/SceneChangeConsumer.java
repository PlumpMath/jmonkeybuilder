package com.ss.editor.model.undo.editor;

import com.ss.editor.annotation.FxThread;
import com.ss.editor.extension.scene.SceneNode;
import com.ss.editor.extension.scene.app.state.SceneAppState;
import com.ss.editor.extension.scene.filter.SceneFilter;

import org.jetbrains.annotations.NotNull;

/**
 * The interface to notify about any changes of a scene.
 *
 * @author JavaSaBr
 */
public interface SceneChangeConsumer extends ModelChangeConsumer {

    /**
     * Notify about added an app state.
     *
     * @param appState the app state
     */
    @FxThread
    void notifyAddedAppState(@NotNull SceneAppState appState);

    /**
     * Notify about removed an app state.
     *
     * @param appState the app state
     */
    @FxThread
    void notifyRemovedAppState(@NotNull SceneAppState appState);

    /**
     * Notify about changed an app state.
     *
     * @param appState the app state
     */
    @FxThread
    void notifyChangedAppState(@NotNull SceneAppState appState);

    /**
     * Notify about added a filter.
     *
     * @param sceneFilter the scene filter
     */
    @FxThread
    void notifyAddedFilter(@NotNull SceneFilter sceneFilter);

    /**
     * Notify about removed a filter.
     *
     * @param sceneFilter the scene filter
     */
    @FxThread
    void notifyRemovedFilter(@NotNull SceneFilter sceneFilter);

    /**
     * Notify about changed a filter.
     *
     * @param sceneFilter the scene filter
     */
    @FxThread
    void notifyChangedFilter(@NotNull SceneFilter sceneFilter);

    @Override
    @NotNull SceneNode getCurrentModel();
}
