# 3Sum — Why `left = i + 1` and Why Skip Duplicates

## 1. Core idea

After sorting:

```java
Arrays.sort(nums);
```

3Sum becomes:

```text
Fix one number nums[i]
        +
Find two numbers to the RIGHT of i
        =
0
```

So the problem becomes:

```text
nums[i] + nums[left] + nums[right] = 0
```

---

## 2. Why start `left` from `i + 1`?

Your code:

```java
int left = target + 1;
int right = n - 1;
```

Here `target` is actually the index `i` from the outer loop.

So:

```java
left = i + 1;
```

### Reason

The element at `i` is already fixed.

For example:

```text
index:  0   1   2   3   4   5
nums:  -4  -1  -1   0   1   2
        ↑
        i
```

If:

```java
i = 1;
```

then:

```text
nums[i] = -1
```

We need to find TWO OTHER elements.

Therefore:

```text
left = i + 1 = 2
```

and:

```text
right = n - 1
```

So we search only:

```text
[-1, 0, 1, 2]
     ↑        ↑
    left     right
```

### Why not start from `0`?

Because index `i` would potentially be used again.

Example:

```text
nums = [-1, 0, 1]
i = 0
```

If `left = 0`, we could accidentally consider:

```text
nums[0] + nums[0] + nums[2]
```

which is:

```text
-1 + -1 + 1 = -1
```

More importantly, conceptually the 3Sum algorithm requires three distinct ARRAY POSITIONS. Once `i` is fixed, the remaining two pointers should search the remaining portion of the array.

### Mental rule

> **Fix `i` → search only `i + 1 ... n - 1`.**

This also means you don't need:

```java
if (left == target) {
    left++;
}

if (right == target) {
    right--;
}
```

because `left` already starts after `target`.

---

# 3. Why skip duplicates in the outer loop?

You already have:

```java
if (i > 0 && nums[i] == nums[i - 1]) {
    continue;
}
```

Suppose:

```text
[-1, -1, 0, 1]
 ↑   ↑
 i   duplicate
```

Using the first `-1` and using the second `-1` can produce exactly the same VALUE triplets.

For example:

```text
i = 0 → -1 + 0 + 1 = 0
i = 1 → -1 + 0 + 1 = 0
```

So:

```java
if (i > 0 && nums[i] == nums[i - 1]) {
    continue;
}
```

means:

> "I already processed this value as the fixed element. Don't process the same value again."

---

# 4. Why skip duplicates inside the 2Sum?

This is the part that is easy to miss.

Suppose:

```text
nums = [-1, 0, 0, 0, 1]
```

Fix:

```text
nums[i] = -1
```

We need:

```text
left + right = 1
```

Eventually we find:

```text
-1 + 0 + 1 = 0
```

Now imagine there are multiple `0`s:

```text
[-1, 0, 0, 0, 1]
     ↑  ↑  ↑
```

If we simply do:

```java
left++;
```

we could produce:

```text
[-1, 0, 1]
[-1, 0, 1]
[-1, 0, 1]
```

These are identical VALUE triplets.

Therefore, after finding a valid pair, we skip all equal values.

Your code:

```java
int leftVal = nums[left];

while (left < right && nums[left] == leftVal) {
    left++;
}
```

means:

> "We already used this left value. Skip every occurrence of the same value."

Similarly:

```java
int rightVal = nums[right];

while (left < right && nums[right] == rightVal) {
    right--;
}
```

means:

> "We already used this right value. Skip every occurrence of the same value."

---

# 5. Why duplicate skipping happens AFTER finding a result

Important:

```java
if (sum == targetSum) {
    // First record the answer

    ans.add(list);

    // Then skip duplicates
}
```

You must not skip the current value before adding the triplet.

Example:

```text
[-1, 0, 1]
```

This is the first valid combination and must be added.

Only AFTER adding it do we move past duplicate values.

---

# 6. Your current logic

Your important section is:

```java
else {
    ArrayList<Integer> list = new ArrayList<>();

    list.add(nums[target]);
    list.add(nums[left]);
    list.add(nums[right]);

    Collections.sort(list);

    ans.add(list);

    int leftVal = nums[left];

    while (left < right && nums[left] == leftVal) {
        left++;
    }

    int rightVal = nums[right];

    while (left < right && nums[right] == rightVal) {
        right--;
    }
}
```

This works because:

```text
1. Find valid pair
2. Add triplet
3. Skip duplicate left values
4. Skip duplicate right values
```

---

# 7. Example walkthrough

Input:

```text
[-1, 0, 1, 2, -1, -4]
```

After sorting:

```text
[-4, -1, -1, 0, 1, 2]
```

### i = 0

```text
-4
```

Search:

```text
left = 1
right = 5
```

No valid triplet.

---

### i = 1

```text
-1
```

Search:

```text
left = 2
right = 5
```

Values:

```text
-1 + (-1) + 2 = 0
```

Add:

```text
[-1, -1, 2]
```

Then duplicate values are skipped.

Eventually:

```text
-1 + 0 + 1 = 0
```

Add:

```text
[-1, 0, 1]
```

---

### i = 2

```text
nums[2] == nums[1]
```

Both are:

```text
-1
```

Therefore:

```java
if (i > 0 && nums[i] == nums[i - 1]) {
    continue;
}
```

Skip `i = 2`.

Otherwise we would search with `-1` again and produce:

```text
[-1, -1, 2]
[-1, 0, 1]
```

again.

---

# 8. The three duplicate rules to remember

### Rule 1 — Fixed element

```java
if (i > 0 && nums[i] == nums[i - 1])
    continue;
```

Prevents duplicate triplets caused by repeating `nums[i]`.

### Rule 2 — Left pointer

After finding a result:

```java
int leftVal = nums[left];

while (left < right && nums[left] == leftVal)
    left++;
```

Prevents duplicate triplets caused by repeating the left value.

### Rule 3 — Right pointer

```java
int rightVal = nums[right];

while (left < right && nums[right] == rightVal)
    right--;
```

Prevents duplicate triplets caused by repeating the right value.

---

# 9. Interview mental model

Think of 3Sum as:

```text
SORT
  ↓
FIX i
  ↓
left = i + 1
right = n - 1
  ↓
two-pointer search
  ↓
found?
  ↓
add triplet
  ↓
skip duplicate left/right values
```

The most important sentence:

> **`i` chooses the first value. `left` and `right` search only after `i`. Duplicate values are skipped because 3Sum asks for unique VALUE triplets, not unique index combinations.**

---

## 10. Small cleanup

Because the array is already sorted and:

```text
target < left < right
```

you don't actually need:

```java
Collections.sort(list);
```

You can directly do:

```java
List<Integer> list = new ArrayList<>();

list.add(nums[target]);
list.add(nums[left]);
list.add(nums[right]);
```

The triplet is already sorted.
