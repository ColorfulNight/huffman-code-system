import java.io.*;
import java.util.Scanner;

/**
 * TODO 完成解码输出选择
 */
public class MainEnter {
    public static void main(String[] args) {
        System.out.println("*********************");
        System.out.println("编码：");
        action(new Encode());
        System.out.println("*********************");
        System.out.println("解码：");
        action(new Decode());
    }

    private static void action(Action action) {
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
            result = action.action(string);
            System.out.println(result);
        } else {
            string = mainEnter.fileInput(action.getInputPath());
            result = action.action(string);
            mainEnter.fileOutput(result, action.getOutputPath());
        }
    }


    private int choiceInput(String type) {
        System.out.println("选择" + type + "种类：1为控制台，2为从文本文件");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private String fileInput(String filePath) {
        File file = new File(filePath);
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
        return stringInFile.toString();
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

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            byte[] bs = result.getBytes();
            fileOutputStream.write(bs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
