import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainEnter {
    public static void main(String[] args) {
        StringBuilder inputString = new StringBuilder();
        Encode encode = new Encode();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("输入要编码的字符");
        try {
            inputString.append(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        encode.encodeString(inputString);

        inputString.delete(0,inputString.length()+1);

        System.out.println("输入解码字符");
        try {
            inputString.append(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        encode.decodeString(inputString);
    }
}
