package com.example.kjorge.agenda.Fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.kjorge.agenda.Activity.EditaActivity;
import com.example.kjorge.agenda.Adapter.AdapterAgendamento;
import com.example.kjorge.agenda.Dao.AgendamentoDAO;
import com.example.kjorge.agenda.Model.Agendamento;
import com.example.kjorge.agenda.R;
import com.example.kjorge.agenda.webservice.AgendaWs;
import com.example.kjorge.agenda.webservice.Connection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

public class Fragment_Historico extends Fragment {
    private RecyclerView recyclerView;
    private AdapterAgendamento adapterAgendamento;
    private AgendamentoDAO db = new AgendamentoDAO(getContext());
    private List<Agendamento> agendaList = new ArrayList<>();
    private List<Agendamento> auxLista = new ArrayList<>();
    private Switch aSwitch;
    private Button btn_excluir;

    private Handler handler;
    private Context context;


    public Fragment_Historico() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment__historico, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        handler = new Handler();
        context = this.getContext();
        db = new AgendamentoDAO(getContext());
        aSwitch = view.findViewById(R.id.sw_ligado);
        btn_excluir = view.findViewById(R.id.btn_excluir);

        getAgenda(getContext(), "agenda/getAgenda");
        Log.e("passou", "onCreate");
        db.ListarBanco().addAll(agendaList);

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aSwitch.isChecked()) {
                    btn_excluir.setVisibility(View.VISIBLE);
                } else {
                    btn_excluir.setVisibility(View.GONE);
                }
            }
        });
        btn_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                View mapeaView = getLayoutInflater().inflate(R.layout.dialog_deleta_tudo, null);
                alerta.setView(mapeaView);
                final AlertDialog fechar = alerta.create();
                fechar.setCanceledOnTouchOutside(false);

                Button btn_nao = mapeaView.findViewById(R.id.btnNao);
                btn_nao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fechar.cancel();
                    }
                });

                Button btnSim = mapeaView.findViewById(R.id.btnSim);
                btnSim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AgendaWs postDel = new AgendaWs();
                        //  int id = agendaList.get(position).getId();
                        db.deleTudo();
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapterAgendamento = new AdapterAgendamento(getContext(), db.ListarBanco(), clickListner());
                        recyclerView.setAdapter(adapterAgendamento);


                        Map<String, String> map = new HashMap<>();
                        postDel.doPostDeletTudo(getContext(), "agenda/postDeletAgendamentoTudo", map);
                        Toast.makeText(getContext(), "Todas Consulta Cancelada", Toast.LENGTH_LONG).show();
                        fechar.cancel();
                    }
                });
                fechar.show();


            }
        });


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (getContext() != null) {

                getAgenda(context, "agenda/getAgenda");
                //setLayoutManager para exibir o recyclerView
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new AdapterAgendamento(getContext(), db.ListarBanco(), clickListner()));
            }

        }
    }


    @Override
    public void onResume() {
        Log.e("passou", "onResume");
        getAgenda(context, "agenda/getAgenda");

        //setLayoutManager para exibir o recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new AdapterAgendamento(getContext(), db.ListarBanco(), clickListner()));


        super.onResume();
    }

    private AdapterAgendamento.AgendamentoOnClickListner clickListner(){
        return new AdapterAgendamento.AgendamentoOnClickListner() {

            @SuppressLint("RestrictedApi")
            @Override
            public void clickListenerView(View view, final int position) {


                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                View mapeaView = getLayoutInflater().inflate(R.layout.dialog_historico, null);
                alerta.setView(mapeaView);
                final AlertDialog fechar = alerta.create();
                fechar.setCanceledOnTouchOutside(false);

                TextView nome = mapeaView.findViewById(R.id.alertNome);
                TextView telefone = mapeaView.findViewById(R.id.alertTelefone);
                TextView endereco = mapeaView.findViewById(R.id.alertEndereco);
                TextView tconsulta = mapeaView.findViewById(R.id.alertTipoConsulta);
                TextView data = mapeaView.findViewById(R.id.alertData);

                Button btn_edt = mapeaView.findViewById(R.id.btn_edt);
                btn_edt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent intent = new Intent(getContext(), EditaActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent.putExtra("objeto", agendaList.get(position));
                        intent.putExtra("objeto", db.ListarBanco().get(position));
                        Toast.makeText(context, "id: " + String.valueOf(db.ListarBanco().get(position).getId()),Toast.LENGTH_LONG ).show();
                        startActivity(intent);
                        fechar.cancel();

                    }
                });

                Button btn_fechar = mapeaView.findViewById(R.id.btn_ok);

                btn_fechar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fechar.cancel();
                    }
                });


                nome.setText(db.ListarBanco().get(position).getNome().toUpperCase());
                telefone.setText(db.ListarBanco().get(position).getTelefone());
                endereco.setText(db.ListarBanco().get(position).getEndereco());
                tconsulta.setText(db.ListarBanco().get(position).getTipoConsulta());
                data.setText(db.ListarBanco().get(position).getData());


                fechar.show();
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onLongClick(View view, final int position) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Atenção");
                alert.setMessage("Deseja excluir a consulta ? ");
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AgendaWs postDel = new AgendaWs();
                       //   int id = agendaList.get(position).getId();
                      int id = db.ListarBanco().get(position).getId();
                        db.delete(id);

                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapterAgendamento = new AdapterAgendamento(getContext(), db.ListarBanco(), clickListner());
                        recyclerView.setAdapter(adapterAgendamento);


                        Map<String, String> map = new HashMap<>();
                        map.put("id", String.valueOf(id));
                        postDel.doPostDelet(getContext(), "agenda/postDeletAgendamento", map);
                        Toast.makeText(getContext(), "Consulta Cancelada", Toast.LENGTH_LONG).show();


                    }
                });

                alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alert.show();
            }
        };


    }

    public void getAgenda(final Context contexto, final String path) {
        final EventBus eventBus = EventBus.getDefault();
        RequestQueue queue = Volley.newRequestQueue(contexto);

        auxLista.addAll(db.ListarBanco());


        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Connection.getUrl() + path, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                auxLista.clear();
                auxLista = new Gson().fromJson(response.toString(), new TypeToken<List<Agendamento>>() {
                }.getType());
                if (auxLista != null) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterAgendamento = new AdapterAgendamento(contexto, auxLista, clickListner());
                    recyclerView.setAdapter(adapterAgendamento);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto, "erro " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        JsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(JsonArrayRequest);


    }


}



