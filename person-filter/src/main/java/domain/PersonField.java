package domain;

public enum PersonField {
    FIRST_NAME,
    LAST_NAME,
    AGE,
    GENDER,
    NONE;

    public static PersonField getFieldFromString(String fieldName) {
        switch (fieldName.toLowerCase()) {
            case "firstname":
                return FIRST_NAME;
            case "lastname":
                return LAST_NAME;
            case "age":
                return AGE;
            case "gender":
                return GENDER;
            default:
                return NONE;
        }
    }
}
