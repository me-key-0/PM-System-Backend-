package com.projectM.repository;

import com.projectM.model.Project;
import com.projectM.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    List<Project> findByOwner(User owner);

    List<Project> findByNameContainingAndTeamContaining(String partialName, User user);

    @Query("SELECT p FROM Project p JOIN p.team t WHERE t =:user")
    List<Project> findProjectByTeam(@Param("user") User user);

    List<Project> findByTeamContainingOrOwner(User user, User owner);
}
