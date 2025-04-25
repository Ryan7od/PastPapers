package ic.doc.awards;

public class Oscar extends Award {

    private Oscar(String category) {
        super(category);
    }

    public static Award forBest(String category) {
        return new Oscar(category);
    }
}
