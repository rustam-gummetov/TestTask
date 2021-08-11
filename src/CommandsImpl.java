import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandsImpl implements Commands {
    private List<Point> pointList = new ArrayList<>();

    private final CheckPoint isLinear = point -> point.getY() >= point.getX();
    private final CheckPoint isQuadratic = point -> point.getY() >= point.getX() * point.getX();
    private final CheckPoint isCubic = point -> point.getY() >= point.getX() * point.getX() * point.getX();

    @Override
    public void addPoint(String points) {
        List<String> splitPoints = Arrays.stream(points.split(" ")).skip(1).filter(el -> !el.equals("")).collect(Collectors.toList());
        if (splitPoints.size() % 2 != 0) {
            System.out.println("Введено нечетное количество координат");
            return;
        }
        for (int i = 0; i < splitPoints.size(); i += 2) {
            try {
                pointList.add(new Point(Integer.parseInt(splitPoints.get(i)), Integer.parseInt(splitPoints.get(i + 1))));
            }
            catch (NumberFormatException ex) {
                System.out.println(ex.fillInStackTrace());
            }
        }
        System.out.println();
    }

    public void printGroup(int groupNum, CheckPoint checkPoint) {
        int counterInGroup = 0;
        System.out.printf("%d группа:\n", groupNum);
        for (Point point : pointList) {
            if (checkPoint.check(point)) {
                counterInGroup++;
                System.out.printf("(%d, %d)%n", point.getX(), point.getY());
            }
        }
        if(counterInGroup == 0)
            System.out.println("группа пуста");
        System.out.println();
    }

    @Override
    public void print() {
        int notIncludedCounter = 0;
        printGroup(1, isLinear);
        printGroup(2, isQuadratic);
        printGroup(3, isCubic);
        for (Point point : pointList) {
            if (!isLinear.check(point) && !isQuadratic.check(point) && !isCubic.check(point)) {
                notIncludedCounter++;
            }
        }
        System.out.println("Количество точек, не вошедших ни в одну группу: " + notIncludedCounter);
        System.out.println();
    }

    @Override
    public void print(String groupNum) {
        List<String> groupNums = Arrays.stream(groupNum.split(" ")).skip(1).filter(el -> !el.equals("")).collect(Collectors.toList());

        for(String num : groupNums) {
            if(Integer.parseInt(num) == 1) {
                printGroup(1, isLinear);
            }
            else if(Integer.parseInt(num) == 2) {
                printGroup(2, isQuadratic);
            }
            else if(Integer.parseInt(num) == 3) {
                printGroup(3, isCubic);
            }
        }
    }

    @Override
    public void remove(String groupNum) {
        List<String> groupNums = Arrays.stream(groupNum.split(" ")).skip(1).filter(el -> !el.equals("")).collect(Collectors.toList());
        for(String num : groupNums) {
            if(Integer.parseInt(num) == 1) {
                pointList.removeIf(isLinear::check);
            }
            else if(Integer.parseInt(num) == 2) {
                pointList.removeIf(isQuadratic::check);
            }
            else if(Integer.parseInt(num) == 3) {
                pointList.removeIf(isCubic::check);
            }
        }
        System.out.println();
    }

    @Override
    public void clear() {
        pointList.clear();
        System.out.println();
    }

    @Override
    public void help() {
        System.out.println("Доступные команды:");
        System.out.println("add <point>        - добавить в память программы точки, координаты передаются парами чисел через пробел");
        System.out.println("print              - напечатать построчно каждую из трех групп (входящие в них точки)\n" +
                "                     если в группу не попадает ни одна точка, то вывести сообщение, что группа пуста\n" +
                "                     последней строкой напечатать количество точек, не вошедших ни в одну группу");
        System.out.println("print <group_num>  - напечатать одним списком точки, входящие в группу(ы) переданную(ые) параметром <group_num>");
        System.out.println("remove <group_num> - удалить из памяти все точки, входящие в группу(ы) <group_num>");
        System.out.println("clear              - очистить память");
        System.out.println("help               - вывод справки по командам");
        System.out.println("exit               - выход");
    }
}
