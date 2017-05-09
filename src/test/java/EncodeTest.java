import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EncodeTest {
    private static Encode encode;
    private static HuffmanInfo[] actualEncode = new HuffmanInfo[3];
    private static HashMap<Byte, HuffmanInfo> huffmanLeaves;
    private static Byte[] bytes = ArrayUtils.toObject("123".getBytes());

    @BeforeClass
    public static void beforeAll() {
        System.out.println("input : 123121");
        encode = new Encode();
        encode.action(null);
        huffmanLeaves = encode.getHuffmanTreeLeaves();
        actualEncode[0] = huffmanLeaves.get(bytes[0]);
        actualEncode[1] = huffmanLeaves.get(bytes[1]);
        actualEncode[2] = huffmanLeaves.get(bytes[2]);
    }

    @Test
    public void test1EncodeString() {
        String actual = encode.getResult();
        String expected = "011100110";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test2CalculateNode() {
        /* 测试总数*/
        Assert.assertEquals(6, encode.getTotal());
        /* 测试次数计算 */
        Integer[] expectedTime = {3, 2, 1};
        Integer[] actualTimes = new Integer[3];
        actualTimes[0] = actualEncode[0].getTimes();
        actualTimes[1] = actualEncode[1].getTimes();
        actualTimes[2] = actualEncode[2].getTimes();
        Assert.assertArrayEquals(expectedTime, actualTimes);
        /* 测试权重计算*/
        Float[] expectedWeight = {0.5f, (1 / 3f), (1 / 6f)};
        Float[] actualWeight = new Float[3];
        actualWeight[0] = actualEncode[0].getWeight();
        actualWeight[1] = actualEncode[1].getWeight();
        actualWeight[2] = actualEncode[2].getWeight();
        Assert.assertArrayEquals(expectedWeight, actualWeight);
    }

    @Test
    public void test3BuildHuffmanTree() {
        HuffmanInfo actualRoot = encode.getRoot();
        Assert.assertEquals(actualEncode[0], actualRoot.getLeftChild());
        Assert.assertEquals(actualEncode[1], actualRoot.getRightChild().getRightChild());
        Assert.assertEquals(actualEncode[2], actualRoot.getRightChild().getLeftChild());
    }

    @Test
    public void test4SetCodeInHuffmanTree() {
        Byte[] actualDecodeTree = new Byte[3];
        actualDecodeTree[0] = encode.getDecodeTree().get("0");
        actualDecodeTree[1] = encode.getDecodeTree().get("11");
        actualDecodeTree[2] = encode.getDecodeTree().get("10");
        Assert.assertArrayEquals(bytes, actualDecodeTree);
    }


}