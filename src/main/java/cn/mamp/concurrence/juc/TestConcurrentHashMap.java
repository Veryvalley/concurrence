package cn.mamp.concurrence.juc;

/**
 * 一. HashTable: 同步
 *  1.效率低,很少用
 *  2. 复合操作存在安全问题, "若不存在,则添加" ,"若存在,则删除"
 *
 * 二. ConcurrentHashMap 1.8之前 锁分段机制(每个分段一个锁) ,1.8后,采用 CAS 机制(减少线程切换,相当于无锁[自旋锁])
 *
 *  @author mamp
 * @data 2020/4/19
 */
public class TestConcurrentHashMap {
}
