import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deriver {

    private static Map<String, List<List<String>>> GRAMMAR;

    static {
        GRAMMAR = new HashMap<>();
        GRAMMAR.put("<enchanted_armor>", Collections.singletonList(Arrays.asList("<armor>", "<enchantment_clause>")));
        GRAMMAR.put("<armor>", Arrays.asList(
                Arrays.asList("<armor_material>", "<armor_piece>"),
                Collections.singletonList("<leather_armor>")
        ));
        GRAMMAR.put("<armor_material>", Arrays.asList(
                Collections.singletonList("Chainmail"),
                Collections.singletonList("Iron"),
                Collections.singletonList("Gold"),
                Collections.singletonList("Diamond"),
                Collections.singletonList("Netherite")
        ));
        GRAMMAR.put("<armor_piece>", Arrays.asList(
                Collections.singletonList("Helmet"),
                Collections.singletonList("Chestplate"),
                Collections.singletonList("Leggings"),
                Collections.singletonList("Boots")
        ));
        GRAMMAR.put("<leather_armor>", Collections.singletonList(Arrays.asList("Leather", "<leather_armor_piece>")));
        GRAMMAR.put("<leather_armor_piece>", Arrays.asList(
                Collections.singletonList("Cap"),
                Collections.singletonList("Tunic"),
                Collections.singletonList("Pants"),
                Collections.singletonList("Boots")
        ));
        GRAMMAR.put("<enchantment_clause>", Collections.singletonList(Arrays.asList("with", "<enchantment_list>")));
        GRAMMAR.put("<enchantment_list>", Arrays.asList(
                Arrays.asList("<enchantment>", ",", "<enchantment_list>"),
                Collections.singletonList("<enchantment>")
        ));
        GRAMMAR.put("<enchantment>", Arrays.asList(
                Collections.singletonList("<enchantment_with_level>"),
                Collections.singletonList("<enchantment_without_level>")
        ));
        GRAMMAR.put("<enchantment_with_level>", Arrays.asList(
                Arrays.asList("<armor_enchantment>", "<level>"),
                Arrays.asList("Unbreaking", "<level>")
        ));
        GRAMMAR.put("<armor_enchantment>", Arrays.asList(
                Collections.singletonList("Protection"),
                Collections.singletonList("Blast Protection"),
                Collections.singletonList("Fire Protection"),
                Collections.singletonList("Projectile Protection"),
                Collections.singletonList("Thorns")
        ));
        GRAMMAR.put("<enchantment_without_level>", Arrays.asList(
                Collections.singletonList("Mending"),
                Collections.singletonList("Curse of Vanishing"),
                Collections.singletonList("Curse of Binding")
        ));
        GRAMMAR.put("<level>", Arrays.asList(
                Collections.singletonList("I"),
                Collections.singletonList("II"),
                Collections.singletonList("III"),
                Collections.singletonList("IV")
        ));
    }

    public void derive(String[] originalTokens) {
        ArrayList<String> initialSymbols = new ArrayList<>();
        initialSymbols.add("<enchanted_armor>");
        List<String> derivationResult = deriveRecursive(initialSymbols, originalTokens, 0);

        if (derivationResult != null) {
            System.out.println(listToString(initialSymbols, 0, originalTokens));
            for (String step : derivationResult) {
                System.out.println("⇒ " + step);
            }
        } else {
            System.out.println("Input does not follow grammar rules. Go play Minecraft and check.");
        }
    }

    public List<String> deriveRecursive(List<String> currentSymbols, String[] originalTokens, int tokenIndex) {
        if (currentSymbols.isEmpty() && tokenIndex == originalTokens.length)
            return new ArrayList<>();
        if (currentSymbols.isEmpty() || tokenIndex > originalTokens.length)
            return null;

        String currentSymbol = currentSymbols.get(0);

        if (!currentSymbol.startsWith("<") && !currentSymbol.endsWith(">")) {
            if (tokenIndex < originalTokens.length && currentSymbol.equals(originalTokens[tokenIndex])) {
                ArrayList<String> nextSymbols = new ArrayList<>(currentSymbols);
                nextSymbols.remove(0);
                return deriveRecursive(nextSymbols, originalTokens, tokenIndex + 1);
            }
            return null;
        }

        List<List<String>> productions = GRAMMAR.get(currentSymbol);
        if (productions != null) {
            for (List<String> production : productions) {
                List<String> nextSymbols = new ArrayList<>(currentSymbols);
                nextSymbols.remove(0);
                nextSymbols.addAll(0, production);

                List<String> result = deriveRecursive(nextSymbols, originalTokens, tokenIndex);

                if (result != null) {
                    String step = listToString(nextSymbols, tokenIndex, originalTokens);
                    result.add(0, step);
                    return result;
                }
            }
        }
        return null;
    }

    public String listToString(List<String> symbols, int matchedCount, String[] originalTokens) {
        String result = "";

        for (int i = 0; i < matchedCount; i++) {
            result += originalTokens[i];
            if (i < matchedCount - 1 && !originalTokens[i + 1].equals(",")) {
                result += " ";
            }
        }

        if (!symbols.isEmpty() && result.length() > 0 && !symbols.get(0).equals(",")) {
            result += " ";
        }

        for (int i = 0; i < symbols.size(); i++) {
            String symbol = symbols.get(i);
            result += symbol;
            if (i < symbols.size() - 1 && !symbols.get(i + 1).equals(",")) {
                result += " ";
            }
        }
        return result.trim();
    }

    public static void main(String[] args) {
        Deriver deriver = new Deriver();
        String[] tokens = "Iron Boots with Mending".split(" ");
        deriver.derive(tokens);
    }
}