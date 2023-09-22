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

public class MatchDataMaxAutoHighView extends MatchDataView implements IMatchDataView {

    public MatchDataMaxAutoHighView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void calculateFromDocuments(List<Document> documents) {
        if (documents == null) {
            return;
        } else if (documents.size() == 0) {
            return;
        }

        Observable autoHighObservable = Observable.from(documents)
                .map(doc -> (Map<String, Object>) doc.getProperty("data"))
                .map(data -> data.get("auto-score_cargo_high"))
                .filter(shots -> shots != null)
                .map(shots -> (int) shots);

        MathObservable.max(autoHighObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(max -> setValueText("" + max, "gray"), error -> Log.d("wildrank", this.getClass().getName()));

    }

    public void calculateFromDocument(Document document) {}
}
