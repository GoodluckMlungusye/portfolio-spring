package com.goodamcodes.controller;
import com.goodamcodes.dto.ProjectDTO;
import com.goodamcodes.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDTO> addProject(@RequestPart("project") ProjectDTO  projectDTO, @RequestPart("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.addProject(projectDTO,file));
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getProjects(){
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getProjects());
    }

    @PatchMapping(path = "/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject( @PathVariable("projectId") Long projectId, @RequestPart ProjectDTO projectDTO, @RequestPart("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(projectService.updateProject(projectId, projectDTO, file));
    }

    @DeleteMapping(path = "/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId") Long projectId){
        return ResponseEntity.status(HttpStatus.OK).body(projectService.deleteProject(projectId));
    }

}
