package ru.otus;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SuppressWarnings({"java:S106", "java:S125"}) // sonarlint
public class App {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        int min = 0;
        int max = 100;

        //        for (int i = min; i < max; i++) {
        //            nums.add(i);
        //        }
        // =
        IntStream.range(min, max).forEach(nums::add); // i -> nums.add(i) = nums::add

        System.out.println(Lists.reverse(nums));
    }
}
