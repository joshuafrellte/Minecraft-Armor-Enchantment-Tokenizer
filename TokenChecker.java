import java.util.Arrays;
import java.util.List;

public class TokenChecker {
    private static List<String> ARMOR_MATERIALS = Arrays.asList("leather", "chainmail", "iron", "gold", "diamond", "netherite");
    private static List<String> LEATHER_ARMOR_PIECES = Arrays.asList("cap", "tunic", "pants", "boots");
    private static List<String> ARMOR_PIECES = Arrays.asList("helmet", "chestplate", "leggings", "boots");
    private static List<String> ARMOR_ENCHANTMENTS = Arrays.asList("blast protection", "fire protection", "projectile protection", "thorns", "protection");
    private static List<String> ENCHANTMENT_WITHOUT_LEVEL = Arrays.asList("mending", "curse of vanishing", "curse of binding");
    private static List<String> LEVELS = Arrays.asList("i", "ii", "iii", "iv");

    public boolean isArmorMaterial(String armorMat) {
        return ARMOR_MATERIALS.contains(armorMat);
    }

    public boolean isLeather(String leather) {
        return leather.equals("leather");
    }

    public boolean isLeatherArmorPiece(String leatherPiece) {
        return LEATHER_ARMOR_PIECES.contains(leatherPiece);
    }

    public boolean isArmorPiece(String armorPiece) {
        return ARMOR_PIECES.contains(armorPiece);
    }

    public boolean isPreposition(String prep) {
        return prep.equals("with");
    }

    public boolean isArmorEnchantment(String armorEnch) {
        return ARMOR_ENCHANTMENTS.contains(armorEnch);
    }

    public boolean isEnchantmentWithLevel(String enchWithLvl) { return isArmorEnchantment(enchWithLvl) || enchWithLvl.equals("unbreaking"); }

    public boolean isEnchantmentWithoutLevel(String enchNoLvl) {
        return ENCHANTMENT_WITHOUT_LEVEL.contains(enchNoLvl);
    }

    public boolean isLevel(String lvl) {
        return LEVELS.contains(lvl);
    }

    public boolean isPunctuation(String punc) {
        return punc.equals(",");
    }
}
