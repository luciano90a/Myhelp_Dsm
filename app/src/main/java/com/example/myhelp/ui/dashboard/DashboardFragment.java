package com.example.myhelp.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myhelp.R;
import com.example.myhelp.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private WebView web; //declaramos el webview
    private EditText url; //declaramos el edittext

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        web=(WebView)root.findViewById(R.id.webView);//asignamos el webview
        url=(EditText)root.findViewById(R.id.url_txt);//asignamos el edittext
        //verificamos que el edittext no este vacio
        if (url.getText().toString().isEmpty()){
            url.setText("https://www.google.com/");//si esta vacio le asignamos una url por defecto
        }//verificamos que la url sea valida
        if (!url.getText().toString().contains("https://")){
            url.setText("https://"+url.getText().toString());//si no contiene https:// se lo agregamos
        }
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(url.getText().toString());//cargamos la url en el webview
        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}