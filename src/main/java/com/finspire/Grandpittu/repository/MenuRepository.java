package com.finspire.Grandpittu.repository;

import com.finspire.Grandpittu.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findAllByAvailable(boolean b);
}
