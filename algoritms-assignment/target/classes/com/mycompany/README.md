 Final Analysis Report: Sorting Algorithm Performance
Introduction
This report compares the performance and complexity metrics of three sorting algorithms—MergeSort, QuickSort, and TimSort—across various array sizes (N) and data states (Random, Sorted, Reverse). The analysis focuses on execution time, comparison counts, maximum recursion depth, and memory allocations.

1. Performance Summary (Execution Time)
TimSort is the clear winner in terms of speed across all tested scenarios, thanks to its highly optimized, hybrid design.

Random Data: All algorithms perform well, demonstrating O(NlogN) complexity. TimSort is the fastest, with MergeSort and QuickSort being closely matched.

Sorted and Reverse Data (Worst Cases): This is where differences are most pronounced:

TimSort shows near-instantaneous execution, performing excellently on already or partially sorted data.

QuickSort exhibits severe degradation. In the worst-case scenarios (Sorted and Reverse), its execution time dramatically increases (e.g., up to 77.68 ms at N=100,000 for Sorted data), confirming its O(N 
2
 ) worst-case time complexity.

MergeSort remains stable and predictable, maintaining its O(NlogN) performance regardless of the input order.

2. Complexity and Reliability Analysis
This section examines the underlying metrics of algorithm stability.

A. Recursion Depth (Max_Depth)
MergeSort demonstrates perfect reliability with a minimum, predictable recursion depth of O(logN). This guarantees that the algorithm will not cause a Stack Overflow Error even with extremely large data sets.

QuickSort shows a critical vulnerability. In the Sorted and Reverse scenarios, its recursion depth skyrockets (up to 331 at N=100,000). This linear increase (O(N)) confirms its worst-case behavior and makes it inherently risky for uncontrolled input data.

B. Memory Allocations
MergeSort is a space-inefficient algorithm, requiring substantial and predictable additional memory O(N) for its auxiliary arrays.

QuickSort, while generally space-efficient, becomes highly unstable in the worst case. The extreme recursion depth causes memory allocations to multiply rapidly, in some cases exceeding even MergeSort's stable usage.

3. Conclusion and Recommendation
The testing clearly demonstrates a trade-off between stability and average-case speed.

Fastest Algorithm: TimSort. Recommended for maximum performance where data is expected to be real-world (partially sorted).

Most Reliable Algorithm: MergeSort. Recommended when stability and guaranteed prevention of stack overflows are paramount, regardless of the input data.

Riskiest Algorithm: QuickSort. Its dramatic performance and stability degradation in worst-case scenarios make it unsuitable for applications where input data cannot be guaranteed to be random.

Final Recommendation: For an application requiring robust stability against all input types, MergeSort is the recommended choice due to its guaranteed O(NlogN) performance and safe O(logN) recursion depth.