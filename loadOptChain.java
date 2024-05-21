package com.mterp.virtual.trading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
//import tech.gusavila92.websocketclient.WebSocketClient;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mterp.virtual.trading.adapter.horzCtgrydapter;
import com.mterp.virtual.trading.entity.CategoryObject;
import com.mterp.virtual.trading.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

class JS_INTERFACE {
    protected WebView mWebView;
    protected loadOptChain parentActivity;

    public String html;

    //Context mContext;
    /** Instantiate the interface and set the context */
    public JS_INTERFACE(loadOptChain loadActivity, WebView webView) {
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

public class loadOptChain extends AppCompatActivity {

    String curIndex="";
    boolean expiryLoad=false;
    RecyclerView recyclerView;
    LinearLayout listHrCtgry2;
    ArrayList<CategoryObject> hrzCtgryItem;

    JSONArray optData;

    Button btnVwChart;
    WebView webview, webview2;
    JS_INTERFACE jsInterface;

    //private OkHttpClient client;
    //private OkHttpClient client;
    public String webMsg="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_opt_chain);

        setTitle("Option Chain");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        curIndex = getIntent().getExtras().getString("curIndex");

        recyclerView = findViewById(R.id.ctgry_list);
        btnVwChart = findViewById(R.id.btnVwChart);
        listHrCtgry2 = findViewById(R.id.listHrCtgry2);

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
        jsInterface = new JS_INTERFACE(this, webview);
        //webView.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(jsInterface, "MyHandler");
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //Load HTML
                //Helper.showMessageDialog(actOptChain.this,"Page load completed...");
                //webview.loadUrl("javascript:window.MyHandler.showHTML(''+document.body.innerText);");
            }
        });

        //webview.loadUrl("https://www.nseindia.com/api/option-chain-indices?symbol="+curIndex);


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
        jsInterface = new JS_INTERFACE(this, webview2);
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
        //webview2.loadUrl("https://www.nseindia.com/api/allIndices");   // now it will not fail here
        webview2.loadUrl("https://www.nseindia.com/api/option-chain-indices?symbol="+curIndex);
//webview.loadUrl("javascript:loadtbl;window.MyHandler.loadtbl(;");


        //============================================//
        //============================================//
        btnVwChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(loadOptChain.this, loadwebsites.class);
                //intent.putExtra("payUrl", "https://in.tradingview.com/chart/?symbol=NSE%3A"+curIndex); //BANKNIFTY
                //startActivity(intent);
                //start2();
                //sendMessage("192.168.0.106", 9002, "Hello..ServerSentData...");

                start("Hello22...");

            }
        });



    }


    void loadFbDb() {

        FirebaseDatabase.getInstance()
                .getReference("fbDbOptChain")
                .child("optChain3")
                .child(curIndex)
                .child("expDates")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        hrzCtgryItem = new ArrayList<>();
                        if (dataSnapshot.getValue() != null) {
                            String jsonobj = dataSnapshot.getValue().toString();
                            String[] allExpDate = jsonobj.split((","));
                            for(int wI=1;wI<allExpDate.length;wI++){
                                CategoryObject categoryObject2 = new CategoryObject(wI,allExpDate[wI] , "", "", 0, "");
                                hrzCtgryItem.add(categoryObject2);

                                if(curoptExpDte.equals("")){
                                    curoptExpDte = allExpDate[wI];
                                    doLoadActiveExpiry(curoptExpDte);
                                }

                            }

                            recyclerView = (RecyclerView) findViewById(R.id.ctgry_list);
                            horzCtgrydapter horzctgrydapter = new horzCtgrydapter(hrzCtgryItem, loadOptChain.this, 0, 0, "Ctgry");
                            recyclerView.setLayoutManager(new LinearLayoutManager(loadOptChain.this, 0, false));
                            recyclerView.setAdapter(horzctgrydapter);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
    }



    //=======================================================//
    //=======================================================//
    //=======================================================//

    private void start(String webMsg2) {
        //Request request = new Request.Builder().url("ws://echo.websocket.org").build();
        Request request = new Request.Builder().url("ws://192.168.1.36:9002").build();
        webMsg = "Hello22...";
        echoWebSocketListener listener = new echoWebSocketListener(loadOptChain.this,webMsg2);
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

                System.out.println("otpt="+txt);
            }
        });
    }


    //=======================================================//
    //=======================================================//
    //=======================================================//




    void dataLoadFbdb(){





        //.child(fbaUserId)
        FirebaseDatabase.getInstance()
                .getReference("fbDbOptChain")
                .child("optChain1")
                .child(curIndex)
                .addValueEventListener(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            String jsonobj = dataSnapshot.getValue().toString();

                            try {
                                JSONObject jSONObjTot = new JSONObject(jsonobj);
                                optData = jSONObjTot.getJSONObject("records").getJSONArray("data");

                                //==================================//
                                if(expiryLoad==false) {
                                    expiryLoad = true;
                                    hrzCtgryItem = new ArrayList<>();

                                    //LinearLayout linearLayout2 = new LinearLayout(getApplicationContext());
                                    //linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    //linearLayout2.setOrientation(LinearLayout.HORIZONTAL);

                                    //JSONArray jSONArr2 = jSONObjTot.getJSONObject("records").getJSONArray("expiryDates");
                                    JSONArray expiryDates = jSONObjTot.getJSONObject("records").getJSONArray("expiryDates");
                                    for (int i = 0; i < expiryDates.length(); i++) {
                                        String dates = expiryDates.getString(i);

                                        if(curoptExpDte.equals("")){
                                            curoptExpDte = expiryDates.getString(i);
                                            doLoadActiveExpiry(curoptExpDte);
                                        }

                                        CategoryObject categoryObject2 = new CategoryObject(i,dates , "", "", 0, "");
                                        hrzCtgryItem.add(categoryObject2);
                                        //addTextView(""+dates, linearLayout2, (totAllCol * (0 + 1)) + i, 360, false);
                                    }

                                    //listHrCtgry2.addView(linearLayout2);


                                    recyclerView = (RecyclerView) findViewById(R.id.ctgry_list);
                                    horzCtgrydapter horzctgrydapter = new horzCtgrydapter(hrzCtgryItem, loadOptChain.this, 0, 0, "Ctgry");
                                    recyclerView.setLayoutManager(new LinearLayoutManager(loadOptChain.this, 0, false));
                                    recyclerView.setAdapter(horzctgrydapter);

                                }

                                //==================================//



                                //==================================//

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


    int totAllCol=15;
    DecimalFormat precision = new DecimalFormat("0.00");
    String curoptExpDte="";
    //===================================================================//



    public void addTextView(String str, LinearLayout linearLayout, int i, int i2, boolean z) {
        TextView textView = new TextView(getApplicationContext());
        textView.setText(str);
        if (textView.getParent() != null) {
            ((ViewGroup) textView.getParent()).removeView(textView);
        }
        linearLayout.addView(textView);
        textView.setId(i);
        //textView.fon

        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        layoutParams.width = i2;
        //if (z) {
        //    textView.setBackgroundResource(R.drawable.table_header_cell_bg);
        //} else {
            textView.setBackgroundResource(R.drawable.table_content_cell_bg);


        //textView.setBackgroundResource(R.drawable.table_content_cell_bg);
        textView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = textView.getId();
                        int i = id % totAllCol;
                        int i2 = id / totAllCol;

                        String selDates=textView.getText().toString();
                        doLoadActiveExpiry(selDates);

                    }
                });

        //}

        textView.setLayoutParams(layoutParams);

    }

    String allIndicesJson="";

    public void runFncn92(String SHtml) {
        System.out.println("shtml=2="+SHtml);

        if (SHtml.contains("Resource not found")) {
            loadIndexAllWbVw2();
        } else {
            JSONArray jSONObjAll2;
            allIndicesJson="";

            try {
                JSONObject jSONObjAll = new JSONObject(SHtml);
                jSONObjAll2 = jSONObjAll.getJSONObject("records").getJSONArray("expiryDates");

                //optData = new JSONArray(jsonobj);
                optData = jSONObjAll.getJSONObject("records").getJSONArray("data");

                if(expiryLoad==false) {
                    expiryLoad = true;
                    hrzCtgryItem = new ArrayList<>();
                    for (int i = 0; i < jSONObjAll2.length(); i++) {
                        String dates = jSONObjAll2.getString(i);

                        if (curoptExpDte.equals("")) {
                            curoptExpDte = jSONObjAll2.getString(i);
                            doLoadActiveExpiry(curoptExpDte);
                        }

                        CategoryObject categoryObject2 = new CategoryObject(i, dates, "", "", 0, "");
                        hrzCtgryItem.add(categoryObject2);
                        //addTextView(""+dates, linearLayout2, (totAllCol * (0 + 1)) + i, 360, false);
                    }

                    //listHrCtgry2.addView(linearLayout2);

                    loadRecyclerVw2();

                }



                try {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //btn.setText("#" + i);
                            doLoadActiveExpiry2(curoptExpDte);

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

    void loadRecyclerVw2() {

        try {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    //btn.setText("#" + i);

                    recyclerView = (RecyclerView) findViewById(R.id.ctgry_list);
                    horzCtgrydapter horzctgrydapter = new horzCtgrydapter(hrzCtgryItem, loadOptChain.this, 0, 0, "Ctgry");
                    recyclerView.setLayoutManager(new LinearLayoutManager(loadOptChain.this, 0, false));
                    recyclerView.setAdapter(horzctgrydapter);
                }
            });
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    void loadIndexAllWbVw2() {
        // java_8
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            //Background work here
            handler.post(() -> {
                //UI Thread work here
                webview2.loadUrl("https://www.nseindia.com/api/option-chain-indices?symbol="+curIndex);
            });
        });
    }

    void loadIndexAllWbVw24() {

        try {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    //btn.setText("#" + i);
                    webview2.loadUrl("https://www.nseindia.com/api/option-chain-indices?symbol="+curIndex);
                }
            });
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runFncn2(String SHtml){
        System.out.println("shtml=2="+SHtml);
        //Helper.showMessageDialog(actOptChain.this,SHtml);



    }

    String ssHtml = "";
    public void doLoadActiveExpiry(String optExpDte) {
        curoptExpDte=optExpDte;

    }
    public void doLoadActiveExpiry9(String optExpDte) {
        curoptExpDte=optExpDte;

        //.child(fbaUserId)
        FirebaseDatabase.getInstance()
                .getReference("fbDbOptChain")
                .child("optChain3")
                .child(curIndex)
                .child("data")
                .child(optExpDte)
                .addValueEventListener(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            String jsonobj = dataSnapshot.getValue().toString();

                            try {
                                optData = new JSONArray(jsonobj);

                                doLoadActiveExpiry2(curoptExpDte);

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

    public void doLoadActiveExpiry2(String optExpDte) {
        //Toast.makeText(loadOptChain.this, "Clicked on "+optExpDte, Toast.LENGTH_LONG).show();
        String optTable = "";
        String optTablePE = "";
        double ceOiTot = 0;
        double peOiTot = 0;
        double pcrVal = 0;

        for (int i = 0; i < optData.length(); i++) {
            try {
                JSONObject jSONObjCur = optData.getJSONObject(i);
                if (jSONObjCur.getString("expiryDate").equals(optExpDte)) {
                    JSONObject jSONObjCE = optData.getJSONObject(i).getJSONObject("CE");
                    JSONObject jSONObjPE = optData.getJSONObject(i).getJSONObject("PE");

                    optTable += "<tr id=\"" + jSONObjCE.getString("identifier") + "\">";
                    optTable += "<td>" + jSONObjCE.getString("totalTradedVolume") + "</td>";
                    optTable += "<td>" + "" + precision.format(Double.parseDouble(jSONObjCE.getString("pchangeinOpenInterest"))) + "</td>";
                    optTable += "<td>" + jSONObjCE.getString("changeinOpenInterest") + "</td>";
                    optTable += "<td>" + jSONObjCE.getString("openInterest") + "</td>";
                    optTable += "<td>" + "" + precision.format(Double.parseDouble(jSONObjCE.getString("pChange"))) + "</td>";
                    optTable += "<td>" + "" + precision.format(Double.parseDouble(jSONObjCE.getString("change"))) + "</td>";
                    optTable += "<td>" + jSONObjCE.getString("lastPrice") + "</td>";
                    optTable += "<td>" + jSONObjCE.getString("strikePrice") + "</td>";
                    optTable += "</tr>";

                    ceOiTot += Double.parseDouble(jSONObjCE.getString("openInterest"));


                    optTablePE += "<tr id=\"" + jSONObjPE.getString("identifier") + "\">";
                    optTablePE += "<td>" + jSONObjPE.getString("strikePrice") + "</td>";
                    optTablePE += "<td>" + jSONObjPE.getString("lastPrice") + "</td>";
                    optTablePE += "<td>" + "" + precision.format(Double.parseDouble(jSONObjPE.getString("change"))) + "</td>";
                    optTablePE += "<td>" + "" + precision.format(Double.parseDouble(jSONObjPE.getString("pChange"))) + "</td>";
                    optTablePE += "<td>" + jSONObjPE.getString("openInterest") + "</td>";
                    optTablePE += "<td>" + jSONObjPE.getString("changeinOpenInterest") + "</td>";
                    optTablePE += "<td>" + "" + precision.format(Double.parseDouble(jSONObjPE.getString("pchangeinOpenInterest"))) + "</td>";
                    optTablePE += "<td>" + jSONObjPE.getString("totalTradedVolume") + "</td>";
                    optTablePE += "</tr>";

                    peOiTot += Double.parseDouble(jSONObjPE.getString("openInterest"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //===============================================================//
        //===============================================================//
        pcrVal = (peOiTot / ceOiTot) * 100;

        ssHtml = "";
        ssHtml += "<html><head>";
        ssHtml += "\n<style type=\"text/css\">";

        ssHtml += "\n";
        ssHtml += "\n.tableFixHead {";
        ssHtml += "\n  overflow-y: auto;";
        ssHtml += "\n  height: 100%;";
        ssHtml += "\n}";
        ssHtml += "\n";
        ssHtml += "\n";
        ssHtml += "\n/* Just common table stuff. */";
        ssHtml += "\n";
        ssHtml += "\ntable {";
        ssHtml += "\n  border-collapse: collapse;";
        ssHtml += "\n  width: 100%;";
        ssHtml += "\n}";
        ssHtml += "\n";
        ssHtml += "\nth,";
        ssHtml += "\ntd {";
        ssHtml += "\n  padding: 4px 4px;";
        ssHtml += "\n}";
        ssHtml += "\n";
        ssHtml += "\nth {";
        ssHtml += "\n  background: #eee;";
        ssHtml += "\n}";
        ssHtml += "\n";
        ssHtml += "\n</style>";
        ssHtml += "\n";
        ssHtml += "\n<script type=\"text/javascript\" src=\"file:///android_asset/www/jquery.min.js\"></script>";
        ssHtml += "\n</head><body onload=\"runTblFncn()\">";
        ssHtml += "\n";
        ssHtml += "\n<div id=\"divCEAll\" style=\"position:fixed;left:1px;top:1px;width:50%;height:80%;border:1px solid red;background-color:white;\">";
        ssHtml += "\n   <div id=\"divCE\" class=\"tableFixHeadC\" style=\"position:absolute;left:1px;top:1px;width:100%;height:100%;border:0px solid;background-color:white;overflow: auto;\">";
        ssHtml += "\n   <table id=\"tblCE\" border=\"1\" style=\"border:1px solid black;border-collapse:collapse;\">";
        ssHtml += "\n   <tbody>";
        ssHtml += "\n   <tr><td>Volume</td><td>Vol%</td><td>ChOI</td><td>OI</td><td>Chg%</td><td>Change</td><td>Price</td><td>Strike</td></tr>";
        ssHtml += "\n"+optTable;
        ssHtml += "\n   </tbody></table>";
        ssHtml += "\n   </div>";
        ssHtml += "\n</div>";
        ssHtml += "\n";
        ssHtml += "\n";
        ssHtml += "\n<div id=\"divPEAll\" style=\"position:fixed;left:50%;top:1px;width:50%;height:80%;border:1px solid red;background-color:white;\">";
        ssHtml += "\n   <div id=\"divPE\" class=\"tableFixHead\" style=\"position:absolute;left:1px;top:1px;width:100%;height:100%;border:0px solid;background-color:white;overflow: auto;\">";
        ssHtml += "\n   <table id=\"tblPE\" border=\"1\" style=\"border:1px solid black;border-collapse:collapse;\">";
        ssHtml += "\n   <tbody>";
        ssHtml += "\n   <tr><td>Strike</td><td>Price</td><td>Change</td><td>Chg%</td><td>OI</td><td>ChOI</td><td>Vol%</td><td>Volume</td></tr>";
        ssHtml += "\n"+optTablePE;
        ssHtml += "\n   </tbody></table>";
        ssHtml += "\n   </div>";
        ssHtml += "\n</div>";
        ssHtml += "\n";
        ssHtml += "\n<br>";
        ssHtml += "\n";
        ssHtml += "\n<div style=\"position:fixed;left:50%;top:83%;width:50%;height:23px;border:0px solid red;background-color:white;\">";
        ssHtml += "\nPCR:"+precision.format(pcrVal);
        ssHtml += "\n</div>";
        ssHtml += "\n<br>";
        ssHtml += "\n";
        ssHtml += "\n</body>";
        ssHtml += "\n<script type=\"text/javascript\" >";
        ssHtml += "\n";
        ssHtml += "\nfunction runTblFncn(){";
        ssHtml += "\n";
        ssHtml += "\n";
        ssHtml += "\n}";
        ssHtml += "\n";
        ssHtml += "\n";
        ssHtml += "\n// Fix table head example:";
        ssHtml += "\nelement = document.getElementById('divCE');";
        ssHtml += "\nvar maxScrollLeft = element.scrollWidth - element.clientWidth;";
        ssHtml += "\nelement.scrollLeft = (maxScrollLeft);";
        ssHtml += "\n";
        ssHtml += "\nfunction tableFixHeadC(evt) {";
        ssHtml += "\n  const el = evt.currentTarget,";
        ssHtml += "\n    sT = el.scrollTop;";
        ssHtml += "\nel.querySelectorAll(\"tr:first-child\").forEach(tr =>";
        ssHtml += "\n    tr.style.transform = `translateY(${sT}px)`";
        ssHtml += "\n  );";
        ssHtml += "\n";
        ssHtml += "\n";
        ssHtml += "\n    sL = el.scrollLeft;";
        ssHtml += "\n    sL = sL-maxScrollLeft;";
        ssHtml += "\n//sL = 0;";
        ssHtml += "\nel.querySelectorAll(\"td:last-child\").forEach(td =>";
        ssHtml += "\n    td.style.transform = `translateX(${sL}px)`";
        ssHtml += "\n  );";
        ssHtml += "\n}";
        ssHtml += "\n";
        ssHtml += "\nfunction tableFixHead(evt) {";
        ssHtml += "\n  const el = evt.currentTarget,";
        ssHtml += "\n    sT = el.scrollTop;";
        ssHtml += "\nel.querySelectorAll(\"tr:first-child\").forEach(tr =>";
        ssHtml += "\n    tr.style.transform = `translateY(${sT}px)`";
        ssHtml += "\n  );";
        ssHtml += "\n";
        ssHtml += "\n    sL = el.scrollLeft;";
        ssHtml += "\nel.querySelectorAll(\"td:first-child\").forEach(td =>";
        ssHtml += "\n    td.style.transform = `translateX(${sL}px)`";
        ssHtml += "\n  );";
        ssHtml += "\n}";
        ssHtml += "\n";
        ssHtml += "\nlet firstCol = document.getElementById('divPE').querySelectorAll('tr:first-child, td:first-child')";
        ssHtml += "\nfor (let i = 0; i < firstCol.length; i++) {";
        ssHtml += "\n  firstCol[i].style.background = 'gray'";
        ssHtml += "\n}";
        ssHtml += "\n";
        ssHtml += "\nlet firstCol2 = document.getElementById('divCE').querySelectorAll('tr:first-child, td:last-child')";
        ssHtml += "\nfor (let i = 0; i < firstCol2.length; i++) {";
        ssHtml += "\n  firstCol2[i].style.background = 'gray'";
        ssHtml += "\n}";
        ssHtml += "\n";
        ssHtml += "\n";
        ssHtml += "\ndocument.querySelectorAll(\".tableFixHead\").forEach(el =>";
        ssHtml += "\n  el.addEventListener(\"scroll\", tableFixHead)";
        ssHtml += "\n);";
        ssHtml += "\ndocument.querySelectorAll(\".tableFixHeadC\").forEach(el =>";
        ssHtml += "\n  el.addEventListener(\"scroll\", tableFixHeadC)";
        ssHtml += "\n);";


//=====================================================//
        ssHtml += "\nfunction runTblFncn2(){";
        ssHtml += "\n";
        ssHtml += "\n";
        ssHtml += "\n";
        ssHtml += "\n}";

        ssHtml += "\n$('#divCE').on('scroll', function () {";
        ssHtml += "\n    $('#divPE').scrollTop($(this).scrollTop());";
        ssHtml += "\n    $('#divPE').scrollLeft(maxScrollLeft-$(this).scrollLeft());";
        ssHtml += "\n";
        ssHtml += "\n});";
        ssHtml += "\n";
        ssHtml += "\n";
        ssHtml += "\n$('#divPE').on('scroll', function () {";
        ssHtml += "\n    $('#divCE').scrollTop($(this).scrollTop());";
        ssHtml += "\n    $('#divCE').scrollLeft(maxScrollLeft-$(this).scrollLeft());";
        ssHtml += "\n";
        ssHtml += "\n});";

        ssHtml += "\n</script>";
        ssHtml += "\n</html>";

        webview.loadDataWithBaseURL(null, ssHtml, "text/html", "UTF-8", null);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 200ms
                //start("getAllOptVal,"+consumerKey+"");

                if(Helper.isBetweenTimeSpan()) {
                    //loadIndexAllWbVw2();
                }

            }
        }, 600);


    }

    //===============================================================//
    //===============================================================//

    public void doLoadActiveExpiry92(String optExpDte){
        //Toast.makeText(loadOptChain.this, "Clicked on "+optExpDte, Toast.LENGTH_LONG).show();
        String optTable = "";
        String optTablePE = "";
        double ceOiTot = 0;
        double peOiTot = 0;
        double pcrVal = 0;

        for (int i = 0; i < optData.length(); i++) {
            try {
                JSONObject jSONObjCur = optData.getJSONObject(i);
                if (jSONObjCur.getString("expiryDate").equals(optExpDte)) {
                    JSONObject jSONObjCE = optData.getJSONObject(i).getJSONObject("CE");
                    JSONObject jSONObjPE = optData.getJSONObject(i).getJSONObject("PE");

                    optTable+="<tr id=\""+jSONObjCE.getString("identifier")+"\">";
                    optTable+="<td>"+jSONObjCE.getString("totalTradedVolume")+"</td>";
                    optTable+="<td>"+""+precision.format(Double.parseDouble(jSONObjCE.getString("pchangeinOpenInterest")))+"</td>";
                    optTable+="<td>"+jSONObjCE.getString("changeinOpenInterest")+"</td>";
                    optTable+="<td>"+jSONObjCE.getString("openInterest")+"</td>";
                    optTable+="<td>"+""+precision.format(Double.parseDouble(jSONObjCE.getString("pChange")))+"</td>";
                    optTable+="<td>"+""+precision.format(Double.parseDouble(jSONObjCE.getString("change")))+"</td>";
                    optTable+="<td>"+jSONObjCE.getString("lastPrice")+"</td>";
                    optTable+="<td>"+jSONObjCE.getString("strikePrice")+"</td>";
                    optTable+="</tr>";

                    ceOiTot += Double.parseDouble(jSONObjCE.getString("openInterest"));


                    optTablePE+="<tr id=\""+jSONObjPE.getString("identifier")+"\">";
                    optTablePE+="<td>"+jSONObjPE.getString("strikePrice")+"</td>";
                    optTablePE+="<td>"+jSONObjPE.getString("lastPrice")+"</td>";
                    optTablePE+="<td>"+""+precision.format(Double.parseDouble(jSONObjPE.getString("change")))+"</td>";
                    optTablePE+="<td>"+""+precision.format(Double.parseDouble(jSONObjPE.getString("pChange")))+"</td>";
                    optTablePE+="<td>"+jSONObjPE.getString("openInterest")+"</td>";
                    optTablePE+="<td>"+jSONObjPE.getString("changeinOpenInterest")+"</td>";
                    optTablePE+="<td>"+""+precision.format(Double.parseDouble(jSONObjPE.getString("pchangeinOpenInterest")))+"</td>";
                    optTablePE+="<td>"+jSONObjPE.getString("totalTradedVolume")+"</td>";
                    optTablePE+="</tr>";

                    peOiTot += Double.parseDouble(jSONObjPE.getString("openInterest"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //===============================================================//
        //===============================================================//
        pcrVal = (peOiTot/ceOiTot)*100;

        ssHtml = "";
        ssHtml += "<html><head>";
        ssHtml += "\n<style type=\"text/css\">";
        ssHtml += "\ncol:nth-child(odd) {";
        ssHtml += "\n  background:#b8d1f3;";
        ssHtml += "\n}";
        ssHtml += "\n";
        ssHtml += "\ncol:nth-child(even) {";
        ssHtml += "\n  background:#dae5f4;";
        ssHtml += "\n}";
        ssHtml += "\nlabel {";
        ssHtml += "\n    display: inline-block;";
        ssHtml += "\n}";

        ssHtml += "\n</style>";
        ssHtml += "\n";
        ssHtml += "\n<script type=\"text/javascript\" src=\"file:///android_asset/www/jquery.min.js\"></script>";
        ssHtml += "\n</head><body onload=\"runTblFncn()\">";
        ssHtml += "\n";

        ssHtml += "\n<div id=\"divCEAll\" style=\"position:fixed;left:1px;top:26px;width:50%;height:80%;border:1px solid red;background-color:white;\">";
        ssHtml += "\n   <div id=\"divCE\" style=\"position:absolute;left:1px;top:1px;width:100%;height:100%;border:0px solid;background-color:white;overflow: auto;\">";
        ssHtml += "\n   <table id=\"tblCE\" border=\"1\" style=\"border:1px solid black;border-collapse:collapse;\"><colgroup><col><col><col><col><col><col><col><col></colgroup>"+optTable+"</table>";
        ssHtml += "\n   </div>";
        ssHtml += "\n</div>";
        ssHtml += "\n";

        ssHtml += "\n<div id=\"divPEAll\" style=\"position:fixed;left:50%;top:26px;width:50%;height:80%;border:1px solid red;background-color:white;\">";
        ssHtml += "\n   <div id=\"divPE\" style=\"position:absolute;left:1px;top:1px;width:100%;height:100%;border:0px solid;background-color:white;overflow: auto;\">";
        ssHtml += "\n   <table id=\"tblPE\" border=\"1\" style=\"border:1px solid black;border-collapse:collapse;\"><colgroup><col><col><col><col><col><col><col><col></colgroup>"+optTablePE+"</table>";
        ssHtml += "\n   </div>";
        ssHtml += "\n</div>";
        ssHtml += "\n";

        ssHtml += "\n<div id=\"divCEHeadAll\" style=\"position:fixed;left:1px;top:1px;width:50%;height:23px;border:1px solid red;background-color:white;\">";
        ssHtml += "\n   <div id=\"divCEHead\" style=\"position:absolute;left:1px;top:-24px;width:100%;height:44px;border:0px solid;background-color:white;overflow: auto;\">";
        ssHtml += "\n   <table id=\"tblCEHead\" border=\"1\" style=\"border:1px solid black;border-collapse:collapse;\">";
        ssHtml += "\n   <colgroup><col><col><col><col><col><col><col><col></colgroup>";
        ssHtml += "\n   <tr><td><label id=\"lblCEHead_0\">1</label></td><td><label id=\"lblCEHead_1\">1</label></td><td><label id=\"lblCEHead_2\">1</label></td><td><label id=\"lblCEHead_3\">1</label></td><td><label id=\"lblCEHead_4\">1</label></td><td><label id=\"lblCEHead_5\">1</label></td><td><label id=\"lblCEHead_6\">1</label></td><td><label id=\"lblCEHead_7\">1</label></td></tr>";
        ssHtml += "\n   <tr><td>Volume</td><td>Vol%</td><td>ChOI</td><td>OI</td><td>Chg%</td><td>Change</td><td>Price</td><td>Strike</td></tr>";
        ssHtml += "\n   </table>";
        ssHtml += "\n   </div>";
        ssHtml += "\n</div>";
        ssHtml += "\n";
        ssHtml += "\n<div id=\"divPEHeadAll\" style=\"position:fixed;left:50%;top:1px;width:50%;height:23px;border:1px solid red;background-color:white;\">";
        ssHtml += "\n   <div id=\"divPEHead\" style=\"position:absolute;left:1px;top:-24px;width:100%;height:44px;border:0px solid;background-color:white;overflow: auto;\">";
        ssHtml += "\n   <table id=\"tblPEHead\" border=\"1\" style=\"border:1px solid black;border-collapse:collapse;\">";
        ssHtml += "\n   <colgroup><col><col><col><col><col><col><col><col></colgroup>";
        ssHtml += "\n   <tr><td><label id=\"lblPEHead_0\">1</label></td><td><label id=\"lblPEHead_1\">1</label></td><td><label id=\"lblPEHead_2\">1</label></td><td><label id=\"lblPEHead_3\">1</label></td><td><label id=\"lblPEHead_4\">1</label></td><td><label id=\"lblPEHead_5\">1</label></td><td><label id=\"lblPEHead_6\">1</label></td><td><label id=\"lblPEHead_7\">1</label></td></tr>";
        ssHtml += "\n   <tr><td>Strike</td><td>Price</td><td>Change</td><td>Chg%</td><td>OI</td><td>ChOI</td><td>Vol%</td><td>Volume</td></tr>";
        ssHtml += "\n   </table>";
        ssHtml += "\n   </div>";
        ssHtml += "\n</div>";
        ssHtml += "\n";
        ssHtml += "\n<div style=\"position:fixed;left:50%;top:87%;width:50%;height:23px;border:0px solid red;background-color:white;\">";
        ssHtml += "\nPCR:"+precision.format(pcrVal);
        ssHtml += "\n</div>";
        ssHtml += "\n<br>";

        ssHtml += "\n";
        ssHtml += "\n</body>";
        ssHtml += "\n<script type=\"text/javascript\" >\n";
        ssHtml += "\n";
        ssHtml += "\nvar trowsCE = document.getElementById(\"tblCE\").rows;";
        ssHtml += "\nvar trowsPE = document.getElementById(\"tblPE\").rows;";
        ssHtml += "\ndocument.getElementById('tblCE').addEventListener('click', function (e) {";
        ssHtml += "\n    var target = e.target,row, col, rX, cX;";
        ssHtml += "\n    col = target.parentElement;";
        ssHtml += "\n    row = col.parentElement;";
        ssHtml += "\n    rX = row.rowIndex;";
        ssHtml += "\n    cX = col.cellIndex;";
        ssHtml += "\n    var trid = $(this).closest('tr').attr('id'); // table row ID";
        ssHtml += "\n";
        ssHtml += "\n});";

        ssHtml += "\nfunction runTblFncn() {";
        ssHtml += "\nvar left=document.getElementById('divCEHead').offsetWidth;";
        ssHtml += "\nvar left2=document.getElementById('divCE').offsetWidth;";
        ssHtml += "\n$('#divCE').scrollLeft(left2+150);";
        ssHtml += "\n$('#divCEHead').scrollLeft(left+150);";
        ssHtml += "\n";



        //ssHtml += "\ndocument.getElementById('divCEAll').style.width=\"900px\";";
        //ssHtml += "\ndocument.getElementById('divPEAll').style.width=\"900px\";";
        //ssHtml += "\ndocument.getElementById('divCEHead').style.width=\"2000px\";";
        //ssHtml += "\ndocument.getElementById('divPEHead').style.width=\"2000px\";";

        ssHtml += "\n";
        ssHtml += "\nlet table1_elems = $(\"#tblCE tr:first td\");";
        ssHtml += "\nlet table2_elems = $(\"#tblCEHead tr:first td\");";
        ssHtml += "\n";
        ssHtml += "\n$(table1_elems).each(function(i) {";
        ssHtml += "\n    document.getElementById(\"lblCEHead_\"+i).style.width = $(this).width();";
        ssHtml += "\n});";
        ssHtml += "\n";
        ssHtml += "\nlet table3_elems = $(\"#tblPE tr:first td\");";
        ssHtml += "\nlet table4_elems = $(\"#tblPEHead tr:first td\");";
        ssHtml += "\n";
        ssHtml += "\n$(table3_elems).each(function(i) {";
        ssHtml += "\n    document.getElementById(\"lblPEHead_\"+i).style.width = $(this).width();";
        ssHtml += "\n});";
        ssHtml += "\n";
        ssHtml += "\n}";
        ssHtml += "\n";
        ssHtml += "\n$('#divCE').on('scroll', function () {";
        ssHtml += "\n    $('#divPE').scrollTop($(this).scrollTop());";
        ssHtml += "\n    var left=document.getElementById('divCEHead').offsetWidth;";
        ssHtml += "\n    var left2=document.getElementById('divCE').offsetWidth-document.getElementById('divCEHead').offsetWidth;";
        ssHtml += "\n    $('#divCEHead').scrollLeft($(this).scrollLeft()-left2);";
        ssHtml += "\n});";
        ssHtml += "\n";
        ssHtml += "\n";
        ssHtml += "\n$('#divPE').on('scroll', function () {";
        ssHtml += "\n    $('#divCE').scrollTop($(this).scrollTop());";
        ssHtml += "\n    $('#divPEHead').scrollLeft($(this).scrollLeft());";
        ssHtml += "\n});";
        ssHtml += "\n";
        ssHtml += "\n";
        ssHtml += "\n</script>";
        ssHtml += "\n</html>";

        webview.loadDataWithBaseURL(null, ssHtml, "text/html", "UTF-8", null);


////send an ACTION_CREATE_DOCUMENT intent to the system. It will open a dialog where the user can choose a location and a filename
        //Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        //intent.addCategory(Intent.CATEGORY_OPENABLE);
        ////intent.setType("YOUR FILETYPE"); //not needed, but maybe usefull
        //intent.putExtra(Intent.EXTRA_TITLE, "opt_chain.html"); //not needed, but maybe usefull
        //intent.setType("*/*");
        //startActivityForResult(intent, 990);
        ////someActivityResultLauncher.launch(intent);

    }


    //===================================================================//

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 990 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            //just as an example, I am writing a String to the Uri I received from the user:
            try {
                //String strcsvdata = TextUtils.join("\n",csvdata);
                byte[] bytescsv = ssHtml.getBytes("UTF-8");

                OutputStream output = getApplicationContext().getContentResolver().openOutputStream(uri);
                output.write(bytescsv);
                output.flush();
                output.close();
            }
            catch(IOException e) {
                Toast.makeText(loadOptChain.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //===================================================================//




}