import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class MinecraftTokenizer {
    public static void main(String[] args) {
        MinecraftTokenizer tokenizer = new MinecraftTokenizer();
        String input = tokenizer.getString();
        String[] tokens = tokenizer.tokenize(input);

        System.out.println("Phase 1: CFG-Based Classification");
        tokenizer.showTokens(tokens);
    }

    public String getString() {
        // receive and return user input
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter enchanted armor string: ");
        return sc.nextLine();
    }

    public String[] tokenize(String enchantedArmor) {
        // distinguishing tokens with spaces and multiple words
        String regex = "curse of vanishing|curse of binding|blast protection|fire protection|projectile protection|,|\\b\\w+\\b";

        // tokenize string based on regex strings
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(enchantedArmor);

        // add tokens to ArrayList, then return as array
        ArrayList<String> enchanted_armor_tokenized = new ArrayList<>();
        while (matcher.find()) {
            enchanted_armor_tokenized.add(matcher.group());
        }
        return enchanted_armor_tokenized.toArray(new String[0]);
    }

    public void showTokens(String[] enchantedArmorToken) {
        TokenChecker checker = new TokenChecker();
        String hold, lowerCaseToken;

        // iterate through user input string
        for (int i = 0; i < enchantedArmorToken.length; i++) {
            hold = enchantedArmorToken[i];
            lowerCaseToken = hold.toLowerCase();

            // determine what token type
            if (checker.isArmorMaterial(lowerCaseToken))
                System.out.println(hold + "\t\t→\t<armor_material>");
            else if (i > 0 && checker.isLeather(enchantedArmorToken[i - 1].toLowerCase()) && checker.isLeatherArmorPiece(lowerCaseToken))
                System.out.println(hold + "\t\t→\t<leather_armor_piece>");
            else if (checker.isArmorPiece(lowerCaseToken))
                System.out.println(hold + "\t\t→\t<armor_piece>");
            else if (checker.isPreposition(lowerCaseToken))
                System.out.println(hold + "\t\t→\t<preposition>");
            else if (checker.isEnchantmentWithLevel(lowerCaseToken))
                if (checker.isArmorEnchantment(lowerCaseToken))
                    System.out.println(hold + "\t→\t<armor_enchantment>");
                else
                    System.out.println(hold + "\t→\t<enchantment_with_level>");
            else if (checker.isEnchantmentWithLevel(enchantedArmorToken[i - 1].toLowerCase()) && checker.isLevel(lowerCaseToken))
                System.out.println(hold + "\t\t\t→\t<level>");
            else if (checker.isEnchantmentWithoutLevel(lowerCaseToken))
                System.out.println(hold + "\t→\t<enchantment_without_level>");
            else if (checker.isPunctuation(lowerCaseToken))
                System.out.println(hold + "\t\t\t→\t<punctuation>");
            else
                System.out.println(hold + "\t\t→\t<unknown>");
        }
    }
}
    /*
    public static boolean isArmorMaterial(String armorMat) {
        if (armorMat.equals("chainmail") || armorMat.equals("iron") || armorMat.equals("gold") || armorMat.equals("diamond") || armorMat.equals("netherite") || armorMat.equals("leather"))
            return true;
        else
            return false;
    }

    public static boolean isLeather(String leather) {
        if (leather.equals("leather"))
            return true;
        else
            return false;
    }

    public static boolean isLeatherArmorPiece(String leatherArmorPiece) {
        if (leatherArmorPiece.equals("cap") || leatherArmorPiece.equals("tunic") || leatherArmorPiece.equals("pants") || leatherArmorPiece.equals("boots"))
            return true;
        else
            return false;
    }

    public static boolean isArmorPiece(String armorPiece) {
        if (armorPiece.equals("helmet") || armorPiece.equals("chestplate") || armorPiece.equals("leggings") || armorPiece.equals("boots"))
            return true;
        else
            return false;
    }

    public static boolean isPreposition(String prep) {
        if (prep.equals("with"))
            return true;
        else
            return false;
    }

    public static boolean isArmorEnchantment(String armorEnch) {
        if (armorEnch.equals("protection") || armorEnch.equals("blast protection") || armorEnch.equals("protection") || armorEnch.equals("protection"))
            return true;
        else
            return false;
    }

    public static boolean isEnchantmentWithoutLevel(String enchNoLvl) {
        if (enchNoLvl.equals("mending") || enchNoLvl.equals("curse of vanishing") || enchNoLvl.equals("curse of binding"))
            return true;
        else
            return false;
    }

    public static boolean isLevel(String lvl) {
        if (lvl.equals("i") || lvl.equals("ii") || lvl.equals("iii") || lvl.equals("iv"))
            return true;
        else
            return false;
    }





}
*/
