package com.example.airlineticketreservationsystem.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.airlineticketreservationsystem.Transaction;

import java.util.List;

@Dao
public interface TransactionDAO {
    @Insert
    void insert(Transaction...transactions);

    @Update
    void update(Transaction... transactions);

    @Delete
    void delete(Transaction transaction);

    @Query("DELETE FROM [Transaction]") //Use [] to escape reserved keywords
    void deleteAll();

    @Query("SELECT * FROM [" + AppDatabase.TRANSACTION_TABLE + "]")
    List<Transaction> getTransaction();

    @Query("SELECT * FROM [" + AppDatabase.TRANSACTION_TABLE+ "] WHERE mId = :transactionNumber")
    Transaction findTransactionWithNumber(String transactionNumber);
}
