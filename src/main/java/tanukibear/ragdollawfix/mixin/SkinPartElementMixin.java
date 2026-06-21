package tanukibear.ragdollawfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tanukibear.ragdollawfix.client.ArmourersSkinPartElementState;
import tanukibear.ragdollawfix.client.SkinPartElementSuppression;

@Mixin(targets = "moe.plushie.armourers_workshop.core.client.render.element.SkinPartElement", remap = false)
public abstract class SkinPartElementMixin implements SkinPartElementSuppression {
    @Unique
    private boolean ragdollawfix$suppressed;

    @Inject(method = "newInstance", at = @At("RETURN"), remap = false)
    private static void ragdollawfix$captureRagdollSuppression(
            @Coerce Object part,
            @Coerce Object skin,
            @Coerce Object scheme,
            @Coerce Object context,
            CallbackInfoReturnable<Object> cir
    ) {
        var element = cir.getReturnValue();
        if (element instanceof SkinPartElementSuppression suppression) {
            suppression.ragdollawfix$setSuppressed(ArmourersSkinPartElementState.shouldSuppressPart(part));
        }
    }

    @Override
    public void ragdollawfix$setSuppressed(boolean suppressed) {
        ragdollawfix$suppressed = suppressed;
    }

    @Inject(method = "prepare", at = @At("HEAD"), cancellable = true, remap = false)
    private void ragdollawfix$skipSuppressedRagdollPart(CallbackInfo ci) {
        if (ragdollawfix$suppressed) {
            ci.cancel();
        }
    }
}
