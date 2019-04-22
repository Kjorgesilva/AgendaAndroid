package com.example.kjorge.agenda.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.kjorge.agenda.Activity.Mask;
import com.example.kjorge.agenda.Dao.AgendamentoDAO;
import com.example.kjorge.agenda.Model.Agendamento;
import com.example.kjorge.agenda.R;
import com.example.kjorge.agenda.webservice.AgendaWs;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Agendamento extends Fragment {
    private Button btn_cad;
    private EditText edt_nome, edt_telefone, edt_endereco, edt_tip_consulta, edt_data;
    private AgendamentoDAO db = new AgendamentoDAO(getContext());
    AgendaWs post = new AgendaWs();


    public Fragment_Agendamento() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment__agendamento, container, false);

        //findView

        btn_cad = view.findViewById(R.id.btn_cadastro);
        edt_nome = view.findViewById(R.id.edt_nome);
        edt_telefone = view.findViewById(R.id.edt_telefone);
        edt_endereco = view.findViewById(R.id.edt_endereco);
        edt_tip_consulta = view.findViewById(R.id.edt_tip_consulta);
        edt_data = view.findViewById(R.id.edt_data);
        edt_telefone.addTextChangedListener(Mask.insert("(##)####-#####", edt_telefone));
        edt_data.addTextChangedListener(Mask.insert("##/##/####", edt_data));
        edt_tip_consulta.addTextChangedListener(Mask.insert("(###)##/##/##", edt_tip_consulta));
        db = new AgendamentoDAO(getContext());


        //Onclick
        btn_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_nome.getText().toString().isEmpty()) {
                    edt_nome.setError("Preencha o campo");
                } else if (edt_telefone.getText().toString().isEmpty()) {
                    edt_telefone.setError("Preencha o campo");
                } else if (edt_endereco.getText().toString().isEmpty()) {
                    edt_endereco.setError("Preencha o campo");
                } else if (edt_tip_consulta.getText().toString().isEmpty()) {
                    edt_tip_consulta.setError("Preencha o campo");
                } else if (edt_data.getText().toString().isEmpty()) {
                    edt_data.setError("Preencha o campo");
                } else {
                    String nome = edt_nome.getText().toString();
                    String telefone = edt_telefone.getText().toString();
                    String endereco = edt_endereco.getText().toString();
                    String tipoConsulta = edt_tip_consulta.getText().toString();
                    String data = edt_data.getText().toString();


                    Map<String, String> map = new HashMap<>();
                    map.put("id", "id");
                    map.put("nome", nome);
                    map.put("telefone", telefone);
                    map.put("endereco", endereco);
                    map.put("tipoConsulta", tipoConsulta);
                    map.put("data", data);

                    post.doPost(getContext(), "agenda/postAgendamento", map);

                    Log.e("ADD no SQLITE", "tamanho: " + db.ListarBanco().size());


                    edt_nome.setText("");
                    edt_telefone.setText("");
                    edt_endereco.setText("");
                    edt_tip_consulta.setText("");
                    edt_data.setText("");


                }
            }
        });


        return view;
    }

}
