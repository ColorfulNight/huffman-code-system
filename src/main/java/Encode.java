import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * TODO 序列化解码树
 */
@Getter
@Setter
public class Encode implements Action {
    private HashMap<String, HuffmanInfo> huffmanTree = new HashMap<>();
    private HashMap<String, String> decodeTree = new HashMap<>();
    private String inputString;
    private String root;
    private String result;
    final String inputPath = "D:\\encode.txt";
    final String outputPath = "D:\\encoded.txt";

    /**
     * 执行编码函数，并输出结果
     *
     * @param string 输入的待编码字符串
     */
    public String action(String string) {
        setInputString(string);
        calculateNode();
        buildHuffmanTree();
        setCodeInHuffmanTree();
        result = encodedCode();
        serializeDecodeTree();
        return result;
    }

    /**
     * 初始化叶子节点,计算叶子节点权值
     */
    private void calculateNode() {
        String str;
        HuffmanInfo huffmanInfo;
        for (int i = 0; i < inputString.length(); i++) {
            str = inputString.substring(i, i + 1);
            if (huffmanTree.get(str) != null) {
                huffmanTree.get(str).setTimes(huffmanTree.get(str).getTimes() + 1);
                HuffmanInfo.total++;
                continue;
            }
            huffmanTree.put(str, new HuffmanInfo(str));
        }
        for (Map.Entry<String, HuffmanInfo> entry : huffmanTree.entrySet()) {
            huffmanInfo = entry.getValue();
            float weight = (float) huffmanInfo.getTimes() / HuffmanInfo.total;
            huffmanInfo.setWeight(weight);
        }
    }

    /**
     * 根据权值生成赫夫曼树
     */
    private void buildHuffmanTree() {
        String code;
        float weight;
        CreateHuffman createHuffman;
        CreateHuffman createHuffman1;
        CreateHuffman newNode;
        HuffmanInfo huffmanInfo;
        PriorityQueue<CreateHuffman> priorityQueue = new PriorityQueue<>(new HuffmanComparator());
        for (Map.Entry<String, HuffmanInfo> entry : huffmanTree.entrySet()) {
            code = entry.getKey();
            weight = entry.getValue().getWeight();
            createHuffman = new CreateHuffman(code, weight);
            priorityQueue.add(createHuffman);
        }
        while (priorityQueue.size() > 1) {
            createHuffman = priorityQueue.poll();
            createHuffman1 = priorityQueue.poll();
            code = createHuffman.getCode() + createHuffman1.getCode();
            weight = createHuffman.getWeight() + createHuffman1.getWeight();
            huffmanInfo = new HuffmanInfo(weight, code);
            huffmanInfo.setLeftChild(huffmanTree.get(createHuffman.getCode()).getCode());
            huffmanInfo.setRightChild(huffmanTree.get(createHuffman1.getCode()).getCode());
            huffmanTree.get(createHuffman.getCode()).setParent(code);
            huffmanTree.get(createHuffman1.getCode()).setParent(code);
            huffmanTree.put(code, huffmanInfo);
            newNode = new CreateHuffman(code, weight);
            priorityQueue.add(newNode);
        }
        root = priorityQueue.poll().getCode();
    }

    /**
     * 开始生成赫夫曼编码，
     * 若为单节点（只有一个字符）则设置节点编码后返回，
     * 否则进入左右孩子节点。
     */
    private void setCodeInHuffmanTree() {
        HuffmanInfo huffmanInfo = huffmanTree.get(root);
        //只有一个节点
        if (huffmanTree.get(huffmanInfo.getLeftChild()) == null) {
            huffmanInfo.setHuffmanCode("0");
            decodeTree.put("0", huffmanInfo.getCode());
            return;
        }
        String leftChild = huffmanInfo.getLeftChild();
        String rightChild = huffmanInfo.getRightChild();
        setCodeInHuffmanTree(huffmanTree.get(leftChild), "0");
        setCodeInHuffmanTree(huffmanTree.get(rightChild), "1");
    }

    /**
     * 设置结点赫夫曼编码，若本结点为叶子节点则设置后返回，否则进入左右孩子
     *
     * @param huffmanInfo 某一节点，或为叶子，或有左右孩子
     * @param huffmanCode 本结点的赫夫曼编码
     */
    private void setCodeInHuffmanTree(HuffmanInfo huffmanInfo, String huffmanCode) {
        if (huffmanTree.get(huffmanInfo.getLeftChild()) == null) {
            huffmanInfo.setHuffmanCode(huffmanCode);
            decodeTree.put(huffmanCode, huffmanInfo.getCode());
            return;
        }
        HuffmanInfo leftChild = huffmanTree.get(huffmanInfo.getLeftChild());
        HuffmanInfo rightChild = huffmanTree.get(huffmanInfo.getRightChild());
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
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < inputString.length(); i++) {
            huffmanInfo = huffmanTree.get(inputString.substring(i, i + 1));
            result.append(huffmanInfo.getHuffmanCode());
        }
        return result.toString();
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
}

/**
 * TODO 完成解码类相关
 * 解码类
 */
@Setter
@Getter
class Decode implements Action {
    private HashMap<String, String> decodeTree = new HashMap<>();
    final String inputPath = "D:\\decode.txt";
    final String outputPath = "D:\\decoded.txt";

    /**
     * 解码工作方法
     */
    public String action(String string) {
        unserializeDecodeTree();
        return decodeString(string);
    }

    /**
     * 反序列化解码树对象
     */
    @SuppressWarnings("unchecked")
    private void unserializeDecodeTree() {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            objectInputStream = new ObjectInputStream(fileInputStream);
            decodeTree = (HashMap<String, String>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行解码并输出结果
     *
     * @param string 要解码的字符串
     */
    private String decodeString(String string) {
        int i = 1;
        String code;
        String nextCode;
        StringBuilder decodeString = new StringBuilder(string);
        StringBuilder result = new StringBuilder();
        while (i <= decodeString.length()) {
            code = decodeString.substring(0, i);
            if (decodeTree.get(code) == null) {
                i++;
                continue;
            }
            if (i + 1 <= decodeString.length()) {
                nextCode = decodeString.substring(0, i + 1);
                if (decodeTree.get(nextCode) != null) {
                    i++;
                    continue;
                }
                result.append(decodeTree.get(code));
                decodeString.delete(0, i);
                i = 1;
                continue;
            }
            result.append(decodeTree.get(code));
            decodeString.delete(0, i);
        }
        return result.toString();
    }
}

/**
 * 用于构建huffman树时的Comparator
 */
class HuffmanComparator implements Comparator<CreateHuffman> {

    @Override
    public int compare(CreateHuffman o1, CreateHuffman o2) {
        if (o1.getWeight() > o2.getWeight())
            return 1;
        if (o2.getWeight() > o1.getWeight())
            return -1;
        return 0;
    }
}

/**
 * 构建huffman树时的辅助类
 */
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
class CreateHuffman {
    private String code;
    private float weight;
}