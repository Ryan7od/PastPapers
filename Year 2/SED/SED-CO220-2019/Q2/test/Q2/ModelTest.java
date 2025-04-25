package Q2;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ModelTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    SimpleStatsInterface stats = context.mock(SimpleStatsInterface.class);

    @Test
    public void correctNumbers() {
        StatsEngine model = new StatsEngine();

        int[] nums = {7, 4, 1, 13, 5};

        for (int i = 0; i < nums.length; i++) {
            model.action(nums[i]);
        }

        List<Integer> modelNums = model.getNumbers();
        Assert.assertEquals(nums.length, modelNums.size());
        for (int i = 0; i < nums.length; i++) {
            Assert.assertEquals(nums[i], (int)modelNums.get(i));
        }
    }

    @Test
    public void correctMax() {
        StatsEngine model = new StatsEngine();

        int[] nums = {7, 4, 1, 13, 5};

        for (int i = 0; i < nums.length; i++) {
            model.action(nums[i]);
        }

        Assert.assertEquals(Arrays.stream(nums).max().orElse(0), model.getMax());
    }

    @Test
    public void correctMean() {
        StatsEngine model = new StatsEngine();

        int[] nums = {7, 4, 1, 13, 5};

        for (int i = 0; i < nums.length; i++) {
            model.action(nums[i]);
        }

        Assert.assertEquals(Arrays.stream(nums).average().orElse(0.0), model.getMean(), 0.1);
    }

    @Test
    public void observerPropogation() {
        StatsEngine model = new StatsEngine();
        model.addObserver(stats);

        context.checking(new Expectations() {{
            exactly(1).of(stats).updateValues(6, 6.0);
        }});

        model.action(6);
    }
}
