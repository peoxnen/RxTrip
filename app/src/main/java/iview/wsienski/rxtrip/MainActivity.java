package iview.wsienski.rxtrip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.text);
        Button b = (Button) findViewById(R.id.button);
        Observable<Void> clickObservable = RxView.clicks(b).share();
        EditText editText = (EditText) findViewById(R.id.editText);
        CompositeSubscription compositeSubscription = new CompositeSubscription();


        bindSubstrictionToKey(clickObservable, compositeSubscription, aVoid -> textView.setText("Click"));
        bindSubstrictionToKey(clickObservable, compositeSubscription, aVoid -> Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show());

        bindEditText(textView, editText);

        simpleCall();
        mapOperator();
        mapOperator2();
        flatMapOpertor();
        filterOperator();
    }

    private void filterOperator() {
      Observable.just("one","two","three")
                .filter(t -> t.contains("o"))
              .subscribe(s -> Log.d("filterOperator", s));
    }

    private void flatMapOpertor() {
        Observable.just("one")
                .flatMap(s -> Observable.from(s.split("")))
                .map(s -> "char "+s)
                .skip(1)
                .subscribe(s -> Log.d("flatMapOpertor", s));
    }

    private void mapOperator() {
        Observable.just("Hello, world!").map(s -> s + " Hello Witek").subscribe(s -> Log.d("mapOperator", s));
    }

    private void mapOperator2() {
        Observable.just("Hello, world!").map(s -> s.length()).subscribe(i -> Log.d("mapOperator", "length "+i));
    }

    private void simpleCall() {
        Observable.just("Hello, world.").subscribe(s -> Log.d("simpleCall", s));
    }

    private void bindSubstrictionToKey(Observable<Void> clickObservable, CompositeSubscription compositeSubscription, Action1<Void> click) {
        Subscription subscription = clickObservable.subscribe(click);
        compositeSubscription.add(subscription);
    }

    private void bindEditText(TextView textView, EditText editText) {
        RxTextView.textChanges(editText)
                .map(charSequence -> new StringBuilder(charSequence).reverse().toString())
                .subscribe(charSequence -> textView.setText(charSequence));
    }
}
