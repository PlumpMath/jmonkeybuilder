package com.ss.editor.ui.control.layer;

import static com.ss.editor.ui.util.UIUtils.findItemForValue;
import com.jme3.scene.Spatial;
import com.ss.editor.model.undo.editor.SceneChangeConsumer;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.NodeTreeCell;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.editor.extension.scene.SceneLayer;
import javafx.scene.control.TreeItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * The implementation of {@link NodeTree} to present the structure of layers in an editor.
 *
 * @author JavaSaBr
 */
public class LayerNodeTree extends NodeTree<SceneChangeConsumer> {

    /**
     * Instantiates a new Layer node tree.
     *
     * @param selectionHandler the selection handler
     * @param consumer         the consumer
     */
    public LayerNodeTree(@NotNull final Consumer<Object> selectionHandler, @Nullable final SceneChangeConsumer consumer) {
        super(selectionHandler, consumer);
    }

    @NotNull
    @Override
    protected NodeTreeCell<SceneChangeConsumer, ?> createNodeTreeCell() {
        return new LayerNodeTreeCell(this);
    }

    /**
     * Notify about added a spatial.
     *
     * @param spatial the spatial.
     */
    public void notifyAdded(@NotNull final Spatial spatial) {

        final SceneLayer layer = SceneLayer.getLayer(spatial);
        if (layer == SceneLayer.NO_LAYER) return;

        final TreeNode<?> objectNode = FACTORY_REGISTRY.createFor(spatial);
        final TreeItem<TreeNode<?>> newLayerItem = findItemForValue(getTreeView(), FACTORY_REGISTRY.createFor(layer));

        if (newLayerItem != null) {
            newLayerItem.getChildren().add(new TreeItem<>(objectNode));
        }
    }

    /**
     * Notify about changed layer.
     *
     * @param object   the object.
     * @param newLayer the new layer.
     */
    public void notifyChangedLayer(@NotNull final Spatial object, @Nullable final SceneLayer newLayer) {

        final TreeNode<?> objectNode = FACTORY_REGISTRY.createFor(object);
        TreeItem<TreeNode<?>> objectItem = findItemForValue(getTreeView(), objectNode);

        if (objectItem == null && newLayer != null) {
            objectItem = new TreeItem<>(objectNode);
        } else if (objectItem != null) {
            final TreeItem<TreeNode<?>> parent = objectItem.getParent();
            parent.getChildren().remove(objectItem);
        }

        final TreeItem<TreeNode<?>> newLayerItem =
                newLayer == null ? null : findItemForValue(getTreeView(), FACTORY_REGISTRY.createFor(newLayer));

        if (newLayerItem != null) {
            newLayerItem.getChildren().add(objectItem);
        }
    }
}