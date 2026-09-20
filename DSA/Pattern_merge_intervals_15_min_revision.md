# Merge Intervals Pattern — 15-Minute Quick Read

## 1. Pattern Recognition

Think **Intervals** when the problem contains:

- `[start, end]`
- overlapping intervals
- merging ranges
- inserting an interval
- meeting schedules
- intersections
- minimum rooms/resources
- free/busy time
- removing overlaps
- maximum simultaneous events

### First question

Ask:

> **Are the intervals sorted by start time?**

If not, the first step is usually:

```java
Arrays.sort(intervals,
    (a, b) -> Integer.compare(a[0], b[0]));
```

---

## 2. Core Merge Interval Pattern

Given:

```text
[1,3], [2,6], [8,10], [9,12]
```

Compare the current interval with the last merged interval.

### Overlap

```text
current.start <= last.end
```

Merge:

```java
last.end = max(last.end, current.end)
```

### No overlap

```text
current.start > last.end
```

Add current interval as a new interval.

---

## 3. Standard Merge Template

```java
Arrays.sort(intervals,
    (a, b) -> Integer.compare(a[0], b[0]));

ArrayList<int[]> ans = new ArrayList<>();

for (int[] curr : intervals) {

    if (ans.isEmpty() ||
        curr[0] > ans.get(ans.size() - 1)[1]) {

        ans.add(new int[]{curr[0], curr[1]});

    } else {

        int[] last = ans.get(ans.size() - 1);

        last[1] = Math.max(last[1], curr[1]);
    }
}

return ans.toArray(new int[ans.size()][]);
```

Mental model:

```text
SORT → compare with last merged
     → overlap? extend
     → otherwise add
```

---

## 4. Main Problem Types

| Problem asks | Pattern |
|---|---|
| Merge overlapping intervals | Sort + Merge |
| Insert interval | Insert + Merge / One-pass |
| Intersection of two lists | Two Pointers |
| Can attend all meetings? | Sort + Adjacent comparison |
| Minimum meeting rooms | Min Heap / Sweep Line |
| Maximum simultaneous meetings | Sweep Line |
| Remove minimum overlapping intervals | Sort by END + Greedy |
| Meeting free time | Merge + gaps |
| Employee free time | Merge all intervals |

---

## 5. Type 1 — Merge Intervals

LC 56 — Merge Intervals.

```text
[1,3], [2,6], [8,10], [9,12]
→ [1,6], [8,12]
```

Approach:

```text
Sort by START
→ compare current with last merged
→ overlap → merge
→ no overlap → add
```

Complexity:

```text
Time  = O(n log n)
Space = O(n) output
```

---

## 6. Type 2 — Insert Interval

LC 57 — Insert Interval.

Example:

```text
intervals = [[1,3], [6,9]]
new       = [2,5]

→ [[1,5], [6,9]]
```

### Easy / Reusable Approach

```text
Insert new interval at correct position
        ↓
Run Merge Intervals
```

Valid approach, especially when you already have LC56.

### One-Pass Approach

Divide intervals into:

```text
1. Completely before new interval
2. Overlapping with new interval
3. Completely after new interval
```

Key conditions:

```java
interval.end < newInterval.start      // before

interval.start <= newInterval.end     // overlap
```

Then merge overlapping intervals and append the remaining intervals.

---

## 7. Type 3 — Interval Intersection

LC 986 — Interval List Intersections.

Two sorted lists:

```text
A = [[0,2], [5,10]]
B = [[1,5], [8,12]]
```

Result:

```text
[1,2]
[5,5]
[8,10]
```

Pattern:

> **Two Pointers**, not Merge Intervals.

For:

```text
A = [aStart,aEnd]
B = [bStart,bEnd]
```

Intersection:

```java
int start = Math.max(aStart, bStart);
int end   = Math.min(aEnd, bEnd);
```

If:

```java
start <= end
```

add the intersection.

Move the interval that ends first:

```java
if (aEnd < bEnd)
    i++;
else
    j++;
```

Memory rule:

> **Intersection = max starts + min ends**

> **Move smaller end**

Complexity:

```text
O(n + m)
```

---

## 8. Type 4 — Meeting Rooms

Question:

> Can one person attend all meetings?

Example:

```text
[0,30]
[5,10]
[15,20]
```

Sort by START, then check adjacent intervals.

If:

```java
current.start < previous.end
```

there is a conflict.

```java
Arrays.sort(intervals,
    (a, b) -> Integer.compare(a[0], b[0]));

for (int i = 1; i < intervals.length; i++) {

    if (intervals[i][0] < intervals[i - 1][1])
        return false;
}

return true;
```

Important boundary:

```text
[0,10]
[10,20]
```

Usually no conflict because the first meeting ends when the second starts.

Therefore use:

```java
current.start < previous.end
```

not:

```java
current.start <= previous.end
```

---

# 9. Meeting Rooms II

Question:

> How many rooms are required to hold all meetings?

Example:

```text
[0,30]
[5,10]
[15,20]
```

Answer:

```text
2
```

## Approach 1 — Min Heap

Sort by START.

Keep meeting END times in a min heap.

```java
Arrays.sort(intervals,
    (a, b) -> Integer.compare(a[0], b[0]));

PriorityQueue<Integer> pq = new PriorityQueue<>();

for (int[] interval : intervals) {

    if (!pq.isEmpty() &&
        pq.peek() <= interval[0]) {

        pq.poll();
    }

    pq.add(interval[1]);
}

return pq.size();
```

Why min heap?

> We need the room that becomes free earliest.

```text
min heap
   ↓
earliest ending meeting
```

If:

```text
earliestEnd <= currentStart
```

reuse the room.

Otherwise, another room is needed.

Complexity:

```text
Time  = O(n log n)
Space = O(n)
```

---

## 10. Meeting Rooms II — Sweep Line

Separate:

```text
start times
end times
```

Sort both.

```java
Arrays.sort(starts);
Arrays.sort(ends);

int rooms = 0;
int maxRooms = 0;
int i = 0;
int j = 0;

while (i < starts.length) {

    if (starts[i] < ends[j]) {
        rooms++;
        maxRooms = Math.max(maxRooms, rooms);
        i++;
    } else {
        rooms--;
        j++;
    }
}

return maxRooms;
```

Core idea:

```text
meeting starts → rooms++
meeting ends   → rooms--
```

Track maximum rooms.

---

## 11. Remove Minimum Number of Overlapping Intervals

Question:

> Remove minimum intervals so remaining intervals don't overlap.

Sort by **END time**, not START time.

Why?

> Keep the interval that ends earliest.

It leaves more room for future intervals.

Mental distinction:

```text
MERGE intervals
    ↓
sort by START

KEEP maximum non-overlapping intervals
    ↓
sort by END
```

---

## 12. Things to Take Care Of

### 1. Are intervals already sorted?

If not:

```java
Arrays.sort(intervals,
    (a, b) -> Integer.compare(a[0], b[0]));
```

### 2. What does overlap mean?

Check whether touching endpoints count.

```text
[1,3] [3,5]
```

Depending on the problem, this may be:

```text
overlap → merge
```

or:

```text
no conflict
```

### 3. Don't overwrite the end incorrectly

Use:

```java
Math.max(lastEnd, currentEnd)
```

Example:

```text
[1,10]
[2,5]
```

Result:

```text
[1,10]
```

### 4. Don't confuse Merge and Intersection

Merge:

```text
[1,5] + [3,8]
→ [1,8]
```

Intersection:

```text
[1,5] + [3,8]
→ [3,5]
```

Remember:

```text
MERGE
min start + max end

INTERSECTION
max start + min end
```

### 5. Empty input

Handle:

```java
if (intervals.length == 0)
```

when required.

### 6. Comparator overflow

Avoid:

```java
(a,b) -> a[0] - b[0]
```

Prefer:

```java
(a,b) -> Integer.compare(a[0], b[0])
```

---

## 13. Master Interval Pattern

```text
                INTERVAL
                    |
        +-----------+-----------+
        |           |           |
     MERGE       INTERSECT    SCHEDULE
        |           |           |
    Sort start   2 pointers   Meeting
        |           |           |
    overlap?     overlap?     conflict?
        |           |           |
    merge         add          heap/
                               sweep
```

---

## 14. Interview Attack Card

### Step 1

Identify:

```text
Are these intervals?
```

### Step 2

Ask:

```text
Are they sorted?
```

If no:

```java
Arrays.sort(intervals,
    (a,b) -> Integer.compare(a[0], b[0]));
```

### Step 3

Identify what is being asked:

```text
Merge?
Intersection?
Conflict?
Minimum rooms?
Maximum overlap?
Remove overlaps?
```

### Step 4

Choose the pattern:

```text
Merge           → Sort + Greedy
Intersection    → Two Pointers
Meeting Room    → Sort + Compare
Meeting Room II → Min Heap / Sweep Line
Remove overlap  → Sort by END + Greedy
```

---

## 15. Most Important Formulas

### Merge

```text
overlap:
current.start <= last.end

merged:
[start, max(last.end, current.end)]
```

### Intersection

```text
start = max(start1, start2)
end   = min(end1, end2)

if start <= end → intersection
```

### Meeting Conflict

```text
current.start < previous.end
```

### Meeting Rooms II

```text
start → +1 room
end   → -1 room
```

---

## 16. Final Memory Rules

```text
1. Intervals → think SORT first.

2. Merge → sort by START.

3. Merge overlap:
   current.start <= last.end

4. Merge end:
   max(last.end, current.end)

5. Intersection:
   max(start) → min(end)

6. Intersection → move smaller END.

7. Meeting Rooms:
   sort by start and check conflict.

8. Meeting Rooms II:
   min heap = earliest ending meeting.

9. Maximum simultaneous meetings:
   sweep line.

10. Remove minimum overlaps:
    sort by END + greedy.

11. Touching endpoints:
    carefully check whether they count as overlap.

12. Comparator:
    Integer.compare(a[0], b[0])
```

### One-line Pattern Recognition

> **Intervals + overlap → sort first; then choose Merge, Two Pointers, Greedy, Heap, or Sweep Line based on what the question asks.**
