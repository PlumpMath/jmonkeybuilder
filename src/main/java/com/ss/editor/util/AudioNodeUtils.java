package com.ss.editor.util;

import com.jme3.audio.AudioData;
import com.jme3.audio.AudioKey;
import com.jme3.audio.AudioNode;
import com.ss.editor.annotation.FromAnyThread;
import com.ss.editor.annotation.JmeThread;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

/**
 * The utility class to work with audio nodes.
 *
 * @author JavaSaBr
 */
public class AudioNodeUtils {

    private static final Field AUDIO_DATA_FIELD;
    private static final Field AUDIO_KEY_FIELD;

    static {
        try {

            AUDIO_KEY_FIELD = AudioNode.class.getDeclaredField("audioKey");
            AUDIO_KEY_FIELD.setAccessible(true);

            AUDIO_DATA_FIELD = AudioNode.class.getDeclaredField("data");
            AUDIO_DATA_FIELD.setAccessible(true);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update audio data for an audio node.
     *
     * @param audioNode the audio node.
     * @param audioData the audio data.
     * @param audioKey  the audio key.
     */
    @JmeThread
    public static void updateData(
            @NotNull AudioNode audioNode,
            @Nullable AudioData audioData,
            @Nullable AudioKey audioKey
    ) {
        try {
            AUDIO_DATA_FIELD.set(audioNode, audioData);
            AUDIO_KEY_FIELD.set(audioNode, audioKey);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get an audio key from the audio node.
     *
     * @param audioNode the audio node.
     * @return the audio key.
     */
    @FromAnyThread
    public static @Nullable AudioKey getAudioKey(@NotNull AudioNode audioNode) {
        try {
            return (AudioKey) AUDIO_KEY_FIELD.get(audioNode);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
