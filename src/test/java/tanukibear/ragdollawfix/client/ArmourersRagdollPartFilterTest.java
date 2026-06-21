package tanukibear.ragdollawfix.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArmourersRagdollPartFilterTest {

    @Test
    void mapsHeadPartsOnlyToHeadRagdollPart() {
        assertTrue(ArmourersRagdollPartFilter.shouldRender("HEAD", "armourers:head.base"));
        assertTrue(ArmourersRagdollPartFilter.shouldRender("head", "armourers:hat.base"));

        assertFalse(ArmourersRagdollPartFilter.shouldRender("HEAD", "armourers:chest.base"));
        assertFalse(ArmourersRagdollPartFilter.shouldRender("HEAD", "armourers:legs.leftLeg"));
    }

    @Test
    void mapsChestLimbsToMatchingRagdollParts() {
        assertTrue(ArmourersRagdollPartFilter.shouldRender("TORSO", "armourers:chest.base"));
        assertTrue(ArmourersRagdollPartFilter.shouldRender("TORSO", "armourers:chest.base2"));
        assertTrue(ArmourersRagdollPartFilter.shouldRender("LEFT_ARM", "armourers:chest.leftArm"));
        assertTrue(ArmourersRagdollPartFilter.shouldRender("LEFT_ARM", "armourers:chest.leftArm2"));
        assertTrue(ArmourersRagdollPartFilter.shouldRender("RIGHT_ARM", "armourers:chest.rightArm"));
        assertTrue(ArmourersRagdollPartFilter.shouldRender("RIGHT_ARM", "armourers:chest.rightArm2"));

        assertFalse(ArmourersRagdollPartFilter.shouldRender("TORSO", "armourers:chest.leftArm"));
        assertFalse(ArmourersRagdollPartFilter.shouldRender("LEFT_ARM", "armourers:chest.rightArm"));
    }

    @Test
    void mapsLegAndFootPartsToMatchingRagdollLegs() {
        assertTrue(ArmourersRagdollPartFilter.shouldRender("LEFT_LEG", "armourers:legs.leftLeg"));
        assertTrue(ArmourersRagdollPartFilter.shouldRender("LEFT_LEG", "armourers:legs.leftLeg2"));
        assertTrue(ArmourersRagdollPartFilter.shouldRender("LEFT_LEG", "armourers:feet.leftFoot"));
        assertTrue(ArmourersRagdollPartFilter.shouldRender("RIGHT_LEG", "armourers:legs.rightLeg"));
        assertTrue(ArmourersRagdollPartFilter.shouldRender("RIGHT_LEG", "armourers:legs.rightLeg2"));
        assertTrue(ArmourersRagdollPartFilter.shouldRender("RIGHT_LEG", "armourers:feet.rightFoot"));

        assertFalse(ArmourersRagdollPartFilter.shouldRender("LEFT_LEG", "armourers:feet.rightFoot"));
        assertFalse(ArmourersRagdollPartFilter.shouldRender("RIGHT_LEG", "armourers:legs.leftLeg"));
    }

    @Test
    void anchorsSharedOrUnknownPartsToTorsoOnly() {
        assertTrue(ArmourersRagdollPartFilter.shouldRender("TORSO", "armourers:legs.skirt"));
        assertTrue(ArmourersRagdollPartFilter.shouldRender("TORSO", "armourers:wings.leftWing"));
        assertTrue(ArmourersRagdollPartFilter.shouldRender("TORSO", "armourers:part.advanced_part"));
        assertTrue(ArmourersRagdollPartFilter.shouldRender("TORSO", "modded:unknown_part"));

        assertFalse(ArmourersRagdollPartFilter.shouldRender("LEFT_LEG", "armourers:legs.skirt"));
        assertFalse(ArmourersRagdollPartFilter.shouldRender("RIGHT_ARM", "armourers:wings.leftWing"));
        assertFalse(ArmourersRagdollPartFilter.shouldRender("HEAD", "modded:unknown_part"));
    }

    @Test
    void suppressesOnlyPartsThatDoNotBelongToTheActiveRagdollPart() {
        assertFalse(ArmourersRagdollPartFilter.shouldSuppress(null, "armourers:chest.base"));
        assertFalse(ArmourersRagdollPartFilter.shouldSuppress("", "armourers:chest.base"));
        assertFalse(ArmourersRagdollPartFilter.shouldSuppress("TORSO", "armourers:chest.base"));

        assertTrue(ArmourersRagdollPartFilter.shouldSuppress("HEAD", "armourers:chest.base"));
        assertTrue(ArmourersRagdollPartFilter.shouldSuppress("LEFT_ARM", "armourers:chest.rightArm"));
    }
}
