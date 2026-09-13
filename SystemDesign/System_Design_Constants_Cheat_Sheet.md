# System Design Constants & Capacity Cheat Sheet

> Interview-grade estimation numbers for SDE-2 system design.
>
> **Important:** These are rough interview assumptions, not hard production limits. Real capacity depends on hardware, payload size, query complexity, indexing, persistence, replication, network, workload, and configuration.

## Quick Reference

| Component | Capacity / instance | Typical latency | General deployment | Scaling |
|---|---:|---:|---|---|
| Redis | ~100K ops/sec | ~0.2–1 ms | 1 primary + 1 replica; 3+ shard primaries for cluster | Sharding |
| Kafka broker | ~50–150 MB/s | ~2–10 ms | 3+ brokers | Partitions + brokers |
| PostgreSQL | ~5K–20K QPS | ~5–20 ms | 1 primary + 1–2+ replicas | Read replicas / sharding |
| Cassandra | ~10K–100K ops/sec | ~2–10 ms | 3+ nodes | Add nodes |
| MongoDB | ~5K–20K ops/sec | ~5–20 ms | 3-node replica set | Sharding |
| Java API server | ~1K–5K RPS | ~10–100 ms | 2–3+ instances | Horizontal scaling |
| Load Balancer | ~10K–100K+ RPS | ~1–5 ms | Managed / 2+ HA | Managed / horizontal |
| Elasticsearch | ~5K–20K search QPS | ~10–100 ms | 3+ nodes | Shards + nodes |
| Message Queue | ~10K–50K msg/sec | ~5–50 ms | 3+ nodes | Nodes / partitioning |
| CDN | Millions+ req/sec | ~10–50 ms | Global edge network | Edge distribution |
| Object Storage | Very high | ~50–200 ms | Managed | Automatic scaling |
| API Gateway | ~10K–100K+ RPS | ~1–10 ms | 2+ / managed | Managed / horizontal |

---

# 1. Time Constants

| Unit | Value |
|---|---:|
| 1 minute | 60 sec |
| 1 hour | 3,600 sec |
| 1 day | 86,400 sec |
| 1 month | ≈ 2.6M sec |
| 1 year | ≈ 31.5M sec |

---

# 2. Traffic: RPS → RPD

```text
RPD = RPS × 86,400
```

| RPS | RPD |
|---:|---:|
| 1 | 86.4K |
| 10 | 864K |
| 100 | 8.64M |
| 1K | 86.4M |
| 10K | 864M |
| 100K | 8.64B |
| 1M | 86.4B |

Reverse:

```text
RPS = Daily Requests / 86,400
```

Useful shortcuts:

```text
1M/day   ≈ 12 RPS
10M/day  ≈ 116 RPS
100M/day ≈ 1.16K RPS
1B/day   ≈ 11.6K RPS
```

---

# 3. Peak Traffic

Typical:

```text
Peak ≈ 3–5 × average
```

Highly spiky systems:

```text
Peak ≈ 10 × average
```

Example:

```text
Average = 10K RPS
Peak    = 5 × 10K
        = 50K RPS
```

---

# 4. Latency Constants

| Operation | Approximate latency |
|---|---:|
| CPU register | ~0.3 ns |
| L1 cache | ~1 ns |
| L2 cache | ~4 ns |
| L3 cache | ~10–15 ns |
| RAM | ~100 ns |
| SSD | ~100 μs |
| Redis | ~0.2–1 ms |
| Same-DC network | ~0.1–1 ms |
| Database | ~5–20 ms |
| Cross-region network | ~50–200 ms |
| External API | ~100 ms–seconds |

Mental ordering:

```text
CPU cache << RAM << SSD << Redis << DB << Cross-region
```

---

# 5. Redis

## Capacity

Use this as an interview default:

```text
≈ 100K ops/sec/node
```

Rough range:

```text
100K–300K ops/sec/node
```

Optimized workloads can be much higher.

## Latency

```text
Server-side:       ~0.2–1 ms
Same-DC app → Redis: ~0.5–2 ms
```

## Memory

Don't assume raw data size equals Redis memory.

```text
Redis memory ≈ raw data × 1.3–2
```

Example:

```text
10M keys × 500 B
= 5 GB raw data

Estimated Redis memory
≈ 6.5–10 GB
```

Leave headroom for fragmentation, buffers, replicas, and operational safety.

## Deployment

Simple:

```text
1 node
```

Production HA:

```text
1 primary + 1 replica
```

Redis Cluster:

```text
3+ primary shards
+
replicas as required
```

Common HA cluster:

```text
3 primaries + 3 replicas = 6 nodes
```

## Scaling

Replication:

```text
Primary
   |
Replica
```

Main purpose:

- High availability
- Failover
- Potential read scaling

**Replica ≠ automatic write scaling.**

Sharding:

```text
Node 1   Node 2   Node 3
100K     100K     100K ops/sec
```

Rough theoretical capacity:

```text
3 × 100K ≈ 300K ops/sec
```

if evenly distributed.

### Key rule

```text
Replication → availability / read scaling
Sharding    → capacity / write scaling
```

---

# 6. Kafka

Kafka is better measured using:

```text
MB/sec
+
messages/sec
+
partitions
```

## Capacity

Single broker:

```text
≈ 50–150 MB/sec
```

Use:

```text
≈ 100 MB/sec/broker
```

as an interview assumption.

For 1 KB messages:

```text
100 MB/sec
≈ 100K messages/sec
```

## Latency

Typical:

```text
~2–10 ms
```

Actual latency depends on batching, acknowledgments, replication, disk, network, and consumer lag.

## Deployment

Typical production:

```text
3 brokers
```

Larger:

```text
6 / 9 / 12+ brokers
```

depending on throughput, partitions, replication, and storage.

## Scaling

```text
Kafka capacity
≈ partitions × partition throughput
```

subject to broker CPU, disk, network, and replication limits.

## Replication

RF = 3 means approximately:

```text
Logical storage × 3
≈ physical storage
```

before other overhead.

---

# 7. Kafka Storage Calculation

Example:

```text
100K messages/sec
1 KB/message
```

```text
100K × 1 KB
≈ 100 MB/sec
```

Daily:

```text
100 MB × 86,400
≈ 8.64 TB/day
```

Annual:

```text
8.64 × 365
≈ 3.15 PB/year
```

RF = 3:

```text
3.15 × 3
≈ 9.45 PB physical storage/year
```

---

# 8. PostgreSQL

## Capacity

Generic interview default:

```text
≈ 10K QPS/node
```

Rough range:

```text
5K–20K QPS
```

Approximate workload ranges:

| Workload | Approximate |
|---|---:|
| Simple indexed read | ~10K–50K QPS |
| Typical application read | ~5K–20K QPS |
| Write-heavy | ~1K–10K QPS |
| Complex queries | ~100–5K QPS |

Actual capacity varies heavily with query design and hardware.

## Latency

```text
Indexed query: ~1–10 ms
Typical query: ~5–20 ms
Complex query: ~10–100+ ms
```

## Deployment

Typical:

```text
1 Primary
+
1–2 Read Replicas
```

Larger:

```text
1 Primary
+
3+ Read Replicas
```

## Scaling

Read scaling:

```text
             Primary
            /              Replica      Replica
```

Writes go to the primary.

For very large systems:

```text
Sharding
```

can provide horizontal capacity.

---

# 9. Cassandra

## Capacity

Rough:

```text
10K–100K ops/sec/node
```

Interview default:

```text
≈ 50K ops/sec/node
```

Example:

```text
3 nodes × 50K
≈ 150K ops/sec
```

6 nodes:

```text
≈ 300K ops/sec
```

assuming suitable workload and even distribution.

## Latency

```text
~2–10 ms
```

for well-designed queries.

## Deployment

Typical:

```text
3+ nodes
```

Common larger clusters:

```text
6 / 9 / 12+ nodes
```

## Scaling

```text
3 → 6 → 12 nodes
```

Cassandra is designed for horizontal scaling.

Important:

- Partition key
- Partition size
- Replication factor
- Consistency level
- Read/write pattern

---

# 10. MongoDB

## Capacity

Interview default:

```text
≈ 10K ops/sec/node
```

Rough range:

```text
5K–20K ops/sec/node
```

Depends heavily on:

- Indexes
- Schema
- Working set
- Query complexity
- Hardware

## Latency

```text
Indexed read ≈ 5–20 ms
```

## Deployment

Typical HA:

```text
3-node replica set
```

```text
        Primary
        /      Secondary  Secondary
```

## Scaling

Replica set provides:

- High availability
- Failover
- Potential read scaling

For horizontal data/capacity scaling:

```text
Sharding
```

Typical:

```text
             mongos
          /    |          Shard1 Shard2 Shard3
```

Each shard is typically itself a replica set.

---

# 11. Normal Java API Server

Highly workload-dependent.

## Capacity

Reasonable I/O-heavy API:

```text
~1K–5K RPS/server
```

Interview default:

```text
≈ 2K RPS/server
```

CPU-heavy:

```text
~100–500 RPS
```

Very simple I/O-heavy APIs:

```text
5K–20K+ RPS
```

## Example

Peak:

```text
100K RPS
```

Assumption:

```text
2K RPS/server
```

Servers:

```text
100K / 2K
= 50 servers
```

With headroom:

```text
≈ 60–70 servers
```

## Typical server assumption

```text
4–8 CPU cores
8–16 GB RAM
```

Useful default:

```text
8 CPU
16 GB RAM
2K RPS
```

## Deployment

Simple:

```text
1
```

Production:

```text
2–5+
```

Large:

```text
10s / 100s+
```

---

# 12. Load Balancer

Modern managed load balancers are not represented well by one fixed RPS number.

Interview range:

```text
≈ 10K–100K+ RPS
```

Large managed infrastructure can handle substantially more.

## Latency

```text
~1–5 ms
```

## Deployment

Typical:

```text
Managed LB
```

or:

```text
2+ LB instances
```

for self-managed HA.

Architecture:

```text
             Load Balancer
            /      |                API     API     API
```

---

# 13. API Gateway

## Capacity

```text
≈ 10K–100K+ RPS
```

## Latency

```text
~1–10 ms
```

## Deployment

```text
2+ instances
```

or managed.

## Responsibilities

- Authentication
- Authorization
- Routing
- Rate limiting
- TLS termination
- Request validation
- API versioning
- Request transformation

---

# 14. Elasticsearch / OpenSearch

## Capacity

Rough:

```text
5K–20K search QPS/node
```

Depends heavily on:

- Query complexity
- Index size
- Shards
- Aggregations
- Hardware

## Latency

```text
~10–100 ms
```

## Deployment

Typical:

```text
3+ nodes
```

Larger:

```text
6+
```

## Scaling

```text
Shards + Nodes
```

---

# 15. Message Queue

For RabbitMQ-like general-purpose messaging:

```text
10K–50K messages/sec/node
```

Actual capacity depends on:

- Persistent vs non-persistent messages
- Message size
- Acknowledgments
- Consumers
- Disk
- Replication

## Latency

```text
~5–50 ms
```

## Deployment

Typical production:

```text
3 nodes
```

Larger:

```text
3–6+
```

For very high-throughput event streaming, Kafka is generally the more natural choice.

---

# 16. CDN

Don't memorize one RPS limit.

Interview assumption:

```text
Millions+ requests/sec globally
```

Focus on cache hit ratio.

Example:

```text
100K RPS
90% cache hit
```

Origin traffic:

```text
100K × 10%
= 10K RPS
```

## Latency

```text
~10–50 ms
```

depending on geography and cache status.

---

# 17. Object Storage

S3-like object storage is appropriate for:

- Images
- Videos
- PDFs
- Backups
- Large files
- Archives

Capacity:

```text
Very high / horizontally scalable
```

Don't invent a fixed RPS number in an interview.

Typical latency:

```text
~50–200 ms
```

---

# 18. Distributed Cache

Usually:

```text
Redis
```

Use for:

- Sessions
- Frequently accessed objects
- Rate limiting
- Distributed locks
- Counters
- Temporary data

Typical:

```text
~100K ops/sec/node
~0.2–1 ms
```

---

# 19. Distributed Lock

Common implementation:

```text
Redis
```

Example:

```text
SET lock:user:123 value NX EX 30
```

Rough latency:

```text
~1 ms
```

Capacity follows Redis capacity.

Use carefully. Alternatives such as database constraints, optimistic concurrency, or idempotency may be simpler.

---

# 20. DNS

Usually don't calculate DNS capacity.

Think:

```text
Client
  ↓
DNS
  ↓
Load Balancer
  ↓
Application
```

Focus on:

- TTL
- Failover
- Routing
- Health checks
- Geographic routing

---

# 21. Availability

| Availability | Downtime/year |
|---|---:|
| 99% | 3.65 days |
| 99.9% | 8.76 hours |
| 99.99% | 52.6 minutes |
| 99.999% | 5.26 minutes |
| 99.9999% | 31.5 seconds |

---

# 22. Bandwidth

| Network | Approximate throughput |
|---|---:|
| 1 Gbps | 125 MB/s |
| 10 Gbps | 1.25 GB/s |
| 100 Gbps | 12.5 GB/s |

Formula:

```text
Bytes/sec = Bits/sec / 8
```

---

# 23. Storage Calculation

Formula:

```text
Data/day
=
writes/sec × bytes/write × 86,400
```

Example:

```text
100K writes/sec
1 KB/write
```

```text
100K × 1 KB
≈ 100 MB/sec

100 MB × 86,400
≈ 8.64 TB/day
```

Annual:

```text
8.64 × 365
≈ 3.15 PB/year
```

RF = 3:

```text
3.15 × 3
≈ 9.45 PB physical storage/year
```

---

# 24. Common Data Sizes

| Data | Rough size |
|---|---:|
| Integer | 4 B |
| Long | 8 B |
| UUID | 16 B |
| Timestamp | 8 B |
| Short string | 10–100 B |
| Email | ~30–100 B |
| User record | ~0.5–2 KB |
| Chat message | ~200 B–2 KB |
| Social post | ~1–5 KB |
| Image | ~0.5–5 MB |
| Video | Highly variable |

Use these only for rough calculations.

---

# 25. Read / Write Calculation

Suppose:

```text
100K API RPS
80:20 read/write
```

Then:

```text
Reads  = 80K RPS
Writes = 20K RPS
```

If every request performs 2 DB reads:

```text
DB reads
= 80K × 2
= 160K QPS
```

Always calculate **actual component operations**, not only API RPS.

---

# 26. Cache Hit Ratio

Suppose:

```text
100K API RPS
90% cache hit
```

Then:

```text
Cache = 90K RPS
DB    = 10K RPS
```

If hit ratio falls to 70%:

```text
Cache = 70K RPS
DB    = 30K RPS
```

Database traffic becomes 3× higher.

---

# 27. Replication vs Sharding

## Replication

```text
Primary
 /    R1     R2
```

Purpose:

```text
Availability
Read scaling
Fault tolerance
```

## Sharding

```text
       Application
       /    |         S1     S2    S3
```

Purpose:

```text
Horizontal capacity
Storage scaling
Write scaling
```

## Combined

```text
             Application
                  |
               Router
            /    |              S1     S2     S3
         / \    / \    /         P  R   P  R   P  R
```

---

# 28. General Production Deployment Defaults

| Component | Simple | Typical production | Large system |
|---|---:|---:|---:|
| API Server | 1 | 2–5+ | 10s–100s+ |
| Redis | 1 | 2–6+ | 10s+ shards |
| PostgreSQL | 1 | 1 primary + 1–2 replicas | Sharded / distributed |
| Cassandra | 1 | 3–6 nodes | 6–100+ |
| MongoDB | 1 | 3-node replica set | Multiple shards |
| Kafka | 1 | 3 brokers | 6–100+ |
| Elasticsearch | 1 | 3 nodes | 6+ |
| RabbitMQ | 1 | 3 nodes | 3–6+ |
| Load Balancer | 1 | Managed / 2+ | Managed / distributed |
| API Gateway | 1 | 2+ / managed | Managed / distributed |

These are starting points, not mandatory numbers.

---

# 29. Capacity Calculation Pattern

For most components:

```text
Required capacity
        ↓
Peak RPS
        ↓
Capacity per instance
        ↓
Instances required
        ↓
Add redundancy / headroom
```

Example:

```text
Peak traffic = 50K RPS
API capacity = 2K RPS/server

50K / 2K
= 25 servers

With headroom:
≈ 30 servers
```

---

# 30. Back-of-the-Hand Master Sheet

```text
================ SYSTEM DESIGN CONSTANTS ================

TIME
1 day       = 86,400 sec
1 month     ≈ 2.6M sec
1 year      ≈ 31.5M sec

TRAFFIC
1K RPS      = 86.4M RPD
10K RPS     = 864M RPD
100K RPS    = 8.64B RPD
1M RPS      = 86.4B RPD

PEAK
Peak ≈ 3–5 × average
Spiky system ≈ 10×

LATENCY
RAM         ≈ 100 ns
SSD         ≈ 100 μs
Redis       ≈ 0.2–1 ms
Same DC     ≈ 0.1–1 ms
DB          ≈ 5–20 ms
Cross DC    ≈ 50–200 ms

REDIS
≈ 100K ops/sec/node
≈ <1 ms server latency
Memory ≈ raw data × 1.3–2
Replica ≠ write scaling
Shard = capacity scaling
Typical HA = 1 primary + 1 replica
Cluster = 3+ primaries; often replicas too

KAFKA
≈ 50–150 MB/s/broker
≈ ~100K msg/s for 1 KB messages
Typical production = 3+ brokers
Scale = partitions + brokers
RF=3 → ≈ 3× physical storage

POSTGRES
≈ 5K–20K QPS/node
Use ≈ 10K QPS as default
Typical = 1 primary + 1–2 replicas
Read replicas → read scaling
Primary → writes
Sharding → horizontal scaling

CASSANDRA
≈ 10K–100K ops/sec/node
Use ≈ 50K default
Typical = 3+ nodes
Scale horizontally

MONGODB
≈ 5K–20K ops/sec/node
Use ≈ 10K default
Typical = 3-node replica set
Replica ≠ 3× write capacity
Sharding → horizontal capacity

API SERVER
≈ 1K–5K RPS/server
Use ≈ 2K default
Typical = 2–5+ instances
≈ 8 CPU / 16 GB RAM

LOAD BALANCER
≈ 10K–100K+ RPS
Latency ≈ 1–5 ms
Typical = managed or 2+ HA

API GATEWAY
≈ 10K–100K+ RPS
Latency ≈ 1–10 ms
Typical = 2+ / managed

ELASTICSEARCH
≈ 5K–20K search QPS/node
Latency ≈ 10–100 ms
Typical = 3+ nodes
Scale = shards + nodes

MESSAGE QUEUE
≈ 10K–50K msg/s/node
Latency ≈ 5–50 ms
Typical = 3+ nodes

CDN
Millions+ requests/sec
Think CACHE HIT RATIO
90% hit → origin gets 10%

OBJECT STORAGE
Very high / horizontally scalable
Typical latency ≈ 50–200 ms

AVAILABILITY
99%      = 3.65 days
99.9%    = 8.76 hours
99.99%   = 52.6 min
99.999%  = 5.26 min
99.9999% = 31.5 sec

BANDWIDTH
1 Gbps   = 125 MB/s
10 Gbps  = 1.25 GB/s
100 Gbps = 12.5 GB/s

STORAGE
Data/day = writes/sec × bytes × 86,400

100K writes/sec × 1 KB
≈ 100 MB/sec
≈ 8.64 TB/day
≈ 3.15 PB/year

READ/WRITE
100K API RPS
80:20 read/write
→ 80K reads
→ 20K writes

CACHE
100K API RPS
90% hit
→ 90K cache
→ 10K DB

REPLICATION
Replication → HA / read scaling
Sharding    → capacity / write scaling
```

---

# 31. Interview Rules

1. Use ranges, not fake precision.
2. State your assumption explicitly.
3. Calculate peak RPS, not only average.
4. Convert API RPS into actual DB/cache/message operations.
5. Calculate storage in GB/TB/PB per day/year.
6. Account for replication when calculating physical storage.
7. Distinguish replication from sharding.
8. Add headroom rather than designing at 100% utilization.
9. For Kafka, think partitions + MB/s + brokers.
10. For databases, query complexity matters more than a universal QPS.
11. For Redis, workload and memory overhead matter.
12. For API servers, capacity is highly workload-dependent.
13. Don't over-engineer a 1K RPS system as if it handles 1M RPS.

## Master Mental Model

```text
Requirements
     ↓
Users
     ↓
Requests/day
     ↓
Average RPS
     ↓
Peak RPS
     ↓
Request size
     ↓
Bandwidth
     ↓
Reads/Writes
     ↓
Cache / DB / Queue operations
     ↓
Per-instance capacity
     ↓
Number of instances
     ↓
Replication + Sharding
     ↓
Storage + Failure handling
```
