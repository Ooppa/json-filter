package app;

import domain.Gender;
import domain.Person;
import domain.PersonField;
import implementations.PersonComparator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

    public static List<Person> runAndReturn(Scanner scanner, Person[] persons) {
        List<Person> personList = filterNull(Stream.of(persons)).collect(Collectors.toList());
        System.out.printf("%d person(s) found in the JSON-file.%n", personList.size());

        personList = askFilter(scanner, personList);
        personList = askSort(scanner, personList);

        return personList;
    }

    public static void run(Scanner scanner, Person[] persons) {
        List<Person> personList = runAndReturn(scanner, persons);
        personList.forEach(System.out::println);
        System.out.printf("%d person(s) listed.%n", personList.size());
    }

    private static List<Person> askSort(Scanner scanner, List<Person> personList) {
        scanner.reset();

        String sortByString = getValidStringFromScanner(
                scanner,
                "Sort by field %s. Empty input to skip:%n",
                "firstname", "lastname", "age", "gender", ""
        );

        if (sortByString.isEmpty()) {
            return personList;
        }

        PersonField fieldToSortBy = PersonField.getFieldFromString(sortByString);

        String sortOrder = getValidStringFromScanner(
                scanner,
                "Sort ascending %s:%n",
                "y", "n"
        );
        return personList.stream().sorted(
                new PersonComparator(fieldToSortBy, sortOrder.equals("y"))).collect(Collectors.toList()
        );
    }

    private static List<Person> askFilter(Scanner scanner, List<Person> personList) {
        scanner.reset();

        String filterByString = getValidStringFromScanner(
                scanner,
                "Filter by field %s. Empty input to skip:%n",
                "firstname", "lastname", "age", "gender", ""
        );

        if (filterByString.isEmpty()) {
            return personList;
        }

        PersonField fieldToFilter = PersonField.getFieldFromString(filterByString);

        switch (fieldToFilter) {
            case FIRST_NAME:
                System.out.println("First name:");
                String firstNameString = scanner.nextLine().toLowerCase();
                personList = personList.stream().filter(x -> x.getFirstName().toLowerCase().equals(firstNameString)).collect(Collectors.toList());
                break;
            case LAST_NAME:
                System.out.println("Last name:");
                String lastNameString = scanner.nextLine().toLowerCase();
                personList = personList.stream().filter(x -> x.getLastName().toLowerCase().equals(lastNameString)).collect(Collectors.toList());
                break;
            case AGE:
                String operatorString = getValidStringFromScanner(scanner,
                        "Operator %s:%n",
                        "=", ">", "<", ">=", "<="
                );
                int validIntFromScanner = getValidIntFromScanner(scanner, "Age:");
                personList = filterAge(personList, operatorString, validIntFromScanner);
                break;
            case GENDER:
                String genderString = getValidStringFromScanner(scanner,
                        "Gender %s:%n",
                        "male", "female", "other"
                );
                personList = personList.stream().filter(
                        x -> x.getGender().equals(Gender.getFromString(genderString))).collect(Collectors.toList()
                );
                break;
            case NONE:
                break;
        }

        return personList;
    }

    private static List<Person> filterAge(List<Person> personList, String operatorString, int intToFilter) {
        switch(operatorString) {
            case "=":
                return personList.stream().filter(x -> x.getAge() == intToFilter).collect(Collectors.toList());
            case ">":
                return personList.stream().filter(x -> x.getAge() > intToFilter).collect(Collectors.toList());
            case "<":
                return personList.stream().filter(x -> x.getAge() < intToFilter).collect(Collectors.toList());
            case ">=":
                return personList.stream().filter(x -> x.getAge() >= intToFilter).collect(Collectors.toList());
            case "<=":
                return personList.stream().filter(x -> x.getAge() <= intToFilter).collect(Collectors.toList());
            default:
                return personList;
        }
    }

    private static int getValidIntFromScanner(Scanner scanner, String question) {
        scanner.reset();
        int answer = -1;

        while(true) {
            System.out.println(question);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.print("Operand must be an integer.\n");
            }
        }

    }

    private static String getValidStringFromScanner(Scanner scanner, String formattedQuestion, String... validInputs) {
        scanner.reset();

        do {
            System.out.printf(formattedQuestion, Arrays.toString(validInputs));
            String line = scanner.nextLine().toLowerCase();
            if (Arrays.asList(validInputs).contains(line)) {
                return line.toLowerCase();
            }
        } while (true);

    }

    private static Stream<Person> filterNull(Stream<Person> personStream) {
        return personStream.filter(Objects::nonNull);
    }
}
