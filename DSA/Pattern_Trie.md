# Trie — Interview Pattern Map

## 1. What problem does a Trie solve?

A Trie is mainly useful for **strings where prefixes matter**.

```text
HashMap / HashSet
        ↓
Exact string lookup

Trie
        ↓
Prefix-based lookup
```

Core idea:

> **A Trie stores characters along paths, not complete strings as independent objects.**

---

# 2. Main Trie Patterns

```text
                         TRIE
                           │
          ┌────────────────┼─────────────────┐
          │                │                 │
      Basic Trie       Prefix Search      Wildcard
          │                │                 │
     insert/search     startsWith          DFS
     startsWith           │
          │               DFS
          │                │
          │          Autocomplete
          │
          ├──────────────────────────────────┐
          │                                  │
       Metadata                           Grid
          │                                  │
    prefixCount                         Trie + DFS
    wordCount                           + Backtracking
    erase                                    │
          │                              Word Search II
          │
          ├──────────────────────────────────┐
          │                                  │
         DP                               Binary Trie
          │                                  │
     Word Break                         Maximum XOR


---

# 3. Pattern 1 — Basic Trie Operations

This is the foundation.

You should be able to implement:

```text
insert(word)
search(word)
startsWith(prefix)
```

Eventually:

```text
delete(word)
```

Typical node:

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd;
}
```

For:

```text
apple
```

the path is:

```text
a → p → p → l → e
```

and the final node has:

```java
isEnd = true;
```

### Insert

Walk through every character and create missing nodes.

### Search

Walk through every character.

At the end:

```java
return node.isEnd;
```

This distinguishes:

```text
app
```

from:

```text
apple
```

### startsWith

For:

```text
app
```

you only need to successfully reach the `p` node. It does not have to be an end node.

### Mandatory problem

**LeetCode 208 — Implement Trie (Prefix Tree)**

---

# 4. Pattern 2 — Trie with Metadata

A Trie node can store more than:

```text
children
isEnd
```

For example:

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];

    boolean isEnd;

    int prefixCount;
    int wordCount;
}
```

This enables:

```text
How many times was "apple" inserted?

How many words start with "app"?
```

### wordCount

> How many complete words end at this node?

### prefixCount

> How many inserted words pass through this node?

This is especially useful when duplicate words are allowed.

### Mandatory problem

**LeetCode 1804 — Implement Trie II (Prefix Tree)**

Learn:

```text
countWordsEqualTo()
countWordsStartingWith()
erase()
```

---

# 5. Pattern 3 — Prefix Search

Suppose:

```text
apple
app
application
apply
apt
banana
```

Query:

```text
app
```

Possible result:

```text
app
apple
application
apply
```

First walk to:

```text
a → p → p
```

Then run DFS from that node.

Pattern:

```text
Prefix search
      +
DFS
      ↓
All matching words
```

### Recognition

Look for:

```text
prefix
starts with
all words beginning with
dictionary prefix
suggestions
autocomplete
```

---

# 6. Pattern 4 — Trie + DFS = Autocomplete

Suppose the user types:

```text
app
```

Dictionary:

```text
apple
app
application
apply
apt
```

First:

```text
Trie search("app")
```

Then:

```text
DFS(node)
```

collects words below that node.

Pattern:

```text
Trie
 ↓
Prefix node
 ↓
DFS
 ↓
Matching words
```

For ranked suggestions:

```text
Trie + DFS + Heap
```

can return Top K suggestions.

### Problem

**LeetCode 642 — Design Search Autocomplete System**

Learn this after basic Trie patterns.

---

# 7. Pattern 5 — Trie + Wildcard Search

Suppose:

```text
bad
dad
mad
```

and query:

```text
.d
```

`.` means any character.

A normal Trie traversal cannot choose only one child for `.`.

For a wildcard:

```text
.
```

try every existing child recursively.

Therefore:

```text
Trie
 +
DFS / Backtracking
```

### Mandatory problem

**LeetCode 211 — Design Add and Search Words Data Structure**

This teaches:

```text
Trie + DFS + wildcard matching
```

---

# 8. Pattern 6 — Trie + Grid DFS / Backtracking

Example:

```text
board:

o a n
e t a
i h k
```

and:

```text
words = [
    "oath",
    "eat",
    "rain"
]
```

Build a Trie from all words.

Then:

```text
Start DFS from every grid cell
        ↓
Follow only Trie-valid prefixes
```

Pattern:

```text
Trie
 +
Grid DFS
 +
Backtracking
```

### Why Trie helps

If the current grid path is:

```text
o → a → z
```

and no dictionary word starts with:

```text
oaz
```

stop immediately.

This is **prefix pruning**.

### Mandatory problem

**LeetCode 212 — Word Search II**

This is one of the highest-value Trie problems for interviews.

---

# 9. Pattern 7 — Trie + DP

Example:

```text
s = "applepie"

dictionary =
[
    "apple",
    "app",
    "pie"
]
```

Question:

> Can the string be segmented into dictionary words?

Use:

```text
Trie + DP
```

From each DP position, walk forward through the Trie.

Example:

```text
applepie
^
```

Trie finds:

```text
apple
```

Then:

```text
dp[5] = true
```

Continue from index `5`.

Pattern:

```text
String
  +
Dictionary
  +
Trie prefix matching
  +
DP
```

### Problems

**LeetCode 139 — Word Break**

Then:

**LeetCode 140 — Word Break II**

which adds:

```text
Trie + DP + Backtracking
```

---

# 10. Pattern 8 — Binary Trie + XOR

A Trie can store bits instead of characters.

For:

```text
101101
```

store:

```text
1 → 0 → 1 → 1 → 0 → 1
```

Useful for:

```text
XOR
maximum XOR
minimum XOR
bitwise comparison
```

### Maximum XOR

For each bit:

```text
current bit = 0
    ↓
prefer 1

current bit = 1
    ↓
prefer 0
```

because:

```text
0 ^ 1 = 1
1 ^ 0 = 1
```

### Problem

**LeetCode 421 — Maximum XOR of Two Numbers in an Array**

This is the main Binary Trie problem to know.

---

# 11. Pattern 9 — Trie + Heap / Ranking

Suppose:

```text
prefix = "app"
```

and:

```text
apple        score = 100
application  score = 80
app          score = 60
apply        score = 40
```

Need:

```text
Top K suggestions
```

Pattern:

```text
Trie
 ↓
Prefix node
 ↓
DFS / stored candidates
 ↓
Heap
 ↓
Top K
```

Useful for:

- Autocomplete
- Search ranking
- Top K prefix suggestions
- Recommendation systems

This is advanced. Do it after the core Trie patterns.

---

# 12. Trie vs HashMap / HashSet

### HashSet / HashMap

Best for:

```text
Does this exact word exist?
```

Example:

```text
"apple" → yes/no
```

Expected lookup:

```text
O(1)
```

### Trie

Best for:

```text
Does any word start with "app"?

What words start with "app"?

Does this prefix exist?
```

Lookup:

```text
O(L)
```

where:

```text
L = length of word/prefix
```

### Mental model

```text
HashSet:
"Does apple exist?"

Trie:
"Does anything start with app?"
```

---

# 13. Trie vs Sorting

Prefix problems can sometimes be solved using:

```text
Sorting + Binary Search
```

After sorting, strings sharing a prefix become contiguous.

Use:

```text
Exact lookup
    ↓
HashSet / HashMap

Prefix operations
    ↓
Trie

Offline prefix queries
    ↓
Sorting + Binary Search can also work
```

Trie is not automatically the best answer.

---

# 14. Java Trie Implementation You Should Master

Start with:

```java
class Trie {

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }

    private final TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;

        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';

            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }

            node = node.children[idx];
        }

        node.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode node = root;

        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';

            if (node.children[idx] == null) {
                return false;
            }

            node = node.children[idx];
        }

        return node.isEnd;
    }

    public boolean startsWith(String prefix) {
        TrieNode node = root;

        for (char ch : prefix.toCharArray()) {
            int idx = ch - 'a';

            if (node.children[idx] == null) {
                return false;
            }

            node = node.children[idx];
        }

        return true;
    }
}
```

You should eventually be able to write this from memory.

---

# 15. How to Tackle Any Trie Problem

## Step 1 — Is there a dictionary?

Look for:

```text
words
dictionary
vocabulary
allowed strings
```

If yes:

```text
Trie candidate
```

## Step 2 — Is the question about prefixes?

Look for:

```text
prefix
starts with
autocomplete
suggestions
common prefix
dictionary matching
```

Strong Trie signal.

## Step 3 — Is there a wildcard?

Look for:

```text
.
*
any character
pattern matching
```

Think:

```text
Trie + DFS
```

## Step 4 — Are you searching words in a grid?

Think:

```text
Trie + Grid DFS + Backtracking
```

## Step 5 — Are you repeatedly checking dictionary prefixes?

Think:

```text
Trie + DP
```

## Step 6 — Is the problem about maximum XOR?

Think:

```text
Binary Trie
```

## Step 7 — Is the result Top K for a prefix?

Think:

```text
Trie + Heap
```

---

# 16. Recommended Learning Order

Do not jump directly to Word Search II.

## Level 1 — Basic Trie

**LeetCode 208 — Implement Trie**

Learn:

```text
insert
search
startsWith
```

## Level 2 — Trie Metadata

**LeetCode 1804 — Implement Trie II**

Learn:

```text
prefixCount
wordCount
erase
```

## Level 3 — Trie + DFS

**LeetCode 211 — Design Add and Search Words Data Structure**

Learn:

```text
Trie
+
DFS
+
Wildcard
```

## Level 4 — Prefix / Autocomplete

Learn:

```text
prefix node
+
DFS
+
ranking
```

## Level 5 — Trie + Grid

**LeetCode 212 — Word Search II**

Learn:

```text
Trie
+
Grid
+
DFS
+
Backtracking
```

## Level 6 — Trie + DP

**LeetCode 139 — Word Break**

Then:

**LeetCode 140 — Word Break II**

Learn:

```text
Trie + DP + Backtracking
```

## Level 7 — Binary Trie

**LeetCode 421 — Maximum XOR of Two Numbers in an Array**

Learn:

```text
Binary Trie
+
Greedy bit selection
```

---

# 17. Problems You Should Know

You do not need 30 Trie problems.

Master around 8–10 representative problems.

## Must Do

```text
1. LeetCode 208 — Implement Trie
2. LeetCode 1804 — Implement Trie II
3. LeetCode 211 — Design Add and Search Words
4. LeetCode 212 — Word Search II
5. LeetCode 139 — Word Break
6. LeetCode 140 — Word Break II
7. LeetCode 421 — Maximum XOR
```

## Optional

```text
8. LeetCode 642 — Design Search Autocomplete System
9. Prefix frequency / ranking problem
10. Another Binary Trie problem
```

---

# 18. Trie Complexity

Let:

```text
L = length of the word/prefix
```

### Insert

```text
O(L)
```

### Search

```text
O(L)
```

### startsWith

```text
O(L)
```

### Delete

Usually:

```text
O(L)
```

### Space

If `N` words contain a total of `S` characters:

```text
O(S)
```

Trie nodes are created for inserted characters.

With:

```java
TrieNode[26]
```

each node has 26 child references. This is simple and fast but can use significant memory.

Using:

```java
HashMap<Character, TrieNode>
```

can be more selective with memory but has HashMap overhead.

---

# 19. Important Trie Concepts

## isEnd vs wordCount

Normal Trie:

```text
isEnd
```

is enough to tell whether a word ends at the node.

For duplicate words:

```text
wordCount
```

is better.

Example:

```text
insert("apple")
insert("apple")
```

Then:

```text
wordCount = 2
```

## Prefix Count

If:

```text
insert("apple")
insert("app")
insert("application")
```

then the node representing:

```text
app
```

can have:

```text
prefixCount = 3
```

because three inserted words pass through it.

This is the key idea behind:

```text
countWordsStartingWith()
```

## Deleting from a Trie

### Logical deletion

Decrement:

```text
wordCount
```

or set:

```text
isEnd = false
```

### Physical deletion

Actually remove nodes that no longer belong to any word.

For:

```text
apple
app
```

after deleting `apple`, you cannot delete the shared:

```text
a → p → p
```

because `app` still needs them.

Physical deletion requires checking whether a node:

```text
has no children
AND
is not the end of another word
```

Understand both concepts.

---

# 20. Core Recognition Map

Memorize this:

```text
                         TRIE
                           │
                    Prefix structure
                           │
          ┌────────────────┼─────────────────┐
          │                │                 │
       Prefix          Enumerate         Wildcard
          │                │                 │
      Trie only        Trie + DFS        Trie + DFS
          │
     startsWith
          │
      Prefix node
```

Then:

```text
Dictionary + Grid
        ↓
Trie + DFS + Backtracking
        ↓
Word Search II
```

```text
Dictionary + String Segmentation
        ↓
Trie + DP
        ↓
Word Break
```

```text
Numbers + Maximum XOR
        ↓
Binary Trie + Greedy
        ↓
Maximum XOR
```

```text
Prefix + Top K
        ↓
Trie + Heap
        ↓
Autocomplete / Ranking
```

---

# 21. Final Trie Mental Model

Do not memorize individual problems.

Think:

```text
                         TRIE
                           │
                    "Do prefixes matter?"
                           │
                          YES
                           │
              ┌────────────┼────────────┐
              │            │            │
           Prefix        Pattern       Grid
              │          matching        │
              │            │             │
          Trie only    Trie + DFS    Trie + DFS
              │                        + Backtracking
              │
         Enumeration
              │
         Trie + DFS
```

Special combinations:

```text
Dictionary + segmentation
        ↓
Trie + DP

Prefix + Top K
        ↓
Trie + Heap

Numbers + XOR
        ↓
Binary Trie + Greedy
```

### One sentence to remember

> **Trie is a prefix tree; once you identify the prefix structure, the rest of the problem usually tells you what to combine with it — DFS, Backtracking, DP, Heap, or Greedy.**

---

# 22. Interview Preparation Target

Before moving away from Trie, you should be able to:

```text
✓ Build Trie from scratch
✓ insert()
✓ search()
✓ startsWith()
✓ Handle duplicate words
✓ Implement erase()
✓ Maintain prefixCount / wordCount
✓ Trie + DFS for wildcard
✓ Trie + DFS for autocomplete
✓ Trie + Grid DFS + Backtracking
✓ Trie + DP
✓ Binary Trie for XOR
✓ Write custom TrieNode metadata
✓ Explain Trie vs HashSet/HashMap
✓ Explain Trie vs Sorting + Binary Search
```

If you can do these, you have covered the **main interview Trie patterns**, rather than just memorizing a collection of problems.
