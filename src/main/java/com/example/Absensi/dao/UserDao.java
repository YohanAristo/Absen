package com.example.Absensi.dao;

import com.example.Absensi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String> {

}
