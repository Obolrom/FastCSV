package example;

import java.util.Locale;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.FieldModifier;

/**
 * Example for reading CSV data from a String while using a field modifier.
 */
public class ExampleCsvReaderWithFieldModifier {

    private static final String DATA = " foo , BAR \n FOO2 , bar2 ";

    public static void main(final String[] args) {
        System.out.println("Trim fields:");
        builderWithTrim().build(DATA)
            .forEach(System.out::println);

        System.out.println("Trim and lowercase fields:");
        builderWithTrimAndLowerCase().build(DATA)
            .forEach(System.out::println);

        System.out.println("Trim and lowercase fields of first record (by using a custom modifier):");
        builderWithCustomModifier().build(DATA)
            .forEach(System.out::println);
    }

    private static CsvReader.CsvReaderBuilder builderWithTrim() {
        return CsvReader.builder().fieldModifier(FieldModifier.TRIM);
    }

    private static CsvReader.CsvReaderBuilder builderWithTrimAndLowerCase() {
        final FieldModifier modifier = FieldModifier.TRIM.andThen(FieldModifier.lower(Locale.ENGLISH));
        return CsvReader.builder().fieldModifier(modifier);
    }

    private static CsvReader.CsvReaderBuilder builderWithCustomModifier() {
        final FieldModifier modifier = (startingLineNumber, fieldIdx, comment, quoted, field) -> {
            if (startingLineNumber == 1) {
                return field.trim().toLowerCase(Locale.ENGLISH);
            }
            return field;
        };

        return CsvReader.builder().fieldModifier(modifier);
    }

}