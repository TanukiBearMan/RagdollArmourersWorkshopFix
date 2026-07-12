package tanukibear.ragdollawfix.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tanukibear.ragdollawfix.client.ArmourersBodyPartOverride;

@Pseudo
@Mixin(targets = "dev.leo.sableplayerragdoll.neoforge.client.RagdollPartBlockEntityRenderer", remap = false)
public abstract class RagdollPartBlockEntityRendererMixin {
    @Shadow
    private PlayerModel<?> model;

    @Inject(method = "renderLayers", at = @At("HEAD"), remap = false)
    private void ragdollawfix$hideOverriddenBodyPart(
            @Coerce Object blockEntity,
            @Coerce Object bodyPart,
            LivingEntity entity,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            float partialTick,
            CallbackInfo ci
    ) {
        var bodyPartName = bodyPart instanceof Enum<?> value ? value.name() : bodyPart.toString();
        if (!ArmourersBodyPartOverride.shouldHide(entity, bodyPartName)) {
            return;
        }

        switch (bodyPartName) {
            case "HEAD" -> {
                model.head.visible = false;
                model.hat.visible = false;
            }
            case "TORSO" -> {
                model.body.visible = false;
                model.jacket.visible = false;
            }
            case "LEFT_ARM" -> {
                model.leftArm.visible = false;
                model.leftSleeve.visible = false;
            }
            case "RIGHT_ARM" -> {
                model.rightArm.visible = false;
                model.rightSleeve.visible = false;
            }
            case "LEFT_LEG" -> {
                model.leftLeg.visible = false;
                model.leftPants.visible = false;
            }
            case "RIGHT_LEG" -> {
                model.rightLeg.visible = false;
                model.rightPants.visible = false;
            }
            default -> {
            }
        }
    }
}
