package com.homeadvisor.kafdrop.model;

import java.util.*;
import java.util.stream.Collectors;

public class TopicVO implements Comparable<TopicVO>
{
   private String name;
   private Map<Integer, TopicPartitionVO> partitions = new TreeMap<>();
   private Map<String, Object> config = new TreeMap<>();
   // description?
   // partition state
   // delete supported?


   public TopicVO(String name)
   {
      this.name = name;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Map<String, Object> getConfig()
   {
      return config;
   }

   public void setConfig(Map<String, Object> config)
   {
      this.config = config;
   }

   public Collection<TopicPartitionVO> getPartitions()
   {
      return partitions.values();
   }

   public Optional<TopicPartitionVO> getPartition(int partitionId)
   {
      return Optional.ofNullable(partitions.get(partitionId));
   }

   public Collection<TopicPartitionVO> getLeaderPartitions(int brokerId)
   {
      return partitions.values().stream()
         .filter(tp -> tp.getLeader().getId() == brokerId)
         .collect(Collectors.toList());
   }

   public void setPartitions(Map<Integer, TopicPartitionVO> partitions)
   {
      this.partitions = partitions;
   }

   public double getPreferredReplicaPercent()
   {
      long preferredLeaderCount = partitions.values().stream()
         .filter(TopicPartitionVO::isLeaderPreferred)
         .count();
      return ((double) preferredLeaderCount) / ((double)partitions.size());
   }

   public void addPartition(TopicPartitionVO partition)
   {
      partitions.put(partition.getId(), partition);
   }

   @Override
   public int compareTo(TopicVO that)
   {
      return this.name.compareTo(that.name);
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      TopicVO that = (TopicVO) o;

      if (!name.equals(that.name)) return false;

      return true;
   }

   @Override
   public int hashCode()
   {
      return name.hashCode();
   }

}
