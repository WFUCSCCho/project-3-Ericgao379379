///∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗*
//∗ @file: Proj3.java
//∗ @description: This program have bubble, quick, heap, merge and transposition sorting algorithms that takes Automobile.csv as input file and generate a csv file with time taken and a txt file of the sorted lists
//∗ @author: Eric
//∗ @date: November 10, 2025
//∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {
        // Finish Me
        if(left >= right){
            return;
        }
        // Find the middle point
        int mid = (left + right) / 2;
        // Sort first and second halves
        mergeSort(a, left, mid);
        mergeSort(a, mid + 1, right);
        // Merge the sorted halves
        merge(a, left, mid, right);
    }

    public static <T extends Comparable> void merge(ArrayList<T> a, int left, int mid, int right) {
        // Finish Me
        // Find sizes of two subarrays to be merged
        int num1 = mid - left + 1;
        int num2 = right - mid;
        // Create temp ArrayLists
        ArrayList<T> L = new ArrayList<>(num1);
        ArrayList<T> R = new ArrayList<>(num2);
        // Copy data to temp arrays
        for (int i = 0; i < num1; i++)
            L.add(a.get(left + i));
        for (int j = 0; j < num2; j++)
            R.add(a.get(mid + j + 1));
        // Initial indices of first and second subarrays. Initial index of merged subarray array
        int i = 0, j = 0, k = left;
        // Initial index of merged subarray array
        while (i < num1 && j < num2) {
            if (L.get(i).compareTo(R.get(j)) <= 0) {
                a.set(k++, L.get(i++));
            }else{
                a.set(k++, R.get(j++));
            }
        }
        // Copy remaining elements of L[] if any
        while(i < num1){
            a.set(k++, L.get(i++));
        }
        // Copy remaining elements of R[] if any
        while(j < num2){
            a.set(k++, R.get(j++));
        }

    }

    // Quick Sort
    public static <T extends Comparable> void quickSort(ArrayList<T> a, int left, int right) {
        // Finish Me
        if (left >= right) {
            return;
        }
        // the partition return index of pivot
        int p = partition(a, left, right);
        // recursion calls for smaller element and greater or equals elements
        quickSort(a, left, p - 1);
        quickSort(a, p + 1, right);
    }

    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {
        // Finish Me
        // choose the pivot
        T pivot = a.get(right);
        // index of smaller element and indicates the right position of pivot found so far
        int i = left -1;
        // go through and move small elements to the left
        for (int j = left; j <= right - 1; j++) {
            if (a.get(j).compareTo(pivot) < 0) {
                i++;
                swap(a, i, j);
            }
        }
        // Move pivot after smaller elements
        swap(a, i + 1, right);
        return i + 1;
    }

    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable> void heapSort(ArrayList<T> a, int left, int right) {
        // Finish Me
        if (left >= right) {
            return;
        }

        int n = right - left + 1;
        // Build max-heap, starting from the last parent node.
        for (int i = left + n / 2 - 1; i >= left; i--) {
            heapify(a, i, left, right);
        }
        // Extract max element one by one
        for (int end = right; end > left; end--) {
            swap(a, left, end);
            heapify(a, left, left, end - 1);
        }
    }

    public static <T extends Comparable> void heapify (ArrayList<T> a,int root, int left, int right) {
        // Finish Me
        int current = root;
        while(true){
            int leftChild = (current - left) * 2 + 1 + left;
            int rightChild = leftChild + 1;
            int largest = current;
            // If left child is larger than root
            if (leftChild <= right && a.get(leftChild).compareTo(a.get(largest)) > 0) {
                largest = leftChild;
            }
            // If right child is larger than largest so far
            if (rightChild <= right && a.get(rightChild).compareTo(a.get(largest)) > 0) {
                largest = rightChild;
            }

            if (largest == current) {
                break;
            }

            swap(a, current, largest);
            current = largest;
        }

    }

    // Bubble Sort
    public static <T extends Comparable> int bubbleSort(ArrayList<T> a, int size) {
        // Finish Me
        int comparisons = 0;
        boolean swapped;
        for (int i = 0; i < size - 1; i++) {
            swapped = false;
            for (int j = 0; j < size - i - 1; j++) {
                comparisons++; // we compare a[j] and a[j+1]
                if (a.get(j).compareTo(a.get(j + 1)) > 0) {
                    swap(a, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break; // already sorted
            }
        }
        return comparisons;
    }

    // Odd-Even Transposition Sort
    public static <T extends Comparable> int transpositionSort(ArrayList<T> a, int size) {
        boolean isSorted = false;
        int comparisons = 0; // one per phase (odd or even)

        while (!isSorted) {
            isSorted = true;

            // Odd phase: compare (1,2), (3,4), ...
            boolean swappedInPhase = false;
            for (int i = 1; i < size - 1; i += 2) {
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    swappedInPhase = true;
                }
            }
            comparisons++; // count this entire phase as one comparison
            if (swappedInPhase){
                isSorted = false;
            }

            // Even phase: compare (0,1), (2,3), ...
            swappedInPhase = false;
            for (int i = 0; i < size - 1; i += 2) {
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    swappedInPhase = true;
                }
            }
            comparisons++; // count this entire phase as one comparison
            if (swappedInPhase){
                isSorted = false;
            }
        }
        return comparisons;
    }

    public static void main(String [] args)  throws IOException {
        if (args.length != 3) {
            System.out.println("Usage: java Proj3 {dataset-file} {sorting-algorithm-type} {number-of-lines}");
            System.out.println("sorting-algorithm-type: bubble | merge | quick | heap | transposition");
            return;
        }

        String datasetFile = args[0];
        String algorithm = args[1].toLowerCase().trim();
        int linesToRead = Integer.parseInt(args[2]);

        ArrayList<Automobile> base = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(datasetFile))) {
            String line;
            br.readLine(); // Skip header
            while (base.size() < linesToRead && (line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    try {
                        base.add(new Automobile(line.trim()));
                    } catch (Exception e) {
                        System.err.println("Skipping malformed line: " + line);
                    }
                }
            }
        }

        if (base.isEmpty()) {
            System.out.println("No data read from file. Exiting.");
            return;
        }

        // Build variants
        ArrayList<Automobile> alreadySorted = new ArrayList<>(base);
        java.util.Collections.sort(alreadySorted);

        ArrayList<Automobile> shuffled = new ArrayList<>(base);
        java.util.Collections.shuffle(shuffled);

        ArrayList<Automobile> reversed = new ArrayList<>(base);
        java.util.Collections.sort(reversed, java.util.Collections.reverseOrder());

        java.nio.file.Path csvPath = java.nio.file.Paths.get("analysis.csv");
        boolean needHeader = !java.nio.file.Files.exists(csvPath);
        java.io.BufferedWriter csvOut = new java.io.BufferedWriter(new java.io.FileWriter("analysis.csv", true));
        if (needHeader) {
            csvOut.write("timestamp,algorithm,N,input_state,time_seconds,comparisons\n");
        }
        String timestamp = java.time.LocalDateTime.now().toString();

        java.io.BufferedWriter sortedOut = new java.io.BufferedWriter(new java.io.FileWriter("sorted.txt", false));
        sortedOut.write("Algorithm: " + algorithm + "\n\n");

        java.util.Locale loc = java.util.Locale.ROOT;

        switch (algorithm) {
            case "merge": {
                // already_sorted
                ArrayList<Automobile> in1 = new ArrayList<>(alreadySorted);
                long t0 = System.nanoTime();
                mergeSort(in1, 0, in1.size() - 1);
                long t1 = System.nanoTime();
                double secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "MERGE | already_sorted | N=%d | time=%.6f s%n", in1.size(), secs);
                csvOut.write(String.format(loc, "%s,merge,%d,already_sorted,%.9f,%s%n", timestamp, in1.size(), secs, ""));
                sortedOut.write("[already_sorted] -> sorted output (" + in1.size() + "):\n");
                for (Automobile s : in1) sortedOut.write(s + "\n");
                sortedOut.write("\n");

                // shuffled
                ArrayList<Automobile> in2 = new ArrayList<>(shuffled);
                t0 = System.nanoTime();
                mergeSort(in2, 0, in2.size() - 1);
                t1 = System.nanoTime();
                secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "MERGE | shuffled       | N=%d | time=%.6f s%n", in2.size(), secs);
                csvOut.write(String.format(loc, "%s,merge,%d,shuffled,%.9f,%s%n", timestamp, in2.size(), secs, ""));
                sortedOut.write("[shuffled] -> sorted output (" + in2.size() + "):\n");
                for (Automobile s : in2) sortedOut.write(s + "\n");
                sortedOut.write("\n");

                // reversed
                ArrayList<Automobile> in3 = new ArrayList<>(reversed);
                t0 = System.nanoTime();
                mergeSort(in3, 0, in3.size() - 1);
                t1 = System.nanoTime();
                secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "MERGE | reversed       | N=%d | time=%.6f s%n", in3.size(), secs);
                csvOut.write(String.format(loc, "%s,merge,%d,reversed,%.9f,%s%n", timestamp, in3.size(), secs, ""));
                sortedOut.write("[reversed] -> sorted output (" + in3.size() + "):\n");
                for (Automobile s : in3) sortedOut.write(s + "\n");
                sortedOut.write("\n");
                break;
            }

            case "quick": {
                ArrayList<Automobile> in1 = new ArrayList<>(alreadySorted);
                long t0 = System.nanoTime();
                quickSort(in1, 0, in1.size() - 1);
                long t1 = System.nanoTime();
                double secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "QUICK | already_sorted | N=%d | time=%.6f s%n", in1.size(), secs);
                csvOut.write(String.format(loc, "%s,quick,%d,already_sorted,%.9f,%s%n", timestamp, in1.size(), secs, ""));
                sortedOut.write("[already_sorted] -> sorted output (" + in1.size() + "):\n");
                for (Automobile s : in1) sortedOut.write(s + "\n");
                sortedOut.write("\n");

                ArrayList<Automobile> in2 = new ArrayList<>(shuffled);
                t0 = System.nanoTime();
                quickSort(in2, 0, in2.size() - 1);
                t1 = System.nanoTime();
                secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "QUICK | shuffled       | N=%d | time=%.6f s%n", in2.size(), secs);
                csvOut.write(String.format(loc, "%s,quick,%d,shuffled,%.9f,%s%n", timestamp, in2.size(), secs, ""));
                sortedOut.write("[shuffled] -> sorted output (" + in2.size() + "):\n");
                for (Automobile s : in2) sortedOut.write(s + "\n");
                sortedOut.write("\n");

                ArrayList<Automobile> in3 = new ArrayList<>(reversed);
                t0 = System.nanoTime();
                quickSort(in3, 0, in3.size() - 1);
                t1 = System.nanoTime();
                secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "QUICK | reversed       | N=%d | time=%.6f s%n", in3.size(), secs);
                csvOut.write(String.format(loc, "%s,quick,%d,reversed,%.9f,%s%n", timestamp, in3.size(), secs, ""));
                sortedOut.write("[reversed] -> sorted output (" + in3.size() + "):\n");
                for (Automobile s : in3) sortedOut.write(s + "\n");
                sortedOut.write("\n");
                break;
            }

            case "heap": {
                ArrayList<Automobile> in1 = new ArrayList<>(alreadySorted);
                long t0 = System.nanoTime();
                heapSort(in1, 0, in1.size() - 1);
                long t1 = System.nanoTime();
                double secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "HEAP  | already_sorted | N=%d | time=%.6f s%n", in1.size(), secs);
                csvOut.write(String.format(loc, "%s,heap,%d,already_sorted,%.9f,%s%n", timestamp, in1.size(), secs, ""));
                sortedOut.write("[already_sorted] -> sorted output (" + in1.size() + "):\n");
                for (Automobile s : in1) sortedOut.write(s + "\n");
                sortedOut.write("\n");

                ArrayList<Automobile> in2 = new ArrayList<>(shuffled);
                t0 = System.nanoTime();
                heapSort(in2, 0, in2.size() - 1);
                t1 = System.nanoTime();
                secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "HEAP  | shuffled       | N=%d | time=%.6f s%n", in2.size(), secs);
                csvOut.write(String.format(loc, "%s,heap,%d,shuffled,%.9f,%s%n", timestamp, in2.size(), secs, ""));
                sortedOut.write("[shuffled] -> sorted output (" + in2.size() + "):\n");
                for (Automobile s : in2) sortedOut.write(s + "\n");
                sortedOut.write("\n");

                ArrayList<Automobile> in3 = new ArrayList<>(reversed);
                t0 = System.nanoTime();
                heapSort(in3, 0, in3.size() - 1);
                t1 = System.nanoTime();
                secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "HEAP  | reversed       | N=%d | time=%.6f s%n", in3.size(), secs);
                csvOut.write(String.format(loc, "%s,heap,%d,reversed,%.9f,%s%n", timestamp, in3.size(), secs, ""));
                sortedOut.write("[reversed] -> sorted output (" + in3.size() + "):\n");
                for (Automobile s : in3) sortedOut.write(s + "\n");
                sortedOut.write("\n");
                break;
            }

            case "bubble": {
                ArrayList<Automobile> in1 = new ArrayList<>(alreadySorted);
                long t0 = System.nanoTime();
                long comps = bubbleSort(in1, in1.size());
                long t1 = System.nanoTime();
                double secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "BUBBLE| already_sorted | N=%d | time=%.6f s | comparisons=%d%n", in1.size(), secs, comps);
                csvOut.write(String.format(loc, "%s,bubble,%d,already_sorted,%.9f,%d%n", timestamp, in1.size(), secs, comps));
                sortedOut.write("[already_sorted] -> sorted output (" + in1.size() + "):\n");
                for (Automobile s : in1) sortedOut.write(s + "\n");
                sortedOut.write("\n");

                ArrayList<Automobile> in2 = new ArrayList<>(shuffled);
                t0 = System.nanoTime();
                comps = bubbleSort(in2, in2.size());
                t1 = System.nanoTime();
                secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "BUBBLE| shuffled       | N=%d | time=%.6f s | comparisons=%d%n", in2.size(), secs, comps);
                csvOut.write(String.format(loc, "%s,bubble,%d,shuffled,%.9f,%d%n", timestamp, in2.size(), secs, comps));
                sortedOut.write("[shuffled] -> sorted output (" + in2.size() + "):\n");
                for (Automobile s : in2) sortedOut.write(s + "\n");
                sortedOut.write("\n");

                ArrayList<Automobile> in3 = new ArrayList<>(reversed);
                t0 = System.nanoTime();
                comps = bubbleSort(in3, in3.size());
                t1 = System.nanoTime();
                secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "BUBBLE| reversed       | N=%d | time=%.6f s | comparisons=%d%n", in3.size(), secs, comps);
                csvOut.write(String.format(loc, "%s,bubble,%d,reversed,%.9f,%d%n", timestamp, in3.size(), secs, comps));
                sortedOut.write("[reversed] -> sorted output (" + in3.size() + "):\n");
                for (Automobile s : in3) sortedOut.write(s + "\n");
                sortedOut.write("\n");
                break;
            }

            case "transposition": {
                ArrayList<Automobile> in1 = new ArrayList<>(alreadySorted);
                long t0 = System.nanoTime();
                long comps = transpositionSort(in1, in1.size()); // 1 comparison per phase
                long t1 = System.nanoTime();
                double secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "TRANS | already_sorted | N=%d | time=%.6f s | comparisons=%d (1 per phase)%n", in1.size(), secs, comps);
                csvOut.write(String.format(loc, "%s,transposition,%d,already_sorted,%.9f,%d%n", timestamp, in1.size(), secs, comps));
                sortedOut.write("[already_sorted] -> sorted output (" + in1.size() + "):\n");
                for (Automobile s : in1) sortedOut.write(s + "\n");
                sortedOut.write("\n");

                ArrayList<Automobile> in2 = new ArrayList<>(shuffled);
                t0 = System.nanoTime();
                comps = transpositionSort(in2, in2.size());
                t1 = System.nanoTime();
                secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "TRANS | shuffled       | N=%d | time=%.6f s | comparisons=%d (1 per phase)%n", in2.size(), secs, comps);
                csvOut.write(String.format(loc, "%s,transposition,%d,shuffled,%.9f,%d%n", timestamp, in2.size(), secs, comps));
                sortedOut.write("[shuffled] -> sorted output (" + in2.size() + "):\n");
                for (Automobile s : in2) sortedOut.write(s + "\n");
                sortedOut.write("\n");

                ArrayList<Automobile> in3 = new ArrayList<>(reversed);
                t0 = System.nanoTime();
                comps = transpositionSort(in3, in3.size());
                t1 = System.nanoTime();
                secs = (t1 - t0) / 1_000_000_000.0;
                System.out.printf(loc, "TRANS | reversed       | N=%d | time=%.6f s | comparisons=%d (1 per phase)%n", in3.size(), secs, comps);
                csvOut.write(String.format(loc, "%s,transposition,%d,reversed,%.9f,%d%n", timestamp, in3.size(), secs, comps));
                sortedOut.write("[reversed] -> sorted output (" + in3.size() + "):\n");
                for (Automobile s : in3) sortedOut.write(s + "\n");
                sortedOut.write("\n");
                break;
            }

            default:
                System.out.println("Unknown algorithm: " + algorithm);
                csvOut.close();
                sortedOut.close();
                return;
        }

        // --- Close files and final console note ---
        csvOut.close();
        sortedOut.close();

        System.out.println("\n=== Run Complete ===");
        System.out.println("Algorithm : " + algorithm);
        System.out.println("N         : " + base.size());
        System.out.println("CSV       : appended to analysis.csv");
        System.out.println("Sorted    : overwritten in sorted.txt");



    }
}

class Automobile implements Comparable<Automobile> {
    String name;
    double mpg;
    int cylinders;
    double displacement;
    double horsepower;
    double weight;
    double acceleration;
    int model_year;
    String origin;

    public Automobile(String line) {
        String[] parts = line.split(",");
        this.name = parts[0];
        try {
            this.mpg = Double.parseDouble(parts[1]);
        } catch (NumberFormatException e) {
            this.mpg = 0;
        }
        this.cylinders = Integer.parseInt(parts[2]);
        this.displacement = Double.parseDouble(parts[3]);
        try {
            this.horsepower = Double.parseDouble(parts[4]);
        } catch (NumberFormatException e) {
            this.horsepower = 0;
        }
        this.weight = Double.parseDouble(parts[5]);
        this.acceleration = Double.parseDouble(parts[6]);
        this.model_year = Integer.parseInt(parts[7]);
        this.origin = parts[8];
    }

    @Override
    public int compareTo(Automobile other) {
        return Double.compare(this.acceleration, other.acceleration);
    }

    @Override
    public String toString() {
        return name + "," + mpg + "," + cylinders + "," + displacement + "," + horsepower + "," + weight + "," + acceleration + "," + model_year + "," + origin;
    }
}
