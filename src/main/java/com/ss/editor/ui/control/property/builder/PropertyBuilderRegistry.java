package com.ss.editor.ui.control.property.builder;

import com.ss.editor.annotation.FxThread;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.model.undo.editor.ChangeConsumer;
import com.ss.editor.ui.control.property.builder.impl.*;
import com.ss.rlib.common.util.array.Array;
import com.ss.rlib.common.util.array.ArrayFactory;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The factory to build property controls for an object.
 *
 * @author JavaSaBr
 */
public class PropertyBuilderRegistry {

    @NotNull
    private static final PropertyBuilderRegistry INSTANCE = new PropertyBuilderRegistry();

    @FromAnyThread
    public static @NotNull PropertyBuilderRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * The list of property builders.
     */
    @NotNull
    private final Array<PropertyBuilder> builders;

    /**
     * THe list of filters.
     */
    @NotNull
    private final Array<PropertyBuilderFilter> filters;

    private PropertyBuilderRegistry() {
        builders = ArrayFactory.newArray(PropertyBuilder.class);
        filters = ArrayFactory.newArray(PropertyBuilderFilter.class);
        register(AudioNodePropertyBuilder.getInstance());
        register(ParticleEmitterPropertyBuilder.getInstance());
        register(GeometryPropertyBuilder.getInstance());
        register(LightPropertyBuilder.getInstance());
        register(SpatialPropertyBuilder.getInstance());
        register(SceneAppStatePropertyBuilder.getInstance());
        register(SceneFilterPropertyBuilder.getInstance());
        register(DefaultControlPropertyBuilder.getInstance());
        register(EditableControlPropertyBuilder.getInstance());
        register(CollisionShapePropertyBuilder.getInstance());
        register(PrimitivePropertyBuilder.getInstance());
        register(MeshPropertyBuilder.getInstance());
        register(MaterialPropertyBuilder.getInstance());
        register(ParticleInfluencerPropertyBuilder.getInstance());
        register(EmitterShapePropertyBuilder.getInstance());
        register(MaterialSettingsPropertyBuilder.getInstance());
    }

    /**
     * Register a new property builder.
     *
     * @param builder the property builder.
     */
    @FromAnyThread
    public void register(@NotNull PropertyBuilder builder) {
        builders.add(builder);
        builders.sort(PropertyBuilder::compareTo);
    }

    /**
     * Register a new property builder filter.
     *
     * @param filter the property builder filter.
     */
    @FromAnyThread
    public void register(@NotNull PropertyBuilderFilter filter) {
        filters.add(filter);
    }

    /**
     * Build properties controls for the object to the container.
     *
     * @param object         the object to build property controls.
     * @param parent         the parent of the object.
     * @param container      the container for containing these controls.
     * @param changeConsumer the consumer to work between controls and editor.
     */
    @FxThread
    public void buildFor(
            @NotNull Object object,
            @Nullable Object parent,
            @NotNull VBox container,
            @NotNull ChangeConsumer changeConsumer
    ) {

        for (var builder : builders) {

            boolean needSkip = false;

            for (var filter : filters) {
                if (filter.skip(builder, object, parent)) {
                    needSkip = true;
                    break;
                }
            }

            if (needSkip) {
                continue;
            }

            builder.buildFor(object, parent, container, changeConsumer);
        }
    }
}
