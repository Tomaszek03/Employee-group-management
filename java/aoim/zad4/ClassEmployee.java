package aoim.zad4;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.*;

public class ClassEmployee implements Comparator<Employee> {
    StringProperty nazwa_gr = new SimpleStringProperty();
    List<Employee> employes = new ArrayList<>();
    IntegerProperty max = new SimpleIntegerProperty();
    IntegerProperty actual_size = new SimpleIntegerProperty();

    ClassEmployee(String nazwa_gr, int max) {
        this.nazwa_gr.set(nazwa_gr);
        this.max.set(max);
        this.actual_size.set(0);
    }

    public StringProperty getNazwa_gr() {
        return nazwa_gr;
    }

    public void setNazwa_gr(String nazwa_gr) {
        this.nazwa_gr.set(nazwa_gr);
    }

    public IntegerProperty getMax() {
        return max;
    }

    public int getActual_size() {
        return actual_size.get();
    }

    public IntegerProperty actual_sizeProperty() {
        return actual_size;
    }

    public void setActual_size(int actual_size) {
        this.actual_size.set(actual_size);
    }

    @Override
    public String toString() {
        return nazwa_gr.get();
    }

    public void addEmployee(Employee x) {
        employes.add(x);
        actual_size.set(actual_size.get() + 1);
        //System.out.println("Dodano pracownika do grupy " + toString());
    }

    public void setSalary(Employee x, double wyg) {
        x.wynagrodzenie = wyg;
    }

    public void removeEmployee(Employee x) {
        employes.remove(x);
        actual_size.set(actual_size.get() - 1);
        //System.out.println("Usunięto pracownika");
    }

    public void changeCondition(Employee x, EmployeeCondition y) {
        x.stan = y;
    }

    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.nazwisko.compareTo(o2.nazwisko);
    }

    public List<Employee> searchPartial(String fr) {
        List<Employee> found = new ArrayList<>();

        for (int i = 0; i < employes.size(); i++) {
            if (employes.get(i).imie.contains(fr) || employes.get(i).nazwisko.contains(fr)) {
                found.add(employes.get(i));
                //System.out.println(found.get(found.size() - 1).imie + " " + found.get(found.size() - 1).nazwisko);
            }
        }
        return found;
    }
}
