package com.project.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.model.Appointment;

@Repository
public class AppointmentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Appointment> findAll() {
        String sql = "SELECT * FROM appointments";
        return jdbcTemplate.query(sql, appointmentRowMapper);
    }

    public Appointment findById(Long id) {
        String sql = "SELECT * FROM appointments WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { id }, appointmentRowMapper);
    }

    public List<Appointment> findByStartLessThanEqualAndEndGreaterThanEqual(LocalDateTime end, LocalDateTime start) {
        String sql = "SELECT * FROM appointments WHERE start <= ? AND end >= ?";
        return jdbcTemplate.query(sql, new Object[] { end, start }, appointmentRowMapper);
    }

    public Appointment save(Appointment appointment) {
        String sql = "INSERT INTO appointments (name, start, end) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, appointment.getName(), appointment.getStart(), appointment.getEnd());
        return appointment;
    }

    // Other methods for updating and deleting appointments

    private RowMapper<Appointment> appointmentRowMapper = new RowMapper<Appointment>() {
        @Override
        public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Appointment appointment = new Appointment();
            appointment.setId(rs.getLong("id"));
            appointment.setName(rs.getString("name"));
            appointment.setStart(rs.getTimestamp("start").toLocalDateTime());
            appointment.setEnd(rs.getTimestamp("end").toLocalDateTime());
            return appointment;
        }
    };

}
