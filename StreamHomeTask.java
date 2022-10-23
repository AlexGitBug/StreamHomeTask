package CollectionLesson.Stream.HomeTask;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Дан список студентов с полями:
 * - Имя
 * - Фамилия
 * - Номер курса в университете
 * - Список оценок за учебу
 * <p>
 * Преобразовать этот список студентов в ассоциативный массив, где ключом является номер курса, а значением:
 * <p>
 * Средняя оценка студентов этого курса, количество оценок у которых больше 3-х
 * <p>
 * Список студентов данного курса, но только с полями Имя и Фамилия.
 * Список должен быть отсортированы по этим двум полям
 * <p>
 * Объект с двумя полями:
 * - Отсортированный список студентов с пункта 2
 * - Средняя оценка этих студентов
 * <p>
 * Подумать, как ассоциативный массив можно было представить в коде в виде отсортированного - TreeMap
 */
public class StreamHomeTask {

    public static void main(String[] args) {


        List<Student> students = List.of(
                new Student("Alex", "Alexov", 1, List.of(5, 3, 5, 10)),
                new Student("Kira", "Kirova", 3, List.of(8, 7, 9, 10)),
                new Student("Boris", "Borisv", 4, List.of(4, 3, 8, 3)),
                new Student("Katya", "Kateva", 3, List.of(1, 8, 6, 5)),
                new Student("Jane", "Jeneva", 1, List.of(8, 7, 9, 6)),
                new Student("Anna", "Anova", 5, List.of(10, 10, 10, 10)),
                new Student("Petr", "Petrov", 5, List.of(2, 3, 4, 5))
        );
        //  Средняя оценка студентов этого курса, количество оценок у которых больше 3-х
        Map<Integer, Double> collect = students.stream()
                .filter(student -> student.getMarks().size() > 3)
                .collect(Collectors.groupingBy(Student::getKurs,
                        Collectors.averagingDouble(student -> student.getMarks().stream()
                                .collect(Collectors.averagingInt(Integer::intValue)))));
        System.out.println(collect);

        System.out.println();

        Map<Integer, List<String>> collect1 = students.stream()

                .collect((Collectors.groupingBy(Student::getKurs,
                        Collectors.mapping(student -> student.getName() + " " + student.getLastName(),
                                Collectors.collectingAndThen(
                                        Collectors.toList(), value -> value.stream().sorted().collect(Collectors.toList())

                                )))));
        System.out.println(collect1);
        System.out.println();

        Map<Integer, List<Student>> example = students.stream()
                .collect(Collectors.groupingBy(Student::getKurs));
        for (Map.Entry<Integer, List<Student>> entry : example.entrySet()) {
            OptionalDouble average = entry.getValue().stream()
                    .flatMap(value -> value.getMarks().stream())
                    .mapToInt(Integer::intValue)
                    .average();
            List<String> list = entry.getValue().stream()
                    .map(student -> student.getName() + " " + student.getLastName())
                    .collect(Collectors.toList()).stream()
                    .sorted()
                    .collect(Collectors.toList());
            System.out.println(entry.getKey() + "  " + list + " " + average);

        }


        Map<Integer, Map<String, Double>> map = students.stream()
                .collect(Collectors.groupingBy(Student::getKurs,
                        Collectors.groupingBy(student -> student.getName() + " " + student.getLastName(),
                                Collectors.averagingDouble(value -> value.getMarks().stream().collect(Collectors.averagingInt(Integer::intValue))))));
        System.out.println(map);











