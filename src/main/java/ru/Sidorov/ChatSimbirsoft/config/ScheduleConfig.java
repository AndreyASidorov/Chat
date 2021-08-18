package ru.Sidorov.ChatSimbirsoft.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.Sidorov.ChatSimbirsoft.domain.AddUserSchedule;
import ru.Sidorov.ChatSimbirsoft.repo.AddUserScheduleRepo;
import ru.Sidorov.ChatSimbirsoft.repo.RoomRepo;
import ru.Sidorov.ChatSimbirsoft.service.RoomServices;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
public class ScheduleConfig {
    @Autowired
    AddUserScheduleRepo addUserScheduleRepo;
    @Autowired
    RoomServices roomServices;

    @Scheduled(fixedDelay = 60000)
    public void scheduleFixedDelayTask() {
        List<AddUserSchedule> addUserScheduleRepoList = addUserScheduleRepo.findAllByTimeBefore(LocalDateTime.now());
        for (AddUserSchedule addUserSchedule : addUserScheduleRepoList){
            addUserScheduleRepo.delete(addUserSchedule);
            roomServices.addUserToRoom(addUserSchedule.getUserId(),addUserSchedule.getRoomId());
        }
    }
}
