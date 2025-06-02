package repository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import model.Holiday;
import java.time.LocalDate;

@Dependent
public class HolidayRepository {
    @Inject
    private EntityManager em;

    @Transactional
    public Holiday createHoliday(Holiday holiday) { return em.merge(holiday); }

    public boolean existsByDateAndCountryCode(LocalDate date, String countryCode) {
        Long count = (Long)em.createNamedQuery(Holiday.GET_HOLIDAY_COUNT_BY_DATE_AND_COUNTRY_CODE).setParameter("date", date).setParameter("countryCode", countryCode).getSingleResult();
        return count > 0;
    }
}