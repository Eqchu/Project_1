public class User {
    private String name;
    private int score;

    public User (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int addScore) {
        this.score += addScore;
    }
}
