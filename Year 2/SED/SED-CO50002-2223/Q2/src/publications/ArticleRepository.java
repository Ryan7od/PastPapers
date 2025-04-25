package publications;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ArticleRepository {
    private final Summariser summariser;
    private HashMap<String, HashSet<Subscriber>> subs = new HashMap<>();

    public ArticleRepository(Summariser summariser) {
        this.summariser = summariser;
    }

    public void publish(Article article) {
        HashSet<Subscriber> notified = new HashSet<>();
        String[] summary = summariser.summarise(article);
        for (String word : summary) {
            HashSet<Subscriber> subList = subs.getOrDefault(word, new HashSet<>());
            for (Subscriber sub : subList) {
                if (!notified.contains(sub)){
                    sub.notify(article);
                    notified.add(sub);
                }
            }
        }
    }

    public void subscribeTo(Subscriber sub, String word) {
        if (subs.containsKey(word)) {
            subs.get(word).add(sub);
        } else {
            HashSet<Subscriber> set = new HashSet<>();
            set.add(sub);
            subs.put(word, set);
        }
    }

    public void unsubscribe(Subscriber sub, String word) {
        HashSet<Subscriber> set = subs.getOrDefault(word, new HashSet<>());
        set.remove(sub);
    }
}
