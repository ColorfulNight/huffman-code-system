import java.util.Scanner;

public class MainEnter {
    public static void main(String[] args) {
        System.out.println("*********************");
        System.out.println("编码：");
        doTask(new Encode());
        System.out.println("*********************");
        System.out.println("解码：");
        doTask(new Decode());
    }

    private static void doTask(Action action) {
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
            action.action(null);
            System.out.println("已输出到文本");
        }
    }

    private int choiceInput(String type) {
        System.out.println("选择" + type + "种类：1为控制台，2为从文本文件");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
