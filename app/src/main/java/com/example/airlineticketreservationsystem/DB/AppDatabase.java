package com.example.airlineticketreservationsystem.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.airlineticketreservationsystem.DB.typeconverter.DateTypeConverter;
import com.example.airlineticketreservationsystem.Flight;
import com.example.airlineticketreservationsystem.Transaction;
import com.example.airlineticketreservationsystem.User;

@Database(entities = {User.class, Flight.class, Transaction.class}, version=7)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DBNAME ="db-airline";

    public static final String USER_TABLE = "user";
    public static final String FLIGHT_TABLE = "flight";
    public static final String TRANSACTION_TABLE = "transaction";

    public abstract UserDAO getUserDAO();

    public abstract FlightDAO getFlightDAO();

    public abstract TransactionDAO getTransactionDAO();






}
