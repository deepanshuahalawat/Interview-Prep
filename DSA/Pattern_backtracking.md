# Backtracking — Interview Pattern Guide

## 0. What is Backtracking?

Backtracking is **DFS over a decision tree**.

Core idea:

```text
Choose
  ↓
Explore recursively
  ↓
Undo the choice
```

Generic template:

```java
void backtrack(state) {
    if (isComplete(state)) {
        addAnswer();
        return;
    }

    for (choice : choices) {
        if (!isValid(choice)) continue;

        makeChoice(choice);
        backtrack(newState);
        undoChoice(choice);
    }
}
```

Always ask:
1. What is my current state?
2. What choices can I make?
3. What makes a choice invalid?
4. When is the solution complete?
5. What must I undo?
6. Can I prune impossible branches?

---

# 1. Subsets / Include-Exclude

## Recognition

Use this when:
- You need all subsets/subsequences.
- Every element can be selected or skipped.
- Order does not matter.

Example `[1,2,3]`:

```text
[]
[1]
[2]
[3]
[1,2]
[1,3]
[2,3]
[1,2,3]
```

## How to Approach

Each element gives two choices:

```text
             element
             /     \
          take      skip
```

Template:

```java
void backtrack(int index) {
    if (index == nums.length) {
        ans.add(new ArrayList<>(path));
        return;
    }

    path.add(nums[index]);
    backtrack(index + 1);
    path.remove(path.size() - 1);

    backtrack(index + 1);
}
```

Alternative:

```java
void backtrack(int start) {
    ans.add(new ArrayList<>(path));

    for (int i = start; i < nums.length; i++) {
        path.add(nums[i]);
        backtrack(i + 1);
        path.remove(path.size() - 1);
    }
}
```

## Complexity

```text
Time:  O(2^N) approximately, excluding output-copying details
Space: O(N) recursion/path
```

## Practice

- LeetCode 78 — Subsets
- LeetCode 90 — Subsets II
    >In the Subset II problem, handling the excluding branch is crucial for avoiding duplicate subsets when the input array contains repeating elements. To ensure only unique combinations are generated, follow these steps:

    1. Sort the Array: Before starting recursion, you must sort the input array. This groups identical elements together, which is essential for the logic to function correctly (33:30).
    Identify Duplicates: When you decide to exclude a specific element at index i, you must skip all subsequent identical elements to prevent re-creating the same subset branch.
    2. Skip Logic: Before making the recursive call for the exclusion path, create a loop that advances the index as long as the next element is the same as the current one:
        Initialize a new pointer at i + 1.
        While the pointer is within bounds and nums[pointer] == nums[pointer - 1], increment the pointer.
        Pass this updated pointer to the next recursive call (34:57-36:50).

    3. This logic effectively forces the algorithm to bypass redundant branches that would otherwise lead to duplicate results (32:10).
- **LeetCode 491 — Non-decreasing Subsequences**

  > At each position, we make up to two recursive calls:

  ### 1. Include current element (if valid)

  ```java
  if (nums[u] >= last) {
      t.add(nums[u]);
      dfs(u + 1, nums[u], t);
      t.remove(t.size() - 1); // backtrack
  }
  ```

  We can only include `nums[u]` if it maintains the non-decreasing property
  (`nums[u] >= last`). After exploring this path, we backtrack by removing the element.

  ### 2. Skip current element (with duplicate avoidance)

  ```java
  if (nums[u] != last) {
      dfs(u + 1, last, t);
  }
  ```

  We skip the current element, but only if it's different from `last`.
  This crucial check prevents generating duplicate subsequences when we have repeated values.

---

# 2. Combinations

## Recognition

Use this when:
- You choose `K` elements from `N`.
- Order does not matter.
- `[1,2]` and `[2,1]` are the same answer.

Example `n=4, k=2`:

```text
[1,2]
[1,3]
[1,4]
[2,3]
[2,4]
[3,4]
```

## How to Approach

Use a `start` index:

```java
void backtrack(int start) {
    if (path.size() == k) {
        ans.add(new ArrayList<>(path));
        return;
    }

    for (int i = start; i <= n; i++) {
        path.add(i);
        backtrack(i + 1);
        path.remove(path.size() - 1);
    }
}
```

`i + 1` prevents generating both `[1,2]` and `[2,1]`.

## Practice

- LeetCode 77 — Combinations
- LeetCode 216 — Combination Sum III

---

# 3. Combination Sum

## Recognition

Look for:
- Combinations whose sum equals a target.
- Order does not matter.
- The problem specifies whether elements can be reused.

Example:

```text
candidates = [2,3,6,7]
target = 7

[2,2,3]
[7]
```

## How to Approach

### Reuse allowed

Use:

```java
backtrack(i, target - nums[i]);
```

because the same candidate can be chosen again.

```java
void backtrack(int start, int target) {
    if (target == 0) {
        ans.add(new ArrayList<>(path));
        return;
    }

    for (int i = start; i < nums.length; i++) {
        if (nums[i] > target) continue;

        path.add(nums[i]);
        backtrack(i, target - nums[i]);
        path.remove(path.size() - 1);
    }
}
```

### No reuse

Use:

```java
backtrack(i + 1, target - nums[i]);
```

Key rule:

```text
Reuse allowed    → backtrack(i)
No reuse         → backtrack(i + 1)
```

## Practice

- LeetCode 39 — Combination Sum
```text
Duplicate handling is a must here also
```
```java
void explore(int[] nums,int start, int target, ArrayList<Integer> oneAns, List<List<Integer>> ans){
        if(target <0){return;}
        if(target == 0){
            ans.add(new ArrayList<>(oneAns));
            return;
        }
        for(int i=start; i< nums.length; i++){
            oneAns.add(nums[i]);
            //satrt choosing next 
            explore(nums,i, target-nums[i], oneAns, ans);
            oneAns.removeLast();
        }
        
    }
```
- LeetCode 40 — Combination Sum II
```text
We have to take care of duplicates in combination sumII like below
```

``` java
    exp(int start, target){
        if(target == 0){return ans;}
        if(target <0){return;}

        for(int i= start; i<n; i++ ){
            //skip duplicates, or we can use hashset sfter sorting evrry set before inserting into ans list
            if(i>start && nums[i-1] == nums[i]){
                continue;
            }

            oneSet.add(nums[i]);
            exp(i+1, target-nums[i]);// start is i+1 because we can not include 1 element multiple times
            oneSet.removeLast();//backtrack

        }
    }
```
- LeetCode 216 — Combination Sum III

---

# 4. Permutations

## Recognition

Use this when:
- Order matters.
- `[1,2]` and `[2,1]` are different.
- You need every possible ordering.

Example `[1,2,3]` has `3! = 6` permutations.

## How to Approach

Use `used[]`:

```java
void backtrack() {
    if (path.size() == nums.length) {
        ans.add(new ArrayList<>(path));
        return;
    }

    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;

        used[i] = true;
        path.add(nums[i]);

        backtrack();

        path.remove(path.size() - 1);
        used[i] = false;
    }
}
```

Key state:

```text
used[i]
```

## Complexity

```text
Time:  O(N * N!) approximately
Space: O(N)
```

## Practice

- LeetCode 46 — Permutations
- LeetCode 47 — Permutations II

---

# 5. Handling Duplicates

## Recognition

Use this when:
- Input contains duplicate values.
- You need unique subsets/combinations/permutations.
- Choosing identical values from the same recursion level would duplicate answers.

## How to Approach

### Step 1 — Sort

```java
Arrays.sort(nums);
```

### Step 2 — Skip duplicates at the same recursion level

```java
if (i > start && nums[i] == nums[i - 1]) {
    continue;
}
```

Remember:

```text
Same value + same recursion level → skip
Same value + deeper recursion   → may be valid
```

For example `[2,2]` can still be a valid answer.

## Practice

- LeetCode 90 — Subsets II
- LeetCode 40 — Combination Sum II
- LeetCode 47 — Permutations II

---

# 6. String Partitioning

## Recognition

Use this when:
- A string must be split into pieces.
- Every piece must satisfy some condition.
- You need all valid partitions.

Example `"aab"` with palindrome pieces:

```text
["a","a","b"]
["aa","b"]
```

## How to Approach

At `start`, try every possible `end`.

```java
void backtrack(int start) {
    if (start == s.length()) {
        ans.add(new ArrayList<>(path));
        return;
    }

    for (int end = start; end < s.length(); end++) {
        if (!isValid(start, end)) continue;

        path.add(s.substring(start, end + 1));
        backtrack(end + 1);
        path.remove(path.size() - 1);
    }
}
```

Mental model:

```text
Choose where the next substring ends
        ↓
Validate substring
        ↓
Recurse from end + 1
        ↓
Undo
```

## Practice

- LeetCode 131 — Palindrome Partitioning
- LeetCode 93 — Restore IP Addresses
- LeetCode 132 — Palindrome Partitioning II (advanced DP connection)

---

# 7. Grid / Matrix Backtracking

## Recognition

Look for:
- Board/matrix.
- Path searching.
- Movement in directions.
- Cells cannot be reused.
- Multiple possible paths.

Typical directions:

```text
up
down
left
right
```

## How to Approach

1. Check bounds.
2. Check whether the cell is valid.
3. Mark visited.
4. Explore neighbors.
5. Undo visited state.

```java
void dfs(int r, int c) {
    if (invalid(r, c)) return;

    visited[r][c] = true;

    for (int[] dir : directions) {
        int nr = r + dir[0];
        int nc = c + dir[1];

        dfs(nr, nc);
    }

    visited[r][c] = false;
}
```

The undo:

```java
visited[r][c] = false;
```

allows another path to use the cell.

## Practice

- LeetCode 79 — Word Search
- LeetCode 980 — Unique Paths III
- LeetCode 212 — Word Search II

---

# 8. Trie + Backtracking

## Recognition

Use this when:
- You have many dictionary words.
- You search for words in a board.
- Prefixes can eliminate entire branches.

## How to Approach

Instead of:

```text
For every word:
    search entire board
```

use:

```text
Dictionary
    ↓
Trie
    ↓
Grid DFS
    ↓
Stop if prefix does not exist
```

This is:

```text
Trie + Grid DFS + Backtracking + Prefix Pruning
```

If current path is `"qzx"` and no dictionary word starts with `"qzx"`, stop immediately.

## Practice

- LeetCode 212 — Word Search II
- LeetCode 79 — Word Search

---

# 9. Constraint Satisfaction

## Recognition

Use this when:
- You place/fill values.
- Each choice must satisfy constraints.
- A bad partial choice can make the whole solution impossible.

Examples:

```text
N-Queens
Sudoku
Graph Coloring
```

## How to Approach

```text
Choose position
      ↓
Try possible values
      ↓
Check constraints
      ↓
Valid?
  /     \
NO       YES
 |        |
skip     choose
          ↓
       recurse
          ↓
         undo
```

Generic template:

```java
boolean backtrack(position) {
    if (isComplete(position)) return true;

    for (choice : choices) {
        if (!isValid(choice)) continue;

        makeChoice(choice);

        if (backtrack(nextPosition)) {
            return true;
        }

        undoChoice(choice);
    }

    return false;
}
```

## Practice

- LeetCode 51 — N-Queens
- LeetCode 52 — N-Queens II
- LeetCode 37 — Sudoku Solver

---

# 10. N-Queens

## Recognition

Place `N` queens so that no two share:
- a row
- a column
- a diagonal

## How to Approach

Place one queen per row.

For every row:

```text
Try every column
      ↓
Check column + diagonals
      ↓
Place queen
      ↓
Recurse
      ↓
Remove queen
```

Efficient tracking:

```java
boolean[] col;
boolean[] diag1;
boolean[] diag2;
```

For `(row, col)`:

```text
main diagonal: row - col
anti diagonal: row + col
```

## Practice

- LeetCode 51 — N-Queens
- LeetCode 52 — N-Queens II

---

# 11. Sudoku / Constraint Filling

## Recognition

Look for:
- Empty positions.
- Limited possible values.
- Multiple constraints.
- Need to find a complete valid configuration.

## How to Approach

For each empty cell:

```text
Try 1..9
  ↓
Check row
Check column
Check 3x3 box
  ↓
Place
  ↓
Recurse
  ↓
Undo if necessary
```

```java
boolean solve() {
    find an empty cell;

    for (int num = 1; num <= 9; num++) {
        if (!isValid(num)) continue;

        board[row][col] = num;

        if (solve()) return true;

        board[row][col] = '.';
    }

    return false;
}
```

## Practice

- LeetCode 37 — Sudoku Solver

---

# 12. Generate All Valid Structures

## Recognition

Use this when:
- You generate strings/configurations.
- Each position has multiple legal choices.
- Partial solutions have constraints.

Examples:

```text
Generate Parentheses
Letter Combinations
Restore IP Addresses
Expression Add Operators
```

## How to Approach

```text
Current position
      ↓
Try every legal choice
      ↓
Check constraints
      ↓
Choose
      ↓
Recurse
      ↓
Undo
```

### Generate Parentheses

For `n=3`:

```text
((()))
(()())
(())()
()(())
()()()
```

Constraints:

```text
open < n
close < open
```

## Practice

- LeetCode 22 — Generate Parentheses
- LeetCode 17 — Letter Combinations of a Phone Number
- LeetCode 93 — Restore IP Addresses
- LeetCode 282 — Expression Add Operators (advanced)

---

# 13. Knapsack — Backtracking Decision Tree → DP

## Important

**Knapsack is primarily a Dynamic Programming topic, not a pure Backtracking pattern.**

However, its recurrence comes directly from the same **choose / skip** decision tree used in Backtracking.

This makes it an important bridge between the two topics.

## Recognition

Look for:
- Items.
- Weight/cost.
- Value/profit.
- Capacity/budget.
- Maximize value, count ways, or determine possibility.

Example:

```text
weights = [1,3,4]
values  = [15,20,30]
capacity = 4
```

For every item:

```text
Take
OR
Skip
```

## How to Approach

### Step 1 — Build the decision tree

For item `i` and capacity `cap`:

```text
             item i
             /     \
          take     skip
```

### Step 2 — Define the state

```text
dp[i][cap]
```

means:

> Best answer using items from index `i` onward with capacity `cap`.

### Step 3 — Recurrence

Skip:

```text
dp[i + 1][cap]
```

Take:

```text
value[i] + dp[i + 1][cap - weight[i]]
```

Therefore:

```text
dp[i][cap] =
    max(
        dp[i + 1][cap],
        value[i] + dp[i + 1][cap - weight[i]]
    )
```

when the item fits.

## Key Connection

Naive Backtracking:

```text
Explore take
Explore skip
```

Then you notice:

```text
Same (i, capacity) state
appears repeatedly
```

So:

```text
Backtracking
    ↓
Repeated states
    ↓
Memoization
    ↓
DP
```

This is the core reason Knapsack belongs in your Backtracking-to-DP transition.

## Variants

### 0/1 Knapsack

Each item can be used once.

```text
take → i + 1
skip → i + 1
```

### Unbounded Knapsack

An item can be reused.

Conceptually:

```text
take → same item
skip → next item
```

### Bounded Knapsack

Each item has a limited number of copies.

## Practice

- Classic 0/1 Knapsack
- LeetCode 416 — Partition Equal Subset Sum
- LeetCode 494 — Target Sum
- LeetCode 1049 — Last Stone Weight II
- LeetCode 474 — Ones and Zeroes
- LeetCode 518 — Coin Change II
- LeetCode 322 — Coin Change

---

# 14. Pruning

## What is pruning?

Stop exploring a branch as soon as you know it cannot produce a valid answer.

Example:

```text
target = 7
current sum = 10
```

If all numbers are positive:

```text
current sum > target
```

means the branch cannot work.

```java
if (sum > target) return;
```

## Common Pruning

### Target exceeded

```text
currentSum > target
```

### Capacity exceeded

```text
currentWeight > capacity
```

### Invalid constraint

```text
queen attacks another queen
```

### Impossible prefix

```text
prefix does not exist in Trie
```

### Duplicate choice

```text
same value at same recursion level
```

Good pruning is often the difference between a practical and impractical Backtracking solution.

---

# 15. `start` vs `used[]`

## Use `start`

When order does not matter:

```text
Subsets
Combinations
Combination Sum
```

Example:

```java
backtrack(i + 1);
```

## Use `used[]`

When order matters:

```text
Permutations
```

Example:

```java
if (used[i]) continue;

used[i] = true;
...
used[i] = false;
```

Mental shortcut:

```text
Order doesn't matter → start
Order matters         → used[]
```

---

# 16. Reuse vs No Reuse

Another critical distinction:

```text
Reuse allowed
    ↓
backtrack(i)

No reuse
    ↓
backtrack(i + 1)
```

Examples:

```text
Combination Sum
    → reuse

Combination Sum II
    → no reuse
```

---

# 17. Backtracking vs DFS

They are closely related.

## DFS

Usually:

```text
Explore an existing graph/tree
```

Examples:

```text
Tree traversal
Graph traversal
```

## Backtracking

Usually:

```text
Make decision
    ↓
DFS
    ↓
Undo decision
```

Examples:

```text
N-Queens
Permutations
Subsets
Sudoku
```

Mental model:

> Backtracking is DFS over a **decision tree**, where state is modified and restored while exploring alternatives.

---

# 18. Backtracking vs DP

## Backtracking

Usually:

```text
Generate all possible solutions
```

Examples:

```text
All subsets
All permutations
All combinations
All valid configurations
```

## DP

Usually:

```text
Can it be done?
How many ways?
Minimum?
Maximum?
```

Examples:

```text
Knapsack
Target Sum
Coin Change
Minimum Cost
```

Important connection:

```text
Backtracking decision tree
        ↓
Repeated states
        ↓
Memoization
        ↓
DP
```

---

# 19. Backtracking vs Greedy

Backtracking:

```text
Try possibilities
    ↓
Explore
    ↓
Undo
```

Greedy:

```text
Choose best-looking option now
    ↓
Never reconsider
```

Do not assume greedy works simply because a locally good choice exists.

---

# 20. Pattern Recognition Cheat Sheet

| Problem signal | Pattern |
|---|---|
| All subsets | Include / Exclude |
| Choose K elements | Combinations |
| Target + combinations | Combination Sum |
| All orderings | Permutations |
| Duplicate input | Sort + skip duplicates |
| Split string into valid pieces | String Partitioning |
| Board + path | Grid Backtracking |
| Many words + board | Trie + Backtracking |
| Place values under constraints | Constraint Backtracking |
| Queens | N-Queens |
| Fill valid configuration | Sudoku |
| Generate valid strings | Constraint Backtracking |
| Items + capacity + value | Knapsack / DP |

---

# 21. Backtracking Decision Tree

```text
                         BACKTRACKING
                              |
                  Are you generating answers?
                              |
                             YES
                              |
              +---------------+---------------+
              |               |               |
           Subsets       Permutations    Combinations
              |               |               |
          take/skip          used[]         start
                                              |
                                     +--------+--------+
                                     |                 |
                                  reuse             no reuse
                                     |                 |
                                backtrack(i)      backtrack(i+1)
```

Then:

```text
String
  |
  +-- Partition?
       |
       +-- choose next cut
       +-- validate substring
```

```text
Grid
  |
  +-- choose direction
  +-- visited
  +-- DFS
  +-- undo
```

```text
Configuration
  |
  +-- choose value
  +-- validate constraints
  +-- recurse
  +-- undo
```

```text
Dictionary + Grid
  |
  +-- Trie
  +-- DFS
  +-- prefix pruning
```

```text
Items + Capacity + Value
  |
  +-- Take / Skip
  +-- repeated states
  +-- Memoization / DP
  |
  +-- Knapsack family
```

---

# 22. Recommended Learning Order

Follow this order:

```text
1. Letter Combinations
       ↓
2. Subsets
       ↓
3. Combinations
       ↓
4. Combination Sum
       ↓
5. Permutations
       ↓
6. Subsets II
       ↓
7. Combination Sum II
       ↓
8. Generate Parentheses
       ↓
9. Palindrome Partitioning
       ↓
10. Word Search
       ↓
11. N-Queens
       ↓
12. Sudoku
       ↓
13. Word Search II
       ↓
14. Knapsack / 0-1 Knapsack
       ↓
15. Knapsack variants
```

Progression:

```text
Basic recursion
    ↓
Choice tree
    ↓
start index
    ↓
reuse
    ↓
used[]
    ↓
duplicates
    ↓
constraints
    ↓
grid
    ↓
pruning
    ↓
Trie + Backtracking
    ↓
Choose/Skip → DP/Knapsack
```

---

# 23. High-Value Practice Set

## Basic Choice Tree

1. LeetCode 17 — Letter Combinations of a Phone Number
2. LeetCode 78 — Subsets
3. LeetCode 77 — Combinations

## Combination Patterns

4. LeetCode 39 — Combination Sum
5. LeetCode 40 — Combination Sum II
6. LeetCode 216 — Combination Sum III

## Permutations

7. LeetCode 46 — Permutations
8. LeetCode 47 — Permutations II

## Constraint / String

9. LeetCode 22 — Generate Parentheses
10. LeetCode 131 — Palindrome Partitioning

## Grid

11. LeetCode 79 — Word Search
12. LeetCode 980 — Unique Paths III

## Advanced Constraint

13. LeetCode 51 — N-Queens
14. LeetCode 37 — Sudoku Solver

## Trie + Backtracking

15. LeetCode 212 — Word Search II

## Backtracking → DP / Knapsack

16. Classic 0/1 Knapsack
17. LeetCode 416 — Partition Equal Subset Sum
18. LeetCode 494 — Target Sum
19. LeetCode 518 — Coin Change II
20. LeetCode 322 — Coin Change

---

# 24. Final Interview Checklist

Before moving on, you should be able to:

```text
✓ Implement choose → explore → undo
✓ Identify a decision tree
✓ Know when to use start
✓ Know when to use used[]
✓ Know when to use i vs i + 1
✓ Handle duplicates
✓ Sort before duplicate skipping
✓ Prune impossible branches
✓ Backtrack correctly on a grid
✓ Solve constraint-placement problems
✓ Combine Trie + Backtracking
✓ Explain Backtracking vs DFS
✓ Explain Backtracking vs DP
✓ Derive Knapsack from take/skip
✓ Recognize repeated states and convert to DP
```

---

# 25. Final Mental Model

When you see a new problem:

```text
1. What is my current state?
2. What choices can I make?
3. Can I make this choice?
4. What state changes after choosing?
5. What recursive state comes next?
6. What must I undo?
7. When is the solution complete?
8. Can I prune this branch?
```

Then classify it:

```text
All subsets?
    → Take / Skip

Order matters?
    → Permutations + used[]

Order doesn't matter?
    → Combinations + start

Target + combinations?
    → Combination Sum

Duplicates?
    → Sort + skip same value at same level

String partition?
    → Choose next cut

Grid?
    → Directions + visited + undo

Placement/configuration?
    → Constraint checking

Dictionary + grid?
    → Trie + Backtracking

Items + capacity/value?
    → Take/Skip → Knapsack DP
```

## One-line rule

> **Backtracking = explore a decision tree by choosing an option, recursively exploring it, undoing the choice, and pruning branches that cannot lead to a valid solution.**
