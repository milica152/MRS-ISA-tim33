package com.tim33.isa.repository;

import com.tim33.isa.model.Soba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoomRepository extends JpaRepository<Soba, Long>, JpaSpecificationExecutor<Soba> {
}
