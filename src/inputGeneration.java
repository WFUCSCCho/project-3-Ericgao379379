import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class inputGeneration {
    public static void main(String[] args) throws IOException {
        // --- Command line: java GenerateDataset filename count ---
        if (args.length != 2) {
            System.out.println("Usage: java GenerateDataset {filename} {count}");
            return;
        }

        String filename = args[0];
        int count = Integer.parseInt(args[1]);

        // --- Create a list of integers 1..count ---
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            list.add(i);
        }

        // --- Shuffle them for randomness ---
        Collections.shuffle(list);

        // --- Write them to file ---
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (int num : list) {
                bw.write(num + "\n");
            }
        }

        System.out.println("✅ Randomized dataset generated: " + filename);
        System.out.println("   Total numbers: " + count);
    }
}
