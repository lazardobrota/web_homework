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
        students.add(new Student("Lazar"));
        students.add(new Student("Petar"));
        students.add(new Student("Mika"));
        students.add(new Student("Zika"));
        students.add(new Student("Anja"));
        students.add(new Student("Nikola"));
        students.add(new Student("Ranko"));
        students.add(new Student("Radojica"));
        students.add(new Student("Sara"));
        List<Student> studentsAssitant = new ArrayList<>();
        List<Student> studentsProf = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            if ( Math.random() <= 0.5) {
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
            sum += students.get(i).getGrade();
        }

        System.out.println("Average grade: " + (double) sum/ len);
    }

    public static double getRandomNumber(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }
}