package com.project.ownote.attendance.service;

import com.project.ownote.attendance.dao.AttendanceDao;
import com.project.ownote.attendance.dto.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class AttendanceService {
    private final AttendanceDao attendanceDao;

    @Autowired
    public AttendanceService(AttendanceDao attendanceDao) {
        this.attendanceDao = attendanceDao;
    }


    public void saveAttendance(Attendance attendance) {
        attendanceDao.saveAttendance(attendance);
    }


    public Attendance getAttendanceById(Long attendanceId) {
        return attendanceDao.getAttendanceById(attendanceId);
    }


    public List<Attendance> getAllAttendances() {
        return attendanceDao.getAllAttendances();
    }


    public void updateAttendance(Attendance attendance) {
        attendanceDao.updateAttendance(attendance);
    }


    public void deleteAttendance(Long attendanceId) {
        attendanceDao.deleteAttendance(attendanceId);
    }










    public void deleteAttendanceById(Long attendanceId) {
    }

    public void recordAttendance(Long attendanceId, Time onTime) {
        Attendance attendance = getAttendanceById(attendanceId);
        if (attendance != null) {
            attendance.recordAttendance(onTime);
            updateAttendance(attendance);
        }
    }

    public void recordLeave(Long attendanceId, Time offTime) {
        Attendance attendance = getAttendanceById(attendanceId);
        if (attendance != null) {
            attendance.recordLeave(offTime);
            updateAttendance(attendance);
        }
    }


    public void recordAttendance(String attendanceTime) {
    }

    public void recordLeave(String leaveTime) {
    }

    public void editAttendance(Long id) {
    }
}

