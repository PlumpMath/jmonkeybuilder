package com.ss.editor.filter;

import com.jme3.post.filters.FXAAFilter;
import com.jme3.texture.Texture;
import org.jetbrains.annotations.NotNull;

/**
 * The changed implementation of {@link FXAAFilter} for this editor.
 *
 * @author JavaSaBr
 */
public class EditorFxaaFilter extends FXAAFilter {

    @Override
    protected boolean isRequiresDepthTexture() {
        return true;
    }

    @Override
    protected void setDepthTexture(@NotNull final Texture depthTexture) {
    }
}
