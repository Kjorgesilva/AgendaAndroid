package com.example.kjorge.agenda.webservice;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kjorge.agenda.Dao.AgendamentoDAO;
import com.example.kjorge.agenda.Model.Agendamento;
import com.example.kjorge.agenda.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AgendaWs {

    private RequestQueue queue;


    public static void getAgendamento(final Context contexto, String path) {

        final EventBus eventBus = EventBus.getDefault();

        RequestQueue queue = Volley.newRequestQueue(contexto);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Connection.getUrl() + path, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Agendamento> agendamentosList = new Gson().fromJson(response.toString(), new TypeToken<List<Agendamento>>() {
                }.getType());

                if (agendamentosList != null) {
                    Toast.makeText(contexto, "erro", Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonArrayRequest);


    }

    public void doPost(final Context contexto, final String name, final Map<String, String> params) {
        queue = Volley.newRequestQueue(contexto);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                Connection.getUrl() + name, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                AgendamentoDAO db = new AgendamentoDAO(contexto);
                Gson gson = new Gson();
                Agendamento agendamento = gson.fromJson(response.toString(), Agendamento.class);
                db.inserir(agendamento);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(contexto, "Erro", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(contexto, "Time erro", Toast.LENGTH_LONG).show();
                }
            }
        });
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    public void doPostUpdate(final Context contexto, final String name, final Map<String, String> params) {
        queue = Volley.newRequestQueue(contexto);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                Connection.getUrl() + name, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                AgendamentoDAO db = new AgendamentoDAO(contexto);
                Gson gson = new Gson();
                Agendamento agendamento = gson.fromJson(response.toString(), Agendamento.class);
                db.update(agendamento);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(contexto, "Erro", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(contexto, "Time erro", Toast.LENGTH_LONG).show();
                }
            }
        });
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);


    }

    public void doPostDelet(final Context contexto, final String name, final Map<String, String> params) {
        queue = Volley.newRequestQueue(contexto);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                Connection.getUrl() + name, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Agendamento agendaDel = new Gson().fromJson(response.toString(), Agendamento.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(contexto, "Erro", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(contexto, "Time erro", Toast.LENGTH_LONG).show();
                }
            }
        });
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    public void doPostDeletTudo(final Context contexto, final String name, final Map<String, String> params) {
        queue = Volley.newRequestQueue(contexto);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                Connection.getUrl() + name, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Agendamento agendaDel = new Gson().fromJson(response.toString(), Agendamento.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(contexto, "Erro", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(contexto, "Time erro", Toast.LENGTH_LONG).show();
                }
            }
        });
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }


}
