package com.ss.builder.fx.control.tree.node.factory.impl;

import static com.ss.rlib.common.util.ClassUtils.unsafeCast;
import com.ss.builder.annotation.FxThread;
import com.ss.builder.model.node.material.*;
import com.ss.builder.annotation.FxThread;
import com.ss.builder.model.node.material.*;
import com.ss.builder.fx.control.tree.node.TreeNode;
import com.ss.builder.fx.control.tree.node.factory.TreeNodeFactory;
import com.ss.builder.fx.control.tree.node.impl.material.settings.*;
import org.jetbrains.annotations.Nullable;

/**
 * The factory to create material settings nodes.
 *
 * @author JavaSaBr
 */
public class MaterialSettingsTreeNodeFactory implements TreeNodeFactory {

    @Override
    @FxThread
    public <T, V extends TreeNode<T>> @Nullable V createFor(@Nullable final T element, final long objectId) {

        if (element instanceof RootMaterialSettings) {
            return unsafeCast(new RootMaterialSettingsTreeNode((RootMaterialSettings) element, objectId));
        } else if (element instanceof TexturesSettings) {
            return unsafeCast(new TexturesSettingsTreeNode((TexturesSettings) element, objectId));
        } else if (element instanceof ColorsSettings) {
            return unsafeCast(new ColorsSettingsTreeNode((ColorsSettings) element, objectId));
        } else if (element instanceof RenderSettings) {
            return unsafeCast(new RenderSettingsTreeNode((RenderSettings) element, objectId));
        } else if (element instanceof OtherSettings) {
            return unsafeCast(new OtherSettingsTreeNode((OtherSettings) element, objectId));
        }

        return null;
    }
}