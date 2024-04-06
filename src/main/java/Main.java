import java.io.*;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class Main {

    private static final int numberOfThreads = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws IOException {
        // Delete existing output files
        deleteAllFilesWithSpecificExtension(new File("").getAbsolutePath(), "txt");

        for (int i = 0; i < numberOfThreads; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                double memory = 0.0;
                String fileName = "thread-" + (finalI + 1) + "-output.txt";

                // Create output file
                try (FileWriter fw = new FileWriter(fileName, true);
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter out = new PrintWriter(bw))
                {
                    // Given number N for target size
                    String N = "500000000";

                    // Date formatter
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy h:mm:ss.SSSS a");

                    // Log start time
                    Date date1 = new Date();
                    String startTimestamp = sdf.format(date1);
                    System.out.println("\nStart time: " + startTimestamp);
                    out.println("\nStart time: " + startTimestamp);

                    // Function call, burn resources, spend time
                    memory = Main.addNumbers(N, 0.0);

                    // Log end time
                    Date date2 = new Date();
                    String endTimestamp = sdf.format(date2);
                    System.out.println("\nEnd time: " + endTimestamp);
                    out.println("\nEnd time: " + endTimestamp);

                    // Difference between start and end times
                    DifferenceOutput differenceOutput = findDifference(startTimestamp, endTimestamp);
                    long difference = differenceOutput.getMilliseconds();
                    System.out.println("\nDuration in milliseconds: " + difference);
                    out.println("\nDuration in milliseconds: " + difference);
                    out.println("\nDuration: " + differenceOutput.getText());

                    double memoryPercentUsed = memory / Runtime.getRuntime().totalMemory();
                    System.out.println("\nMax percent of all memory used at an instance: " + memoryPercentUsed);
                    out.println("\nMax percent of all memory used at an instant: " + memoryPercentUsed);

                    out.flush();
                    out.close();
                    System.out.println("\nDone, sleeping for 15 minutes and shutting down");
                    try {
                        TimeUnit.MINUTES.sleep(15);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
            thread.start();
        }
    }

    public static double addNumbers(String N, double memory) {
        BigInteger i = new BigInteger("0");
        BigInteger j = new BigInteger("0");
        while (i.compareTo(new BigInteger(N)) < 0) {
            i = i.add(new BigInteger("1"));
            j = j.add(i);
        }
        memory = Math.max(memory, Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        return memory;
    }

    static DifferenceOutput findDifference(String start_date, String end_date) {
        // SimpleDateFormat converts the string format to date object
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy h:mm:ss.SSSS a");
        try {
            // parse method is used to parse the text from a string to produce the date
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            // Calculate time difference in milliseconds
            long difference_In_Time = d2.getTime() - d1.getTime();

            // Calculate time difference in seconds, minutes, hours, years, and days
            long difference_In_Milliseconds = difference_In_Time % 1000;
            long difference_In_Seconds = (difference_In_Time / 1000) % 60;
            long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
            long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
            long difference_In_Years = (difference_In_Time / (1000L * 60 * 60 * 24 * 365));
            long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
            // Print the date difference in years, in days, in hours, in minutes, and in seconds
            String finalTime =
                      difference_In_Years + " years, "
                    + difference_In_Days + " days, "
                    + difference_In_Hours + " hours, "
                    + difference_In_Minutes + " minutes, "
                    + difference_In_Seconds + " seconds, "
                    + difference_In_Milliseconds + " milliseconds";
            System.out.print("Difference between two dates is: " + finalTime);
            return new DifferenceOutput(difference_In_Time, finalTime);
        }
        // Catch the Exception
        catch (ParseException e) {
            System.out.println(e.getMessage());
            return new DifferenceOutput(-1, "Failed to get difference in time");
        }
    }

    public static void deleteAllFilesWithSpecificExtension(String pathToDir, String extension) {
        File folder = new File(pathToDir);
        File[] fList = folder.listFiles();
        assert fList != null;
        for (File file : fList) {
            String pes = file.getName();
            if (pes.endsWith("." + extension)) {
                boolean success = new File(String.valueOf(file)).delete();
                System.out.println("Deleted all files in specified directory with extension ." + extension);
            }
        }
    }

    static class DifferenceOutput {
        private long milliseconds;
        private String text;

        public DifferenceOutput(long milliseconds, String text) {
            this.milliseconds = milliseconds;
            this.text = text;
        }

        public long getMilliseconds() {
            return milliseconds;
        }

        public void setMilliseconds(long milliseconds) {
            this.milliseconds = milliseconds;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}