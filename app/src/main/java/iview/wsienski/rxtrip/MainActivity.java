package iview.wsienski.rxtrip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.text);
        Button b = (Button) findViewById(R.id.button);
        EditText editText = (EditText) findViewById(R.id.editText);

        bindButton(b);

        bindEditText(textView, editText);
    }

    private void bindEditText(TextView textView, EditText editText) {
        RxTextView.textChanges(editText)
                .map(charSequence -> new StringBuilder(charSequence).reverse().toString())
                .subscribe(charSequence -> textView.setText(charSequence));
    }

    private void bindButton(Button b) {
        RxView.clicks(b)
                .subscribe(aVoid -> Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show());
    }
}
