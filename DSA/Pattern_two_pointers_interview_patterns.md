# Two Pointers — Interview Pattern & Quick Revision Guide

> **Core idea:** Two Pointers uses two or more indices to scan an array, string, or linked list while avoiding unnecessary nested searches.

---

## 1. Quick Recognition Flow

```text
                    ARRAY / LINKED LIST / STRING
                              |
                              v
                 Can 2+ pointers eliminate
                    unnecessary searches?
                              |
                             YES
                              |
          +-------------------+-------------------+
          v                   v                   v
       SORTED?           PAIR/TRIPLET?        IN-PLACE?
          |                   |                   |
          +-------------------+-------------------+
                              |
                              v
                       TWO POINTERS
```

### Strong signals

- Array, string, or linked list
- Array is **already sorted** or can safely be sorted
- Find **pair / triplet / quadruplet**
- Remove duplicates
- Rearrange/filter elements
- Merge sorted arrays/lists
- Reverse or compare from both ends
- Partition elements into regions
- `O(1)` extra-space requirement
- Need better than brute-force combinations

---

# 2. The Most Important Question

Before coding, ask:

> **What does each pointer represent, and what condition tells me which pointer to move?**

For every pointer movement, be able to explain:

```text
left  = ?
right = ?

What invariant am I maintaining?

If I move this pointer,
why can I safely eliminate the candidates behind it?
```

The last question is the key to Two Pointers.

---

# 3. Main Two-Pointer Patterns

| Pattern | Pointer Movement | Typical Use |
|---|---|---|
| **Opposite Ends** | `left++`, `right--` | Pair target, palindrome, container |
| **Same Direction** | `slow++`, `fast++` | Remove/filter/compress |
| **Fast/Slow** | Different speeds | Linked-list cycle/middle |
| **Merge Pointers** | One pointer per sequence | Merge/intersection |
| **Partition Pointers** | 2–3 regions | In-place rearrangement |
| **Fix + Two Pointers** | Fix one, scan remaining range | 3Sum/4Sum |

---

# 4. Pattern #1 — Opposite Ends

## Recognition

Usually:

- Sorted array
- Pair + target
- Compare both ends
- Palindrome/reversal
- Need to shrink a search space

Start with:

```java
int left = 0;
int right = n - 1;

while (left < right) {
    // decide which pointer to move
}
```

### Example — Two Sum II

For a sorted array:

```text
[1, 2, 4, 7, 11, 15]
target = 15
```

If:

```text
arr[left] + arr[right] < target
```

we need a larger sum:

```text
left++
```

If:

```text
arr[left] + arr[right] > target
```

we need a smaller sum:

```text
right--
```

### Recognition shortcut

> **Sorted + pair + target → Opposite Two Pointers**

### LeetCode

- **LC 167** — Two Sum II ⭐
- **LC 11** — Container With Most Water ⭐
- **LC 125** — Valid Palindrome
- **LC 15** — 3Sum ⭐⭐⭐
- **LC 18** — 4Sum

---

# 5. Why Sorting Enables Two Pointers

Sorting gives **directional information**.

Example:

```text
[1, 3, 5, 7, 9]
```

Suppose:

```text
1 + 9 < target
```

Moving `9` left makes the sum even smaller, so `9` cannot help.

Therefore:

```text
left++
```

Similarly, if:

```text
1 + 9 > target
```

moving `1` right makes the sum larger, so `1` cannot help.

Therefore:

```text
right--
```

> **Sorting converts an unordered search into a directional search.**

---

# 6. Pattern #2 — Same Direction / Slow-Fast

Typical structure:

```java
int slow = 0;

for (int fast = 0; fast < n; fast++) {
    if (valid(nums[fast])) {
        nums[slow] = nums[fast];
        slow++;
    }
}
```

Think:

```text
fast → explores
slow → builds the valid result
```

### Recognition

- Remove duplicates
- Remove/filter elements
- Move valid elements forward
- In-place modification
- Preserve relative order
- Compress an array

### LeetCode

- **LC 26** — Remove Duplicates from Sorted Array ⭐
- **LC 27** — Remove Element
- **LC 283** — Move Zeroes ⭐
- **LC 80** — Remove Duplicates from Sorted Array II

### Recognition shortcut

> **One pointer explores, another builds the answer → Slow/Fast**

---

# 7. Pattern #3 — Fast / Slow on Linked Lists

```text
slow → 1 step
fast → 2 steps
```

### Recognition

- Linked list
- Detect cycle
- Find middle
- Relative distance between nodes
- Need different pointer speeds

### LeetCode

- **LC 141** — Linked List Cycle ⭐
- **LC 142** — Linked List Cycle II ⭐⭐
- **LC 876** — Middle of the Linked List
- **LC 19** — Remove Nth Node From End ⭐

### Recognition shortcut

> **Linked List + cycle/middle/distance → Fast/Slow**

---

# 8. Pattern #4 — Merge Two Sorted Sequences

Use one pointer for each sorted sequence:

```text
 i → array A
 j → array B
```

Compare the current elements and advance the pointer whose element was consumed.

### Recognition

- Two sorted arrays/lists
- Merge
- Intersection
- Common elements
- Compare two sorted sequences

### LeetCode

- **LC 88** — Merge Sorted Array ⭐
- **LC 21** — Merge Two Sorted Lists ⭐
- **LC 349** — Intersection of Two Arrays
- **LC 350** — Intersection of Two Arrays II

### Recognition shortcut

> **Two sorted sequences → One pointer per sequence**

---

# 9. Pattern #5 — Partition / Dutch National Flag

Use multiple pointers to maintain regions.

Classic:

```text
low
mid
high
```

Maintain:

```text
[0 ... low-1]    = 0
[low ... mid-1]  = 1
[mid ... high]   = unknown
[high+1 ... n-1] = 2
```

### Recognition

- Rearrange
- Partition
- Sort a small number of categories
- In-place
- `0, 1, 2`
- Three regions

### LeetCode

- **LC 75** — Sort Colors ⭐⭐

### Recognition shortcut

> **In-place partition into categories → Multiple Pointers**

---

# 10. Pattern #6 — 3Sum / 4Sum

Combination pattern:

```text
sort(array)

for each fixed i:
    left = i + 1
    right = n - 1

    use two pointers for the remaining target
```

For 3Sum, this changes brute force from `O(n³)` to `O(n²)` after sorting.

### Recognition

> **Find triplets/quadruplets + sorting is allowed → Fix one + Two Pointers**

### LeetCode

- **LC 15** — 3Sum ⭐⭐⭐
- **LC 18** — 4Sum
- **LC 16** — 3Sum Closest

---

# 11. Pattern #7 — String Two Pointers

Start from both ends:

```java
int left = 0;
int right = s.length() - 1;
```

Compare and move inward.

### Recognition

- Palindrome
- Reverse
- Compare both ends
- Symmetry
- Ignore certain characters

### LeetCode

- **LC 125** — Valid Palindrome ⭐
- **LC 344** — Reverse String
- **LC 680** — Valid Palindrome II ⭐

### Recognition shortcut

> **String + compare/reason from both ends → Two Pointers**

---

# 12. Important Trick — Sorting May Unlock Two Pointers

If the problem says:

> Find pairs/triplets with a condition

and the array is not sorted, ask:

> **Can I sort it without breaking the problem?**

Typical transformation:

```text
Unsorted array
      ↓
    Sort
      ↓
Fix one element
      ↓
Two Pointers
      ↓
O(n²)
```

### Do NOT blindly sort

Sorting can destroy information about:

- Original indices
- Original order
- Stable order
- Positional relationships

If those matter, preserve the information or use another pattern.

---

# 13. Important Trick — Duplicate Handling

Sorted Two Pointer problems that return **unique** combinations usually need duplicate skipping.

For 3Sum:

```java
for (int i = 0; i < n - 2; i++) {
    if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
    }

    int left = i + 1;
    int right = n - 1;

    // two-pointer search
}
```

After finding a valid pair, skip duplicate values before moving inward.

### Recognition

> **Sorted + unique pairs/triplets → Think duplicate skipping**

---

# 14. Two Pointers vs Sliding Window

They are related but not identical.

### Two Pointers

Pointers move according to a relationship such as:

```text
sum < target
sum > target
duplicate
comparison
partition condition
```

### Sliding Window

Usually:

```text
right expands
left shrinks
```

while maintaining a window property.

### Quick distinction

```text
Pair / triplet / sorted array
        → Two Pointers

Contiguous window + constraint
        → Sliding Window
```

Some problems can use both ideas. Identify the main invariant.

---

# 15. Two Pointers vs Prefix Sum

### Prefix Sum

Think:

> **Need cumulative information / subarray sum.**

Typical relationship:

```text
subarray sum = prefix[j] - prefix[i]
```

### Two Pointers

Think:

> **Can pointer movement eliminate a group of candidates?**

Typical:

```text
sorted array + pair target
```

### Quick distinction

```text
Subarray sum relationship
        → Prefix Sum

Sorted pair / rearrangement / comparison
        → Two Pointers
```

---

# 16. The Invariant — Most Important Interview Concept

Before moving pointers, define what remains true.

Example: 3Sum.

After sorting:

```text
nums[i] is fixed
left > i
right > left
```

Search space:

```text
[left ... right]
```

If:

```text
nums[i] + nums[left] + nums[right] < 0
```

then increasing `right` cannot help because the array is sorted.

Therefore:

```text
left++
```

> Don't say: **"I move left because Two Pointers does that."**
>
> Say: **"Moving left is safe because every value to the left of the current left pointer is even smaller, so it cannot increase the sum enough."**

---

# 17. Pointer Movement Rules

## Opposite pointers

```java
while (left < right) {
    if (condition) {
        left++;
    } else {
        right--;
    }
}
```

The condition must justify which side can be eliminated.

## Same-direction pointers

```java
int slow = 0;

for (int fast = 0; fast < n; fast++) {
    if (valid(nums[fast])) {
        nums[slow] = nums[fast];
        slow++;
    }
}
```

Think:

```text
fast → investigate
slow → maintain answer
```

## Three pointers

Typical:

```text
low / mid / high
```

Used for partitioning.

---

# 18. Complexity Pattern

```text
One pointer scan             → O(n)
Two-pointer scan             → O(n)
Sort + Two Pointers          → O(n log n)
Fix one + Two Pointers       → O(n²)
```

Space is often:

```text
O(1)
```

if the algorithm is in-place and the problem's output storage is excluded.

Remember that the language's sorting implementation may use additional stack/workspace.

---

# 19. Recognition Checklist

When reading a problem, ask:

```text
1. Is it an array / string / linked list?

2. Is it sorted?
   OR can I sort it safely?

3. Am I looking for:
   - pair?
   - triplet?
   - quadruplet?
   - comparison from both ends?

4. Is it asking me to:
   - remove duplicates?
   - filter?
   - rearrange?
   - partition?
   - merge?

5. Does it require O(1) extra space?

6. Can one pointer movement eliminate
   multiple candidates?

7. What does each pointer represent?

8. What invariant am I maintaining?

9. Why is moving this pointer safe?

10. How do I handle duplicates?
```

If several answers are **YES**, strongly consider Two Pointers.

---

# 20. High-Value LeetCode Set

For SDE2, master these rather than solving dozens of random Two Pointer problems.

### Foundation

1. **LC 167** — Two Sum II ⭐
2. **LC 125** — Valid Palindrome
3. **LC 26** — Remove Duplicates from Sorted Array
4. **LC 27** — Remove Element
5. **LC 283** — Move Zeroes

### Core

6. **LC 15** — 3Sum ⭐⭐⭐
7. **LC 11** — Container With Most Water ⭐
8. **LC 75** — Sort Colors ⭐⭐
9. **LC 88** — Merge Sorted Array
10. **LC 680** — Valid Palindrome II

### Advanced / Variations

11. **LC 18** — 4Sum
12. **LC 16** — 3Sum Closest
13. **LC 42** — Trapping Rain Water ⭐⭐⭐
14. **LC 881** — Boats to Save People
15. **LC 845** — Longest Mountain in Array

---

# 21. Final Mental Model

```text
                     TWO POINTERS
                          |
          +---------------+----------------+
          v               v                v
     Opposite Ends   Same Direction    Multiple Pointers
          |               |                |
      left/right      slow/fast       low/mid/high
          |               |                |
    pair / compare    filter/remove     partition
    target / reverse  duplicates         rearrange
```

### 10-second recognition rules

```text
Sorted + pair               → Two Pointers

Sorted + triplet            → Fix one + Two Pointers

Compare both ends           → left/right

Remove/filter in-place      → slow/fast

Two sorted arrays           → one pointer per array

Linked list + cycle/middle  → fast/slow

0/1/2 partition             → low/mid/high

Rearrange in-place          → partition pointers

Unique combinations         → sort + duplicate skipping

Need O(1) extra space       → consider Two Pointers
```

> **Core principle:** Two Pointers works when pointer movement lets you **discard a group of impossible candidates** instead of checking them one by one.

### Final interview question

> **"If I move this pointer, exactly which candidates become impossible, and why?"**
