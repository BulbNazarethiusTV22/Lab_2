import java.util.*;
import java.util.concurrent.*;

class Main {
    public static void main(String[] args) throws 
    InterruptedException, ExecutionException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter min value: ");
        int minValue = scanner.nextInt();
        System.out.println("Enter max value: ");
        int maxValue = scanner.nextInt();
        System.out.println("Enter multiplier: ");
        int multiplier = scanner.nextInt();
        
        int size = new Random().nextInt(21) + 40;
        int [] array = new Random().ints(size,
        minValue, maxValue + 1).toArray();
        System.out.println("Generated array: " + Arrays.toString(array));
        
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List <Future<int[]>> futures = new ArrayList<>();
        int chunk = 10;
        CopyOnWriteArrayList<Integer> resultList = new CopyOnWriteArrayList<>();
        
        for (int i = 0; i < array.length; i += chunk) {
            int start = i;
            int end = Math.min(i + chunk, array.length);
            
            Callable <int[]> task = new ArrayMultiplier(Arrays.copyOfRange(array, start, end), multiplier);
            Future <int[]> future = executorService.submit(task);
            futures.add(future);
        }
        
        for (Future<int[]> future: futures) {
            if (!future.isCancelled()) {
                int [] processedChunk = future.get();
                for (int value : processedChunk) {
                    resultList.add(value);
                }
            }
        }
        
        executorService.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("New Array: " + resultList);
        System.out.println("Time of execution: " + (endTime - startTime) + " ms");
    }
}

class ArrayMultiplier implements Callable<int[]> {
    private final int [] array;
    private final int multiplier;
    
    public ArrayMultiplier(int [] array, int multiplier) {
        this.array = array;
        this.multiplier = multiplier;
    }
    
    @Override 
    public int[] call() {
        int [] result = new int[array.length];
        for(int i = 0; i < array.length; i++) {
            result[i] = array[i] * multiplier;
        }
        return result;
    }
}
