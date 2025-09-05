

// THIS IS NOT BEING USED

public class MinecraftEnums {
    public enum ArmorMaterial {
        CHAINMAIL("chainmail"),
        IRON("iron"),
        GOLD("gold"),
        DIAMOND("diamond"),
        NETHERITE("netherite");

        private final String name;
        ArmorMaterial(String name) { this.name = name; }
        public String getName() { return name; }
    }

    public enum ArmorPiece {
        HELMET("helmet"),
        CHESTPLATE("chestplate"),
        LEGGINGS("leggings"),
        BOOTS("boots");

        private final String name;
        ArmorPiece(String name) { this.name = name; }
        public String getName() { return name; }
    }

    public enum LeatherArmorPiece {
        CAP("cap"),
        TUNIC("tunic"),
        PANTS("leggings"),
        BOOTS("boots");

        private final String name;
        LeatherArmorPiece(String name) { this.name = name; }
        public String getName() { return name; }
    }

    public enum EnchantmentWithLevel {
        PROTECTION("protection"),
        BLAST_PROTECTION("blast protection"),
        FIRE_PROTECTION("fire protection"),
        PROJECTILE_PROTECTION("projectile protection"),
        THORNS("thorns"),
        UNBREAKING("unbreaking");

        private final String name;
        EnchantmentWithLevel(String name) { this.name = name; }
        public String getName() { return name; }
    }

    public enum EnchantmentWithoutLevel {
        MENDING("mending"),
        CURSE_OF_VANISHING("curse of vanishing"),
        CURSE_OF_BINDING("curse of vinding");

        private final String name;
        EnchantmentWithoutLevel(String name) { this.name = name; }
        public String getName() { return name; }
    }

    public enum Level {
        I("I"),
        II("II"),
        III("III"),
        IV("IV");

        private final String name;
        Level(String name) { this.name = name; }
        public String getName() { return name; }
    }

    public static void main(String[] args) {
        System.out.println(ArmorMaterial.DIAMOND.getName());
    }
}
