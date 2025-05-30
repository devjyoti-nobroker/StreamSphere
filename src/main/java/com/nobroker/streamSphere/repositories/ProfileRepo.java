package com.nobroker.streamSphere.repositories;

import com.nobroker.streamSphere.models.Account;
import com.nobroker.streamSphere.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Long> {
    List<Profile> findByAccountId(Long accountId);
}
