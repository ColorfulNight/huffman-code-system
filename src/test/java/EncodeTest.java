//
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class EncodeTest {
//    private static Encode encode;
//    private static HuffmanNode[] actualEncode = new HuffmanNode[3];
//    private static Map<String, HuffmanNode> huffmanTree;
//
//    @BeforeClass
//    public static void beforeAll() {
//        System.out.println("input : 123121");
//        encode = new Encode();
//        encode.setInputString("123121");
//        encode.encodeString("123121");
//        huffmanTree = encode.getHuffmanTree();
//        actualEncode[0] = huffmanTree.get("1");
//        actualEncode[1] = huffmanTree.get("2");
//        actualEncode[2] = huffmanTree.get("3");
//    }
//
//    @Test
//    public void test1EncodeString() {
//        String actual = encode.getResult();
//        String expected = "011100110";
//        Assert.assertEquals(expected, actual);
//    }
//
//    @Test
//    public void test2CalculateNode() {
//        /* 测试总数*/
//        Assert.assertEquals(8, HuffmanNode.total);
//        /* 测试次数计算 */
//        Integer[] expectedTime = {3, 2, 1};
//        Integer[] actualTimes = new Integer[3];
//        actualTimes[0] = actualEncode[0].getTimes();
//        actualTimes[1] = actualEncode[1].getTimes();
//        actualTimes[2] = actualEncode[2].getTimes();
//        Assert.assertArrayEquals(expectedTime,actualTimes);
//        /* 测试权重计算*/
//        Float[] expectedWeight = {0.5f, (1 / 3f), (1 / 6f)};
//        Float[] actualWeight = new Float[3];
//        actualWeight[0] = actualEncode[0].getWeight();
//        actualWeight[1] = actualEncode[1].getWeight();
//        actualWeight[2] = actualEncode[2].getWeight();
//        Assert.assertArrayEquals(expectedWeight, actualWeight);
//    }
//
//    @Test
//    public void test3BuildHuffmanTree() {
//        String[] expectedParent ={"132","32","32"};
//        String[] actualParent = new String[3];
//        actualParent[0] = actualEncode[0].getParent();
//        actualParent[1] = actualEncode[1].getParent();
//        actualParent[2] = actualEncode[2].getParent();
//        Assert.assertArrayEquals(expectedParent, actualParent);
//        Assert.assertEquals("132", encode.getRoot());
//    }
//
//    @Test
//    public void test4SetCodeInHuffmanTree() {
//        String[] expectedDecodeTree = {"1", "2", "3"};
//        String[] actualDecodeTree = new String[3];
//        actualDecodeTree[0] = encode.getDecodeTree().get("0");
//        actualDecodeTree[1] = encode.getDecodeTree().get("11");
//        actualDecodeTree[2] = encode.getDecodeTree().get("10");
//
//        Assert.assertArrayEquals(expectedDecodeTree, actualDecodeTree);
//    }
//
////    @Test
////    public void test5DecodeString() throws Exception {
////        encode.decodeString(new StringBuilder("011100110"));
////        String actual = encode.getResult();
////        String expected = "123121";
////        Assert.assertEquals(expected, actual);
////    }
//
//}