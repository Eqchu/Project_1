public class User {
    private String name;
    private int score;
    private int totalScore;
    private int totalAnswers;

    // Konstruktorid
    public User () {
    }
    public User (String name) {
        this.name = name;
    }

    // Meetodid nime seadmiseks ja saamiseks. // Meetodid kogu vastuste arvu saamiseks ja seadmiseks
    public void setName(String name) {
        this.name = name;
    }

    public int getTotalAnswers() {
        return totalAnswers;
    }
    public void setTotalAnswers(int totalAnswers) {
        this.totalAnswers = totalAnswers;
    }
    public String getName() {
        return name;
    }

    // Meetodid skoori saamiseks ja seadmiseks
    public int getScore() {
        return score;
    }

    // Meetodid kogu skoori saamiseks ja seadmiseks
    public int getTotalScore() {
        return totalScore;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int addScore) {

        this.score += addScore;
        this.totalScore += addScore;
    }
    public void addTotalAnswers(int addAnswer) {
        this.totalAnswers += addAnswer;
    }
}
