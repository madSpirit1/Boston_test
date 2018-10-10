package generator.FileWriter;

import generator.FileLoader.XMLLoader;
import generator.FileLoader.column;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import java.util.ArrayList;

/**
 * класс,генерирующий финальный отчет
 */

public class OutputGenerator {
    private static final char CONST_separator_line_char = '-';
    private static final char CONST_separator_column_char = '|';
    private static final char CONST_separator_word_char = ' ';
    private static final char CONST_separator_page_char = '~';
    private static final String CONST_new_line = "\r\n";
    private static final String CONST_wrap_on = "[^\\dA-ZА-ЯЁa-zа-яё]";//регулярка для нахождения разделителей
    private static final String CONST_space = "\\s";//регулярка для пробела
    private ArrayList<String[]> TSVList;
    private ArrayList<column> columns;
    private ArrayList<String> head;
    private int width;
    private int height;
    private int ColumnCount;
    private String separatorLine = "";

    public OutputGenerator(ArrayList<String[]> TSVList, XMLLoader settings) {
        this.TSVList = TSVList;
        this.columns = settings.getColumns();
        this.height = settings.getHeight();
        this.ColumnCount = columns.size();
        int ourWidth = ColumnCount * 3 + 1;
        for (column column : columns) {
            ourWidth += column.getWidth();
        }
        this.width = ourWidth;
        this.head = generateHead();
        separatorLine = StringUtils.repeat(CONST_separator_line_char, width) + CONST_new_line;
    }

    /**
     * возвращает список строк,на которые мы делим колонку
     *
     * @param ourString
     * @param length
     * @return
     */
    private ArrayList<String> parseColumn(String ourString, int length) {
        int ourStringLength = ourString.length();
        int StartIterator = 0;
        int iterator = 0;
        ArrayList<String> result = new ArrayList<String>();
        String wrapResult = WordUtils.wrap(ourString, length, CONST_new_line, true, CONST_wrap_on);//перенос по словам
        String[] splitResult = wrapResult.split(CONST_new_line);//разделим по строкам
        boolean flagLastDelimiter = false;//нужно поставить разделитель с прошлой строки
        boolean flagLeftDelimiter = false;//если на этой строкие добавили разделитель спереди
        String termDelimiter = "";
        for (String termLine : splitResult) {
            int termLength = termLine.length();
            if (flagLastDelimiter) {
                if (termLength < length) {//если  разделитель влезает,то ставим его.Нет-удаляем разделитель.
                    flagLeftDelimiter = true;
                    if (termDelimiter.matches(CONST_space)) {
                        StartIterator++;
                        flagLeftDelimiter = false;
                    }//исключение-пробел в начале строки
                } else {
                    StartIterator++;
                }
                iterator++;
                flagLastDelimiter = false;
            }
            iterator += termLength;
            if (iterator + 1 < ourStringLength) {//это не конец строки.проверяем, не удалили ли мы разделитель
                termDelimiter = ourString.substring(iterator, iterator + 1);
                if (termDelimiter.matches(CONST_wrap_on)) {
                    if (termLength + (flagLeftDelimiter ? 1 : 0) < length) {//наш разделитель на текущей строке останется
                        iterator++;
                    } else {// наш разделитель на следущей строке
                        flagLastDelimiter = true;
                    }
                }
            }
            result.add(ourString.substring(StartIterator, iterator));
            StartIterator = iterator;
            flagLeftDelimiter = false;
        }

        return result;
    }

    /**
     * возвращает исходную строку,готовую к записи.
     *
     * @param inputString
     * @return
     */
    private ArrayList<String> buildLine(String[] inputString) {
        int StringHeight = 0;
        int columnIndex = 0;
        int columnWidth;
        ArrayList<String[]> result = new ArrayList<String[]>();
        for (String inputColumn : inputString) {
            columnWidth = columns.get(columnIndex).getWidth();
            ArrayList<String> termColumn = parseColumn(inputColumn, columnWidth);//тут получили одну конкретную колонку, разбитую на строки
            int columnHeight = 1;
            for (String subStringColumn : termColumn) {
                if (columnHeight > StringHeight) {
                    String[] newString = new String[ColumnCount];
                    newString[columnIndex] = subStringColumn;
                    result.add(newString);
                    StringHeight++;
                } else {
                    String[] setString = result.get(columnHeight - 1);
                    setString[columnIndex] = subStringColumn;
                    result.set(columnHeight - 1, setString);
                }
                columnHeight++;
            }
            columnIndex++;
        }
        return delimiterString(result);
    }

    /**
     * добавляет разделители в строку
     *
     * @param inputString
     * @return
     */
    private ArrayList<String> delimiterString(ArrayList<String[]> inputString) {
        ArrayList<String> result = new ArrayList<String>();
        String resultLine;
        int i;
        for (String[] currentLine : inputString) {
            i = -1;
            resultLine = "";
            for (String subString : currentLine) {
                i++;
                if (subString == null) {
                    subString = "";
                }
                resultLine += String.valueOf(CONST_separator_column_char) + String.valueOf(CONST_separator_word_char) + subString;
                resultLine += StringUtils.repeat(CONST_separator_word_char, columns.get(i).getWidth() - subString.length() + 1);
                if (i == ColumnCount - 1) {
                    resultLine += String.valueOf(CONST_separator_column_char) + CONST_new_line;
                }
            }
            result.add(resultLine);
        }
        return result;
    }

    /**
     * создает загаловок таблицы
     *
     * @return
     */
    private ArrayList<String> generateHead() {
        String[] ourString = new String[ColumnCount];
        for (int i = 0; i < ColumnCount; i++) {
            ourString[i] = columns.get(i).getTitle();
        }
        return buildLine(ourString);
    }

    /**
     * возвращает документ, готовый к записи
     *
     * @return
     */
    public ArrayList<String> buildDocument() {
        ArrayList<String> result = new ArrayList<String>();
        boolean stop = false;
        int TSVListSize = TSVList.size();
        int iterator = 0;
        while (true) {
            int busy = 0;
            if (stop) {
                break;
            }
            for (String subLine : head) {
                busy++;
                result.add(subLine);
            }
            int i = 0;
            for (int k = iterator; k < TSVListSize; k++) {//добавит проверку,влезает ли на страницу одна запись
                String[] line = TSVList.get(k);
                ArrayList<String> termLine = buildLine(line);
                int termLineSize = termLine.size();
                if ((busy + termLineSize + 1) > height) {
                    if (i == 0) {//первая запись не помещается на страницу
                        stop = true;
                        System.out.println("запись не влезает на страницу");
                    }
                    result.add(String.valueOf(CONST_separator_page_char) + CONST_new_line);
                    break;
                } else {
                    busy++;
                    result.add(separatorLine);
                    iterator++;
                    for (String termSubLine : termLine) {
                        result.add(termSubLine);
                        busy++;
                    }
                    if (iterator == TSVListSize) {
                        stop = true;
                    }

                }
                i++;
            }
        }
        return result;
    }
}
