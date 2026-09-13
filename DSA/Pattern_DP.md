# Dynamic Programming — SDE2 Interview Pattern Guide

## 1. What is Dynamic Programming?

Dynamic Programming (DP) solves problems by breaking them into smaller states, solving each state once, storing the result, and reusing it.

### Two approaches
- **Top-down / Memoization:** recursion + cache
- **Bottom-up / Tabulation:** solve smaller states first and build upward

The most important DP skill is **state design**, not memorizing formulas.

---

## 2. How to Identify DP

### Strong signals
Look for:
- minimum / maximum
- best
- longest / shortest
- number of ways / how many
- can we / possible
- optimal
- minimum cost / maximum profit

### Decision signals
DP is especially likely when you repeatedly make choices:
- take / skip
- buy / sell
- match / don't match
- choose / don't choose
- move right / down
- use / don't use
- partition / don't partition

### Two core properties

**Overlapping subproblems:** the same smaller state is reached repeatedly.

**Optimal substructure:** the optimal larger answer can be built from optimal smaller answers.

> **Overlapping subproblems + optimal substructure = strong DP candidate.**

---

## 3. DP Recognition Cheat Sheet

| Problem shape | Likely pattern |
|---|---|
| `i` / previous positions | 1D DP |
| Grid + min/max/count | Grid DP |
| Take/skip + capacity | 0/1 Knapsack |
| Target + ways/existence | Knapsack / Subset Sum |
| Reusable items + target | Unbounded Knapsack |
| Two strings | String / 2D DP |
| Subsequences | LCS / LIS |
| Buy/sell | Stock State DP |
| Cannot choose adjacent | House Robber / State DP |
| `[l,r]` interval | Interval DP |
| Palindrome | Palindrome DP |
| Tree + choose/not choose | Tree DP |
| DAG + paths | Graph DP |
| Small N + visited subset | Bitmask DP |
| Digit restrictions + range | Digit DP |
| Dictionary + segmentation | Word Break / String DP |

---

## 4. Most Important Habit: Define the State

Before coding, complete:

> `dp[...]` means __________.

Examples:

```text
dp[i] = answer for first i elements
dp[i] = maximum money from houses 0...i
dp[i][j] = LCS of prefixes of lengths i and j
dp[i][capacity] = best value using items from i onward
dp[row][col] = best answer reaching this cell
dp[day][holding][transactions] = best stock profit for this state
```

### State Design Rule

A DP state must contain **all information necessary to describe the remaining problem**.

If two recursive calls have exactly the same state, their answers should be identical. That repeated state is a candidate for memoization.

---

# 5. Pattern — 1D Linear DP

### Recognition
- Array/string is linear.
- Answer at `i` depends on previous positions.
- Question asks min/max/count/best.

### State

```text
dp[i]
```

### Example — Climbing Stairs

```text
dp[i] = dp[i-1] + dp[i-2]
```

### Famous Problems
- LeetCode 70 — Climbing Stairs
- 746 — Min Cost Climbing Stairs
- 198 — House Robber
- 213 — House Robber II
- 91 — Decode Ways

---

# 6. Pattern — Take / Skip DP

One of the most important DP patterns.

It is often the **DP version of a backtracking decision tree**.

### Recognition

Think:

```text
take
skip
```

Typical recursive state:

```text
solve(i, state)
```

Then:

```text
skip = solve(i + 1, state)
take = ...
answer = max/min/count depending on the problem
```

### Famous Problems
- 0/1 Knapsack
- 198 — House Robber
- 213 — House Robber II
- 416 — Partition Equal Subset Sum
- 494 — Target Sum
- 1049 — Last Stone Weight II
- 474 — Ones and Zeroes

---

# 7. Pattern — 0/1 Knapsack

### Recognition
- Each item can be used at most once.
- Capacity/weight/limit exists.
- Maximize value.
- Take or skip.

### State

```text
dp[i][capacity]
```

### Transition

```text
skip = dp[i+1][capacity]

take = value[i] + dp[i+1][capacity-weight[i]]

dp[i][capacity] = max(skip, take)
```

### Famous Problems
- Classic 0/1 Knapsack
- 416 — Partition Equal Subset Sum
- 474 — Ones and Zeroes
- 1049 — Last Stone Weight II

### Connection to Backtracking

```text
Backtracking:
             take
           /
choice ----
                        skip

DP = same decision tree + repeated states + caching
```

---

# 8. Pattern — Unbounded Knapsack / Reuse DP

### Recognition
The important phrase is:

> **An item can be used multiple times.**

Typical problems:
- coin change
- unlimited items
- minimum number of items
- number of combinations

### State

```text
dp[target]
```

or:

```text
dp[i][target]
```

### Example — Coin Change

```text
dp[amount] = min(dp[amount-coin] + 1)
```

### 0/1 vs Unbounded

| 0/1 Knapsack | Unbounded |
|---|---|
| Item used once | Item can be reused |
| Move to next item after take | Same item may be used again |
| Subset selection | Repeated selection |

### Famous Problems
- 322 — Coin Change
- 518 — Coin Change II
- 279 — Perfect Squares
- Classic Unbounded Knapsack
- Rod Cutting

---

# 9. Pattern — Subset Sum / Target Sum

### Recognition
Look for:
- subset
- target
- sum
- partition
- possible
- can we form
- achieve target
- number of ways

### State

```text
dp[i][sum]
```

or optimized:

```text
dp[sum]
```

### Variants
- **Existence:** can target be formed?
- **Optimization:** best value for target
- **Counting:** number of ways to form target

### Famous Problems
- 416 — Partition Equal Subset Sum
- 494 — Target Sum
- 474 — Ones and Zeroes
- 698 — Partition to K Equal Sum Subsets
- Classic Subset Sum

---

# 10. Pattern — Grid DP

### Recognition
- Matrix/grid
- restricted movement
- count paths
- min/max path
- obstacles

### State

```text
dp[i][j]
```

### Example

```text
dp[i][j] = dp[i-1][j] + dp[i][j-1]
```

### Famous Problems
- 62 — Unique Paths
- 63 — Unique Paths II
- 64 — Minimum Path Sum
- 120 — Triangle
- 931 — Minimum Falling Path Sum
- 221 — Maximal Square

---

# 11. Pattern — Two-String / String Matching DP

### Recognition
- two strings
- compare characters
- subsequence
- edit/transform
- matching

### State

```text
dp[i][j]
```

Usually means the answer for prefixes of the two strings.

### LCS

If characters match:

```text
dp[i][j] = 1 + dp[i-1][j-1]
```

Otherwise:

```text
dp[i][j] = max(dp[i-1][j], dp[i][j-1])
```

### Famous Problems
- 1143 — Longest Common Subsequence
- 72 — Edit Distance
- 115 — Distinct Subsequences
- 97 — Interleaving String
- 10 — Regular Expression Matching
- 44 — Wildcard Matching

> **Longest Common Subsequence is a DP/string-DP problem, not a backtracking problem.**

---

# 12. Pattern — LIS

### Recognition
- subsequence
- increasing/decreasing
- longest
- order preserved
- not necessarily contiguous

### Basic DP

```text
dp[i] = LIS ending at i
```

Transition:

```text
if nums[j] < nums[i]:
    dp[i] = max(dp[i], dp[j] + 1)
```

Basic complexity: `O(N^2)`

Optimized LIS: `O(N log N)`

### Famous Problems
- 300 — Longest Increasing Subsequence
- 673 — Number of Longest Increasing Subsequence
- 354 — Russian Doll Envelopes
- 646 — Maximum Length of Pair Chain

---

# 13. Pattern — Stock State DP

### Recognition
- buy/sell
- hold
- transactions
- cooldown
- transaction fee
- maximum profit

### States

```text
holding
notHolding
```

May add:
- day
- number of transactions
- cooldown
- fee

### Famous Problems
- 121 — Best Time to Buy and Sell Stock
- 122 — Best Time to Buy and Sell Stock II
- 123 — Best Time to Buy and Sell Stock III
- 188 — Best Time to Buy and Sell Stock IV
- 309 — Best Time to Buy and Sell Stock with Cooldown
- 714 — Best Time to Buy and Sell Stock with Transaction Fee

---

# 14. Pattern — House Robber / Adjacent Restriction

### Recognition

> Cannot choose adjacent elements.

### State

```text
dp[i] = best answer up to index i
```

### Transition

Skip:

```text
dp[i-1]
```

Take:

```text
nums[i] + dp[i-2]
```

Therefore:

```text
dp[i] = max(dp[i-1], nums[i] + dp[i-2])
```

### Famous Problems
- 198 — House Robber
- 213 — House Robber II
- 337 — House Robber III

House Robber III is Tree DP.

---

# 15. Pattern — Interval DP

### Recognition
- interval/range `[l,r]`
- merge
- split
- partition
- burst
- minimum cost
- matrix-chain style

### State

```text
dp[l][r]
```

### Common Transition

```text
dp[l][r] =
    best over k of
    dp[l][k] + dp[k+1][r] + cost
```

### Famous Problems
- 312 — Burst Balloons
- 1039 — Minimum Score Triangulation of Polygon
- 1547 — Minimum Cost to Cut a Stick
- 516 — Longest Palindromic Subsequence
- 1000 — Minimum Cost to Merge Stones

---

# 16. Pattern — Palindrome DP

### Recognition
- palindrome
- palindromic substring
- palindromic subsequence
- minimum cuts
- palindrome partitioning

### State

```text
dp[l][r]
```

For palindrome checking:

```text
s[l] == s[r]
```

and the inner range must also satisfy the palindrome condition.

### Famous Problems
- 5 — Longest Palindromic Substring
- 516 — Longest Palindromic Subsequence
- 647 — Palindromic Substrings
- 131 — Palindrome Partitioning
- 132 — Palindrome Partitioning II

---

# 17. Pattern — String Partition / Word Break

### Recognition
- dictionary
- break/segment string
- words
- construct prefix
- sentence generation

### State

```text
dp[i]
```

Meaning:

> Can the prefix ending at `i` be constructed?

### Transition

Try a previous split `j`. If:

```text
dp[j] == true
```

and:

```text
s[j...i]
```

is a valid word, then:

```text
dp[i] = true
```

### Famous Problems
- 139 — Word Break
- 140 — Word Break II
- 472 — Concatenated Words

---

# 18. Pattern — Counting DP

### Recognition
- how many ways?
- number of ways
- count arrangements
- count paths
- count subsequences

Usually transitions use:

```text
+
```

rather than min/max.

### Critical Distinction

**Ordered sequences:** `[1,2]` and `[2,1]` are different.

**Unordered combinations:** they represent the same selection.

This distinction is critical in Coin Change-style problems.

### Famous Problems
- 70 — Climbing Stairs
- 62 — Unique Paths
- 91 — Decode Ways
- 115 — Distinct Subsequences
- 518 — Coin Change II

---

# 19. Pattern — Tree DP

### Recognition
- binary tree
- choose/not choose a node
- max/min/count
- answer combines left and right subtrees

### Typical State

Return multiple states, for example:

```text
rob
notRob
```

For each node:

```text
notRob = max(left.rob, left.notRob)
        + max(right.rob, right.notRob)

rob = node.val
      + left.notRob
      + right.notRob
```

### Famous Problems
- 337 — House Robber III
- 124 — Binary Tree Maximum Path Sum
- 968 — Binary Tree Cameras
- 543 — Diameter of Binary Tree

---

# 20. Pattern — DAG / Graph DP

### Recognition
- directed acyclic graph
- paths
- longest/shortest path
- number of ways
- dependencies

### Approaches
- DFS + memoization
- Topological sort + DP

### Famous Problems
- 329 — Longest Increasing Path in a Matrix
- 787 — Cheapest Flights Within K Stops
- 2050 — Parallel Courses III
- 1377 — Frog Position After T Seconds

---

# 21. Pattern — Bitmask DP

### Recognition
- small `N`, often around 15–20
- visited subset
- assignment
- visit every node
- TSP-style state
- set of selected elements

### State

```text
dp[mask][i]
```

Meaning:

> Best answer when visited set is `mask` and current position is `i`.

### Famous Problems
- 847 — Shortest Path Visiting All Nodes
- 943 — Find the Shortest Superstring
- 1125 — Smallest Sufficient Team
- 1349 — Maximum Students Taking Exam

---

# 22. Pattern — Digit DP

Advanced pattern.

### Recognition
- count numbers in `[L,R]`
- digit restrictions
- digit sum
- repeated digits
- numbers containing certain digits

### Typical State

```text
dp[pos][tight][started][property]
```

### Famous Problems
- 233 — Number of Digit One
- 600 — Non-negative Integers without Consecutive Ones
- 1012 — Numbers With Repeated Digits

**Priority:** Learn the core DP patterns first. Digit DP is advanced.

---

# 23. Pattern — State Machine DP

### Recognition

The problem can be represented as:

```text
current state
      ↓
allowed choices
      ↓
next state
```

Examples:
- stock trading
- cooldown
- paint house
- attendance
- restricted sequences

Typical state:

```text
dp[position][state]
```

or:

```text
dp[day][state][extra information]
```

The key is identifying valid states and transitions.

---

# 24. Pattern — Prefix / Suffix DP

### Recognition
The answer depends on:
- information before `i`
- information after `i`
- both sides

Typical structures:

```text
prefix[i]
suffix[i]
```

or a forward/reverse DP pass.

Related examples:
- 238 — Product of Array Except Self
- 42 — Trapping Rain Water
- 152 — Maximum Product Subarray

Not every prefix/suffix problem is strictly DP, but the state-compression idea is closely related.

---

# 25. Memoization vs Tabulation

## Memoization

```text
solve(state)
```

with:

```text
memo[state]
```

Advantages:
- natural from recursion
- easy to derive
- useful for tree/graph DP
- computes only reachable states

## Tabulation

Start with base cases and build larger states.

Advantages:
- no recursion overhead
- often easier to space-optimize
- common interview implementation

### Best Learning Sequence

```text
Brute force recursion
        ↓
Identify state
        ↓
Memoization
        ↓
Tabulation
        ↓
Space optimization
```

---

# 26. Derive DP From Recursion

This is one of the best ways to learn DP.

Example: 0/1 Knapsack.

### Step 1 — Brute Force

At every item:

```text
take
skip
```

### Step 2 — Recursive State

```text
solve(i, capacity)
```

### Step 3 — Find Repeated States

Different paths may reach:

```text
solve(5,20)
```

again.

### Step 4 — Memoize

```text
memo[i][capacity]
```

### Step 5 — Tabulate

Build:

```text
dp[i][capacity]
```

### Step 6 — Optimize Space

If only the previous row is needed, reduce 2D to 1D.

> **Backtracking explores decision paths. DP notices that many paths reach the same state and solves that state once.**

---

# 27. DP Complexity

Interview formula:

> **Time = Number of states × transitions per state**

Example:

```text
dp[i][capacity]
```

with `N` items and capacity `C`:

```text
states = N × C
```

If each state has 2 transitions:

```text
Time = O(N × C)
Space = O(N × C)
```

Potentially optimize space to:

```text
O(C)
```

---

# 28. Space Optimization

First get the recurrence correct.

Then ask:

> Do I need the entire previous history?

If:

```text
dp[i]
```

only depends on:

```text
dp[i-1]
dp[i-2]
```

you may use a few variables.

2D DP may sometimes become 1D:

```text
dp[i][j] → dp[j]
```

> **Correctness first. Optimization second.**

---

# 29. DP Pattern Map

```text
i
→ 1D DP

i + capacity
→ Knapsack

i + sum
→ Subset Sum

row + col
→ Grid DP

i + j
→ String DP / LCS

i + state
→ State Machine DP

l + r
→ Interval / Palindrome DP

tree node + states
→ Tree DP

mask + position
→ Bitmask DP

digit position + tight + property
→ Digit DP
```

---

# 30. Recommended Learning Order

1. Fibonacci / Climbing Stairs
2. Basic 1D DP
3. House Robber
4. Take / Skip
5. 0/1 Knapsack
6. Subset Sum / Partition
7. Unbounded Knapsack
8. Coin Change
9. Grid DP
10. LCS
11. Edit Distance
12. LIS
13. String DP / Word Break
14. Stock State DP
15. Palindrome DP
16. Interval DP
17. Tree DP
18. DAG DP
19. Bitmask DP
20. Digit DP

---

# 31. High-Value LeetCode Set

### Level 1 — Foundation
- 70 — Climbing Stairs
- 746 — Min Cost Climbing Stairs
- 198 — House Robber
- 213 — House Robber II
- 91 — Decode Ways

### Level 2 — Knapsack / Subset
- 416 — Partition Equal Subset Sum
- 494 — Target Sum
- 322 — Coin Change
- 518 — Coin Change II
- 1049 — Last Stone Weight II

### Level 3 — Grid
- 62 — Unique Paths
- 63 — Unique Paths II
- 64 — Minimum Path Sum
- 221 — Maximal Square

### Level 4 — String
- 1143 — Longest Common Subsequence
- 72 — Edit Distance
- 115 — Distinct Subsequences
- 97 — Interleaving String
- 139 — Word Break

### Level 5 — LIS
- 300 — Longest Increasing Subsequence
- 673 — Number of Longest Increasing Subsequence
- 354 — Russian Doll Envelopes

### Level 6 — Stock
- 121 — Best Time to Buy and Sell Stock
- 122 — Best Time to Buy and Sell Stock II
- 123 — Best Time to Buy and Sell Stock III
- 309 — Best Time to Buy and Sell Stock with Cooldown
- 714 — Best Time to Buy and Sell Stock with Transaction Fee

### Level 7 — Advanced
- 5 — Longest Palindromic Substring
- 516 — Longest Palindromic Subsequence
- 312 — Burst Balloons
- 337 — House Robber III
- 329 — Longest Increasing Path in a Matrix
- 847 — Shortest Path Visiting All Nodes

---

# 32. Interview Checklist

Before coding:

- What is the smaller subproblem?
- Are states repeated?
- Is the state finite?
- Does my state contain all necessary information?
- Is there optimal substructure?
- What are the base cases?
- What are the choices/transitions?
- Is this min, max, count, or boolean DP?
- How many states exist?
- How many transitions per state?
- Can I memoize the recursion?
- Can I tabulate it?
- Can I optimize space?

---

# 33. Final Mental Model

Do not memorize 30 formulas.

Memorize:

```text
1. Define state
        ↓
2. Identify choices/transitions
        ↓
3. Ask whether states repeat
        ↓
4. If yes → DP candidate
        ↓
5. Write recurrence
        ↓
6. Define base cases
        ↓
7. Memoization
        ↓
8. Tabulation
        ↓
9. Space optimization
```

Recognize state shape:

```text
i
→ 1D DP

i + capacity
→ Knapsack

i + sum
→ Subset Sum

row + col
→ Grid DP

i + j
→ String DP / LCS

i + state
→ State Machine

l + r
→ Interval / Palindrome

tree node + states
→ Tree DP

mask + position
→ Bitmask DP

digit position + tight + property
→ Digit DP
```

> **Don't memorize DP solutions. Learn to recognize the state.**

---

# 34. MUST SOLVE — 10 DP QUESTIONS

## 1D / Basic
1. **70 — Climbing Stairs**

## 1D / Cost
2. **746 — Min Cost Climbing Stairs**

## Take / Skip
3. **198 — House Robber**

## Circular / State
4. **213 — House Robber II**

## String / Counting
5. **91 — Decode Ways**

## Subset / Knapsack
6. **416 — Partition Equal Subset Sum**

## Unbounded Knapsack
7. **322 — Coin Change**

## Grid
8. **64 — Minimum Path Sum**

## String DP
9. **1143 — Longest Common Subsequence**

## LIS
10. **300 — Longest Increasing Subsequence**

---

# 35. MUST SOLVE — 20 DP QUESTIONS

## 1D / Take-Skip
1. **70 — Climbing Stairs**
2. **746 — Min Cost Climbing Stairs**
3. **198 — House Robber**
4. **213 — House Robber II**

## String / Sequence
5. **91 — Decode Ways**
6. **139 — Word Break**
7. **300 — Longest Increasing Subsequence**
8. **673 — Number of Longest Increasing Subsequence**

## Knapsack / Subset
9. **416 — Partition Equal Subset Sum**
10. **494 — Target Sum**
11. **1049 — Last Stone Weight II**
12. **474 — Ones and Zeroes**

## Unbounded / Counting
13. **322 — Coin Change**
14. **518 — Coin Change II**
15. **279 — Perfect Squares**

## Grid
16. **62 — Unique Paths**
17. **63 — Unique Paths II**
18. **64 — Minimum Path Sum**

## String 2D
19. **1143 — Longest Common Subsequence**
20. **72 — Edit Distance**

---

# 36. MUST SOLVE — 30 DP QUESTIONS

## Basic / 1D / State
1. **70 — Climbing Stairs**
2. **746 — Min Cost Climbing Stairs**
3. **198 — House Robber**
4. **213 — House Robber II**
5. **91 — Decode Ways**

## Knapsack / Subset
6. **416 — Partition Equal Subset Sum**
7. **494 — Target Sum**
8. **1049 — Last Stone Weight II**
9. **474 — Ones and Zeroes**

## Unbounded / Target
10. **322 — Coin Change**
11. **518 — Coin Change II**
12. **279 — Perfect Squares**

## Grid
13. **62 — Unique Paths**
14. **63 — Unique Paths II**
15. **64 — Minimum Path Sum**
16. **120 — Triangle**
17. **221 — Maximal Square**

## String DP
18. **1143 — Longest Common Subsequence**
19. **72 — Edit Distance**
20. **115 — Distinct Subsequences**
21. **97 — Interleaving String**
22. **139 — Word Break**

## LIS
23. **300 — Longest Increasing Subsequence**
24. **673 — Number of Longest Increasing Subsequence**
25. **354 — Russian Doll Envelopes**

## Stock State DP
26. **121 — Best Time to Buy and Sell Stock**
27. **122 — Best Time to Buy and Sell Stock II**
28. **309 — Best Time to Buy and Sell Stock with Cooldown**

## Tree / Advanced
29. **337 — House Robber III**
30. **329 — Longest Increasing Path in a Matrix**

---

# 37. MUST SOLVE — 50 DP QUESTIONS

This is the full interview-focused set covering the major DP patterns.

## Pattern 1 — Basic 1D DP
1. **70 — Climbing Stairs**
2. **746 — Min Cost Climbing Stairs**
3. **198 — House Robber**
4. **213 — House Robber II**
5. **91 — Decode Ways**

## Pattern 2 — 0/1 Knapsack / Take-Skip
6. **416 — Partition Equal Subset Sum**
7. **494 — Target Sum**
8. **1049 — Last Stone Weight II**
9. **474 — Ones and Zeroes**
10. **518 — Coin Change II** — revisit specifically for counting/state-order reasoning

## Pattern 3 — Unbounded Knapsack / Reuse
11. **322 — Coin Change**
12. **518 — Coin Change II**
13. **279 — Perfect Squares**
14. **377 — Combination Sum IV**

## Pattern 4 — Grid DP
15. **62 — Unique Paths**
16. **63 — Unique Paths II**
17. **64 — Minimum Path Sum**
18. **120 — Triangle**
19. **931 — Minimum Falling Path Sum**
20. **221 — Maximal Square**

## Pattern 5 — Two-String / LCS / Edit DP
21. **1143 — Longest Common Subsequence**
22. **72 — Edit Distance**
23. **115 — Distinct Subsequences**
24. **97 — Interleaving String**
25. **583 — Delete Operation for Two Strings**
26. **712 — Minimum ASCII Delete Sum for Two Strings**

## Pattern 6 — LIS / Subsequence
27. **300 — Longest Increasing Subsequence**
28. **673 — Number of Longest Increasing Subsequence**
29. **354 — Russian Doll Envelopes**
30. **646 — Maximum Length of Pair Chain**

## Pattern 7 — String / Partition
31. **139 — Word Break**
32. **140 — Word Break II**
33. **472 — Concatenated Words**

## Pattern 8 — Stock State DP
34. **121 — Best Time to Buy and Sell Stock**
35. **122 — Best Time to Buy and Sell Stock II**
36. **123 — Best Time to Buy and Sell Stock III**
37. **188 — Best Time to Buy and Sell Stock IV**
38. **309 — Best Time to Buy and Sell Stock with Cooldown**
39. **714 — Best Time to Buy and Sell Stock with Transaction Fee**

## Pattern 9 — Palindrome / Interval DP
40. **5 — Longest Palindromic Substring**
41. **516 — Longest Palindromic Subsequence**
42. **647 — Palindromic Substrings**
43. **312 — Burst Balloons**
44. **1547 — Minimum Cost to Cut a Stick**

## Pattern 10 — Tree / Graph / Advanced DP
45. **337 — House Robber III**
46. **124 — Binary Tree Maximum Path Sum**
47. **329 — Longest Increasing Path in a Matrix**
48. **787 — Cheapest Flights Within K Stops**
49. **847 — Shortest Path Visiting All Nodes**
50. **1125 — Smallest Sufficient Team**

---

# 38. How to Use the 10 / 20 / 30 / 50 Lists

## Stage 1 — 10 Questions
Goal:

> Understand what DP actually is.

You should be able to identify:
- state
- transition
- base case
- memoization

## Stage 2 — 20 Questions
Goal:

> Become comfortable with the major DP patterns.

Recognize:

```text
1D
Knapsack
Subset
Unbounded
Grid
String
LIS
```

## Stage 3 — 30 Questions
Goal:

> Become interview-ready with the core patterns.

Add:

```text
Stock DP
Tree DP
Graph DP
```

## Stage 4 — 50 Questions
Goal:

> Strong SDE2 DP coverage.

Add:

```text
Palindrome
Interval
Advanced String
Bitmask
Graph
State Machine
```

---

# 39. Final DP Pattern Checklist

```text
[ ] I can explain what dp[state] means.
[ ] My state contains all necessary information.
[ ] I know the choices/transitions.
[ ] I know the base cases.
[ ] I handle impossible states correctly.
[ ] I know whether this is min/max/count/boolean DP.
[ ] I checked for repeated states.
[ ] I know the number of states.
[ ] I know transitions per state.
[ ] I can explain time complexity.
[ ] I can explain space complexity.
[ ] I can derive memoization from recursion.
[ ] I can convert it to tabulation if asked.
[ ] I know whether space can be optimized.
```

## Final Rule

> **Don't memorize DP solutions. Learn to recognize the state.**
