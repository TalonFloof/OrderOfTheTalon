package sh.talonfloof.orderofthetalon.mixins.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Vec3D;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sh.talonfloof.orderofthetalon.IExtendedPlayer;
import sh.talonfloof.orderofthetalon.mixins.accessors.LivingEntityAccessor;

@Mixin(PlayerEntity.class)
public abstract class NoClipMixin extends LivingEntity implements IExtendedPlayer {
    @Unique private Vec3D talon_flightSpeed = Vec3D.make(0, 0, 0);
    @Unique
    public boolean talon$shouldNoClip = false;

    public NoClipMixin(Level world) {
        super(world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void creative_tick(CallbackInfo info) {
        this.updateFromBB = this.talon$shouldNoClip;
        if(this.talon$shouldNoClip) {
            this.onGround = false;
        }
        if (!this.talon$shouldNoClip) return;
        this.fallDistance = 0;

        LivingEntityAccessor entity = (LivingEntityAccessor) this;

        float front = entity.talon_getFrontMovement();
        float right = entity.talon_getRightMovement();
        double angle = Math.toRadians(this.yaw);
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        float dx = (front * cos - right * sin);
        float dz = (right * cos + front * sin);

        talon_flightSpeed.x = MathHelper.lerp(0.15, talon_flightSpeed.x, dx * 0.4);
        talon_flightSpeed.z = MathHelper.lerp(0.15, talon_flightSpeed.z, dz * 0.4);

        boolean sneaking = this.isChild(); // isChild

        dx = 0;
        if (jumping) dx += 0.4F;
        if (sneaking) dx -= 0.4F;

        talon_flightSpeed.y = MathHelper.lerp(0.2, talon_flightSpeed.y, dx);

        this.velocityX = talon_flightSpeed.x * 2;
        this.velocityY = talon_flightSpeed.y * 2;
        this.velocityZ = talon_flightSpeed.z * 2;
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void creative_damage(Entity target, int amount, CallbackInfoReturnable<Boolean> info) {
        if (this.talon$shouldNoClip) {
            info.setReturnValue(false);
            info.cancel();
        }
    }

    @Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true) // applyDamage
    private void creative_applyDamage(int damageAmount, CallbackInfo info) {
        if (this.talon$shouldNoClip) {
            info.cancel();
        }
    }

    @Unique
    @Override
    public void talon$setNoClip(boolean value) {
        talon$shouldNoClip = value;
        talon_flightSpeed = Vec3D.make(0,0,0);
    }

    @Unique
    @Override
    public boolean talon$getNoClip() {
        return talon$shouldNoClip;
    }
}
