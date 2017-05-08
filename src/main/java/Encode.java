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
        if (string != null) {
            setInputString(string);
        } else {
            input();
        }
        calculateNode();
        buildHuffmanTree();
        setCodeInHuffmanTree();
        result = encodedCode();
        serializeDecodeTree();
        if (string == null) {
            output();
        }
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
        HuffmanInfo newNode;
        HuffmanInfo first;
        HuffmanInfo second;
        PriorityQueue<HuffmanInfo> priorityQueue = new PriorityQueue<>();
        ArrayList<HuffmanInfo> list = new ArrayList<>(huffmanTree.values());
        priorityQueue.addAll(list);
        while (priorityQueue.size() > 1) {
            first = priorityQueue.poll();
            second = priorityQueue.poll();
            code = first.getCode() + second.getCode();
            weight = first.getWeight() + second.getWeight();
            newNode = new HuffmanInfo(weight, code);
            newNode.setLeftChild(first.getCode());
            newNode.setRightChild(second.getCode());
            first.setParent(code);
            second.setParent(code);
            huffmanTree.put(code, newNode);
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
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < inputString.length(); i++) {
            huffmanInfo = huffmanTree.get(inputString.substring(i, i + 1));
            temp.append(huffmanInfo.getHuffmanCode());
        }
        decodeTree.put("resultLength", String.valueOf(temp.length()));
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
        StringBuilder stringInFile = new StringBuilder();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String buffer;
            while ((buffer = bufferedReader.readLine()) != null) {
                stringInFile.append(buffer).append("\n");
            }
            stringInFile.deleteCharAt(stringInFile.length() - 1);
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
}