package domain;

import java.util.Objects;

public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;

    public Person() {
    }

    public Person(String firstName, String lastName, int age, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%d, %s)",
                firstName,
                lastName,
                age,
                gender.toString()
        );
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && firstName.equals(person.firstName) && lastName.equals(person.lastName) && gender == person.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, gender);
    }
}
