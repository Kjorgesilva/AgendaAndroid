package com.example.kjorge.agenda.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kjorge.agenda.Dao.AgendamentoDAO;
import com.example.kjorge.agenda.Fragment.Fragment_Historico;
import com.example.kjorge.agenda.InterfaceHelp.InterfaceHelp;
import com.example.kjorge.agenda.Model.Agendamento;
import com.example.kjorge.agenda.R;
import com.example.kjorge.agenda.webservice.AgendaWs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditaActivity extends AppCompatActivity implements InterfaceHelp {
    private EditText edt_nomeED, edt_telefoneED, edt_enderecoED, edt_tdConsultaED, edt_dataED;
    private Button btn_edotarED;
    private Context contexto = this;
    private Agendamento agendd;
    AgendaWs post = new AgendaWs();
    private Handler handler;
    private AgendamentoDAO db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita);

        FindView();
        OnClick();
        getValor();
    }


    @Override
    public void FindView() {
        edt_nomeED = findViewById(R.id.edt_nomeED);
        edt_telefoneED = findViewById(R.id.edt_telefoneED);
        edt_enderecoED = findViewById(R.id.edt_enderecoED);
        edt_tdConsultaED = findViewById(R.id.edt_tdConsultaED);
        edt_dataED = findViewById(R.id.edt_dataED);
        btn_edotarED = findViewById(R.id.btn_alterarED);
        handler = new Handler();
        db = new AgendamentoDAO(contexto);


    }

    public void getValor() {

        agendd = (Agendamento) getIntent().getSerializableExtra("objeto");
        edt_telefoneED.addTextChangedListener(Mask.insert("(##)####-#####", edt_telefoneED));
        edt_dataED.addTextChangedListener(Mask.insert("##/##/####", edt_dataED));
        edt_tdConsultaED.addTextChangedListener(Mask.insert("(###)##/##/##", edt_tdConsultaED));


        edt_nomeED.setText(agendd.getNome());
        edt_telefoneED.setText(agendd.getTelefone());
        edt_enderecoED.setText(agendd.getEndereco());
        edt_tdConsultaED.setText(agendd.getTipoConsulta());
        edt_dataED.setText(agendd.getData());
    }


    @Override
    public void OnClick() {
        btn_edotarED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //USA O MESMO METODO DE INSERIR PASSANDO O ID E USANDO O  "MERGE NO ECLIPSE"

                if (edt_nomeED.getText().toString().isEmpty()) {
                    edt_nomeED.setError("Preencha o campo");
                } else if (edt_telefoneED.getText().toString().isEmpty()) {
                    edt_telefoneED.setError("Preencha o campo");
                } else if (edt_enderecoED.getText().toString().isEmpty()) {
                    edt_enderecoED.setError("Preencha o campo");
                } else if (edt_tdConsultaED.getText().toString().isEmpty()) {
                    edt_tdConsultaED.setError("Preencha o campo");
                } else if (edt_dataED.getText().toString().isEmpty()) {
                    edt_dataED.setError("Preencha o campo");
                } else {

                final int id = agendd.getId();
                String nome = edt_nomeED.getText().toString();
                String telefone = edt_telefoneED.getText().toString();
                String endereco = edt_enderecoED.getText().toString();
                String tipoConsulta = edt_tdConsultaED.getText().toString();
                String data = edt_dataED.getText().toString();



                    Map<String, String> map = new HashMap<>();
                    map.put("id", String.valueOf(id));
                    map.put("nome", nome);
                    map.put("telefone", telefone);
                    map.put("endereco", endereco);
                    map.put("tipoConsulta", tipoConsulta);
                    map.put("data", data);

                    post.doPostUpdate(contexto, "agenda/postAgendamento", map);


                String retornValor = "valor";
                Intent intent = new Intent(contexto, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("valor", retornValor);
                startActivity(intent);
                finish();

            }}

        });

    }
}




