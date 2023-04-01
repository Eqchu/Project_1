public class User {
    private String name;
    private int score;
    private int totalScore;
    private int totalAnswers;

    public User () {
    }
    public User (String name) {
        this.name = name;
    }

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

    public int getScore() {
        return score;
    }

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
