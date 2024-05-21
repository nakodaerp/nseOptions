package com.mterp.virtual.trading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mterp.virtual.trading.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

class JS_INTERFACE2 {
    protected WebView mWebView;
    protected homeActivity parentActivity;

    public String html;

    //Context mContext;
    /** Instantiate the interface and set the context */
    public JS_INTERFACE2(homeActivity loadActivity, WebView webView) {
        //mContext = c;
        mWebView = webView;
        parentActivity = loadActivity;
    }
    /** Show a toast from the web page */

    @JavascriptInterface
    public void showHTML2(String _html) {
        html = _html;
        //Helper.showMessageDialog(parentActivity.getApplicationContext(),html);
        //System.out.println("shtml="+html);
        parentActivity.runFncn92(html);

    }

    @JavascriptInterface
    public void showHTML(String _html) {
        html = _html;
        //Helper.showMessageDialog(parentActivity.getApplicationContext(),html);
        //System.out.println("shtml="+html);
        parentActivity.runFncn2(html);

    }

}

public class homeActivity extends AppCompatActivity {
    Button btnBankNifty,btnNifty;
    LinearLayout lnLyBankNifty,lnLyNifty;
    String consumerKey = "";

    WebView webview, webview2;
    JS_INTERFACE2 jsInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("Virtual Trading");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        //getActionBar().setDisplayShowHomeEnabled( true ); // In your onCreate() or wherever.

        lnLyBankNifty=findViewById(R.id.lnLyBankNifty);
        lnLyNifty=findViewById(R.id.lnLyNifty);

        consumerKey = BuildConfig.API_KEY;

        //System.out.println( BuildConfig.KEY);

        btnBankNifty = findViewById(R.id.btnBankNifty);
        btnNifty = findViewById(R.id.btnNifty);



//=============================================================//

        webview = (WebView) findViewById(R.id.webView);
        WebSettings settings = webview.getSettings();
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setUseWideViewPort(false);
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setLightTouchEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        WebView.enableSlowWholeDocumentDraw();
        //webview.loadUrl("https://www.nseindia.com/api/option-chain-indices?symbol=BANKNIFTY");
        //webview.addJavascriptInterface(new JavaScriptInterface(chatviewsgroup.this, webview), "MyHandler");
        jsInterface = new JS_INTERFACE2(this, webview);
        //webView.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(jsInterface, "MyHandler");
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //Load HTML
                //loadPyOptIndex();

            }
        });

        //webview.loadUrl("");
        webview.loadUrl("file:///android_asset/www/optIndexAll.html");   // now it will not fail here
//webview.loadUrl("javascript:loadtbl;window.MyHandler.loadtbl(;");


        //============================================//
        //============================================//
        webview2 = (WebView) findViewById(R.id.webView2);
        WebSettings settings2 = webview2.getSettings();
        settings2.setBuiltInZoomControls(false);
        settings2.setSupportZoom(false);
        settings2.setUseWideViewPort(false);
        settings2.setJavaScriptEnabled(true);
        settings2.setSupportMultipleWindows(false);
        settings2.setLoadsImagesAutomatically(true);
        settings2.setLightTouchEnabled(true);
        settings2.setDomStorageEnabled(true);
        settings2.setLoadWithOverviewMode(true);
        WebView.enableSlowWholeDocumentDraw();
        //webview.loadUrl("https://www.nseindia.com/api/option-chain-indices?symbol=BANKNIFTY");
        //webview.addJavascriptInterface(new JavaScriptInterface(chatviewsgroup.this, webview), "MyHandler");
        jsInterface = new JS_INTERFACE2(this, webview2);
        //webView.getSettings().setJavaScriptEnabled(true);
        webview2.addJavascriptInterface(jsInterface, "MyHandler");
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webview2.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //Load HTML
                //loadPyOptIndex();
                webview2.loadUrl("javascript:window.MyHandler.showHTML2(''+document.body.innerText);");

            }
        });

        //webview.loadUrl("");
        webview2.loadUrl("https://www.nseindia.com/api/allIndices");   // now it will not fail here
//webview.loadUrl("javascript:loadtbl;window.MyHandler.loadtbl(;");


        //============================================//
        //============================================//






        //============================================//
        //============================================//

        lnLyBankNifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, loadOptChain.class);
                intent.putExtra("curIndex", "BANKNIFTY");
                startActivity(intent);
            }
        });

        lnLyNifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, loadOptChain.class);
                intent.putExtra("curIndex", "NIFTY");
                startActivity(intent);
            }
        });

        btnBankNifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, loadOptChain.class);
                intent.putExtra("curIndex", "BANKNIFTY");
                startActivity(intent);
            }
        });


        //loadFbdbUrlSett();





    }




    String webSocket="";

    void loadFbdbUrlSett(){
        //.child("webSocket")
        FirebaseDatabase.getInstance()
                .getReference("appSettings")
                .child("webSocket")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            webSocket=dataSnapshot.getValue().toString();

                            start("getAllOptVal,"+consumerKey+"");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });



    }

    void loadfbdb() {

        FirebaseDatabase.getInstance()
                .getReference("fbDbOptChain")
                .child("optChain2")
                .addValueEventListener(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            String jsonobj = dataSnapshot.getValue().toString();
                            jsonobj=jsonobj.replace("=,","=0,");

                            //System.out.println("jsonobj="+jsonobj);

                            try {
                                JSONObject optData = new JSONObject(jsonobj);

                                ((TextView) findViewById(R.id.txtBankNiftyVal)).setText(optData.getString("BANKNIFTY"));
                                ((TextView) findViewById(R.id.txtNiftyVal)).setText(optData.getString("NIFTY"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

    }

    String allIndicesJson="";

    public void runFncn92(String SHtml){
        //System.out.println("shtml=2="+SHtml);

        if(SHtml.contains("Resource not found")){

            loadIndexAllWbVw2();


        } else {

            JSONArray jSONObjAll2;
            allIndicesJson="";

            try {
                JSONObject jSONObjAll = new JSONObject(SHtml);
                jSONObjAll2 = jSONObjAll.getJSONArray("data");

                List<String> someList = Arrays.asList("NIFTY 50", "NIFTY MIDCAP SELECT", "NIFTY BANK", "NIFTY FINANCIAL SERVICES");

                for (int i = 0; i < jSONObjAll2.length(); i++) {
                    JSONObject idxData = jSONObjAll2.getJSONObject(i);

                    //if (idxData.getString("index").equalsIgnoreCase(someList.get(0))) {
                    if (someList.contains(idxData.getString("index"))) {
                        if (!(allIndicesJson.equals(""))) {
                            allIndicesJson += ",";
                        }

                        allIndicesJson += "{\"index\":\"" + idxData.getString("index") + "\"";
                        allIndicesJson += ",\"last\":\"" + idxData.getString("last") + "\"";
                        allIndicesJson += ",\"variation\":\"" + idxData.getString("variation") + "\"";
                        allIndicesJson += ",\"percentChange\":\"" + idxData.getString("percentChange") + "\"";
                        allIndicesJson += ",\"open\":\"" + idxData.getString("open") + "\"";
                        allIndicesJson += ",\"high\":\"" + idxData.getString("high") + "\"";
                        allIndicesJson += ",\"low\":\"" + idxData.getString("low") + "\"";
                        allIndicesJson += ",\"previousClose\":\"" + idxData.getString("previousClose") + "\"";

                        allIndicesJson += "}";
                    }
                }

                allIndicesJson = "["+allIndicesJson+"]";
                //System.out.println("shtml=3="+allIndicesJson);


                try {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //btn.setText("#" + i);
                            webview.loadUrl("javascript:loadTbl('"+allIndicesJson+"');");

                            //======================================================//

                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Do something after 200ms
                                    //start("getAllOptVal,"+consumerKey+"");

                                    if(Helper.isBetweenTimeSpan()) {
                                       //loadIndexAllWbVw2();
                                        loadIndexAllWbVw2();
                                    }

                                }
                            }, 600);


                            //======================================================//

                        }
                    });
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }








            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    void loadIndexAllWbVw2() {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Background work here
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                        webview2.loadUrl("https://www.nseindia.com/api/allIndices");   // now it will not fail here
                    }
                });
            }
        });
    }

    void loadIndexAllWbVw24() {
        // java_8
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            //Background work here
            handler.post(() -> {
                //UI Thread work here
            });
        });
    }

    void loadIndexAllWbVw22() {

        try {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    //btn.setText("#" + i);
                    //webview2.loadUrl("https://www.nseindia.com/api/allIndices");   // now it will not fail here

                    new HTML_asyncCLASS().execute();

                }
            });
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runFncn2(String SHtml){
        //System.out.println("shtml=2="+SHtml);
        //Helper.showMessageDialog(MainActivity.this,SHtml);
        if(SHtml.equals("Bank Nifty")) {

            Intent intent = new Intent(homeActivity.this, loadOptChain.class);
            intent.putExtra("curIndex", "BANKNIFTY");
            startActivity(intent);

        }

    }

    //=========================================================//

    //=======================================================//
    //=======================================================//
    //=======================================================//

    private void start(String webMsg2) {
        //Request request = new Request.Builder().url("ws://echo.websocket.org").build();
        Request request = new Request.Builder().url("ws://"+webSocket+":9002").build();
        //webMsg = "Hello22...";
        echoWebSocketListener listener = new echoWebSocketListener(homeActivity.this,webMsg2);
        //echoWebSocketListener.getMsg("Hello22...");

        OkHttpClient client = new OkHttpClient();
        //client = new OkHttpClient();

        WebSocket ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();

    }

    public void output(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //output.setText(output.getText().toString() + "\n\n" + txt);

                procWebSocket(txt);


            }
        });
    }


    //=======================================================//
    //=======================================================//
    //=======================================================//

    void procWebSocket(String strSocket) {
        //System.out.println("otpt="+strSocket);
        String[] strSocketA = strSocket.split((";"));

        //System.out.println("otpt=2="+strSocketA[0]+";"+strSocketA[1]);

        if((strSocketA[0]).equals("getAllOptVal")) {
            String optIndexAll=strSocketA[1];
            webview.loadUrl("javascript:loadTbl('"+optIndexAll+"');");

            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 200ms
                    start("getAllOptVal,"+consumerKey+"");
                }
            }, 100);
        }
    }

    //=======================================================//
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return true;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(
                this, R.style.CustomAlertDialog);

        //builder.setTitle("Gheewala");
        //builder.setMessage("Click yes to exit!");
        //builder.setMessage("Are you sure want to \nexit the application ?");
        builder.setMessage("Are you sure to \nexit the application ?");
        //builder.setIcon(R.mipmap.ic_launcher_round);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
                finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.65);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);

        Window window = alertDialog.getWindow();
        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        alertDialog.show();
        //alertDialog.getWindow().setLayout(width, 425); //Controlling width and height.

        return true;
    }


    private class HTML_asyncCLASS extends AsyncTask<Void, Void, Void> {
        //private ProgressDialog PprogressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            webview2.loadUrl("https://www.nseindia.com/api/allIndices");   // now it will not fail here

            //PprogressDialog = new ProgressDialog(MainActivity.this);
            //PprogressDialog.setMessage("Fetching HTML Code...");
            //PprogressDialog.setIndeterminate(true);
            //PprogressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
//            html_string_async=html_string;
            return null;
        }

        protected void onPostExecute(Void result) {

        }
    }

}




