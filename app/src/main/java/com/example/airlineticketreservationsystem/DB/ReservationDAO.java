package com.example.airlineticketreservationsystem.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.airlineticketreservationsystem.Reservation;

import java.util.List;

@Dao
public interface ReservationDAO {

    @Insert
    void insert(Reservation...reservations);

    @Update
    void update(Reservation...reservations);

    @Delete
    void delete(Reservation reservation);

    @Query("DELETE FROM Reservation") //Use [] to escape reserved keywords
    void deleteAll();

    @Query("SELECT * FROM " + AppDatabase.RESERVATION_TABLE)
    List<Reservation> getReservation();

    @Query("SELECT * FROM " + AppDatabase.RESERVATION_TABLE + " WHERE mId = :reservationNumber")
    List<Reservation> findReservationWithNumber(String reservationNumber);

    @Query("SELECT * FROM " + AppDatabase.RESERVATION_TABLE + " WHERE mUsername = :username")
    List<Reservation> findReservationWithUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.RESERVATION_TABLE + " WHERE mUsername = :username AND mFlightNo = :flightNo")
    Reservation findReservationWithUsernameAndFlight(String username, String flightNo);




}

