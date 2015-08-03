package cn.chaobao.android_rxjava_multiple_sources_sample;

import android.app.Activity;
import android.os.Bundle;

import rx.Observable;

public class MainActivity extends Activity {
    Sources sources = new Sources();
    // Create our sequence for querying best available data
    Observable<Data> source = Observable.concat(
            sources.memory(),
            sources.disk(),
            sources.network()
    )
            .first(data -> data != null && data.isUpToDate());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.get).setOnClickListener(e -> start());
        findViewById(R.id.clear).setOnClickListener(e -> clear());
    }

    private void clear() {
        sources.clearMemory();
    }

    public void start() {
        source.subscribe(data -> System.out.println("Received: " + data.value));
    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
}
