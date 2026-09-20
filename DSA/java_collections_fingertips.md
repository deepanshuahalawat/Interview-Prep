# Java Collections & DSA Finger-Tips Cheat Sheet

## Collection Selection

| Need | Use |
|---|---|
| Dynamic array / random access | `ArrayList` |
| Add/remove both ends | `ArrayDeque` |
| Stack / DFS | `ArrayDeque` |
| Queue / BFS | `ArrayDeque` |
| Fast key lookup | `HashMap` |
| Fast uniqueness/seen check | `HashSet` |
| Insertion order + hash lookup | `LinkedHashMap` / `LinkedHashSet` |
| Sorted unique elements | `TreeSet` |
| Sorted key-value pairs | `TreeMap` |
| Min/Max heap | `PriorityQueue` |

---

# ArrayList

```java
List<Integer> list = new ArrayList<>();
```

| Operation | Method | Complexity |
|---|---|---:|
| Add last | `add(x)` | `O(1)` amortized |
| Add at index | `add(i,x)` | `O(n)` |
| Get ith | `get(i)` | `O(1)` |
| Get first | `get(0)` | `O(1)` |
| Get last | `get(size-1)` | `O(1)` |
| Set ith | `set(i,x)` | `O(1)` |
| Remove ith | `remove(i)` | `O(n)` |
| Remove last | `remove(size-1)` | `O(1)` |
| Contains | `contains(x)` | `O(n)` |
| Index | `indexOf(x)` | `O(n)` |
| Size | `size()` | `O(1)` |

**Remember:** removing from the beginning shifts elements → `O(n)`.

---

# LinkedList

```java
LinkedList<Integer> list = new LinkedList<>();
```

| Operation | Method | Complexity |
|---|---|---:|
| Add first | `addFirst(x)` | `O(1)` |
| Add last | `addLast(x)` | `O(1)` |
| Get first | `getFirst()` | `O(1)` |
| Get last | `getLast()` | `O(1)` |
| Remove first | `removeFirst()` | `O(1)` |
| Remove last | `removeLast()` | `O(1)` |
| Get ith | `get(i)` | `O(n)` |
| Search | `contains(x)` | `O(n)` |

For DSA, prefer `ArrayDeque` when you only need deque/queue/stack behavior.

---

# ArrayDeque — Stack + Queue + Deque

```java
Deque<Integer> dq = new ArrayDeque<>();
```

| Operation | Method | Complexity |
|---|---|---:|
| Add beginning | `addFirst(x)` / `offerFirst(x)` | `O(1)` amortized |
| Add last | `addLast(x)` / `offerLast(x)` | `O(1)` amortized |
| Remove beginning | `removeFirst()` / `pollFirst()` | `O(1)` amortized |
| Remove last | `removeLast()` / `pollLast()` | `O(1)` amortized |
| Get first | `getFirst()` / `peekFirst()` | `O(1)` |
| Get last | `getLast()` / `peekLast()` | `O(1)` |
| Size | `size()` | `O(1)` |

### Stack

```java
Deque<Integer> stack = new ArrayDeque<>();

stack.push(x);
stack.pop();
stack.peek();
```

All are `O(1)` amortized.

### Queue

```java
Deque<Integer> q = new ArrayDeque<>();

q.offerLast(x);
q.pollFirst();
q.peekFirst();
```

All are `O(1)` amortized.

---

# HashMap

```java
Map<Integer,Integer> map = new HashMap<>();
```

Average expected complexity:

| Operation | Method | Complexity |
|---|---|---:|
| Put | `put(k,v)` | `O(1)` |
| Get | `get(k)` | `O(1)` |
| Check key | `containsKey(k)` | `O(1)` |
| Remove | `remove(k)` | `O(1)` |
| Size | `size()` | `O(1)` |
| Default get | `getOrDefault(k,d)` | `O(1)` average |
| Put if absent | `putIfAbsent(k,v)` | `O(1)` average |

### Frequency

```java
map.put(x, map.getOrDefault(x, 0) + 1);
```

### First occurrence

```java
map.putIfAbsent(x, i);
```

### Iterate

```java
for (Map.Entry<Integer,Integer> e : map.entrySet()) {
    int key = e.getKey();
    int value = e.getValue();
}
```

---

# HashSet

```java
Set<Integer> set = new HashSet<>();
```

| Operation | Method | Average |
|---|---|---:|
| Add | `add(x)` | `O(1)` |
| Search | `contains(x)` | `O(1)` |
| Remove | `remove(x)` | `O(1)` |
| Size | `size()` | `O(1)` |

Classic:

```java
if (seen.contains(x)) { ... }
seen.add(x);
```

---

# LinkedHashMap / LinkedHashSet

Hash-based collections that preserve insertion order.

Typical lookup/add/remove:

```text
O(1) average
```

Use when you need:

> HashMap/HashSet behavior + insertion order.

---

# TreeSet

Sorted unique elements.

```java
TreeSet<Integer> set = new TreeSet<>();
```

| Operation | Method | Complexity |
|---|---|---:|
| Add | `add(x)` | `O(log n)` |
| Remove | `remove(x)` | `O(log n)` |
| Search | `contains(x)` | `O(log n)` |
| First | `first()` | `O(1)` in common implementation |
| Last | `last()` | `O(1)` in common implementation |
| <= x | `floor(x)` | `O(log n)` |
| < x | `lower(x)` | `O(log n)` |
| >= x | `ceiling(x)` | `O(log n)` |
| > x | `higher(x)` | `O(log n)` |

**Very important:**

```java
floor(x)    // <= x
lower(x)    // < x
ceiling(x)  // >= x
higher(x)   // > x
```

---

# TreeMap

Sorted keys.

```java
TreeMap<Integer,String> map = new TreeMap<>();
```

| Operation | Method | Complexity |
|---|---|---:|
| Put | `put(k,v)` | `O(log n)` |
| Get | `get(k)` | `O(log n)` |
| Remove | `remove(k)` | `O(log n)` |
| Contains key | `containsKey(k)` | `O(log n)` |
| <= key | `floorKey(k)` | `O(log n)` |
| < key | `lowerKey(k)` | `O(log n)` |
| >= key | `ceilingKey(k)` | `O(log n)` |
| > key | `higherKey(k)` | `O(log n)` |
| First key | `firstKey()` | `O(1)` in common implementation |
| Last key | `lastKey()` | `O(1)` in common implementation |

---

# PriorityQueue — Heap

Min-heap by default:

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
```

| Operation | Method | Complexity |
|---|---|---:|
| Insert | `offer(x)` / `add(x)` | `O(log n)` |
| Minimum | `peek()` | `O(1)` |
| Remove minimum | `poll()` | `O(log n)` |
| Remove arbitrary value | `remove(x)` | `O(n)` |
| Size | `size()` | `O(1)` |

### Max Heap

```java
PriorityQueue<Integer> maxHeap =
    new PriorityQueue<>(Collections.reverseOrder());
```

### Custom Heap

```java
PriorityQueue<int[]> pq =
    new PriorityQueue<>(
        (a,b) -> Integer.compare(a[1], b[1])
    );
```

---

# Arrays Utility

```java
Arrays.sort(arr);
Arrays.fill(arr, -1);
Arrays.binarySearch(arr, target);
Arrays.copyOf(arr, n);
Arrays.copyOfRange(arr, from, to);
Arrays.equals(a, b);
```

Typical:

| Method | Complexity |
|---|---:|
| `sort` | `O(n log n)` typical |
| `fill` | `O(n)` |
| `binarySearch` | `O(log n)` |
| `copyOf` | `O(n)` |
| `copyOfRange` | `O(n)` |
| `equals` | `O(n)` |

---

# Collections Utility

```java
Collections.sort(list);
Collections.reverse(list);
Collections.min(list);
Collections.max(list);
Collections.binarySearch(list, x);
Collections.swap(list, i, j);
Collections.frequency(list, x);
Collections.fill(list, x);
Collections.reverseOrder();
```

| Method | Complexity |
|---|---:|
| `sort` | `O(n log n)` |
| `reverse` | `O(n)` |
| `min/max` | `O(n)` |
| `binarySearch` | `O(log n)` on suitable sorted random-access list |
| `swap` | `O(1)` |
| `frequency` | `O(n)` |
| `fill` | `O(n)` |

Modern alternative:

```java
list.sort(null);
```

---

# Comparator — Must Be on Your Fingertips

### Ascending

```java
Arrays.sort(arr);
```

### Custom ascending

```java
Arrays.sort(arr,
    (a,b) -> Integer.compare(a[0], b[0]));
```

### Descending

```java
Arrays.sort(arr,
    (a,b) -> Integer.compare(b[0], a[0]));
```

### Multiple criteria

```java
Arrays.sort(arr, (a,b) -> {
    if (a[1] != b[1]) {
        return Integer.compare(a[1], b[1]);
    }
    return Integer.compare(b[0], a[0]);
});
```

### List

```java
list.sort(Comparator.comparing(Person::getAge));
```

Descending:

```java
list.sort(
    Comparator.comparing(Person::getAge).reversed()
);
```

Multiple fields:

```java
list.sort(
    Comparator.comparing(Person::getAge)
             .thenComparing(Person::getName)
);
```

### IMPORTANT — Avoid subtraction comparator

Bad:

```java
(a,b) -> a - b
```

Good:

```java
(a,b) -> Integer.compare(a,b)
```

Subtraction can overflow.

---

# Reverse Ordering

```java
Collections.reverseOrder()
Comparator.reverseOrder()
Comparator.naturalOrder()
```

Examples:

```java
list.sort(Collections.reverseOrder());

list.sort(Comparator.reverseOrder());
```

Max heap:

```java
new PriorityQueue<>(Collections.reverseOrder());
```

---

# Binary Search

```java
Arrays.binarySearch(arr, target);
Collections.binarySearch(list, target);
```

But for DSA, know these concepts more importantly:

```text
Lower Bound = first index with arr[i] >= target

Upper Bound = first index with arr[i] > target
```

Safe midpoint:

```java
int mid = left + (right - left) / 2;
```

Avoid:

```java
int mid = (left + right) / 2;
```

because of integer overflow.

---

# String

```java
s.length();
s.charAt(i);
s.substring(l,r);
s.indexOf(x);
s.lastIndexOf(x);
s.contains(x);
s.equals(t);
s.toCharArray();
```

Typical:

| Operation | Complexity |
|---|---:|
| `length` | `O(1)` |
| `charAt` | `O(1)` |
| `substring` | `O(length of result)` |
| `indexOf` | `O(n)` typical |
| `contains` | `O(n)` typical |
| `toCharArray` | `O(n)` |

---

# StringBuilder

```java
StringBuilder sb = new StringBuilder();
```

| Operation | Complexity |
|---|---:|
| `append` | `O(1)` amortized |
| `charAt` | `O(1)` |
| `setCharAt` | `O(1)` |
| `deleteCharAt` | `O(n)` |
| `insert` | `O(n)` |
| `reverse` | `O(n)` |
| `toString` | `O(n)` |

---

# Integer / Long / Math

Must know:

```java
Integer.MAX_VALUE
Integer.MIN_VALUE

Long.MAX_VALUE
Long.MIN_VALUE

Math.max(a,b)
Math.min(a,b)
Math.abs(x)
Math.sqrt(x)
Math.pow(a,b)
Math.ceil(x)
Math.floor(x)
```

### Parsing

```java
Integer.parseInt("123");
Long.parseLong("123");
String.valueOf(123);
```

### Character digit conversion

```java
int digit = c - '0';
char c = (char)('0' + digit);
```

---

# Java Modulo — Critical for DSA

Java:

```java
-9 % 5   // -4
```

Mathematical modulo with the usual non-negative convention:

```text
-9 mod 5 = 1
```

Why Java gives `-4`:

```text
-9 / 5 = -1
-9 = (-1 × 5) + (-4)
```

For mathematical/non-negative modulo:

```java
Math.floorMod(-9, 5); // 1
```

Useful in prefix-sum modulo problems when the prefix can be negative.

---

# Character

```java
Character.isDigit(c);
Character.isLetter(c);
Character.isLetterOrDigit(c);
Character.isWhitespace(c);
Character.toLowerCase(c);
Character.toUpperCase(c);
```

---

# Bit Operations

Useful for bitmask problems:

```java
Integer.bitCount(x);
Integer.numberOfLeadingZeros(x);
Integer.numberOfTrailingZeros(x);
Integer.highestOneBit(x);
Integer.lowestOneBit(x);
Long.bitCount(x);
```

---

# Common Initialization

```java
int[] arr = new int[n];       // filled with 0
Integer[] arr = new Integer[n]; // filled with null

int[][] dp = new int[m][n];

for (int[] row : dp) {
    Arrays.fill(row, -1);
}
```

---

# High-Priority DSA Snippets

## Frequency Map

```java
map.put(x, map.getOrDefault(x, 0) + 1);
```

## Seen Set

```java
if (set.contains(x)) { ... }
set.add(x);
```

## First Occurrence

```java
map.putIfAbsent(x, i);
```

## Queue

```java
Deque<Integer> q = new ArrayDeque<>();
q.offerLast(x);
q.pollFirst();
q.peekFirst();
```

## Stack

```java
Deque<Integer> stack = new ArrayDeque<>();
stack.push(x);
stack.pop();
stack.peek();
```

## Min Heap

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
```

## Max Heap

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>(Collections.reverseOrder());
```

## Sort

```java
Arrays.sort(nums);
```

## Descending List

```java
list.sort(Collections.reverseOrder());
```

## Custom Sort

```java
Arrays.sort(arr,
    (a,b) -> Integer.compare(a[0], b[0]));
```

---

# Finger-Tips Checklist

## Collections

```text
ArrayList
ArrayDeque
HashMap
HashSet
LinkedHashMap
LinkedHashSet
TreeMap
TreeSet
PriorityQueue
```

## ArrayList

```text
add
get
set
remove
size
contains
indexOf
sort
```

## ArrayDeque

```text
offerFirst
offerLast
pollFirst
pollLast
peekFirst
peekLast
push
pop
peek
```

## HashMap

```text
put
get
getOrDefault
putIfAbsent
containsKey
remove
keySet
values
entrySet
```

## TreeMap / TreeSet

```text
floor
lower
ceiling
higher
first
last

floorKey
lowerKey
ceilingKey
higherKey
firstKey
lastKey
```

## PriorityQueue

```text
offer
poll
peek
```

## Arrays

```text
sort
fill
binarySearch
copyOf
copyOfRange
equals
```

## Collections

```text
sort
reverse
reverseOrder
min
max
binarySearch
swap
frequency
fill
```

## Comparator

```text
Integer.compare
Comparator.comparing
thenComparing
reversed
naturalOrder
reverseOrder
```

## Math / Integer

```text
Integer.MAX_VALUE
Integer.MIN_VALUE
Long.MAX_VALUE
Long.MIN_VALUE
Math.max
Math.min
Math.abs
Math.sqrt
Math.pow
```

## Strings

```text
length
charAt
substring
indexOf
contains
equals
toCharArray
```

## StringBuilder

```text
append
deleteCharAt
setCharAt
reverse
toString
```

---

# Final SDE2 Rule

Don't just memorize method names.

For every collection, know:

```text
1. When to use it
2. Main operations
3. Complexity
4. What it is backed by / how it behaves
5. Its common DSA pattern
```

The most important mapping:

```text
Random access             → ArrayList

Both-end operations       → ArrayDeque

Stack                     → ArrayDeque

Queue / BFS               → ArrayDeque

Fast lookup               → HashMap / HashSet

Frequency counting        → HashMap

Sorted elements           → TreeSet

Sorted key-value pairs    → TreeMap

Predecessor/successor     → TreeSet / TreeMap

Min/Max dynamically       → PriorityQueue

Top K                     → PriorityQueue

Sorting                   → Arrays.sort / list.sort

Custom ordering           → Comparator

Binary search             → sorted array/list

Repeated string building  → StringBuilder
```

> **Interview standard:** You should be able to write the common collection declaration, core operation, comparator, sorting, heap, HashMap frequency, and binary-search syntax without looking them up.
