package net.spell_engine.api.render;

import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.spell_engine.client.render.CustomModelRegistry;
import net.spell_engine.mixin.client.render.ItemRendererAccessor;

import java.util.List;

public class CustomModels {
    public static void registerModelIds(List<Identifier> ids) {
        CustomModelRegistry.modelIds.addAll(ids);
    }

    public static void render(RenderLayer renderLayer, ItemRenderer itemRenderer, Identifier modelId,
                              MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int seed) {
        var model = BakedModelManagerHelper.getModel(MinecraftClient.getInstance().getBakedModelManager(), modelId);
        if (model == null) {
            var stack = Registry.ITEM.get(modelId).getDefaultStack();
            if (!stack.isEmpty()) {
                model = itemRenderer.getModel(stack, null, null, seed);
            }
        }
        var buffer = vertexConsumers.getBuffer(renderLayer);
        matrices.translate(-0.5, -0.5, -0.5);
        ((ItemRendererAccessor)itemRenderer).SpellEngine_renderBakedItemModel(model, ItemStack.EMPTY, light, OverlayTexture.DEFAULT_UV, matrices, buffer);
    }
}
