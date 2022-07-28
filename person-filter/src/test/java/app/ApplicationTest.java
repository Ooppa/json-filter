package app;

import domain.Gender;
import domain.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class ApplicationTest {

    public static final Person NATALIE_FISHER = new Person("Natalie", "Fisher", 40, Gender.FEMALE);
    public static final Person JOEL_FISHER = new Person("Joel", "Fisher", 38, Gender.MALE);
    public static final Person LISA_SCHWARTZ = new Person("Lisa", "Schwartz", 20, Gender.FEMALE);
    public static final Person NATALIE_FLORES = new Person("Natalie", "Flores", 27, Gender.OTHER);
    private Person[] persons;

    @BeforeEach
    void setUp() {
        persons = new Person[]{
                NATALIE_FISHER,
                JOEL_FISHER,
                LISA_SCHWARTZ,
                NATALIE_FLORES
        };
    }

    private String toStringWithLineSeparators(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(strings).forEach(x -> stringBuilder.append(x).append(System.lineSeparator()));
        return stringBuilder.toString();
    }

    private void assertContains(Person person, List<Person> personList) {
        Assertions.assertTrue(personList.contains(person), String.format("The filtered list didn't contain: \"%s\".", person));
    }

    private void assertNotContains(Person person, List<Person> personList) {
        Assertions.assertFalse(personList.contains(person), String.format("The filtered list shouldn't contain: \"%s\".", person));
    }

    private void assertOrder(Person firstPerson, Person lastPerson, List<Person> personList) {
        if (personList.size() < 2) {
            throw new TestAbortedException("The given list should contain at least 2 objects.");
        }

        System.out.println("=========================== START ");
        personList.forEach(x -> System.out.println(x));
        System.out.println("=========================== END ");
        Assertions.assertEquals(firstPerson, personList.get(0));
        Assertions.assertEquals(lastPerson, personList.get(personList.size() - 1));
    }

    @Test
    void filterByNothing() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("", ""));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        assertContains(JOEL_FISHER, personList);
        assertContains(NATALIE_FISHER, personList);
        assertContains(LISA_SCHWARTZ, personList);
        assertContains(NATALIE_FLORES, personList);
    }

    @Test
    void filterByNothingOrderByAgeAsc() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("", "age", "y"));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        assertOrder(LISA_SCHWARTZ, NATALIE_FISHER, personList);
    }

    @Test
    void filterByNothingOrderByAgeDesc() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("", "age", "n"));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        assertOrder(NATALIE_FISHER, LISA_SCHWARTZ, personList);
    }

    @Test
    void filterByNothingOrderByFirstNameAsc() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("", "firstname", "y"));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        // If the first names are the same, we should order by the last name, if selected mode is filter by first name.
        assertOrder(JOEL_FISHER, NATALIE_FLORES, personList);
    }

    @Test
    void filterByNothingOrderByFirstNameDesc() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("", "firstname", "n"));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        // If the first names are the same, we should order by the last name, if selected mode is filter by first name.
        assertOrder(NATALIE_FLORES, JOEL_FISHER, personList);
    }

    @Test
    void filterByNothingOrderByLastNameAsc() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("", "lastname", "y"));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        // If the first names are the same, we should order by the last name, if selected mode is filter by first name.
        assertOrder(JOEL_FISHER, LISA_SCHWARTZ, personList);
    }

    @Test
    void filterByNothingOrderByLastNameDesc() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("", "lastname", "n"));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        // If the first names are the same, we should order by the last name, if selected mode is filter by first name.
        assertOrder(LISA_SCHWARTZ, JOEL_FISHER, personList);
    }

    @Test
    void filterByNothingOrderByGenderAsc() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("", "gender", "y"));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        System.out.println("-- gender, y");

        Assertions.assertEquals(Gender.FEMALE, personList.get(0).getGender());
        Assertions.assertEquals(Gender.FEMALE, personList.get(1).getGender());
        Assertions.assertEquals(Gender.MALE, personList.get(2).getGender());
        Assertions.assertEquals(Gender.OTHER, personList.get(3).getGender());
    }

    @Test
    void filterByNothingOrderByGenderDesc() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("", "gender", "n"));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        Assertions.assertEquals(Gender.OTHER, personList.get(0).getGender());
        Assertions.assertEquals(Gender.MALE, personList.get(1).getGender());
        Assertions.assertEquals(Gender.FEMALE, personList.get(2).getGender());
        Assertions.assertEquals(Gender.FEMALE, personList.get(3).getGender());
    }

    @Test
    void filterByFirstName() {
        Scanner scanner = new Scanner(toStringWithLineSeparators(
                "fistname",
                "0",
                "-8",
                "firstnamE", // Case-insensitive
                "Joel",
                "age",
                "y"
        ));
        assertContains(JOEL_FISHER, (Application.runAndReturn(scanner, persons)));
    }

    @Test
    void filterByFirstNameMultiple() {
        Scanner scanner = new Scanner(toStringWithLineSeparators(
                "fistname",
                "friskname",
                "ffffname",
                "firstname",
                "natalie",
                "agge",
                "AGE", // Case-insensitive
                "yeno",
                "y"
        ));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        assertContains(NATALIE_FISHER, personList);
        assertContains(NATALIE_FLORES, personList);
    }

    @Test
    void filterByLastName() {
        Scanner scanner = new Scanner(toStringWithLineSeparators(
                "fistname",
                "--Aa--",
                "2lastname",
                "lastNAME", // Case-insensitive
                "flOreS", // Case-insensitive
                "age",
                "y"
        ));
        assertContains(NATALIE_FLORES, (Application.runAndReturn(scanner, persons)));
    }

    @Test
    void filterByLastNameMultiple() {
        Scanner scanner = new Scanner(toStringWithLineSeparators(
                "lastname",
                "fisher",
                "agge",
                "age",
                "yeno",
                "y"
        ));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        assertContains(NATALIE_FISHER, personList);
        assertContains(JOEL_FISHER, personList);
    }

    @Test
    void filterByAgeEquals() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("age", "=", "27", ""));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        assertContains(NATALIE_FLORES, personList);
    }

    @Test
    void filterByAgeNotEquals() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("age", "=", "99", ""));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        Assertions.assertTrue(personList.isEmpty());
    }

    @Test
    void filterByAgeLessThan() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("age", "<", "39", ""));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        assertContains(JOEL_FISHER, personList);
        assertContains(LISA_SCHWARTZ, personList);
        assertNotContains(NATALIE_FISHER, personList);
    }

    @Test
    void filterByAgeMoreThan() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("age", ">", "39", ""));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        assertContains(NATALIE_FISHER, personList);
        assertNotContains(JOEL_FISHER, personList);
    }

    @Test
    void filterByAgeLessOrEqualThan() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("age", "<=", "27", ""));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        assertContains(NATALIE_FLORES, personList);
        assertContains(LISA_SCHWARTZ, personList);
        assertNotContains(JOEL_FISHER, personList);
    }

    @Test
    void filterByAgeMoreOrEqualThan() {
        Scanner scanner = new Scanner(toStringWithLineSeparators("age", ">=", "27", ""));
        List<Person> personList = Application.runAndReturn(scanner, persons);
        assertContains(NATALIE_FISHER, personList);
        assertContains(JOEL_FISHER, personList);
        assertNotContains(LISA_SCHWARTZ, personList);
    }
}