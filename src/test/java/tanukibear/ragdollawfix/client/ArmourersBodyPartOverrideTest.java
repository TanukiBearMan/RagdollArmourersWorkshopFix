package tanukibear.ragdollawfix.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ArmourersBodyPartOverrideTest {
    @Test
    void mapsSableBodyPartsToArmourersOverrideProperties() {
        assertEquals("OVERRIDE_MODEL_HEAD", ArmourersBodyPartOverride.propertyName("HEAD"));
        assertEquals("OVERRIDE_MODEL_CHEST", ArmourersBodyPartOverride.propertyName("torso"));
        assertEquals("OVERRIDE_MODEL_LEFT_ARM", ArmourersBodyPartOverride.propertyName("LEFT_ARM"));
        assertEquals("OVERRIDE_MODEL_RIGHT_ARM", ArmourersBodyPartOverride.propertyName("RIGHT_ARM"));
        assertEquals("OVERRIDE_MODEL_LEFT_LEG", ArmourersBodyPartOverride.propertyName("LEFT_LEG"));
        assertEquals("OVERRIDE_MODEL_RIGHT_LEG", ArmourersBodyPartOverride.propertyName("RIGHT_LEG"));
        assertNull(ArmourersBodyPartOverride.propertyName("UNKNOWN"));
        assertNull(ArmourersBodyPartOverride.propertyName(null));
    }
}
