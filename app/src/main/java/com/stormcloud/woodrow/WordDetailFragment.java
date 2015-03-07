package com.stormcloud.woodrow;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stormcloud.woodrow.DictionaryEntry.DictionaryEntryXmlParser;
import com.stormcloud.woodrow.DictionaryEntry.DictionaryEntry;
import com.stormcloud.woodrow.database.DatabaseHandler;
import com.stormcloud.woodrow.model.Word;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by schhan on 2/20/15.
 */
public class WordDetailFragment extends Fragment {

    public static final String TAG = "WordDetailFragment";

    /**
     * The fragment argument representing the item ID that this fragment represents
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The Word this fragment is presenting
     */
    private Word mItem;
    private DictionaryEntry mEntry;

    /**
     * The UI elements showing the details of the Word*
     */
    private TextView tvWord;
    private TextView tvDateAdded;
    private TextView tvDefinition;

    private Button btnRetrieveDefinition;

    /**
     * Connection/Internet availability related variables
     */

    public static final String WIFI = "Wi-Fi";
    public static final String ANY = "Any";

    // Whether there is a Wi-Fi connection.
    private static boolean wifiConnected = false;
    // Whether there is a mobile connection.
    private static boolean mobileConnected = false;
    // Whether the display should be refreshed.
    public static boolean refreshDisplay = true;
    public static String sPref = null;

    private String urlString;
    private static final String URL_STEM = "http://www.dictionaryapi.com/api/v1/references/collegiate/xml/";
    private static final String API_KEY = "a151e819-6103-452b-a42a-438e13922ae6";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     * (e.g. upon screen orientation changes).
     */
//    public WordDetailFragment() {}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider

            mItem = DatabaseHandler.getInstance(getActivity()).getWord(getArguments().getLong(ARG_ITEM_ID));
        } else {
            mItem = new Word();
        }

        ConnectivityManager connManager = (ConnectivityManager) getActivity().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        wifiConnected = mWifi.isConnected();

        NetworkInfo mMobi = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        mobileConnected = mMobi.isConnected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_word_detail, container, false);

        if (mItem != null) {
            tvWord = (TextView) view.findViewById(R.id.textWord);
            tvWord.setText(mItem.word);

            tvDateAdded = (TextView) view.findViewById(R.id.textDateAdded);
            tvDateAdded.setText(mItem.dateadded);

            tvDefinition = (TextView) view.findViewById(R.id.textDefinition);
            tvDefinition.setText(mItem.definition);
        }

        btnRetrieveDefinition = (Button) view.findViewById(R.id.buttonRetrieveDefinition);
        btnRetrieveDefinition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String word = tvWord.getText().toString();
                new DownloadXmlTask().execute(word);

            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        updateWordFromUI();
    }

    private void updateWordFromUI() {
        if (mItem != null) {
            mItem.word = tvWord.getText().toString();
            mItem.dateadded = tvDateAdded.getText().toString();
            mItem.definition = tvDefinition.getText().toString();

            DatabaseHandler.getInstance(getActivity()).putWord(mItem);
        }
    }

    // Uses AsyncTask to download the XML feed from DictionaryAPI.com
    private class DownloadXmlTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            loadXmlFromNetWork(params[0]);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            // TODO Use mEntry and populate with retreieved info from DictionaryAPI.com
            
            if (mEntry != null) {
                tvDefinition.setText(mEntry.getEntryDefinition());
            }
        }
    }

    public void loadXmlFromNetWork(String word) {

//        if((sPref.equals(ANY)) && (wifiConnected || mobileConnected)) {
        if (wifiConnected || mobileConnected) {

            urlString = URL_STEM + word + "?key=" + API_KEY;
            InputStream in = null;

            try {

                /**
                 * Start XML parsing. Check if parsing is functioning properly 
                 */

                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();

                in = new BufferedInputStream(urlConnection.getInputStream());

                DictionaryEntryXmlParser deXmlParser = new DictionaryEntryXmlParser();
                List<DictionaryEntry> entries = null;

                entries = deXmlParser.parse(in);

                if (entries.size() > 0) {
                    /* TODO Implement "Multiple entries found" dialog and allow user to choose
                    Save this for when we find multiple entries. For now let's just match the first.
                   
                    for (DictionaryEntry entry : entries) {
                        if(entry == null) {

                            Log.e(TAG, "the entry was null. A null was added to the collection");
                        } else {
                            Log.e(TAG, entry.toString());
                        }
                    } */
                    mEntry = entries.get(0);
                    Log.e(TAG, mEntry.toString());
                } else {
                    Log.e(TAG, "Entries is empty");
                }

                in.close();

            } catch (MalformedURLException ex) {
                Log.e("DictionaryEntry", "Malformed URL: " + ex.toString());
            } catch (IOException ex) {
                Log.e("DictionaryEntry", "IOException: " + ex.toString());
            } catch (XmlPullParserException ex) {
                Log.e("DictionaryEntry", "XmlPullParserException: " + ex.toString());
            }

//        else if ((sPref.equals(WIFI)) && (wifiConnected)) {
//        }

        }
    }
}
