package ru.Sidorov.ChatSimbirsoft.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.Sidorov.ChatSimbirsoft.domain.AddUserSchedule;

import java.time.LocalDateTime;
import java.util.List;

public interface AddUserScheduleRepo extends JpaRepository<AddUserSchedule, Integer> {
    List<AddUserSchedule> findAllByTimeBefore(LocalDateTime time);
}
