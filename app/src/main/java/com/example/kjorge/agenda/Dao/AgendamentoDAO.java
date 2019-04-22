package com.example.kjorge.agenda.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kjorge.agenda.Model.Agendamento;

import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Agendamento de = new Agendamento();


    public AgendamentoDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }


    public SQLiteDatabase getDabase() {
        if (sqLiteDatabase == null) {
            sqLiteDatabase = databaseHelper.getWritableDatabase();
        }
        return sqLiteDatabase;
    }

    public long inserir(Agendamento cadastro) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", cadastro.getId());
        contentValues.put("nome", cadastro.getNome());
        contentValues.put("telefone", cadastro.getTelefone());
        contentValues.put("endereco", cadastro.getEndereco());
        contentValues.put("tipoConsulta", cadastro.getTipoConsulta());
        contentValues.put("data", cadastro.getData());

        return getDabase().insert("aged", null, contentValues);
    }

    //
    public List<Agendamento> ListarBanco() {
        List<Agendamento> listarTodosOsElementos = new ArrayList<Agendamento>();
        Cursor cursor = getDabase().rawQuery("SELECT * FROM aged ORDER BY _id", null);
        while (cursor.moveToNext()) {
            Agendamento listarCadastro = new Agendamento();
            listarCadastro.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            listarCadastro.setNome((cursor.getString(cursor.getColumnIndex("nome"))));
            listarCadastro.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            listarCadastro.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            listarCadastro.setTipoConsulta((cursor.getString(cursor.getColumnIndex("tipoConsulta"))));
            listarCadastro.setData((cursor.getString(cursor.getColumnIndex("data"))));


            Log.e("nome", "passou : " + listarCadastro.getNome() + String.valueOf(listarCadastro.getId()));

            listarTodosOsElementos.add(listarCadastro);
        }

        cursor.close();

        Log.e("listar", "foi " + listarTodosOsElementos.size());
        return listarTodosOsElementos;
    }

    public long update(Agendamento agendamento) {

        ContentValues values = new ContentValues();
        values.put("nome", agendamento.getNome());
        values.put("telefone", agendamento.getTelefone());
        values.put("endereco", agendamento.getEndereco());
        values.put("tipoConsulta", agendamento.getTipoConsulta());
        values.put("data", agendamento.getData());

        Log.e("pass", "update: " + agendamento.getNome());

        return getDabase().update("aged", values, "_id = ?", new String[]{String.valueOf(agendamento.getId())});

    }

    public void delete(Integer id) {
        getDabase().delete("aged", "_id = ?", new String[]{String.valueOf(id)});

        Log.e("passouAqui", "Delete" + ListarBanco().size());
    }


    public void deleTudo() {
        SQLiteDatabase db = getDabase();
        Log.e("teste", "passou aqui");
        db.execSQL(String.format("DELETE FROM %s", "aged"));
        db.execSQL("VACUUM");
    }


}
