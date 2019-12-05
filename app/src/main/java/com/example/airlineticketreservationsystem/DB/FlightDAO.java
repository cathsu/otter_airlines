package com.example.airlineticketreservationsystem.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.airlineticketreservationsystem.Flight;

import java.util.List;

@Dao
public interface FlightDAO {
    @Insert
    void insert(Flight... flights);

    @Update
    void update(Flight... flights);

    @Delete
    void delete(Flight flight);

    @Query("DELETE FROM Flight")
    void deleteAll();

    @Query("SELECT * FROM " + AppDatabase.FLIGHT_TABLE)
    List<Flight> getFlights();

    @Query("SELECT * FROM " + AppDatabase.FLIGHT_TABLE + " WHERE mId = :logID")
    Flight getQuestionWithId(int logID);

    @Query("SELECT * FROM " + AppDatabase.FLIGHT_TABLE+ " WHERE mNumber = :flightNumber")
    Flight findFlightWithNumber(String flightNumber);

    @Query("SELECT * FROM " + AppDatabase.FLIGHT_TABLE + " WHERE mDeparture = :departure AND mArrival =:arrival AND mCapacity >= :tickets")
    List<Flight> findFlightsThatMeetCriteria(String departure, String arrival, int tickets);
}
