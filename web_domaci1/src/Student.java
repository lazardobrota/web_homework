public class Student {

    private String name;
    private boolean isAssistant;

    private int grade;

    public Student(String name) {
        this.name = name;
    }

    public Student(boolean isAssistant) {
        this.isAssistant = isAssistant;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", isAssistant=" + isAssistant +
                ", grade=" + grade +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAssistant() {
        return isAssistant;
    }

    public void setAssistant(boolean assistant) {
        isAssistant = assistant;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
