package com.ss.editor.ui.control.tree.action.impl.particle.emitter.influencer;

import com.jme3.effect.influencers.EmptyParticleInfluencer;
import com.jme3.effect.influencers.ParticleInfluencer;
import com.jme3.effect.influencers.RadialParticleInfluencer;
import com.ss.editor.Messages;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import org.jetbrains.annotations.NotNull;

/**
 * The action to create an {@link EmptyParticleInfluencer}.
 *
 * @author JavaSaBr
 */
public class CreateRadialParticleInfluencerAction extends AbstractCreateParticleInfluencerAction {

    public CreateRadialParticleInfluencerAction(@NotNull final NodeTree<?> nodeTree, @NotNull final TreeNode<?> node) {
        super(nodeTree, node);
    }

    @Override
    @FxThread
    protected @NotNull ParticleInfluencer createInfluencer() {
        return new RadialParticleInfluencer();
    }

    @Override
    @FxThread
    protected @NotNull String getName() {
        return Messages.MODEL_NODE_TREE_ACTION_PARTICLE_EMITTER_INFLUENCER_RADIAL;
    }
}
