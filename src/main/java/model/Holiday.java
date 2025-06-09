package model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = Holiday.GET_HOLIDAY_COUNT_BY_DATE_AND_COUNTRY_CODE, query = "Select COUNT(h) from Holiday h WHERE h.date = :date and h.countryCode = :countryCode")
public class Holiday {
    public static final String GET_HOLIDAY_COUNT_BY_DATE_AND_COUNTRY_CODE = "Holiday.getHolidaysByYearAndCountryCode";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "holiday_seq")
    private Long id;

    private LocalDate date;
    private String localName;
    private String name;
    private String countryCode;
    private boolean fixed;
    private boolean global;
    private Integer launchYear;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "holiday_id")
    private List<HolidayType> types;

    public Holiday() {
        super();
    }

    public Holiday(Long id, LocalDate date, String localName, String name, String countryCode, boolean fixed, boolean global, Integer launchYear, List<HolidayType> types) {
        this.id = id;
        this.date = date;
        this.localName = localName;
        this.name = name;
        this.countryCode = countryCode;
        this.fixed = fixed;
        this.global = global;
        this.launchYear = launchYear;
        this.types = types;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public Integer getLaunchYear() {
        return launchYear;
    }

    public void setLaunchYear(Integer launchYear) {
        this.launchYear = launchYear;
    }

    public List<HolidayType> getTypes() {
        return types;
    }

    public void setTypes(List<HolidayType> types) {
        this.types = types;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Holiday holiday = (Holiday) o;
        return isFixed() == holiday.isFixed() && isGlobal() == holiday.isGlobal() && Objects.equals(getId(), holiday.getId()) && Objects.equals(getDate(), holiday.getDate()) && Objects.equals(getLocalName(), holiday.getLocalName()) && Objects.equals(getName(), holiday.getName()) && Objects.equals(getCountryCode(), holiday.getCountryCode()) && Objects.equals(getLaunchYear(), holiday.getLaunchYear()) && Objects.equals(getTypes(), holiday.getTypes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate(), getLocalName(), getName(), getCountryCode(), isFixed(), isGlobal(), getLaunchYear(), getTypes());
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "id=" + id +
                ", date=" + date +
                ", localName='" + localName + '\'' +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", fixed=" + fixed +
                ", global=" + global +
                ", launchYear=" + launchYear +
                ", types=" + types +
                '}';
    }
}
