package aoim.zad4;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class DialogUtils {
    public static void dialogGroupAdded(String name, int number){
        Alert groupAdded = new Alert(Alert.AlertType.INFORMATION);
        groupAdded.setTitle("Utworzono grupę");
        groupAdded.setHeaderText("Pomyślnie utworzono grupę");
        groupAdded.setContentText("Utworzono grupę " + name + " o pojemności " + number + " osób.");
        groupAdded.showAndWait();
    }

    public static Optional<ButtonType> dialogGroupRemovedConfirmation(String name){
        Alert groupRemovedConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        groupRemovedConfirmation.setTitle("Potwierdzenie usunięcia grupy");
        groupRemovedConfirmation.setHeaderText("Czy na pewno chcesz usunąć grupę " + name + "?");
        Optional<ButtonType> result = groupRemovedConfirmation.showAndWait();
        return result;
    }

    public static void dialogGroupRemoved(String name){
        Alert groupRemoved = new Alert(Alert.AlertType.INFORMATION);
        groupRemoved.setTitle("Usunięto grupę");
        groupRemoved.setHeaderText("Pomyślnie usunięto grupę");
        groupRemoved.setContentText("Usunięto grupę " + name +".");
        groupRemoved.showAndWait();
    }

    public static void dialogIncorrectAmount(){
        Alert incorrectAmount = new Alert(Alert.AlertType.ERROR);
        incorrectAmount.setTitle("Nieprawidłowa wartość");
        incorrectAmount.setHeaderText("Nieprawidłowa wartość dla maksymalnej liczby osób");
        incorrectAmount.setContentText("Podano nieprawidłową wartość dla maksymalnej liczby osób w grupie. Proszę wprowadzić liczbę całkowitą większą od zera.");
        incorrectAmount.showAndWait();
    }

    public static void dialogGroupExists(String name){
        Alert groupExists = new Alert(Alert.AlertType.ERROR);
        groupExists.setTitle("Nieprawidłowa wartość");
        groupExists.setHeaderText("Grupa o podanej nazwie już istnieje");
        groupExists.setContentText("Grupa o nazwie " + name + " już istnieje.");
        groupExists.showAndWait();
    }

    public static Optional<ButtonType> dialogExitConfirmation(){
        Alert exitConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        exitConfirmation.setTitle("Potwierdzenie wyjścia z aplikacji");
        exitConfirmation.setHeaderText("Czy na pewno chcesz wyjść?");
        Optional<ButtonType> result = exitConfirmation.showAndWait();
        return result;
    }

    public static void dialogIncorrectYear(){
        Alert incorrectAmount = new Alert(Alert.AlertType.ERROR);
        incorrectAmount.setTitle("Nieprawidłowa wartość");
        incorrectAmount.setHeaderText("Nieprawidłowa wartość dla roku");
        incorrectAmount.setContentText("Podano nieprawidłową wartość dla roku urodzenia pracownika. Proszę wprowadzić liczbę całkowitą większą od zera.");
        incorrectAmount.showAndWait();
    }

    public static void dialogIncorrectSalary(){
        Alert incorrectAmount = new Alert(Alert.AlertType.ERROR);
        incorrectAmount.setTitle("Nieprawidłowa wartość");
        incorrectAmount.setHeaderText("Nieprawidłowa wartość dla wynagrodzenia");
        incorrectAmount.setContentText("Podano nieprawidłową wartość dla wynagrodzenia pracownika. Proszę wprowadzić liczbę większą od zera.");
        incorrectAmount.showAndWait();
    }

    public static void dialogGroupMax(){
        Alert incorrectAmount = new Alert(Alert.AlertType.ERROR);
        incorrectAmount.setTitle("Nie można dodać pracownika");
        incorrectAmount.setHeaderText("Nie można dodać więcej pracowników do tej grupy");
        incorrectAmount.setContentText("Osiągnięto maksymalną ilość pracowników dla tej grupy.");
        incorrectAmount.showAndWait();
    }

    public static void dialogDataExport(){
        Alert exportData = new Alert(Alert.AlertType.INFORMATION);
        exportData.setTitle("Eksport danych do pliku CSV");
        exportData.setHeaderText("Wyeksportowano dane do pliku CSV.");
        exportData.showAndWait();
    }
}
