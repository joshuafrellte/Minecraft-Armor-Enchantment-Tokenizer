import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class MinecraftTokenizer {
    boolean isValid;
    public static void main(String[] args) {
        MinecraftTokenizer tokenizer = new MinecraftTokenizer();
        Deriver deriver = new Deriver();
        System.out.println("[]=====[ MINECRAFT ARMOR ENCHANTMENT TOKENIZER =====>");
        String input = tokenizer.getString();
        System.out.println("Phase 1: CFG-Based Classification");
        /*
        if (spaceBefore(input)) {
            System.out.println("Input does not follow grammar rules. Go play Minecraft and check.");
        }
         */
        String[] tokens = tokenizer.tokenize(input);
        tokenizer.showTokens(tokens);

        System.out.println("\nPhase 2: Derivation");

        if (tokenizer.isValid) {
            deriver.derive(removeSpace(tokens));
        } else {
            System.out.println("Invalid spacing! Go play Minecraft and check.");
        }



    }


    public static boolean spaceBefore(String str) {
        char firstChar = str.charAt(0);
        if (firstChar==' ') {
            return true;
        } else {
            return false;
        }
    }
    // REMOVE SPACES FROM TOKENIZED STRING!!!!!!!!!!!!!!!!!!!
    public static String[] removeSpace(String[] arr) {
        ArrayList<String> newList = new ArrayList<>();
        for (String element : arr) {
            if (!element.equals(" "))
                newList.add(element);
        } return newList.toArray(new String[0]);
    }

    public String getString() {
        // receive and return user input
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter enchanted armor string: ");
        return sc.nextLine();
    }

    public String[] tokenize(String enchantedArmor) {
        // distinguishing tokens with spaces and multiple words
        String regex = "curse of vanishing|curse of binding|blast protection|fire protection|projectile protection|,| |\\b\\w+\\b";

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
        String hold;

        // iterate through user input string
        for (int i = 0; i < enchantedArmorToken.length; i++) {
            try {
                hold = enchantedArmorToken[i];

                // determine what token type
                if (checker.isArmorMaterial(enchantedArmorToken[i]))
                    System.out.println(hold + "\t\t→\t<armor_material>");
                else if (i > 0 && checker.isLeather(enchantedArmorToken[i - 1]) && checker.isLeatherArmorPiece(enchantedArmorToken[i]))
                    System.out.println(hold + "\t\t→\t<leather_armor_piece>");
                else if (checker.isArmorPiece(enchantedArmorToken[i]))
                    System.out.println(hold + "\t\t→\t<armor_piece>");
                else if (checker.isPreposition(enchantedArmorToken[i]))
                    System.out.println(hold + "\t\t→\t<preposition>");
                else if (checker.isEnchantmentWithLevel(enchantedArmorToken[i])) {
                    if (checker.isArmorEnchantment(enchantedArmorToken[i]))
                        System.out.println(hold + "\t→\t<armor_enchantment>");
                    else
                        System.out.println(hold + "\t→\t<enchantment_with_level>");
                } else if (checker.isEnchantmentWithLevel(enchantedArmorToken[i - 1]) && checker.isLevel(enchantedArmorToken[i]))
                    System.out.println(hold + "\t\t\t→\t<level>");
                else if (checker.isEnchantmentWithoutLevel(enchantedArmorToken[i]))
                    System.out.println(hold + "\t→\t<enchantment_without_level>");
                else if (checker.isPunctuation(enchantedArmorToken[i]))
                    System.out.println(hold + "\t\t\t→\t<punctuation>");
                else if (checker.isOneSpace(enchantedArmorToken[i])) {
                    System.out.println("\"" + hold + "\"" + "\t\t→\t<space>");
                    isValid = true;
                    if (checker.isOneSpace(enchantedArmorToken[i-1])) {
                        System.out.println("\"" + hold + "\"" + "\t\t→\t<space>");
                        //System.out.println("Multiple spaces are not allowed! Play Minecraft!");
                        isValid = false;
                        //break;
                    }
                }
                else
                    System.out.println(hold + "\t\t→\t<unknown>");
            } catch (ArrayIndexOutOfBoundsException e) {
                //System.out.println("Input does not follow grammar rules. Go play Minecraft and check.");
            }
        }
    }


}