package com.ss.editor.ui.control.tree.node.impl.control.anim;

import com.jme3.animation.SpatialTrack;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.ui.Icons;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The implementation of node to show {@link SpatialTrack}.
 *
 * @author JavaSaBr
 */
public class AnimationSpatialTrackTreeNode extends AnimationTrackTreeNode<SpatialTrack> {

    public AnimationSpatialTrackTreeNode(@NotNull final SpatialTrack element, final long objectId) {
        super(element, objectId);
    }

    @Override
    @FxThread
    protected @NotNull String computeName() {
        return "Spatial track";
    }

    @Override
    @FxThread
    public @Nullable Image getIcon() {
        return Icons.NODE_16;
    }
}
