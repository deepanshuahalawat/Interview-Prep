# Heap Patterns — Interview Pattern Map

## 0. First understand what a Heap gives you

A heap is useful when the problem repeatedly asks:

> **"Give me the smallest/largest element among the currently available elements."**

Think:

```text
Need repeated min/max
        ↓
Priority Queue / Heap
```

### Min Heap

Smallest element is always available at the top.

```text
        1
      /   \
     3     5
    / \   /
   7   8 9
```

Java:

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
```

### Max Heap

Largest element is always available at the top.

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>(Collections.reverseOrder());
```

---

# 1. Pattern — Top K Elements

This is probably the **most important heap pattern**.

### Problem

You need:

- K largest elements
- K smallest elements
- K most frequent elements
- K closest elements
- K highest scores

### Key idea

If you need only `K` elements, don't sort everything.

For **K largest**, maintain a **min heap of size K**.

Example:

```text
Array:
3 1 5 12 2 11 8

K = 3
```

Whenever the heap becomes larger than `K`, remove the smallest.

At the end, the heap contains the K largest elements.

### Why min heap?

Among the current top K candidates, you want the **smallest one available for removal**.

### Recognition

Look for:

- "K largest"
- "K smallest"
- "Top K"
- "K most frequent"
- "K closest"

### Mental model

> **Top K largest → min heap of size K.**

> **Top K smallest → max heap of size K.**

### Complexity

```text
O(N log K)
```

instead of sorting:

```text
O(N log N)
```

---

# 2. Pattern — Kth Largest / Kth Smallest

This is basically Top K with a different output.

### Kth largest

Use:

```text
Min Heap
size = K
```

Example:

```text
3 1 5 12 2 11
K = 3
```

After processing:

```text
heap = [5, 12, 11]
```

The smallest element is:

```text
5
```

Therefore:

```text
3rd largest = 5
```

### Kth smallest

Reverse the idea:

```text
Max Heap
size = K
```

### Mental model

> **Kth largest → min heap of size K.**

> **Kth smallest → max heap of size K.**

---

# 3. Pattern — Two Heaps / Running Median

This is a major interview pattern.

### Problem

Numbers arrive one by one and you need the median after each insertion.

Instead of sorting after every insertion, use two heaps.

```text
        Left half       Right half

       Max Heap         Min Heap
       [smaller]        [larger]
```

Example:

```text
1 3 5 7 9
```

Split:

```text
Left:  [1, 3, 5]
Right: [7, 9]
```

Max heap gives `5`.

Min heap gives `7`.

The median is determined from their tops.

### Why two different heaps?

The left half needs its **largest** element:

```text
Max Heap
```

The right half needs its **smallest** element:

```text
Min Heap
```

### Invariants

Maintain:

```text
|left.size() - right.size()| <= 1
```

and:

```text
max(left) <= min(right)
```

### Recognition

Look for:

- Running median
- Median of stream
- Data arriving continuously
- Median after every insertion

### Mental model

> **Median = split data into two halves → Max Heap + Min Heap.**

---

# 4. Pattern — Merge K Sorted Structures

Suppose:

```text
List 1: 1 4 7
List 2: 2 5 8
List 3: 3 6 9
```

You need one sorted list.

Don't combine everything and sort again.

Instead, keep the smallest unprocessed element from each list in a **min heap**.

Initially:

```text
1
2
3
```

Take `1`, then insert the next element from List 1:

```text
2
3
4
```

Continue.

### Heap size

At most:

```text
K
```

where K is the number of sorted lists.

### Complexity

For `N` total elements:

```text
O(N log K)
```

### Recognition

- Merge K sorted arrays
- Merge K sorted linked lists
- K sorted streams
- Smallest element from multiple sorted sources

### Mental model

> **K sorted sources → heap contains one candidate from each source.**

---

# 5. Pattern — Heap + Greedy Selection

Sometimes the heap isn't the main algorithm.

Instead:

```text
Greedy decision
      +
Priority Queue
```

At every step, select the **currently best available option**.

Examples:

- Minimum cost to connect things
- Schedule tasks
- Choose smallest/largest available item
- Select next cheapest operation

### Recognition

> "At every step, choose the minimum/maximum available..."

### Mental model

> **Greedy tells you what should be selected. Heap makes finding that choice efficient.**

---

# 6. Pattern — Scheduling / Resource Allocation

Example:

```text
Meeting:
[1, 4]
[2, 5]
[6, 8]
```

You may need:

- Minimum rooms
- Whether a resource is available
- Earliest finishing meeting
- Next available machine

Sort by start time.

Then maintain a:

```text
Min Heap of end times
```

For `[1,4]`:

```text
heap = [4]
```

For `[2,5]`, since:

```text
2 < 4
```

the existing room is occupied, so add another end time:

```text
heap = [4,5]
```

For `[6,8]`, the earliest end time is `4`.

Since:

```text
4 <= 6
```

that resource can be reused.

### Recognition

- Meeting rooms
- CPU scheduling
- Servers
- Machines
- Resources
- Jobs
- Tasks with start/end times

### Mental model

> **Need the earliest finishing/available resource → Min Heap.**

---

# 7. Pattern — Greedy + Heap for Scheduling Jobs

Suppose jobs have:

```text
deadline
profit
```

and you want maximum profit.

A common pattern is:

```text
Sort jobs by deadline
        ↓
Use heap to keep selected jobs
        ↓
Remove the least valuable job when capacity is exceeded
```

The exact heap type depends on what you need to discard.

### General principle

> **Sort by one dimension + heap to optimize another dimension.**

---

# 8. Pattern — Repeated Min/Max Extraction

If the problem repeatedly says:

```text
Take the smallest
modify it
put it back
repeat
```

or:

```text
Take the largest
modify it
put it back
repeat
```

think **Heap**.

Without a heap, repeatedly finding the minimum/maximum can cost:

```text
O(N)
```

per operation.

A heap makes extraction:

```text
O(log N)
```

per operation.

### Recognition

Words such as:

- repeatedly
- smallest
- largest
- minimum
- maximum
- extract
- remove and insert again

### Mental model

> **Repeated min/max → heap.**

---

# 9. Pattern — Custom Priority

The heap doesn't have to contain simple integers.

You can store:

```java
int[]
Pair
Node
Object
```

and define your own priority.

Example:

```java
PriorityQueue<int[]> pq =
    new PriorityQueue<>(
        (a, b) -> Integer.compare(a[1], b[1])
    );
```

Now priority is based on `a[1]`.

Common examples:

```text
{node, distance}
{value, index}
{frequency, value}
{start, end}
{cost, node}
```

### Recognition

When the problem says:

> "Choose the element with the smallest X."

you may need a custom comparator.

### Mental model

> **Heap priority = whatever property the problem says should be smallest/largest.**

---

# 10. Pattern — Heap + Frequency

Suppose:

```text
nums = [1,1,1,2,2,3]
```

Need:

```text
Top K frequent elements
```

First:

```text
HashMap
```

to calculate:

```text
1 → 3
2 → 2
3 → 1
```

Then use:

```text
Heap
```

to maintain the best K frequencies.

So:

```text
HashMap + Heap
```

### Recognition

- Most frequent
- Least frequent
- Top K frequency
- Frequency-based priority

### Mental model

> **HashMap calculates the score → Heap keeps the best K.**

---

# 11. Pattern — Heap + Other Data Structures

A heap often appears as a **supporting data structure**, not the complete solution.

Examples:

```text
HashMap + Heap
Sorting + Heap
Binary Search + Heap
Graph + Heap
Greedy + Heap
```

The heap's job is usually:

> **Efficiently maintain the current best candidate.**

Don't force a problem into "heap only."

---

# 12. Pattern — Dijkstra / Graph + Heap

Dijkstra uses:

```text
Graph
+
Min Heap / Priority Queue
```

The heap stores:

```text
(node, currentDistance)
```

and extracts the node with the smallest known distance.

So:

```text
Weighted shortest path
        ↓
Dijkstra
        ↓
Priority Queue
```

### Mental model

> **Graph determines the neighbors.**

> **Dijkstra determines the relaxation logic.**

> **Heap determines which node to process next.**

---

# 13. Pattern — Max Heap for "Best Available"

A max heap is useful when you repeatedly need:

- Largest
- Highest priority
- Highest profit
- Highest frequency
- Maximum score

Example:

```text
profits = 10, 50, 20, 30
```

Max heap:

```text
50
30
20
10
```

Every `poll()` gives the current best/largest candidate.

### Mental model

> **"Give me the best available candidate right now" → Max Heap.**

---

# 14. Pattern — Min Heap for "Earliest / Cheapest / Smallest"

A min heap is useful when you repeatedly need:

- Smallest
- Cheapest
- Earliest
- Minimum distance
- Earliest finishing
- Lowest cost

### Mental model

> **"Give me the cheapest/earliest/smallest available candidate right now" → Min Heap.**

---

# 15. Most Important Heap Decision Tree

```text
                  HEAP PROBLEM
                       │
                       ↓
             Do I repeatedly need
                min / max?
                       │
                      YES
                       │
          ┌────────────┼─────────────┐
          │            │             │
        Top K       Repeated      Scheduling
          │         min/max          │
          │            │             │
       size K        Heap       earliest end/
                                   available
```

Then:

```text
Need K largest?
    ↓
Min Heap of size K

Need K smallest?
    ↓
Max Heap of size K
```

```text
Need running median?
    ↓
Two Heaps
(Max Heap + Min Heap)
```

```text
Merge K sorted sources?
    ↓
Min Heap
(one candidate per source)
```

```text
Need earliest available resource?
    ↓
Min Heap
```

```text
Need best/largest available option?
    ↓
Max Heap
```

```text
Need weighted shortest path?
    ↓
Dijkstra + Min Heap
```

---

# 16. The Most Important Trick — Heap Size

A lot of heap problems are solved by controlling heap size.

### If you need K largest

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();

for (int x : nums) {
    pq.add(x);

    if (pq.size() > k) {
        pq.poll();
    }
}
```

At all times:

```text
heap size <= K
```

At the end:

```text
heap = K largest elements
```

The top is:

```text
Kth largest
```

### Why?

Because the min heap removes the smallest among the current candidates.

This is one of the **highest-value heap patterns for interviews**.

---

# 17. Heap Pattern Combinations

| Combination | Typical problem |
|---|---|
| Heap + HashMap | Top K frequent |
| Heap + Sorting | Scheduling |
| Heap + Two pointers | K closest / merging variants |
| Heap + Binary Search | Search + candidate selection |
| Heap + Graph | Dijkstra |
| Heap + Greedy | Job scheduling / minimum cost |
| Two Heaps | Running median |
| Heap + K-way merge | Merge K sorted lists |

Instead of asking:

> "Is this a heap problem?"

ask:

> **"What job is the heap performing inside the solution?"**

---

# 18. Heap Complexity You Should Know

For a binary heap with `N` elements:

| Operation | Complexity |
|---|---:|
| `peek()` | `O(1)` |
| `add()` | `O(log N)` |
| `poll()` | `O(log N)` |
| `remove()` arbitrary element | `O(N)` in Java `PriorityQueue` |
| Build heap / heapify | `O(N)` |
| Search arbitrary value | `O(N)` |

For a heap of size `K`:

```text
add / poll = O(log K)
```

Therefore Top K becomes:

```text
O(N log K)
```

instead of:

```text
O(N log N)
```

---

# 19. Common Heap Mistakes

## Mistake 1 — Wrong heap for Top K

For K largest:

```text
❌ Max Heap of size K
```

usually unnecessary.

Use:

```text
✅ Min Heap of size K
```

For K smallest:

```text
✅ Max Heap of size K
```

---

## Mistake 2 — Sorting when K is small

If:

```text
N = 1,000,000
K = 10
```

sorting everything is wasteful.

Think:

```text
Min Heap of size 10
```

---

## Mistake 3 — Forgetting Java's PriorityQueue default

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
```

is a **Min Heap**.

For Max Heap:

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>(Collections.reverseOrder());
```

---

## Mistake 4 — Wrong comparator

Prefer:

```java
Integer.compare(a, b)
```

instead of:

```java
a - b
```

because subtraction can overflow.

---

# 20. Important Interview Connections

## A. BFS vs DFS

### Prefer DFS when:

- You need reachability.
- You need to explore a complete component.
- The problem naturally follows recursive structure.
- You are detecting directed cycles using recursion-stack state.
- You are doing backtracking.

### Prefer BFS when:

- You need shortest path in an unweighted graph.
- You need minimum number of steps.
- You need level-by-level processing.
- You have multiple simultaneous sources.
- The problem is described in terms of time/rounds/layers.

### Core rule

> **Unweighted shortest path → BFS.**

---

## B. Weighted Graph Decision Rule

```text
No weights / equal cost
        ↓
       BFS

Weights only 0 or 1
        ↓
     0-1 BFS

Positive arbitrary weights
        ↓
    Dijkstra

Negative weights
        ↓
Bellman-Ford
```

> **Do not use Dijkstra just because a graph is weighted.**

First check whether the weights have a special structure such as `0/1`.

---

## C. Important Dijkstra Detail

Dijkstra works with **non-negative edge weights**.

The priority queue may contain multiple entries for the same node.

Example:

```text
node A first gets distance 10
later A gets distance 5
```

The queue may contain:

```text
(A, 10)
(A, 5)
```

When `(A, 10)` is removed, it is stale.

Use:

```java
if (d > dist[node]) {
    continue;
}
```

---

## D. Topological Sort — Critical Restriction

Topological sorting applies to a **directed acyclic graph (DAG)**.

Example:

```text
A → B → C
```

can have:

```text
A, B, C
```

But:

```text
A → B
B → C
C → A
```

contains a cycle, so no valid topological ordering exists.

### Interview shortcut

```text
Prerequisites
Dependencies
Build order
Course schedule
        ↓
Topological Sort
        ↓
Cycle?
```

---

## E. Topological Sort — DFS vs Kahn

### Kahn's Algorithm

Uses:

```text
in-degree + queue
```

Good when:

- Thinking in prerequisites.
- You want straightforward cycle detection.
- You want to process nodes whose prerequisites are satisfied.

Cycle test:

```java
if (order.size() != n) {
    // cycle exists
}
```

### DFS Topological Sort

Uses:

```text
DFS + postorder
```

A node is added after all dependencies have been explored.

Directed cycle detection uses:

```text
0 = unvisited
1 = visiting
2 = completed
```

---

## F. Cycle Detection — Don't Mix the Two Cases

### Undirected graph

Use:

```text
visited + parent
```

```java
if (visited[neighbor] && neighbor != parent) {
    return true;
}
```

The edge back to your parent is expected.

### Directed graph

Use:

```text
0 = unvisited
1 = currently visiting
2 = completely processed
```

If:

```java
state[neighbor] == 1
```

you found a cycle.

### Memory rule

> **Undirected → parent**

> **Directed → recursion stack**

---

## G. Connected Components vs Multi-Source BFS

### Connected Components

Question:

> "How many separate groups exist?"

```text
A — B       C — D       E
```

Run DFS/BFS from every unvisited node.

Result:

```text
3 components
```

### Multi-Source BFS

Question:

> "How quickly/how far does something spread from the nearest of multiple sources?"

```text
S . . S
. . . .
. . . .
```

Put both `S` cells into the queue at time `0`.

### Memory rule

> **Components → repeatedly start new traversals.**

> **Multi-source BFS → start one BFS with many sources.**

---

## H. Shortest Path Decision

When a problem says **"shortest path"**, do not immediately choose Dijkstra.

Ask:

```text
Equal edge cost?
    ↓
BFS

0/1 weights?
    ↓
0-1 BFS

Positive arbitrary weights?
    ↓
Dijkstra

Negative edges?
    ↓
Dijkstra is not valid
```

---

## I. Backtracking vs Normal DFS

### Normal DFS

Question:

> "Can I reach/explore this node?"

Visited state generally remains visited.

### Backtracking

Question:

> "What are all the possibilities?"

State belongs to the current path:

```java
choose();
backtrack();
undo();
```

### Memory rule

> **Traversal → remember what has already been explored.**

> **Backtracking → choose, explore, undo, try another.**

---

## J. State-Space Search

Ask:

> **Does the same location with different additional information represent a different state?**

Example:

```text
(row=2, col=3, keys=001)
(row=2, col=3, keys=101)
```

These are different states.

Therefore:

```java
visited[row][col][keyMask]
```

### General form

```text
State = Location + Information that affects future decisions
```

Examples:

```text
(node, fuel)
(node, keys)
(node, mask)
(node, remaining jumps)
(node, discounts used)
(row, col, obstacle-breaks-used)
```

### Memory rule

> **If two visits to the same location can have different futures, they must be different states.**

---

## K. Grid Problems Are Graph Problems in Disguise

A grid can be viewed as a graph.

Each valid cell is a node.

The movement directions define edges.

With 4-directional movement:

```text
        UP
        (-1,0)

LEFT   (r,c)   RIGHT
(0,-1)         (0,+1)

       DOWN
       (+1,0)
```

So:

```text
Grid
 ↓
Neighbor function
 ↓
Graph
 ↓
DFS / BFS / other traversal
```

This explains why graph patterns appear in:

- Number of Islands
- Rotting Oranges
- Flood Fill
- Word Search
- Shortest Path in a Grid
- Walls and Gates

---

## L. Grid Neighbor Function

### Four directions

```java
int[][] dirs = {
    {-1, 0},
    {1, 0},
    {0, -1},
    {0, 1}
};
```

### Eight directions

```java
int[][] dirs = {
    {-1, -1},
    {-1, 0},
    {-1, 1},
    {0, -1},
    {0, 1},
    {1, -1},
    {1, 0},
    {1, 1}
};
```

### Generic traversal

```java
for (int[] dir : dirs) {
    int nr = r + dir[0];
    int nc = c + dir[1];

    if (nr < 0 || nr >= rows ||
        nc < 0 || nc >= cols) {
        continue;
    }

    // process (nr, nc)
}
```

> **The traversal algorithm only needs a way to obtain neighbors.**

---

# 21. What You Should Memorize for Interviews

## Tier 1 — Must be automatic

1. **Min Heap / Max Heap basics**
2. **Top K**
3. **Kth largest / Kth smallest**
4. **Repeated min/max**
5. **Custom comparator**
6. **Heap size K**

## Tier 2 — Must recognize

7. **Two Heaps / Running Median**
8. **Merge K sorted lists/arrays**
9. **Scheduling / Resource allocation**
10. **HashMap + Heap**
11. **Greedy + Heap**

## Tier 3 — Important combinations

12. **Dijkstra + Heap**
13. **Heap + Binary Search**
14. **Heap + Sorting**
15. **Heap + other data structures**

---

# 22. Final Heap Mental Model

Don't memorize 15 unrelated heap problems.

Think:

```text
                       HEAP
                         │
             "Need best element repeatedly?"
                         │
            ┌────────────┴────────────┐
            │                         │
         MIN HEAP                  MAX HEAP
            │                         │
     smallest / earliest        largest / best
     cheapest / minimum         highest / maximum
            │                         │
       ┌────┴────┐               ┌────┴────┐
       │         │               │         │
      Top K    Scheduling       Top K     Greedy
```

Then special structures:

```text
K largest
    ↓
Min Heap size K

K smallest
    ↓
Max Heap size K

Running Median
    ↓
Max Heap + Min Heap

Merge K sorted
    ↓
Min Heap

Earliest available
    ↓
Min Heap

Best available
    ↓
Max Heap

Weighted shortest path
    ↓
Dijkstra + Min Heap
```

### One sentence to remember

> **Use a heap when you repeatedly need to efficiently access the current best candidate, where "best" means minimum or maximum according to the problem.**

That is the core of almost every heap interview pattern.
