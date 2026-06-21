package tanukibear.ragdollawfix.client;

import java.lang.reflect.Method;

public final class SableRagdollRenderContext {
    private static final String RAGDOLL_PART_RENDERER_CLASS =
            "dev.leo.sableplayerragdoll.neoforge.client.RagdollPartBlockEntityRenderer";

    private static Method activeBodyPartMethod;
    private static boolean lookupFailed;

    private SableRagdollRenderContext() {
    }

    public static String activeBodyPartName() {
        var method = activeBodyPartMethod();
        if (method == null) {
            return null;
        }
        try {
            var bodyPart = method.invoke(null);
            if (bodyPart instanceof Enum<?> enumValue) {
                return enumValue.name();
            }
            return bodyPart == null ? null : bodyPart.toString();
        } catch (ReflectiveOperationException | LinkageError ignored) {
            return null;
        }
    }

    private static Method activeBodyPartMethod() {
        if (lookupFailed) {
            return null;
        }
        if (activeBodyPartMethod != null) {
            return activeBodyPartMethod;
        }
        try {
            activeBodyPartMethod = Class.forName(RAGDOLL_PART_RENDERER_CLASS).getMethod("activeBodyPart");
            return activeBodyPartMethod;
        } catch (ReflectiveOperationException | LinkageError ignored) {
            lookupFailed = true;
            return null;
        }
    }
}
