///////////////////////////////////////////////////////////////////////////////
//
// Title: p1 Implement and Test an ADT
// Class: TestMaxPQ
// Course: CS400
//
// Author: Khai Bin Woon
// Email: woon2@wisc.edu
// Due : Feb 5 2018
// Known Bugs :
// Limitations : null value inputs are ignored
//
//////////////////////////////////////////////////////////////////////////////
import java.util.Arrays;
import java.util.Random;

public class TestMaxPQ {
    private static final boolean PASS = true;
    private static final boolean FAIL = false;
    private static int num = 11; // number of tests

    public static void main(String args[]) {
        int passed = 0;

        passed += test01_isEmpty() ? 1 : 0;
        passed += test02_getMax_Exception() ? 1 : 0;
        passed += test03_removeMax_Exception() ? 1 : 0;
        passed += test04_getMax_alter() ? 1 : 0;
        passed += test05_insert_remove_1() ? 1 : 0;
        passed += test06_insert_remove_many() ? 1 : 0;
        passed += test07_dups_allowed() ? 1 : 0;
        passed += test08_dups_zeros() ? 1 : 0;
        passed += test09_extra_removeMax() ? 1 : 0;
        passed += test10_ignore_null_values() ? 1 : 0;
        passed += test11_big_data() ? 1 : 0;

        System.out.println("\nTests passed: " + passed + "/" + num);
        if (passed == num)
            System.out.println("All test passed.");
    }

    public static boolean test01_isEmpty() {
        String s = "TEST(1/" + num + ") isEmpty()";
        System.out.printf("%-45s - ", s);
        PriorityQueueADT<Integer> pq = new MaxPQ<Integer>();
        if (pq.isEmpty() == true) {
            System.out.println("PASSED");
            return PASS;
        } else {
            System.out.println("FAILED\nisEmpty() method did not return true on empty queue");
            return FAIL;
        }
    }

    public static boolean test02_getMax_Exception() {
        String s = "TEST(2/" + num + ") getMax() throwned Exception()";
        System.out.printf("%-45s - ", s);

        PriorityQueueADT<Integer> pq = new MaxPQ<Integer>();
        
        try {
            Integer actualMax = pq.getMax();

            pq.getMax(); // should throw EmptyQueueException
        } catch (EmptyQueueException e) {
            System.out.println("PASSED");
            return PASS;
        } catch (Exception e) {
            System.out.println("FAILED.\nAn unexpected exception was thrown.\n" + e.toString());
            return FAIL;
        }
        System.out.println("FAILED. \nNo exceptions thrown.");
        return FAIL;
    }

    public static boolean test03_removeMax_Exception() {
        String s = "TEST(3/" + num + ") removeMax() throwned Exception()";
        System.out.printf("%-45s - ", s);

        PriorityQueueADT<Integer> pq = new MaxPQ<Integer>();
        try {
            pq.removeMax(); // should throw EmptyQueueException
        } catch (EmptyQueueException e) {
            System.out.println("PASSED");
            return PASS;
        } catch (Exception e) {
            System.out.println("FAILED.\nAn unexpected exception was thrown.\n" + e.toString());
            return FAIL;
        }
        System.out.println("FAILED. \nNo exceptions thrown.");
        return FAIL;
    }

    public static boolean test04_getMax_alter() {
        String s = "TEST(4/" + num + ") getMax() does not alter array";
        System.out.printf("%-45s - ", s);

        PriorityQueueADT<Integer> pq = new MaxPQ<Integer>();
        try {
            Integer expected = 4;
            pq.insert(expected);
            pq.insert(9);
            pq.getMax();
            pq.getMax();
            pq.getMax();
            if (pq.getMax() == pq.getMax()) {
                System.out.println("PASSED");
                return PASS;
            } else {
                System.out.println("FAILED. \ngetMax() returns " + pq.getMax()
                                + ". When expected was");
                return FAIL;
            }
        } catch (Exception e) {
            System.out.println("FAILED. \nAn exception was caught.\n" + e.toString());
            return FAIL;
        }
    }

    public static boolean test05_insert_remove_1() {
        String s = "TEST(5/" + num + ") inserting and removing 1 element";
        System.out.printf("%-45s - ", s);

        PriorityQueueADT<Integer> pq = new MaxPQ<Integer>();
        int actual = 10;
        int expected = actual;

        pq.insert(actual);
        Integer removed = pq.removeMax();

        if (removed.equals(expected)) {
            System.out.println("PASSED");
            return PASS;
        } else {
            System.out.println("FAILED. \nExpected element: " + expected + "\nActual element: "
                            + removed);
            return FAIL;
        }
    }

    public static boolean test06_insert_remove_many() {
        String s = "TEST(6/" + num + ") inserting and removing 8 elements";
        System.out.printf("%-45s - ", s);

        int[] unsorted = {4, 9, 2, 6, 8, 5, 1, 7};
        int[] sorted = unsorted.clone();
        Arrays.sort(sorted);
        boolean passFail = PASS;

        PriorityQueueADT<Integer> pq = new MaxPQ<Integer>();
        for (int u : unsorted)
            pq.insert(u);
        for (int i = sorted.length - 1; i >= 0; i--) {
            Integer expected = sorted[i];
            Integer actual = pq.removeMax();

            if (!(expected.equals(actual))) {
                System.out.println("FAILED \nExpected element: " + expected + "\nActual element: "
                                + actual);
                passFail = FAIL;
            }
        }
        if (passFail == true)
            System.out.println("PASSED");
        return passFail;
    }

    public static boolean test07_dups_allowed() {
        String s = "TEST(7/" + num + ") inserting duplicate elements";
        System.out.printf("%-45s - ", s);

        int[] unsorted = {5, 5, 5, 4, 6, 8, 5, 5};
        int[] sorted = unsorted.clone();
        Arrays.sort(sorted);
        boolean passFail = PASS;

        PriorityQueueADT<Integer> pq = new MaxPQ<Integer>();
        for (int u : unsorted)
            pq.insert(u);

        for (int i = sorted.length - 1; i >= 0; i--) {
            Integer expected = sorted[i];
            Integer actual = pq.removeMax();

            if (!(expected.equals(actual))) {
                System.out.println("FAILED \nExpected element: " + expected + "\nActual element: "
                                + actual);
                passFail = FAIL;
            }
        }
        if (passFail == true)
            System.out.println("PASSED");
        return passFail;
    }

    public static boolean test08_dups_zeros() {
        String s = "TEST(8/" + num + ") inserting duplicate zeros"; // since int arrays initialize
                                                                    // with 0's
        System.out.printf("%-45s - ", s);

        int[] unsorted = {0, 1, 0, 2, 0, 3, 0, 4};
        int[] sorted = unsorted.clone();
        Arrays.sort(sorted);
        boolean passFail = PASS;

        PriorityQueueADT<Integer> pq = new MaxPQ<Integer>();
        for (int u : unsorted)
            pq.insert(u);

        for (int i = sorted.length - 1; i >= 0; i--) {
            Integer expected = sorted[i];
            Integer actual = pq.removeMax();

            if (!(expected.equals(actual))) {
                System.out.println("FAILED \nExpected element: " + expected + "\nActual element: "
                                + actual);
                passFail = FAIL;
            }
        }
        if (passFail == true)
            System.out.println("PASSED");
        return passFail;
    }

    public static boolean test09_extra_removeMax() {
        String s = "TEST(9/" + num + ") over amounts of removeMax()";
        System.out.printf("%-45s - ", s);

        int [] unsorted = {4, 4, 4, 4, 4};

        PriorityQueueADT<Integer> pq = new MaxPQ<Integer>();
        for (int u : unsorted)
            pq.insert(u);

        try {
            for (int i = 0; i <= unsorted.length + 1; i++) {
                pq.removeMax();
            }
        } catch (EmptyQueueException e) {
            System.out.println("PASSED");
            return PASS;
        } catch (Exception e) {
            System.out.println("FAILED.\nAn unexpected exception was thrown.\n" + e.toString());
            return FAIL;
        }
        System.out.println("FAILED. \nNo exceptions thrown.");
        return FAIL;
    }

    public static boolean test10_ignore_null_values() {
        String s = "TEST(10/" + num + ") inserting null values (ignored)";
        System.out.printf("%-45s - ", s);

        Integer[] unsorted = {1, null, null, 2, null};
        Integer[] sorted = {1, 2};
        Integer actual;
        Integer expected = sorted[0];
        boolean passFail = PASS;

        try {
            PriorityQueueADT<Integer> pq = new MaxPQ<Integer>();
            for (int i = 0; i < unsorted.length; i++)
                pq.insert(unsorted[i]);

            // doesn't matter how the null value is handled, checks if valid inputs are inserted and
            // removed correctly
            for (int i = sorted.length - 1; i >= 0; i--) {
                actual = pq.removeMax();
                expected = sorted[i];
                if (!(expected.equals(actual))) {
                    System.out.println("FAILED \nExpected element: " + expected
                                    + "\nActual element: " + actual);
                    passFail = FAIL;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("FAILED. \nNullPointerException caught when " + expected
                            + " is expected.");
            return FAIL;
        } catch (Exception e) {
            System.out.println("FAILED.\nAn unexpected exception was thrown.\n" + e.toString());
            return FAIL;
        }
        if (passFail == true)
            System.out.println("PASSED");
        return passFail;
    }

    public static boolean test11_big_data() {
        String s = "TEST(11/" + num + ") inserting 100 random values";
        System.out.printf("%-45s - ", s);

        PriorityQueueADT<Integer> pq = new MaxPQ<Integer>();
        Integer[] unsorted = new Integer[100];
        Random rand = new Random();

        try {
            for (int i = 0; i < 100; i++) {
                unsorted[i] = rand.nextInt(100) + 1;
                pq.insert(unsorted[i]);
            }

            Integer[] sorted = unsorted.clone();
            Arrays.sort(sorted);
            boolean passFail = PASS;

            for (int i = sorted.length - 1; i >= 0; i--) {
                Integer actual = pq.removeMax();
                Integer expected = sorted[i];

                if (!(expected.equals(actual))) {
                    System.out.println("FAILED \nExpected element: " + expected
                                    + "\nActual element: " + actual);
                    passFail = FAIL;
                }
            }
            if (passFail == true)
                System.out.println("PASSED");
            return passFail;
        } catch (EmptyQueueException e) {
            System.out.println("FAILED \nAn exception was caught. \n" + e.toString());
            return FAIL;
        }
    }
}
