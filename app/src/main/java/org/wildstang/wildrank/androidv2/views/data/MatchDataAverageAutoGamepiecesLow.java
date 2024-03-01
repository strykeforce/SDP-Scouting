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

public class MatchDataAverageAutoGamepiecesLow extends MatchDataView implements IMatchDataView {

    public MatchDataAverageAutoGamepiecesLow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }

        Observable defObservable = Observable.from(documents)
                .map(doc -> (Map<String, Object>) doc.getProperty("data"))
                .map(data -> data.get("auto-score_cargo_low"))
                .filter(defs -> defs != null)
                .map(defs -> (int) defs);

        MathObservable.averageDouble(defObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sum -> setValueText("" + sum, "gray"), error -> Log.d("wildrank", this.getClass().getName()));

    }

    public void calculateFromDocument(Document document) {}
}
