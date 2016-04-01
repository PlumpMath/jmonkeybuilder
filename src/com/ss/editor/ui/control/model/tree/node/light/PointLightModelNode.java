package com.ss.editor.ui.control.model.tree.node.light;

import com.jme3.light.PointLight;
import com.ss.editor.Messages;
import com.ss.editor.ui.Icons;

import javafx.scene.image.Image;

/**
 * Реализация узла для точки источника света.
 *
 * @author Ronn
 */
public class PointLightModelNode extends LightModelNode<PointLight> {

    public PointLightModelNode(final PointLight element, final long objectId) {
        super(element, objectId);
    }

    @Override
    public Image getIcon() {
        return Icons.POINT_16;
    }

    @Override
    public String getName() {
        return Messages.MODEL_FILE_EDITOR_NODE_POINT_LIGHT;
    }
}