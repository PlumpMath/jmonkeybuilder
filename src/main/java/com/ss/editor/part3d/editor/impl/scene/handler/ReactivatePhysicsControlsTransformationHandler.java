package com.ss.editor.part3d.editor.impl.scene.handler;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.scene.Spatial;
import com.ss.editor.util.ControlUtils;
import com.ss.editor.util.NodeUtils;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * The handler to reactivate enabled physics controls during transforming spatial.
 *
 * @author JavaSaBr
 */
public class ReactivatePhysicsControlsTransformationHandler implements Consumer<Spatial> {

    @Override
    public void accept(@NotNull final Spatial spatial) {
        NodeUtils.children(spatial)
                .flatMap(ControlUtils::controls)
                .filter(RigidBodyControl.class::isInstance)
                .map(RigidBodyControl.class::cast)
                .filter(RigidBodyControl::isEnabled)
                .filter(control -> Float.compare(control.getMass(), 0.0F) != 0)
                .filter(control -> !control.isActive())
                .forEach(PhysicsRigidBody::activate);
    }
}
