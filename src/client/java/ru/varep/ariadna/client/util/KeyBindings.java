package ru.varep.ariadna.client.util;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import java.util.List;

public class KeyBindings {
    public static final KeyBinding RESULT_KEY = new KeyBinding(
            "key.ariadna.result",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_HOME,
            "category.ariadna.main");
    public static KeyBinding SUM_KEY = new KeyBinding(
            "key.ariadna.sum",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_END,
            "category.ariadna.main");

    public static final List<KeyBinding> ALL = List.of(
            RESULT_KEY,
            SUM_KEY
    );
}
