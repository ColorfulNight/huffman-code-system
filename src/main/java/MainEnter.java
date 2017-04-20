import java.io.File;
import java.util.Scanner;

/**
 * TODO 完成解码输出选择
 */
public class MainEnter {
    public static void main(String[] args) {
        System.out.println("*********************");
        System.out.println("编码：");
        task(new Encode());
//        System.out.println("*********************");
//        System.out.println("解码：");
//        task(new Decode());
    }

    private static void task(Action action) {
        MainEnter mainEnter = new MainEnter();
        int type;
        Scanner scanner;
        String string;
        String result;
        type = mainEnter.choiceInput("输入输出");
        if (type == 1) {
            scanner = new Scanner(System.in);
            System.out.print("输入字符: ");
            string = scanner.nextLine();
            action.setInputString(string);
            result = action.action();
            System.out.println(result);
        } else {
            action.input();
            action.action();
            action.output();
        }
    }


    private int choiceInput(String type) {
        System.out.println("选择" + type + "种类：1为控制台，2为从文本文件");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private void fileOutput(String result, String filePath) {
        File file = new File(filePath);
//        FileWriter fileWriter = null;
//        BufferedWriter bufferedWriter = null;
//        try {
//            fileWriter = new FileWriter(file);
//            bufferedWriter = new BufferedWriter(fileWriter);
//            bufferedWriter.write(result);
//            bufferedWriter.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if (bufferedWriter != null){
//                try {
//                    bufferedWriter.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (fileWriter!= null){
//                try {
//                    fileWriter.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }


    }
}
