package tanukibear.ragdollawfix.client;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ArmourersSkinPartElementState {
    private static final Map<String, Method> METHOD_CACHE = new ConcurrentHashMap<>();

    private ArmourersSkinPartElementState() {
    }

    public static boolean shouldSuppressPart(Object part) {
        var bodyPartName = SableRagdollRenderContext.activeBodyPartName();
        if (bodyPartName == null || bodyPartName.isBlank() || part == null) {
            return false;
        }

        try {
            return ArmourersRagdollPartFilter.shouldSuppress(bodyPartName, partTypeName(part));
        } catch (ReflectiveOperationException | RuntimeException | LinkageError ignored) {
            return false;
        }
    }

    private static String partTypeName(Object part) throws ReflectiveOperationException {
        var type = invoke(part, "type");
        try {
            var name = invoke(type, "name");
            return name == null ? "" : name.toString();
        } catch (NoSuchMethodException ignored) {
            return type == null ? "" : type.toString();
        }
    }

    private static Object invoke(Object target, String methodName) throws ReflectiveOperationException {
        return method(target.getClass(), methodName).invoke(target);
    }

    private static Method method(Class<?> owner, String methodName, Class<?>... parameterTypes)
            throws NoSuchMethodException {
        var key = owner.getName() + "#" + methodName + "#" + parameterKey(parameterTypes);
        var cached = METHOD_CACHE.get(key);
        if (cached != null) {
            return cached;
        }
        var method = owner.getMethod(methodName, parameterTypes);
        try {
            method.setAccessible(true);
        } catch (RuntimeException ignored) {
            // Public access usually works; setAccessible is just a best-effort module boundary bypass.
        }
        METHOD_CACHE.put(key, method);
        return method;
    }

    private static String parameterKey(Class<?>[] parameterTypes) {
        if (parameterTypes.length == 0) {
            return "";
        }
        var builder = new StringBuilder();
        for (var parameterType : parameterTypes) {
            if (builder.length() != 0) {
                builder.append(',');
            }
            builder.append(parameterType.getName());
        }
        return builder.toString();
    }
}
