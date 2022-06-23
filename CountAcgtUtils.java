import java.io.*;
import java.util.*;

public class CountAcgtUtils {

    public static void main(String[] args) throws  Exception {
        //File file = new File("C:\\Users\\hbhu\\S0.target.txt");
        File file = new File(args[0]);
        //File outFile = new File("C:\\Users\\hbhu\\S0.target.stat.csv");
        File outFile = new File(args[1]);
        Set<Character> set = new HashSet<>();
        set.add('A');
        set.add('C');
        set.add('G');
        set.add('T');
        if (file.isFile() && file.exists()) { //判断文件或目录是否存在
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outFile), "gbk");
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            String lineTxt = null;
            List<Map<Character, Integer>> list = new ArrayList<>();
            try {
                //对List容器初始化
                for (int i = 0; i < 10; i++) {
                    Map<Character, Integer> countMap = new HashMap<>();
                    list.add(countMap);
                }
                int index = 0;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    index ++;
                    String[] lineArr = lineTxt.split("\t");
                    if (lineArr.length == 4) {
                        try {
                            char[] arr = lineArr[3].toCharArray();
                            if(arr.length<10) {
                                System.out.println("第"+index+"行出现第四列位数不足");
                            } else {
                                for (int i = 0; i < arr.length; i++) {
                                    char key = arr[i];
                                    Map<Character, Integer> countMap = list.get(i);
                                    if (set.contains(key)) {
                                        if (countMap.containsKey(key)) {
                                            countMap.put(key, countMap.get(key) + Integer.valueOf(lineArr[0]));
                                        } else {
                                            countMap.put(key, Integer.valueOf(lineArr[0]));
                                        }
                                    } else {
                                        System.out.println("第" + index + "行出现异常字母" + key);
                                    }
                                }
                            }

                        } catch (Exception e) {
                            System.out.println("第"+index+"行出现格式转换错误");
                        }
                    }
                }

                //组装输出格式
                List<List<String>> printList = new ArrayList<>();
                List<String> title = new ArrayList<>();
                title.add("");
                title.add("A出现次数");
                title.add("C出现次数");
                title.add("G出现次数");
                title.add("T出现次数");
                printList.add(title);
                for (int i = 0; i < list.size(); i++) {
                    Map<Character, Integer> countMap = list.get(i);
                    List<String> statData = new ArrayList<>();
                    statData.add("第" + (i + 1) + "个字符");
                    if (countMap.containsKey('A')) {
                        statData.add(countMap.get('A').toString());
                    } else {
                        statData.add("");
                    }
                    if (countMap.containsKey('C')) {
                        statData.add(countMap.get('C').toString());
                    } else {
                        statData.add("");
                    }
                    if (countMap.containsKey('G')) {
                        statData.add(countMap.get('G').toString());
                    } else {
                        statData.add("");
                    }
                    if (countMap.containsKey('T')) {
                        statData.add(countMap.get('T').toString());
                    } else {
                        statData.add("");
                    }
                    printList.add(statData);
                }

                for (List<String> lineList : printList) {
                    String lineStr = "";
                    for (String s : lineList) {
                        lineStr += s + ",";
                    }
                    bufferedWriter.write(lineStr);
                    bufferedWriter.newLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            bufferedWriter.close();
            writer.close();
            bufferedReader.close();
            reader.close();
        }
    }

}
