package mod.maxbogomol.fluffy_fur.common.itemskin;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.model.armor.ArmorModel;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurModels;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ItemSkin {
    public String id;
    public Color color;
    public List<ItemSkinEntry> skinEntries = new ArrayList<>();
    public List<Component> applyingItems = new ArrayList<>();

    public ItemSkin(String id) {
        this.id = id;
        this.color = Color.WHITE;
    }

    public ItemSkin(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public String getTranslatedName() {
        return getTranslatedName(id);
    }

    public static String getTranslatedName(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String monogramId = id.substring(i + 1);
        return "item_skin."  + modId + "." + monogramId;
    }

    public static Component getSkinName(ItemSkin skin) {
        Color color = skin.getColor();
        return Component.translatable(skin.getTranslatedName()).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(color)));
    }

    public static Component getSkinComponent(ItemSkin skin) {
        return Component.translatable("lore.fluffy_fur.skin").withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, 249, 210, 129))).append(" ").append(getSkinName(skin));
    }

    public static List<Component> getApplyingItemsComponents(ItemSkin skin, boolean isShift) {
        List<Component> list = new ArrayList<>();
        list.add(Component.translatable("lore.fluffy_fur.skin_applies").append(isShift ? Component.empty() : Component.literal(" []")).withStyle(ChatFormatting.GRAY));
        if (isShift) {
            for (Component component : skin.applyingItems) {
                list.add(Component.literal(" ").append(component).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, 249, 210, 129))));
            }
        }
        return list;
    }

    public Component getSkinName() {
        return getSkinName(this);
    }

    public Component getSkinComponent() {
        return getSkinComponent(this);
    }

    public List<Component> getApplyingItemsComponents(boolean isShift) {
        return getApplyingItemsComponents(this, isShift);
    }

    public void addApplyingItem(Component component) {
        applyingItems.add(component);
    }

    public boolean canApplyOnItem(ItemStack itemStack) {
        for (ItemSkinEntry skinEntry : getSkinEntries()) {
            if (skinEntry.canApplyOnItem(itemStack)) return true;
        }
        return false;
    }

    public ItemStack applyOnItem(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        nbt.putString("skin", this.getId());
        return itemStack;
    }

    public static ItemSkin getSkinFromItem(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        if (nbt.contains("skin")) {
            return ItemSkinHandler.getSkin(nbt.getString("skin"));
        }
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public ArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
        for (ItemSkinEntry skinEntry : getSkinEntries()) {
            if (skinEntry.canApplyOnItem(itemStack)) return skinEntry.getArmorModel(entity, itemStack, armorSlot, _default);
        }
        return FluffyFurModels.EMPTY_ARMOR;
    }

    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        for (ItemSkinEntry skinEntry : getSkinEntries()) {
            if (skinEntry.canApplyOnItem(stack)) return skinEntry.getArmorTexture(stack, entity, slot, type);
        }
        return FluffyFur.MOD_ID + ":textures/models/armor/skin/empty.png";
    }

    @OnlyIn(Dist.CLIENT)
    public String getItemModelName(ItemStack stack) {
        for (ItemSkinEntry skinEntry : getSkinEntries()) {
            if (skinEntry.canApplyOnItem(stack)) return skinEntry.getItemModelName(stack);
        }
        return null;
    }

    public List<ItemSkinEntry> getSkinEntries() {
        return skinEntries;
    }

    public void addSkinEntry(ItemSkinEntry skinEntry) {
        skinEntries.add(skinEntry);
    }

    public void setupSkinEntries() {

    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isDefaultModel(Entity entity) {
        if (entity instanceof AbstractClientPlayer player) {
            return player.getModelName().equals("default");
        }

        return true;
    }
}
