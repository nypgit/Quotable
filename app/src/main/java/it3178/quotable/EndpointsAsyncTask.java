package it3178.quotable;

/**
 * Created by user on 4/8/2017.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import it3178.backend.quoteApi.QuoteApi;
import it3178.backend.quoteApi.model.Quote;

public class EndpointsAsyncTask extends AsyncTask<Void, Void, List<Quote>> {
    private static QuoteApi myApiService = null;
    private Context context;

    EndpointsAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Quote> doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            QuoteApi.Builder builder = new   QuoteApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://it3178student-146512.appspot.com/_ah/api/");
            myApiService = builder.build();
        }

        try {
            List<Quote> quotes =  myApiService.list().execute().getItems();
            return quotes;
        } catch (IOException e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<Quote> result) {
        for (Quote q : result) {
            Toast.makeText(context, q.getWho() + " : " + q.getWhat(), Toast.LENGTH_LONG).show();
        }
    }
}