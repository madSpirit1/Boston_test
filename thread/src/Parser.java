import java.util.HashMap;
import java.util.Map;

/**
 * задаем числа на английском и обрабатываем их
 */
public class Parser {
    private Map<String, Integer> unit;//0-9
    private Map<String, Integer> ten;//20,...,90
    private Map<String, Integer> moreUnit;//10,11,12,...,19
    private static final String thousand = "thousand";
    private static final String hundred = "hundred";

    public Parser() {
        Map<String, Integer> unit = new HashMap<>();
        unit.put("one", 1);
        unit.put("two", 2);
        unit.put("three", 3);
        unit.put("four", 4);
        unit.put("five", 5);
        unit.put("six", 6);
        unit.put("seven", 7);
        unit.put("eight", 8);
        unit.put("nine", 9);
        Map<String, Integer> ten = new HashMap<>();
        ten.put("twenty", 20);
        ten.put("thirty", 30);
        ten.put("forty", 40);
        ten.put("fifty", 50);
        ten.put("sixty", 60);
        ten.put("seventy", 70);
        ten.put("eighty", 80);
        ten.put("ninety", 90);
        Map<String, Integer> moreUnit = new HashMap<>();
        moreUnit.put("ten", 10);
        moreUnit.put("eleven", 11);
        moreUnit.put("twelve", 12);
        moreUnit.put("thirteen", 13);
        moreUnit.put("fourteen", 14);
        moreUnit.put("fifteen", 15);
        moreUnit.put("sixteen", 16);
        moreUnit.put("seventeen", 17);
        moreUnit.put("eighteen", 18);
        moreUnit.put("nineteen", 19);
        this.unit = unit;
        this.ten = ten;
        this.moreUnit = moreUnit;
    }

    /**
     * возвращает распознанное число из строки
     *
     * @param input
     * @return
     */
    public int parse(String input) {
        if (input.equals(" ")) {
            return -1;
        }
        int index = 0;
        String[] splitInput = input.split("\\s");
        int inputLength = splitInput.length;
        int result = 0;
        int add;
        add = parseThousandAndHundred(thousand, 1000, index, splitInput);//находим тысячи
        if (add > 0) {
            index += 2;
        }
        result += add;
        add = parseThousandAndHundred(hundred, 100, index, splitInput);//находим сотни
        if (add > 0) {
            index += 2;
        }
        result += add;

        add = parseTensAndUnits(index, splitInput, ten);//ищем сотни
        if (add > 0) {//мы нашли десятки, дальше еще могут быть единицы
            result += add;
            index++;
            int innerAdd = parseTensAndUnits(index, splitInput, unit);
            if (innerAdd > 0) {//нашли единицы
                index++;
                result += innerAdd;
            }
        } else {//не нашли десятки
            int innerAdd = parseTensAndUnits(index, splitInput, moreUnit);
            if (innerAdd > 0) {//нашли 11,12....
                index++;
                result += innerAdd;
            } else {//не нашли 11,12....
                innerAdd = parseTensAndUnits(index, splitInput, unit);
                if (innerAdd > 0) {//нашли единицы
                    index++;
                    result += innerAdd;
                }
            }

        }
        if (inputLength != index) {//если в строке еще что то лишнее,ты выдаем -1
            return -1;
        }

        return result;
    }

    /**
     * обрабатывает тысячи и сотни
     *
     * @param TenWord
     * @param multiplier
     * @param index
     * @param splitInput
     * @return
     */
    private int parseThousandAndHundred(String TenWord, int multiplier, int index, String[] splitInput) {//-1 если неверные данные
        int result = 0;
        int inputLength = splitInput.length;
        if (inputLength > 1 + index) {
            if (TenWord.equals(splitInput[1 + index])) {//нашли тысячу.обрабатываем
                result = -1;
                Object unit_count = unit.get(splitInput[index]);
                if (unit_count != null) {
                    result = ((Integer) unit_count) * multiplier;
                }
            }
        }
        return result;
    }

    /**
     * обрабатывает числа меньше 100
     *
     * @param index
     * @param splitInput
     * @param ourCollection
     * @return
     */
    private int parseTensAndUnits(int index, String[] splitInput, Map<String, Integer> ourCollection) {
        int result = 0;
        int inputLength = splitInput.length;
        if (inputLength > index) {
            Object count = ourCollection.get(splitInput[index]);
            if (count != null) {
                result = ((Integer) count);
            }
        }
        return result;
    }

}
