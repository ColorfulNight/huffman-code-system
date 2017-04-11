import org.junit.*;
import org.junit.runners.MethodSorters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO Consider the reflection is necessary or not.
 * TODO Finish the @Ignore test.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EncodeTest {
    private static Encode encode;
    private static Class clazz = Encode.class;
    private static HuffmanInfo[] expectedInfos = new HuffmanInfo[3];
    private static HuffmanInfo[] actualEncode = new HuffmanInfo[3];
    private static Map<String, HuffmanInfo> huffmanTree;
    private static Map<String,String> decodeTree;
    private static String[] excpetedDecodeTree = {"0","11","10"};
    private static String[] actualDecodeTree = new String[3];

    @BeforeClass
    public static void beforeAll() {
        try {
            System.out.println("input : 123121");
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
            setCodeInHuffmanTreeMethod.invoke(encode);
            Method decodeMethod = clazz.getDeclaredMethod("decode",StringBuilder.class);
            decodeMethod.setAccessible(true);
            decodeMethod.invoke(encode,new StringBuilder("011100110"));
            huffmanTree = encode.getHuffmanTree();
            actualEncode[0] = huffmanTree.get("1");
            actualEncode[1] = huffmanTree.get("2");
            actualEncode[2] = huffmanTree.get("3");
            expectedInfos[0] = new HuffmanInfo(3, 0.5f, "1","0", null, null, "132");
            expectedInfos[1] = new HuffmanInfo(2, (1 / 3f), "2","11", null, null, "32");
            expectedInfos[2] = new HuffmanInfo(1, (1 / 6f), "3","10", null, null, "32");
            decodeTree = encode.getDecodeTree();
            actualDecodeTree[0] = decodeTree.get("0");
            actualDecodeTree[1] = decodeTree.get("11");
            actualDecodeTree[2] = decodeTree.get("10");
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
        actualTimes[0] = actualEncode[0].getTimes();
        actualTimes[1] = actualEncode[1].getTimes();
        actualTimes[2] = actualEncode[2].getTimes();
        Assert.assertArrayEquals(expectedTime,actualTimes);
        /* 测试权重计算*/
        Float[] expectedWeight = {0.5f, (1 / 3f), (1 / 6f)};
        Float[] actualWeight = new Float[3];
        actualWeight[0] = actualEncode[0].getWeight();
        actualWeight[1] = actualEncode[1].getWeight();
        actualWeight[2] = actualEncode[2].getWeight();
        Assert.assertArrayEquals(expectedWeight, actualWeight);
    }

    @Test
    public void test2BuildHuffmanTree() {
        String[] expectedParent ={"132","32","32"};
        String[] actualParent = new String[3];
        actualParent[0] = actualEncode[0].getParent();
        actualParent[1] = actualEncode[1].getParent();
        actualParent[2] = actualEncode[2].getParent();
        Assert.assertArrayEquals(expectedParent, actualParent);
        Assert.assertEquals("132", encode.getRoot());
    }

    @Test
    public void test3SetCodeInHuffmanTree() {
        Assert.assertArrayEquals(expectedInfos, actualEncode);
    }
    @Test
    public void test4EncodeString() throws Exception {
        encode = new Encode();
        encode.encodeString(new StringBuilder("123121"));
        String actual = encode.getResult();
        String expected = "011100110";
        Assert.assertEquals(expected, actual);
    }

    @Ignore
    @org.junit.Test
    public void test5DecodeString() throws Exception {
        encode = new Encode();
        HashMap<String, String> decodeTree = new HashMap<>();
        decodeTree.put("0","1");
        decodeTree.put("10","3");
        decodeTree.put("11","2");
        encode.decodeString(new StringBuilder("011100110"));
        String actual = encode.getResult();
        String expected = "123121";
        Assert.assertEquals(expected, actual);
    }

    @Ignore
    @Test
    public void test6BuildDecodeTree(){

    }

    @Ignore
    @Test
    public void test7Decode(){

    }
}