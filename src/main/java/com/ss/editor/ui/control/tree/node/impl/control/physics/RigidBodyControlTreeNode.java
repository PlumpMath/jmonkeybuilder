package com.ss.editor.ui.control.tree.node.impl.control.physics;

import com.jme3.bullet.control.RigidBodyControl;
import com.ss.editor.Messages;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.ui.Icons;
import com.ss.editor.ui.control.tree.action.impl.control.physics.ReactivatePhysicsControlAction;
import com.ss.editor.ui.control.tree.NodeTree;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The implementation of node to show {@link RigidBodyControl}.
 *
 * @author JavaSaBr
 */
public class RigidBodyControlTreeNode extends PhysicsControlTreeNode<RigidBodyControl> {

    public RigidBodyControlTreeNode(@NotNull final RigidBodyControl element, final long objectId) {
        super(element, objectId);
    }

    @Override
    @FxThread
    public @Nullable Image getIcon() {

        final RigidBodyControl element = getElement();

        if (element.getMass() == 0F) {
            return Icons.STATIC_RIGID_BODY_16;
        }

        return Icons.RIGID_BODY_16;
    }

    @Override
    @FromAnyThread
    public @NotNull String getName() {

        final RigidBodyControl element = getElement();

        if (element.getMass() == 0F) {
            return Messages.MODEL_FILE_EDITOR_NODE_STATIC_RIGID_BODY_CONTROL;
        }

        return Messages.MODEL_FILE_EDITOR_NODE_RIGID_BODY_CONTROL;
    }

    @Override
    @FxThread
    public void fillContextMenu(@NotNull final NodeTree<?> nodeTree,
                                @NotNull final ObservableList<MenuItem> items) {

        final RigidBodyControl element = getElement();

        if (!element.isActive()) {
            items.add(new ReactivatePhysicsControlAction(nodeTree, this));
        }

        super.fillContextMenu(nodeTree, items);
    }
}
