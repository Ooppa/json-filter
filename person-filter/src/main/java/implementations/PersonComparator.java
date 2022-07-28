package implementations;

import domain.Person;
import domain.PersonField;

import java.util.Comparator;

public class PersonComparator implements Comparator<Person> {

    private final PersonField fieldToCompare;

    /**
     * Flips the order if needed. Should be 1 or -1.
     */
    private final int orderMultiplier;

    public PersonComparator(PersonField fieldToCompare, boolean ascendingOrder) {
        this.fieldToCompare = fieldToCompare;
        this.orderMultiplier = ascendingOrder ? 1 : -1; // Flip order if needed
    }

    @Override
    public int compare(Person o1, Person o2) {
        switch (fieldToCompare) {
            case FIRST_NAME:
                if (o1.getFirstName().equals(o2.getFirstName())) { // If same first name, return order in last name order
                    return o1.getLastName().compareTo(o2.getLastName()) * orderMultiplier;
                }
                return o1.getFirstName().compareTo(o2.getFirstName()) * orderMultiplier;
            case LAST_NAME:
                if (o1.getLastName().equals(o2.getLastName())) { // If same last name, return order in first name order
                    return o1.getFirstName().compareTo(o2.getFirstName()) * orderMultiplier;
                }
                return o1.getLastName().compareTo(o2.getLastName()) * orderMultiplier;
            case AGE:
                return Integer.compare(o1.getAge(), o2.getAge()) * orderMultiplier;
            case GENDER:
                return o1.getGender().toString().compareTo(o2.getGender().toString()) * orderMultiplier;
            case NONE:
                return 0;
        }

        return 0;
    }
}
