package org.sprint;

import java.util.ArrayList;
import java.util.List;

import static org.sprint.Color.BLACK_COLOR;
import static org.sprint.Color.GREY_COLOR;

public class ColorGenerator {
    public static List<String> getColorBlack() {
        List<String> color = new ArrayList<>();
        color.add(BLACK_COLOR);
        return color;
    }

    public static List<String> getColorGrey() {
        List<String> color = new ArrayList<>();
        color.add(GREY_COLOR);
        return color;
    }

    public static List<String> getColorBoth() {
        List<String> colors = new ArrayList<>();
        colors.add(BLACK_COLOR);
        colors.add(GREY_COLOR);
        return colors;
    }

    public static List<String> getEmptyColors() {
        return new ArrayList<>();
    }
}
