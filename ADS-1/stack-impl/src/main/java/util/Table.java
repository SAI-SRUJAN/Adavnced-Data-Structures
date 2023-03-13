package util;

import java.util.List;

public class Table {

    public static void Draw(List<String> headers,List<Object> content) {
        drawLine(headers.size());
        makeHeader(headers);
        drawLine(headers.size());
        content.forEach(System.out::println);
        drawLine(headers.size());
    }

    private static void drawLine(Integer columns) {
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
        for(String header:headers) {
            System.out.format("%-45s",header);
            System.out.print("|");
        }
        System.out.print('\n');
    }


}
