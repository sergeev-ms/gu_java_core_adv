package jcoreadv.lesson1;

class Course {
    private Barrier[] barriers;

    Course(Barrier[] barriers) {
        this.barriers = barriers;
    }

    void doIt(Team team){
        TeamMember[] teamMembers = team.getTeamMembers();
        for (TeamMember teamMember : teamMembers) {
            teamMember.setCourseResult(true);
            for (Barrier barrier : barriers) {
                boolean tryToForce = barrier.tryToForce(teamMember);
                if (!tryToForce){
                    teamMember.setCourseResult(false);
                    break;
                }
            }
        }
    }

}
