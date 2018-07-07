package jcoreadv.lesson1;

public class Main {

    public static void main(String[] args) {

        Team team = new Team(
                new TeamMember("Tom"),
                new TeamMember("Jerry"),
                new TeamMember("Volk"),
                new TeamMember("Zayac")
        );


        Course course = new Course(
                new Barrier[]{
                        new Barrier("hill"),
                        new Barrier("pit"),
                        new Barrier("something else")
                }
        );

        course.doIt(team);
        team.teamInfo(true);
    }
}
