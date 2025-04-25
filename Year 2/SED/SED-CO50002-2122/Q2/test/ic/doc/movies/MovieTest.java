package ic.doc.movies;

import ic.doc.awards.Oscar;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;
import static java.util.Collections.EMPTY_SET;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MovieTest {

    public static final Movie SHREK =
            new Movie("Shrek",
                    "A mean lord exiles fairytale creatures to the swamp of a grumpy ogre, who must go on " +
                            "a quest and rescue a princess for the lord in order to get his land back.",
            55554321,
            EMPTY_LIST,
            Collections.EMPTY_SET,
            List.of(Oscar.forBest("Animated Feature")),
            Certification.UNIVERSAL);


    public static final Movie THE_GODFATHER =
            new Movie("The Godfather",
            "The aging patriarch of an organized crime dynasty in postwar New York City transfers control " +
                    "of his clandestine empire to his reluctant youngest son.",
            21111112,
            List.of(new Actor("Marlon Brando"), new Actor("Al Pacino")),
            EMPTY_SET,
            EMPTY_LIST,
            Certification.EIGHTEEN);

    @Test
    public void canBeFilteredForAgeAppropriateness() {
        assertTrue(SHREK.isSuitableForChildren());
        assertFalse(THE_GODFATHER.isSuitableForChildren());
    }

}
