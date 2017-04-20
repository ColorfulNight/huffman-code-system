import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * TODO 序列化解码树
 */
@Setter
@Getter
public class Encode implements Action {
    private HashMap<Byte, HuffmanNode> huffmanTree = new HashMap<>();
    private String inputString;
    private HuffmanNode root;
    private byte[] result;
    final String inputPath = "D:\\encode.txt";
    final String outputPath = "D:\\encoded.txt";

    /**
     * 执行编码函数，并输出结果
     */
    public String action() {
        initLeafNode();
        calculateWeignt();
        buildHuffmanTree();
        setCodeInHuffmanTree();
        encodeInput();
        return new String(result, Charset.forName("UTF-8"));
    }

    @Override
    public void input() {
        File file = new File(inputPath);
        StringBuilder stringInFile = new StringBuilder();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String bufferString;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            while ((bufferString = bufferedReader.readLine()) != null) {
                stringInFile.append(bufferString).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        setInputString(stringInFile.toString());
    }

    @Override
    public void output() {
        File file = new File(outputPath);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 初始化叶子节点
     */
    private void initLeafNode() {
        byte str[] = inputString.getBytes();
        HuffmanNode node;
        for (byte b : str) {
            HuffmanNode.total++;
            node = huffmanTree.get(b);
            if (node != null) {
                node.setFrequence(node.getFrequence() + 1);
                continue;
            }
            node = new HuffmanNode();
            huffmanTree.put(b, node);
        }
    }

    /**
     * 计算叶子节点权值
     */
    private void calculateWeignt() {
        ArrayList<HuffmanNode> nodes = new ArrayList<>(huffmanTree.values());
        for (HuffmanNode leafNode : nodes) {
            float weight = (float) leafNode.getFrequence() / HuffmanNode.total;
            leafNode.setWeight(weight);
        }
    }

    /**
     * 根据权值生成赫夫曼树
     */
    private void buildHuffmanTree() {
        float weight;
        HuffmanNode first;
        HuffmanNode second;
        HuffmanNode newNode;
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        ArrayList<HuffmanNode> nodes = new ArrayList<>(huffmanTree.values());
        priorityQueue.addAll(nodes);
        while (priorityQueue.size() > 1) {
            first = priorityQueue.poll();
            second = priorityQueue.poll();
            weight = first.getWeight() + second.getWeight();
            newNode = new HuffmanNode(weight);
            newNode.setLeftChild(first);
            newNode.setRightChild(second);
            first.setParent(newNode);
            second.setParent(newNode);
            huffmanTree.put(null, newNode);
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
            root.setByteCode(new Byte("0"));
            return;
        }
        setCodeInHuffmanTree(root.getLeftChild(), "0");
        setCodeInHuffmanTree(root.getRightChild(), "1");
    }

    /**
     * 设置结点赫夫曼编码，若本结点为叶子节点则设置后返回，否则进入左右孩子
     *
     * @param node 某一节点，或为叶子，或有左右孩子
     * @param code 本结点的赫夫曼编码
     */
    private void setCodeInHuffmanTree(HuffmanNode node, String code) {
        if (node.getLeftChild() == null) {
            node.setByteCode(new Byte(code));
            return;
        }
        HuffmanNode leftChild = node.getLeftChild();
        HuffmanNode rightChild = node.getRightChild();
        setCodeInHuffmanTree(leftChild, code + "0");
        setCodeInHuffmanTree(rightChild, code + "1");
    }

    /**
     * 对输入的字符按赫夫曼编码进行编码
     *
     */
    private void encodeInput() {
        byte bs[] = inputString.getBytes();
        for (int i = 0; i < bs.length; i++) {
            result[i] = huffmanTree.get(bs[i]).getByteCode();
        }
    }

//
//    /**
//     * 序列化赫夫曼树
//     */
//    private void serializeDecodeTree() {
//        FileOutputStream fileOutputStream = null;
//        ObjectOutputStream objectOutputStream = null;
//        try {
//            fileOutputStream = new FileOutputStream(path);
//            objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(decodeTree);
//        } catch (java.io.IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (objectOutputStream != null) {
//                try {
//                    objectOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (fileOutputStream != null) {
//                try {
//                    fileOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
}

/**
 * TODO 完成解码类相关
 * 解码类
 */
//@Setter
//@Getter
//class Decode implements Action {
//    private HashMap<String, String> decodeTree = new HashMap<>();
//    final String inputPath = "D:\\decode.txt";
//    final String outputPath = "D:\\decoded.txt";
//
//    /**
//     * 解码工作方法
//     */
//    public String action(String string) {
//        unserializeDecodeTree();
//        return decodeString(string);
//    }
//
//    /**
//     * 反序列化解码树对象
//     */
//    @SuppressWarnings("unchecked")
//    private void unserializeDecodeTree() {
//        FileInputStream fileInputStream = null;
//        ObjectInputStream objectInputStream = null;
//        try {
//            fileInputStream = new FileInputStream(path);
//            objectInputStream = new ObjectInputStream(fileInputStream);
//            decodeTree = (HashMap<String, String>) objectInputStream.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 执行解码并输出结果
//     *
//     * @param string 要解码的字符串
//     */
//    private String decodeString(String string) {
//        int i = 1;
//        String code;
//        String nextCode;
//        StringBuilder decodeString = new StringBuilder(string);
//        StringBuilder result = new StringBuilder();
//        while (i <= decodeString.length()) {
//            code = decodeString.substring(0, i);
//            if (decodeTree.get(code) == null) {
//                i++;
//                continue;
//            }
//            if (i + 1 <= decodeString.length()) {
//                nextCode = decodeString.substring(0, i + 1);
//                if (decodeTree.get(nextCode) != null) {
//                    i++;
//                    continue;
//                }
//                result.append(decodeTree.get(code));
//                decodeString.delete(0, i);
//                i = 1;
//                continue;
//            }
//            result.append(decodeTree.get(code));
//            decodeString.delete(0, i);
//        }
//        return result.toString();
//    }
//}

///**
// * 用于构建huffman树时的Comparator
// */
//class HuffmanComparator implements Comparator<CreateHuffman> {
//
//    @Override
//    public int compare(CreateHuffman o1, CreateHuffman o2) {
//        if (o1.getWeight() > o2.getWeight())
//            return 1;
//        if (o2.getWeight() > o1.getWeight())
//            return -1;
//        return 0;
//    }
//}
//
///**
// * 构建huffman树时的辅助类
// */
//@Setter
//@Getter
//@AllArgsConstructor
//@EqualsAndHashCode
//class CreateHuffman {
//    private Byte code;
//    private float weight;
//}