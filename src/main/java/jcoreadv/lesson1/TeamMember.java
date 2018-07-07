package jcoreadv.lesson1;

import java.util.Random;

class TeamMember {
    private String name;
    private boolean courseResult;

    private static Random random = new Random();
    private int power = random.nextInt(90) + 10;


    TeamMember(String name) {
        this.name = name;
    }

    int getPower() {
        return power;
    }

    String getName() {
        return name;
    }

    boolean getCourseResult() {
        return courseResult;
    }

    void setCourseResult(boolean courseResult) {
        this.courseResult = courseResult;
    }
}
