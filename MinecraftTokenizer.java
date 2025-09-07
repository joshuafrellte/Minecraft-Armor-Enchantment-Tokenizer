import java.util.Scanner;
import java.util.ArrayList;

public class MinecraftTokenizer {
    TokenChecker checker;
    MinecraftTokenizer tokenizer;
    public String[] tokens;

    public static void main(String[] args) {
        MinecraftTokenizer tokenizer = new MinecraftTokenizer();
        String input = tokenizer.getString();
        String[] preTokens = tokenizer.splitToArray(input);
        tokenizer.tokens = tokenizer.tokenizeArray(preTokens);

        String[] originalTokens = tokenizer.tokenizeArray(preTokens);
        String[] lowerCaseTokens = tokenizer.tokenizeArrayToLowerCase(preTokens);

        System.out.println("Phase 1: CFG-Based Classification");
        tokenizer.showTokens(tokenizer.tokenizeArray(preTokens));

        System.out.println();

        Deriver deriver = new Deriver();
        deriver.derive(originalTokens, lowerCaseTokens);
    }

    public String getString() {
        // receive and return user input
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter enchanted armor string: ");
        return sc.nextLine();
    }

    // traverses string via characters, splits by " "
    public String[] splitToArray(String str) {
        str += " "; // for last token
        ArrayList<String> tokensList = new ArrayList<>();

        String holdToken = "";  // hold token until space or ','
        for (int i = 0; i < str.length(); i++) {
            // if current char is space or ',', add to tokensList and reset holdToken
            if (Character.isWhitespace(str.charAt(i)) || str.charAt(i) == ',') {
                if (!holdToken.isEmpty()) {
                    tokensList.add(holdToken);
                    holdToken = "";
                }
                if (str.charAt(i) == ',') {
                    tokensList.add(String.valueOf(str.charAt(i)));
                }
            } else {
                holdToken += str.charAt(i);
            }
        }
        return tokensList.toArray(new String[0]);
    }

    // properly segregates these multi-word tokens into their own indices
    // blast protection, fire protection, projectile protection
    // curse of binding, curse of vanishing
    public String[] tokenizeArray(String[] arr) {
        checker = new TokenChecker();
        ArrayList<String> tokenized = new ArrayList<>();
        int i = 0;
        while (i < arr.length) {
            String threeWords = "", twoWords = "";

            if (i+2 < arr.length) {
                threeWords = (arr[i]+" "+arr[i+1]+" "+arr[i+2]).toLowerCase();
            }
            if (i+1 < arr.length) {
                twoWords = (arr[i]+" "+ arr[i+1]).toLowerCase();
            }

            if (checker.isEnchantmentWithoutLevel(threeWords)) {
                tokenized.add(arr[i]+" "+arr[i+1]+" "+arr[i+2]);
                i+=3;
            } else if (checker.isArmorEnchantment(twoWords)) {
                tokenized.add(arr[i]+" "+ arr[i+1]);
                i+=2;
            } else {
                tokenized.add(arr[i]);
                i++;
            }
        }
        return tokenized.toArray(new String[0]);
    }

    public String[] tokenizeArrayToLowerCase(String[] arr) {
        checker = new TokenChecker();
        ArrayList<String> tokenized = new ArrayList<>();
        int i = 0;
        while (i < arr.length) {
            String threeWords = "", twoWords = "";
            if (i + 2 < arr.length) {
                threeWords = (arr[i] + " " + arr[i + 1] + " " + arr[i + 2]).toLowerCase();
            }
            if (i + 1 < arr.length) {
                twoWords = (arr[i] + " " + arr[i + 1]).toLowerCase();
            }

            if (checker.isEnchantmentWithoutLevel(threeWords)) {
                tokenized.add(threeWords);
                i += 3;
            } else if (checker.isArmorEnchantment(twoWords)) {
                tokenized.add(twoWords);
                i += 2;
            } else {
                tokenized.add(arr[i].toLowerCase());
                i++;
            }
        }
        return tokenized.toArray(new String[0]);
    }

    public void showTokens(String[] enchantedArmorToken) {
        TokenChecker checker = new TokenChecker();
        String hold, lowerCaseToken;

        // iterate through user input string
        for (int i = 0; i < enchantedArmorToken.length; i++) {
            hold = enchantedArmorToken[i];
            lowerCaseToken = hold.toLowerCase();

            // determine token type
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
            else if (i>=1 && checker.isEnchantmentWithLevel(enchantedArmorToken[i-1].toLowerCase()) && checker.isLevel(lowerCaseToken))
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
