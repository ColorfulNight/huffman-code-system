import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.BitSet;
import java.util.HashMap;

/**
 * 解码类
 */
@Setter
@Getter
public class Decode implements Action {
    private HashMap<String, String> decodeTree = new HashMap<>();
    final String inputPath = "D:\\encoded.txt";
    final String outputPath = "D:\\decoded.txt";
    String result;
    String inputString;
    int resultLength;

    /**
     * 解码工作方法
     */
    public String action(String string) {
        unserializeDecodeTree();
        if (string != null) {
            inputString = string;
            decodeString();
            return result;
        } else {
            input();
            decodeString();
            output();
            return null;
        }

    }

    @Override
    public void output() {
        File file = new File(outputPath);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(result);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void input() {
        byte[] bytes = new byte[1024];
        BitSet bitSet;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(inputPath));
            fileInputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        bitSet = BitSet.valueOf(bytes);
        for (int i = 0; i < resultLength; i++) {
            if (bitSet.get(i)) {
                stringBuilder.append("1");
            } else {
                stringBuilder.append("0");
            }
        }
        setInputString(stringBuilder.toString());
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
            resultLength = Integer.parseInt(decodeTree.get("resultLength"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行解码并输出结果
     */
    private void decodeString() {
        int i = 1;
        String code;
        String nextCode;
        StringBuilder decodeString = new StringBuilder(inputString);
        StringBuilder temp = new StringBuilder();
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
                temp.append(decodeTree.get(code));
                decodeString.delete(0, i);
                i = 1;
                continue;
            }
            temp.append(decodeTree.get(code));
            decodeString.delete(0, i);
        }
        result = temp.toString();
    }
}
