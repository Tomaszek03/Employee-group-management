package aoim.zad4;

public class Employee {
    String imie;
    String nazwisko;
    EmployeeCondition stan;
    int rok_urodzenia;
    double wynagrodzenie;

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public EmployeeCondition getStan() {
        return stan;
    }

    public void setStan(EmployeeCondition stan) {
        this.stan = stan;
    }

    public int getRok_urodzenia() {
        return rok_urodzenia;
    }

    public void setRok_urodzenia(int rok_urodzenia) {
        this.rok_urodzenia = rok_urodzenia;
    }

    public double getWynagrodzenie() {
        return wynagrodzenie;
    }

    public void setWynagrodzenie(double wynagrodzenie) {
        this.wynagrodzenie = wynagrodzenie;
    }

    Employee(String imie, String nazwisko, EmployeeCondition stan, int rok_urodzenia, double wynagrodzenie) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.stan = stan;
        this.rok_urodzenia = rok_urodzenia;
        this.wynagrodzenie = wynagrodzenie;
    }
}

