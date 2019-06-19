package xml.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import xml.booking.model.Day;

/**
* Generated by Spring Data Generator on 19/06/2019
*/
@Repository
public interface DayRepository extends JpaRepository<Day, Long>, JpaSpecificationExecutor<Day> {

}
