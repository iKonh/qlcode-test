package test;

import java.util.ArrayList;
import java.util.List;

/**
 * 7566:老王装货 (01背包)
 */
public class Test {

    public static int[] goods = {509, 838, 924, 650, 604, 793, 564, 651, 697, 649, 747, 787, 701, 605, 644};
    public static int max = 5000;
    public static void main(String[] args) {
//        Arrays.sort(goods);
        SumObject sumObject  = getSum(0, 0);
        System.out.println(sumObject.sum);
        System.out.println(sumObject.indexList);
        for (int index : sumObject.indexList) {
            System.out.println(goods[index]);
        }
    }

    public static SumObject getSum(int index, int sum) {
        if (index == goods.length) {
            SumObject sumObject = new SumObject();
            sumObject.sum = sum;
            sumObject.indexList = new ArrayList<>();
            return sumObject;
        }
        int kg = goods[index];
        int tmp = sum + kg;
        // 若超过5000kg不考虑之后的货物
        if (tmp > max) {
            return getSum(index + 1, sum);
        }
        // 添加本货物
        SumObject sumObject0 = getSum(index + 1, tmp);
        // 不添加本货物
        SumObject sumObject1 = getSum(index + 1, sum);
        int sum0 = sumObject0.sum;
        int sum1 = sumObject1.sum;
        if (sum0 > sum1) {
            sumObject0.indexList.add(index);
            return sumObject0;
        } else {
            return sumObject1;
        }
    }

    public static class SumObject {
        public int sum;
        public List<Integer> indexList;
    }
}
