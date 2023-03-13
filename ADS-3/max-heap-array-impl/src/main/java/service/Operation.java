package service;

import java.util.List;


/**
 * Abstract class operation, which provides methods like</br>
 * -> perform : to write in the algorithm to perform the particular operation.</br>
 * -> compute: to compute the output from perform method and display in a nice output.</br>
 * -> computeWithTimer-> similar to compute just wraps a timer to it.</br>
 **/

public abstract class Operation {

    protected abstract Object Perform() throws CloneNotSupportedException;

    public abstract Object Compute() throws CloneNotSupportedException;

    public Object ComputeWithTimer() throws CloneNotSupportedException {
        var startTime = (double)System.nanoTime();
        var result = Compute();
        var endTime = (double) System.nanoTime();
        var timeElapsed = endTime - startTime;
        System.out.println("Execution time in nanoseconds: " + timeElapsed);
        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
        return result;
    }
}

