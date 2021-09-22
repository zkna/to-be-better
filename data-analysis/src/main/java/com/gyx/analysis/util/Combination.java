package com.gyx.analysis.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Combination {
    /**
     * 集合大小
     */
    private Integer nv = 0;
    /**
     * 从中挑选的个数
     */
    private Integer mv = 1;
    /**
     * 中间值
     */
    private Integer[] b = null;
    /**
     * 挑选的集合
     */
    private List<Integer> collection = null;

    List<List<Integer>> result = new ArrayList<>();

    /**
     * 从集合中筛选出组合
     * @param collection 要筛选的集合
     * @param mv 组合数
     */
    public Combination(List<Integer> collection, Integer mv) {
        this.collection = collection;
        this.mv = mv;
        this.nv = collection.size();
        this.b = new Integer[mv];
    }

    /**
     * 计算 C(n,m)
     */
    public List<List<Integer>> calc() {
        calc(nv, mv);
        return result;
    }

    /**
     * 计算C(n,m)
     *
     * @param n
     * @param m
     */
    private void calc(Integer n, Integer m) {
        int i, j;
        for (i = m; i <= n; i++) {
            b[m - 1] = i - 1;
            if (m > 1) {
                calc(i - 1, m - 1);
            } else {
                List<Integer> c = new ArrayList<>();
                for (j = 0; j <= mv - 1; j++) {
                    c.add(collection.get(b[j]));
                }
                result.add(c);
            }
        }
    }

}
