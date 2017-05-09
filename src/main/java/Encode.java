import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.*;

/**
 * TODO 序列化解码树
 */
@Getter
@Setter
public class Encode implements Action {
    private HashMap<Byte, HuffmanInfo> huffmanTreeLeaves = new HashMap<>();
    private HashMap<String, Byte> decodeTree = new HashMap<>();
    private String inputString;
    private int total;
    private Byte[] inputBytes;
    private HuffmanInfo root;
    private String result;
    final String inputPath = "D:\\encode.txt";
    final String outputPath = "D:\\encoded.txt";

    /**
     * 执行编码函数，并输出结果
     *
     * @param string 输入的待编码字符串
     */
    public String action(String string) {
        input();
        calculateNode();
        buildHuffmanTree();
        setCodeInHuffmanTree();
        result = encodedCode();
        serializeDecodeTree();
        output();
        return result;
    }

    /**
     * 初始化叶子节点,计算叶子节点权值
     */
    private void calculateNode() {
        HuffmanInfo huffmanInfo;
        for (byte b : inputBytes) {
            if (huffmanTreeLeaves.get(b) != null) {
                huffmanTreeLeaves.get(b).setTimes(huffmanTreeLeaves.get(b).getTimes() + 1);
                total++;
                continue;
            }
            huffmanTreeLeaves.put(b, new HuffmanInfo(new Byte(b)));
            total++;
        }
        for (Map.Entry<Byte, HuffmanInfo> entry : huffmanTreeLeaves.entrySet()) {
            huffmanInfo = entry.getValue();
            float weight = (float) huffmanInfo.getTimes() / total;
            huffmanInfo.setWeight(weight);
        }
    }

    /**
     * 根据权值生成赫夫曼树
     */
    private void buildHuffmanTree() {
        float weight;
        HuffmanInfo newNode;
        HuffmanInfo first;
        HuffmanInfo second;
        PriorityQueue<HuffmanInfo> priorityQueue = new PriorityQueue<>();
        ArrayList<HuffmanInfo> list = new ArrayList<>(huffmanTreeLeaves.values());
        priorityQueue.addAll(list);
        while (priorityQueue.size() > 1) {
            first = priorityQueue.poll();
            second = priorityQueue.poll();
            weight = first.getWeight() + second.getWeight();
            newNode = new HuffmanInfo(weight);
            newNode.setLeftChild(first);
            newNode.setRightChild(second);
            first.setParent(newNode);
            second.setParent(newNode);
            priorityQueue.add(newNode);
        }
        root = priorityQueue.poll();
    }

    /**
     * 开始生成赫夫曼编码，
     * 若为单节点（只有一个字符）则设置节点编码后返回，
     * 否则进入左右孩子节点。
     */
    private void setCodeInHuffmanTree() {
        //只有一个节点
        if (root.getLeftChild() == null) {
            root.setHuffmanCode("0");
            decodeTree.put("0", root.getCode());
            return;
        }
        setCodeInHuffmanTree(root.getLeftChild(), "0");
        setCodeInHuffmanTree(root.getRightChild(), "1");
    }

    /**
     * 设置结点赫夫曼编码，若本结点为叶子节点则设置后返回，否则进入左右孩子
     *
     * @param huffmanInfo 某一节点，或为叶子，或有左右孩子
     * @param huffmanCode 本结点的赫夫曼编码
     */
    private void setCodeInHuffmanTree(HuffmanInfo huffmanInfo, String huffmanCode) {
        if (huffmanInfo.getLeftChild() == null) {
            huffmanInfo.setHuffmanCode(huffmanCode);
            decodeTree.put(huffmanCode, huffmanInfo.getCode());
            return;
        }
        HuffmanInfo leftChild = huffmanInfo.getLeftChild();
        HuffmanInfo rightChild = huffmanInfo.getRightChild();
        setCodeInHuffmanTree(leftChild, huffmanCode + "0");
        setCodeInHuffmanTree(rightChild, huffmanCode + "1");
    }

    /**
     * 对输入的字符按赫夫曼编码进行编码
     *
     * @return 编码后的字符串
     */
    private String encodedCode() {
        HuffmanInfo huffmanInfo;
        StringBuilder temp = new StringBuilder();
        for (byte b : inputBytes) {
            huffmanInfo = huffmanTreeLeaves.get(b);
            temp.append(huffmanInfo.getHuffmanCode());
        }
        return temp.toString();
    }

    /**
     * 序列化赫夫曼树
     */
    private void serializeDecodeTree() {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(decodeTree);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private byte[] packEncodedCharsToByte() {
        BitSet bitSet = new BitSet();
        String temp;
        for (int i = 0; i < result.length(); i++) {
            temp = result.substring(i, i + 1);
            if (Integer.valueOf(temp) == 1) {
                bitSet.set(i);
            } else {
                bitSet.clear(i);
            }
        }
        return bitSet.toByteArray();
    }

    @Override
    public void output() {
        byte[] bytes = packEncodedCharsToByte();
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(new File(outputPath));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(bytes);
            byteArrayOutputStream.writeTo(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void input() {
        File file = new File(inputPath);

        FileInputStream fileInputStream = null;
        int length;
        byte[] bytes = new byte[1024];
        ArrayList<Byte> arrayList = new ArrayList<>();
        try {
            fileInputStream = new FileInputStream(file);
            while ((length = fileInputStream.read(bytes)) != -1) {
//                arrayList.addAll(Arrays.asList(ArrayUtils.toObject(bytes)));
                for (int i = 0; i < length; i++) {
                    arrayList.add(bytes[i]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Byte[] temp = new Byte[arrayList.size()];
        arrayList.toArray(temp);
        setInputBytes(temp);
    }
}