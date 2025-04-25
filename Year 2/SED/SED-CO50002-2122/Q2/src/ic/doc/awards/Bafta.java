package ic.doc.awards;

public class Bafta extends Award {

    private Bafta(String category) {
        super(category);
    }

    public static Award forBest(String category) {
        return new Bafta(category);
    }
}
