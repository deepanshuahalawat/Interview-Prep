# System Design — Back-of-the-Envelope Estimation Cheat Sheet

Use these as **interview starting assumptions**, not exact production guarantees.

## 1. Time → RPS

```text
RPS = Daily requests / 86,400
```

| Daily Requests | Average RPS |
|---:|---:|
| 1M/day | ~12 |
| 10M/day | ~116 |
| 100M/day | ~1,160 |
| 1B/day | ~11,600 |
| 10B/day | ~116,000 |

```text
Peak RPS ≈ 2–5 × average
```

Use **3×** as a default interview assumption.

---

## 2. DAU → Requests

```text
Daily requests = DAU × actions/user/day
```

Example:

```text
100M DAU × 20 requests/user/day = 2B requests/day
2B / 86,400 ≈ 23K RPS
Peak ≈ 70K RPS
```

| DAU | Requests/day | Avg RPS | Peak ~3× |
|---:|---:|---:|---:|
| 1M | 20M | 230 | 700 |
| 10M | 200M | 2.3K | 7K |
| 100M | 2B | 23K | 70K |
| 500M | 10B | 116K | 350K |
| 1B | 20B | 231K | 700K |

**Memorize:** `1M requests/day ≈ 12 RPS`

---

## 3. Read / Write Ratio

Typical starting assumptions:

- Social media: `~10:1`, sometimes `20:1`
- Content-heavy systems: `~100:1`
- Messaging: can be close to `1:1`

Example:

```text
100K total RPS
90% reads → 90K RPS
10% writes → 10K RPS
```

---

## 4. Storage

```text
Storage/day = writes/day × average object size
Storage/year = storage/day × 365
```

Useful conversions:

```text
1 KB × 1M ≈ 1 GB
10 KB × 1M ≈ 10 GB
100 KB × 1M ≈ 100 GB
1 MB × 1M ≈ 1 TB
1 KB × 1B ≈ 1 TB
```

Example:

```text
100M writes/day × 1 KB
= 100 GB/day
≈ 36.5 TB/year
```

---

## 5. Replication / Storage Overhead

Common starting assumption:

```text
Replication factor ≈ 3
```

Example:

```text
36.5 TB × 3 ≈ 110 TB
```

Also account for indexes, metadata, compaction, etc.

A useful buffer:

```text
Actual storage ≈ calculated storage × 1.3–2
```

---

## 6. Media Storage

### Images

```text
~100 KB – 1 MB
```

If one number is needed:

```text
~500 KB/image
```

### Thumbnails

```text
~10–50 KB
```

### Video

```text
Size = bitrate × duration
```

Example:

```text
5 Mbps × 60 sec
= 300 Mb
≈ 37.5 MB
```

---

## 7. RAM / Cache

Usually cache the **hot subset**, not the whole database.

```text
Cache = hot objects × average object size
```

Example:

```text
DB = 10 TB
Hot data = 10%
Cache ≈ 1 TB
```

Another example:

```text
100M hot objects × 1 KB
= 100 GB
```

Add cache metadata/structure overhead.

---

## 8. Cache Hit Rate

Reasonable starting assumption:

```text
80–95%
```

For heavily cacheable reads:

```text
~90%+ hit rate
```

Example:

```text
100K read RPS
90% hit

Cache → 90K RPS
DB    → 10K RPS
```

---

## 9. Latency

Rough orders of magnitude:

| Operation | Rough latency |
|---|---:|
| CPU operation | ns |
| Memory access | ~100 ns |
| SSD access | ~100 µs |
| Same-DC network | ~0.1–1 ms |
| Redis/cache | ~0.1–2 ms |
| Simple DB query | ~1–10 ms |
| Cross-region network | ~50–200+ ms |

Typical API starting targets:

| Percentile | Rough target |
|---|---:|
| P50 | ~50–100 ms |
| P95 | ~100–300 ms |
| P99 | ~300–1,000 ms |

These are workload-dependent.

---

## 10. Network Bandwidth

```text
Bandwidth = RPS × request/response size
```

Example:

```text
10K RPS × 10 KB
= 100 MB/sec
≈ 800 Mbps
```

Remember:

```text
1 byte = 8 bits
1 GB/s ≈ 8 Gbps
```

---

## 11. Daily Network Traffic

```text
Daily bandwidth =
requests/day × average response size
```

Example:

```text
100M requests/day × 100 KB
= 10 TB/day
```

Large media systems generally use **CDN + object storage**.

---

## 12. Application Servers

Rough starting assumption for simple APIs:

```text
~500–2,000 RPS/server
```

Use workload-dependent benchmarking in real systems.

Interview assumption:

```text
~1K RPS/server
```

Example:

```text
100K peak RPS / 1K RPS/server
= 100 servers
```

With headroom:

```text
~130–150 servers
```

---

## 13. CPU / Server Headroom

Don't target 100% utilization.

```text
Target CPU utilization ≈ 50–70%
```

Leaves room for:

- Traffic spikes
- Instance failures
- Deployments
- GC
- Background jobs

---

## 14. Database QPS

Very useful formula:

```text
DB QPS =
API RPS × cache miss rate × DB queries/request
```

Example:

```text
100K API RPS
× 10% cache miss
× 3 DB queries/request

= 30K DB QPS
```

---

## 15. Queue / Message Volume

```text
Events/sec × event size = bandwidth/sec
```

Example:

```text
10K events/sec × 2 KB
= 20 MB/sec

20 MB/sec × 86,400
≈ 1.7 TB/day
```

With replication factor 3:

```text
≈ 5 TB/day
```

---

## 16. Kafka / Queue Partitions

Starting approach:

```text
Partitions =
required throughput / throughput per partition
```

Don't memorize a universal partition capacity. It depends on message size, batching, compression, producers, consumers, and hardware.

Also consider **consumer parallelism**.

---

## 17. Long-Lived Connections

Important:

```text
Concurrent connections ≠ RPS
```

Example:

```text
10M concurrent WebSocket users
100K connections/server

10M / 100K = 100 servers
```

Add redundancy/headroom.

---

## 18. WebSocket Message Traffic

Example:

```text
1M concurrent users
1 message every 10 seconds

1M / 10 = 100K messages/sec
```

If each message is 1 KB:

```text
100K × 1 KB
= 100 MB/sec
≈ 800 Mbps
```

---

## 19. CDN

Typical flow:

```text
Client
  ↓
CDN
  ↓ cache miss
Object Storage
```

If:

```text
100K requests/sec
95% CDN hit rate
```

Origin receives approximately:

```text
100K × 5%
= 5K RPS
```

---

## 20. Unique IDs

Useful number:

```text
64-bit = 8 bytes
```

A billion 64-bit IDs:

```text
1B × 8 bytes
= 8 GB
```

Snowflake-style IDs commonly combine:

```text
Timestamp + worker/machine ID + sequence
```

---

## 21. Pagination

Example:

```text
10M items
100 items/page

= 100K pages
```

For large datasets prefer:

```text
Cursor / keyset pagination
```

over:

```text
OFFSET 10000000
```

because large offsets can become expensive.

---

## 22. Availability

| Availability | Downtime / year |
|---:|---:|
| 99% | ~3.65 days |
| 99.9% | ~8.8 hours |
| 99.99% | ~53 minutes |
| 99.999% | ~5.3 minutes |

---

## 23. Data Transfer Cheat Sheet

```text
1 KB × 1M ≈ 1 GB
1 MB × 1M ≈ 1 TB

1 KB × 1B ≈ 1 TB

1 GB/day × 365 ≈ 365 GB/year
1 TB/day × 365 ≈ 365 TB/year
```

---

# 24. Core Formulas

### Daily requests

```text
Daily requests = DAU × actions/user/day
```

### RPS

```text
RPS = daily requests / 86,400
```

### Peak RPS

```text
Peak RPS ≈ average RPS × 3
```

### Storage

```text
Daily storage = writes/day × object size
```

### Bandwidth

```text
Bandwidth = RPS × request/response size
```

### Database QPS

```text
DB QPS =
API RPS × cache miss rate × DB queries/request
```

### Servers

```text
Servers =
peak RPS / RPS per server
```

### Cache

```text
Cache =
hot objects × average object size
```

### Queue storage

```text
Queue storage/day =
events/sec × event size × 86,400
```

---

# 25. Interview Estimation Checklist

Before drawing the architecture, estimate:

```text
1. DAU = ?

2. Actions/user/day = ?

3. Requests/day = ?

4. Average RPS = ?

5. Peak RPS = ?

6. Read/write ratio = ?

7. Request size = ?

8. Response size = ?

9. Storage/day = ?

10. Storage/year = ?

11. Replication factor = ?

12. Bandwidth = ?

13. Cache hit rate = ?

14. Cache size = ?

15. DB QPS = ?

16. Application servers = ?

17. Availability target = ?

18. Latency target = ?
```

---

# 🧠 10 Numbers to Memorize

Don't memorize every number independently.

```text
1 day               = 86,400 sec

1M requests/day     ≈ 12 RPS

1B requests/day     ≈ 11.6K RPS

Peak traffic        ≈ 3× average

Cache hit rate      ≈ 90%

DB replication      ≈ 3×

1 KB × 1M           ≈ 1 GB

1 KB × 1B           ≈ 1 TB

99.9% availability  ≈ 8.8 hours/year

99.99% availability ≈ 53 minutes/year
```

# Mental Model

For most system-design estimation questions:

```text
DAU
 ↓
Actions/user/day
 ↓
Requests/day
 ↓
Average RPS
 ↓
Peak RPS
 ↓
Read / Write split
 ↓
Cache hit rate
 ↓
DB QPS
 ↓
Storage
 ↓
Bandwidth
 ↓
Servers
 ↓
Architecture
```

**Practice this chain until you can do it almost automatically.**
