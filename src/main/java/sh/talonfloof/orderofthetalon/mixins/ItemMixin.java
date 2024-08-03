package sh.talonfloof.orderofthetalon.mixins;

import net.minecraft.item.Item;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Item.class)
public class ItemMixin {
    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Item;maxStackSize:I", opcode = Opcodes.PUTFIELD))
    public void meow(Item instance, int value) {
        if(value == 64) {
            instance.setMaxStackSize(100);
        }
    }
}
