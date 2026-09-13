# Graph Patterns — Interview Pattern Map

## 0. First understand the 3 dimensions

Every graph problem can be identified using:

**Graph Problem = Representation + Traversal/Driver + Visited/State**

### 1. Representation — "How do I find neighbors?"

| RepresentationHow to get neighbors |                           |
| ---------------------------------- | ------------------------- |
| Grid                               | 4/8 directions            |
| Adjacency List                     | `adj.get(node)`           |
| Edge List                          | Convert to adjacency list |
| Matrix                             | Scan row                  |
| Implicit Graph                     | Generate next states      |

The important point: **representation does not change the fundamental algorithm.** DFS/BFS remains DFS/BFS; only the way you generate neighbors changes. 

---

# Pattern 1 — Single Source DFS/BFS

### What is it?

You are given **one starting node** and need to explore everything reachable from it.

```
```

```
start
 ↓
A → B → C
    ↓
    D
```

Start from `A` and visit every node reachable from `A`.

### Recognition

Look for:

-  "Can I reach X from Y?" 
-  "Is there a path?" 
-  "Explore all nodes connected to X" 
-  "Find whether destination is reachable" 

### Template

**DFS**

```
```

```
dfs(start);
```

**BFS**

```
```

```
queue.add(start);
while (!queue.isEmpty()) {
    ...
}
```

### Mental model

> **One source → explore its entire reachable region.**

This is the fundamental graph pattern. 

---

# Pattern 2 — Connected Components / Multi-Component Traversal

### What changes?

Pattern 1 explores **one component**.

Here the graph may contain multiple disconnected components:

```
```

```
A — B       C — D       E
```

You need to explore **every component**.

### Approach

```
```

```
for (int i = 0; i < n; i++) {
    if (!visited[i]) {
        dfs(i);
        components++;
    }
}
```

Every time you encounter an unvisited node, you have discovered a **new component**.

### Recognition

Typical wording:

-  Number of islands 
-  Number of provinces 
-  Number of groups 
-  Number of connected components 
-  How many separate regions? 

### Mental model

> **Loop over all nodes + run Pattern 1 whenever you find an unvisited node.**

So this isn't really a new traversal algorithm. It's **single-source DFS/BFS + outer loop**. 

---

# Pattern 3 — Multi-Source BFS

This is one of the most important patterns.

### Problem setup

You don't have **one source**.

You have multiple sources:

```
```

```
S   .   .   S
.   .   .   .
.   .   .   .
```

You want to know the minimum distance/time from **the nearest source**.

### Key trick

Instead of doing:

```
```

```
BFS(S1)
BFS(S2)
BFS(S3)
...
```

put **all sources into the queue initially**.

```
```

```
Queue:

S1
S2
S3
```

Then BFS expands from all of them simultaneously.

### Why does this work?

Imagine fire spreading simultaneously from multiple locations.

At:

```
```

```
time = 0
```

all sources are burning.

At:

```
```

```
time = 1
```

their neighbors burn.

At:

```
```

```
time = 2
```

the next layer burns.

Therefore, the first time a cell is reached, it was reached from the **nearest source**.

### Recognition

Look for:

-  "nearest X" 
-  "minimum time for all..." 
-  "simultaneously" 
-  "multiple starting points" 
-  Rotting Oranges 
-  Walls and Gates 
-  Distance from nearest source 

### Mental model

> **Multiple sources → put all of them into BFS initially → normal level-order BFS.**

Your original notes correctly identify multi-source BFS as essentially a change in the **seeding step**, not a completely different BFS. 

---

# Pattern 4 — Level-Order BFS

### Why do we need levels?

Normal BFS tells us:

> "What nodes can I reach?"

Level-order BFS additionally tells us:

> **"How many edges/steps away is this node?"**

Example:

```
```

```
       A          level 0
      / \
     B   C        level 1
    / \
   D   E          level 2
```

Use:

```
```

```
int level = 0;

while (!queue.isEmpty()) {

    int size = queue.size();

    for (int i = 0; i < size; i++) {
        int node = queue.poll();

        // process current level
    }

    level++;
}
```

### Why `size = queue.size()`?

Suppose the queue currently contains:

```
```

```
[B, C]
```

These are **all level 1 nodes**.

While processing them, you add:

```
```

```
D, E, F
```

Those belong to **level 2**, not level 1.

Therefore freeze the current queue size first.

### Recognition

-  Minimum number of steps 
-  Shortest path in an unweighted graph 
-  Minimum time 
-  Distance from source 
-  Level/depth 
-  "After X minutes..." 

### Mental model

> **BFS + queue size = levels.**

And importantly:

**Multi-source BFS usually uses level-order BFS internally.** 

---

# Pattern 5 — 0-1 BFS

Now introduce **edge weights**.

Suppose edges have only:

```
```

```
0 or 1
```

Example:

```
```

```
A --0--> B
A --1--> C
```

Normal BFS doesn't work because edges don't all have equal cost.

But we don't need full Dijkstra.

### Trick

Use a `Deque`.

If edge weight is:

```
```

```
0 → addFirst()
1 → addLast()
```

Why?

A zero-cost edge does not increase the distance, so that node should be processed **before** nodes that require an additional cost of 1.

### Recognition

-  Edge costs are only `0` and `1` 
-  Minimum obstacle removals 
-  Free vs costly movement 
-  Minimum number of modifications 

### Mental model

> **Weights = 0/1 → Deque → 0 goes front, 1 goes back.**

This is basically the bridge between BFS and Dijkstra. 

---

# Pattern 6 — Dijkstra

Now edge weights can be:

```
```

```
1, 3, 7, 10, 100...
```

Normal BFS cannot guarantee the shortest path.

Why?

Because the node discovered first isn't necessarily the node with the smallest total distance.

### Example

```
```

```
A → B = 10
A → C = 2
C → B = 3
```

Initially:

```
```

```
B = 10
C = 2
```

We must process `C` first because its current distance is smaller.

Then:

```
```

```
A → C → B
2 + 3 = 5
```

So:

```
```

```
B = 5
```

### Data structure

Use:

```
```

```
PriorityQueue
```

ordered by:

```
```

```
current shortest distance
```

### Recognition

-  Weighted graph 
-  Positive edge weights 
-  Minimum cost 
-  Minimum travel time 
-  Network delay 
-  Cheapest route 

### Mental model

> **BFS chooses the next node by queue order.**
> **
> Dijkstra chooses the next node by smallest distance.**

Your document correctly places Dijkstra as the weighted counterpart to BFS. 

---

# Pattern 7 — Bidirectional BFS

Normal BFS:

```
```

```
Start
  ↓
  ↓
  ↓
Target
```

If branching factor is large, the number of explored nodes can explode.

Instead:

```
```

```
Start → → → ← ← ← Target
             ↑
           meet
```

Run BFS from **both ends**.

### Why is it faster?

If the answer is distance `d`:

Normal BFS roughly explores:

```
```

```
b^d
```

Bidirectional BFS explores roughly:

```
```

```
2 × b^(d/2)
```

which can be dramatically smaller.

### Recognition

Use when:

-  Start and target are both known 
-  Need shortest path 
-  Huge branching factor 
-  State transformation problem 

Classic example:

**Word Ladder**

```
```

```
hit → hot → dot → dog → cog
```

### Mental model

> **Known start + known destination + huge search space → search from both ends.**

---

# Pattern 8 — Topological Sort

This is fundamentally a **dependency problem**.

Example:

```
```

```
A → B
A → C
B → D
C → D
```

Meaning:

```
```

```
A must happen before B
A must happen before C
B/C must happen before D
```

We need an ordering satisfying all dependencies.

### Recognition

Words like:

-  prerequisite 
-  dependency 
-  ordering 
-  build order 
-  course schedule 
-  task scheduling 
-  compilation order 

### Two approaches

#### DFS

Explore dependencies first.

```
```

```
DFS(A)
 ↓
dependencies
 ↓
add A after processing
```

This gives a postorder-based solution.

#### Kahn's Algorithm

Use **in-degree**.

```
```

```
in-degree = number of prerequisites
```

Start with nodes having:

```
```

```
in-degree = 0
```

Then remove their outgoing edges.

### Cycle detection

If:

```
```

```
processed nodes < total nodes
```

then there is a cycle.

### Mental model

> **Dependencies → in-degree / DFS postorder → topological ordering.**

---

# Pattern 9 — Cycle Detection

There are **two different mental models** depending on the graph.

## Undirected Graph

Suppose:

```
```

```
A — B
|   |
C — D
```

While traversing, if you encounter an already visited node that is **not your parent**, you found a cycle.

```
```

```
visited neighbor != parent
        ↓
      cycle
```

### Why parent matters?

Suppose:

```
```

```
A — B
```

When you're at `B`, you naturally see `A` as visited.

That doesn't mean there's a cycle.

`A` is simply the node you came from.

---

## Directed Graph

Parent isn't enough.

Use 3 states:

```
```

```
0 = unvisited
1 = currently in recursion stack
2 = completely processed
```

If you find:

```
```

```
current → node with state 1
```

you found a back edge.

Therefore:

> **Directed cycle = edge to a node currently in the DFS path.**

### Mental model

```
```

```
Undirected → parent tracking

Directed → recursion-stack / 3-state tracking
```

---

# Pattern 10 — Backtracking DFS

This is where you need to change your mental model.

Normal DFS:

```
```

```
Visit node
 ↓
Explore
 ↓
Never need to undo
```

Backtracking:

```
```

```
Choose
 ↓
Explore
 ↓
UNDO
 ↓
Choose another
```

Example:

```
```

```
       A
      / \
     B   C
```

You explore:

```
```

```
A → B
```

Then **undo B** and explore:

```
```

```
A → C
```

### Why is visited different?

Normal graph traversal:

```
```

```
visited = globally visited
```

Backtracking:

```
```

```
used = valid only for current path
```

So after returning from one branch, you undo the state.

### Recognition

-  Find **all** paths 
-  Generate permutations 
-  Generate combinations 
-  N-Queens 
-  Sudoku 
-  Word Search 
-  Generate all arrangements 

### Mental model

> **Choose → explore → undo → choose next.**

The "undo" is the defining characteristic. 

---

# Pattern 11 — State-Space BFS/DFS

This is a very important advanced pattern.

Sometimes a location alone doesn't define a state.

For example:

```
```

```
(row, col)
```

is not enough.

Suppose you have keys:

```
```

```
(row, col, keysCollected)
```

Now:

```
```

```
(2,3,001)
```

and

```
```

```
(2,3,101)
```

are **different states**, even though you're standing at the same cell.

Therefore:

```
```

```
visited[row][col][keyMask]
```

instead of:

```
```

```
visited[row][col]
```

### Other examples

```
```

```
(position, fuel)
(position, remaining jumps)
(position, keys)
(position, mask)
(node, number of discounts used)
```

### Recognition

Ask yourself:

> **"Can I be at the same node twice and still have a different future?"**

If yes, the state needs additional information.

### Mental model

> **Same physical location ≠ same state.**

This is the key insight behind state-space search. 

---

# The Most Important Interview Decision Tree

Instead of memorizing 11 patterns independently, I would memorize this:

```
```

```
                GRAPH PROBLEM
                     │
          ┌──────────┴──────────┐
          │                     │
    What is the cost?      No/Equal cost
          │                     │
     ┌────┴────┐                │
     │         │                │
    0/1     Positive            │
     │         │                │
  0-1 BFS   Dijkstra             │
                               BFS/DFS
                                  │
                       ┌──────────┴──────────┐
                       │                     │
                  One source?          Multiple sources?
                       │                     │
                      DFS/BFS          Multi-source BFS
                       │
              ┌────────┴────────┐
              │                 │
        Need distance?      Just traversal?
              │                 │
        Level-order BFS    Normal DFS/BFS
```

Then separately ask:

```
```

```
Is graph disconnected?
        ↓
Connected Components

Is there dependency/order?
        ↓
Topological Sort

Need cycle detection?
        ↓
Cycle Detection

Need ALL possible solutions?
        ↓
Backtracking

Does a node require extra state?
        ↓
State-space BFS/DFS

Know both start + target?
        ↓
Bidirectional BFS
```

## What I would actually memorize for interviews

Don't memorize the code for 11 patterns equally.

### Tier 1 — Must be automatic

1. **Single-source DFS** 
2. **Single-source BFS** 
3. **Connected Components** 
4. **Level-order BFS** 
5. **Multi-source BFS** 
6. **Cycle Detection** 
7. **Topological Sort** 

### Tier 2 — Must recognize

8. **Dijkstra** 
9. **Backtracking** 
10. **State-space BFS/DFS** 

### Tier 3 — Specialized

11. **0-1 BFS** 
12. **Bidirectional BFS** 

The biggest improvement I'd make to your original notes is to **stop treating every pattern as an isolated algorithm**. Your own document already points toward the better model: many patterns are just modifications of the basic DFS/BFS driver. 

For interview prep, the goal should be:

> **Read problem → identify source(s) → identify cost → identify state → identify whether ordering/cycle is involved → choose the traversal.**

That is much more useful than memorizing 20 graph templates.

---

# 12. Important Corrections and Practical Additions

The patterns above are the core map. For interview preparation, there are a few additional distinctions worth keeping explicit.

## A. BFS vs DFS — choose based on the question

### Prefer DFS when:

- You only need reachability.
- You need to explore a complete component.
- The problem naturally follows recursive structure.
- You are detecting cycles using recursion-stack state.
- You are doing backtracking.

### Prefer BFS when:

- You need the shortest path in an unweighted graph.
- You need minimum number of steps.
- You need level-by-level processing.
- You have multiple simultaneous sources.
- The problem is naturally described in terms of time/rounds/layers.

### Core rule

> **Unweighted shortest path → BFS.**

DFS can find a path, but it does not naturally guarantee the shortest path.

---

# 13. The Weighted-Graph Decision Rule

Once edge weights appear, ask what values are possible.

```text
No weights / every edge has equal cost
        ↓
       BFS

Weights are only 0 or 1
        ↓
     0-1 BFS

Weights are positive and arbitrary
        ↓
    Dijkstra

Negative weights
        ↓
Bellman-Ford
```

For most SDE interviews, BFS, 0-1 BFS, and Dijkstra are the important progression to recognize.

> **Do not use Dijkstra just because a graph is weighted.**
>
> First check whether the weights have a special structure such as `0/1`.

---

# 14. Important Dijkstra Detail

Dijkstra works with **non-negative edge weights**.

A common implementation uses a priority queue and may insert the same node multiple times.

Example:

```text
node A first gets distance 10
later A gets distance 5
```

The priority queue may contain both:

```text
(A, 10)
(A, 5)
```

When `(A, 10)` is eventually removed, it is stale.

So use:

```java
if (d > dist[node]) {
    continue;
}
```

This is an important implementation detail to know for interviews.

---

# 15. Topological Sort — Critical Restriction

Topological sorting applies to a **directed acyclic graph (DAG)**.

The key word is **directed**.

Example:

```text
A → B → C
```

can have a valid topological ordering:

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

If the problem asks:

> "Can I complete all tasks given these prerequisites?"

Think:

```text
Directed dependencies
        ↓
Topological Sort
        ↓
Cycle?
```

---

# 16. Topological Sort: DFS vs Kahn

Both produce a topological ordering, but they are useful in slightly different situations.

## Kahn's Algorithm

Uses:

```text
in-degree + queue
```

Good when:

- You naturally think in prerequisites.
- You want straightforward cycle detection.
- You want to process nodes whose prerequisites are already satisfied.

Cycle test:

```java
if (order.size() != n) {
    // cycle exists
}
```

## DFS Topological Sort

Uses:

```text
DFS + postorder
```

A node is added to the result **after** all of its dependencies have been explored.

For cycle detection in directed graphs, use the 3-state approach:

```text
0 = unvisited
1 = visiting
2 = completed
```

---

# 17. Cycle Detection — Do Not Mix the Two Cases

This distinction should become automatic.

## Undirected graph

Use:

```text
visited + parent
```

Logic:

```java
if (visited[neighbor] && neighbor != parent) {
    return true;
}
```

Why?

Because the edge back to your parent is expected.

## Directed graph

Use:

```text
visited + recursion-stack state
```

Logic:

```text
0 = unvisited
1 = currently visiting
2 = completely processed
```

If you see:

```java
state[neighbor] == 1
```

you found a cycle.

### Memory rule

> **Undirected → parent**
>
> **Directed → recursion stack**

---

# 18. Connected Components vs Multi-Source BFS

These two are easy to confuse.

## Connected Components

Question:

> "How many separate groups exist?"

Example:

```text
A — B       C — D       E
```

Run DFS/BFS from every unvisited node.

Result:

```text
3 components
```

## Multi-Source BFS

Question:

> "How quickly/how far does something spread from the nearest of multiple sources?"

Example:

```text
S . . S
. . . .
. . . .
```

Put both `S` cells into the queue at time `0`.

### Memory rule

> **Components → repeatedly start new traversals.**
>
> **Multi-source BFS → start one BFS with many sources.**

---

# 19. Shortest Path — A Critical Distinction

When a problem says **"shortest path"**, do not immediately choose Dijkstra.

First ask:

### Are all edges equal cost?

```text
Yes
 ↓
BFS
```

### Are weights only 0/1?

```text
Yes
 ↓
0-1 BFS
```

### Are weights positive and arbitrary?

```text
Yes
 ↓
Dijkstra
```

### Are there negative edges?

```text
Yes
 ↓
Dijkstra is not valid
```

This decision alone prevents a lot of wrong graph solutions.

---

# 20. Backtracking vs Normal DFS

They both use recursion, but their purposes are different.

## Normal DFS

You are asking:

> "Can I reach/explore this node?"

Once a node is visited:

```text
visited[node] = true
```

it generally stays visited for the traversal.

## Backtracking

You are asking:

> "What are all the possibilities?"

Therefore the state belongs to the **current path**.

```java
choose();
backtrack();
undo();
```

Example:

```text
A
├── B
└── C
```

You can explore:

```text
A → B
```

then undo the choice and explore:

```text
A → C
```

### Memory rule

> **Traversal → remember what has already been explored.**
>
> **Backtracking → explore a choice, undo it, try another choice.**

---

# 21. State-Space Search — The Most Important Question

The most useful question is:

> **Does the same location with different additional information represent a different state?**

Suppose a grid has keys.

These are different:

```text
(row=2, col=3, keys=001)
(row=2, col=3, keys=101)
```

Even though both are at `(2,3)`.

Why?

Because their future possibilities are different.

Therefore:

```java
visited[row][col][keyMask]
```

is required.

### General form

Think of a state as:

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

# 22. Grid Problems Are Usually Graph Problems in Disguise

A grid:

```text
1 1 0
0 1 0
1 0 1
```

can be viewed as a graph.

Each valid cell is a node.

The directions define edges between cells.

For example, with 4-directional movement:

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

This is why the same graph patterns appear in:

- Number of Islands
- Rotting Oranges
- Flood Fill
- Word Search
- Shortest Path in a Grid
- Walls and Gates

The grid is simply another graph representation.

---

# 23. Grid Neighbor Function

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

The important idea is:

> **The traversal algorithm does not care whether the graph came from a grid or adjacency list. It only needs a way to obtain neighbors.**

---

# 24. The Full Interview Decision Process

When you read a graph problem, use this sequence.

## Step 1 — Identify the representation

Ask:

```text
Is it:
- adjacency list?
- edge list?
- matrix?
- grid?
- implicit state?
```

## Step 2 — Identify the objective

```text
Reachability?
Components?
Shortest path?
Dependency ordering?
Cycle?
All possible solutions?
```

## Step 3 — Identify the number of sources

```text
One source
    ↓
DFS/BFS

Multiple sources
    ↓
Multi-source BFS
```

## Step 4 — Identify edge cost

```text
Equal cost
    ↓
BFS

0/1
    ↓
0-1 BFS

Positive arbitrary
    ↓
Dijkstra
```

## Step 5 — Ask whether levels matter

If the question asks:

```text
minimum steps
minimum time
distance
after X rounds
```

think:

```text
BFS + levels
```

## Step 6 — Ask whether dependencies exist

```text
Prerequisites
Dependencies
Build order
Course schedule
```

Think:

```text
Topological Sort
```

## Step 7 — Ask whether a cycle matters

```text
Undirected
    ↓
Parent tracking

Directed
    ↓
3-state DFS / Kahn
```

## Step 8 — Ask whether all possibilities are required

```text
All paths
All combinations
All permutations
All arrangements
```

Think:

```text
Backtracking
```

## Step 9 — Ask whether location alone defines a state

```text
Same location + different condition
        ↓
State-space search
```

---

# 25. Final Graph Pattern Cheat Sheet

| Problem signal | Pattern |
|---|---|
| Explore from one node | Single-source DFS/BFS |
| Count disconnected groups | Connected Components |
| Multiple starting points spread simultaneously | Multi-source BFS |
| Minimum steps / distance in unweighted graph | Level-order BFS |
| Edge weights only 0/1 | 0-1 BFS |
| Positive arbitrary weights | Dijkstra |
| Known start + known target + huge search space | Bidirectional BFS |
| Prerequisites / dependencies / ordering | Topological Sort |
| Undirected cycle | DFS/BFS + parent |
| Directed cycle | DFS + 3 states / Kahn |
| Find all possibilities | Backtracking |
| Location + extra state | State-space BFS/DFS |

---

# 26. The Mental Model You Should Aim For

Do not memorize:

```text
11 unrelated graph algorithms
```

Instead memorize this hierarchy:

```text
                         GRAPH
                           │
               ┌───────────┴───────────┐
               │                       │
        What is the goal?         What is the cost?
               │                       │
       ┌───────┼────────┐       ┌──────┼───────┐
       │       │        │       │      │       │
   Explore   Shortest  Order   Equal  0/1   Positive
       │       │        │       │      │       │
    DFS/BFS   BFS      Topo     BFS   0-1   Dijkstra

```

Then add special conditions:

```text
Multiple sources?
        ↓
Multi-source BFS

Disconnected?
        ↓
Connected Components

Cycle?
        ↓
Cycle Detection

All possibilities?
        ↓
Backtracking

Extra state?
        ↓
State-space search

Known start + target?
        ↓
Bidirectional BFS
```

The ultimate interview skill is:

> **Problem statement → identify representation → identify objective → identify source count → identify edge cost → identify state → choose pattern.**

Once this becomes automatic, graph problems stop looking like dozens of unrelated questions and start looking like combinations of a small number of patterns.
