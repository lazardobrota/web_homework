import java.util.List;
import java.util.concurrent.*;

public class Review implements Runnable {

    CyclicBarrier cyclicBarrier;
    List<Student> students;

    public Review(CyclicBarrier cyclicBarrier, List<Student> students) {
        this.cyclicBarrier = cyclicBarrier;
        this.students = students;
    }


    @Override
    public void run() {
        int i = 0;
        double cameIn = 0, defenseTime = 0, currentTime = 0, futureTime = 0;
        int grade = 0, twoStudentsPassed = 0;
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            cyclicBarrier.await();

            CyclicBarrier twoStudentsCB = new CyclicBarrier(2, () -> {
                System.out.println("Two Students for Professor are ready");
            });


            for (i = 0; i < students.size(); i++) {
                cameIn = Math.random();
                defenseTime = Main.getRandomNumber(0.5, 1);

                if (students.get(i).isAssistant()) {

                    Thread.sleep(Math.round((cameIn + defenseTime) * 1000));

                    grade = Math.round((float) Main.getRandomNumber(5, 10));
                    System.out.println("Thread: " + students.get(i).getName() + " Arrival: " + cameIn + " Prof: Assistant TTC: " + defenseTime + " : " + currentTime + " Score: " + grade + "\n " + students.get(i).getNum() + "-----------");
                    students.get(i).setGrade(grade);
                    currentTime += cameIn + defenseTime;
                    continue;
                }

                //Professor
                futureTime = Math.max(futureTime, cameIn + defenseTime);

                //Get new currentTime after 2 student left
                if (++twoStudentsPassed % 3 == 0) {
                    twoStudentsPassed = 0;
                    currentTime += futureTime;
                    Thread.sleep(Math.round((futureTime) * 1000));
                }

                executorService.submit(new ProfessorReview(twoStudentsCB, students.get(i), currentTime, cameIn, defenseTime));
            }

            executorService.shutdown();
        } catch (InterruptedException | BrokenBarrierException e) {
            System.out.println("Canceled Thread: " + students.get(i).getName() + " Arrival: " + cameIn + " Prof: Assistant TTC: " + defenseTime + " : " + currentTime + " Score: " + grade + "\n " + students.get(i).getNum() + "-----------");
            throw new RuntimeException(e);
        }
    }
}
