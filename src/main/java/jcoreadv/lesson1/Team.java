package jcoreadv.lesson1;

class Team {

    private static final String MSG_TEMPLATE = "Член команды %s с показателем силы %s сделал попытку. Результат: %b. \n";

    private TeamMember[] teamMembers = new TeamMember[4];

    Team(TeamMember... teamMembers) {
        System.arraycopy(teamMembers, 0, this.teamMembers, 0, this.teamMembers.length);
    }

    TeamMember[] getTeamMembers() {
        return teamMembers;
    }

    void teamInfo(boolean onlyTheBest) {
        for (TeamMember teamMember : teamMembers) {
            if (onlyTheBest) {
                if (teamMember.getCourseResult())
                    memberInfo(teamMember);

            } else memberInfo(teamMember);
        }
    }

    private void memberInfo(TeamMember teamMember) {
        System.out.printf(MSG_TEMPLATE, teamMember.getName(), teamMember.getPower(), teamMember.getCourseResult());
    }
}
