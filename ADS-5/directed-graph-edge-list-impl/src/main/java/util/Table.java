package util;
import java.util.List;

public class Table {

    public static void Draw(List<String> headers,List<Object> content) {
        drawLine(headers.size());
        makeHeader(headers);
        drawLine(headers.size());
        var index=1;
        for (Object o : content) {
            System.out.format("|%-5s|%s\n",index,o);
            index++;
        }
        drawLine(headers.size());
    }

    private static void drawLine(Integer columns) {
        System.out.print('+');
        for (int i = 0; i < 5; i++) {
            System.out.print('-');
        }
        System.out.print('+');
        for(int c = 0; c< columns; c++) {
            for (int i = 0; i < 45; i++) {
                System.out.print('-');
            }
            System.out.print("+");
        }
        System.out.print('\n');
    }

    private static void makeHeader(List<String> headers) {
        System.out.print('|');
        System.out.format("%-5s","");
        System.out.print('|');
        for(String header:headers) {
            System.out.format("%-45s",header);
            System.out.print("|");
        }
        System.out.print('\n');
    }


}
