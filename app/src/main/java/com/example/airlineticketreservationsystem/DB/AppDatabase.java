package com.example.airlineticketreservationsystem.DB;

import androidx.room.Database;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.airlineticketreservationsystem.DB.typeconverter.DateTypeConverter;
import com.example.airlineticketreservationsystem.Flight;
import com.example.airlineticketreservationsystem.Reservation;
import com.example.airlineticketreservationsystem.Transaction;
import com.example.airlineticketreservationsystem.User;

import java.util.List;

@Database(entities = {User.class, Flight.class, Transaction.class, Reservation.class}, version=9)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DBNAME ="db-airline";

    public static final String USER_TABLE = "user";
    public static final String FLIGHT_TABLE = "flight";
    public static final String TRANSACTION_TABLE = "transaction";
    public static final String RESERVATION_TABLE = "reservation";

    public abstract UserDAO getUserDAO();

    public abstract FlightDAO getFlightDAO();

    public abstract TransactionDAO getTransactionDAO();

    public abstract ReservationDAO getReservationDAO();








}
