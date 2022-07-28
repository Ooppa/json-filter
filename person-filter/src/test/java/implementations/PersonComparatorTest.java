package implementations;

import domain.Gender;
import domain.Person;
import domain.PersonField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class PersonComparatorTest {

    private ArrayList<Person> persons;

    @BeforeEach
    void setUp() {
        persons = new ArrayList<>();
        persons.add(new Person("Cc", "cC", 30, Gender.FEMALE));
        persons.add(new Person("Aa", "aA", 10, Gender.MALE));
        persons.add(new Person("Bb", "bB", 20, Gender.FEMALE));
    }

    @Test
    void testSortByFirstNameAsc() {
        PersonComparator comparator = new PersonComparator(PersonField.FIRST_NAME, true);
        persons.sort(comparator);
        Assertions.assertEquals("Aa", persons.get(0).getFirstName());
        Assertions.assertEquals("Cc", persons.get(2).getFirstName());
    }

    @Test
    void testSortByFirstNameDesc() {
        PersonComparator comparator = new PersonComparator(PersonField.FIRST_NAME, false);
        persons.sort(comparator);
        Assertions.assertEquals("Cc", persons.get(0).getFirstName());
        Assertions.assertEquals("Aa", persons.get(2).getFirstName());
    }

    @Test
    void testSortByLastNameAsc() {
        PersonComparator comparator = new PersonComparator(PersonField.LAST_NAME, true);
        persons.sort(comparator);
        Assertions.assertEquals("aA", persons.get(0).getLastName());
        Assertions.assertEquals("cC", persons.get(2).getLastName());
    }

    @Test
    void testSortByLastNameDesc() {
        PersonComparator comparator = new PersonComparator(PersonField.LAST_NAME, false);
        persons.sort(comparator);
        Assertions.assertEquals("cC", persons.get(0).getLastName());
        Assertions.assertEquals("aA", persons.get(2).getLastName());
    }

    @Test
    void testSortByAgeAsc() {
        PersonComparator comparator = new PersonComparator(PersonField.AGE, true);
        persons.sort(comparator);
        Assertions.assertEquals(10, persons.get(0).getAge());
        Assertions.assertEquals(30, persons.get(2).getAge());
    }

    @Test
    void testSortByAgeDesc() {
        PersonComparator comparator = new PersonComparator(PersonField.AGE, false);
        persons.sort(comparator);
        Assertions.assertEquals(30, persons.get(0).getAge());
        Assertions.assertEquals(10, persons.get(2).getAge());
    }

    @Test
    void testSortByGenderAsc() {
        PersonComparator comparator = new PersonComparator(PersonField.GENDER, true);
        persons.sort(comparator);
        Assertions.assertEquals(Gender.FEMALE, persons.get(0).getGender());
        Assertions.assertEquals(Gender.MALE, persons.get(2).getGender());
    }

    @Test
    void testSortByGenderDesc() {
        PersonComparator comparator = new PersonComparator(PersonField.GENDER, false);
        persons.sort(comparator);
        Assertions.assertEquals(Gender.MALE, persons.get(0).getGender());
        Assertions.assertEquals(Gender.FEMALE, persons.get(2).getGender());
    }

    @Test
    void testSortByNoneAsc() {
        PersonComparator comparator = new PersonComparator(PersonField.NONE, true);
        persons.sort(comparator);
        Assertions.assertEquals("Cc", persons.get(0).getFirstName());
        Assertions.assertEquals("Bb", persons.get(2).getFirstName());
    }

    @Test
    void testSortByNoneDesc() {
        PersonComparator comparator = new PersonComparator(PersonField.NONE, false);
        persons.sort(comparator);
        Assertions.assertEquals("Cc", persons.get(0).getFirstName());
        Assertions.assertEquals("Bb", persons.get(2).getFirstName());
    }
}