package tanukibear.ragdollawfix.client;

import net.minecraft.world.entity.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ArmourersBodyPartOverride {
    private static final String ENTITY_RENDER_DATA_CLASS =
            "moe.plushie.armourers_workshop.core.client.other.EntityRenderData";
    private static final String SKIN_PROPERTY_CLASS =
            "moe.plushie.armourers_workshop.core.skin.property.SkinProperty";

    private static final Map<String, Object> PROPERTY_CACHE = new ConcurrentHashMap<>();
    private static Method entityRenderDataOf;
    private static Method tick;
    private static Method overriddenManager;
    private static Method contains;
    private static boolean lookupFailed;

    private ArmourersBodyPartOverride() {
    }

    public static boolean shouldHide(Entity entity, String bodyPartName) {
        var propertyName = propertyName(bodyPartName);
        if (entity == null || propertyName == null || !resolveMethods()) {
            return false;
        }

        try {
            var renderData = entityRenderDataOf.invoke(null, entity);
            tick.invoke(renderData, entity);
            var manager = overriddenManager.invoke(renderData);
            var property = PROPERTY_CACHE.computeIfAbsent(propertyName, ArmourersBodyPartOverride::readProperty);
            return property != null && Boolean.TRUE.equals(contains.invoke(manager, property));
        } catch (ReflectiveOperationException | RuntimeException | LinkageError ignored) {
            return false;
        }
    }

    static String propertyName(String bodyPartName) {
        if (bodyPartName == null) {
            return null;
        }
        return switch (bodyPartName.trim().toUpperCase(Locale.ROOT)) {
            case "HEAD" -> "OVERRIDE_MODEL_HEAD";
            case "TORSO" -> "OVERRIDE_MODEL_CHEST";
            case "LEFT_ARM" -> "OVERRIDE_MODEL_LEFT_ARM";
            case "RIGHT_ARM" -> "OVERRIDE_MODEL_RIGHT_ARM";
            case "LEFT_LEG" -> "OVERRIDE_MODEL_LEFT_LEG";
            case "RIGHT_LEG" -> "OVERRIDE_MODEL_RIGHT_LEG";
            default -> null;
        };
    }

    private static boolean resolveMethods() {
        if (lookupFailed) {
            return false;
        }
        if (entityRenderDataOf != null) {
            return true;
        }
        try {
            var renderDataClass = Class.forName(ENTITY_RENDER_DATA_CLASS);
            var propertyClass = Class.forName(SKIN_PROPERTY_CLASS);
            entityRenderDataOf = renderDataClass.getMethod("of", Entity.class);
            tick = renderDataClass.getMethod("tick", Entity.class);
            overriddenManager = renderDataClass.getMethod("overriddenManager");
            contains = overriddenManager.getReturnType().getMethod("contains", propertyClass);
            return true;
        } catch (ReflectiveOperationException | LinkageError ignored) {
            lookupFailed = true;
            return false;
        }
    }

    private static Object readProperty(String name) {
        try {
            Field field = Class.forName(SKIN_PROPERTY_CLASS).getField(name);
            return field.get(null);
        } catch (ReflectiveOperationException | LinkageError ignored) {
            return null;
        }
    }
}
