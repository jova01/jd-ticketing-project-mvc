package com.cybertek.controller;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.enums.Status;
import com.cybertek.service.ProjectService;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    @GetMapping("/create")
    public String createProject(Model model) {

        model.addAttribute("project", new ProjectDTO());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("managers", userService.findManagers());

        return "/project/create";
    }

    @PostMapping("/create")
    public String insertProject(@ModelAttribute("project") ProjectDTO projectDTO) {
        projectService.save(projectDTO);
        projectDTO.setProjectStatus(Status.OPEN);
        return "redirect:/project/create";
    }

    @GetMapping("/delete/{projectcode}")
    public String deleteProject(@PathVariable String projectcode) {
        projectService.deleteById(projectcode);
        return "redirect:/project/create";
    }

    @GetMapping("/complete/{projectcode}")
    public String completeProject(@PathVariable String projectcode) {
        projectService.complete(projectService.findById(projectcode));
        return "redirect:/project/create";
    }

    @GetMapping("/update/{projectcode}")
    public String editProject(@PathVariable String projectcode, Model model) {

        model.addAttribute("project", projectService.findById(projectcode));
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("managers", userService.findManagers());

        return "/project/update";
    }

    @PostMapping("/update/{projectcode}")
    public String updateProject(@PathVariable String projectcode, ProjectDTO project) {
        project.setProjectStatus(projectService.findById(projectcode).getProjectStatus());
        projectService.update(project);
        return "redirect:/project/create";
    }

    @GetMapping("/manager/complete")
    public String getProjectByManager(Model model) {
        UserDTO manager = userService.findById("john@cybertek.com");

        List<ProjectDTO> projects = getCountedListOfProjectDTO(manager);

        model.addAttribute("projects", projects);
        return "/manager/project-status";
    }

    List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {
        List<ProjectDTO> list = projectService
                .findAll().stream()
                .filter(each -> each.getAssignedManager().equals(manager))
                .peek(projectDTO -> {
                    int completeCount = (int) taskService.findAll().stream()
                            .filter(task -> task.getProject().equals(projectDTO) && task.getTaskStatus() == Status.COMPLETE)
                            .count();
                    int inCompleteCount = (int) taskService.findAll().stream()
                            .filter(task -> task.getProject().equals(projectDTO) && task.getTaskStatus() != Status.COMPLETE)
                            .count();

                    projectDTO.setCompleteTaskCounts(completeCount);
                    projectDTO.setUnfinishedTaskCounts(inCompleteCount);

                }).collect(Collectors.toList());
        return list;
    }

    public void toString(ProjectDTO projectDTO) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            System.out.println(mapper.writeValueAsString(projectDTO));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
