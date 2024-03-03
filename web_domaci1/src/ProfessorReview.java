import java.util.concurrent.*;

public class ProfessorReview implements Runnable {

    private final CyclicBarrier cyclicBarrier;

    private final Student student;

    private final Double currentTime;

    private final Double cameIn;
    private final Double defenseTime;

    public ProfessorReview(CyclicBarrier cyclicBarrier, Student student, Double currentTime, Double cameIn, Double defenseTime) {
        this.cyclicBarrier = cyclicBarrier;
        this.student = student;
        this.currentTime = currentTime;
        this.cameIn = cameIn;
        this.defenseTime = defenseTime;
    }

    @Override
    public void run() {
        int grade = 0;
        try {
            //waits for 3 seconds, no need for one student to still wait for another if he wont come
            cyclicBarrier.await(3, TimeUnit.SECONDS);


            grade = Math.round((float) Main.getRandomNumber(5, 10));
            System.out.println("Thread: " + student.getName() + " Arrival: " + cameIn + " Prof: Professor TTC: " + defenseTime + " : " + currentTime + " Score: " + grade + "\n " + student.getNum() + "-----------");
            student.setGrade(grade);

        } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
            System.out.println("Canceled Thread: " + student.getName() + " Arrival: " + cameIn + " Prof: Professor TTC: " + defenseTime + " : " + currentTime + " Score: " + grade + "\n " + student.getNum() + "-----------");
            throw new RuntimeException(e);
        }
    }
}
