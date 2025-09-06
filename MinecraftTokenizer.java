import java.util.Scanner;
import java.util.ArrayList;

public class MinecraftTokenizer {
    TokenChecker checker;
    MinecraftTokenizer tokenizer;
    public static void main(String[] args) {
        MinecraftTokenizer tokenizer = new MinecraftTokenizer();
        String input = tokenizer.getString();
        String[] preTokens = tokenizer.splitToArray(input);
//        for (int i=0; i<tokens.length; i++) {
//            System.out.println(tokens[i]);
//        }

        System.out.println("Phase 1: CFG-Based Classification");
        tokenizer.showTokens(tokenizer.tokenizeArray(preTokens));
    }

    public String getString() {
        // receive and return user input
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter enchanted armor string: ");
        return sc.nextLine();
    }

    public String[] splitToArray(String str) {
        str += " ";
        ArrayList<String> tokensList = new ArrayList<>();

        String holdToken = "";
        for (int i = 0; i < str.length(); i++) {
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

    // blast protection, fire protection, projectile protection
    // curse of binding, curse of vanishing
    public String[] tokenizeArray(String[] arr) {
        checker = new TokenChecker();
        ArrayList<String> tokenized = new ArrayList<>();
        int i = 0;
        while (i < arr.length) {
            String threeWords = "", twoWords = "";

            if (i+2 < arr.length) {
                threeWords = arr[i]+" "+arr[i+1]+" "+arr[i+2];
            }
            if (i+1 < arr.length) {
                twoWords = arr[i]+" "+ arr[i+1];
            }

            if (checker.isEnchantmentWithoutLevel(threeWords)) {
                tokenized.add(threeWords);
                i+=3;
            } else if (checker.isArmorEnchantment(twoWords)) {
                tokenized.add(twoWords);
                i+=2;
            } else {
                tokenized.add(arr[i]);
                i++;
            }
        }
        return tokenized.toArray(new String[0]);
    }

    /*
    public String[] tokenizeArray(String[] arr) {
        checker = new TokenChecker();
        ArrayList<String> tokenized = new ArrayList<>();
        String twoWords="", threeWords="";
        for (int i=0; i<arr.length; i++) {
            if (i+1 < arr.length-2) {
                twoWords = arr[i]+" "+arr[i+1];
                System.out.println(twoWords);
                if (checker.isArmorEnchantment(twoWords) && i+2 < arr.length && checker.isLevel(arr[i+2])) {
                    tokenized.add(twoWords);
                    tokenized.add(arr[i+2]);
                    i++;
                    continue;
                }
            }
            else if (i+2 < arr.length-3) {
                threeWords = arr[i]+" "+arr[i+1]+" "+arr[i+2];
                System.out.println(threeWords);
                if (checker.isEnchantmentWithoutLevel(threeWords)) {
                    tokenized.add(threeWords);
                    i+=2;
                    continue;
                }
            }


            tokenized.add(arr[i]);
        }
        return tokenized.toArray(new String[0]);
    } */

    public boolean isTwoWordEnch(String two_words) {
        if (two_words.equals("blast protection") || two_words.equals("fire protection") || two_words.equals("projectile protection")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isThreeWordEnch(String three_words) {
        if (three_words.equals("curse of vanishing") || three_words.equals("curse of binding")) {
            return true;
        } else {
            return false;
        }
    }



    /*
    // split string by whitespace(" ")
    public String[] splitToArray(String str) {
       // counting space between words to determine size
        int tokenCount = 0;
        str += " ";
        for (int i=0; i<str.length(); i++) {
            if (Character.isWhitespace(str.charAt(i)) || str.charAt(i)==',') {
                tokenCount++;
            }   // outputs null, need to token out comma to fill up nulls
        }
        //System.out.println(tokenCount);

        // add words to tokenized array
        String[] arr = new String[tokenCount];
        String hold = "";
        int indexCount = 0;
        for (int i=0; i<str.length(); i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                arr[indexCount] = hold;
                hold = "";
                indexCount++;
            }
            else {
                hold += str.charAt(i);
            }
        }
        return arr;
    } */

    /*
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
    */

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
