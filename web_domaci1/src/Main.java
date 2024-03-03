import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    static final Object object = new Object();
    public static void main(String[] args) {
        int len = 8;
        List<Student> students = new ArrayList<>();
        students.add(new Student("Lazar", 0));
        students.add(new Student("Petar", 1));
        students.add(new Student("Mika", 2));
        students.add(new Student("Zika", 3));
        students.add(new Student("Anja", 4));
        students.add(new Student("Nikola", 5));
        students.add(new Student("Ranko", 6));
        students.add(new Student("Radojica", 7));
        students.add(new Student("Sara", 8));
        List<Student> studentsAssitant = new ArrayList<>();
        List<Student> studentsProf = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            double d = Math.random();
            System.out.println(i + " " + d);
            if (d <= 0.5) {
                students.get(i).setAssistant(true);
                studentsAssitant.add(students.get(i));
            }
            else {
                students.get(i).setAssistant(false);
                studentsProf.add(students.get(i));
            }
        }

        CyclicBarrier readyReviewersCB = new CyclicBarrier(2, () -> {
            System.out.println("Assistant and Professor are ready");
        });

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Review(readyReviewersCB, studentsAssitant));
        executorService.submit(new Review(readyReviewersCB, studentsProf));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdownNow();


        int sum = 0;
        for (int i = 0; i < len; i++) {
            System.out.println(students.get(i));
            sum += students.get(i).getGrade();
        }

        System.out.println("Average grade: " + (double) sum/ len);
    }

    public static double getRandomNumber(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }
}