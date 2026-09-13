1. **Deque Category (Medium):** When dealing with the "shortest window" problem with a sum greater than or equal to  in an array containing negative numbers, a *Deque* (double-ended queue) is required (5:12).
2. **Merge Sort Category (Hard):** These are complex problems involving range sums, which may require applying *Merge Sort* principles on a prefix sum array (5:38, 5:56)., similar to this video give me prefix sum patterns with recognition steps to start solving then sample problems of leet code

Yes. The video’s **4-category structure is a good way to learn Prefix Sum**. For interviews, I would expand it slightly so you can recognize the pattern before coding.

# Prefix Sum — Interview Pattern Guide

The core idea:

> **Prefix Sum = preprocess cumulative information so a range/subarray query can be answered without repeatedly traversing the array.**

For an array:

`prefix[i] = sum of elements from 0 to i`

Then:

`sum(l..r) = prefix[r] - prefix[l-1]`

But in interviews, the important part is **recognizing which data structure/pattern is needed**.

---

## Quick Recognition Table

| PatternRecognitionData StructureTypical Goal |                                                                |                       |                               |
| -------------------------------------------- | -------------------------------------------------------------- | --------------------- | ----------------------------- |
| **1. Simple Prefix Sum**                     | Need sum before/after each index                               | Variable / array      | Pivot, equilibrium, range sum |
| **2. Prefix Sum + HashMap**                  | Need **exact subarray sum = K**                                | HashMap               | Count / existence             |
| **3. Prefix Sum + HashMap + Modulo**         | Need sum divisible by `K` / remainder condition                | HashMap               | Count / longest subarray      |
| **4. Prefix Sum + Deque**                    | Need shortest subarray `sum >= K` + **negative numbers**       | Monotonic Deque       | Minimum length                |
| **5. Prefix Sum + Binary Search**            | Prefix sums are monotonic, need threshold/range                | Array + Binary Search | Find boundary                 |
| **6. Prefix Sum + Sorting**                  | Need count of prefix-pair relationships                        | Sorted structure      | Counting                      |
| **7. Prefix Sum + Merge Sort**               | Need count of prefix pairs satisfying an **inequality/range**  | Merge Sort            | Hard counting                 |
| **8. 2D Prefix Sum**                         | Matrix + rectangular region sum                                | 2D prefix array       | Range sum                     |
| **9. Difference Array**                      | Many range updates, then reconstruct values                    | Difference array      | Range increment/update        |
| **10. Prefix Sum + Other Pattern**           | Prefix sum reduces problem, then another technique finishes it | Depends               | Advanced                      |

For your SDE2 preparation, **1–8 are the important ones**.

---

# 1. Simple Prefix Sum

### Recognition

Ask:

> **Can I express the answer using "sum before me" + "sum after me"?**

Typical wording:

-  equilibrium index 
-  pivot index 
-  left sum = right sum 
-  range sum queries 
-  cumulative sum 
-  sum of elements before/after an index 

### Basic thought process

At index `i`:

```
```

```
leftSum  = sum[0 ... i-1]
rightSum = totalSum - leftSum - nums[i]
```

Then compare them.

### LeetCode

**Easy → Medium**

-  LC 724 — Find Pivot Index 
-  LC 303 — Range Sum Query - Immutable 
-  LC 1480 — Running Sum of 1d Array 
-  LC 1991 — Find the Middle Index in Array 

### Recognition shortcut

> **"Balance around an index" → Prefix Sum**

---

# 2. Prefix Sum + HashMap — Exact Sum K

This is probably the **most important prefix-sum pattern**.

### Recognition

Look for:

> **subarray + sum = K**

Especially:

-  number of subarrays with sum `K` 
-  does a subarray sum to `K`? 
-  longest subarray with sum `K` 
-  exact target sum 

### Key idea

Suppose:

```
```

```
prefix[j] - prefix[i] = K
```

Therefore:

```
```

```
prefix[i] = prefix[j] - K
```

So while processing the current prefix:

> **Have I seen** **`currentPrefix - K`** **before?**

Use a HashMap.

### Example

```
```

```
nums = [1, 2, 3]
K = 3

prefix:
1
3
6
```

At prefix `3`:

```
```

```
3 - 3 = 0
```

So a previous prefix `0` gives a subarray with sum `3`.

### LeetCode

**Must know:**

-  LC 560 — Subarray Sum Equals K ⭐ 
-  LC 523 — Continuous Subarray Sum 
-  LC 525 — Contiguous Array 
-  LC 325 — Maximum Size Subarray Sum Equals K 

### Recognition shortcut

> **"Subarray sum exactly K" → Prefix Sum + HashMap**

---

# 3. Prefix Sum + HashMap + Modulo

This is a variation of the previous pattern.

### Recognition

Look for:

-  divisible by `K` 
-  remainder 
-  multiple of `K` 
-  sum % K 
-  subarray whose sum is divisible by K 

The key mathematical observation:

If

```
```

```
prefix[j] % K == prefix[i] % K
```

then:

```
```

```
(prefix[j] - prefix[i]) % K == 0
```

Therefore the subarray between them is divisible by `K`.

### Example

```
```

```
prefix remainder:
2 → 4 → 2
```

The repeated remainder `2` means the elements between those two positions have a sum divisible by `K`.

### LeetCode

-  LC 523 — Continuous Subarray Sum ⭐ 
-  LC 974 — Subarray Sums Divisible by K ⭐ 
-  LC 1590 — Make Sum Divisible by P 

### Recognition shortcut

> **"Subarray + divisible/modulo" → Prefix Sum + HashMap(remainder)**

---

# 4. Prefix Sum + Monotonic Deque

This is the **important advanced pattern** mentioned in your video.

### Recognition

Look for exactly this combination:

> **shortest subarray + sum >= K + negative numbers**

This combination should immediately make you think:

**Prefix Sum + Monotonic Deque**

### Why normal sliding window fails

Sliding window generally relies on:

```
```

```
adding an element → sum increases
removing an element → sum decreases
```

That assumption breaks when negative numbers exist.

Example:

```
```

```
[2, -5, 10]
```

The sum can go up and down unpredictably.

So we use prefix sums:

```
```

```
P[j] - P[i] >= K
```

and maintain useful candidate prefix indices in a **monotonic increasing deque**.

### LeetCode

**The main problem:**

-  LC 862 — Shortest Subarray with Sum at Least K ⭐⭐⭐ 

This is a **must-understand problem** for advanced prefix sum.

### Recognition shortcut

> **"Shortest subarray" + "sum ≥ K" + negatives → Prefix Sum + Monotonic Deque**

---

# 5. Prefix Sum + Binary Search

### Recognition

Think about this when:

-  array values are non-negative 
-  prefix sums are therefore monotonic 
-  need to find the first prefix reaching some target 
-  need a boundary/index 

For non-negative numbers:

```
```

```
prefix = [0, 2, 5, 9, 14, ...]
```

Prefix is sorted.

Therefore you can binary-search it.

### Example

If you need:

> smallest index where cumulative sum ≥ K

You can binary search the prefix array.

### LeetCode

-  LC 209 — Minimum Size Subarray Sum 
-  LC 713 — Subarray Product Less Than K *(related sliding-window idea, not pure prefix sum)* 
-  LC 2389 — Longest Subsequence With Limited Sum *(prefix sum + sorting + binary search)* 

### Recognition shortcut

> **Non-negative + cumulative sum + find boundary → Prefix Sum + Binary Search**

---

# 6. Prefix Sum + Sorting

This is where prefix sums start becoming more advanced.

### Recognition

Look for:

> Count pairs of prefix sums satisfying an inequality/range condition.

For example:

```
```

```
lower <= prefix[j] - prefix[i] <= upper
```

Rearrange:

```
```

```
prefix[j] - upper <= prefix[i] <= prefix[j] - lower
```

Now the problem becomes:

> How many previous prefix sums fall inside this range?

That leads to sorting / ordered structures / merge sort.

---

# 7. Prefix Sum + Merge Sort

This is the **hard category** from the video.

### Recognition

Look for:

-  count range sums 
- `lower <= subarraySum <= upper` 
-  number of subarrays satisfying a sum range 
-  prefix sums + inequalities 
-  need to count pairs efficiently 

### Core transformation

Given:

```
```

```
prefix[j] - prefix[i]
```

and condition:

```
```

```
lower <= prefix[j] - prefix[i] <= upper
```

we get:

```
```

```
prefix[j] - upper <= prefix[i] <= prefix[j] - lower
```

So for every prefix `j`, we need to count earlier prefix values inside a range.

Merge sort lets us do this efficiently while maintaining sorted halves.

### LeetCode

**LC 327 — Count of Range Sum ⭐⭐⭐**

This is the key problem to learn.

### Recognition shortcut

> **"Count subarrays whose sum lies within [lower, upper]" → Prefix Sum + Merge Sort**

381227439311825

svg

svg

svg

Divide

The values begin in their original order.

Merge step

Merge step

8 · even7 · uneven

8 · even7 · uneven

Give feedback

---

# 8. 2D Prefix Sum

Don't restrict prefix sums to 1D arrays.

### Recognition

Look for:

> **Matrix + rectangular region + sum**

Example:

```
```

```
Find sum of matrix:
(row1,col1) → (row2,col2)
```

Build:

```
```

```
prefix[i][j]
```

representing the sum of the rectangle from `(0,0)` to `(i,j)`.

Then any rectangle can be calculated in **O(1)**.

### Formula

Conceptually:

```
```

```
answer
= big rectangle
- top rectangle
- left rectangle
+ overlapping rectangle
```

The `+` is needed because the top-left region was subtracted twice.

### LeetCode

-  LC 304 — Range Sum Query 2D - Immutable ⭐ 
-  LC 1074 — Number of Submatrices That Sum to Target ⭐⭐⭐ 

### Important advanced connection

LC 1074 combines:

> **2D Prefix Sum + HashMap**

This is a very good SDE2-level problem.

---

# 9. Difference Array

This is technically the **reverse direction of prefix sums** and worth learning alongside them.

### Recognition

Look for:

> **Many range updates, then ask for the final array.**

Example:

```
```

```
add +5 to [2, 6]
add +3 to [4, 8]
add -2 to [1, 3]
```

Don't update every element individually.

Instead:

```
```

```
diff[l] += value
diff[r + 1] -= value
```

Then take a prefix sum to reconstruct the final array.

### Recognition shortcut

> **Many range updates → Difference Array**

### LeetCode

-  LC 1094 — Car Pooling 
-  LC 1109 — Corporate Flight Bookings ⭐ 
-  LC 1589 — Maximum Sum Obtained of Any Permutation 

---

# The Most Important Recognition Flow

When you see an array/subarray sum problem, go through this:

```
```

```
                ARRAY / SUBARRAY SUM
                         |
                         ↓
              Is it about a range sum?
                         |
                       YES
                         |
                         ↓
                 Can prefix sum help?
                         |
                       YES
                         |
          ┌──────────────┼───────────────┐
          ↓              ↓               ↓
       Exact K       Divisible K      Sum >= K
          |              |               |
          ↓              ↓               ↓
      HashMap        HashMap          Negative?
                                          |
                                    ┌─────┴─────┐
                                   YES           NO
                                    ↓             ↓
                                  Deque       Sliding Window
```

And for counting harder relationships:

```
```

```
Prefix Sum
    |
    ↓
Need count of prefix pairs?
    |
    ↓
Inequality / range condition?
    |
    ↓
Prefix Sum + Merge Sort
```

---

# Your Prefix Sum Cheat Sheet

For interviews, memorize these **recognition triggers**, not implementations:

| If you see...Think...               |                                             |
| ----------------------------------- | ------------------------------------------- |
| Pivot / equilibrium                 | **Prefix Sum**                              |
| Range sum                           | **Prefix Sum**                              |
| Subarray sum = K                    | **Prefix + HashMap**                        |
| Longest subarray sum = K            | **Prefix + HashMap**                        |
| Count subarrays sum = K             | **Prefix + HashMap**                        |
| Sum divisible by K                  | **Prefix + HashMap + Modulo**               |
| Shortest subarray ≥ K + negatives   | **Prefix + Monotonic Deque**                |
| Non-negative + cumulative threshold | **Prefix + Binary Search / Sliding Window** |
| Count range sums                    | **Prefix + Merge Sort**                     |
| Matrix rectangle sum                | **2D Prefix Sum**                           |
| Many range additions                | **Difference Array**                        |
| 2D submatrix target sum             | **2D compression + Prefix Sum + HashMap**   |

---

# Problems I'd Actually Solve

For your **SDE2 preparation**, don't solve 30 prefix-sum questions. I'd do these **10–12 properly**:

### Foundation

1. **LC 1480** — Running Sum of 1d Array 
2. **LC 724** — Find Pivot Index 
3. **LC 303** — Range Sum Query - Immutable 

### HashMap

4. **LC 560** — Subarray Sum Equals K ⭐ 
5. **LC 525** — Contiguous Array 
6. **LC 974** — Subarray Sums Divisible by K ⭐ 
7. **LC 325** — Maximum Size Subarray Sum Equals K 

### Advanced

8. **LC 862** — Shortest Subarray with Sum at Least K ⭐⭐⭐ 
9. **LC 327** — Count of Range Sum ⭐⭐⭐ 

### 2D

10. **LC 304** — Range Sum Query 2D 
11. **LC 1074** — Number of Submatrices That Sum to Target ⭐⭐⭐ 

### Difference Array

12. **LC 1109** — Corporate Flight Bookings 

If you can look at a new problem and correctly classify it into these categories **before writing code**, you've learned Prefix Sum—not just memorized solutions.

Yes. **This is exactly the kind of trick you should collect**—small mathematical relationships that let you avoid unnecessary arrays or nested loops.

Your observation is correct, with one small notation point:

> `prefixSum[i] = sum of elements from index 0 to i-1`

So if `prefixSum` has length `n + 1`, then:

```
```

```
prefixSum[0] = 0
prefixSum[i] = arr[0] + ... + arr[i-1]
```

For Pivot Index, the key identity is:

```
```

```
leftSum + arr[i] + rightSum = totalSum
```

Therefore:

```
```

```
rightSum = totalSum - leftSum - arr[i]
```

And we don't need a `prefixSum[]` array:

```
```

```
leftSum = 0

for each i:
    rightSum = totalSum - leftSum - arr[i]

    if leftSum == rightSum:
        return i

    leftSum += arr[i]
```

That's a very useful **Prefix Sum Trick #1**.

---

# Prefix Sum Tricks You Should Add

I'd organize your notes around these **reusable transformations**.

---

## Trick #1 — Prefix + Suffix From Total

Your trick:

```
```

```
leftSum + arr[i] + rightSum = totalSum
```

Therefore:

```
```

```
rightSum = totalSum - leftSum - arr[i]
```

### Recognition

If the problem asks:

> "What is the sum on the left and right of every index?"

Don't build both prefix and suffix arrays.

Keep:

```
```

```
totalSum
leftSum
```

and calculate the other side.

### Problem

**LC 724 — Find Pivot Index**

### Complexity

```
```

```
Time  = O(n)
Space = O(1)
```

---

# Trick #2 — Subarray Sum = Difference of Two Prefix Sums

This is probably the **most important prefix-sum trick**.

If:

```
```

```
prefix[i] = arr[0] + ... + arr[i-1]
```

then:

```
```

```
sum(l ... r) = prefix[r + 1] - prefix[l]
```

Example:

```
```

```
arr = [2, 4, 1, 5, 3]

prefix = [0, 2, 6, 7, 12, 15]

sum(1...3)
= 4 + 1 + 5
= prefix[4] - prefix[1]
= 12 - 2
= 10
```

### Recognition

Whenever you see:

> **sum of elements between L and R**

think:

```
```

```
PREFIX[R+1] - PREFIX[L]
```

### Problems

-  LC 303 — Range Sum Query - Immutable 
-  LC 304 — Range Sum Query 2D - Immutable 

---

# Trick #3 — Subarray Sum = K → Find Two Prefix Sums Differing by K

Start from:

```
```

```
subarraySum = prefix[j] - prefix[i]
```

If:

```
```

```
subarraySum = K
```

then:

```
```

```
prefix[j] - prefix[i] = K
```

Rearrange:

```
```

```
prefix[i] = prefix[j] - K
```

So at every index:

> **Have I already seen** **`currentPrefix - K`****?**

That's why HashMap works.

### Recognition

```
```

```
subarray
+
sum exactly K
```

→ **Prefix Sum + HashMap**

### Problem

**LC 560 — Subarray Sum Equals K**

This mathematical transformation is more important than memorizing the code.

---

# Trick #4 — Same Prefix Sum Means Zero-Sum Subarray

Suppose:

```
```

```
prefix[i] = prefix[j]
```

Then:

```
```

```
prefix[j] - prefix[i] = 0
```

Therefore:

> The elements between `i` and `j-1` have sum `0`.

### Recognition

If you see:

> Find a subarray with sum `0`

think:

```
```

```
same prefix sum
```

### Example

```
```

```
arr = [4, 2, -2, 5]

prefix:
0
4
6
4
9
```

`4` appears twice.

Therefore:

```
```

```
2 + (-2) = 0
```

### Useful extension

If you need the **longest** zero-sum subarray:

> Store the **first occurrence** of each prefix sum.

This gives the maximum distance between equal prefix sums.

### Related problem

**LC 525 — Contiguous Array**

---

# Trick #5 — Same Remainder → Sum Divisible by K

This is a very important extension.

If:

```
```

```
prefix[j] % K == prefix[i] % K
```

then:

```
```

```
(prefix[j] - prefix[i]) % K == 0
```

Therefore:

> Same prefix remainder ⇒ subarray sum is divisible by K.

### Recognition

If you see:

```
```

```
subarray
+
divisible by K
```

think:

**Prefix Sum + Remainder + HashMap**

### Problems

-  LC 523 — Continuous Subarray Sum 
-  LC 974 — Subarray Sums Divisible by K 

---

# Trick #6 — Convert "Longest" Into Maximum Distance Between Prefix States

This is more of a **general prefix-sum strategy**.

Suppose:

```
```

```
prefix[j] = prefix[i]
```

Then the subarray between them has sum `0`.

If you want the **longest** such subarray:

```
```

```
length = j - i
```

So:

> Store the **earliest occurrence** of a prefix state.

Don't overwrite it.

Example:

```
```

```
prefix = 5 first seen at index 2
prefix = 5 again at index 10
```

Then:

```
```

```
longest length = 10 - 2 = 8
```

### Recognition

```
```

```
longest subarray
+
same prefix condition
```

→ Store **first occurrence**.

---

# Trick #7 — Counting = Store Frequency, Not First Index

The previous trick changes if the question asks:

> **How many subarrays?**

Suppose:

```
```

```
currentPrefix = 10
K = 3
```

We need:

```
```

```
previousPrefix = 10 - 3 = 7
```

If `7` has appeared **5 times**, then there are **5 different subarrays** ending here with sum `3`.

Therefore:

```
```

```
count += frequency[currentPrefix - K]
```

### Recognition

```
```

```
number of subarrays
```

→ HashMap stores **frequency**

Whereas:

```
```

```
longest subarray
```

→ HashMap usually stores **first index**

This distinction is extremely important.

---

# Trick #8 — Initialize Prefix HashMap With `0 → 1`

This tiny trick prevents edge-case bugs.

For:

```
```

```
arr = [3, ...]
K = 3
```

At the first element:

```
```

```
currentPrefix = 3
currentPrefix - K = 0
```

We need to recognize that a prefix sum of `0` existed **before the array started**.

So initialize:

```
```

```
map.put(0, 1)
```

Meaning:

> Prefix sum `0` has occurred once at the virtual position before index 0.

### Recognition

Whenever doing:

```
```

```
Prefix Sum + HashMap + counting
```

usually start with:

```
```

```
map.put(0, 1)
```

### Problem

**LC 560 — Subarray Sum Equals K**

---

# Trick #9 — Convert 0/1 Problems Into +1/-1

This one is extremely useful.

Suppose the problem asks:

> Find the longest subarray containing equal numbers of `0` and `1`.

Transform:

```
```

```
0 → -1
1 → +1
```

Now:

```
```

```
equal 0s and 1s
```

means:

```
```

```
sum = 0
```

And now we can use the **same prefix-sum trick**.

Example:

```
```

```
[0, 1, 0, 1]

→ [-1, +1, -1, +1]

prefix:
0
-1
0
-1
0
```

Repeated prefix sums reveal zero-sum subarrays.

### Recognition

Whenever you see:

> equal number of A and B

ask:

> **Can I represent A as +1 and B as -1?**

Then use prefix sum.

### Problem

**LC 525 — Contiguous Array**

This is a very good interview trick.

---

# Trick #10 — 2D Prefix Sum = Inclusion/Exclusion

For matrices, the same idea becomes:

```
```

```
Desired rectangle
= big rectangle
- unwanted top
- unwanted left
+ overlapping top-left
```

The `+` is critical because the overlap was removed twice.

### Recognition

```
```

```
matrix
+
rectangle
+
sum
```

→ **2D Prefix Sum**

### Problem

**LC 304 — Range Sum Query 2D**

And then:

**LC 1074 — Number of Submatrices That Sum to Target**

takes this much further by combining 2D prefix logic with HashMap.

---

# Trick #11 — Negative Numbers Kill Normal Sliding Window

This is an important recognition rule.

If:

```
```

```
all numbers >= 0
```

then increasing the window generally increases the sum, and shrinking decreases it.

So:

```
```

```
sum >= K
```

often → **Sliding Window**

But with:

```
```

```
negative numbers
```

that monotonic behavior disappears.

For:

```
```

```
shortest subarray with sum >= K
```

you need:

**Prefix Sum + Monotonic Deque**

### Problem

**LC 862 — Shortest Subarray with Sum at Least K**

---

# Trick #12 — Range Sum Inequality → Prefix Pair Problem

This is the bridge to the hard Merge Sort problem.

Suppose:

```
```

```
lower <= subarraySum <= upper
```

Using:

```
```

```
subarraySum = prefix[j] - prefix[i]
```

we get:

```
```

```
lower <= prefix[j] - prefix[i] <= upper
```

Rearrange:

```
```

```
prefix[j] - upper <= prefix[i] <= prefix[j] - lower
```

Now the problem is no longer really about subarrays.

It's:

> **For every prefix[j], count previous prefix values inside a range.**

That's the key transformation.

Then:

**Prefix Sum + Merge Sort**

### Problem

**LC 327 — Count of Range Sum**

---

# Your Prefix Sum Mental Model

I would put this at the top of your notes:

```
```

```
                    PREFIX SUM
                         |
                         ↓
             Subarray sum = difference
                of two prefixes
                         |
          ┌──────────────┼──────────────┐
          ↓              ↓              ↓
      Exact K        Divisible K      Zero Sum
          |              |              |
      HashMap        Remainder        Same Prefix
          |              |              |
          ↓              ↓              ↓
      Frequency       Frequency       First Index
```

And then the advanced branch:

```
```

```
PREFIX SUM
    |
    ├── Exact sum K
    │      └── HashMap
    │
    ├── Modulo K
    │      └── HashMap of remainder
    │
    ├── Shortest sum >= K + negatives
    │      └── Monotonic Deque
    │
    ├── Sum in [L, R]
    │      └── Merge Sort
    │
    ├── Matrix rectangle sum
    │      └── 2D Prefix Sum
    │
    └── Many range updates
           └── Difference Array
```

### The **most valuable tricks to memorize**

If I were narrowing this down for your SDE2 preparation, I'd make these your **Prefix Sum "aha" rules**:

1. **Left + current + right = total** 
2. **Subarray sum = prefix[j] − prefix[i]** 
3. **Sum = K → look for prefix − K** 
4. **Same prefix → zero-sum subarray** 
5. **Same remainder → divisible by K** 
6. **Longest → store first occurrence** 
7. **Count → store frequency** 
8. **Equal 0/1 → convert 0→−1, 1→+1** 
9. **Negative + shortest sum ≥ K → monotonic deque** 
10. **Range sum inequality → prefix pairs → merge sort** 

These are much more valuable than memorizing individual Prefix Sum problems.

# Java Modulo — Important for Prefix Sum

Java `