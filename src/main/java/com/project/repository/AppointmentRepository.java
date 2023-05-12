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

    public List<Appointment> findByUserId(Long id) {
        String sql = "SELECT * FROM appointments WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[] { id }, appointmentRowMapper);
    }

    public Appointment findById(Long id) {
        String sql = "SELECT * FROM appointments WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { id }, appointmentRowMapper);
    }

    public List<Appointment> findByStartLessThanEqualAndEndGreaterThanEqual(LocalDateTime end, LocalDateTime start) {
        String sql = "SELECT * FROM appointments WHERE start_t <= ? AND end_t >= ?";
        return jdbcTemplate.query(sql, new Object[] { end, start }, appointmentRowMapper);
    }

    public Appointment save(Appointment appointment) {
        String sql = "INSERT INTO appointments (name, start_t, end_t, user_id, location) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, appointment.getName(), appointment.getStart(), appointment.getEnd(), 1, appointment.getLocation());
        return appointment;
    }


    private RowMapper<Appointment> appointmentRowMapper = new RowMapper<Appointment>() {
        @Override
        public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Appointment appointment = new Appointment();
            appointment.setId((int) rs.getLong("id"));
            appointment.setName(rs.getString("name"));
            appointment.setStart(rs.getTimestamp("start_t").toLocalDateTime());
            appointment.setEnd(rs.getTimestamp("end_t").toLocalDateTime());
            appointment.setLocation(rs.getString("location"));
            appointment.setUserId((int) rs.getLong("user_id"));
            return appointment;
        }
    };

}
