package jcoreadv.lesson1;

import java.util.Random;

class Barrier {
    private static Random random = new Random();

    private int difficulty = random.nextInt(50) + 20;

    private String name;

    String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    Barrier(String name) {
        this.name = name;
    }

    boolean tryToForce(TeamMember member){
        return member.getPower() >= this.difficulty;
    }
}
