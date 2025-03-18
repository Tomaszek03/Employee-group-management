package aoim.zad4;

import javafx.util.StringConverter;

public class EnumStringConverter<T extends Enum<T>> extends StringConverter<T> {
    private final Class<T> enumClass;

    public EnumStringConverter(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String toString(T t) {
        return t==null ? "" : t.name();
    }

    @Override
    public T fromString(String s) {
        try {
            return s == null || s.isEmpty() ? null: Enum.valueOf(enumClass, s);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
