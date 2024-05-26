package org.test1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    final static ArrayList<String> openerSequence = new ArrayList<>(Arrays.asList("(", "{", "["));
    final static ArrayList<String> closingSequence  = new ArrayList<>(Arrays.asList(")", "}", "]"));

    public static void main(String[] args) {
        String line = null;
        Map<String, String> expressions = new HashMap<String, String>();

        System.out.println("Укажите путь к файлу:");
        //C:\sample.txt
        try (BufferedReader lineExpr = new BufferedReader(new FileReader(new Scanner(System.in).nextLine()))) {
            while ((line = lineExpr.readLine()) != null){
                if(isTheCorrectSequence(line)){
                    expressions.put("<" + line + ">", " - правильная скобочная последовательность");
                } else {
                    expressions.put("<" + line + ">", " - неправильная скобочная последовательность");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int i = 0;
        for(Map.Entry<String, String> entry: expressions.entrySet()) {
            System.out.println(entry.getKey() + entry.getValue());
        }
    }
    public static boolean isTheCorrectSequence(String line){
        ArrayList<String> expr = new ArrayList<String>();
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            //если скобка открывающая, то всегда добавляем её в список скобок
            if(openerSequence.contains(String.valueOf(ch))){
                expr.add(String.valueOf(ch));
                //если скобка закрывающая,то проверяем, сответствует ли ей последняя добавленная и удаляем элемент списка
            } else if (closingSequence.contains(String.valueOf(ch))){
                try {
                    Character c = expr.getLast().charAt(0);
                    int openerIndex = openerSequence.indexOf(expr.getLast());
                    int closingIndex = closingSequence.indexOf(String.valueOf(ch));
                    //если последнюю добавленную скобку можно удалить, то удаляем
                    if (openerIndex == closingIndex){
                        expr.removeLast();
                        //иначе это не правильная последовательность
                    } else {
                        return false;
                    }
                } catch (IndexOutOfBoundsException | NoSuchElementException e){
                    return false;
                }
            }
        }
        //если список скобок не пуст, то эта последовательность не закрыта и является некорректной
        return expr.isEmpty();
    }
}