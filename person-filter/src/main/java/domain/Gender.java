package domain;

import com.google.gson.annotations.SerializedName;

public enum Gender {
    @SerializedName("male")
    MALE,
    @SerializedName("female")
    FEMALE,
    @SerializedName("other")
    OTHER;

    public static Gender getFromString(String string) throws EnumConstantNotPresentException {
        if (string == null) {
            throw new EnumConstantNotPresentException(Gender.class, "null string");
        }

        switch (string.toLowerCase()) {
            case "male":
                return Gender.MALE;
            case "female":
                return Gender.FEMALE;
            case "other":
                return Gender.OTHER;
        }

        throw new EnumConstantNotPresentException(Gender.class, string);
    }
}
