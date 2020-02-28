package co.com.rni.encuestadormovil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.webkit.WebChromeClient;

import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


public class Servicios extends AppCompatActivity {

    private LinearLayout llVolverParametricas;
    private WebView webView1;
    private ProgressBar progressBar;
    //private String mainUrl = "http://vivantov2.unidadvictimas.gov.co";
    private String mainUrl = "file:///android_asset/www/index.html";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);


        webView1 = (WebView) findViewById(R.id.webview1);
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.setWebChromeClient(new WebChromeClient());
        webView1.clearCache(true);
        webView1.clearHistory();
        webView1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //webView1.loadUrl(mainUrl);

        webView1.getSettings().setBuiltInZoomControls(true); // Habilita el Zoom
        webView1.setWebViewClient(new webviewclient());
        webView1.loadUrl(mainUrl);


        webView1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView1.canGoBack()) {
                                webView1.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });




        if (Build.VERSION.SDK_INT >= 21) { webView1.getSettings().setMixedContentMode( WebSettings.MIXED_CONTENT_ALWAYS_ALLOW ); }



        //Sincronizamos la barra de progreso de la web
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        webView1.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
                Servicios.this.setProgress(progress * 1000);

                progressBar.incrementProgressBy(progress);

                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }

        });

        llVolverParametricas = (LinearLayout) findViewById(R.id.llVolverParametricas);
        llVolverParametricas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irParametricas = new Intent(v.getContext(), Parametricas.class);
                startActivity(irParametricas);
                finish();
            }
        });

    }




    @Override
    // Detectar cuando se presiona el botón de retroceso
    public void onBackPressed() {
        if(webView1.canGoBack()) {
            webView1.goBack();
        } else {
            //super.onBackPressed();
        }
    }


    public class webviewclient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(getBaseContext(), description, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            super.onPageStarted(view, url, favicon);
            String TAG = null;
            Log.i(TAG,"onPageStarted() URL: " + url);
        }

    }

}

