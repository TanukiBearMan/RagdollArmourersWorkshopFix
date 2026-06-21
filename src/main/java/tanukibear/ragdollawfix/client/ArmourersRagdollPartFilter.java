package tanukibear.ragdollawfix.client;

import java.util.Locale;
import java.util.Set;

public final class ArmourersRagdollPartFilter {
    private static final Set<String> HEAD_PARTS = Set.of(
            "armourers:head.base",
            "armourers:hat.base"
    );

    private static final Set<String> TORSO_PARTS = Set.of(
            "armourers:chest.base",
            "armourers:chest.base2",
            "armourers:legs.skirt",
            "armourers:wings.leftWing",
            "armourers:wings.rightWing",
            "armourers:wings.leftWing2",
            "armourers:wings.rightWing2",
            "armourers:backpack.base",
            "armourers:part.advanced_part",
            "armourers:part.locator",
            "armourers:part.static",
            "armourers:part.float"
    );

    private static final Set<String> LEFT_ARM_PARTS = Set.of(
            "armourers:chest.leftArm",
            "armourers:chest.leftArm2"
    );

    private static final Set<String> RIGHT_ARM_PARTS = Set.of(
            "armourers:chest.rightArm",
            "armourers:chest.rightArm2"
    );

    private static final Set<String> LEFT_LEG_PARTS = Set.of(
            "armourers:legs.leftLeg",
            "armourers:legs.leftLeg2",
            "armourers:feet.leftFoot"
    );

    private static final Set<String> RIGHT_LEG_PARTS = Set.of(
            "armourers:legs.rightLeg",
            "armourers:legs.rightLeg2",
            "armourers:feet.rightFoot"
    );

    private ArmourersRagdollPartFilter() {
    }

    public static boolean shouldRender(String ragdollBodyPartName, String armourersPartTypeName) {
        var bodyPart = normalize(ragdollBodyPartName);
        var partType = normalize(armourersPartTypeName);

        return switch (bodyPart) {
            case "HEAD" -> HEAD_PARTS.contains(partType);
            case "LEFT_ARM" -> LEFT_ARM_PARTS.contains(partType);
            case "RIGHT_ARM" -> RIGHT_ARM_PARTS.contains(partType);
            case "LEFT_LEG" -> LEFT_LEG_PARTS.contains(partType);
            case "RIGHT_LEG" -> RIGHT_LEG_PARTS.contains(partType);
            case "TORSO" -> TORSO_PARTS.contains(partType) || isUnknownPart(partType);
            default -> false;
        };
    }

    public static boolean shouldSuppress(String ragdollBodyPartName, String armourersPartTypeName) {
        var bodyPart = normalize(ragdollBodyPartName);
        if (bodyPart.isEmpty()) {
            return false;
        }
        return !shouldRender(bodyPart, armourersPartTypeName);
    }

    private static boolean isUnknownPart(String partType) {
        return !HEAD_PARTS.contains(partType)
                && !TORSO_PARTS.contains(partType)
                && !LEFT_ARM_PARTS.contains(partType)
                && !RIGHT_ARM_PARTS.contains(partType)
                && !LEFT_LEG_PARTS.contains(partType)
                && !RIGHT_LEG_PARTS.contains(partType);
    }

    private static String normalize(String value) {
        if (value == null) {
            return "";
        }
        var normalized = value.trim();
        if (normalized.indexOf(':') >= 0 || normalized.indexOf('.') >= 0) {
            return normalized;
        }
        return normalized.toUpperCase(Locale.ROOT);
    }
}
