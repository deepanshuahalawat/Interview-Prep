# Sliding Window — 15-Minute Revision

## Pattern Recognition + Attack Plan

> **Maintain a contiguous window and move two boundaries forward without going backward.**

---

## 1. What is Sliding Window?

Used for problems involving a **contiguous** portion of an array/string.

```text
[left ........ right]
```

Instead of checking every subarray in `O(n²)`, maintain one window and try to process it in `O(n)`.

---

## 2. Pattern Recognition

Strong signals:

- subarray / substring
- contiguous / consecutive
- longest / shortest / minimum / maximum
- window
- at most K / exactly K
- no repeating characters
- distinct characters
- contains all characters
- sum ≥ K / sum ≤ K

Strong trigger:

> **contiguous + optimize/count + constraint → consider Sliding Window**

---

## 3. Subarray vs Subsequence

### Subarray

Must be contiguous:

```text
[1, 2, 3, 4, 5]
[2, 3, 4] ✓
```

### Subsequence

Does not need to be contiguous:

```text
[1, 3, 5]
```

Mental trigger:

```text
CONTIGUOUS → Sliding Window candidate
```

---

## 4. Fixed vs Variable Window

### Fixed-size

Window size is explicitly `k`.

Example:

> Maximum sum of a subarray of size `k`.

```java
for(int right = 0; right < n; right++) {

    // add nums[right]

    if(right >= k) {
        // remove nums[right-k]
    }

    if(right >= k-1) {
        // process window
    }
}
```

Think:

```text
add right
remove element leaving from left
process when size == k
```

### Variable-size

Window size changes according to a condition.

```java
int left = 0;

for(int right = 0; right < n; right++) {

    // add right element

    while(/* invalid */) {
        // remove nums[left]
        left++;
    }

    // update answer
}
```

---

## 5. The Most Important Question

Ask:

> **What information does my current window need to maintain?**

Common state:

```text
sum
frequency of characters
frequency of numbers
number of distinct elements
number of zeros
number of odd numbers
number of violations
```

Examples:

```text
Minimum Size Subarray Sum → sum
No Repeating Characters  → set/map
At Most K Distinct        → HashMap
```

---

## 6. Variable Window — Core Attack Plan

```text
1. Expand right
2. Add right element
3. Check constraint
4. If invalid → shrink left
5. Repeat until valid
6. Update answer
```

Generic:

```java
int left = 0;

for(int right = 0; right < n; right++) {

    add(nums[right]);

    while(INVALID) {
        remove(nums[left]);
        left++;
    }

    updateAnswer();
}
```

---

## 7. Maximum-Length Problems

Example:

> Longest subarray satisfying condition X.

```java
int left = 0;
int maxLen = 0;

for(int right = 0; right < n; right++) {

    add(nums[right]);

    while(INVALID) {
        remove(nums[left]);
        left++;
    }

    maxLen = Math.max(maxLen, right-left+1);
}
```

Mental model:

```text
Expand
  ↓
Invalid?
  ↓ yes
Shrink until valid
  ↓
Take biggest valid window
```

### Rule

> **MAXIMUM → shrink only when invalid.**

---

## 8. Example: Longest Sum ≤ K

Assume positive numbers.

```java
int left = 0;
int sum = 0;
int maxLen = 0;

for(int right = 0; right < nums.length; right++) {

    sum += nums[right];

    while(sum > K) {
        sum -= nums[left++];
    }

    maxLen = Math.max(maxLen, right-left+1);
}
```

Pattern:

```text
INVALID → shrink
VALID   → maximize
```

---

## 9. Minimum-Length Problems

Example:

> Minimum length subarray with sum ≥ target.

```java
int left = 0;
int sum = 0;
int minLen = Integer.MAX_VALUE;

for(int right = 0; right < nums.length; right++) {

    sum += nums[right];

    while(sum >= target) {

        minLen = Math.min(minLen, right-left+1);

        sum -= nums[left++];
    }
}
```

Mental model:

```text
VALID
  ↓
record answer
  ↓
shrink
  ↓
still valid?
  ↓
record again
  ↓
shrink again
```

### Rule

> **MINIMUM → shrink while valid.**

---

## 10. Maximum vs Minimum

| Goal | Shrink when | Update |
|---|---|---|
| Maximum length | Invalid | After shrinking |
| Minimum length | Valid | Before/while shrinking |

### Maximum

```java
while(INVALID)
    shrink();

max = Math.max(max, windowSize);
```

### Minimum

```java
while(VALID) {
    min = Math.min(min, windowSize);
    shrink();
}
```

---

## 11. Fixed Window Template

Example:

> Maximum sum of subarray of size `k`.

```java
int left = 0;

for(int right = 0; right < n; right++) {

    add(nums[right]);

    if(right-left+1 > k) {
        remove(nums[left]);
        left++;
    }

    if(right-left+1 == k) {
        // process window
    }
}
```

Think:

```text
add right
→ if too large, remove left
→ process when size == k
```

---

## 12. Variable Window + HashMap

Example:

> Longest substring with at most K distinct characters.

```java
Map<Character,Integer> map = new HashMap<>();
int left = 0;

for(int right = 0; right < s.length(); right++) {

    char c = s.charAt(right);
    map.put(c, map.getOrDefault(c, 0) + 1);

    while(map.size() > k) {

        char x = s.charAt(left++);

        map.put(x, map.get(x) - 1);

        if(map.get(x) == 0) {
            map.remove(x);
        }
    }

    maxLen = Math.max(maxLen, right-left+1);
}
```

Structure:

```text
window state
     ↓
constraint violated?
     ↓
shrink
     ↓
restore validity
     ↓
update answer
```

---

## 13. HashSet vs HashMap

Use `Set` when you only care:

> Is this element present?

```java
Set<Character> set = new HashSet<>();
```

Use `HashMap` when you care:

> How many times does this element occur?

```java
Map<Character,Integer> map = new HashMap<>();
```

Examples:

```text
No repeating characters → Set/Map
At most K distinct       → Map
Frequency requirements   → Map
```

---

## 14. "At Most K" — Huge Signal

Whenever you see:

> longest subarray with **at most K**

or:

> substring with **at most K distinct**

think:

```text
Sliding Window
```

Example:

```text
at most 2 distinct
→ invalid when distinct > 2
```

```java
while(map.size() > 2)
    shrink();
```

Mental trigger:

```text
AT MOST K
→ maintain valid window
→ shrink when > K
```

---

## 15. "At Least K"

Example:

> Minimum length subarray with sum at least K.

For positive numbers:

```text
valid = sum >= K
```

Therefore:

```java
while(sum >= K) {
    answer = ...;
    shrink();
}
```

Useful clue:

```text
AT MOST → often maximum window
AT LEAST → often minimum window
```

Not universal, but a strong signal.

---

## 16. "Exactly K"

Problems asking:

> exactly K distinct elements

can often be transformed:

```text
exactly K
=
atMost(K) - atMost(K-1)
```

Example:

> Number of subarrays with exactly K distinct elements.

Use:

```text
countAtMost(K) - countAtMost(K-1)
```

Important advanced Sliding Window pattern.

---

## 17. Counting Windows

For counting problems, don't necessarily enumerate every window.

Example:

> Number of subarrays with at most K distinct elements.

Once you have:

```text
[left ........ right]
```

the number of valid subarrays ending at `right` is:

```text
right-left+1
```

Because:

```text
[left...right]
[left+1...right]
[left+2...right]
...
[right...right]
```

Therefore:

```java
count += right-left+1;
```

This works when the condition is monotonic.

---

## 18. Critical Requirement: Monotonicity

Sliding Window works when expanding/shrinking has a **predictable effect** on the constraint.

With positive numbers:

```text
expand → sum increases
shrink → sum decreases
```

Perfect.

With negative numbers, this can break:

```text
expand → sum may increase OR decrease
```

So don't blindly use:

```text
sum > K → shrink
sum < K → expand
```

---

## 19. Example: Subarray Sum Equals K

If numbers can be negative:

```text
[1, -1, 1]
```

sum is not monotonic.

Usually use:

```text
Prefix Sum + HashMap
```

Mental trigger:

```text
SUM + arbitrary positive/negative values
        ↓
don't force Sliding Window
        ↓
consider Prefix Sum
```

---

## 20. Positive Numbers Are Special

If:

```text
nums[i] > 0
```

Sliding Window becomes much more attractive for sum constraints.

Because:

```text
expand → sum ↑
shrink → sum ↓
```

Classic:

> Minimum Size Subarray Sum

---

## 21. Attack Plan — Step by Step

When you get a new problem:

### Step 1 — Is it contiguous?

```text
subarray or substring?
```

If no → probably not Sliding Window.

### Step 2 — Fixed size?

```text
window size explicitly K?
```

If yes → Fixed Window.

If no → Variable Window.

### Step 3 — Identify state

```text
sum?
frequency?
distinct count?
zeros?
matches?
```

### Step 4 — Define valid condition

Example:

```text
at most K distinct
→ map.size() <= K
```

### Step 5 — Define invalid condition

```text
map.size() > K
```

### Step 6 — Identify objective

```text
MAX / MIN / COUNT
```

### Step 7 — Choose shrink strategy

```text
MAX   → shrink while INVALID
MIN   → shrink while VALID
COUNT → often count right-left+1
```

---

## 22. Universal Variable Window Template

Start with:

```java
int left = 0;

for(int right = 0; right < n; right++) {

    // ADD right element

    while(/* window invalid */) {

        // REMOVE left element
        left++;
    }

    // UPDATE ANSWER
}
```

---

## 23. Minimum Template

```java
int left = 0;

for(int right = 0; right < n; right++) {

    add(nums[right]);

    while(VALID) {

        answer = Math.min(answer,
                          right-left+1);

        remove(nums[left]);
        left++;
    }
}
```

---

## 24. Maximum Template

```java
int left = 0;

for(int right = 0; right < n; right++) {

    add(nums[right]);

    while(INVALID) {

        remove(nums[left]);
        left++;
    }

    answer = Math.max(answer,
                      right-left+1);
}
```

---

## 25. Counting Template

For monotonic conditions:

```java
int left = 0;
long count = 0;

for(int right = 0; right < n; right++) {

    add(nums[right]);

    while(INVALID) {
        remove(nums[left]);
        left++;
    }

    count += right-left+1;
}
```

Why?

```text
valid window:
[left ........ right]

Every starting point from left to right
creates a valid subarray ending at right.
```

---

## 26. Sliding Window Maximum

Problem:

> Maximum in every window of size K.

Fixed-size window, but maintaining the maximum efficiently is the challenge.

```text
PriorityQueue → O(n log k)
Monotonic Deque → O(n)
```

Deque maintains candidates in decreasing order:

```text
largest
  ↓
[9, 7, 5, 3]
```

When a larger value arrives, smaller values behind it can be removed because they can never become the maximum while the larger value remains in the window.

---

## 27. Why O(n) Is Possible

Seeing:

```java
for(...)
    while(...)
```

does **not** automatically mean `O(n²)`.

If `right` moves at most `n` times and `left` also moves at most `n` times:

```text
O(n + n) = O(n)
```

This is amortized linear time.

Key principle:

> **Each element enters the window once and leaves the window at most once.**

---

## 28. Common Mistakes

### Mistake 1 — Negative numbers

Don't automatically use Sliding Window for sum constraints with arbitrary negative values.

### Mistake 2 — `if` instead of `while`

Often wrong:

```java
if(INVALID)
    shrink();
```

Usually:

```java
while(INVALID)
    shrink();
```

because one removal may not restore validity.

### Mistake 3 — Wrong answer-update timing

Minimum:

```java
while(VALID) {
    update;
    shrink();
}
```

Maximum:

```java
while(INVALID)
    shrink();

update;
```

### Mistake 4 — Forgetting removal

If moving `left`, remove its contribution from the window state.

### Mistake 5 — Frequency reaches zero

```java
map.put(x, map.get(x)-1);

if(map.get(x) == 0)
    map.remove(x);
```

Otherwise `map.size()` can be wrong.

---

## 29. Two Pointer vs Sliding Window

Sliding Window is basically a specialized form of Two Pointers.

```text
Two pointers:
left, right

Sliding Window:
left, right
+ maintain window state
+ expand/shrink based on constraint
```

Relationship:

```text
Two Pointer
     ↓
Sliding Window
```

---

## 30. Common Problems → Pattern

| Problem type | Pattern |
|---|---|
| Max sum of size K | Fixed Window |
| Average of size K | Fixed Window |
| First negative in every K window | Fixed Window |
| Minimum Size Subarray Sum | Variable Window |
| Longest substring without repeat | Variable + Set/Map |
| Longest substring at most K distinct | Variable + Map |
| Max consecutive ones with K flips | Variable Window |
| Minimum Window Substring | Variable + Frequency |
| Subarrays with at most K distinct | Variable + Map + Count |
| Exactly K distinct | AtMost(K) - AtMost(K-1) |
| Sliding Window Maximum | Deque |
| Subarray Sum Equals K | Prefix Sum + Map |

---

## 31. Biggest Recognition Shortcut

When you see:

> **Longest/shortest contiguous subarray/substring satisfying a condition**

ask:

```text
Can I maintain this condition while moving
left and right only forward?
```

If yes:

```text
Sliding Window
```

If no:

```text
Look for Prefix Sum / HashMap / DP / Binary Search / Deque
```

---

## 32. Interview Attack Card

Before coding:

```text
CONTIGUOUS?
    ↓
YES
    ↓
FIXED SIZE?
 ┌──┴──┐
YES   NO
 ↓     ↓
Fixed  Variable
Window Window
       ↓
   What state?
       ↓
sum / map / set / count
       ↓
What objective?
       ↓
MAX / MIN / COUNT
```

Then:

```text
MAX:
    expand
    shrink while invalid
    update max

MIN:
    expand
    while valid:
        update min
        shrink

COUNT:
    expand
    shrink while invalid
    count += window size
```

---

## 33. Final Mental Model

```text
                CONTIGUOUS
                     ↓
              SLIDING WINDOW?
                     ↓
             ┌───────┴───────┐
             ↓               ↓
           FIXED           VARIABLE
             ↓               ↓
          size K       constraint-based
                             ↓
                      maintain STATE
                             ↓
                    ┌────────┼────────┐
                    ↓        ↓        ↓
                   MAX       MIN     COUNT
                    ↓        ↓        ↓
                invalid     valid    invalid
                 → shrink   → shrink → shrink
                    ↓        ↓        ↓
                  update   update   + window size
```

---

## 34. Five Lines to Memorize

```text
1. Contiguous → consider Sliding Window.
2. Fixed K → fixed-size window.
3. Variable size → expand right, adjust left.
4. MAX → shrink while INVALID, then update.
5. MIN → while VALID, update and shrink.
```

### Big Exception

> **If the window condition isn't monotonic, don't force Sliding Window.**

For example, arbitrary positive/negative sum problems often point toward:

```text
Prefix Sum + HashMap
```
