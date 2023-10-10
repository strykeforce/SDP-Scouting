package org.wildstang.wildrank.androidv2.views.data;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.couchbase.lite.Document;

import org.wildstang.wildrank.androidv2.interfaces.IMatchDataView;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.observables.MathObservable;
import rx.schedulers.Schedulers;


public class MatchDataMaxEndLocAttemptView extends MatchDataView implements IMatchDataView {

    public MatchDataMaxEndLocAttemptView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }

        Observable<Double> stacksObservable = Observable.from(documents)
                .map(doc -> (Map<String, Object>) doc.getProperty("data"))
                .filter(data -> data.get("post_match-climb_attempt") != null)
                .map(data -> Double.parseDouble((String) data.get("post_match-climb_attempt")))
                .filter(ranking -> ranking != 0)
                .defaultIfEmpty(0.0)
                .subscribeOn(Schedulers.computation());

        MathObservable.max(stacksObservable)
                .map(max -> String.format("%.0f", max))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(max -> setValueText(max, "gray"), error -> Log.d("wildrank", this.getClass().getName()));

    }

    public void calculateFromDocument(Document document) {}
}
