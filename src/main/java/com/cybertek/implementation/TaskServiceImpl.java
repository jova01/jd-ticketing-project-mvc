package com.cybertek.implementation;

import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.service.TaskService;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl extends AbstractMapService<TaskDTO, Long> implements TaskService {
    @Override
    public List<TaskDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(TaskDTO t) {
        super.delete(t);
    }

    @Override
    public TaskDTO save(TaskDTO taskDTO) {
//        TaskDTO object= findById(taskDTO.getId());
//        taskDTO.setAssignedDate(object.getAssignedDate());
//        taskDTO.setTaskStatus(object.getTaskStatus());
        return super.save(taskDTO.getId(), taskDTO);
    }

    @Override
    public List<TaskDTO> findTaskByManager(UserDTO manager) {

        return super.findAll().stream()
                        .filter(task -> task.getProject()
                        .getAssignedManager().equals(manager))
                        .collect(Collectors.toList());
    }

    @Override
    public void update(TaskDTO object) {
        super.update(object.getId(), object);
    }

    @Override
    public TaskDTO findById(Long id) {
        return super.findById(id);
    }


}
