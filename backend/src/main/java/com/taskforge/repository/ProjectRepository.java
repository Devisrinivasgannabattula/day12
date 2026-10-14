package com.taskforge.repository;

import com.taskforge.entity.Project;
import com.taskforge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwnerOrderByIdDesc(User owner);
    Optional<Project> findByIdAndOwner(Long id, User owner);
}

/*for projects completed section added this code*/

//package com.taskforge.repository;
//
//import com.taskforge.entity.Project;
//import com.taskforge.entity.TaskStatus;
//import com.taskforge.entity.User;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface ProjectRepository extends JpaRepository<Project, Long> {
//
//    @Query("""
//        SELECT DISTINCT p
//        FROM Project p
//        JOIN p.tasks t
//        WHERE p.owner = :owner
//        AND NOT EXISTS (
//            SELECT t2
//            FROM Task t2
//            WHERE t2.project = p
//            AND t2.status <> :doneStatus
//        )
//        """)
//    List<Project> findCompletedProjects(
//            User owner,
//            TaskStatus doneStatus
//    );
//}