import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleTranslator {
    private Map<String, String> dictonary;

    public SimpleTranslator() {
        dictonary = new HashMap<String, String>();
    }

    public void addTranslation(String word, String translation) {
        dictonary.put(word, translation);
    }

    public void translateWords(List<String> phrase) {
        StringBuilder sb = new StringBuilder();
        for (String word : phrase) {
            if (dictonary.containsKey(word)) {
                sb.append(dictonary.get(word));
                sb.append(" ");
            } else {
                sb.append(word);
                sb.append(" ");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args) {
        SimpleTranslator st = new SimpleTranslator();
        st.addTranslation("gr8", "great");
        st.addTranslation("irl", "in real life");
        st.addTranslation("r", "are");
        st.translateWords(Arrays.asList("irl", "hashtables", "r", "gr8"));
    }
}
