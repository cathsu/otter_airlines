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

    @Query("SELECT * FROM " + AppDatabase.RESERVATION_TABLE)
    List<Reservation> getReservation();

    @Query("SELECT * FROM " + AppDatabase.RESERVATION_TABLE + " WHERE mId = :reservationNumber")
    Reservation findReservationWithNumber(String reservationNumber);

    @Query("SELECT * FROM " + AppDatabase.RESERVATION_TABLE + " WHERE mUsername = :username")
    Reservation findReservationWithUsername(String username);


}

