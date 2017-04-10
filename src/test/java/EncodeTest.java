import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EncodeTest {
    private static Encode encode;
    private static Class clazz = Encode.class;
    private static HuffmanInfo[] expectedInfos = new HuffmanInfo[3];
    private static HuffmanInfo[] result = new HuffmanInfo[3];
    private static Map<String, HuffmanInfo> huffmanTree;


    @BeforeClass
    public static void beforeAll() {
        try {
            encode = new Encode();
            encode.setInputString("123121");
            Method calculateNodeMethod = clazz.getDeclaredMethod("calculateNode");
            calculateNodeMethod.setAccessible(true);
            calculateNodeMethod.invoke(encode);
            Method buildHuffmanTreeMethod = clazz.getDeclaredMethod("buildHuffmanTree");
            buildHuffmanTreeMethod.setAccessible(true);
            buildHuffmanTreeMethod.invoke(encode);
            Method setCodeInHuffmanTreeMethod = clazz.getDeclaredMethod("setCodeInHuffmanTree");
            setCodeInHuffmanTreeMethod.setAccessible(true);
            huffmanTree = encode.getHuffmanTree();
            setCodeInHuffmanTreeMethod.invoke(encode);
            result[0] = huffmanTree.get("1");
            result[1] = huffmanTree.get("2");
            result[2] = huffmanTree.get("3");
            expectedInfos[0] = new HuffmanInfo(3, 0.5f, "1", new StringBuilder("0"), null, null, "132");
            expectedInfos[1] = new HuffmanInfo(2, (1 / 3f), "2", new StringBuilder("11"), null, null, "32");
            expectedInfos[2] = new HuffmanInfo(1, (1 / 6f), "3", new StringBuilder("10"), null, null, "32");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1CalculateNode() {
        /* 测试总数*/
        Assert.assertEquals(8, HuffmanInfo.total);
        /* 测试次数计算 */
        Integer[] expectedTime = {3, 2, 1};
        Integer[] actualTimes = new Integer[3];
        actualTimes[0] = result[0].getTimes();
        actualTimes[1] = result[1].getTimes();
        actualTimes[2] = result[2].getTimes();
        Assert.assertArrayEquals(expectedTime,actualTimes);
        /* 测试权重计算*/
        Float[] expectedWeight = {0.5f, (1 / 3f), (1 / 6f)};
        Float[] actualWeight = new Float[3];
        actualWeight[0] = result[0].getWeight();
        actualWeight[1] = result[1].getWeight();
        actualWeight[2] = result[2].getWeight();
        Assert.assertArrayEquals(expectedWeight, actualWeight);
    }




    @Test
    public void test2BuildHuffmanTree() {
        String[] expectedParent ={"132","32","32"};
        String[] actualParent = new String[3];
        actualParent[0] = result[0].getParent();
        actualParent[1] = result[1].getParent();
        actualParent[2] = result[2].getParent();
        Assert.assertArrayEquals(expectedParent, actualParent);
        Assert.assertEquals("132", encode.getRoot());
    }

    @Test
    public void test3SetCodeInHuffmanTree() {
        Assert.assertArrayEquals(expectedInfos, result);
    }
    @Test
    public void test4EncodeString() throws Exception {
        encode = new Encode();
        encode.encodeString(new StringBuilder("123121"));
        String actual = encode.getResult();
        String expected = "011100110";
        Assert.assertEquals(expected, actual);
    }

    @org.junit.Test
    public void test5EecodeString() throws Exception {
        encode = new Encode();
        encode.encodeString(new StringBuilder("123121"));
        encode.decodeString(new StringBuilder("011100110"));
        String actual = encode.getResult();
        String expected = "123121";
        Assert.assertEquals(expected, actual);
    }
}