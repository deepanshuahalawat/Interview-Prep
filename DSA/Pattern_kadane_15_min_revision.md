# Kadane's Algorithm — 15-Minute Revision

## Pattern Recognition + Attack Plan

> **Kadane = maintain the best contiguous-subarray result ending at the current index, then use it to build the result for the next index.**

The goal is not to memorize Kadane's code. Understand the state transition.

---

## 1. How to Identify Kadane

Look for problems involving:

- subarray
- contiguous
- maximum sum
- minimum sum
- maximum product
- maximum absolute sum
- one deletion
- circular subarray
- choosing a contiguous segment while optimizing some value

Strong signal:

```text
CONTIGUOUS SUBARRAY
        +
OPTIMIZATION
        ↓
Think Kadane / Kadane variation
```

---

## 2. Core Kadane Idea

At index `i`, let `x = nums[i]`.

There are two basic choices:

1. **Start a new subarray:** `x`
2. **Extend the best subarray ending at `i-1`:** `previousBest + x`

Therefore:

```text
currentBest =
    max(x,
        previousBest + x)
```

The key state is:

```text
BEST SUBARRAY RESULT ENDING AT CURRENT INDEX
```

Not the best subarray seen anywhere.

---

## 3. Why "Ending Here" Matters

Example:

```text
nums = [-2, 3, -1, 5]
```

Best subarray ending at each position:

```text
-2

max(3, -2 + 3) = 3

max(-1, 3 + -1) = 2

max(5, 2 + 5) = 7
```

Global answer = `7`, from:

```text
[3, -1, 5]
```

---

## 4. Standard Kadane Template

```java
int current = nums[0];
int answer = nums[0];

for(int i = 1; i < nums.length; i++) {

    current = Math.max(nums[i],
                       current + nums[i]);

    answer = Math.max(answer, current);
}

return answer;
```

Two states:

```text
current → best result ending at current index
answer  → best result seen anywhere
```

---

## 5. Kadane Attack Plan

When you suspect Kadane:

### Step 1
Confirm it is a **contiguous subarray** problem.

### Step 2
Ask:

> What is the best result ending at index `i`?

### Step 3
Identify the choices:

```text
START NEW
OR
EXTEND PREVIOUS
```

### Step 4
Write the recurrence.

For maximum sum:

```text
current =
max(nums[i],
    current + nums[i])
```

### Step 5
Maintain the global answer:

```text
answer = max(answer, current)
```

### Step 6
Check special conditions:

```text
product?
deletion?
circular?
minimum?
absolute value?
```

---

## 6. Maximum Subarray Sum

Classic Kadane:

```java
int current = nums[0];
int maxSum = nums[0];

for(int i = 1; i < nums.length; i++) {

    current = Math.max(nums[i],
                       current + nums[i]);

    maxSum = Math.max(maxSum, current);
}
```

Complexity:

```text
Time  = O(n)
Space = O(1)
```

---

## 7. Minimum Subarray Sum

Reverse the optimization:

```java
int current = nums[0];
int minSum = nums[0];

for(int i = 1; i < nums.length; i++) {

    current = Math.min(nums[i],
                       current + nums[i]);

    minSum = Math.min(minSum, current);
}
```

Mental transformation:

```text
Maximum Kadane → max()
Minimum Kadane → min()
```

---

## 8. Maximum Product Subarray

Product introduces:

```text
negative × negative = positive
```

Therefore the smallest product can suddenly become the largest.

Example:

```text
[-2, 3, -4]
```

At `-4`:

```text
minimum × negative
        ↓
(-2) × (-4)
        ↓
8
```

So maintain:

```text
maxProd
minProd
```

---

## 9. Maximum Product State

For current value `x`:

```text
x
x * previousMin
x * previousMax
```

Therefore:

```java
newMin = Math.min(x,
           Math.min(x * minProd,
                    x * maxProd));

newMax = Math.max(x,
           Math.max(x * minProd,
                    x * maxProd));
```

Then:

```java
minProd = newMin;
maxProd = newMax;

answer = Math.max(answer, maxProd);
```

Important:

> Calculate `newMin` and `newMax` using the **old** `minProd` and `maxProd` before overwriting them.

Memory line:

> **Product + negatives → keep both min and max.**

---

## 10. Maximum Subarray Sum With One Deletion

Problem:

> Find maximum subarray sum if you may delete at most one element.

Maintain two states:

```text
noDelete
oneDelete
```

### No deletion

```text
noDelete =
    max(x,
        oldNoDelete + x)
```

### One deletion

Two choices:

```text
1. Delete current x
2. Keep current x; deletion happened earlier
```

So:

```text
oneDelete =
    max(oldNoDelete,
        oldOneDelete + x)
```

Important:

> When deleting the current element, use the **old** `noDelete` state.

Mental model:

```text
current element
      ↓
   ┌──┴──┐
   ↓     ↓
 keep   delete
```

This is a classic **state expansion** of Kadane.

---

## 11. Maximum Absolute Sum

Problem:

> Maximum absolute value of the sum of a subarray.

Need both:

```text
maximum subarray sum
minimum subarray sum
```

Because:

```text
maximum absolute sum
=
max(maxSum, abs(minSum))
```

Mental model:

```text
absolute value
      ↓
positive extreme OR negative extreme
      ↓
MAX SUM + MIN SUM
```

---

## 12. Maximum Circular Subarray Sum

A circular subarray can wrap around the end.

Example:

```text
[5, -3, 5]
```

Circular maximum:

```text
5 + 5 = 10
```

There are two cases.

### Case 1 — Normal subarray

```text
maxKadane
```

### Case 2 — Wrapping subarray

A wrapping subarray is:

```text
totalSum - minimumSubarraySum
```

Therefore:

```text
wrapMax =
    totalSum - minKadane
```

Final:

```text
answer =
    max(maxKadane,
        totalSum - minKadane)
```

Mental model:

> **To maximize the wrapping part, remove the worst contiguous middle part.**

---

## 13. Circular Subarray — Edge Case

If all numbers are negative:

```text
[-3, -2, -5]
```

blindly using:

```text
totalSum - minKadane
```

can effectively represent an empty subarray.

For the standard non-empty-subarray problem:

```java
if(maxKadane < 0)
    return maxKadane;
```

Otherwise:

```java
return Math.max(maxKadane,
                totalSum - minKadane);
```

---

## 14. All-Negative Arrays

Do not blindly initialize:

```java
current = 0;
answer = 0;
```

if the problem requires a **non-empty** subarray.

Example:

```text
[-5, -2, -8]
```

Answer:

```text
-2
```

not `0`.

Safer initialization:

```java
int current = nums[0];
int answer = nums[0];
```

Important interview edge case.

---

## 15. Why Starting Fresh Works

Suppose:

```text
current < 0
```

and next value is `x`.

Compare:

```text
x
```

with:

```text
current + x
```

Since `current` is negative:

```text
current + x < x
```

So Kadane automatically chooses:

```text
x
```

This is why:

```java
Math.max(nums[i],
         current + nums[i])
```

already handles restarting.

---

## 16. Classic Alternative Kadane Form

You may see:

```java
current += nums[i];

if(current < 0)
    current = 0;

maxSum = Math.max(maxSum, current);
```

This is common, but needs special handling for all-negative arrays.

The safer general form is:

```java
current = Math.max(nums[i],
                   current + nums[i]);

answer = Math.max(answer, current);
```

For interviews, this version makes the state transition explicit.

---

## 17. Kadane Is Really DP

Kadane can be viewed as a space-optimized DP.

Define:

```text
dp[i] =
best subarray sum ending at index i
```

Recurrence:

```text
dp[i] =
max(nums[i],
    dp[i-1] + nums[i])
```

To calculate `dp[i]`, we only need:

```text
dp[i-1]
```

Therefore:

```text
DP array
   ↓
one variable
```

This explains:

```text
Time  = O(n)
Space = O(1)
```

---

## 18. General Kadane Pattern

The real pattern is:

```text
DP state:
best result ending at i
```

Then ask:

> How can the current element interact with the previous state?

Usually:

```text
START NEW
OR
EXTEND OLD
```

For standard sum:

```text
max(x, old + x)
```

For minimum:

```text
min(x, old + x)
```

For product:

```text
x
x × min
x × max
```

For deletion:

```text
no deletion
one deletion
```

This is the deeper pattern behind Kadane variations.

---

## 19. Special Condition Checklist

When you identify Kadane, immediately check:

### Can values be negative?

```text
Don't initialize answer to 0 blindly.
```

### Is it product?

```text
Track MIN + MAX.
```

### Is one deletion allowed?

```text
Track NO DELETE + ONE DELETE.
```

### Is it absolute sum?

```text
Track MAX SUM + MIN SUM.
```

### Is it circular?

```text
max(normalMax,
    total - normalMin)
```

Handle all-negative arrays.

---

## 20. Kadane vs Sliding Window

### Sliding Window

Usually maintains a window based on a **constraint**:

```text
at most K
sum >= target
distinct <= K
```

and moves:

```text
left / right
```

### Kadane

Usually asks for an **optimal contiguous subarray**, especially sum-like objectives.

It maintains:

```text
best result ending here
```

Mental distinction:

```text
CONSTRAINT
   ↓
Sliding Window

BEST CONTIGUOUS SUBARRAY VALUE
   ↓
Kadane
```

---

## 21. Kadane vs Prefix Sum

### Prefix Sum

Useful when you need:

```text
range sum
sum == K
sum divisible by K
```

Often:

```text
Prefix Sum + HashMap
```

### Kadane

Useful when:

```text
maximum/minimum subarray sum
```

and the objective is to optimize the contiguous segment.

Mental distinction:

```text
Need RANGE SUM information?
    ↓
Prefix Sum

Need BEST contiguous SUM?
    ↓
Kadane
```

---

## 22. Common Problems → Pattern

| Problem | Pattern |
|---|---|
| Maximum Subarray | Kadane |
| Minimum Subarray Sum | Reverse Kadane |
| Maximum Product Subarray | Kadane + Min/Max |
| Maximum Subarray Sum With One Deletion | Kadane + 2 states |
| Maximum Absolute Sum | Max Kadane + Min Kadane |
| Maximum Circular Subarray | Max Kadane + Min Kadane |
| Maximum Subarray with extra constraints | May become DP/Sliding Window |

---

## 23. Complexity

Standard Kadane:

```text
Time  = O(n)
Space = O(1)
```

Why?

```text
Each element is processed once.
```

Kadane variations can also remain `O(1)` extra space when only a constant number of states is required.

---

## 24. Interview Attack Card

When you see:

```text
CONTIGUOUS SUBARRAY
        +
MAX / MIN / OPTIMIZE
```

ask:

```text
What is the BEST result ending here?
        ↓
Can I:
    START NEW
       OR
    EXTEND PREVIOUS?
        ↓
Write recurrence
        ↓
Track GLOBAL answer
```

Then check:

```text
PRODUCT?
→ min + max

ONE DELETION?
→ 2 states

ABSOLUTE?
→ max + min

CIRCULAR?
→ max + (total - min)

ALL NEGATIVE?
→ don't allow empty subarray
```

---

## 25. Five Lines to Memorize

```text
1. Kadane = best contiguous result ending at current index.
2. At every index: START NEW or EXTEND PREVIOUS.
3. Keep a global answer separate from the current state.
4. Product → keep MIN + MAX because negatives flip them.
5. Circular → max(normalMax, totalSum - normalMin), with all-negative check.
```

---

## 26. Final Mental Model

```text
              CONTIGUOUS SUBARRAY
                       ↓
                 OPTIMIZATION?
                       ↓
                     KADANE
                       ↓
            BEST RESULT ENDING HERE
                       ↓
               ┌───────┴───────┐
               ↓               ↓
          START NEW        EXTEND OLD
               └───────┬───────┘
                       ↓
                CURRENT STATE
                       ↓
                 GLOBAL ANSWER
```

### Variations

```text
SUM
 ↓
max(current, current + x)

MIN SUM
 ↓
min(current, current + x)

PRODUCT
 ↓
min + max

ONE DELETION
 ↓
noDelete + oneDelete

ABSOLUTE SUM
 ↓
maxSum + minSum

CIRCULAR
 ↓
max(normalMax, total - normalMin)
```

---

## 27. Final Memory Rule

> **The core of Kadane is NOT “reset when sum becomes negative.”**
>
> The deeper pattern is:
>
> **`best ending here = best of START NEW vs EXTEND PREVIOUS`.**

---

## 26. Circular Subarray — Two Cases

For a circular array, the maximum subarray can be in one of two forms.

### Case 1 — Normal Subarray

The maximum subarray does **not wrap around** the end.

Use normal Kadane:

```java
maxSum
```

### Case 2 — Circular / Wrapping Subarray

The maximum subarray wraps from the end back to the beginning.

Instead of directly finding the wrapping subarray:

```text
wrapping sum = total sum - minimum subarray sum
```

So:

```text
circularSum = totalSum - minSum
```

Example:

```text
nums = [5, -3, 5]

totalSum = 7
minSum   = -3

circularSum = 7 - (-3)
            = 10

Wrapping subarray = [5, 5]
```

### Java Code

```java
class Solution {
    public int maxSubarraySumCircular(int[] nums) {

        int total = 0;

        int maxSum = nums[0];
        int currMax = nums[0];

        int minSum = nums[0];
        int currMin = nums[0];

        for (int i = 0; i < nums.length; i++) {

            total += nums[i];

            // Normal maximum subarray
            if (i > 0) {
                currMax = Math.max(nums[i], currMax + nums[i]);
                maxSum = Math.max(maxSum, currMax);

                // Minimum subarray
                currMin = Math.min(nums[i], currMin + nums[i]);
                minSum = Math.min(minSum, currMin);
            }
        }

        // Case 1: normal maximum subarray
        // Case 2: maximum wrapping subarray
        int circularSum = total - minSum;

        // All elements are negative.
        // total - minSum would represent an empty subarray.
        if (maxSum < 0)
            return maxSum;

        return Math.max(maxSum, circularSum);
    }
}
```

### Why the All-Negative Check Matters

For:

```text
[-3, -2, -5]
```

Normal Kadane gives:

```text
maxSum = -2
```

But:

```text
totalSum - minSum = 0
```

`0` is wrong because it represents selecting **no elements**.

Therefore:

```java
if (maxSum < 0)
    return maxSum;
```

### Circular Subarray Memory Rule

```text
CIRCULAR MAX SUBARRAY
        |
        +--> Normal case
        |      maxSum
        |
        +--> Wrapping case
               totalSum - minSum

answer = max(maxSum, totalSum - minSum)

BUT:
all negative → return maxSum
```

### Interview Attack

When you see:

```text
MAXIMUM SUBARRAY
+
CIRCULAR ARRAY
```

Immediately think:

```text
1. Find normal maximum → Kadane
2. Find minimum subarray → Reverse Kadane
3. total - minimum = wrapping maximum
4. Take max of the two
5. Handle all-negative array
```

Time:

```text
O(n)
```

Space:

```text
O(1)
```

